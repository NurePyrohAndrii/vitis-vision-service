package com.vitisvision.vitisvisionservice.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vitisvision.vitisvisionservice.common.util.MessageSourceUtils;
import com.vitisvision.vitisvisionservice.security.advisor.JwtFilterExceptionHandler;
import com.vitisvision.vitisvisionservice.security.service.JwtService;
import com.vitisvision.vitisvisionservice.security.repository.TokenRepository;
import com.vitisvision.vitisvisionservice.user.dto.ChangePasswordRequest;
import com.vitisvision.vitisvisionservice.user.repository.UserRepository;
import com.vitisvision.vitisvisionservice.user.service.UserService;
import com.vitisvision.vitisvisionservice.common.util.AdvisorUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
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
    private UserRepository userRepository;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private JwtFilterExceptionHandler jwtExceptionHandler;

    @MockBean
    private TokenRepository tokenRepository;

    @MockBean
    private MessageSourceUtils messageSourceUtils;

    @MockBean
    private AdvisorUtils advisorUtils;

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
        when(messageSourceUtils.getLocalizedMessage("password.change.success")).thenReturn("Password changed successfully");

        // When & Then
        mockMvc.perform(patch("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("Password changed successfully"));
    }

}