package com.vitisvision.vitisvisionservice.auth;

import com.vitisvision.vitisvisionservice.api.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.ThreadContext;
import org.jboss.logging.NDC;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user authentication and registration")
public class AuthController {

    private final AuthService service;

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
