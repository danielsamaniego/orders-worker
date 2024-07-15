package com.danielsamaniego.order_worker.service;

import com.danielsamaniego.order_worker.domain.Product;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ProductService implements IProductService {
    private final WebClient webClient;

    public ProductService(@Qualifier("productsWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<Product> getProductById(String productId) {
        return this.webClient.get()
                .uri("product/{id}", productId)
                .retrieve()
                .bodyToMono(Product.class);
    }
}