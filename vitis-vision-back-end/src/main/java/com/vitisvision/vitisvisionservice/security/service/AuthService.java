package com.vitisvision.vitisvisionservice.security.service;

import com.vitisvision.vitisvisionservice.common.entity.auditing.ApplicationAuditorAware;
import com.vitisvision.vitisvisionservice.common.exception.DuplicateResourceException;
import com.vitisvision.vitisvisionservice.common.exception.ResourceNotFoundException;
import com.vitisvision.vitisvisionservice.security.dto.AuthResponse;
import com.vitisvision.vitisvisionservice.security.dto.AuthenticationRequest;
import com.vitisvision.vitisvisionservice.security.dto.RegisterRequest;
import com.vitisvision.vitisvisionservice.security.entity.Token;
import com.vitisvision.vitisvisionservice.security.repository.TokenRepository;
import com.vitisvision.vitisvisionservice.security.enumeration.TokenType;
import com.vitisvision.vitisvisionservice.user.enumeration.Role;
import com.vitisvision.vitisvisionservice.user.entity.User;
import com.vitisvision.vitisvisionservice.user.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * AuthService class provides methods for user authentication and registration.
 * It also provides methods for generating and refreshing access and refresh tokens.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    /**
     * UserRepository is used to interact with the database to perform CRUD operations on User entity.
     */
    private final UserRepository userRepository;

    /**
     * TokenRepository is used to interact with the database to perform CRUD operations on Token entity.
     */
    private final TokenRepository tokenRepository;

    /**
     * PasswordEncoder is used to encode and decode passwords.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * JwtService is used to generate and validate JWT tokens.
     */
    private final JwtService jwtService;

    /**
     * AuthenticationManager is used to authenticate users.
     */
    private final AuthenticationManager authenticationManager;

    /**
     * This method registers a new user.
     * It checks if the email is already used by another user.
     * If the email is not used, it creates a new user and saves it to the database.
     * It then generates and saves access and refresh tokens for the user.
     *
     * @param request RegisterRequest object containing user details.
     * @return AuthResponse object containing access and refresh tokens.
     */
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        try {
            // check if email exists
            String email = request.getEmail();
            if (userRepository.existsByEmail(email)) {
                throw new DuplicateResourceException(
                        "error.email.duplicate"
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

            ApplicationAuditorAware.setAuditor(user.getUsername());
            userRepository.save(user);

            // generate and save tokens
            String accessToken = jwtService.generateAccessToken(Map.of("role", "USER"), user);
            saveUserToken(user, accessToken, TokenType.ACCESS);

            String refreshToken = jwtService.generateRefreshToken(user);
            saveUserToken(user, refreshToken, TokenType.REFRESH);

            return AuthResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        } finally {
            ApplicationAuditorAware.clearAuditor();
        }
    }

    /**
     * This method authenticates a user.
     * It checks if the email and password match a user in the database.
     * If the email and password match, it generates and saves access and refresh tokens for the user.
     *
     * @param request AuthenticationRequest object containing user email and password.
     * @return AuthResponse object containing access and refresh tokens.
     */
    public AuthResponse authenticate(AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // generate and save tokens
        User user = (User) authentication.getPrincipal();

        String jwt = jwtService.generateAccessToken(Map.of("role", user.getRole().toString()), user);
        String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);

        saveUserToken(user, jwt, TokenType.ACCESS);
        saveUserToken(user, refreshToken, TokenType.REFRESH);

        return AuthResponse.builder()
                .accessToken(jwt)
                .refreshToken(refreshToken)
                .build();
    }

    /**
     * This method refreshes the access token using the refresh token.
     * It checks if the refresh token is valid and not expired.
     * If the refresh token is valid, it generates a new access token and saves it to the database.
     * It then revokes all the user's access tokens.
     *
     * @param jwt Refresh token.
     * @return AuthResponse object containing the new access token and the refresh token.
     */
    public AuthResponse refreshToken(String jwt) {
        final String refreshToken;
        final String userEmail;

        if (Objects.isNull(jwt) || !jwt.startsWith("Bearer ")) {
            return null;
        }

        refreshToken = jwt.substring(7);

        userEmail = jwtService.extractEmail(refreshToken);
        if (Objects.nonNull(userEmail)) {
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new ResourceNotFoundException("user.not.found.with.token"));

            if (jwtService.isTokenValid(refreshToken, user)) {
                String accessToken = jwtService.generateAccessToken(Map.of("role", user.getRole().toString()), user);
                revokeAllUserAccessTokens(user);
                saveUserToken(user, accessToken, TokenType.ACCESS);
                return AuthResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
            } else {
                throw new ExpiredJwtException(null, null, "expired.refresh.token");
            }
        } else {
            throw new MalformedJwtException("invalid.refresh.token");
        }
    }

    /**
     * This method revokes all the user's jwt.
     *
     * @param user User object whose tokens are to be revoked.
     */
    public void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokensByUserId(user.getId());
        if (validUserTokens.isEmpty()) return;
        validUserTokens.forEach(t -> t.setRevoked(true));
        tokenRepository.saveAll(validUserTokens);
    }

    /**
     * This method saves the jwt of the {@link TokenType} to the database.
     *
     * @param user      User object to which the token belongs.
     * @param jwt       JWT token to be saved.
     * @param tokenType TokenType of the token.
     */
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

    /**
     * This method revokes all the user's access tokens.
     *
     * @param user User object whose access tokens are to be revoked.
     */
    private void revokeAllUserAccessTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokensByUserId(user.getId());
        if (validUserTokens.isEmpty()) return;
        validUserTokens.stream().filter(t -> t.getTokenType() == TokenType.ACCESS).forEach(t -> t.setRevoked(true));
        tokenRepository.saveAll(validUserTokens);
    }

}
