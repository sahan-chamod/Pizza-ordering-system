package com.esoft.models;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Favourite {
    private String id;
    private String username;
    private Pizza pizza;
    List<Toppings> toppings;

    @Contract(pure = true)
    public Favourite(@NotNull Builder builder){
        this.id =builder.id;
        this.username = builder.username;
        this.toppings = builder.toppings;
        this.pizza = builder.pizza;
    }
    public List<Toppings> getToppings() {
        return toppings;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public String getUsername() {
        return username;
    }

    public String getId() {
        return id;
    }


    public static class Builder{
        private String id;
        private String username;
        private Pizza pizza;
        private List<Toppings> toppings;

        public Builder id(String id){
            this.id = id;
            return this;
        }

        public Builder username(String username){
            this.username = username;
            return this;
        }

        public Builder pizza(Pizza pizza){
            this.pizza = pizza;
            return this;
        }

        public Builder toppings(List<Toppings> toppings){
            this.toppings = toppings;
            return this;
        }
        public Favourite build(){
            return new Favourite(this);
        }
    }
}
