package com.vitisvision.vitisvisionservice.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * PasswordEncoderConfig class is used to configure the password encoder for the application.
 */
@Configuration
public class PasswordEncoderConfig {

    /**
     * passwordEncoder method is used to create a bean of PasswordEncoder.
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
