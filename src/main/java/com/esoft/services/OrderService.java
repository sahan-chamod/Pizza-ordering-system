package com.esoft.services;

import com.esoft.models.Order;
import com.esoft.models.Pizza;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderService {
    private List<Order> orders = new ArrayList<>();

    public void createOrder(@NotNull Order order) {
        Pizza pizza = order.getPizza();
        BigDecimal price = pizza.getTotalPrice();
        String Toppings = order.getToppings().stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));

        String[] orders = {String.valueOf(pizza.getPizzaId()), pizza.getName(), String.valueOf(price), Toppings};

    }

    public Map<String, Order> getAllOrders() {
        Map<String, Order> orders = new HashMap<>();
        return orders;
    }

    public void canselOrder(Order order) {

    }
}
