package com.example.pahanaedu.service;

import com.example.pahanaedu.dao.CustomerDAO;
import com.example.pahanaedu.model.Customer;

import java.util.List;

/**
 * Service layer for customer-related operations.
 * This class contains the business logic. The controller will call this class,
 * and this class will call the DAO. This follows the Single Responsibility Principle.
 */
public class CustomerService implements ICustomerService {

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
     *
     * @param customer The customer object to be added.
     * @return true if the customer was added successfully, false otherwise.
     */
    @Override
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

    /**
     * Handles the business logic for retrieving all customers.
     *
     * @return A List of all Customer objects.
     */
    @Override
    public List<Customer> getAllCustomers() {
        // Business logic could be added here, such as filtering or sorting the list.
        // For now, we simply pass the request to the DAO.
        return customerDAO.getAllCustomers();
    }

    /**
     * Handles the business logic for retrieving a single customer by their ID.
     *
     * @param id The ID of the customer to retrieve.
     * @return The Customer object if found, otherwise null.
     */
    @Override
    public Customer getCustomerById(int id) {
        // Business logic could be added here, e.g., checking if the current user has permission to view this customer.
        return customerDAO.getCustomerById(id);
    }

    /**
     * Handles the business logic for updating an existing customer.
     *
     * @param customer The customer object with updated details.
     * @return true if the update was successful, false otherwise.
     */
    @Override
    public boolean updateCustomer(Customer customer) {
        // Business logic and validation would go here. For example:
        // if (customer.getFullName() == null || customer.getFullName().trim().isEmpty()) {
        //     return false; // Name cannot be empty
        // }
        return customerDAO.updateCustomer(customer);
    }

    /**
     * Handles the business logic for deleting a customer.
     *
     * @param id The ID of the customer to delete.
     * @return true if the deletion was successful, false otherwise.
     */
    @Override
    public boolean deleteCustomer(int id) {
        // Business logic could be added here, such as checking if the customer
        // has any unpaid bills before allowing deletion.
        return customerDAO.deleteCustomer(id);
    }

    @Override
    public Customer createCustomerForBilling(Customer customer) {
        return customerDAO.addCustomerAndReturn(customer);
    }
}