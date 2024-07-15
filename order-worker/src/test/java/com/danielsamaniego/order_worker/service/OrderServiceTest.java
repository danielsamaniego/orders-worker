package com.danielsamaniego.order_worker.service;

import com.danielsamaniego.order_worker.domain.Order;
import com.danielsamaniego.order_worker.domain.Product;
import com.danielsamaniego.order_worker.domain.Customer;
import com.danielsamaniego.order_worker.repository.IOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class OrderServiceTest {

    @Mock
    private IOrderRepository orderRepository;

    @Mock
    private IProductService productService;

    @Mock
    private ICustomerService customerService;

    @InjectMocks
    private OrderService orderService;

    private Order order;
    private Product product;
    private Customer customer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        order = new Order();
        order.setId("1");
        order.setProductId("P123");
        order.setCustomerIdentification("C456");
        order.setQuantity(10);

        product = new Product();
        product.setId("P123");
        product.setName("Test Product");
        product.setPrice(100.0);

        customer = new Customer();
        customer.setId("C456");
        customer.setName("Test Customer");
    }

    @Test
    public void shouldProcessOrderSuccessfully() {
        when(productService.getProductById("P123")).thenReturn(Mono.just(product));
        when(customerService.getCustomerByIdentification("C456")).thenReturn(Mono.just(customer));
        when(orderRepository.save(any(Order.class))).thenReturn(Mono.just(order));

        Mono<Order> processedOrder = orderService.processOrder(order);

        StepVerifier.create(processedOrder)
                .expectNextMatches(savedOrder ->
                        savedOrder.getProductName().equals("Test Product") &&
                                savedOrder.getCustomerName().equals("Test Customer") &&
                                savedOrder.getSubtotal() == 1000.0 &&
                                savedOrder.getStatus().equals("PROCESSED")
                )
                .verifyComplete();
    }

    @Test
    public void shouldHandleProductServiceError() {
        when(productService.getProductById("P123")).thenReturn(Mono.error(new RuntimeException("Product service error")));
        when(customerService.getCustomerByIdentification("C456")).thenReturn(Mono.just(customer));

        Mono<Order> processedOrder = orderService.processOrder(order);

        StepVerifier.create(processedOrder)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Product service error"))
                .verify();
    }

    @Test
    public void shouldHandleCustomerServiceError() {
        when(productService.getProductById("P123")).thenReturn(Mono.just(product));
        when(customerService.getCustomerByIdentification("C456")).thenReturn(Mono.error(new RuntimeException("Customer service error")));

        Mono<Order> processedOrder = orderService.processOrder(order);

        StepVerifier.create(processedOrder)
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException &&
                        throwable.getMessage().equals("Customer service error"))
                .verify();
    }
}
