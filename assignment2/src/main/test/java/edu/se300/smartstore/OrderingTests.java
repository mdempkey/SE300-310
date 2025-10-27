package edu.se300.smartstore;

import edu.se300.smartstore.model.Inventory;
import edu.se300.smartstore.model.Product;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class OrderingTests {

    static Inventory inv;

    @BeforeAll
    static void setupAll() {
        inv = new Inventory();
        inv.addProduct(new Product("SKU-001", "Widget", 9.99));
    }

    @Test
    void methodOrderTest_A_add() {
        inv.addStock("SKU-001", 2);
        assertEquals(2, inv.getStock("SKU-001"));
    }

    @Test
    void methodOrderTest_B_remove() {
        inv.removeStock("SKU-001", 1);
        assertEquals(1, inv.getStock("SKU-001"));
    }
}
