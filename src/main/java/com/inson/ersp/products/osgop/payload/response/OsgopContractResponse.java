package com.inson.ersp.products.osgop.payload.response;

import com.inson.ersp.commons.payload.response.ApiResponseAll;
import com.inson.ersp.commons.payload.response.StatusResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OsgopContractResponse extends ApiResponseAll {
    @Schema(description = "OSGOP contract data.")
    private OsgopCreateContractData data;

    public OsgopContractResponse(OsgopCreateContractData data, StatusResponse status, Integer statusCode) {
        super(status, statusCode);
        this.data = data;
    }

    public OsgopContractResponse(StatusResponse status, Integer statusCode) {
        super(status, statusCode);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class OsgopCreateContractData {
        @Schema(description = "Contract ID.")
        private Long contractId;
    }

}