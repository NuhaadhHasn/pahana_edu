package com.example.pahanaedu.dao;

import com.example.pahanaedu.model.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// Data Access Object for Customer-related database operations.
public class CustomerDAO {

    private static final String INSERT_CUSTOMER_SQL = "INSERT INTO customers (account_number, full_name, address, phone_number) VALUES (?, ?, ?, ?);";

    public boolean addCustomer(Customer customer) {
        boolean rowUpdated = false;

        // Establish a connection to the database.
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CUSTOMER_SQL)) {

            // Set the parameters for the SQL query.
            preparedStatement.setString(1, customer.getAccountNumber());
            preparedStatement.setString(2, customer.getFullName());
            preparedStatement.setString(3, customer.getAddress());
            preparedStatement.setString(4, customer.getPhoneNumber());

            // Execute the query; executeUpdate() returns the number of rows affected.
            rowUpdated = preparedStatement.executeUpdate() > 0;

        } catch (SQLException | ClassNotFoundException e) {
            // Print the stack trace for debugging purposes.
            e.printStackTrace();
        }
        return rowUpdated;
    }
}