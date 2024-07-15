package com.danielsamaniego.order_worker.service;

import com.danielsamaniego.order_worker.domain.Customer;
import com.danielsamaniego.order_worker.domain.Order;
import com.danielsamaniego.order_worker.domain.Product;
import com.danielsamaniego.order_worker.repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final IOrderRepository orderRepository;
    private final IProductService productService;
    private final ICustomerService customerService;


    public Mono<Order> processOrder(Order order) {
        return Mono.zip(
                productService.getProductById(order.getProductId()),
                customerService.getCustomerByIdentification(order.getCustomerIdentification())
        ).flatMap(tuple -> {
            Product product = tuple.getT1();
            Customer customer = tuple.getT2();
            order.setProductName(product.getName());
            order.setCustomerName(customer.getName());
            order.setSubtotal(product.getPrice() * order.getQuantity());
            order.setStatus("PROCESSED");
            return orderRepository.save(order);
        });
    }
}