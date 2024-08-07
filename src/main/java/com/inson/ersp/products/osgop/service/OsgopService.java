package com.inson.ersp.products.osgop.service;

import com.inson.ersp.commons.entity.UserEntity;
import com.inson.ersp.commons.exceptions.DatabaseIntegrationException;
import com.inson.ersp.commons.payload.request.InsurantRequest;
import com.inson.ersp.commons.payload.response.ApiResponseAll;
import com.inson.ersp.commons.payload.response.StatusResponse;
import com.inson.ersp.commons.service.LogService;
import com.inson.ersp.commons.utils.DateUtil;
import com.inson.ersp.products.osgop.payload.request.OsgopContractRequest;
import com.inson.ersp.products.osgop.payload.response.OsgopContractResponse;
import lombok.RequiredArgsConstructor;
import oracle.jdbc.OracleConnection;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OsgopService {
    private final LogService logService;
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public OsgopContractResponse createContract(OsgopContractRequest dto) throws SQLException {
        dto.validateRequest();
        OsgopContractResponse response;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) authentication.getPrincipal();

        SimpleJdbcCall jdbcCall =
                new SimpleJdbcCall(jdbcTemplate)
                        .withCatalogName("for_osgop_ersp_api")
                        .withFunctionName("create_contract")
                        .declareParameters(
                                new SqlOutParameter("error", Types.INTEGER),
                                new SqlOutParameter("error_text", Types.VARCHAR)
                        );



        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("contract_uuid", dto.getContractUuid())
                .addValue("p_contract_register_number", dto.getContractRegisterNumber())
                .addValue("contract_sum", dto.getContractSum())
                .addValue("contract_start_date", DateUtil.dateToString(dto.getContractStartDate()), Types.DATE)
                .addValue("contract_end_date", DateUtil.dateToString(dto.getContractEndDate()), Types.DATE)
                .addValue("region_id", dto.getRegionId())
                .addValue("area_type_id", dto.getAreaTypeId())
                .addValue("insurant", getInsurant(dto.getInsurant()), Types.STRUCT, "INSURANT_ROW_TYPE")
                .addValue("policy", getPolicy(dto.getPolicies()), Types.ARRAY, "OSGOP_POLICIES")
                .addValue("p_user_id", user.getTbId());

        Map<String, Object> out = jdbcCall.execute(in);
        int result = Integer.parseInt(String.valueOf(out.get("error")));
        String errText = String.valueOf(out.get("error_text"));
        if (result == 0) {
            long contractId = Long.parseLong(String.valueOf(out.get("return")));
            response = new OsgopContractResponse(new OsgopContractResponse.OsgopCreateContractData(contractId));
            logService.addLog(dto, response, "OSGOP: create()");
            return response;
        } else {
            logService.addLog(dto, new ApiResponseAll(new StatusResponse("Database error", List.of(errText), result)), "OSGOP: create()");
            throw new DatabaseIntegrationException(result, errText);
        }

    }

    @Transactional
    public Struct getInsurant(InsurantRequest req) throws SQLException {

        Connection connection = DataSourceUtils.getConnection(Objects.requireNonNull(jdbcTemplate.getDataSource()));
        OracleConnection oracleConnection = connection.unwrap(OracleConnection.class);

        boolean isOrganization = req.getOrganization() != null;
        try {
                Object[] insurant = new Object[20];
                insurant[0] = !isOrganization ? req.getPerson().getPassportData().getPinfl() : null;
                insurant[1] = !isOrganization ? req.getPerson().getPassportData().getSeria() : null;
                insurant[2] = !isOrganization ? req.getPerson().getPassportData().getNumber(): null;
                insurant[3] = !isOrganization ? req.getPerson().getFullName().getFirstname() : null;
                insurant[4] = !isOrganization ? req.getPerson().getFullName().getLastname() : null;
                insurant[5] = !isOrganization ? req.getPerson().getFullName().getMiddlename() : null;
                insurant[6] = !isOrganization ? req.getPerson().getRegionId() : req.getOrganization().getRegionId();
                insurant[7] = !isOrganization ? req.getPerson().getBirthDate() : null;
                insurant[8] = !isOrganization ? (req.getPerson().getGender().equals("m")?1:0) : null;
                insurant[9] = !isOrganization ? req.getPerson().getResidentType() : null;
                insurant[10] = isOrganization ? req.getOrganization().getInn() : null;
                insurant[11] = isOrganization ? req.getOrganization().getName() : null;
                insurant[12] = isOrganization ? req.getOrganization().getRepresentativeName() : null;
                insurant[13] = isOrganization ? req.getOrganization().getOked() : null;
                insurant[14] = isOrganization ? req.getOrganization().getPosition() : null;
                insurant[15] = isOrganization ? req.getOrganization().getCheckingAccount() : null;
                insurant[16] = isOrganization ? 1 : 0;
                insurant[17] = isOrganization ? req.getOrganization().getPhone() : req.getPerson().getPhone();
                insurant[18] = 1010;
                insurant[19] = isOrganization ? req.getOrganization().getAddress() : req.getPerson().getAddress();

            return oracleConnection.createStruct("INSURANT_ROW_TYPE", insurant);
        } catch (SQLException e) {
            // Log the error message
            System.err.println("SQLException: " + e.getMessage());
            throw e;
        }
    }

    @Transactional
    public Array getPolicy(List<OsgopContractRequest.OsgopPolicy> policies) throws SQLException {

        if (policies == null || policies.isEmpty()) {
            return null;
        }

        Connection connection = DataSourceUtils.getConnection(Objects.requireNonNull(jdbcTemplate.getDataSource()));
        OracleConnection oracleConnection = connection.unwrap(OracleConnection.class);

        Struct[] policiesList = new Struct[policies.size()];

        try {
            for (int i = 0; i < policies.size(); i++) {
                OsgopContractRequest.OsgopPolicy policy = policies.get(i);
                Object[] policyObj = new Object[8];
                policyObj[0] = policy.getUuid();
                policyObj[1] = DateUtil.dateToString(policy.getStartDate());
                policyObj[2] = DateUtil.dateToString(policy.getEndDate());
                policyObj[3] = policy.getInsuranceSum();
                policyObj[4] = policy.getInsurancePremium();
                policyObj[5] = policy.getInsuranceRate();
                policyObj[6] = policy.getInsuranceTermId();
                policyObj[7] = getObjects(policy.getObjects());
                policiesList[i] = oracleConnection.createStruct("POLICY_OSGOP", policyObj);
            }

            return oracleConnection.createOracleArray("OSGOP_POLICIES", policiesList);
        } catch (SQLException e) {
            // Log the error message
            System.err.println("SQLException: " + e.getMessage());
            throw e;
        }

    }

    @Transactional
    public Array getObjects(List<OsgopContractRequest.OsgopObject> objectsData) throws SQLException {

        if (objectsData == null || objectsData.isEmpty()) {
            return null;
        }

        Connection connection = DataSourceUtils.getConnection(Objects.requireNonNull(jdbcTemplate.getDataSource()));
        OracleConnection oracleConnection = connection.unwrap(OracleConnection.class);

        Struct[] objects = new Struct[objectsData.size()];

        try {
            for (int i = 0; i < objectsData.size(); i++) {
                OsgopContractRequest.OsgopObject object = objectsData.get(i);
                Object[] policyObj = new Object[17];
                policyObj[0] = object.getUuid();
                policyObj[1] = object.getObjectType();
                policyObj[2] = object.getVehicle().getTechPassport().getSeria();
                policyObj[3] = object.getVehicle().getTechPassport().getNumber();
                policyObj[4] = object.getVehicle().getGovNumber();
                policyObj[5] = object.getVehicle().getRegionId();
                policyObj[6] = object.getVehicle().getModelCustomName();
                policyObj[7] = object.getVehicle().getVehicleTypeId();
                policyObj[8] = object.getVehicle().getIssueYear();
                policyObj[9] = object.getVehicle().getBodyNumber();
                policyObj[10] = object.getVehicle().getNumberOfSeats();
                policyObj[11] = object.getVehicle().getLicense().getSeria();
                policyObj[12] = object.getVehicle().getLicense().getNumber();
                policyObj[13] = DateUtil.dateToString(object.getVehicle().getLicense().getBeginDate());
                policyObj[14] = DateUtil.dateToString(object.getVehicle().getLicense().getEndDate());
                policyObj[15] = object.getVehicle().getLicense().getTypeCode();
                policyObj[16] = getOwner(object.getVehicle().getOwnerPerson(), object.getVehicle().getOwnerOrganization());
                objects[i] = oracleConnection.createStruct("POLICY_OBJECT_OSGOP", policyObj);
            }

            return oracleConnection.createOracleArray("OSGOP_POLICY_OBJECTS", objects);
        } catch (SQLException e) {
            // Log the error message
            System.err.println("SQLException: " + e.getMessage());
            throw e;
        }

    }

    @Transactional
    public Struct getOwner(OsgopContractRequest.OsgopObject.Vehicle.OwnerPerson person, OsgopContractRequest.OsgopObject.Vehicle.OwnerOrganization organization) throws SQLException {

        Connection connection = DataSourceUtils.getConnection(Objects.requireNonNull(jdbcTemplate.getDataSource()));
        OracleConnection oracleConnection = connection.unwrap(OracleConnection.class);

        boolean isOrganization = organization != null;
        try {
            Object[] insurant = new Object[20];
            insurant[0] = !isOrganization ? person.getPassportData().getPinfl() : null;
            insurant[1] = !isOrganization ? person.getPassportData().getSeria() : null;
            insurant[2] = !isOrganization ? person.getPassportData().getNumber(): null;
            insurant[3] = !isOrganization ? person.getFullName().getFirstname() : null;
            insurant[4] = !isOrganization ? person.getFullName().getLastname() : null;
            insurant[5] = !isOrganization ? person.getFullName().getMiddlename() : null;
            insurant[6] = !isOrganization ? person.getRegionId() : organization.getRegionId();
            insurant[7] = !isOrganization ? person.getBirthDate() : null;
            insurant[8] = !isOrganization ? (person.getGender().equals("m")?1:0) : null;
            insurant[9] = !isOrganization ? person.getResidentType() : null;
            insurant[10] = isOrganization ? organization.getInn() : null;
            insurant[11] = isOrganization ? organization.getName() : null;
            insurant[12] = isOrganization ? organization.getRepresentativeName() : null;
            insurant[13] = isOrganization ? organization.getOked() : null;
            insurant[14] = isOrganization ? organization.getPosition() : null;
            insurant[15] = isOrganization ? organization.getCheckingAccount() : null;
            insurant[16] = isOrganization ? 1 : 0;
            insurant[17] = isOrganization ? organization.getPhone() : null;
            insurant[18] = 1010;
            insurant[19] = isOrganization ? organization.getAddress() : person.getAddress();

            return oracleConnection.createStruct("INSURANT_ROW_TYPE", insurant);
        } catch (SQLException e) {
            // Log the error message
            System.err.println("SQLException: " + e.getMessage());
            throw e;
        }
    }

}