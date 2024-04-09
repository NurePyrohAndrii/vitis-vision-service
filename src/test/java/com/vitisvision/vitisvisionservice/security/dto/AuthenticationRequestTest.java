package com.vitisvision.vitisvisionservice.security.dto;

import com.vitisvision.vitisvisionservice.security.dto.AuthenticationRequest;
import org.junit.jupiter.api.Test;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationRequestTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private AuthenticationRequest createAuthenticationRequest(String email, String password) {
        return AuthenticationRequest.builder()
                .email(email)
                .password(password)
                .build();
    }

    @Test
    void testValidEmailAndPassword() {
        // Given
        AuthenticationRequest request = createAuthenticationRequest("valid.email@example.com", "Password1$");

        // When
        Set<ConstraintViolation<AuthenticationRequest>> violations = validator.validate(request);

        // Then
        assertEquals(0, violations.size());
    }

    @Test
    void testInvalidEmailEmptyString() {
        // Given
        AuthenticationRequest request = createAuthenticationRequest("", "invalid");

        // When
        Set<ConstraintViolation<AuthenticationRequest>> violations = validator.validate(request);

        // Then
        assertEquals(3, violations.size());
    }
}