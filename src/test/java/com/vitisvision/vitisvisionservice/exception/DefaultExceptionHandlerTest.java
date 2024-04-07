package com.vitisvision.vitisvisionservice.exception;

import com.vitisvision.vitisvisionservice.api.ApiError;
import com.vitisvision.vitisvisionservice.api.ApiResponse;
import com.vitisvision.vitisvisionservice.util.AdvisorUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class DefaultExceptionHandlerTest {

    private DefaultExceptionHandler defaultExceptionHandler;

    @Mock
    private AdvisorUtils advisorUtils;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        defaultExceptionHandler = new DefaultExceptionHandler(advisorUtils);
    }

    @Test
    public void handleException_ShouldReturnInternalServerError() {
        // Given
        Exception exception = new Exception("Internal error occurred");
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Translated message", "Error details", LocalDateTime.now().toString());
        when(advisorUtils.createErrorResponseEntity(any(), any()))
                .thenReturn(new ResponseEntity<>(ApiResponse.error(List.of(apiError), HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR));

        // When
        ResponseEntity<ApiResponse<List<ApiError>>> response = defaultExceptionHandler.handleException(exception);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Translated message", response.getBody().getErrors().get(0).getMessage());
    }

    @Test
    public void handleValidationException_ShouldReturnBadRequest() {
        // Given
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "objectName");
        bindingResult.addError(new FieldError("objectName", "field", "must not be null"));
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Translated message", "Validation error", LocalDateTime.now().toString());
        when(advisorUtils.createErrorResponseEntity(any(), any()))
                .thenReturn(new ResponseEntity<>(ApiResponse.error(List.of(apiError), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST));

        // When
        ResponseEntity<ApiResponse<List<ApiError>>> response = defaultExceptionHandler.handleValidationException(ex);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Translated message", response.getBody().getErrors().get(0).getMessage());
        assertEquals("Validation error", response.getBody().getErrors().get(0).getDetails());
    }
}