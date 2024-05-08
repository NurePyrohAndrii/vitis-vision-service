package com.vitisvision.vitisvisionservice.controller.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vitisvision.vitisvisionservice.common.response.ApiResponse;
import com.vitisvision.vitisvisionservice.security.advisor.LogoutExceptionHandler;
import com.vitisvision.vitisvisionservice.security.service.LogoutService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

/**
 * LogoutController class is responsible for handling logout requests.
 */
@Service
@RequiredArgsConstructor
public class LogoutController implements LogoutHandler {

    /**
     * The logout service to perform logout operations.
     */
    private final LogoutService logoutService;

    /**
     * The logout exception handler to handle logout exceptions.
     */
    private final LogoutExceptionHandler logoutExceptionHandler;

    /**
     * Logs out the user.
     *
     * @param request        the HTTP request
     * @param response       the HTTP response
     * @param authentication the authentication object
     */
    @SneakyThrows
    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        try {
            logoutService.logout(request);

            ApiResponse<?> apiResponse = ApiResponse.success(
                    null,
                    HttpStatus.OK.value()
            );
            response.setStatus(HttpStatus.OK.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getWriter(), apiResponse);
        } catch (Exception e) {
            logoutExceptionHandler.handleLogoutException(response, e);
        }
    }
}
