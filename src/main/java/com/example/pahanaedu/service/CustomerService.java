package com.example.pahanaedu.service;

import com.example.pahanaedu.dao.CustomerDAO;
import com.example.pahanaedu.model.Customer;

/**
 * Service layer for customer-related operations.
 * This class contains the business logic. The controller will call this class,
 * and this class will call the DAO. This follows the Single Responsibility Principle.
 */
public class CustomerService {

    private final CustomerDAO customerDAO;

    public CustomerService() {
        // This service class depends on the CustomerDAO.
        // This is a simple form of dependency management.
        this.customerDAO = new CustomerDAO();
    }

    /**
     * Handles the business logic for adding a new customer.
     * For now, it just passes the request to the DAO. Later, this is where we would
     * add validation rules (e.g., checking if the account number is in a valid format).
     * @param customer The customer object to be added.
     * @return true if the customer was added successfully, false otherwise.
     */
    public boolean addCustomer(Customer customer) {
        // --- Business Logic would go here ---
        // For example:
        // if (customer.getAccountNumber().startsWith("ACC-")) {
        //     return customerDAO.addCustomer(customer);
        // } else {
        //     return false; // Invalid account number format
        // }

        // For now, we keep it simple and just delegate to the DAO.
        return customerDAO.addCustomer(customer);
    }
}