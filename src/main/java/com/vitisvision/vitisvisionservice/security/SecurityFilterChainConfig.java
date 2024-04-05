package com.vitisvision.vitisvisionservice.security;

import com.vitisvision.vitisvisionservice.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/**
 * SecurityFilterChainConfig class is used to configure the security filter chain that will be used to secure the application.
 */
@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityFilterChainConfig {

    /**
     * List of white listed URLs that are allowed to be accessed without authentication.
     */
    private static final String[] WHITE_LIST_URL = {
            "/api/v1/auth/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**"
    };

    /**
     * JwtAuthenticationFilter bean that will be used to authenticate the user using JWT token.
     */
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * AuthenticationProvider bean that will be used to authenticate the user.
     */
    private final AuthenticationProvider authenticationProvider;

    /**
     * LogoutHandler bean that will be used to handle the logout request.
     */
    private final LogoutHandler logoutHandler;

    /**
     * CorsConfigurationSource bean that will be used to configure the CORS policy.
     */
    //private final CorsConfigurationSource corsConfigurationSource;

    /**
     * Method to configure the security filter chain that will be used to secure the application.
     * @param http HttpSecurity object that will be used to configure the security filter chain.
     * @return SecurityFilterChain object that will be used to secure the application.
     * @throws Exception Exception that will be thrown if any error occurs.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                //.cors(cors -> cors.configurationSource(corsConfigurationSource))
                .authorizeHttpRequests(
                        req -> req
                                .requestMatchers(WHITE_LIST_URL)
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> {
                    logout.logoutUrl("/api/v1/auth/logout");
                    logout.addLogoutHandler(logoutHandler);
                    logout.logoutSuccessHandler(
                            (request, response, authentication) ->
                                    SecurityContextHolder.clearContext());
                })
                .build();
    }

}
