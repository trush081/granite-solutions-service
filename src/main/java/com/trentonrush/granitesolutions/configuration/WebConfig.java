package com.trentonrush.granitesolutions.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@EnableWebFlux
public class WebConfig implements WebFluxConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedHeaders(
                        HttpHeaders.ACCEPT,
                        HttpHeaders.CONTENT_TYPE)
                .allowedMethods(
                        HttpMethod.POST.name(),
                        HttpMethod.GET.name())
                .allowCredentials(true)
                .maxAge(3600);
    }
}
