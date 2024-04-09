package com.vitisvision.vitisvisionservice.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * SecurityConfig class is used to configure the beans required for the security
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    /**
     * UserDetailsService bean is used to load the user details from the database and provide it to the authentication provider
     */
    private final UserDetailsService userDetailsService;

    /**
     * PasswordEncoder bean is used to encode the password before storing it in the database
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * AuthenticationProvider bean is used to authenticate the user based on the user details provided by the userDetailsService
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    /**
     * AuthenticationManager bean is used to authenticate the user based on the authentication provider
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
