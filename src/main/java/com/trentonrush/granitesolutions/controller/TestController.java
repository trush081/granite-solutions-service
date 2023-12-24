package com.trentonrush.granitesolutions.controller;

import com.trentonrush.granitesolutions.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class TestController {
    private final UserService userService;

    public TestController(UserService userService) {
        this.userService = userService;
    }

    // TODO make below
//    @PostMapping("/register")
//    public Mono<Customer> registerUser(@RequestBody Customer customer) {
//        return customerService.registerUser(customer);
//    }

    @GetMapping("/test")
    public Mono<String> loginUser() {
        return Mono.just("Test");
    }
}

