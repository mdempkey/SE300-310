package edu.se300.smartstore;

import edu.se300.smartstore.model.Inventory;
import edu.se300.smartstore.model.Product;
import edu.se300.smartstore.service.StoreService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MockitoTests {

    @Test
    void mockBehaviorTest() {
        Inventory inv = mock(Inventory.class);
        when(inv.getStock("SKU-001")).thenReturn(42);
        StoreService store = new StoreService(inv);
        assertEquals(42, store.currentStock("SKU-001"));
    }

    @Test
    void mockVerificationTest() {
        Inventory inv = mock(Inventory.class);
        StoreService store = new StoreService(inv);
        store.registerProduct(new Product("SKU-001", "Widget", 9.99));
        verify(inv, times(1)).addProduct(ArgumentMatchers.any());
    }

    @Test
    void mockArgumentMatchersTest() {
        Inventory inv = mock(Inventory.class);
        StoreService store = new StoreService(inv);
        store.currentStock("SKU-ABC");
        verify(inv).getStock(ArgumentMatchers.eq("SKU-ABC"));
    }
}
