package com.esoft.models;

import java.math.BigDecimal;

public class Pizza {
    private int pizzaId;
    private String name;
    private String description;
    private BigDecimal basePrice;
    private String imageUrl;
    private BigDecimal totalPrice;

    // Default constructor
    public Pizza() {
    }

    // Parameterized constructor (private to enforce Builder usage)
    private Pizza(Builder builder) {
        this.pizzaId = builder.pizzaId;
        this.name = builder.name;
        this.description = builder.description;
        this.basePrice = builder.basePrice;
        this.imageUrl = builder.imageUrl;
        this.totalPrice = builder.totalPrice;
    }

    // Getters and Setters
    public int getPizzaId() {
        return pizzaId;
    }

    public void setPizzaId(int pizzaId) {
        this.pizzaId = pizzaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    // toString method for easy debugging
    @Override
    public String toString() {
        return "Pizza{" +
                "pizzaId=" + pizzaId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", basePrice=" + basePrice +
                ", imageUrl='" + imageUrl + '\'' +
                ", totalPrice=" + totalPrice +
                '}';
    }

    // Builder Class
    public static class Builder {
        private int pizzaId;
        private String name;
        private String description;
        private BigDecimal basePrice;
        private String imageUrl;
        private BigDecimal totalPrice;

        public Builder setPizzaId(int pizzaId) {
            this.pizzaId = pizzaId;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setBasePrice(BigDecimal basePrice) {
            this.basePrice = basePrice;
            return this;
        }

        public Builder setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder setTotalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public Pizza build() {
            return new Pizza(this);
        }
    }
}
