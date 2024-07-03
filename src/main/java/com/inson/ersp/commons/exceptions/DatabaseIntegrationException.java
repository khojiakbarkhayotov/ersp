package com.inson.ersp.commons.exceptions;

public class DatabaseIntegrationException extends RuntimeException {
    private final Integer statusCode;
    public DatabaseIntegrationException(Integer statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

}
