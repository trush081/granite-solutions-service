package com.trentonrush.granitesolutions.payment.model;

import java.util.List;

public class PaymentRequest {
    private String userName;
    private String email;
    private List<CheckoutProduct> checkoutProducts;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<CheckoutProduct> getCheckoutProducts() {
        return checkoutProducts;
    }

    public void setCheckoutProducts(List<CheckoutProduct> checkoutProducts) {
        this.checkoutProducts = checkoutProducts;
    }
}
