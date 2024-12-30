package com.esoft.services;

import com.esoft.builder.PizzaBuilder;
import com.esoft.models.Order;
import com.esoft.models.Pizza;
import com.esoft.models.Toppings;
import com.esoft.patterns.*;
import com.esoft.state.OrderState;
import com.esoft.state.OrderStateContext;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class AdminService {
    private List<Order> orders = new ArrayList<>();
    private Map<Integer, Pizza> pizzaMap = new HashMap<>();
    private Map<Integer, Toppings> toppingsMap = new HashMap<>();

    public void showOrders() {
        ObserverPattern observerPattern = new ObserverPattern(orders);
        observerPattern.displayOrders();
    }

    public void addPizza() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Pizza Name:");
        String name = scanner.nextLine();

        System.out.println("Enter Pizza Description:");
        String description = scanner.nextLine();

        System.out.println("Enter Pizza Base Price:");
        double basePrice = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character

        // Create a Pizza object using the Builder
        Pizza pizza = new Pizza.Builder()
                .setName(name)
                .setDescription(description)
                .setBasePrice(BigDecimal.valueOf(basePrice))
                .build();

        // Generate a unique ID for the new pizza
        int id = pizzaMap.size() + 1;
        pizza.setPizzaId(id); // Set the ID to the Pizza object

        // Store the Pizza in the map
        pizzaMap.put(id, pizza);
        System.out.println("Pizza added successfully: " + pizza);
    }


    public void addToppings() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Topping Name:");
        String toppingName = scanner.nextLine();

        System.out.println("Enter Topping Price:");
        BigDecimal toppingPrice = scanner.nextBigDecimal();

        Toppings topping = new Toppings(toppingsMap.size() + 1, toppingName, toppingPrice);
        toppingsMap.put(topping.getId(), topping);
        System.out.println("Topping added successfully: " + topping);
    }

    public void updateOrderState() {
        System.out.println("Order ID to update:");
        int orderId = new Scanner(System.in).nextInt();

        Order order = orders.stream()
                .filter(o -> o.getPizza().getPizzaId() == orderId)
                .findFirst()
                .orElse(null);

        if (order != null) {
            OrderStateContext context = new OrderStateContext(order);
            context.nextState();
        } else {
            System.out.println("Order not found.");
        }
    }
}
