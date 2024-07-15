package com.danielsamaniego.order_worker.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductTest {

    @Test
    public void productShouldHaveCorrectAttributes() {
        Product product = new Product();
        product.setId("P123");
        product.setName("Test Product");
        product.setDescription("This is a test product.");
        product.setPrice(100.0);

        assertThat(product.getId()).isEqualTo("P123");
        assertThat(product.getName()).isEqualTo("Test Product");
        assertThat(product.getDescription()).isEqualTo("This is a test product.");
        assertThat(product.getPrice()).isEqualTo(100.0);
    }

    @Test
    public void productEqualityShouldWork() {
        Product product1 = new Product();
        product1.setId("P123");
        product1.setName("Test Product");
        product1.setDescription("This is a test product.");
        product1.setPrice(100.0);

        Product product2 = new Product();
        product2.setId("P123");
        product2.setName("Test Product");
        product2.setDescription("This is a test product.");
        product2.setPrice(100.0);

        assertThat(product1).isEqualTo(product2);
    }

    @Test
    public void productHashCodeShouldWork() {
        Product product1 = new Product();
        product1.setId("P123");
        product1.setName("Test Product");
        product1.setDescription("This is a test product.");
        product1.setPrice(100.0);

        Product product2 = new Product();
        product2.setId("P123");
        product2.setName("Test Product");
        product2.setDescription("This is a test product.");
        product2.setPrice(100.0);

        assertThat(product1.hashCode()).isEqualTo(product2.hashCode());
    }

    @Test
    public void productToStringShouldWork() {
        Product product = new Product();
        product.setId("P123");
        product.setName("Test Product");
        product.setDescription("This is a test product.");
        product.setPrice(100.0);

        assertThat(product.toString()).contains("P123", "Test Product", "This is a test product.", "100.0");
    }
}
