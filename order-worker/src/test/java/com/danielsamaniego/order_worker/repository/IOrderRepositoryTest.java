package com.danielsamaniego.order_worker.repository;

import com.danielsamaniego.order_worker.domain.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class IOrderRepositoryTest {

    @Mock
    private IOrderRepository orderRepository;

    private Order order;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        order = new Order();
        order.setId("1");
        order.setProductId("P123");
        order.setCustomerIdentification("C456");
        order.setProductName("Product Name");
        order.setCustomerName("Customer Name");
        order.setQuantity(10);
        order.setStatus("Pending");
    }

    @Test
    public void shouldSaveOrderWhenValidOrder() {
        when(orderRepository.save(any(Order.class))).thenReturn(Mono.just(order));

        Mono<Order> savedOrder = orderRepository.save(order);

        StepVerifier.create(savedOrder)
                .expectNextMatches(saved ->
                        saved.getId().equals("1") &&
                                saved.getProductId().equals("P123") &&
                                saved.getCustomerIdentification().equals("C456") &&
                                saved.getProductName().equals("Product Name") &&
                                saved.getCustomerName().equals("Customer Name") &&
                                saved.getQuantity() == 10 &&
                                saved.getStatus().equals("Pending")
                )
                .verifyComplete();
    }

    @Test
    public void shouldFindOrderByIdWhenOrderExists() {
        when(orderRepository.findById(anyString())).thenReturn(Mono.just(order));

        Mono<Order> retrievedOrder = orderRepository.findById("1");

        StepVerifier.create(retrievedOrder)
                .expectNextMatches(found ->
                        found.getId().equals("1") &&
                                found.getProductId().equals("P123") &&
                                found.getCustomerIdentification().equals("C456") &&
                                found.getProductName().equals("Product Name") &&
                                found.getCustomerName().equals("Customer Name") &&
                                found.getQuantity() == 10 &&
                                found.getStatus().equals("Pending")
                )
                .verifyComplete();
    }

    @Test
    public void shouldDeleteOrderWhenOrderExists() {
        when(orderRepository.deleteById(anyString())).thenReturn(Mono.empty());

        Mono<Void> deleteResult = orderRepository.deleteById("1");

        StepVerifier.create(deleteResult)
                .verifyComplete();
    }
}
