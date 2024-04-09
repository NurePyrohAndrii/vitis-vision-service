package com.vitisvision.vitisvisionservice;

import com.vitisvision.vitisvisionservice.security.service.JwtService;
import com.vitisvision.vitisvisionservice.security.token.Token;
import com.vitisvision.vitisvisionservice.security.token.TokenRepository;
import com.vitisvision.vitisvisionservice.user.entity.Role;
import com.vitisvision.vitisvisionservice.user.entity.User;
import com.vitisvision.vitisvisionservice.user.repository.UserRepository;
import com.vitisvision.vitisvisionservice.security.token.TokenType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;

/**
 * The main class for the Spring Boot application.
 */
@SpringBootApplication
public class VitisVisionServiceApplication {

    /**
     * The main method to start the Spring Boot application.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(VitisVisionServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(UserRepository userRepository, JwtService jwtService, TokenRepository tokenRepository) {
        return args -> {


            // Create an admin user and save it to the database
            User admin = User.builder()
                    .firstName("Admin")
                    .lastName("User")
                    .email("admin@admin.com")
                    .id(1)
                    .password("admin")
                    .role(Role.ADMIN)
					.build();

			userRepository.save(admin);

			String accessToken = jwtService.generateAccessToken(Collections.emptyMap(), admin);
			String refreshToken = jwtService.generateRefreshToken(admin);

			Token accessToken1 = Token.builder()
					.token(accessToken)
					.user(admin)
					.tokenType(TokenType.ACCESS)
					.build();

			Token refreshToken1 = Token.builder()
					.token(refreshToken)
					.user(admin)
					.tokenType(TokenType.REFRESH)
					.build();

			tokenRepository.save(accessToken1);
			tokenRepository.save(refreshToken1);

			UsernamePasswordAuthenticationToken authToken =
					new UsernamePasswordAuthenticationToken(
							admin, null, admin.getAuthorities()
					);

			SecurityContextHolder.getContext().setAuthentication(authToken);

			System.out.printf("Admin access token: %s%n", accessToken);
			System.out.printf("Admin refresh token: %s%n", refreshToken);

        };
    }

}
