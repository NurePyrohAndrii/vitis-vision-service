package com.vitisvision.vitisvisionservice.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

/**
 * OpenAPI Configuration class for Vitis Vision Service API
 */
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Vitis Vision",
                        email = "vitisvision@gmail.com",
                        url = "https://vitisvision.com"
                ),
                description = "OpenAPI documentation for Vitis Vision Service",
                title = "Vitis Vision Service",
                version = "0.0.1"
        ),
        servers = {
                @Server(
                        description = "Local Server",
                        url = "http://localhost:8181"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "Bearer Auth"
                )
        }
)
@SecurityScheme(
        name = "Bearer Auth",
        description = "JWT bearer token security scheme",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
