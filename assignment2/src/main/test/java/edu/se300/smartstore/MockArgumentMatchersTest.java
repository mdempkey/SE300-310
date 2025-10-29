package edu.se300.smartstore;

import edu.se300.smartstore.model.Inventory;
import edu.se300.smartstore.model.Product;
import edu.se300.smartstore.service.StoreService;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatchers;

import static org.mockito.Mockito.*;

public class MockArgumentMatchersTest {

    @Test
    void mockArgumentMatchersTest() {
        Inventory mockInventory = mock(Inventory.class);
        StoreService store = new StoreService(mockInventory);

        store.currentStock("SKU-123");

        verify(mockInventory).getStock(ArgumentMatchers.eq("SKU-123"));
    }
}