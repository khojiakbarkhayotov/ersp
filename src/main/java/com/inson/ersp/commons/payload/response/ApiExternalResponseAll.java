package com.inson.ersp.commons.payload.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ApiExternalResponseAll<T> extends ApiResponseAll{
    private Integer error;
    private String error_message;
    private T result;
}
