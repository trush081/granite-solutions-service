package com.trentonrush.granitesolutions.auth;
import com.trentonrush.granitesolutions.service.JwtService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@Primary
public class JwtAuthenticationWebFilter extends AuthenticationWebFilter {

    private final JwtService jwtService;

    public JwtAuthenticationWebFilter(@Qualifier("jwtAuthenticationManager")ReactiveAuthenticationManager authenticationManager,
                                      @Qualifier("jwtServerAuthenticationConverter") ServerAuthenticationConverter authenticationConverter,
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

}
