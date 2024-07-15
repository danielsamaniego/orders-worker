package com.danielsamaniego.order_worker.domain;

import lombok.Data;

// Customer is a model class that represents a customer.
@Data
public class Customer {
    // id The id of the customer.
    private String id;
    // identificationNumber The id of the customer.
    private String identificationNumber;
    // name The name of the customer.
    private String name;
    // email The email of the customer.
    private String email;
}
