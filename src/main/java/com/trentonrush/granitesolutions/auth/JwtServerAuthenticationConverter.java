package com.trentonrush.granitesolutions.auth;

import com.trentonrush.granitesolutions.service.JwtService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
class JwtServerAuthenticationConverter implements ServerAuthenticationConverter {

    private final JwtService jwtService;
    private final ReactiveUserDetailsService userDetailsService;

    public JwtServerAuthenticationConverter(JwtService jwtService, ReactiveUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        String token = extractTokenFromHeader(exchange.getRequest().getHeaders());
        if (token != null) {
            String username = jwtService.extractUsername(token);
            return userDetailsService.findByUsername(username)
                    .flatMap(userDetails -> Mono.just(new JwtAuthenticationToken(userDetails, token, userDetails.getAuthorities(), jwtService)));
        }
        return Mono.empty();
    }

    private String extractTokenFromHeader(HttpHeaders headers) {
        final String authHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}