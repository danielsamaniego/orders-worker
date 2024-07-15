package com.danielsamaniego.order_worker.consumer;

import com.danielsamaniego.order_worker.domain.Order;
import com.danielsamaniego.order_worker.service.IOrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.test.context.EmbeddedKafka;
import reactor.core.publisher.Mono;
import org.slf4j.event.Level;
import com.github.valfirst.slf4jtest.TestLogger;
import com.github.valfirst.slf4jtest.TestLoggerFactory;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@EmbeddedKafka
public class OrderConsumerTest {

    @Mock
    private IOrderService orderService;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private OrderConsumer orderConsumer;

    private Order order;
    private String message;

    private final TestLogger logger = TestLoggerFactory.getTestLogger(OrderConsumer.class);

    @BeforeEach
    public void setup() throws Exception {
        MockitoAnnotations.openMocks(this);
        order = new Order();
        order.setId("1");
        order.setProductId("P123");
        order.setCustomerIdentification("C456");
        order.setProductName("Product Name");
        order.setCustomerName("Customer Name");
        order.setQuantity(10);
        order.setPrice(100.0);
        order.setSubtotal(1000.0);
        order.setStatus("Pending");

        message = new ObjectMapper().writeValueAsString(order);
    }

    @AfterEach
    public void tearDown() {
        TestLoggerFactory.clear();
    }

    @Test
    public void shouldConsumeOrderWhenValidMessage() throws Exception {
        when(objectMapper.readValue(message, Order.class)).thenReturn(order);
        when(orderService.processOrder(any(Order.class))).thenReturn(Mono.just(order));

        orderConsumer.consumeOrder(message);

        verify(orderService, times(1)).processOrder(any(Order.class));
    }

    @Test
    public void shouldLogErrorWhenExceptionThrown() throws Exception {
        when(objectMapper.readValue(message, Order.class)).thenThrow(new RuntimeException("Invalid message"));

        orderConsumer.consumeOrder(message);

        verify(orderService, never()).processOrder(any(Order.class));
        assert logger.getLoggingEvents().stream()
                .anyMatch(event -> event.getLevel().equals(Level.ERROR) && event.getMessage().contains("Error processing order message"));
    }
}
