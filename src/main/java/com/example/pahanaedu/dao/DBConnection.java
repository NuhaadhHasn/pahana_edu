package com.example.pahanaedu.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Manages the database connection using the Singleton Design Pattern.
 * This ensures that only one instance of the connection manager exists,
 * preventing multiple, resource-intensive connections.
 */
public class DBConnection {

    // The single, static instance of this class.
    private static DBConnection dbConnection;

    // The single JDBC Connection object.
    private final Connection connection;

    // Database connection parameters.
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/pahana_edu";
    private static final String DB_USER = "root"; // IMPORTANT: Change if your user is different
    private static final String DB_PASSWORD = ""; // IMPORTANT: Change to your password

    /**
     * Private constructor to prevent anyone else from creating an instance.
     * This is the core of the Singleton pattern.
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the JDBC driver class is not found
     */
    private DBConnection() throws SQLException, ClassNotFoundException {
        // Step 1 & 2 from slides: Load the Driver and Register It
        Class.forName("com.mysql.cj.jdbc.Driver");
        // Step 3 from slides: Establish the Connection
        this.connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    /**
     * The public static method to get the single instance of this class.
     * If the instance doesn't exist, it creates one.
     * @return The single DBConnection instance.
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the JDBC driver class is not found
     */
    public static synchronized DBConnection getInstance() throws SQLException, ClassNotFoundException {
        if (dbConnection == null) {
            dbConnection = new DBConnection();
        }
        return dbConnection;
    }

    /**
     * Provides the active database connection to other parts of the application (the DAOs).
     * @return The active JDBC Connection object.
     */
    public Connection getConnection() {
        return this.connection;
    }
}