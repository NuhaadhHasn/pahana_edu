package com.example.pahanaedu.service;

import com.example.pahanaedu.dao.LoginHistoryDAO;
import com.example.pahanaedu.model.LoginHistory;
import com.example.pahanaedu.dao.UserDAO;
import com.example.pahanaedu.model.User;

import java.util.List;

/**
 * Service layer for user-related business logic.
 * It uses the UserDAO to interact with the database.
 */
public class UserService implements IUserService {

    private final UserDAO userDAO;
    private final LoginHistoryDAO loginHistoryDAO;

    public UserService() {
        this.userDAO = new UserDAO();
        this.loginHistoryDAO = new LoginHistoryDAO();
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public User getUserById(int id) {
        return userDAO.getUserById(id);
    }

    @Override
    public boolean updateUser(User user) {
        return userDAO.updateUser(user);
    }

    @Override
    public boolean deleteUser(int id) {
        return userDAO.deleteUser(id);
    }

    /**
     * Authenticates a user based on the provided username and password.
     * This method will contain business logic, for example, logging login attempts.
     *
     * @param username The user's username.
     * @param password The user's password.
     * @return The User object if authentication is successful, otherwise null.
     */
    @Override
    public User loginUser(String username, String password) {
        User user = userDAO.validate(username, password);

        if (user != null) {
            loginHistoryDAO.logAttempt(user.getUserId(), "SUCCESS");
            System.out.println("Login successful for user: " + username);
        } else {
            loginHistoryDAO.logAttempt(0, "FAILED");
            System.out.println("Login failed for user: " + username);
        }

        return user;
    }

    /**
     * Handles the business logic for adding a new user.
     *
     * @param user The User object to be added.
     * @return true if the user was successfully added, false otherwise.
     */
    @Override
    public User addUser(User user) {
        // This method should add a user and return the full User object if successful.
        boolean success = userDAO.addUser(user);
        if (success) {
            // After adding, we immediately validate/fetch the user to get their auto-generated ID.
            return userDAO.validate(user.getUsername(), user.getPassword());
        }
        // If the addUser call failed (e.g., duplicate username), return null.
        return null;
    }

    @Override
    public List<LoginHistory> getLoginHistory() {
        return loginHistoryDAO.getAllLoginHistory();
    }
}