package com.danielsamaniego.order_worker.service;

import com.danielsamaniego.order_worker.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class ProductServiceTest {

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private ProductService productService;

    private Product product;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        product = new Product();
        product.setId("P123");
        product.setName("Test Product");
    }

    @Test
    public void shouldReturnProductWhenValidId() {
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Product.class)).thenReturn(Mono.just(product));

        Mono<Product> productMono = productService.getProductById("P123");

        StepVerifier.create(productMono)
                .expectNextMatches(prod -> prod.getId().equals("P123") && prod.getName().equals("Test Product"))
                .verifyComplete();
    }

    @Test
    public void shouldReturnEmptyWhenProductNotFound() {
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Product.class)).thenReturn(Mono.error(new WebClientResponseException(404, "Not Found", null, null, null)));

        Mono<Product> productMono = productService.getProductById("P123");

        StepVerifier.create(productMono)
                .expectError(WebClientResponseException.class)
                .verify();
    }
}
