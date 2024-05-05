package com.vitisvision.vitisvisionservice.controller.security;

import com.vitisvision.vitisvisionservice.common.response.ApiResponse;
import com.vitisvision.vitisvisionservice.security.dto.AuthResponse;
import com.vitisvision.vitisvisionservice.security.service.AuthService;
import com.vitisvision.vitisvisionservice.security.dto.AuthenticationRequest;
import com.vitisvision.vitisvisionservice.security.dto.RegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * AuthController class for handling authentication and registration requests from the client
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user authentication and registration")
public class AuthController {

    /**
     * The service class dependency for handling authentication and registration requests
     */
    private final AuthService service;

    /**
     * Register a new user with the provided details
     *
     * @param request the request object containing the user details
     * @return the response entity containing the response object
     */
    @Operation(
            summary = "Register a new user",
            description = "Register a new user with the provided details"
    )
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(
            @RequestBody @Valid RegisterRequest request
    ) {
        MDC.put("context", request.getEmail());
        return ResponseEntity.ok(ApiResponse.success(service.register(request), HttpStatus.OK.value()));
    }

    /**
     * Authenticate a user with the provided credentials
     *
     * @param request the request object containing the user credentials
     * @return the response entity containing the response object
     */
    @Operation(
            summary = "Authenticate a user",
            description = "Authenticate a user with the provided credentials"
    )
    @PostMapping("/authenticate")
    public ResponseEntity<ApiResponse<AuthResponse>> authenticate(
            @RequestBody @Valid AuthenticationRequest request
    ) {
        MDC.put("context", request.getEmail());
        return ResponseEntity.ok(ApiResponse.success(service.authenticate(request), HttpStatus.OK.value()));
    }

    /**
     * Refresh an access token using the refresh token
     *
     * @param authorization the authorization header containing the refresh token
     * @return the response entity containing the response object
     */
    @Operation(
            summary = "Refresh an access token",
            description = "Refresh an access token using the refresh token"
    )
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthResponse>> refresh(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization
    ) {
        return ResponseEntity.ok(ApiResponse.success(service.refreshToken(authorization), HttpStatus.OK.value()));
    }

}
