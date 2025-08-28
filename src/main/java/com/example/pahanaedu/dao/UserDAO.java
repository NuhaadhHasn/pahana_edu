package com.example.pahanaedu.dao;

import com.example.pahanaedu.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles all database operations related to the User entity.
 */
public class UserDAO {

    // SQL query to select a user by their username and password.
    private static final String VALIDATE_USER_SQL = "SELECT * FROM users WHERE username = ? AND password = ?;";
    private static final String INSERT_USER_SQL = "INSERT INTO users (username, password, full_name, role) VALUES (?, ?, ?, ?);";
    private static final String SELECT_ALL_USERS_SQL = "SELECT * FROM users;";
    private static final String SELECT_USER_BY_ID_SQL = "SELECT * FROM users WHERE user_id = ?;";
    private static final String UPDATE_USER_SQL = "UPDATE users SET username = ?, full_name = ?, role = ? WHERE user_id = ?;";
    private static final String DELETE_USER_SQL = "DELETE FROM users WHERE user_id = ?;";
    private static final String UPDATE_PASSWORD_SQL = "UPDATE users SET password = ? WHERE user_id = ?;";
    private static final String VERIFY_PASSWORD_SQL = "SELECT user_id FROM users WHERE user_id = ? AND password = ?;";
    private static final String CHECK_USERNAME_EXISTS_SQL = "SELECT user_id FROM users WHERE username = ?;";

    /**
     * Validates a user's credentials against the database.
     *
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
     *
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

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return users;
        }
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(SELECT_ALL_USERS_SQL)) {
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setFullName(rs.getString("full_name"));
                user.setRole(rs.getString("role"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public User getUserById(int id) {
        User user = null;
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setFullName(rs.getString("full_name"));
                user.setRole(rs.getString("role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean updateUser(User user) {
        boolean rowUpdated = false;
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_SQL)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getFullName());
            preparedStatement.setString(3, user.getRole());
            preparedStatement.setInt(4, user.getUserId());
            rowUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    public boolean changePassword(int userId, String newPassword) {
        boolean rowUpdated = false;
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PASSWORD_SQL)) {
                preparedStatement.setString(1, newPassword);
                preparedStatement.setInt(2, userId);
                rowUpdated = preparedStatement.executeUpdate() > 0;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    public boolean deleteUser(int id) {
        boolean rowDeleted = false;
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_SQL)) {
            preparedStatement.setInt(1, id);
            rowDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }

    public boolean verifyPassword(int userId, String password) {
        boolean passwordMatches = false;
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement(VERIFY_PASSWORD_SQL)) {
                preparedStatement.setInt(1, userId);
                preparedStatement.setString(2, password);

                // If the query returns a row, it means we found a user with that ID and that password.
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        passwordMatches = true;
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return passwordMatches;
    }

    public boolean usernameExists(String username) {
        System.out.println("--- DAO CHECK: Executing SQL check for username: '" + username + "' ---");
        boolean exists = false;
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement(CHECK_USERNAME_EXISTS_SQL)) {
                preparedStatement.setString(1, username);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    // If rs.next() is true, it means we found at least one row
                    if (rs.next()) {
                        exists = true;
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("--- DAO CHECK: SQL query result, exists = " + exists + " ---");
        return exists;
    }
}