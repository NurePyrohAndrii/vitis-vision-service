package com.vitisvision.vitisvisionservice.security.dto;

import com.vitisvision.vitisvisionservice.security.dto.RegisterRequest;
import org.junit.jupiter.api.Test;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegisterRequestTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private RegisterRequest createRegisterRequest(String firstName, String lastName, String email, String password) {
        return RegisterRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)
                .build();
    }

    @Test
    public void testValidRegistrationData() {
        // Given
        RegisterRequest request = createRegisterRequest("John", "Doe", "john.doe@example.com", "Password1$");

        // When
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);

        // Then
        assertEquals(0, violations.size());
    }

    @Test
    public void testCompletelyInvalidRegistrationData() {
        // Given
        RegisterRequest request = createRegisterRequest("J", "D", "johnxample.com", "invalid");

        // When
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);

        // Then
        assertEquals(5, violations.size());
    }

}