package com.trentonrush.granitesolutions.configuration;

import com.trentonrush.granitesolutions.auth.BasicAuthenticationWebFilter;
import com.trentonrush.granitesolutions.auth.JwtAuthenticationWebFilter;
import com.trentonrush.granitesolutions.auth.JwtReactiveAuthenticationManager;
import com.trentonrush.granitesolutions.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public ReactiveAuthenticationManager jwtAuthenticationManager() {
        return new JwtReactiveAuthenticationManager();
    }

    @Bean
    @Primary
    public ReactiveAuthenticationManager userAuthenticationManager(UserService userService) {
        return new UserDetailsRepositoryReactiveAuthenticationManager(userService);
    }

    @Bean
    public SecurityWebFilterChain basicWebFilterChain(ServerHttpSecurity http, BasicAuthenticationWebFilter basicAuthenticationWebFilter) {
        return http
                .authorizeExchange(exchanges ->
                        exchanges
                                .pathMatchers("/auth/**").permitAll()
                )
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .addFilterAt(basicAuthenticationWebFilter, SecurityWebFiltersOrder.HTTP_BASIC)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .build();
    }

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Bean
    public SecurityWebFilterChain bearerWebFilterChain(ServerHttpSecurity http, JwtAuthenticationWebFilter jwtAuthenticationWebFilter) {
        return http
                .securityMatcher(new PathPatternParserServerWebExchangeMatcher("/api/**"))
                .authorizeExchange(exchanges -> exchanges.anyExchange().authenticated())
//                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
//                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .addFilterAt(jwtAuthenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .build();
    }
}
