package com.inson.ersp.products.osgop.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.inson.ersp.commons.payload.request.ApiRequest;
import com.inson.ersp.commons.payload.request.InsurantRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class OsgopContractRequest extends ApiRequest {

    @Schema(description = "UUID of the OSGOP contract.", example = "91dc546b-0026-4b8e-a073-02f5847d1024")
    @NotNull(message = "UUID cannot be null.")
    @NotBlank(message = "UUID cannot be blank.")
    private String contractUuid;

    @Schema(description = "Contract Register Number of the OSGOP contract.", example = "AA0000050")
    @NotNull(message = "Contract Register Number cannot be null.")
    @NotBlank(message = "Contract Register Number cannot be blank.")
    private String contractRegisterNumber;

    @Schema(description = "Contract sum of the OSGOP contract.", example = "100000")
    @NotNull(message = "Contract sum cannot be null.")
    private Double contractSum;

    @Schema(description = "Contract start date.", example = "2021-01-01", pattern = "yyyy-MM-dd", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "Contract start date cannot be null.")
    @FutureOrPresent(message = "Contract start date must be future or present.")
    private LocalDate contractStartDate;

    @Schema(description = "Contract end date.", example = "2022-01-01", pattern = "yyyy-MM-dd", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "Contract end date cannot be null.")
    @FutureOrPresent(message = "Contract end date must be future or present.")
    private LocalDate contractEndDate;

    @Schema(description = "Region ID.", example = "10")
    @NotNull(message = "Region ID cannot be null.")
    private Integer regionId;

    @Schema(description = "Area type ID.", example = "1")
    @NotNull(message = "Area type ID cannot be null.")
    private Integer areaTypeId;

    @Schema(description = "List of policies.")
    @NotNull(message = "Policies cannot be null.")
    private List<OsgopPolicy> policies;

    @Schema(description = "Information about the insurant.")
    @NotNull(message = "Insurant cannot be null.")
    private InsurantRequest insurant;

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class OsgopPolicy {
        @Schema(description = "UUID of the policy.", example = "4ee1603d-e035-4ec5-9960-d246495d5712")
        @NotNull(message = "UUID cannot be null.")
        @NotBlank(message = "UUID cannot be blank.")
        private String uuid;

        @Schema(description = "Policy start date.", example = "2024-06-21", pattern = "yyyy-MM-dd", requiredMode = Schema.RequiredMode.REQUIRED)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        @NotNull(message = "Policy start date cannot be null.")
        @FutureOrPresent(message = "Policy start date must be future or present.")
        private LocalDate startDate;

        @Schema(description = "Policy end date.", example = "2024-08-18", pattern = "yyyy-MM-dd", requiredMode = Schema.RequiredMode.REQUIRED)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        @NotNull(message = "Policy end date cannot be null.")
        @FutureOrPresent(message = "Policy end date must be future or present.")
        private LocalDate endDate;

        @Schema(description = "Insurance sum of the policy.", example = "10000")
        @NotNull(message = "Insurance sum cannot be null.")
        private Double insuranceSum;

        @Schema(description = "Insurance rate.", example = "100")
        @NotNull(message = "Insurance rate cannot be null.")
        private Double insuranceRate;

        @Schema(description = "Insurance premium.", example = "1000")
        @NotNull(message = "Insurance premium cannot be null.")
        private Double insurancePremium;

        @Schema(description = "Insurance term ID.", example = "1")
        @NotNull(message = "Insurance term ID cannot be null.")
        private Integer insuranceTermId;

        @Schema(description = "List of insured objects.")
        @NotNull(message = "Objects cannot be null.")
        private List<OsgopObject> objects;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class OsgopObject {
        @Schema(description = "UUID of the object.", example = "14110652-6c6c-4f16-ae10-6339e8e9b182")
        @NotNull(message = "UUID cannot be null.")
        @NotBlank(message = "UUID cannot be blank.")
        private String uuid;

        @Schema(description = "Type of the object.", example = "3")
        @NotNull(message = "Object type cannot be null.")
        private Integer objectType;

        @Schema(description = "Vehicle details.")
        private Vehicle vehicle;

        @Getter
        @Setter
        @NoArgsConstructor
        @ToString
        public static class Vehicle {
            @Schema(description = "Technical passport details.")
            @NotNull(message = "Technical passport cannot be null.")
            private TechPassport techPassport;

            @Schema(description = "Government number of the vehicle.", example = "10A111AA")
            @NotNull(message = "Government number cannot be null.")
            @NotBlank(message = "Government number cannot be blank.")
            private String govNumber;

            @Schema(description = "Region ID.", example = "10")
            @NotNull(message = "Region ID cannot be null.")
            private Integer regionId;

            @Schema(description = "Custom model name of the vehicle.", example = "MATIZ")
            @NotNull(message = "Model custom name cannot be null.")
            @NotBlank(message = "Model custom name cannot be blank.")
            private String modelCustomName;

            @Schema(description = "Vehicle type ID.", example = "1")
            @NotNull(message = "Vehicle type ID cannot be null.")
            private Integer vehicleTypeId;

            @Schema(description = "Year of issue.", example = "2006")
            @NotNull(message = "Issue year cannot be null.")
            private Integer issueYear;

            @Schema(description = "Body number.", example = "XWB4A11AD6A079405")
            @NotNull(message = "Body number cannot be null.")
            @NotBlank(message = "Body number cannot be blank.")
            private String bodyNumber;

            @Schema(description = "Number of seats.", example = "5")
            @NotNull(message = "Number of seats cannot be null.")
            private Integer numberOfSeats;

            @Schema(description = "License details.")
            @NotNull(message = "License cannot be null.")
            private License license;

            @Schema(description = "Owner person details.")
            @NotNull(message = "Owner person cannot be null.")
            private OwnerPerson ownerPerson;

            @Schema(description = "Owner organization details.")
            private OwnerOrganization ownerOrganization;

            @Getter
            @Setter
            @NoArgsConstructor
            @ToString
            public static class TechPassport {
                @Schema(description = "Tech passport series of the vehicle.", example = "AAL")
                @NotNull(message = "Tech passport series cannot be null.")
                @NotBlank(message = "Tech passport series cannot be blank.")
                private String seria;

                @Schema(description = "Tech passport number of the vehicle.", example = "1234567")
                @NotNull(message = "Tech passport number cannot be null.")
                @NotBlank(message = "Tech passport number cannot be blank.")
                private String number;
            }

            @Getter
            @Setter
            @NoArgsConstructor
            @ToString
            public static class License {
                @Schema(description = "License series.", example = "LS")
                @NotNull(message = "License series cannot be null.")
                @NotBlank(message = "License series cannot be blank.")
                private String seria;

                @Schema(description = "License number.", example = "12345")
                @NotNull(message = "License number cannot be null.")
                @NotBlank(message = "License number cannot be blank.")
                private String number;

                @Schema(description = "License begin date.", example = "2024-01-01", pattern = "yyyy-MM-dd")
                @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
                @PastOrPresent(message = "License begin date must be past or present.")
                @NotNull(message = "License begin date cannot be null.")
                private LocalDate beginDate;

                @Schema(description = "License end date.", example = "2025-01-01", pattern = "yyyy-MM-dd")
                @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
                @NotNull(message = "License end date cannot be null.")
                private LocalDate endDate;

                @Schema(description = "License type code.", example = "SADFASD")
                @NotNull(message = "License type code cannot be null.")
                @NotBlank(message = "License type code cannot be blank.")
                private String typeCode;
            }

            @Getter
            @Setter
            @NoArgsConstructor
            @ToString
            public static class OwnerPerson {
                @Schema(description = "Passport data of the owner.")
                @NotNull(message = "Passport data cannot be null.")
                private PassportData passportData;

                @Schema(description = "Full name of the owner.")
                @NotNull(message = "Full name cannot be null.")
                private FullName fullName;

                @Schema(description = "Region ID.", example = "10")
                @NotNull(message = "Region ID cannot be null.")
                private Integer regionId;

                @Schema(description = "Birth date of the owner.", example = "1989-05-11", pattern = "yyyy-MM-dd")
                @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
                @NotNull(message = "Birth date cannot be null.")
                private LocalDate birthDate;

                @Schema(description = "Address of the owner.", example = "ул. Такая-то, дом такой-то....")
                @NotNull(message = "Address cannot be null.")
                @NotBlank(message = "Address cannot be blank.")
                private String address;

                @Schema(description = "Gender of the owner.", example = "m")
                @NotNull(message = "Gender cannot be null.")
                @NotBlank(message = "Gender cannot be blank.")
                private String gender;

                @Schema(description = "Resident type of the owner.", example = "1")
                @NotNull(message = "Resident type cannot be null.")
                private Integer residentType;

                @Getter
                @Setter
                @NoArgsConstructor
                @ToString
                public static class PassportData {
                    @Schema(description = "Pinfl of the owner.", example = "12345678901234")
                    @NotNull(message = "Pinfl cannot be null.")
                    @NotBlank(message = "Pinfl cannot be blank.")
                    private String pinfl;

                    @Schema(description = "Passport series of the owner.", example = "AA")
                    @NotNull(message = "Passport series cannot be null.")
                    @NotBlank(message = "Passport series cannot be blank.")
                    private String seria;

                    @Schema(description = "Passport number of the owner.", example = "1234567")
                    @NotNull(message = "Passport number cannot be null.")
                    @NotBlank(message = "Passport number cannot be blank.")
                    private String number;
                }

                @Getter
                @Setter
                @NoArgsConstructor
                @ToString
                public static class FullName {
                    @Schema(description = "First name of the owner.", example = "Иван")
                    @NotNull(message = "First name cannot be null.")
                    @NotBlank(message = "First name cannot be blank.")
                    private String firstname;

                    @Schema(description = "Last name of the owner.", example = "Иванов")
                    @NotNull(message = "Last name cannot be null.")
                    @NotBlank(message = "Last name cannot be blank.")
                    private String lastname;

                    @Schema(description = "Middle name of the owner.", example = "Иванович")
                    @NotNull(message = "Middle name cannot be null.")
                    @NotBlank(message = "Middle name cannot be blank.")
                    private String middlename;
                }
            }

            @Getter
            @Setter
            @NoArgsConstructor
            @ToString
            public static class OwnerOrganization {
                @Schema(description = "INN of the organization.", example = "123456789")
                @NotNull(message = "INN cannot be null.")
                @NotBlank(message = "INN cannot be blank.")
                private String inn;

                @Schema(description = "Name of the organization.", example = "ООО Рога и копыта")
                @NotNull(message = "Name cannot be null.")
                @NotBlank(message = "Name cannot be blank.")
                private String name;

                @Schema(description = "Representative name of the organization.", example = "Ольгов Ольг Ольгович")
                @NotNull(message = "Representative name cannot be null.")
                @NotBlank(message = "Representative name cannot be blank.")
                private String representativeName;

                @Schema(description = "Address of the organization.", example = "ул. Такая-то, дом такой-то....")
                @NotNull(message = "Address cannot be null.")
                @NotBlank(message = "Address cannot be blank.")
                private String address;

                @Schema(description = "OKED of the organization.", example = "62090")
                @NotNull(message = "OKED cannot be null.")
                @NotBlank(message = "OKED cannot be blank.")
                private String oked;

                @Schema(description = "Position of the representative in the organization.", example = "Директор")
                @NotNull(message = "Position cannot be null.")
                @NotBlank(message = "Position cannot be blank.")
                private String position;

                @Schema(description = "Checking account of the organization.", example = "123456789123456789")
                @NotNull(message = "Checking account cannot be null.")
                @NotBlank(message = "Checking account cannot be blank.")
                private String checkingAccount;

                @Schema(description = "Phone number of the organization.", example = "998901111111")
                @NotNull(message = "Phone number cannot be null.")
                @NotBlank(message = "Phone number cannot be blank.")
                private String phone;

                @Schema(description = "Region ID of the organization.", example = "10")
                @NotNull(message = "Region ID cannot be null.")
                private Integer regionId;
            }
        }
    }

    public void validateRequest() {
        // Implement any custom validation logic here if needed
    }
}
