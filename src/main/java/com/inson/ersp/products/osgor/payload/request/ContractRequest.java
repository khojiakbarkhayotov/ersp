package com.inson.ersp.products.osgor.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.inson.ersp.commons.payload.request.ApiRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class ContractRequest extends ApiRequest {

    @Schema(description = "UUID of the OSGOR contract.", example = "2b38364b-fc31-42d5-9bc5-19efaafccf9e")
    @NotNull(message = "UUID cannot be null.")
    @NotBlank(message = "UUID cannot be blank.")
    private String contractUuid;

    @Schema(description = "Contract Register Number of the OSGOR contract.", example = "AA0000053")
    @NotNull(message = "Contract Register Number cannot be null.")
    @NotBlank(message = "Contract Register Number cannot be blank.")
    private String contractRegisterNumber;

    @Schema(description = "Contract sum of the OSGOR contract.", example = "100000")
    @NotNull(message = "contractSum Number cannot be null.")
    @NotBlank(message = "contractSum cannot be blank.")
    private Double contractSum;

    @Schema(description = "Contract start date.", example = "2021-01-01", pattern = "yyyy-MM-dd", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "startDate can not be null")
    @FutureOrPresent(message = "startDate must be future or present")
    private LocalDate contractStartDate;

    @Schema(description = "Contract end date.", example = "2022-01-01", pattern = "yyyy-MM-dd", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "endDate can not be null.")
    @FutureOrPresent(message = "endDate must be future or present.")
    private LocalDate contractEndDate;

    @Schema(description = "Region.", example = "10")
    @NotNull(message = "regionId cannot be null.")
    private Integer regionId;

    @Schema(description = "Area type.", example = "1")
    @NotNull(message = "areaTypeId cannot be null.")
    private Integer areaTypeId;

}
