package com.inson.ersp.products.opo.payload.request;

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
public class OpoContractRequest extends ApiRequest {

    @Schema(description = "UUID of the OSGOR contract.", example = "41153593-46be-4297-a89f-8ed5e02b4086")
    @NotNull(message = "UUID cannot be null.")
    @NotBlank(message = "UUID cannot be blank.")
    private String contractUuid;

    @Schema(description = "Contract Register Number of the OSGOR contract.", example = "AA0000057")
    @NotNull(message = "Contract Register Number cannot be null.")
    @NotBlank(message = "Contract Register Number cannot be blank.")
    private String contractRegisterNumber;

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
    private List<OpoPolicyData> policies;

    @Schema(description = "Info about insurant")
    @NotNull(message = "insurant can not be null")
    private InsurantRequest insurant;

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class OpoPolicyData {

        @Schema(description = "uuid of the policy", example = "ed3b4562-09bb-48fa-ad24-ff98e25481fc")
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

        @Schema(description = "Insurance Term id", example = "1")
        @NotNull(message = "insuranceTermId cannot be null.")
        private Integer insuranceTermId;

        @Schema(description = "Object details")
        @NotNull(message = "object cannot be null.")
        private ObjectDetailsOpo object;

        @Getter
        @Setter
        @NoArgsConstructor
        @ToString
        public static class ObjectDetailsOpo {

            @Schema(description = "uuid of the object", example = "f4a2451b-5202-40e5-b945-6819e71accb3")
            @NotNull(message = "uuid can not be null")
            @NotBlank(message = "uuid can not be blank")
            private String uuid;

            @Schema(description = "Type of the object", example = "9")
            @NotNull(message = "objectType cannot be null.")
            private Integer objectType;

            @Schema(description = "Name of the object", example = "Название объекта")
            @NotNull(message = "objectName cannot be null.")
            private String objectName;

            @Schema(description = "Address of the object", example = "Адрес объекта")
            @NotNull(message = "objectAddress cannot be null.")
            private String objectAddress;

            @Schema(description = "Opo Object Type", example = "1")
            @NotNull(message = "opoObjectType cannot be null.")
            private String opoObjectType;

            @Schema(description = "Document link", example = "Ссылка тут")
            @NotNull(message = "docLink cannot be null.")
            private String docLink;

            @Schema(description = "License", example = "20000")
            @NotNull(message = "license cannot be null.")
            private String license;
        }
    }
}
