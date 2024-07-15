package com.danielsamaniego.order_worker.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerTest {

    @Test
    public void customerShouldHaveCorrectAttributes() {
        Customer customer = new Customer();
        customer.setId("123");
        customer.setIdentificationNumber("ID456");
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");

        assertThat(customer.getId()).isEqualTo("123");
        assertThat(customer.getIdentificationNumber()).isEqualTo("ID456");
        assertThat(customer.getName()).isEqualTo("John Doe");
        assertThat(customer.getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    public void customerEqualityShouldWork() {
        Customer customer1 = new Customer();
        customer1.setId("123");
        customer1.setIdentificationNumber("ID456");
        customer1.setName("John Doe");
        customer1.setEmail("john.doe@example.com");

        Customer customer2 = new Customer();
        customer2.setId("123");
        customer2.setIdentificationNumber("ID456");
        customer2.setName("John Doe");
        customer2.setEmail("john.doe@example.com");

        assertThat(customer1).isEqualTo(customer2);
    }

    @Test
    public void customerHashCodeShouldWork() {
        Customer customer1 = new Customer();
        customer1.setId("123");
        customer1.setIdentificationNumber("ID456");
        customer1.setName("John Doe");
        customer1.setEmail("john.doe@example.com");

        Customer customer2 = new Customer();
        customer2.setId("123");
        customer2.setIdentificationNumber("ID456");
        customer2.setName("John Doe");
        customer2.setEmail("john.doe@example.com");

        assertThat(customer1.hashCode()).isEqualTo(customer2.hashCode());
    }

    @Test
    public void customerToStringShouldWork() {
        Customer customer = new Customer();
        customer.setId("123");
        customer.setIdentificationNumber("ID456");
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");

        assertThat(customer.toString()).contains("123", "ID456", "John Doe", "john.doe@example.com");
    }
}
