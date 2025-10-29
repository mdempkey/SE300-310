package edu.se300.smartstore;

import edu.se300.smartstore.model.Inventory;
import edu.se300.smartstore.model.Product;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestOrderingExample {

    static Inventory inventory;

    @BeforeAll
    static void setup() {
        inventory = new Inventory();
        inventory.addProduct(new Product("SKU-001", "Widget", 9.99));
    }

    @Test
    @Order(1)
    void testAddStock() {
        inventory.addStock("SKU-001", 5);
        assertEquals(5, inventory.getStock("SKU-001"));
    }

    @Test
    @Order(2)
    void testRemoveStock() {
        inventory.removeStock("SKU-001", 2);
        assertEquals(3, inventory.getStock("SKU-001"));
    }
}
