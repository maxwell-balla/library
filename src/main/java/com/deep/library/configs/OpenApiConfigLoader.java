package com.deep.library.configs;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Component
public class OpenApiConfigLoader {

    public OpenAPI load(InputStream inputStream) throws IOException {
        Yaml yaml = new Yaml();
        String yamlContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        return yaml.loadAs(yamlContent, OpenAPI.class);
    }
}