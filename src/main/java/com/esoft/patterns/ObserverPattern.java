package com.esoft.patterns;

import com.esoft.models.Order;

import java.util.List;

public class ObserverPattern {
    private List<Order> orders;

    public ObserverPattern(List<Order> orders) {
        this.orders = orders;
    }

    public void displayOrders() {
        System.out.println("--- Orders ---");
        for (Order order : orders) {
            System.out.println(order);
        }
    }
}
