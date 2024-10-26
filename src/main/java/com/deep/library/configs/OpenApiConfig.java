package com.deep.library.configs;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class OpenApiConfig {

    private final OpenApiConfigLoader openApiConfigLoader;

    public OpenApiConfig(OpenApiConfigLoader openApiConfigLoader) {
        this.openApiConfigLoader = openApiConfigLoader;
    }

    @Bean
    public OpenAPI customOpenAPI() throws IOException {
        try (InputStream inputStream = new ClassPathResource("api.yaml")
                .getInputStream()) {
            return openApiConfigLoader.load(inputStream);
        }
    }
}
