package com.vitisvision.vitisvisionservice.auth;

import com.vitisvision.vitisvisionservice.exception.DuplicateResourceException;
import com.vitisvision.vitisvisionservice.jwt.JwtService;
import com.vitisvision.vitisvisionservice.token.Token;
import com.vitisvision.vitisvisionservice.token.TokenRepository;
import com.vitisvision.vitisvisionservice.user.Role;
import com.vitisvision.vitisvisionservice.user.User;
import com.vitisvision.vitisvisionservice.user.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    // service to test
    @InjectMocks
    private AuthService authService;

    // declare dependencies
    @Mock
    private UserRepository userRepository;
    @Mock
    private TokenRepository tokenRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;

    // setup
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegister_NewUser_ReturnsAuthResponse() {
        // Given
        RegisterRequest request = new RegisterRequest("John", "Doe", "john.doe@example.com", "password");
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password("encodedPassword") // Assume password gets encoded to this
                .role(Role.USER)
                .build();

        // Mock
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn(user.getPassword());
        when(jwtService.generateAccessToken(anyMap(), any(User.class))).thenReturn("accessToken");
        when(jwtService.generateRefreshToken(any(User.class))).thenReturn("refreshToken");

        // When
        AuthResponse response = authService.register(request);

        // Then
        assertNotNull(response);
        assertEquals("accessToken", response.getAccessToken());
        assertEquals("refreshToken", response.getRefreshToken());

        verify(userRepository).save(any(User.class));
        verify(tokenRepository, times(2)).save(any(Token.class)); // Assuming saveUserToken saves tokens
    }

    @Test
    public void testRegister_ExistingEmail_ThrowsDuplicateResourceException() {
        // Given
        RegisterRequest request = new RegisterRequest("John", "Doe", "john.doe@example.com", "password");

        // Mock
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        // When & Then
        assertThrows(DuplicateResourceException.class, () -> authService.register(request));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testAuthenticate_ValidCredentials_ReturnsAuthResponse() {
        // Given
        AuthenticationRequest request = new AuthenticationRequest("john.doe@example.com", "password");
        User user = new User();
        Authentication auth = mock(Authentication.class);
        List<Token> existingTokens = Arrays.asList(new Token(), new Token()); // Mock existing tokens

        // Mock
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
        when(auth.getPrincipal()).thenReturn(user);
        when(jwtService.generateAccessToken(anyMap(), eq(user))).thenReturn("accessToken");
        when(jwtService.generateRefreshToken(user)).thenReturn("refreshToken");
        when(tokenRepository.findAllValidTokensByUserId(user.getId())).thenReturn(existingTokens);

        // When
        AuthResponse response = authService.authenticate(request);

        // Then
        assertNotNull(response);
        assertEquals("accessToken", response.getAccessToken());
        assertEquals("refreshToken", response.getRefreshToken());
        verify(tokenRepository, times(1)).saveAll(existingTokens); // Ensure existing tokens are revoked
        verify(tokenRepository, times(2)).save(any(Token.class)); // Check for new tokens saved
    }

    @Test
    public void testRefreshToken_ValidToken_ReturnsNewAccessToken() {
        // Given
        String refreshToken = "validRefreshToken";
        String newAccessToken = "newAccessToken";
        String userEmail = "user@example.com";
        User user = User.builder()
                .id(1)
                .email(userEmail)
                .build();
        List<Token> userTokens = List.of(new Token(), new Token()); // Mock existing tokens

        // Mock
        when(jwtService.extractEmail(refreshToken)).thenReturn(userEmail);
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(user));
        when(jwtService.isTokenValid(refreshToken, user)).thenReturn(true);
        when(jwtService.generateAccessToken(anyMap(), eq(user))).thenReturn(newAccessToken);
        when(tokenRepository.findAllValidTokensByUserId(user.getId())).thenReturn(userTokens);

        // When
        AuthResponse response = authService.refreshToken("Bearer " + refreshToken);

        // Then
        assertNotNull(response);
        assertEquals(newAccessToken, response.getAccessToken());
        assertEquals(refreshToken, response.getRefreshToken());
        verify(tokenRepository).saveAll(anyList()); // Check that all access tokens are revoked and saved
    }

    @Test
    public void testRefreshToken_InvalidToken_ThrowsMalformedJwtException() {
        // Given
        String invalidToken = "Bearer invalidToken";

        // Mock
        when(jwtService.extractEmail(invalidToken)).thenThrow(new MalformedJwtException("invalid.refresh.token"));

        // When & Then
        Exception exception = assertThrows(MalformedJwtException.class, () -> authService.refreshToken(invalidToken));
        assertEquals("invalid.refresh.token", exception.getMessage());
    }

    @Test
    public void testRefreshToken_ExpiredToken_ThrowsExpiredJwtException() {
        // Given
        String expiredToken = "expiredRefreshToken";
        String userEmail = "user@example.com";
        User user = User.builder()
                .id(1)
                .email(userEmail)
                .build();

        // Mock
        when(jwtService.extractEmail(expiredToken)).thenReturn(userEmail);
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(user));
        when(jwtService.isTokenValid(expiredToken, user)).thenReturn(false);

        // When & Then
        assertThrows(ExpiredJwtException.class, () -> authService.refreshToken("Bearer " + expiredToken));
    }
}