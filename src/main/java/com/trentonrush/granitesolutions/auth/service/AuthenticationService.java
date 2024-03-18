package com.trentonrush.granitesolutions.auth.service;

import com.trentonrush.granitesolutions.auth.model.LoginResponse;
import com.trentonrush.granitesolutions.auth.model.LoginRequest;
import com.trentonrush.granitesolutions.user.service.UserService;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AuthenticationService {

    private final ReactiveAuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;

    public AuthenticationService(ReactiveAuthenticationManager authenticationManager,
                                 UserService userService,
                                 JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    public Mono<LoginResponse> authenticate(LoginRequest loginRequest) {
        return authenticateUser(loginRequest.getUsername(), loginRequest.getPassword())
                .flatMap(this::generateTokenAndRespond);
    }

    private Mono<Authentication> authenticateUser(String username, String password) {
        return Mono.just(new UsernamePasswordAuthenticationToken(username, password))
                .flatMap(authenticationManager::authenticate);
    }

    private Mono<LoginResponse> generateTokenAndRespond(Authentication authentication) {
        return userService.findByUsername(authentication.getName())
                .flatMap(userDetails -> jwtService.generateToken(userDetails)
                        .map(LoginResponse::new))
                .onErrorResume(ex -> Mono.error(new UsernameNotFoundException(ex.getMessage())));
    }

}
