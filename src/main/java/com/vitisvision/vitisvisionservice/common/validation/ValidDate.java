package com.vitisvision.vitisvisionservice.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * The annotation to validate a date.
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateValidator.class)
@Documented
public @interface ValidDate {
    String message() default "Invalid date format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String format();
}