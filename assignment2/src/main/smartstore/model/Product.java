package edu.se300.smartstore.model;

import java.util.Objects;

public class Product {
    private final String sku;
    private final String name;
    private final double price;

    public Product(String sku, String name, double price) {
        if (sku == null || sku.isBlank()) throw new IllegalArgumentException("SKU required");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name required");
        if (price < 0) throw new IllegalArgumentException("Price must be non-negative");
        this.sku = sku;
        this.name = name;
        this.price = price;
    }

    public String getSku() { return sku; }
    public String getName() { return name; }
    public double getPrice() { return price; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Double.compare(product.price, price) == 0 &&
               sku.equals(product.sku) &&
               name.equals(product.name);
    }

    @Override
    public int hashCode() { return Objects.hash(sku, name, price); }

    @Override
    public String toString() {
        return "Product{" + "sku='" + sku + '\'' + ", name='" + name + '\'' + ", price=" + price + '}';
    }
}
