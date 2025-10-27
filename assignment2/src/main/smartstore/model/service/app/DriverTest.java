package edu.se300.smartstore.app;

import edu.se300.smartstore.model.Product;
import edu.se300.smartstore.model.Inventory;
import edu.se300.smartstore.service.LedgerParser;
import edu.se300.smartstore.service.StoreService;

import java.io.InputStream;

public class DriverTest {
    public static void main(String[] args) throws Exception {
        Inventory inventory = new Inventory();
        StoreService store = new StoreService(inventory);

        // Register sample products (SKUs referenced in ledger.script)
        store.registerProduct(new Product("SKU-001", "Widget", 9.99));
        store.registerProduct(new Product("SKU-002", "Gadget", 19.49));
        store.registerProduct(new Product("SKU-003", "Thingamajig", 5.00));

        InputStream ledgerStream = DriverTest.class.getResourceAsStream("/ledger.script");
        if (ledgerStream == null) throw new IllegalStateException("ledger.script not found");

        var parser = new LedgerParser();
        var entries = parser.parse(ledgerStream);
        store.applyLedger(entries);

        // Print identical results to this CLI
        for (var p : inventory.allProducts()) {
            System.out.println(p.getSku() + " " + p.getName() + " stock=" + inventory.getStock(p.getSku()));
        }
    }
}
