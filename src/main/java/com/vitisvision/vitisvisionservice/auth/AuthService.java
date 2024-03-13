package com.vitisvision.vitisvisionservice.auth;

import com.vitisvision.vitisvisionservice.jwt.JwtService;
import com.vitisvision.vitisvisionservice.token.Token;
import com.vitisvision.vitisvisionservice.token.TokenRepository;
import com.vitisvision.vitisvisionservice.token.TokenType;
import com.vitisvision.vitisvisionservice.user.Role;
import com.vitisvision.vitisvisionservice.user.User;
import com.vitisvision.vitisvisionservice.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);

        String jwt = jwtService.generateToken(user);
        saveUserToken(user, jwt);
        return AuthResponse.builder()
                .token(jwt)
                .build();
    }

    public AuthResponse authenticate(AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = (User) authentication.getPrincipal();
        String jwt = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwt);

        return AuthResponse.builder()
                .token(jwt)
                .build();
    }

    private void saveUserToken(User user, String jwt) {
        Token token = Token.builder()
                .user(user)
                .token(jwt)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokensByUserId(user.getId());
        if (validUserTokens.isEmpty()) return;
        validUserTokens.forEach(t -> {
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

}
