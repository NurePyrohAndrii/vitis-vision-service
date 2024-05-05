package com.vitisvision.vitisvisionservice.security.service;

import com.vitisvision.vitisvisionservice.security.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;


import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    public void setUp() {
        jwtService = new JwtService();

        ReflectionTestUtils.setField(jwtService, "SECRET_KEY", "3063273f3276282c4a2f7d3f28593f6557707247672b35546e47547e66");
        ReflectionTestUtils.setField(jwtService, "accessExpiration", 1000 * 60 * 15); // 15 minutes
        ReflectionTestUtils.setField(jwtService, "refreshExpiration", 1000 * 60 * 60 * 24); // 24 hours
    }

    @Test
    public void testGenerateAccessToken() {
        // Given
        UserDetails userDetails = new User("user@example.com", "", AuthorityUtils.createAuthorityList("ROLE_USER"));

        // When
        String token = jwtService.generateAccessToken(new HashMap<>(), userDetails);

        // Then
        assertNotNull(token);
        assertEquals("user@example.com", jwtService.extractEmail(token));
    }

    @Test
    public void testTokenValidation() {
        // Given
        UserDetails userDetails = new User("user@example.com", "", AuthorityUtils.createAuthorityList("ROLE_USER"));

        // When
        String token = jwtService.generateAccessToken(new HashMap<>(), userDetails);

        // Then
        assertTrue(jwtService.isTokenValid(token, userDetails));
        assertFalse(jwtService.isTokenExpired(token));
    }

    @Test
    public void testExtractEmail() {
        // Given
        UserDetails userDetails = new User("user@example.com", "", AuthorityUtils.createAuthorityList("ROLE_USER"));
        String token = jwtService.generateAccessToken(new HashMap<>(), userDetails);

        // When
        String email = jwtService.extractEmail(token);

        // Then
        assertEquals("user@example.com", email);
    }

    @Test
    public void testGenerateRefreshToken() {
        // Given
        UserDetails userDetails = new User("user@example.com", "", AuthorityUtils.createAuthorityList("ROLE_USER"));

        // When
        String token = jwtService.generateRefreshToken(userDetails);

        // Then
        assertNotNull(token);
        assertEquals("user@example.com", jwtService.extractEmail(token));
    }

}