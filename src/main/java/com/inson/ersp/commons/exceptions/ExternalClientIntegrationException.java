package com.inson.ersp.commons.exceptions;

import com.inson.ersp.commons.payload.response.ApiExternalResponseAll;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ExternalClientIntegrationException extends RuntimeException {

    private Integer statusCode;
    private ApiExternalResponseAll response;
    public ExternalClientIntegrationException(Integer statusCode, ApiExternalResponseAll response, String message) {
        super(message);
        this.response = response;
        this.statusCode = statusCode;
    }
}
