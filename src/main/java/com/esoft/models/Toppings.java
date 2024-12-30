package com.esoft.models;

import java.math.BigDecimal;

public class Toppings {
    private int id;
    private String topping;
    private BigDecimal price;

    public Toppings(int i, String toppingName, BigDecimal toppingPrice) {
        this.id = i;
        this.topping = toppingName;
        this.price = toppingPrice;
    }

    public Toppings() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTopping() {
        return topping;
    }

    public void setTopping(String topping) {
        this.topping = topping;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
