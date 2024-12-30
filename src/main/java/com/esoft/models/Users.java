package com.esoft.models;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class Users {
    private int userId;
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private String role;  // "Admin" or "Customer"
    private String createdAt;
    private String updatedAt;
    private List<Favourite> favourites;

    // Constructor with necessary fields
    public Users(int userId, String username, String email, String password, String phoneNumber, String address, String role) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.role = role;
    }

    public List<Favourite> getFavourites() {
        return favourites;
    }

    public void addFavourite(Favourite favourite) {
        favourites.add(favourite);
    }

    public void removeFavourite(String favouriteId) {
        favourites.removeIf(fav -> fav.getId().equals(favouriteId));
    }

    // Getters and setters for all fields
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Converts the user data to an array in the correct order for database insertion.
     *
     * @return an Object array containing user data.
     */
    public @NotNull String[] toDataArray() {
        return new String[] {
                username,       // 1st parameter
                email,          // 2nd parameter
                password,       // 3rd parameter (should already be hashed)
                phoneNumber,    // 4th parameter
                address,        // 5th parameter
                role            // 6th parameter
        };
    }

    @Override
    public String toString() {
        return "Users{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return userId == users.userId && username.equals(users.username) && email.equals(users.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, email);
    }
}
