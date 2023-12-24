package com.trentonrush.granitesolutions.auth;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Base64;

import static java.lang.Thread.sleep;

@Component
class BasicServerAuthenticationConverter implements ServerAuthenticationConverter {

    private final ReactiveUserDetailsService userDetailsService;
    BasicServerAuthenticationConverter(ReactiveUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        String authorizationHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith("Basic ")) {
            String base64Credentials = authorizationHeader.substring(6).trim();
            // Decode base64Credentials to extract username and password
            // For example:
            String decodedCredentials = new String(Base64.getDecoder().decode(base64Credentials));
            String[] credentials = decodedCredentials.split(":");

            String username = credentials[0];

            return userDetailsService.findByUsername(username)
                    .flatMap(userDetails -> Mono.just(new UsernamePasswordAuthenticationToken(userDetails, credentials[1], userDetails.getAuthorities())));
        }
        return Mono.empty();
    }
}
