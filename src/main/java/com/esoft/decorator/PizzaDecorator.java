package com.esoft.decorator;

// Abstract Decorator
public abstract class PizzaDecorator implements Pizza {
    protected Pizza pizza; // Wrapped Pizza object

    public PizzaDecorator(Pizza pizza) {
        this.pizza = pizza;
    }

    @Override
    public String getDescription() {
        return pizza.getDescription(); // Delegate to the wrapped object
    }

    @Override
    public double cost() {
        return pizza.cost(); // Delegate to the wrapped object
    }
}
