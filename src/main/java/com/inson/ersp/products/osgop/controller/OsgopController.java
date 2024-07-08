package com.inson.ersp.products.osgop.controller;

import com.inson.ersp.commons.payload.response.ApiResponseAll;
import com.inson.ersp.products.osgop.payload.request.OsgopContractRequest;
import com.inson.ersp.products.osgop.service.OsgopService;
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
@RequestMapping("/api/v1/osgop")
@RequiredArgsConstructor
@Tag(name = "OSGOP", description = "APIs for OSGOP product")
public class OsgopController {
    private final OsgopService osgorService;

    @Operation(summary = "OSGOP contract.", description = "API to create OSGOP contract.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request processed",
                    content = {@Content(mediaType = "application/json", schema=@Schema(implementation = ApiResponseAll.class))})
    })
    @PostMapping("/contract")
    public HttpEntity<?> create(@RequestBody @Validated OsgopContractRequest dto){
        return new ResponseEntity<>(osgorService.createContract(dto), HttpStatus.OK);
    }
}
