package com.trentonrush.granitesolutions.payment.service;

import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.trentonrush.granitesolutions.payment.model.PaymentRequest;
import com.trentonrush.granitesolutions.payment.model.CheckoutProduct;
import com.trentonrush.granitesolutions.payment.model.PaymentResponse;
import com.trentonrush.granitesolutions.shared.model.strapi.Product;
import com.trentonrush.granitesolutions.shared.config.ApplicationConfig;
import com.trentonrush.granitesolutions.shared.config.WebConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StripeService implements PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(StripeService.class);
    private final WebClient strapiWebClient;
    private final WebConfig webConfig;

    public StripeService(ApplicationConfig applicationConfig, @Qualifier(value="strapiWebClient") WebClient strapiWebClient, WebConfig webConfig) {
        Stripe.apiKey = applicationConfig.getStripeSecretKey();
        this.strapiWebClient = strapiWebClient;
        this.webConfig = webConfig;
    }

    public Mono<PaymentResponse> create(Mono<PaymentRequest> paymentRequestMono) {
        return paymentRequestMono
                .flatMap(paymentRequest -> createSession(paymentRequest)
                        .map(session -> new PaymentResponse(paymentRequest.getCheckoutProducts(), session.getUrl())));
    }

    public Mono<Session> createSession(PaymentRequest paymentRequest) {
        return buildLineItems(paymentRequest.getCheckoutProducts())
                .collectList()
                .flatMap(lineItems -> Mono.fromCallable(() -> Session.create(SessionCreateParams.builder()
                            .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                            .setCustomerEmail(paymentRequest.getEmail())
                            .setMode(SessionCreateParams.Mode.PAYMENT)
                            .setSuccessUrl(String.format("%s/checkout/success", webConfig.getFrontend()))
                            .setCancelUrl(webConfig.getFrontend())
                            .addAllLineItem(lineItems)
                            .build())));

    }

    private Flux<SessionCreateParams.LineItem> buildLineItems(List<CheckoutProduct> checkoutProducts) {
        return Flux.fromIterable(checkoutProducts)
                .flatMap(checkoutProduct -> strapiWebClient.get()
                        .uri("/api/products/{id}", checkoutProduct.getId())
                        .retrieve()
                        .bodyToMono(Product.class)
                        .map(product -> SessionCreateParams.LineItem.builder()
                                .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                        .setCurrency("usd")
                                        .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                .setName(product.getData().getAttributes().getName())
                                                .putMetadata("id", Integer.toString(product.getData().getId()))
                                                .build())
                                        .setUnitAmount((long) product.getData().getAttributes().getPrice() * 100)
                                        .build())
                                .setQuantity(checkoutProduct.getCount())
                                .build()));
    }
}
