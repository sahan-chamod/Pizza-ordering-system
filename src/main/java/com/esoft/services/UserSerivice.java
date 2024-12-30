package com.esoft.services;

import com.esoft.config.DBConnection;
import com.esoft.models.Users;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserSerivice {
    private List<Users> users = new ArrayList<>();

    public UserSerivice(){

    }
    public void createUser(Users user){
        users.add(user);
        String query = "INSERT INTO Users (username, email, password_hash, phone_number, address, role) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        DBConnection.executeUpdate(query,user.toDataArray());
        DBConnection.closeConnectio();
    }

    public List<Users> getUsers() throws SQLException {
        ResultSet rs = DBConnection.executeSearch("SELECT * FROM Users");
        try {
            while (rs.next()) {
                // Retrieve each column's value from the ResultSet
                int userId = rs.getInt("user_id"); // Assuming the user_id column is of type INT
                String username = rs.getString("username");
                String email = rs.getString("email");
                String passwordHash = rs.getString("password_hash");
                String phoneNumber = rs.getString("phone_number");
                String address = rs.getString("address");
                String role = rs.getString("role");
                String createdAt = rs.getString("created_at");
                String updatedAt = rs.getString("updated_at");

                Users user = new Users(userId, username, email, passwordHash, phoneNumber, address, role);

                user.setCreatedAt(createdAt);
                user.setUpdatedAt(updatedAt);

                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                DBConnection.closeConnectio();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return users;
    }

}
