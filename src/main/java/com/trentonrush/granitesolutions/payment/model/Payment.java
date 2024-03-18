package com.trentonrush.granitesolutions.payment.model;

import com.trentonrush.granitesolutions.payment.model.enums.PaymentStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "payments")
public class Payment {
    @Id
    private String id;
    private String stripeSessionId;
    private PaymentStatus paymentStatus;
    private List<CheckoutProduct> checkoutProducts;
    private String customerName;
    private String customerEmail;

    public Payment(String stripeSessionId, PaymentStatus paymentStatus, List<CheckoutProduct> checkoutProducts, String customerName, String customerEmail) {
        this.stripeSessionId = stripeSessionId;
        this.paymentStatus = paymentStatus;
        this.checkoutProducts = checkoutProducts;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStripeSessionId() {
        return stripeSessionId;
    }

    public void setStripeSessionId(String stripeSessionId) {
        this.stripeSessionId = stripeSessionId;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public List<CheckoutProduct> getCheckoutProducts() {
        return checkoutProducts;
    }

    public void setCheckoutProducts(List<CheckoutProduct> checkoutProducts) {
        this.checkoutProducts = checkoutProducts;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id='" + id + '\'' +
                ", stripeSessionId='" + stripeSessionId + '\'' +
                ", paymentStatus=" + paymentStatus +
                ", checkoutProducts=" + checkoutProducts +
                ", customerName='" + customerName + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                '}';
    }
}
