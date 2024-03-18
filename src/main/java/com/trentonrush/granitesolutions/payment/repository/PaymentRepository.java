package com.trentonrush.granitesolutions.payment.repository;

import com.trentonrush.granitesolutions.payment.model.Payment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends ReactiveMongoRepository<Payment, String> {

}
