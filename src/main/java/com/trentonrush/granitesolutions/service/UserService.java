package com.trentonrush.granitesolutions.service;

import com.trentonrush.granitesolutions.configuration.SecurityConfig;
import com.trentonrush.granitesolutions.entity.User;
import com.trentonrush.granitesolutions.entity.enums.Role;
import com.trentonrush.granitesolutions.repository.UserRepository;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService implements ReactiveUserDetailsService {

    private final UserRepository userRepository;

    private final SecurityConfig securityConfig;
    public UserService(UserRepository userRepository, SecurityConfig securityConfig) {
        this.userRepository = userRepository;
        this.securityConfig = securityConfig;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .cast(UserDetails.class)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found")));
    }

    public Mono<User> registerUser(User user) {
        return userRepository.existsByUsername(user.getUsername())
                .flatMap(doesExist -> {
                    if (Boolean.TRUE.equals(doesExist)) {
                        return Mono.error(new RuntimeException("User already exists!"));
                    } else {
                        // Encode password and save the user
                        user.setPassword(securityConfig.passwordEncoder().encode(user.getPassword()));
                        return userRepository.save(user);
                    }
                });
    }



}
