package com.inson.ersp.commons.exceptions;

import com.inson.ersp.commons.payload.enums.StatusMessage;
import com.inson.ersp.commons.payload.response.ApiResponse;
import com.inson.ersp.commons.payload.response.StatusResponse;
import com.inson.ersp.commons.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import javax.naming.AuthenticationException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.round;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandle {
    private static final Logger logger = LogManager.getLogger(ExceptionHandle.class);
    private final EmailService emailService;

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse> handleForbiddenException(AuthenticationException ex, WebRequest request) {
        logExceptionDetails(request, ex);
        return new ResponseEntity<>(new ApiResponse(new StatusResponse(StatusMessage.AUTHENTICATION_FAILED, List.of(ex.getMessage()))), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ApiResponse> handleForbiddenException(ForbiddenException ex, WebRequest request) {
        logExceptionDetails(request, ex);
        return new ResponseEntity<>(new ApiResponse(new StatusResponse(StatusMessage.PERMISSION_DENIED, List.of(ex.getMessage()))), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ExternalServerIntegrationException.class)
    public ResponseEntity<ApiResponse> handleExternalIntegrationException(ExternalServerIntegrationException ex, WebRequest request) {
        logExceptionDetails(request, ex.getResponse(), ex);
        return new ResponseEntity<>(new ApiResponse(
                new StatusResponse(StatusMessage.PROVIDER_NOT_RESPONDING,
                        List.of((ex.getResponse().getError_message() == null) ? "Service currently not available, try again later" : ex.getResponse().getError_message()))),
                HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(ExternalClientIntegrationException.class)
    public ResponseEntity<ApiResponse> handleExternalIntegrationException(ExternalClientIntegrationException ex, WebRequest request) {
        logExceptionDetails(request, ex.getResponse(), ex);
//        emailService.sendMessage("External Client Integration Exception", ex.getMessage());
        return new ResponseEntity<>(new ApiResponse(new StatusResponse(StatusMessage.SERVER_ERROR, List.of((ex.getResponse().getError_message() == null) ? ex.getMessage() : ex.getResponse().getError_message()))),
                (ex.getStatusCode() == HttpStatus.NOT_FOUND.value()) ? HttpStatus.NOT_FOUND : HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        logExceptionDetails(request, ex);
        return new ResponseEntity<>(new ApiResponse(new StatusResponse(StatusMessage.INVALID_INPUT_DATA, List.of(ex.getMessage()))), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse> handleNotFoundException(NotFoundException ex, WebRequest request) {
        logExceptionDetails(request, ex);
        return new ResponseEntity<>(new ApiResponse(new StatusResponse(StatusMessage.RESOURCE_NOT_FOUND, List.of(ex.getMessage()))), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MailException.class)
    public ResponseEntity<ApiResponse> handleMailException(MailException ex, WebRequest request) {
        logExceptionDetails(request, ex);
//        emailService.sendMessage("Mail Exception", ex.getMessage());
        return new ResponseEntity<>(new ApiResponse(new StatusResponse(StatusMessage.SERVER_ERROR, List.of(ex.getMessage()))), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DatabaseIntegrationException.class)
    public ResponseEntity<ApiResponse> handleDatabaseIntegrationException(DatabaseIntegrationException ex, WebRequest request) {
        int statusCode;
        logExceptionDetails(request, ex);
        if (Math.abs(ex.getStatusCode()) < 20000)
            statusCode = ex.getStatusCode();
        else
            statusCode = Math.abs(ex.getStatusCode()) - 20000;

        return switch (round((float) statusCode / 100)) {
            case 4 ->
                    new ResponseEntity<>(new ApiResponse(new StatusResponse(StatusMessage.CLIENT_ERROR, List.of(ex.getMessage()))), HttpStatus.valueOf(statusCode));
            case 5 ->
                    new ResponseEntity<>(new ApiResponse(new StatusResponse(StatusMessage.PROVIDER_NOT_RESPONDING, List.of(ex.getMessage()))), HttpStatus.valueOf(statusCode));
            default ->
                    new ResponseEntity<>(new ApiResponse(new StatusResponse(StatusMessage.SERVER_ERROR, List.of("Server encountered an error, please, try again later."))), HttpStatus.INTERNAL_SERVER_ERROR);
        };
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        BindingResult result = ex.getBindingResult();
        List<String> errors = new ArrayList<>();
        result.getFieldErrors().forEach(fieldError -> errors.add(fieldError.getDefaultMessage()));
        logExceptionDetails(request, new Exception("MethodArgumentNotValidException: " + errors));
        return new ResponseEntity<>(new ApiResponse(new StatusResponse(StatusMessage.INVALID_INPUT_DATA, errors)), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ApiResponse> handleValidationExceptions(HandlerMethodValidationException ex, WebRequest request) {
        String errorMessage = "Validation error(s): " + ex.getMessage();
        logExceptionDetails(request, new Exception("ConstraintViolationException: " + errorMessage));
        return new ResponseEntity<>(new ApiResponse(new StatusResponse(StatusMessage.INVALID_INPUT_DATA, List.of(ex.getMessage()))), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    private void logExceptionDetails(WebRequest request, Exception ex) {
        logger.error("Transaction ID: {}", request.getAttribute("transactionId", WebRequest.SCOPE_REQUEST));
        logger.error("Exception occurred for URL: {}", request.getDescription(true));
        logger.error("Exception class: {}", ex.getClass().getName());
        logger.error("Exception message: {}", ex.getMessage());
    }

    private void logExceptionDetails(WebRequest request, String message) {
        logger.error("Transaction ID: {}", request.getAttribute("transactionId", WebRequest.SCOPE_REQUEST));
        logger.error("Exception occurred for URL: {}", request.getDescription(true));
        logger.error("Exception message: {}", message);
    }

    private void logExceptionDetails(WebRequest request, Object response, Exception ex) {
        logger.error("Transaction ID: {}", request.getAttribute("transactionId", WebRequest.SCOPE_REQUEST));
        logger.error("Exception occurred for URL: {}", request.getDescription(true));
        logger.error("Object (Response): {}", response);
        logger.error("Exception class: {}", ex.getClass().getName());
        logger.error("Exception message: {}", ex.getMessage());
    }

}
