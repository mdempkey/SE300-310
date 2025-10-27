package edu.se300.smartstore.service;

import edu.se300.smartstore.model.Inventory;
import edu.se300.smartstore.model.LedgerEntry;
import edu.se300.smartstore.model.Product;

import java.util.List;

public class StoreService {
    private final Inventory inventory;

    public StoreService(Inventory inventory) { this.inventory = inventory; }

    public void applyLedger(List<LedgerEntry> entries) {
        for (LedgerEntry e : entries) {
            switch (e.getType()) {
                case ADD -> inventory.addStock(e.getSku(), e.getQuantity());
                case REMOVE -> inventory.removeStock(e.getSku(), e.getQuantity());
                case SALE -> {
                    inventory.removeStock(e.getSku(), e.getQuantity());
                    // could log revenue, etc.
                }
                default -> throw new IllegalArgumentException("Unknown type: " + e.getType());
            }
        }
    }

    public void registerProduct(Product p) { inventory.addProduct(p); }

    public int currentStock(String sku) { return inventory.getStock(sku); }
}
