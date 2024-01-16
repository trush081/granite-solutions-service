package com.trentonrush.granitesolutions.auth;
import com.trentonrush.granitesolutions.service.JwtService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;

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

}
