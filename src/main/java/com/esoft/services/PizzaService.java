package com.esoft.services;

import com.esoft.config.DBConnection;
import com.esoft.models.Pizza;
import com.esoft.models.Size;
import com.esoft.models.Toppings;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PizzaService {
    private Map<Integer, Pizza> pizzaMap = new HashMap<>();
    private Map<Integer, Toppings> toppingsMap = new HashMap<>();
    private Map<Integer, Size> sizeMap = new HashMap<>();

    public Map<Integer, Pizza> getAllPizza() throws SQLException {
        pizzaMap.clear(); // Clear map before populating
        ResultSet rs = DBConnection.executeSearch("SELECT * FROM Pizzas");

        while (rs.next()) {
            Pizza pizza = new Pizza();
            pizza.setPizzaId(rs.getInt("pizza_id"));
            pizza.setName(rs.getString("name"));
            pizza.setDescription(rs.getString("description"));
            pizza.setBasePrice(rs.getBigDecimal("base_price"));
            pizza.setImageUrl(rs.getString("image_url"));
            pizzaMap.put(pizza.getPizzaId(), pizza); // Store in map
        }
        DBConnection.closeConnectio();
        return pizzaMap;
    }

    public Map<Integer, Toppings> getAllToppings() throws SQLException {
        toppingsMap.clear(); // Clear map before populating
        ResultSet rs = DBConnection.executeSearch("SELECT * FROM Toppings");

        while (rs.next()) {
            Toppings toppings1 = new Toppings();
            toppings1.setId(rs.getInt("topping_id"));
            toppings1.setTopping(rs.getString("name"));
            toppings1.setPrice(BigDecimal.valueOf(rs.getDouble("price")));
            toppingsMap.put(toppings1.getId(), toppings1); // Store in map
        }
        DBConnection.closeConnectio();
        return toppingsMap;
    }

    public Map<Integer, Size> getAllSize() throws SQLException {
        sizeMap.clear(); // Clear map before populating
        ResultSet rs = DBConnection.executeSearch("SELECT * FROM sizes");

        while (rs.next()) {
            Size size = new Size();
            size.setId(rs.getInt("size_id"));
            size.setSize(rs.getString("size"));
            size.setPrice(rs.getBigDecimal("price"));
            sizeMap.put(size.getId(), size); // Store in map
        }
        DBConnection.closeConnectio();
        return sizeMap;
    }

    public Pizza getPizzaById(int pizzaId) {
        return pizzaMap.get(pizzaId); // Retrieve a specific Pizza
    }

    public Toppings getToppingById(int toppingId) {
        return toppingsMap.get(toppingId); // Retrieve a specific Topping
    }

    public Size getSizeById(int sizeId) {
        return sizeMap.get(sizeId); // Retrieve a specific Size
    }

    public void addPizza(Pizza pizza) throws SQLException {
        // Validate the input Pizza object
        if (pizza == null) {
            throw new IllegalArgumentException("Pizza object cannot be null.");
        }

        String query = "INSERT INTO Pizzas (name, description, base_price, image_url) VALUES (?, ?, ?, ?)";

        String [] pizzas = {pizza.getName(),pizza.getDescription(),String.valueOf(pizza.getBasePrice()),pizza.getImageUrl()};

        try {
            int rowsAffected = DBConnection.executeUpdate(query,pizzas);

            if (rowsAffected > 0) {
                System.out.println("Pizza added successfully!");
            } else {
                System.out.println("Failed to add pizza.");
            }
        } finally {
            DBConnection.closeConnectio();
        }
    }

}
