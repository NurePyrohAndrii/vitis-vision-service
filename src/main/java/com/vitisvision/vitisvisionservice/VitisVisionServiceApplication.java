package com.vitisvision.vitisvisionservice;

import com.vitisvision.vitisvisionservice.common.util.StartupSeeder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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

	//@Bean
    public CommandLineRunner run(StartupSeeder startupSeeder) {
        return args -> startupSeeder.run();
    }

}
