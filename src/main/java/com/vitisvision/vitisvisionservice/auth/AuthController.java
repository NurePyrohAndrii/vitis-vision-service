package com.vitisvision.vitisvisionservice.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vitisvision.vitisvisionservice.exception.ApiResponse;
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
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(
            @RequestBody @Valid RegisterRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.success(service.register(request), HttpStatus.OK.value()));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<ApiResponse<AuthResponse>> authenticate(
            @RequestBody @Valid AuthenticationRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.success(service.authenticate(request), HttpStatus.OK.value()));
    }

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
