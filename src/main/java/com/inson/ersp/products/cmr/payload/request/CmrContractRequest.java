package com.inson.ersp.products.cmr.payload.request;

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
public class CmrContractRequest extends ApiRequest {

    @Schema(description = "UUID of the OSGOR contract.", example = "d88235bb-2564-4ef6-87a7-67cbf83a3b89")
    @NotNull(message = "UUID cannot be null.")
    @NotBlank(message = "UUID cannot be blank.")
    private String contractUuid;

    @Schema(description = "Contract Register Number of the OSGOR contract.", example = "AA0000056")
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
    private List<CmrPolicyData> policies;

    @Schema(description = "Info about insurant")
    @NotNull(message = "insurant can not be null")
    private InsurantRequest insurant;

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class CmrPolicyData {

        @Schema(description = "uuid of the policy", example = "c2b654c3-5b71-46d4-9045-8676b97ee68a")
        @NotNull(message = "uuid can not be null")
        @NotBlank(message = "uuid can not be blank")
        private String uuid;

        @Schema(description = "Policy start date.", example = "2024-06-21", pattern = "yyyy-MM-dd", requiredMode = Schema.RequiredMode.REQUIRED)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        @NotNull(message = "Policy startDate can not be null")
        @FutureOrPresent(message = "Policy startDate must be future or present")
        private LocalDate startDate;

        @Schema(description = "Policy end date.", example = "2025-06-12", pattern = "yyyy-MM-dd", requiredMode = Schema.RequiredMode.REQUIRED)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        @NotNull(message = "Policy endDate can not be null")
        @FutureOrPresent(message = "Policy endDate must be future or present")
        private LocalDate endDate;

        @Schema(description = "Insurance sum of the policy.", example = "1000")
        @NotNull(message = "insuranceSum cannot be null.")
        private Double insuranceSum;

        @Schema(description = "Insurance Term id", example = "1")
        @NotNull(message = "insuranceTermId cannot be null.")
        private Integer insuranceTermId;

        @Schema(description = "Object details")
        @NotNull(message = "object cannot be null.")
        private ObjectDetailsCmr object;

        @Getter
        @Setter
        @NoArgsConstructor
        @ToString
        public static class ObjectDetailsCmr {

            @Schema(description = "uuid of the object", example = "aecaf8db-015d-455c-9d0d-33323ae55acb")
            @NotNull(message = "uuid can not be null")
            @NotBlank(message = "uuid can not be blank")
            private String uuid;

            @Schema(description = "Type of the object", example = "8")
            @NotNull(message = "objectType cannot be null.")
            private Integer objectType;

            @Schema(description = "Lot ID", example = "123")
            @NotNull(message = "lotId cannot be null.")
            private String lotId;

            @Schema(description = "Document number", example = "sae12e")
            @NotNull(message = "dogNum cannot be null.")
            private String dogNum;

            @Schema(description = "Document date", example = "2024-01-01", pattern = "yyyy-MM-dd")
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
            @NotNull(message = "dogDate can not be null")
            private LocalDate dogDate;

            @Schema(description = "Name of the object", example = "Название объекта")
            @NotNull(message = "objectName cannot be null.")
            private String objectName;

            @Schema(description = "Address of the object", example = "Адрес объекта")
            @NotNull(message = "objectAddress cannot be null.")
            private String objectAddress;

            @Schema(description = "Price of the object", example = "1000000")
            @NotNull(message = "objectPrice cannot be null.")
            private Double objectPrice;

            @Schema(description = "Current year price of the object", example = "20000")
            @NotNull(message = "currentYearPrice cannot be null.")
            private Double currentYearPrice;

            @Schema(description = "Document link", example = "Ссылка тут")
            @NotNull(message = "docLink cannot be null.")
            private String docLink;
        }
    }

    public void validateRequest(){

    }
}
