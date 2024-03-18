package com.trentonrush.granitesolutions.payment.service;

import com.trentonrush.granitesolutions.payment.model.PaymentRequest;
import com.trentonrush.granitesolutions.payment.model.PaymentResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public interface PaymentService {
    Mono<PaymentResponse> create(Mono<PaymentRequest> paymentRequestMono);
}
