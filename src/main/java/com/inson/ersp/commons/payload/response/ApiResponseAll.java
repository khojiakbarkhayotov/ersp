package com.inson.ersp.commons.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseAll extends ApiResponse {

    @JsonIgnore
    private Long anketaId;

    @JsonIgnore
    private Integer integrationStatusCode;

    public ApiResponseAll(StatusResponse status, Integer integrationStatusCode) {
        super(status);
        this.integrationStatusCode = integrationStatusCode;
    }

    public ApiResponseAll(StatusResponse status, Integer integrationStatusCode, Long anketaId) {
        super(status);
        this.integrationStatusCode = integrationStatusCode;
        this.anketaId = anketaId;
    }

    public ApiResponseAll(StatusResponse status) {
        super(status);
    }

    public ApiResponseAll(Long anketaId) {
        this.anketaId = anketaId;
    }

    public ApiResponseAll(Integer integrationStatusCode) {
        this.integrationStatusCode = integrationStatusCode;
    }

}
