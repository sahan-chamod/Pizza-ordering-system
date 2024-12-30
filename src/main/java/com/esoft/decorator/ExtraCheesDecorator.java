package com.esoft.decorator;

// Concrete Decorator
public class ExtraCheesDecorator extends PizzaDecorator {

    public ExtraCheesDecorator(Pizza pizza) {
        super(pizza);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", Extra Cheese";
    }

    @Override
    public double cost() {
        return super.cost() + 2.0; // Add cost of extra cheese
    }
}
