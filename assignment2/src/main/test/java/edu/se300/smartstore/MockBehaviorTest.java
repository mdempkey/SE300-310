package edu.se300.smartstore;

import edu.se300.smartstore.model.Inventory;
import edu.se300.smartstore.service.StoreService;
import org.junit.jupiter.api.Test;
import edu.se300.smartstore.model.Product;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MockBehaviorTest {

    @Test
    void mockBehaviorTest() {
        Inventory inv = mock(Inventory.class);
        when(inv.getStock("SKU-001")).thenReturn(42);

        StoreService store = new StoreService(inv);
        int stock = store.currentStock("SKU-001");

        assertEquals(42, stock);
    }
}
