package com.vitisvision.vitisvisionservice.security.service;

import com.vitisvision.vitisvisionservice.common.exception.ResourceNotFoundException;
import com.vitisvision.vitisvisionservice.security.entity.Token;
import com.vitisvision.vitisvisionservice.security.repository.TokenRepository;
import com.vitisvision.vitisvisionservice.user.entity.User;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class LogoutServiceTest {

    @InjectMocks
    private LogoutService logoutService;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private JwtService jwtService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void logout_ValidToken_RevokesToken() {
        // Given
        String validJwtToken = "validJwtToken";

        // Mock
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn("Bearer " + validJwtToken);
        User user = mock(User.class);
        when(user.getId()).thenReturn(1);
        Token token = new Token();
        token.setUser(user);
        token.setToken(validJwtToken);

        when(tokenRepository.findByToken(validJwtToken)).thenReturn(Optional.of(token));
        when(jwtService.isTokenValid(validJwtToken, user)).thenReturn(true);
        when(tokenRepository.findAllValidTokensByUserId(user.getId())).thenReturn(List.of(token));

        // When
        logoutService.logout(request);

        // Then
        verify(tokenRepository).findByToken(validJwtToken);
        verify(tokenRepository).findAllValidTokensByUserId(user.getId());
        verify(tokenRepository, atLeastOnce()).save(any(Token.class));
    }

    @Test
    public void logout_MalformedToken_ThrowsMalformedJwtException() {
        // Given
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bad token");

        // When & Then
        Exception exception = assertThrows(MalformedJwtException.class, () -> logoutService.logout(request));
        assertEquals("error.malformed.jwt", exception.getMessage());
    }

    @Test
    public void logout_TokenNotFound_ThrowsResourceNotFoundException() {
        // Given
        String missingJwt = "missingJwtToken";
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + missingJwt);

        // Mock
        when(tokenRepository.findByToken(missingJwt)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> logoutService.logout(request));
    }

    @Test
    public void logout_InvalidOrExpiredToken_ThrowsJwtException() {
        // Given
        String invalidJwt = "invalidJwtToken";
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + invalidJwt);
        User user = new User();
        Token storedToken = new Token();
        storedToken.setToken(invalidJwt);
        storedToken.setUser(user);

        // Mock
        when(tokenRepository.findByToken(invalidJwt)).thenReturn(Optional.of(storedToken));
        when(jwtService.isTokenValid(invalidJwt, user)).thenReturn(false);

        // When & Then
        assertThrows(JwtException.class, () -> logoutService.logout(request));
    }
}