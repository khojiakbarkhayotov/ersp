package com.inson.ersp.commons.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class DateUtil {
    public static String toDate(String date) {
        if (date == null || date.isBlank())
            return null;
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate localDate = LocalDate.parse(date, inputFormatter);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return localDate.format(outputFormatter);
    }

    public static String dateToString(LocalDate date, String pattern) {
        if (date == null)
            return null;
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern(pattern);
        return date.format(formatters);
    }

    public static String dateToString(LocalDate date) {
        if (date == null)
            return null;
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatters);
    }

    public static String toDate(String date, String pattern) {
        if (date == null || date.isBlank())
            return null;
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime localDateTime = LocalDateTime.parse(date, inputFormatter);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println(localDateTime.format(outputFormatter));
        return localDateTime.format(outputFormatter);
    }

    public static LocalDate stringToDate(String date, String pattern) {
        if( date != null && !date.isBlank() && !date.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            return LocalDate.parse(date, formatter);
        }
        log.error("String is null and can not be converted to Local");
        return null;
    }

    public static int calculateAge(LocalDate birthDate) {
        if (birthDate == null) {
            throw new IllegalArgumentException("Birth date cannot be null");
        }

        LocalDate currentDate = LocalDate.now();
        if (birthDate.isAfter(currentDate)) {
            throw new IllegalArgumentException("Birth date cannot be in the future");
        }

        return Period.between(birthDate, currentDate).getYears();
    }

}
