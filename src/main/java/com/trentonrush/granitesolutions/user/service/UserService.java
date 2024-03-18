package com.trentonrush.granitesolutions.user.service;

import com.trentonrush.granitesolutions.auth.model.RegistrationResponse;
import com.trentonrush.granitesolutions.shared.config.SecurityConfig;
import com.trentonrush.granitesolutions.auth.model.RegistrationRequest;
import com.trentonrush.granitesolutions.user.exception.UserAlreadyExistsException;
import com.trentonrush.granitesolutions.user.model.User;
import com.trentonrush.granitesolutions.user.repository.UserRepository;
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
        return userRepository.findByUsernameOrEmail(username, username)
                .cast(UserDetails.class)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("A user was not found with that username or email")));
    }

    public Mono<RegistrationResponse> createUser(RegistrationRequest registrationRequest) {
        return userRepository.existsByUsernameOrEmail(registrationRequest.getUsername(), registrationRequest.getEmail())
                .flatMap(exists -> Boolean.TRUE.equals(exists)
                        ? Mono.error(new UserAlreadyExistsException("A user with that username or email already exists"))
                        : Mono.just(createUserEntity(registrationRequest)))
                .flatMap(userRepository::save)
                .map(user -> new RegistrationResponse(user.getId(), user.getUsername(), user.getEmail()));
    }

    private User createUserEntity(RegistrationRequest registrationRequest) {
        User user = new User();
        user.setUsername(registrationRequest.getUsername());
        user.setPassword(securityConfig.passwordEncoder().encode(registrationRequest.getPassword()));
        user.setEmail(registrationRequest.getEmail());
        return user;
    }



}
