package com.trentonrush.granitesolutions.auth;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationWebFilter extends AuthenticationWebFilter {

    public JwtAuthenticationWebFilter(@Qualifier("jwtAuthenticationManager")ReactiveAuthenticationManager authenticationManager,
                                      @Qualifier("jwtServerAuthenticationConverter") ServerAuthenticationConverter authenticationConverter) {
        super(authenticationManager);
        setServerAuthenticationConverter(authenticationConverter);
    }

    // TODO Reminder to add success handler to renew token every call

}
