package com.trentonrush.granitesolutions.payment.model;

import java.util.List;

public class PaymentResponse {
    private List<CheckoutProduct> checkoutProducts;
    private String sessionUrl;

    public PaymentResponse(List<CheckoutProduct> checkoutProducts, String sessionUrl) {
        this.checkoutProducts = checkoutProducts;
        this.sessionUrl = sessionUrl;
    }

    public List<CheckoutProduct> getCheckoutProducts() {
        return checkoutProducts;
    }

    public void setCheckoutProducts(List<CheckoutProduct> checkoutProducts) {
        this.checkoutProducts = checkoutProducts;
    }

    public String getSessionUrl() {
        return sessionUrl;
    }

    public void setSessionUrl(String sessionUrl) {
        this.sessionUrl = sessionUrl;
    }
}
