package com.vitisvision.vitisvisionservice.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vitisvision.vitisvisionservice.auth.AuthController;
import com.vitisvision.vitisvisionservice.jwt.JwtExceptionHandler;
import com.vitisvision.vitisvisionservice.jwt.JwtService;
import com.vitisvision.vitisvisionservice.token.TokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private JwtExceptionHandler jwtExceptionHandler;

    @MockBean
    private TokenRepository tokenRepository;

    @InjectMocks
    private AuthController authController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void changePassword_ValidRequest_ReturnsOk() throws Exception {
        // Given
        ChangePasswordRequest request = ChangePasswordRequest.builder()
                .currentPassword("Password1$")
                .newPassword("Password1$2")
                .confirmPassword("Password1$2")
                .build();

        // Mock
        doNothing().when(userService).changePassword(any(ChangePasswordRequest.class), any(Principal.class));

        // When & Then
        mockMvc.perform(patch("/api/v1/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("Password changed successfully"));
    }

}