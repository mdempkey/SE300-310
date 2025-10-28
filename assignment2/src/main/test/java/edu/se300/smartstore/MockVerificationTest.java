package edu.se300.smartstore;

import edu.se300.smartstore.model.Inventory;
import edu.se300.smartstore.model.Product;
import edu.se300.smartstore.service.StoreService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import static org.mockito.Mockito.*;

public class MockVerificationTest {

    @Test
    void mockVerificationTest() {
        Inventory inv = mock(Inventory.class);
        StoreService store = new StoreService(inv);

        Product product = new Product("SKU-001", "Widget", 9.99);
        store.registerProduct(product);

        verify(inv, times(1)).addProduct(ArgumentMatchers.any());
    }
}
