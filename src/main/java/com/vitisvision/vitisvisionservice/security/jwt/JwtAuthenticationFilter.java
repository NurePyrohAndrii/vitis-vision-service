package com.vitisvision.vitisvisionservice.security.jwt;

import com.vitisvision.vitisvisionservice.security.token.Token;
import com.vitisvision.vitisvisionservice.security.token.TokenRepository;
import com.vitisvision.vitisvisionservice.security.token.TokenType;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
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

/**
 * This class is responsible for authenticating the request using JWT token
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /**
     * The JwtService object to handle JWT operations
     */
    private final JwtService jwtService;

    /**
     * The UserDetailsService object to load user details
     */
    private final UserDetailsService userDetailsService;

    /**
     * The JwtExceptionHandler object to handle JWT exceptions
     */
    private final JwtExceptionHandler jwtExceptionHandler;

    /**
     * The TokenRepository object to interact with the token persistence
     */
    private final TokenRepository tokenRepository;

    /**
     * Constructor to initialize the JwtAuthenticationFilter object
     * @param jwtService the JwtService object
     * @param userDetailsService the UserDetailsService object qualified with "userDetailsServiceImpl"
     * @param jwtExceptionHandler the JwtExceptionHandler object
     * @param tokenRepository the TokenRepository object
     */
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

    /**
     * This method is called by the filter chain to authenticate the request
     * @param request the request object
     * @param response the response object
     * @param filterChain the filter chain object
     * @throws ServletException if an error occurs while processing the request
     * @throws IOException if an error occurs while reading the request
     */
    @Override
    public void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String jwt;
        final String userEmail;

        if (Objects.isNull(authHeader) || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        MDC.put("context", "authenticating...");
        jwt = authHeader.substring(7);


        try {
            userEmail = jwtService.extractEmail(jwt);
            MDC.put("context", userEmail);

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
                    jwtExceptionHandler.handleJwtException(response, new JwtException("invalid.jwt"));
                }

            }

            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            jwtExceptionHandler.handleJwtException(response, e);
        }
    }
}
