package com.esoft.builder;

import com.esoft.models.Pizza;
import com.esoft.models.Size;
import com.esoft.models.Toppings;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.esoft.Main.sizeMap;
import static com.esoft.Main.toppingsMap;

public class PizzaBuilder {
    private int selectedSizeId; // Add this field to the PizzaBuilder class
    private Pizza pizza;
    private Set<Integer> selectedToppings;
    private Size selectedSize;
    private int quantity;

    // Constructor with Pizza object
    public PizzaBuilder(Pizza pizza) {
        this.pizza = pizza != null ? pizza : new Pizza(); // Ensure pizza is not null
        this.selectedToppings = new HashSet<>();
    }

    // Method to add a topping to the pizza
    public PizzaBuilder addTopping(int toppingId) {
        if (toppingsMap.containsKey(toppingId) && !selectedToppings.contains(toppingId)) {
            selectedToppings.add(toppingId);
        } else {
            System.out.println("Invalid or already selected Topping ID: " + toppingId);
        }
        return this;
    }

    // Method to select a pizza size
    public PizzaBuilder selectSize(int sizeId) {
        this.selectedSizeId = sizeId;
        if (sizeMap.containsKey(sizeId)) {
            selectedSize = sizeMap.get(sizeId);
        } else {
            System.out.println("Invalid Size ID!");
        }
        return this;
    }

    // Method to set the quantity of the pizza
    public PizzaBuilder setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    // Method to build the final pizza object and return the total price
    public BigDecimal build() {
        if (selectedSize == null) {
            throw new IllegalStateException("Pizza size must be selected.");
        }

        BigDecimal price = pizza.getBasePrice(); // Start with the base price

        // Add toppings price
        for (int toppingId : selectedToppings) {
            Toppings topping = toppingsMap.get(toppingId);
            if (topping != null) {
                price = price.add(topping.getPrice());
            }
        }

        // Add size adjustment price
        BigDecimal sizeAdjustment = selectedSize.getPrice();
        price = price.add(sizeAdjustment);

        // Multiply by quantity
        BigDecimal totalPrice = price.multiply(BigDecimal.valueOf(quantity));

        pizza.setBasePrice(totalPrice); // Update pizza price
        return totalPrice;
    }

    // Get the selected size name
    public String getSelectedSize() {
        return selectedSize != null ? selectedSize.getSize() : "Default";
    }

    // Get the names of the selected toppings
    public List<String> getSelectedToppings() {
        List<String> toppingNames = new ArrayList<>();
        for (int toppingId : selectedToppings) {
            Toppings topping = toppingsMap.get(toppingId);
            if (topping != null) {
                toppingNames.add(topping.getTopping());
            }
        }
        return toppingNames;
    }

    // Get the quantity of pizzas
    public int getQuantity() {
        return quantity;
    }

    public void reset() {
        this.selectedSize = null;
        this.quantity = 0;
        this.selectedToppings.clear();
    }

}
