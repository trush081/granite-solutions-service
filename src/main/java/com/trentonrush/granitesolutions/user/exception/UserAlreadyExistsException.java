package com.trentonrush.granitesolutions.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String message) {
        super(message);
    }

    public Mono<ResponseStatusException> asResponseStatusException() {
        return Mono.just(new ResponseStatusException(HttpStatus.CONFLICT, this.getMessage()));
    }
}
