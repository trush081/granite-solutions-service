package com.trentonrush.granitesolutions.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Value("${granite.app.jwtSecret}")
    private String jwtSecret;

    public String getJwtSecret() {
        return jwtSecret;
    }
}
