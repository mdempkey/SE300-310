package edu.se300.smartstore.model;

import java.util.*;

public class Inventory {
    private final Map<String, Product> products = new HashMap<>();
    private final Map<String, Integer> stock = new HashMap<>();

    public void addProduct(Product p) {
        products.put(p.getSku(), p);
        stock.putIfAbsent(p.getSku(), 0);
    }

    public void addStock(String sku, int qty) {
        if (!products.containsKey(sku)) throw new IllegalArgumentException("Unknown SKU: " + sku);
        if (qty <= 0) throw new IllegalArgumentException("qty must be positive");
        stock.put(sku, stock.getOrDefault(sku, 0) + qty);
    }

    public void removeStock(String sku, int qty) {
        if (!products.containsKey(sku)) throw new IllegalArgumentException("Unknown SKU: " + sku);
        if (qty <= 0) throw new IllegalArgumentException("qty must be positive");
        int current = stock.getOrDefault(sku, 0);
        if (current < qty) throw new IllegalStateException("Insufficient stock for " + sku);
        stock.put(sku, current - qty);
    }

    public int getStock(String sku) { return stock.getOrDefault(sku, 0); }

    public Optional<Product> findProduct(String sku) { return Optional.ofNullable(products.get(sku)); }

    public Collection<Product> allProducts() { return Collections.unmodifiableCollection(products.values()); }
}
