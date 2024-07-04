package com.inson.ersp.commons.service;

import com.inson.ersp.commons.entity.LogEntity;
import com.inson.ersp.commons.entity.UserEntity;
import com.inson.ersp.commons.payload.request.ApiRequest;
import com.inson.ersp.commons.payload.response.ApiExternalResponseAll;
import com.inson.ersp.commons.payload.response.ApiResponse;
import com.inson.ersp.commons.payload.response.ApiResponseAll;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.TimeZone;

import static com.inson.ersp.commons.utils.JSONUtil.convertObjectToJson;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogService {
    //    private final LogRepository repository;
    private final JdbcTemplate jdbcTemplate;
    String desiredTimeZone = "Asia/Tashkent";

    // Get the current date and time in the specified time zone
    LocalDateTime zonedDateTime = ZonedDateTime.now(TimeZone.getTimeZone(desiredTimeZone).toZoneId()).toLocalDateTime();

    @Transactional
    public void addLog(ApiRequest req, ApiExternalResponseAll res, Integer integrationStatusCode, String method) {
        try {
            UserEntity users = getUserFromAuthentication();
            LogEntity log = prepareLog(req,   res.getError_message(), res, integrationStatusCode, method, users.getTbId(), res.getAnketaId());
            insertLogIntoDatabase(log);
        } catch (Exception e) {
            log.error("Error in addLog method: " + e.getMessage());
        }
    }

    @Transactional
    public void addLog(ApiRequest req, ApiResponseAll res, String method) {
        try {
            UserEntity users = getUserFromAuthentication();
            LogEntity log = prepareLog(req,   res.getStatus().getMessage(), res, null, method, users.getTbId(), res.getAnketaId());
            insertLogIntoDatabase(log);
        } catch (Exception e) {
            log.error("Error in addLog method: " + e.getMessage());
        }
    }

    @Transactional
    public void addLog(ApiResponseAll res, Integer integrationStatusCode, String method) {
        try {
            UserEntity users = getUserFromAuthentication();
            LogEntity log = prepareLog(null, (res.getStatus()) != null ? res.getStatus().getMessage() : null, res, integrationStatusCode, method, users.getTbId(), res.getAnketaId());
            insertLogIntoDatabase(log);
        } catch (Exception e) {
            log.error("Error in addLog method: " + e.getMessage());
        }
    }

    @Transactional
    public void addErrorLog(ApiRequest req, String errorMessage, ApiResponse response, Integer integrationStatusCode, String method) {
        try {
            UserEntity users = getUserFromAuthentication();
            LogEntity log = prepareLog(req, errorMessage, response, integrationStatusCode, method, users.getTbId(), null);
            insertLogIntoDatabase(log);
        } catch (Exception e) {
            log.error("Error in addErrorLog method: " + e.getMessage());
        }
    }

    @Transactional
    public void addErrorLog(ApiRequest req, String errorMessage, Integer integrationStatusCode, String method) {
        try {
            UserEntity users = getUserFromAuthentication();
            LogEntity log = prepareLog(req, errorMessage, null, integrationStatusCode, method, users.getTbId(), null);
            insertLogIntoDatabase(log);
        } catch (Exception e) {
            log.error("Error in addErrorLog method: " + e.getMessage());
        }
    }

    @Transactional
    public void addErrorLog(String errorMessage, Integer integrationStatusCode, String method) {
        try {
            UserEntity users = getUserFromAuthentication();
            LogEntity log = prepareLog(null, errorMessage, null, integrationStatusCode, method, users.getTbId(), null);
            insertLogIntoDatabase(log);
        } catch (Exception e) {
            log.error("Error in addErrorLog method: " + e.getMessage());
        }
    }

    private UserEntity getUserFromAuthentication() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return (UserEntity) authentication.getPrincipal();
        } catch (NullPointerException e) {
            UserEntity users = new UserEntity();
            users.setTbId(0L);
            return users;
        }
    }

    private LogEntity prepareLog(ApiRequest req, String message, ApiResponse response, Integer integrationStatusCode, String method, Long userId, Long anketaId) throws JsonProcessingException {
        LogEntity log = new LogEntity();
        log.setMethod(method);
        log.setReqContent(convertObjectToJson(req));
        log.setResCode(integrationStatusCode);
        log.setResMessage(message);
        log.setResContent(convertObjectToJson(response));
        log.setUserId(userId);
        log.setAnketaId(anketaId);
        return log;
    }

    private void insertLogIntoDatabase(LogEntity log) {
        String sql = "INSERT INTO OSAGO.LOG_API_SERVER(REQ_CONTENT, RES_CODE, RES_CONTENT, RES_MESSAGE, TIME_SPENT, METHOD, MICROSERVICE, USER_ID, ANKETA_ID, PRODUCT_TYPE) values (?, ?, ?, ?, ?, ?, ?, ?, ? ,?)";
        System.out.println(log.getAnketaId());
        jdbcTemplate.update(sql, log.getReqContent(), log.getResCode(), log.getResContent(), log.getResMessage(), log.getTimeSpent(), log.getMethod(), "proref", log.getUserId(), log.getAnketaId(), null);
    }
}
