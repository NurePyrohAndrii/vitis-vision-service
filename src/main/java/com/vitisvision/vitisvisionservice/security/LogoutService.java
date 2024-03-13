package com.vitisvision.vitisvisionservice.security;

import com.vitisvision.vitisvisionservice.exception.ResourceNotFoundException;
import com.vitisvision.vitisvisionservice.token.Token;
import com.vitisvision.vitisvisionservice.token.TokenRepository;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LogoutService {

    private final TokenRepository tokenRepository;

    public void logout(HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;

        if (Objects.isNull(authHeader) || !authHeader.startsWith("Bearer ")) {
            throw new MalformedJwtException("JWT was not correctly constructed");
        }

        jwt = authHeader.substring(7);
        Token storedToken = tokenRepository.findByToken(jwt)
                .orElse(null);

        if (Objects.nonNull(storedToken) && !storedToken.isRevoked() && !storedToken.isExpired()) {
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
        } else {
            throw new ResourceNotFoundException("Token is invalid or has expired");
        }
    }

}
