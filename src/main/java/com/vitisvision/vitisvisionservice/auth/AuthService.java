package com.vitisvision.vitisvisionservice.auth;

import com.vitisvision.vitisvisionservice.exception.DuplicateResourceException;
import com.vitisvision.vitisvisionservice.exception.ResourceNotFoundException;
import com.vitisvision.vitisvisionservice.jwt.JwtService;
import com.vitisvision.vitisvisionservice.token.Token;
import com.vitisvision.vitisvisionservice.token.TokenRepository;
import com.vitisvision.vitisvisionservice.token.TokenType;
import com.vitisvision.vitisvisionservice.user.Role;
import com.vitisvision.vitisvisionservice.user.User;
import com.vitisvision.vitisvisionservice.user.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        // check if email exists
        String email = request.getEmail();
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateResourceException(
                    "Such email is already used by another user. Please use another email."
            );
        }

        // create and save user
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);

        // generate and save tokens
        String accessToken = jwtService.generateAccessToken(Map.of(), user);
        saveUserToken(user, accessToken, TokenType.ACCESS);

        String refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(user, refreshToken, TokenType.REFRESH);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthResponse authenticate(AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // generate and save tokens
        User user = (User) authentication.getPrincipal();

        String jwt = jwtService.generateAccessToken(Map.of(), user);
        String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);

        saveUserToken(user, jwt, TokenType.ACCESS);
        saveUserToken(user, refreshToken, TokenType.REFRESH);

        return AuthResponse.builder()
                .accessToken(jwt)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthResponse refreshToken(
            HttpServletRequest request
    ) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;

        if (Objects.isNull(authHeader) || !authHeader.startsWith("Bearer ")) {
            return null;
        }

        refreshToken = authHeader.substring(7);

        userEmail = jwtService.extractEmail(refreshToken);
        if (Objects.nonNull(userEmail)) {
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new ResourceNotFoundException("User associated with the refresh token not found"));

            if (jwtService.isTokenValid(refreshToken, user)) {
                String accessToken = jwtService.generateAccessToken(Map.of(), user);
                revokeAllUserAccessTokens(user);
                saveUserToken(user, accessToken, TokenType.ACCESS);
                return AuthResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
            } else {
                throw new ExpiredJwtException(null, null, "Refresh token has expired, please authenticate again");
            }
        } else {
            throw new MalformedJwtException("Invalid refresh token, principal not found");
        }
    }

    private void saveUserToken(User user, String jwt, TokenType tokenType) {
        Token token = Token.builder()
                .user(user)
                .token(jwt)
                .tokenType(tokenType)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokensByUserId(user.getId());
        if (validUserTokens.isEmpty()) return;
        validUserTokens.forEach(t -> t.setRevoked(true));
        tokenRepository.saveAll(validUserTokens);
    }

    private void revokeAllUserAccessTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokensByUserId(user.getId());
        if (validUserTokens.isEmpty()) return;
        validUserTokens.stream().filter(t -> t.getTokenType() == TokenType.ACCESS).forEach(t -> t.setRevoked(true));
        tokenRepository.saveAll(validUserTokens);
    }

}
