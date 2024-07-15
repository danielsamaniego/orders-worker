package com.danielsamaniego.order_worker.consumer;

import com.danielsamaniego.order_worker.domain.Order;
import com.danielsamaniego.order_worker.service.IOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import reactor.core.scheduler.Schedulers;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderConsumer {

    private final IOrderService orderService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "order-topic", groupId = "order-worker-group")
    public void consumeOrder(String message) {
        try {
            Order order = objectMapper.readValue(message, Order.class);
            orderService.processOrder(order)
                    .subscribeOn(Schedulers.boundedElastic())
                    .subscribe(
                            savedOrder -> log.info("Order saved: {}", savedOrder),
                            error -> log.error("Error saving order", error)
                    );
        } catch (Exception e) {
            log.error("Error processing order message", e);
        }
    }
}