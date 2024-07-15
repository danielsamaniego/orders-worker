package com.danielsamaniego.order_worker.service;

import com.danielsamaniego.order_worker.domain.Product;
import reactor.core.publisher.Mono;

public interface IProductService {
    Mono<Product> getProductById(String productId);
}