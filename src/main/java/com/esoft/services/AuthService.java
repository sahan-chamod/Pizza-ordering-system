package com.esoft.services;

import com.esoft.models.Users;

import java.util.List;
import java.util.Optional;

public class AuthService {
    private List<Users> users;

    public AuthService(){

    }
    public AuthService(List<Users> users) {
        this.users = users;
    }

    public Users login(String username, String password) {
        Optional<Users> user = users.stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst();

        return user.orElse(null);
    }
}
