package com.vitisvision.vitisvisionservice.common;

import com.vitisvision.vitisvisionservice.common.advisor.GlobalAdvisor;
import com.vitisvision.vitisvisionservice.common.exception.ResourceNotFoundException;
import com.vitisvision.vitisvisionservice.common.response.ApiError;
import com.vitisvision.vitisvisionservice.common.response.ApiResponse;
import com.vitisvision.vitisvisionservice.common.util.AdvisorUtils;
import com.vitisvision.vitisvisionservice.common.util.MessageSourceUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class GlobalAdvisorTest {

    private GlobalAdvisor globalAdvisor;

    @Mock
    private AdvisorUtils advisorUtils;

    @Mock
    private MessageSourceUtils messageSourceUtils;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        globalAdvisor = new GlobalAdvisor(advisorUtils, messageSourceUtils);
        setUpMocks();
    }

    private void setUpMocks() {
        when(advisorUtils.createErrorResponseEntity(any(Exception.class), any(HttpStatus.class)))
                .thenAnswer(invocation -> {
                    HttpStatus status = invocation.getArgument(1);
                    ApiError apiError = new ApiError(status, "Default Error Message", "Default Error Details", null);
                    ApiResponse<List<ApiError>> apiResponse = ApiResponse.error(List.of(apiError), status.value());
                    return new ResponseEntity<>(apiResponse, status);
                });
    }

    @Test
    public void handleException_ShouldReturnInternalServerError() {
        // Given
        Exception exception = new Exception("Internal error occurred");
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", "An unexpected error occurred", null);
        ResponseEntity<ApiResponse<List<ApiError>>> expectedResponse = new ResponseEntity<>(ApiResponse.error(List.of(apiError), HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
        when(advisorUtils.createErrorResponseEntity(exception, HttpStatus.INTERNAL_SERVER_ERROR)).thenReturn(expectedResponse);

        // When
        ResponseEntity<ApiResponse<List<ApiError>>> response = globalAdvisor.handleException(exception);

        // Then
        assertNotNull(response, "The response should not be null");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Internal Server Error", Objects.requireNonNull(response.getBody()).getErrors().get(0).getMessage());
    }

    @Test
    public void handleValidationException_ShouldReturnBadRequest() {
        when(advisorUtils.createErrorResponseEntity(anyList(), eq(HttpStatus.BAD_REQUEST)))
                .thenReturn(new ResponseEntity<>(ApiResponse.error(List.of(new ApiError(HttpStatus.BAD_REQUEST, "Validation Error", "Field must not be null", null)), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST));

        // Given
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "objectName");
        bindingResult.addError(new FieldError("objectName", "field", "must not be null"));
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

        // When
        ResponseEntity<ApiResponse<List<ApiError>>> response = globalAdvisor.handleValidationException(ex);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void handleAccessDeniedException_ShouldReturnForbiddenStatus() {
        // Given
        AccessDeniedException e = new AccessDeniedException("Access is denied");

        when(messageSourceUtils.getLocalizedMessage("error.access.denied")).thenReturn("Access is denied");
        when(advisorUtils.getErrorDetailsString(e)).thenReturn("You do not have permission to access this resource");

        when(advisorUtils.createErrorResponseEntity(any(List.class), any(HttpStatus.class))).thenAnswer(invocation -> {
            List<ApiError> errors = invocation.getArgument(0);
            HttpStatus status = invocation.getArgument(1);
            ApiResponse<List<ApiError>> apiResponse = ApiResponse.error(errors, status.value());
            return new ResponseEntity<>(apiResponse, status);
        });

        // When
        ResponseEntity<ApiResponse<List<ApiError>>> response = globalAdvisor.handleAccessDeniedException(e);

        // Then
        assertNotNull(response, "The response should not be null");
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Access is denied", Objects.requireNonNull(response.getBody()).getErrors().get(0).getMessage());
    }


    @Test
    public void handleResourceNotFoundException_ShouldReturnNotFoundStatus() {
        // Given
        ResourceNotFoundException e = new ResourceNotFoundException("Resource not found");
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, "Resource not found", "The requested resource was not found", null);
        ResponseEntity<ApiResponse<List<ApiError>>> expectedResponse = new ResponseEntity<>(ApiResponse.error(List.of(apiError), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        when(advisorUtils.createErrorResponseEntity(e, HttpStatus.NOT_FOUND)).thenReturn(expectedResponse);

        // When
        ResponseEntity<ApiResponse<List<ApiError>>> response = globalAdvisor.handleResourceNotFoundException(e);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Resource not found", Objects.requireNonNull(response.getBody()).getErrors().get(0).getMessage());
    }
}
