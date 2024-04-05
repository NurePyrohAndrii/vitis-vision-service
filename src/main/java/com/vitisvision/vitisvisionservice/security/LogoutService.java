package com.vitisvision.vitisvisionservice.security;

import com.vitisvision.vitisvisionservice.exception.ResourceNotFoundException;
import com.vitisvision.vitisvisionservice.jwt.JwtService;
import com.vitisvision.vitisvisionservice.token.Token;
import com.vitisvision.vitisvisionservice.token.TokenRepository;
import com.vitisvision.vitisvisionservice.user.User;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Service class for logging out a user.
 */
@Service
@RequiredArgsConstructor
public class LogoutService {

    /**
     * The token repository to access the tokens.
     */
    private final TokenRepository tokenRepository;

    /**
     * The JWT service to validate the token.
     */
    private final JwtService jwtService;

    /**
     * Logs out a user by revoking all valid tokens.
     *
     * @param request the HTTP request
     */
    public void logout(HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;

        if (Objects.isNull(authHeader) || !authHeader.startsWith("Bearer ")) {
            throw new MalformedJwtException("JWT was not correctly constructed");
        }

        jwt = authHeader.substring(7);
        Token storedToken = tokenRepository.findByToken(jwt).orElse(null);

        if (Objects.nonNull(storedToken)) {
            User user = storedToken.getUser();
            boolean isTokenValid = jwtService.isTokenValid(storedToken.getToken(), user);

            if (!storedToken.isRevoked() && isTokenValid) {
                tokenRepository.findAllValidTokensByUserId(user.getId()).forEach(token -> {
                    token.setRevoked(true);
                    tokenRepository.save(token);
                });
            } else {
                throw new JwtException("Token is invalid or has expired");
            }
        } else {
            throw new ResourceNotFoundException("Token not found");
        }
    }

}
