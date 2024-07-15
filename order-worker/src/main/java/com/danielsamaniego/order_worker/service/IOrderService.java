package com.danielsamaniego.order_worker.service;

import com.danielsamaniego.order_worker.domain.Order;
import reactor.core.publisher.Mono;

public interface IOrderService {
    Mono<Order> processOrder(Order order);
}