package com.vitisvision.vitisvisionservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vitisvision.vitisvisionservice.exception.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutController implements LogoutHandler {

    private final LogoutService logoutService;
    private final LogoutExceptionHandler logoutExceptionHandler;

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
