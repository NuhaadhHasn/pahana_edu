package com.example.pahanaedu.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Manages the database connection using the Singleton Design Pattern.
 * This ensures that only one instance of the connection manager exists,
 * preventing multiple, resource-intensive connections.
 *
 * The address, user and password can be changed without editing this file:
 * set the environment variables DB_URL, DB_USER and DB_PASSWORD (or the Java system
 * properties pahana.db.url, pahana.db.user and pahana.db.password). When they are not
 * set the original defaults below are used.
 */
public class DBConnection {

    // The single, static instance of this class.
    private static DBConnection dbConnection;

    // The single JDBC Connection object (replaced when it is found closed or dead).
    private Connection connection;

    // Database connection parameters (defaults; see the note above).
    private static final String DB_URL = setting("pahana.db.url", "DB_URL", "jdbc:mysql://127.0.0.1:3306/pahana_edu");
    private static final String DB_USER = setting("pahana.db.user", "DB_USER", "root"); // IMPORTANT: Change if your user is different
    private static final String DB_PASSWORD = setting("pahana.db.password", "DB_PASSWORD", ""); // IMPORTANT: Change to your password

    private static String setting(String property, String environmentVariable, String defaultValue) {
        String value = System.getProperty(property);
        if (value == null) {
            value = System.getenv(environmentVariable);
        }
        return value != null ? value : defaultValue;
    }

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
     * If the connection has been closed, or the server dropped it (restart, idle time-out),
     * a new one is opened, so the site recovers without restarting Tomcat.
     * @return The active JDBC Connection object.
     */
    public synchronized Connection getConnection() {
        try {
            if (connection == null || connection.isClosed() || !connection.isValid(2)) {
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            }
        } catch (SQLException e) {
            // keep the old object: the DAO that uses it will report the real error
            e.printStackTrace();
        }
        return this.connection;
    }
}
