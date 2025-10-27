package edu.se300.smartstore;


import static org.mockito.Mockito.*;

public class MockArgumentMatchersTest {

    @Test
    void mockArgumentMatchersTest() {
        Inventory mockInventory = mock(Inventory.class);
        StoreService store = new StoreService(mockInventory);

        store.currentStock("SKU-123");

        // Verify that getStock was called with "SKU-123"
        verify(mockInventory).getStock(ArgumentMatchers.eq("SKU-123"));
    }
}
