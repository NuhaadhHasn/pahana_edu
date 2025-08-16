package com.example.pahanaedu.dao;

import com.example.pahanaedu.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Handles all database operations related to the User entity.
 */
public class UserDAO {

    // SQL query to select a user by their username and password.
    private static final String VALIDATE_USER_SQL = "SELECT * FROM users WHERE username = ? AND password = ?;";
    private static final String INSERT_USER_SQL = "INSERT INTO users (username, password, full_name, role) VALUES (?, ?, ?, ?);";

    /**
     * Validates a user's credentials against the database.
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     * @return A User object if the credentials are valid, otherwise null.
     */
    // Corrected UserDAO.java
    public User validate(String username, String password) {
        User user = null;
        // Get the single connection instance
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null; // Can't connect, so can't validate
        }

        // Use try-with-resources ONLY for the PreparedStatement
        try (PreparedStatement preparedStatement = connection.prepareStatement(VALIDATE_USER_SQL)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setFullName(rs.getString("full_name"));
                user.setRole(rs.getString("role"));
            }
            // ResultSet is automatically closed here by the try-with-resources if you declare it inside.
            // Or you can close it manually if needed: rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    /**
     * Adds a new user to the database.
     * @param user The User object to add.
     * @return true if the user was added successfully, false otherwise.
     */
    public boolean addUser(User user) {
        boolean rowInserted = false;
        Connection connection = null;

        try {
            connection = DBConnection.getInstance().getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFullName());
            preparedStatement.setString(4, user.getRole());

            rowInserted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowInserted;
    }

}