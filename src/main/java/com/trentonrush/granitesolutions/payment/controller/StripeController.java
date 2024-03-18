package com.trentonrush.granitesolutions.payment.controller;

import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.LineItem;
import com.stripe.model.LineItemCollection;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionLineItemListParams;
import com.stripe.param.checkout.SessionListLineItemsParams;
import com.trentonrush.granitesolutions.payment.model.PaymentRequest;
import com.trentonrush.granitesolutions.payment.model.PaymentResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/payment/stripe")
public class StripeController {

    @PostMapping("/webhook")
    public void createPayment(@RequestBody Event event) throws StripeException {
        Session session = Session.retrieve("cs_test_a1ntCMiHbjOM1War3TcQGUxGqT8mOqNVdTaJ5gT9W1ddqnWGHx6OIqG5ks");
        SessionListLineItemsParams params = SessionListLineItemsParams.builder().build();
        System.out.println(session.listLineItems(params).getData().get(1));
    }

}
