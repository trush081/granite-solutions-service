package com.trentonrush.granitesolutions.controller;

import com.trentonrush.granitesolutions.entity.User;
import com.trentonrush.granitesolutions.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public Mono<ResponseEntity<String>> registerUser(@RequestBody User user) {
        return userService.registerUser(user)
                .map(savedUser -> ResponseEntity.ok("User registered successfully"))
                .onErrorResume(error -> Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error.getMessage())));
    }

    @GetMapping("/login")
    public Mono<String> loginUser() {
        return Mono.just("Login");
    }
}
