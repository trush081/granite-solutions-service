package com.trentonrush.granitesolutions.payment.controller;

import com.stripe.model.checkout.Session;
import com.trentonrush.granitesolutions.payment.model.PaymentRequest;
import com.trentonrush.granitesolutions.payment.model.PaymentResponse;
import com.trentonrush.granitesolutions.payment.service.StripeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    final StripeService stripeService;
    public PaymentController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping()
    public Mono<PaymentResponse> createPayment(@RequestBody Mono<PaymentRequest> paymentRequestMono) {
         return stripeService.create(paymentRequestMono);
    }
}
