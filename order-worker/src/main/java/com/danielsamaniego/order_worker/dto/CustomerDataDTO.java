package com.danielsamaniego.order_worker.dto;

import com.danielsamaniego.order_worker.domain.Customer;
import lombok.Data;

@Data
public class CustomerDataDTO {
    private Customer customerByIdentificationNumber;
}