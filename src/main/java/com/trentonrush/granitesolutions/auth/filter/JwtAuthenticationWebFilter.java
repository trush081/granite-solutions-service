package com.trentonrush.granitesolutions.auth.filter;
import com.trentonrush.granitesolutions.auth.model.JwtAuthenticationToken;
import com.trentonrush.granitesolutions.auth.service.JwtService;
import com.trentonrush.granitesolutions.user.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@Primary
public class JwtAuthenticationWebFilter implements WebFilter {

    private final JwtService jwtService;
    private final UserService userService;

    public JwtAuthenticationWebFilter(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    @NonNull
    public Mono<Void> filter(ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        String token = extractTokenFromRequest(exchange.getRequest());
        if (null != token) {
            String username = jwtService.extractUsername(token);
            return userService.findByUsername(username)
                    .flatMap(userDetails -> {
                        Authentication authentication = new JwtAuthenticationToken(userDetails, token, userDetails.getAuthorities(), jwtService);
                        return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
                    }).switchIfEmpty(chain.filter(exchange));
        }
        return chain.filter(exchange);
    }

    private String extractTokenFromRequest(ServerHttpRequest request) {
        List<String> authHeaders = request.getHeaders().get("Authorization");
        if (authHeaders != null && !authHeaders.isEmpty()) {
            String bearerToken = authHeaders.get(0);
            if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
                return bearerToken.substring(7);
            }
        }
        return null;
    }

}
