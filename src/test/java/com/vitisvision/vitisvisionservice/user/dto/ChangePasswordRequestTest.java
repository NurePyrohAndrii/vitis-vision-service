package com.vitisvision.vitisvisionservice.user.dto;

import com.vitisvision.vitisvisionservice.user.dto.ChangePasswordRequest;
import org.junit.jupiter.api.Test;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChangePasswordRequestTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void shouldValidatePasswordSuccessfully() {
        ChangePasswordRequest request = ChangePasswordRequest.builder()
                .currentPassword("Password1$")
                .newPassword("Password1$")
                .confirmPassword("Password1@")
                .build();

        Set<ConstraintViolation<ChangePasswordRequest>> violations = validator.validate(request);
        assertEquals(0, violations.size());
    }

    @Test
    public void shouldDetectInvalidPassword() {
        ChangePasswordRequest request = ChangePasswordRequest.builder()
                .currentPassword("short")
                .newPassword("short")
                .confirmPassword("short")
                .build();

        Set<ConstraintViolation<ChangePasswordRequest>> violations = validator.validate(request);
        assertEquals(6, violations.size());
    }
}