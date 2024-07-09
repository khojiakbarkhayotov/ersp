package com.inson.ersp.products.osgop.controller;

import com.inson.ersp.commons.payload.response.ApiResponseAll;
import com.inson.ersp.products.opo.payload.request.OpoContractRequest;
import com.inson.ersp.products.opo.service.OpoService;
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
@RequestMapping("/api/v1/opo")
@RequiredArgsConstructor
@Tag(name = "ОПО", description = "APIs for ОПО product")
public class OpoController {
    private final OpoService opoService;

    @Operation(summary = "ОПО contract.", description = "API to create ОПО contract.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request processed",
                    content = {@Content(mediaType = "application/json", schema=@Schema(implementation = ApiResponseAll.class))})
    })
    @PostMapping("/contract")
    public HttpEntity<?> create(@RequestBody @Validated OpoContractRequest dto){
        return new ResponseEntity<>(opoService.createContract(dto), HttpStatus.OK);
    }
}
