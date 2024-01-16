package com.trentonrush.granitesolutions.controller;

import com.trentonrush.granitesolutions.entity.User;
import com.trentonrush.granitesolutions.service.UserService;
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
    public Mono<String> registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @GetMapping("/login")
    public Mono<String> loginUser() {
        return Mono.just("Login");
    }
}
