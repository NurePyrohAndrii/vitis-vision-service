package com.vitisvision.vitisvisionservice.exception;

import com.vitisvision.vitisvisionservice.api.ApiError;
import com.vitisvision.vitisvisionservice.api.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultExceptionHandlerTest {

    private DefaultExceptionHandler defaultExceptionHandler;

    @BeforeEach
    public void setUp() {
        defaultExceptionHandler = new DefaultExceptionHandler();
    }

    @Test
    public void handleException_ShouldReturnInternalServerError() {
        // Given
        Exception exception = new Exception("Internal error occurred");

        // When
        ResponseEntity<ApiResponse<List<ApiError>>> response = defaultExceptionHandler.handleException(exception);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Internal error occurred", response.getBody().getErrors().get(0).getDetails());
    }

    @Test
    public void handleValidationException_ShouldReturnBadRequest() {
        // Given
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "objectName");
        bindingResult.addError(new FieldError("objectName", "field", "must not be null"));
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

        // When
        ResponseEntity<ApiResponse<List<ApiError>>> response = defaultExceptionHandler.handleValidationException(ex);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getErrors().stream()
                .anyMatch(error -> "must not be null".equals(error.getMessage())));
    }
}