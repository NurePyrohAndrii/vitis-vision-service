package com.vitisvision.vitisvisionservice.controller.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vitisvision.vitisvisionservice.common.util.AdvisorUtils;
import com.vitisvision.vitisvisionservice.common.util.MessageSourceUtils;
import com.vitisvision.vitisvisionservice.security.dto.AuthResponse;
import com.vitisvision.vitisvisionservice.security.dto.AuthenticationRequest;
import com.vitisvision.vitisvisionservice.security.dto.RegisterRequest;
import com.vitisvision.vitisvisionservice.security.advisor.JwtFilterExceptionHandler;
import com.vitisvision.vitisvisionservice.security.service.AuthService;
import com.vitisvision.vitisvisionservice.security.service.JwtService;
import com.vitisvision.vitisvisionservice.security.repository.TokenRepository;
import com.vitisvision.vitisvisionservice.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private AuthService authService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private JwtFilterExceptionHandler jwtExceptionHandler;

    @MockBean
    private TokenRepository tokenRepository;

    @MockBean
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    @InjectMocks
    private AuthController authController;

    @MockBean
    private MessageSourceUtils messageSourceUtils;

    @MockBean
    private AdvisorUtils advisorUtils;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testRegister() throws Exception {
        // Given
        RegisterRequest registerRequest = RegisterRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@gmail.com")
                .password("Password1$")
                .build();

        AuthResponse authResponse = AuthResponse.builder()
                .accessToken("access_token")
                .refreshToken("refresh_token")
                .build();

        // Mock
        when(authService.register(registerRequest)).thenReturn(authResponse);

        // When & Then
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.access_token").value("access_token"))
                .andExpect(jsonPath("$.data.refresh_token").value("refresh_token"));
    }

    @Test
    public void testAuthenticate() throws Exception {
        // Given
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("user@example.com", "Password1$");

        AuthResponse authResponse = AuthResponse.builder()
                .accessToken("access_token")
                .refreshToken("refresh_token")
                .build();

        // Mock
        when(authService.authenticate(authenticationRequest)).thenReturn(authResponse);

        // When & Then
        mockMvc.perform(post("/api/v1/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authenticationRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.access_token").value("access_token"))
                .andExpect(jsonPath("$.data.refresh_token").value("refresh_token"));
    }

    @Test
    public void testRefreshToken() throws Exception {
        // Given
        String refreshToken = "refresh_token";
        String newAccessToken = "new_access_token";

        AuthResponse authResponse = AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken)
                .build();

        // Mock
        when(authService.refreshToken("Bearer " + refreshToken)).thenReturn(authResponse);

        // When & Then
        mockMvc.perform(post("/api/v1/auth/refresh")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + refreshToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.access_token").value(newAccessToken))
                .andExpect(jsonPath("$.data.refresh_token").value(refreshToken));
    }
}