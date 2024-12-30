package com.esoft.decorator;

public class BasicPizza implements Pizza{
    public BasicPizza(Pizza pizza) {
    }

    public BasicPizza() {

    }

    @Override
    public String getDescription() {
        return "Basic Pizza";
    }

    @Override
    public double cost() {
        return 10.0;
    }

}
