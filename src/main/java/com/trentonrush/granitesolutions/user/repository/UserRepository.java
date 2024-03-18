package com.trentonrush.granitesolutions.user.repository;

import com.trentonrush.granitesolutions.user.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
    Mono<User> findByUsernameOrEmail(String username, String email);
    Mono<User> findByEmail(String email);
    Mono<Boolean> existsByUsernameOrEmail(String username, String email);
}
