package edu.se300.smartstore.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class LedgerEntry {
    public enum Type { ADD, REMOVE, SALE }

    private final LocalDateTime timestamp;
    private final Type type;
    private final String sku;
    private final int quantity;

    public LedgerEntry(LocalDateTime timestamp, Type type, String sku, int quantity) {
        if (timestamp == null) throw new IllegalArgumentException("timestamp required");
        if (type == null) throw new IllegalArgumentException("type required");
        if (sku == null || sku.isBlank()) throw new IllegalArgumentException("sku required");
        if (quantity <= 0) throw new IllegalArgumentException("quantity must be positive");
        this.timestamp = timestamp;
        this.type = type;
        this.sku = sku;
        this.quantity = quantity;
    }

    public LocalDateTime getTimestamp() { return timestamp; }
    public Type getType() { return type; }
    public String getSku() { return sku; }
    public int getQuantity() { return quantity; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LedgerEntry)) return false;
        LedgerEntry that = (LedgerEntry) o;
        return quantity == that.quantity &&
               timestamp.equals(that.timestamp) &&
               type == that.type &&
               sku.equals(that.sku);
    }

    @Override
    public int hashCode() { return Objects.hash(timestamp, type, sku, quantity); }

    @Override
    public String toString() {
        return "LedgerEntry{" + "timestamp=" + timestamp + ", type=" + type + ", sku='" + sku + '\'' + ", quantity=" + quantity + '}';
    }
}
