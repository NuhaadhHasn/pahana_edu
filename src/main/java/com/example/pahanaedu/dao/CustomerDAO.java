package com.example.pahanaedu.dao;

import com.example.pahanaedu.model.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.Statement;

// Data Access Object for Customer-related database operations.
public class CustomerDAO {

    private static final String INSERT_CUSTOMER_SQL = "INSERT INTO customers (account_number, full_name, address, phone_number) VALUES (?, ?, ?, ?);";
    private static final String SELECT_ALL_CUSTOMERS_SQL = "SELECT * FROM customers;";
    private static final String SELECT_CUSTOMER_BY_ID_SQL = "SELECT * FROM customers WHERE customer_id = ?;";
    private static final String UPDATE_CUSTOMER_SQL = "UPDATE customers SET account_number = ?, full_name = ?, address = ?, phone_number = ? WHERE customer_id = ?;";
    private static final String DELETE_CUSTOMER_SQL = "DELETE FROM customers WHERE customer_id = ?;";

    // Corrected CustomerDAO.java
    public boolean addCustomer(Customer customer) {
        boolean rowUpdated = false;
        Connection connection = null;

        try {
            connection = DBConnection.getInstance().getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false; // Can't get a connection, so can't add customer
        }

        // Use try-with-resources ONLY for the PreparedStatement
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CUSTOMER_SQL)) {

            preparedStatement.setString(1, customer.getAccountNumber());
            preparedStatement.setString(2, customer.getFullName());
            preparedStatement.setString(3, customer.getAddress());
            preparedStatement.setString(4, customer.getPhoneNumber());

            rowUpdated = preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    /**
     * Retrieves a list of all customers from the database.
     * @return A List of Customer objects.
     */
    public List<Customer> getAllCustomers() {
        // We use an ArrayList to store the Customer objects we retrieve.
        List<Customer> customers = new ArrayList<>();
        Connection connection = null;

        try {
            connection = DBConnection.getInstance().getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return customers; // Return an empty list if connection fails
        }

        // Use try-with-resources for Statement and ResultSet
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(SELECT_ALL_CUSTOMERS_SQL)) {

            // Loop through each row in the ResultSet
            while (rs.next()) {
                // For each row, create a Customer object
                Customer customer = new Customer();
                customer.setCustomerId(rs.getInt("customer_id"));
                customer.setAccountNumber(rs.getString("account_number"));
                customer.setFullName(rs.getString("full_name"));
                customer.setAddress(rs.getString("address"));
                customer.setPhoneNumber(rs.getString("phone_number"));

                // Add the created customer object to our list
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    /**
     * Retrieves a single customer from the database based on their ID.
     * @param id The ID of the customer to retrieve.
     * @return A Customer object, or null if not found.
     */
    public Customer getCustomerById(int id) {
        Customer customer = null;
        Connection connection = null;

        try {
            connection = DBConnection.getInstance().getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CUSTOMER_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                customer = new Customer();
                customer.setCustomerId(rs.getInt("customer_id"));
                customer.setAccountNumber(rs.getString("account_number"));
                customer.setFullName(rs.getString("full_name"));
                customer.setAddress(rs.getString("address"));
                customer.setPhoneNumber(rs.getString("phone_number"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customer;
    }

    /**
     * Updates an existing customer's record in the database.
     * @param customer The Customer object containing the updated information.
     * @return true if the update was successful, false otherwise.
     */
    public boolean updateCustomer(Customer customer) {
        boolean rowUpdated = false;
        Connection connection = null;

        try {
            connection = DBConnection.getInstance().getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CUSTOMER_SQL)) {
            preparedStatement.setString(1, customer.getAccountNumber());
            preparedStatement.setString(2, customer.getFullName());
            preparedStatement.setString(3, customer.getAddress());
            preparedStatement.setString(4, customer.getPhoneNumber());
            preparedStatement.setInt(5, customer.getCustomerId());

            rowUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    /**
     * Deletes a customer from the database.
     * @param id The ID of the customer to delete.
     * @return true if the deletion was successful, false otherwise.
     */
    public boolean deleteCustomer(int id) {
        boolean rowDeleted = false;
        Connection connection = null;

        try {
            connection = DBConnection.getInstance().getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CUSTOMER_SQL)) {
            preparedStatement.setInt(1, id);
            rowDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }

    /**
     * Counts the total number of customers in the database.
     * @return The total count of customers.
     */
    public int getCustomerCount() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM customers;";
        Connection connection = null;

        try {
            connection = DBConnection.getInstance().getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return 0;
        }

        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            if (rs.next()) {
                count = rs.getInt(1); // Get the count from the first column
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
}