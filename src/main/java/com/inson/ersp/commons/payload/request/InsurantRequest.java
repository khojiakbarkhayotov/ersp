package com.inson.ersp.commons.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class InsurantRequest {

    @Schema(description = "Insurant is individual")
    private Person person;

    @Schema(description = "Insurant is entity")
    private Organization organization;

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class Person {
        @NotNull(message = "Passport data cannot be null.")
        private PassportData passportData;

        @NotNull(message = "Full name cannot be null.")
        private FullName fullName;

        @Schema(description = "Birth date of the person.", example = "1989-05-11")
        @NotNull(message = "Birth date cannot be null.")
        @NotBlank(message = "Birth date cannot be blank.")
        private String birthDate;

        @Schema(description = "Address of the person.", example = "ул. Такая-то, дом такой-то....")
        @NotNull(message = "Address cannot be null.")
        @NotBlank(message = "Address cannot be blank.")
        private String address;

        @Schema(description = "Phone number of the person.", example = "998903209003")
        @NotNull(message = "Phone number cannot be null.")
        @NotBlank(message = "Phone number cannot be blank.")
        private String phone;

        @Schema(description = "Resident type of the person.", example = "1")
        @NotNull(message = "Resident type cannot be null.")
        @NotBlank(message = "Resident type cannot be blank.")
        private String residentType;

        @Schema(description = "Gender of the person.", example = "m")
        @NotNull(message = "Gender cannot be null.")
        @NotBlank(message = "Gender cannot be blank.")
        private String gender;

        @Schema(description = "Region ID of the person.", example = "10")
        @NotNull(message = "Region ID cannot be null.")
        @NotBlank(message = "Region ID cannot be blank.")
        private String regionId;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class PassportData {
        @Schema(description = "Pinfl of the person.", example = "12345678901234")
        @NotNull(message = "Pinfl cannot be null.")
        @NotBlank(message = "Pinfl cannot be blank.")
        private String pinfl;

        @Schema(description = "Passport series of the person.", example = "AA")
        @NotNull(message = "Passport seria cannot be null.")
        @NotBlank(message = "Passport seria cannot be blank.")
        private String seria;

        @Schema(description = "Passport number of the person.", example = "1234567")
        @NotNull(message = "Passport number cannot be null.")
        @NotBlank(message = "Passport number cannot be blank.")
        private String number;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class FullName {
        @Schema(description = "First name of the person.", example = "Иван")
        @NotNull(message = "First name cannot be null.")
        @NotBlank(message = "First name cannot be blank.")
        private String firstname;

        @Schema(description = "Last name of the person.", example = "Иванов")
        @NotNull(message = "Last name cannot be null.")
        @NotBlank(message = "Last name cannot be blank.")
        private String lastname;

        @Schema(description = "Middle name of the person.", example = "Иванович")
        @NotNull(message = "Middle name cannot be null.")
        @NotBlank(message = "Middle name cannot be blank.")
        private String middlename;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class Organization {
        @Schema(description = "INN of the organization.", example = "123456789")
        @NotNull(message = "INN cannot be null.")
        @NotBlank(message = "INN cannot be blank.")
        private String inn;

        @Schema(description = "Name of the organization.", example = "ООО Рога и копыта")
        @NotNull(message = "Name cannot be null.")
        @NotBlank(message = "Name cannot be blank.")
        private String name;

        @Schema(description = "Representative name of the organization.", example = "Ольгов Ольг Ольгович")
        @NotNull(message = "Representative name cannot be null.")
        @NotBlank(message = "Representative name cannot be blank.")
        private String representativeName;

        @Schema(description = "Address of the organization.", example = "ул. Такая-то, дом такой-то....")
        @NotNull(message = "Address cannot be null.")
        @NotBlank(message = "Address cannot be blank.")
        private String address;

        @Schema(description = "OKED of the organization.", example = "62090")
        @NotNull(message = "OKED cannot be null.")
        @NotBlank(message = "OKED cannot be blank.")
        private String oked;

        @Schema(description = "Position of the representative.", example = "Директор")
        @NotNull(message = "Position cannot be null.")
        @NotBlank(message = "Position cannot be blank.")
        private String position;

        @Schema(description = "Checking account of the organization.", example = "123456789123456789")
        @NotNull(message = "Checking account cannot be null.")
        @NotBlank(message = "Checking account cannot be blank.")
        private String checkingAccount;

        @Schema(description = "Phone number of the organization.", example = "998901111111")
        @NotNull(message = "Phone number cannot be null.")
        @NotBlank(message = "Phone number cannot be blank.")
        private String phone;

        @Schema(description = "Region ID of the organization.", example = "10")
        @NotNull(message = "Region ID cannot be null.")
        private int regionId;
    }
}
