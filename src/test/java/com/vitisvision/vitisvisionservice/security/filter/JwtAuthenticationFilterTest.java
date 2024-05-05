package com.vitisvision.vitisvisionservice.security.filter;

import com.vitisvision.vitisvisionservice.security.advisor.JwtFilterExceptionHandler;
import com.vitisvision.vitisvisionservice.security.service.JwtService;
import com.vitisvision.vitisvisionservice.security.entity.Token;
import com.vitisvision.vitisvisionservice.security.repository.TokenRepository;
import com.vitisvision.vitisvisionservice.security.enumeration.TokenType;
import com.vitisvision.vitisvisionservice.user.entity.User;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private JwtFilterExceptionHandler jwtExceptionHandler;

    @Mock
    private TokenRepository tokenRepository;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private FilterChain filterChain;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtService, userDetailsService, jwtExceptionHandler, tokenRepository);
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        filterChain = mock(FilterChain.class);
        SecurityContextHolder.clearContext();
    }

    @Test
    public void whenTokenIsMissing_thenContinueFilterChain() throws IOException, ServletException {
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    public void whenTokenIsValid_thenAuthenticateAndContinueFilterChain() throws IOException, ServletException {
        // Given
        String validToken = "validToken";
        String userEmail = "user@example.com";
        request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + validToken);

        // Mock
        User user = mock(User.class);
        when(jwtService.extractEmail(validToken)).thenReturn(userEmail);
        when(userDetailsService.loadUserByUsername(userEmail)).thenReturn(user);
        when(jwtService.isTokenValid(validToken, user)).thenReturn(true);
        Token accessToken = new Token(1, validToken, TokenType.ACCESS, false, false, user);
        when(tokenRepository.findByToken(validToken)).thenReturn(Optional.of(accessToken));

        // When
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Then
        verify(filterChain, times(1)).doFilter(request, response);
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    public void whenTokenIsInvalid_thenHandleJwtException() throws IOException, ServletException {
        String invalidToken = "invalidToken";
        request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + invalidToken);

        when(jwtService.extractEmail(invalidToken)).thenThrow(new JwtException("Invalid JWT Token"));

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(jwtExceptionHandler, times(1)).handleJwtException(eq(response), any(JwtException.class));
    }

    @Test
    public void whenTokenIsNotValidOrRevoked_thenHandleJwtException() throws IOException, ServletException {
        String revokedToken = "revokedToken";
        String userEmail = "user@example.com";
        User user = mock(User.class);
        request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + revokedToken);

        when(jwtService.extractEmail(revokedToken)).thenReturn(userEmail);
        when(userDetailsService.loadUserByUsername(userEmail)).thenReturn(user);
        when(jwtService.isTokenValid(revokedToken, user)).thenReturn(false);
        Token accessToken = new Token(1, revokedToken, TokenType.ACCESS, false, true, user);
        when(tokenRepository.findByToken(revokedToken)).thenReturn(Optional.of(accessToken));

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(jwtExceptionHandler, times(1)).handleJwtException(eq(response), any(JwtException.class));
        verify(filterChain, times(1)).doFilter(request, response);
    }
}