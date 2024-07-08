package com.inson.ersp.products.osgor.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.inson.ersp.commons.payload.request.ApiRequest;
import com.inson.ersp.commons.payload.request.InsurantRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class OsgorContractRequest extends ApiRequest {

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
    private Double contractSum;

    @Schema(description = "Contract start date.", example = "2021-01-01", pattern = "yyyy-MM-dd", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "Contract startDate can not be null")
    @FutureOrPresent(message = "Contract startDate must be future or present")
    private LocalDate contractStartDate;

    @Schema(description = "Contract end date.", example = "2022-01-01", pattern = "yyyy-MM-dd", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "Contract endDate can not be null.")
    @FutureOrPresent(message = "Contract endDate must be future or present.")
    private LocalDate contractEndDate;

    @Schema(description = "Region.", example = "10")
    @NotNull(message = "regionId cannot be null.")
    private Integer regionId;

    @Schema(description = "Area type.", example = "1")
    @NotNull(message = "areaTypeId cannot be null.")
    private Integer areaTypeId;

    @Schema(description = "List of policies.")
    @NotNull(message = "Policies can not be null")
    private List<Policy> policies;

    @Schema(description = "Info about insurant")
    @NotNull(message = "insurant can not be null")
    private InsurantRequest insurant;

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class Policy{
        @Schema(description = "uuid of the policy", example = "5d10da97-2450-497b-b039-8c225b0eb7f8")
        @NotNull(message = "uuid can not be null")
        @NotBlank(message = "uuid can not be blank")
        private String uuid;

        @Schema(description = "Policy start date.", example = "2021-01-01", pattern = "yyyy-MM-dd", requiredMode = Schema.RequiredMode.REQUIRED)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        @NotNull(message = "Policy startDate can not be null")
        @FutureOrPresent(message = "Policy startDate must be future or present")
        private LocalDate startDate;

        @Schema(description = "Policy end date.", example = "2021-01-01", pattern = "yyyy-MM-dd", requiredMode = Schema.RequiredMode.REQUIRED)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        @NotNull(message = "Policy end date can not be null")
        @FutureOrPresent(message = "Policy end date must be future or present")
        private LocalDate endDate;

        @Schema(description = "Insurance sum of the policy.", example = "100000")
        @NotNull(message = "insuranceSum cannot be null.")
        private Double insuranceSum;

        @Schema(description = "Insurance rate", example = "100")
        @NotNull(message = "insuranceRate cannot be null.")
        private Double insuranceRate;

        @Schema(description = "Insurance premium", example = "10000")
        @NotNull(message = "insurancePremium cannot be null.")
        private Double insurancePremium;

        @Schema(description = "Insurance Term id", example = "1")
        @NotNull(message = "insuranceTermId cannot be null.")
        private Integer insuranceTermId;

        @Schema(description = "fot", example = "20000")
        @NotNull(message = "fot cannot be null.")
        private Double fot;

        @Schema(description = "fot", example = "10000")
        @NotNull(message = "funeralExpensesSum cannot be null.")
        private Double funeralExpensesSum;
    }

    public void validateRequest(){

    }
}
