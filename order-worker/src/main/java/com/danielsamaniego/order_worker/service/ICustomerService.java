package com.danielsamaniego.order_worker.service;

import com.danielsamaniego.order_worker.domain.Customer;
import reactor.core.publisher.Mono;

public interface ICustomerService {
    Mono<Customer> getCustomerByIdentification(String customerIdentification);
}