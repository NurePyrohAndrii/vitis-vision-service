package com.vitisvision.vitisvisionservice.jwt;

import com.vitisvision.vitisvisionservice.token.Token;
import com.vitisvision.vitisvisionservice.token.TokenRepository;
import com.vitisvision.vitisvisionservice.token.TokenType;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final JwtExceptionHandler jwtExceptionHandler;
    private final TokenRepository tokenRepository;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            @Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService,
            JwtExceptionHandler jwtExceptionHandler,
            TokenRepository tokenRepository
    ) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.jwtExceptionHandler = jwtExceptionHandler;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        log.info("In doFilterInternal method");
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String jwt;
        final String userEmail;

        if (Objects.isNull(authHeader) || !authHeader.startsWith("Bearer ")) {
            log.warn("JWT token is missing");
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);

        try {
            userEmail = jwtService.extractEmail(jwt);

            if (Objects.nonNull(userEmail)
                    && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
                Optional<Token> token = tokenRepository.findByToken(jwt);

                boolean isDbRefreshTokenValid = token
                        .map(t -> !t.isExpired() && !t.isRevoked() && t.getTokenType() == TokenType.REFRESH)
                        .orElse(false);

                boolean isProvidedRefreshTokenValid = jwtService.isTokenValid(jwt, userDetails);

                // if token is refresh, then let it pass
                if (isDbRefreshTokenValid && isProvidedRefreshTokenValid) {
                    filterChain.doFilter(request, response);
                    return;
                }

                boolean isAccessTokenValid = token
                        .map(t -> !t.isExpired() && !t.isRevoked() && t.getTokenType() == TokenType.ACCESS)
                        .orElse(false);

                if (isProvidedRefreshTokenValid && isAccessTokenValid) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities()
                            );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    log.warn("This JWT token is not valid or has been revoked");
                    jwtExceptionHandler.handleJwtException(response, new JwtException("This JWT token is not valid or has been revoked"));
                }

            }

            log.info("Out doFilterInternal method");
            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            log.warn("JWT token is invalid");
            jwtExceptionHandler.handleJwtException(response, e);
        }
    }
}
