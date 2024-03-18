package com.trentonrush.granitesolutions.order.controller;

import com.trentonrush.granitesolutions.order.model.Order;
import com.trentonrush.granitesolutions.order.model.OrderRequest;
import com.trentonrush.granitesolutions.order.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping()
    public void createOrder(Mono<OrderRequest> orderRequest){
    }
}
