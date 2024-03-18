package com.trentonrush.granitesolutions.shared.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Value("${granite.app.jwtSecret}")
    private String jwtSecret;

    @Value("${granite.app.stripeSecretKey}")
    private String stripeSecretKey;

    public String getJwtSecret() {
        return jwtSecret;
    }

    public String getStripeSecretKey() {
        return stripeSecretKey;
    }
}
