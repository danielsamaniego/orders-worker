package com.danielsamaniego.order_worker.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// Order is a model class that represents an order
@Data
@Document
public class Order {
    // id is the primary key of the order
    @Id
    private String id;
    // productId is the identifier of the product
    private String productId;
    // customerId is the identifier of the customer
    private String customerIdentification;
    // productName is the name of the product
    private String productName;
    // customerName is the name of the customer
    private String customerName;
    // subtotal is the total price of the order
    private double subtotal;
    // quantity is the number of units of the product
    private int quantity;
    // price by unit of the product
    private double price;
    // status is the status of the order
    private String status;
}
