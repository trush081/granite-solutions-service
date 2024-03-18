package com.trentonrush.granitesolutions.shared.config;

import com.trentonrush.granitesolutions.user.exception.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import java.util.Map;

@Configuration
@EnableWebFlux
public class WebConfig implements WebFluxConfigurer {

    @Value("${spring.data.frontend}")
    private String frontend;

    @Bean
    public WebProperties.Resources resources() {
        return new WebProperties.Resources();
    }

    @Bean
    public Map<Class<? extends Exception>, HttpStatus> exceptionToStatusCode() {
        return Map.of(
                UsernameNotFoundException.class, HttpStatus.NOT_FOUND,
                AuthenticationException.class, HttpStatus.UNAUTHORIZED,
                BadCredentialsException.class, HttpStatus.UNAUTHORIZED,
                UserAlreadyExistsException.class, HttpStatus.CONFLICT
        );
    }

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .allowedOrigins(frontend)
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

    public String getFrontend() {
        return frontend;
    }

    public void setFrontend(String frontend) {
        this.frontend = frontend;
    }
}
