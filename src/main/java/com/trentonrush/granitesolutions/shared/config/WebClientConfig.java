package com.trentonrush.granitesolutions.shared.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${spring.data.strapi}")
    private String strapiUrl;

    @Bean
    public WebClient strapiWebClient() {
        return WebClient.builder()
                .baseUrl(strapiUrl)
                .build();
    }

}
