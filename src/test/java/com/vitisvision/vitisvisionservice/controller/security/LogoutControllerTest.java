package com.vitisvision.vitisvisionservice.controller.security;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.vitisvision.vitisvisionservice.controller.security.LogoutController;
import com.vitisvision.vitisvisionservice.security.advisor.LogoutExceptionHandler;
import com.vitisvision.vitisvisionservice.security.service.LogoutService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;

import java.io.PrintWriter;

class LogoutControllerTest {

    @Mock
    private LogoutService logoutService;

    @Mock
    private LogoutExceptionHandler logoutExceptionHandler;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private LogoutController logoutController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogoutSuccess() throws Exception {
        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        logoutController.logout(request, response, authentication);

        verify(logoutService, times(1)).logout(request);
        verify(response, times(1)).setStatus(HttpStatus.OK.value());
        verify(response, times(1)).setContentType(APPLICATION_JSON_VALUE);
    }

    @Test
    void testLogoutFailureHandled() throws Exception {
        doThrow(new RuntimeException("Test Exception")).when(logoutService).logout(request);

        logoutController.logout(request, response, authentication);

        verify(logoutExceptionHandler, times(1)).handleLogoutException(eq(response), any(RuntimeException.class));
    }
}