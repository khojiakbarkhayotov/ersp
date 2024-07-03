package com.inson.ersp.commons.exceptions;

import com.inson.ersp.commons.payload.response.ApiExternalResponseAll;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
@Getter
@Setter
public class ExternalServerIntegrationException extends RuntimeException {

    private Integer statusCode;
    private ApiExternalResponseAll response;

    public ExternalServerIntegrationException(Integer statusCode, ApiExternalResponseAll response, String message) {
        super(message);
        this.response = response;
        this.statusCode = statusCode;
    }
}
