package edu.se300.smartstore;

import edu.se300.smartstore.model.Inventory;
import edu.se300.smartstore.model.Product;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AdvancedAssertionsTest {

    @Test
    void advancedAssertionsTest() {
        Inventory inv = new Inventory();
        inv.addProduct(new Product("SKU-001", "Widget", 9.99));
        inv.addProduct(new Product("SKU-002", "Gadget", 14.99));
        inv.updateStock("SKU-001", 10);
        inv.updateStock("SKU-002", 5);

        assertAll("Inventory checks",
            () -> assertEquals(15, inv.getStock("SKU-001") + inv.getStock("SKU-002")),
            () -> assertTrue(inv.hasProduct("SKU-001")),
            () -> assertThrows(IllegalArgumentException.class, () -> inv.getStock("SKU-999"))
        );
    }
}