package com.vitisvision.vitisvisionservice.security;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation for password validation using a combination of regex and size constraints.
 * {@link NotBlank} - checks if the password is not blank
 * {@link Size} - checks if the password is between 10 and 16 characters
 * {@link Pattern} - checks if the password contains at least one uppercase letter, 8 lowercase letters, one digit and one special character
 */
@NotBlank(message = "Password is required")
@Size(min = 10, max = 16, message = "Password must be at least 10 characters, but no more than 16")
@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Password must contain at least one uppercase letter, 8 lowercase letters, one digit and one special character")
@Constraint(validatedBy = {})
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {

    /**
     * Message to be displayed when validation fails
     * @return message to be displayed when validation fails
     */
    String message() default "Invalid password";

    /**
     * Groups to which the annotation belongs
     * @return groups to which the annotation belongs
     */
    Class<?>[] groups() default {};

    /**
     * Payload to be used for validation
     * @return payload to be used for validation
     */
    Class<? extends Payload>[] payload() default {};
}