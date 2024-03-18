package com.trentonrush.granitesolutions.user.controller;

import com.trentonrush.granitesolutions.auth.model.LoginResponse;
import com.trentonrush.granitesolutions.auth.model.RegistrationResponse;
import com.trentonrush.granitesolutions.auth.service.AuthenticationService;
import com.trentonrush.granitesolutions.auth.model.LoginRequest;
import com.trentonrush.granitesolutions.auth.model.RegistrationRequest;
import com.trentonrush.granitesolutions.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    public UserController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public Mono<ResponseEntity<RegistrationResponse>> registerUser(@RequestBody Mono<RegistrationRequest> registrationRequest) {
        return registrationRequest.flatMap(userService::createUser)
                .map(registrationResponse -> ResponseEntity.status(HttpStatus.CREATED).body(registrationResponse));
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<LoginResponse>> loginUser(@RequestBody Mono<LoginRequest> loginRequest) {
        return loginRequest.flatMap(authenticationService::authenticate).map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }


}
