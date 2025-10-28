package edu.se300.smartstore;

import edu.se300.smartstore.model.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BasicAssertionsTest {

    @Test
    void basicAssertionsTest() {
        Product product = new Product("SKU-001", "Widget", 9.99);

        assertEquals("SKU-001", product.getSku());
        assertEquals("Widget", product.getName());
        assertEquals(9.99, product.getPrice());
        assertNotNull(product);
        assertTrue(product.getPrice() > 0);
    }
}
