package com.inson.ersp.commons.payload.response;

import com.inson.ersp.commons.payload.enums.StatusMessage;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StatusResponse {
    private String message;
    private List<String> errors;
    private Integer errorCode;

    public StatusResponse(StatusMessage statusMessage, List<String> errors) {
        this.message = statusMessage.getMessage();
        this.errorCode = statusMessage.getErrorCode();
        this.errors = errors;
    }

}
