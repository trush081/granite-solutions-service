package com.trentonrush.granitesolutions.auth;

import com.trentonrush.granitesolutions.service.JwtService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class BasicAuthenticationWebFilter extends AuthenticationWebFilter {

    private final JwtService jwtService;

    public BasicAuthenticationWebFilter(@Qualifier("userAuthenticationManager") ReactiveAuthenticationManager authenticationManager,
                                        @Qualifier("basicServerAuthenticationConverter") ServerAuthenticationConverter authenticationConverter,
                                        JwtService jwtService) {
        super(authenticationManager);
        this.jwtService = jwtService;
        setServerAuthenticationConverter(authenticationConverter);
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // Check if the request is for the registration endpoint
        ServerHttpRequest request = exchange.getRequest();
        if (request.getPath().toString().contains("/auth/register")) {
            // If it's the registration endpoint, bypass this filter
            return chain.filter(exchange);
        }

        // Otherwise, continue with the filter processing
        return super.filter(exchange, chain);
    }

    @Override
    protected Mono<Void> onAuthenticationSuccess(Authentication authentication, WebFilterExchange exchange) {
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            String token = jwtService.generateToken(authentication.getName());
            HttpHeaders headers = exchange.getExchange().getResponse().getHeaders();

            // Set the JWT in the response headers
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        }

        return super.onAuthenticationSuccess(authentication, exchange);
    }
}
