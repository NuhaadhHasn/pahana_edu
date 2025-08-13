package com.example.pahanaedu.service;

import com.example.pahanaedu.dao.UserDAO;
import com.example.pahanaedu.model.User;

/**
 * Service layer for user-related business logic.
 * It uses the UserDAO to interact with the database.
 */
public class UserService {

    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    /**
     * Authenticates a user based on the provided username and password.
     * This method will contain business logic, for example, logging login attempts.
     * @param username The user's username.
     * @param password The user's password.
     * @return The User object if authentication is successful, otherwise null.
     */
    public User loginUser(String username, String password) {
        // Business logic can be added here.
        // For example, we could log the login attempt before validating.
        System.out.println("Attempting login for user: " + username);

        User user = userDAO.validate(username, password);

        if (user != null) {
            System.out.println("Login successful for user: " + username);
        } else {
            System.out.println("Login failed for user: " + username);
        }

        return user;
    }
}