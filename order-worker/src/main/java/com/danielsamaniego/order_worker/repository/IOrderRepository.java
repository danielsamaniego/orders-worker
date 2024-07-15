package com.danielsamaniego.order_worker.repository;

import com.danielsamaniego.order_worker.domain.Order;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderRepository extends ReactiveMongoRepository<Order, String> {
}