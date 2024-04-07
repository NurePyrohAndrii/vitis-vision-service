package com.vitisvision.vitisvisionservice.user;

import com.vitisvision.vitisvisionservice.api.ApiError;
import com.vitisvision.vitisvisionservice.api.ApiResponse;
import com.vitisvision.vitisvisionservice.exception.ChangePasswordException;
import com.vitisvision.vitisvisionservice.util.AdvisorUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserAdvisorTest {

    private UserAdvisor userAdvisor;

    @Mock
    private AdvisorUtils advisorUtils;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userAdvisor = new UserAdvisor(advisorUtils);
    }

    @Test
    public void handleChangePasswordException_ReturnsBadRequestRequest() {
        // Given
        ChangePasswordException exception = new ChangePasswordException("Password change failed");
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiError apiError = new ApiError(
                status,
                "Password change failed",
                "ChangePasswordException occurred",
                LocalDateTime.now().toString()
        );

        when(advisorUtils.getAnnotationResponseStatusCode(exception.getClass())).thenReturn(HttpStatus.BAD_REQUEST);
        when(advisorUtils.getErrorMessageString(exception)).thenReturn("Password change failed");
        when(advisorUtils.getErrorDetailsString(exception)).thenReturn("ChangePasswordException occurred");
        when(advisorUtils.createErrorResponseEntity(any(List.class), eq(status)))
                .thenReturn(new ResponseEntity<>(ApiResponse.error(List.of(apiError), status.value()), status));

        // When
        ResponseEntity<ApiResponse<List<ApiError>>> response = userAdvisor.handleChangePasswordException(exception);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ApiResponse<List<ApiError>> apiResponse = response.getBody();
        assertNotNull(apiResponse);
        assertEquals("Password change failed", apiResponse.getErrors().get(0).getMessage());
        assertEquals("ChangePasswordException occurred", apiResponse.getErrors().get(0).getDetails());
    }
}
