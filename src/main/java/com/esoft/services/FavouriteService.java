package com.esoft.services;

import com.esoft.builder.PizzaBuilder;
import com.esoft.config.DBConnection;
import com.esoft.models.Favourite;
import com.esoft.models.Pizza;
import com.esoft.models.Toppings;
import com.esoft.models.Users;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavouriteService {
    public boolean addFavourite(int userId, @NotNull Favourite favourite) {
        String query = "INSERT INTO `Favourites`(`favourite_id`,`user_id`,`pizza_id`) VALUES(?,?,?)";
        String[] params = {favourite.getId(), String.valueOf(userId), String.valueOf(favourite.getPizza().getPizzaId())};
        try {
            int rowsAffected = DBConnection.executeUpdate(query, params);
            DBConnection.closeConnectio();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.err.println("Error adding favorite: " + e.getMessage());
            return false;
        }
    }

    public Map<String, Favourite> getAllFavourite(@NotNull Users user) {
        Map<String, Favourite> favourites = new HashMap<>();
        String query = "SELECT * FROM Favourites " +
                "INNER JOIN `Pizzas` ON Pizzas.pizza_id = Favourites.pizza_id " +
                " WHERE `user_id`=" + user.getUserId();
        try {
            ResultSet rs = DBConnection.executeSearch(query);
            while (rs.next()){
                Pizza pizza = new Pizza();
                pizza.setPizzaId(rs.getInt("Pizzas.pizza_id"));
                pizza.setName(rs.getString("Pizzas.name"));
                pizza.setBasePrice(rs.getBigDecimal("Pizzas.base_price"));
                pizza.setDescription(rs.getString("Pizzas.description"));

                List<Toppings> toppingsList = new ArrayList<>();

                Toppings topping = new Toppings();
                topping.setId(rs.getInt(""));
                topping.setTopping(rs.getString(""));
                toppingsList.add(topping);
                Favourite favourite = new Favourite.Builder()
                        .id(rs.getString("Favourites.favourite_id"))
                        .pizza(pizza)
                        .toppings(toppingsList)
                        .username(user.getUsername())
                        .build();
                favourites.put(favourite.getId(),favourite);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return favourites;
    }
}
