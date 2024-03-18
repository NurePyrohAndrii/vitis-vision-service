package com.vitisvision.vitisvisionservice.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vitisvision.vitisvisionservice.api.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

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
        return ResponseEntity.ok(ApiResponse.success(service.authenticate(request), HttpStatus.OK.value()));
    }

    @Operation(
            summary = "Refresh an access token",
            description = "Refresh an access token using the refresh token"
    )
    @PostMapping("/refresh")
    public void refresh(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        AuthResponse authResponse = service.refreshToken(request);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getWriter(), ApiResponse.success(authResponse, HttpStatus.OK.value()));
        // can also use ResponseEntity, but this is just to show how to write to response manually using ObjectMapper
    }

}
