package com.vitisvision.vitisvisionservice.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * DateValidator class is a validator class for local date and ISO8601 date time format.
 */
public class DateValidator implements ConstraintValidator<ValidDate, String> {

    private DateTimeFormatter dateFormatter;

    @Override
    public void initialize(ValidDate constraintAnnotation) {
        String format = constraintAnnotation.format();
        dateFormatter = DateTimeFormatter.ofPattern(format);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }

        try {
            // Try to parse as LocalDate
            LocalDate.parse(value, dateFormatter);
            return true;
        } catch (DateTimeParseException e) {
            try {
                // Try to parse as LocalDateTime
                LocalDateTime.parse(value, dateFormatter);
                return true;
            } catch (DateTimeParseException ignored) {
                // If both fail, it's invalid
                return false;
            }
        }
    }
}
