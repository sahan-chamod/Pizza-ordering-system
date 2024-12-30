package com.esoft.models;

import java.util.List;

public class Order {
    private Pizza pizza;
    private String status;
    private List<String> toppings;
    private Integer count;
    private String size;

    // Updated constructor to include count and size
    public Order(Pizza pizza, String status, List<String> toppings, Integer count, String size) {
        this.pizza = pizza;
        this.status = status;
        this.toppings = toppings;
        this.count = count;
        this.size = size;
    }

    // Getters and setters
    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getToppings() {
        return toppings;
    }

    public void setToppings(List<String> toppings) {
        this.toppings = toppings;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Order{" +
                "pizza=" + pizza +
                ", status='" + status + '\'' +
                ", toppings=" + toppings +
                ", count=" + count +
                ", size='" + size + '\'' +
                '}';
    }
}
