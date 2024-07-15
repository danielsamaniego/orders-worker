package com.danielsamaniego.order_worker.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class OrderTest {

    @Test
    public void orderShouldHaveCorrectAttributes() {
        Order order = new Order();
        order.setId("1");
        order.setProductId("P123");
        order.setCustomerIdentification("C456");
        order.setProductName("Product Name");
        order.setCustomerName("Customer Name");
        order.setSubtotal(1000.0);
        order.setQuantity(10);
        order.setPrice(100.0);
        order.setStatus("Pending");

        assertThat(order.getId()).isEqualTo("1");
        assertThat(order.getProductId()).isEqualTo("P123");
        assertThat(order.getCustomerIdentification()).isEqualTo("C456");
        assertThat(order.getProductName()).isEqualTo("Product Name");
        assertThat(order.getCustomerName()).isEqualTo("Customer Name");
        assertThat(order.getSubtotal()).isEqualTo(1000.0);
        assertThat(order.getQuantity()).isEqualTo(10);
        assertThat(order.getPrice()).isEqualTo(100.0);
        assertThat(order.getStatus()).isEqualTo("Pending");
    }

    @Test
    public void orderEqualityShouldWork() {
        Order order1 = new Order();
        order1.setId("1");
        order1.setProductId("P123");
        order1.setCustomerIdentification("C456");
        order1.setProductName("Product Name");
        order1.setCustomerName("Customer Name");
        order1.setSubtotal(1000.0);
        order1.setQuantity(10);
        order1.setPrice(100.0);
        order1.setStatus("Pending");

        Order order2 = new Order();
        order2.setId("1");
        order2.setProductId("P123");
        order2.setCustomerIdentification("C456");
        order2.setProductName("Product Name");
        order2.setCustomerName("Customer Name");
        order2.setSubtotal(1000.0);
        order2.setQuantity(10);
        order2.setPrice(100.0);
        order2.setStatus("Pending");

        assertThat(order1).isEqualTo(order2);
    }

    @Test
    public void orderHashCodeShouldWork() {
        Order order1 = new Order();
        order1.setId("1");
        order1.setProductId("P123");
        order1.setCustomerIdentification("C456");
        order1.setProductName("Product Name");
        order1.setCustomerName("Customer Name");
        order1.setSubtotal(1000.0);
        order1.setQuantity(10);
        order1.setPrice(100.0);
        order1.setStatus("Pending");

        Order order2 = new Order();
        order2.setId("1");
        order2.setProductId("P123");
        order2.setCustomerIdentification("C456");
        order2.setProductName("Product Name");
        order2.setCustomerName("Customer Name");
        order2.setSubtotal(1000.0);
        order2.setQuantity(10);
        order2.setPrice(100.0);
        order2.setStatus("Pending");

        assertThat(order1.hashCode()).isEqualTo(order2.hashCode());
    }

    @Test
    public void orderToStringShouldWork() {
        Order order = new Order();
        order.setId("1");
        order.setProductId("P123");
        order.setCustomerIdentification("C456");
        order.setProductName("Product Name");
        order.setCustomerName("Customer Name");
        order.setSubtotal(1000.0);
        order.setQuantity(10);
        order.setPrice(100.0);
        order.setStatus("Pending");

        assertThat(order.toString()).contains("1", "P123", "C456", "Product Name", "Customer Name", "1000.0", "10", "100.0", "Pending");
    }
}
