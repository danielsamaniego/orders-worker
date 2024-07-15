package com.danielsamaniego.order_worker.domain;

import lombok.Data;

// Product is a model class that represents a product.
@Data
public class Product {
    // The id of the product.
    private String id;

    // The name of the product.
    private String name;

    // The description of the product.
    private String description;

    // The price of the product.
    private double price;
}