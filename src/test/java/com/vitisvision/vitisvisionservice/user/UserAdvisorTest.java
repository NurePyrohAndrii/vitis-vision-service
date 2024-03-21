package com.vitisvision.vitisvisionservice.user;

import com.vitisvision.vitisvisionservice.api.ApiError;
import com.vitisvision.vitisvisionservice.api.ApiResponse;
import com.vitisvision.vitisvisionservice.exception.ChangePasswordException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserAdvisorTest {

    private UserAdvisor userAdvisor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userAdvisor = new UserAdvisor();
    }

    @Test
    public void handleChangePasswordException_ReturnsBadRequestRequest() {
        // Given
        ChangePasswordException exception = new ChangePasswordException("Password change failed");

        // When
        ResponseEntity<ApiResponse<List<ApiError>>> response = userAdvisor.handleChangePasswordException(exception);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ApiResponse<List<ApiError>> apiResponse = response.getBody();
        assertNotNull(apiResponse);
        assertEquals("Password change failed", apiResponse.getErrors().get(0).getMessage());
    }
}