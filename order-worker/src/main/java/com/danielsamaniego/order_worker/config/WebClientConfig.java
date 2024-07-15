package com.danielsamaniego.order_worker.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${api.product.base-url}")
    private String productsBaseUrl;

    @Value("${api.product.port}")
    private int productsPort;

    @Value("${api.customer.base-url}")
    private String customerBaseurl;

    @Value("${api.customer.port}")
    private String customerPort;

    @Bean
    public WebClient productsWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.baseUrl(productsBaseUrl + ":" + productsPort).build();
    }

    @Bean
    public WebClient customersWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.baseUrl(customerBaseurl + ":" + customerPort).build();
    }
}