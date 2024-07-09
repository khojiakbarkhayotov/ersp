package com.inson.ersp.products.cmr.controller;

import com.inson.ersp.commons.payload.response.ApiResponseAll;
import com.inson.ersp.products.cmr.payload.request.CmrContractRequest;
import com.inson.ersp.products.cmr.service.CmrService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cmr")
@RequiredArgsConstructor
@Tag(name = "СМР", description = "APIs for СМР product")
public class CmrController {
    private final CmrService cmrService;

    @Operation(summary = "СМР contract.", description = "API to create СМР contract.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request processed",
                    content = {@Content(mediaType = "application/json", schema=@Schema(implementation = ApiResponseAll.class))})
    })
    @PostMapping("/contract")
    public HttpEntity<?> create(@RequestBody @Validated CmrContractRequest dto){
        return new ResponseEntity<>(cmrService.createContract(dto), HttpStatus.OK);
    }
}
