package com.inson.ersp.products.osgor.controller;

import com.inson.ersp.commons.payload.response.ApiResponseAll;
import com.inson.ersp.products.osgor.payload.request.OsgorContractRequest;
import com.inson.ersp.products.osgor.service.OsgorService;
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
@RequestMapping("/api/v1/osgor")
@RequiredArgsConstructor
@Tag(name = "OSGOR", description = "APIs for osgor product")
public class OsgorController {
    private final OsgorService osgorService;

    @Operation(summary = "OSGOR contract.", description = "API to create OSGOR contract.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request processed",
                    content = {@Content(mediaType = "application/json", schema=@Schema(implementation = ApiResponseAll.class))})
    })
    @PostMapping("/contract")
    public HttpEntity<?> create(@RequestBody @Validated OsgorContractRequest dto){
        return new ResponseEntity<>(osgorService.createContract(dto), HttpStatus.OK);
    }
}
