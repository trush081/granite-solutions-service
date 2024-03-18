package com.trentonrush.granitesolutions.shared.exception;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Component
public class ReactiveExceptionHandler extends AbstractErrorWebExceptionHandler {
    private final Map<Class<? extends Exception>, HttpStatus> exceptionToStatusCode;
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    public ReactiveExceptionHandler(ErrorAttributes errorAttributes, WebProperties.Resources resources,
                                    ApplicationContext applicationContext, ServerCodecConfigurer configurer,
                                    Map<Class<? extends Exception>, HttpStatus> exceptionToStatusCode) {
        super(errorAttributes, resources, applicationContext);
        this.setMessageReaders(configurer.getReaders());
        this.setMessageWriters(configurer.getWriters());
        this.exceptionToStatusCode = exceptionToStatusCode;
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(),this::renderException);
    }

    private Mono<ServerResponse> renderException(ServerRequest request) {
        Throwable error = getError(request);
        HttpStatus httpStatus = resolveHttpStatus(error);
        return ServerResponse
                .status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(new ErrorResponse(
                        request.path(),
                        httpStatus.getReasonPhrase(),
                        httpStatus.value(),
                        formatTimestamp(),
                        error.getMessage()
                )));

    }

    private HttpStatus resolveHttpStatus(Throwable error) {

        if (error instanceof Exception exception) {
            return exceptionToStatusCode.getOrDefault(exception.getClass(), HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    private String formatTimestamp() {
        return ZonedDateTime.now().withZoneSameInstant(java.time.ZoneOffset.UTC).format(TIMESTAMP_FORMATTER);
    }

    private record ErrorResponse(String path, String error, int status, String timestamp, String message) {
    }
}
