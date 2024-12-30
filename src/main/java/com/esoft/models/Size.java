package com.esoft.models;

import java.math.BigDecimal;

public class Size {
    private int id;
    private String size; // Change the field name to lowercase
    private BigDecimal price;

    // Getter for price
    public BigDecimal getPrice() {
        return price;
    }

    // Setter for price
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    // Getter for ID
    public int getId() {
        return id;
    }

    // Setter for ID
    public void setId(int id) {
        this.id = id;
    }

    // Getter for size
    public String getSize() {
        return size; // Use the updated field name
    }

    // Setter for size
    public void setSize(String size) {
        this.size = size; // Use the updated field name
    }
}
