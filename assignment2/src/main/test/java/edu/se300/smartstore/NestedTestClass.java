package edu.se300.smartstore;

import edu.se300.smartstore.model.Product;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import edu.se300.smartstore.model.Inventory;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class NestedTestClass {

    @Nested
    class ProductInitializationTests {

        @Test
        void validProductCreation() {
            Product product = new Product("SKU-001", "Widget", 9.99);
            assertEquals("SKU-001", product.getSku());
            assertEquals("Widget", product.getName());
            assertEquals(9.99, product.getPrice());
        }

        @Test
        void priceShouldBePositive() {
            Product product = new Product("SKU-002", "Gadget", 14.99);
            assertTrue(product.getPrice() > 0);
        }
    }

    @Nested
    class ProductEdgeCaseTests {

        @Test
        void emptyNameShouldBeAllowed() {
            Product product = new Product("SKU-003", "", 5.00);
            assertEquals("", product.getName());
        }

        @Test
        void zeroPriceShouldBeAllowed() {
            Product product = new Product("SKU-004", "Freebie", 0.00);
            assertEquals(0.00, product.getPrice());
        }
    }
}
