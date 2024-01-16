package com.trentonrush.granitesolutions.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@EnableWebFlux
public class WebConfig implements WebFluxConfigurer {

    @Value("${spring.data.origins}")
    private String allowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .allowedOrigins(allowedOrigins)
                .allowedHeaders(
                        HttpHeaders.ACCEPT,
                        HttpHeaders.ACCEPT_ENCODING,
                        HttpHeaders.ACCEPT_LANGUAGE,
                        HttpHeaders.AUTHORIZATION,
                        HttpHeaders.CONTENT_TYPE,
                        HttpHeaders.ORIGIN,
                        HttpHeaders.REFERER,
                        HttpHeaders.USER_AGENT)
                .allowedMethods(
                        HttpMethod.POST.name(),
                        HttpMethod.GET.name())
                .exposedHeaders(
                        HttpHeaders.AUTHORIZATION
                )
                .allowCredentials(true)
                .maxAge(3600);
    }
}
