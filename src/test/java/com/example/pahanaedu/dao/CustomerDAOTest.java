package com.example.pahanaedu.dao;

import com.example.pahanaedu.model.Customer;

import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class CustomerDAOTest {

    /**
     * TDD Test - Red Phase 1:
     * This test defines the functionality of adding a customer.
     * It will fail because the Customer model and CustomerDAO do not exist yet.
     */
    @Test
    public void testAddCustomer() {
        // 1. Setup
        CustomerDAO customerDAO = new CustomerDAO();
        Customer newCustomer = new Customer();
        newCustomer.setAccountNumber("TEST-001");
        newCustomer.setFullName("Test User");
        newCustomer.setAddress("123 Test Street");
        newCustomer.setPhoneNumber("0123456789");

        // 2. Execution
        boolean result = customerDAO.addCustomer(newCustomer);

        // 3. Assertion
        assertTrue("The customer should be added successfully.", result);
    }

    /**
     * TDD Test - Red Phase: test retrieving all customers.
     * This test will fail because the getAllCustomers() method does not exist in CustomerDAO yet.
     */
    @Test
    public void testGetAllCustomers() {
        // 1. Setup
        CustomerDAO customerDAO = new CustomerDAO();

        // 2. Execution
        List<Customer> customers = customerDAO.getAllCustomers();

        // 3. Assertion
        assertNotNull("The customer list should not be null.", customers);
        // We know from our SQL script that there is at least one customer.
        assertFalse("The customer list should not be empty.", customers.isEmpty());
    }

    /**
     * TDD Test - Red Phase: test fetching a single customer by their ID.
     * This will fail as getCustomerById() does not exist in the DAO.
     */
    @Test
    public void testGetCustomerById() {
        CustomerDAO customerDAO = new CustomerDAO();
        // Assuming a customer with ID 1 exists from our sample data.
        Customer customer = customerDAO.getCustomerById(1);

        assertNotNull("Customer should not be null for a valid ID.", customer);
        assertEquals("The customer ID should be 1.", 1, customer.getCustomerId());
    }

    /**
     * TDD Test - Red Phase: test updating an existing customer's details.
     * This will fail as updateCustomer() does not exist in the DAO.
     */
    @Test
    public void testUpdateCustomer() {
        CustomerDAO customerDAO = new CustomerDAO();
        // First, get an existing customer to update.
        Customer customer = customerDAO.getCustomerById(1);
        assertNotNull("Setup failed: Could not retrieve customer to update.", customer);

        // Change some details
        customer.setFullName("Kamal Perera Updated");
        customer.setPhoneNumber("077-9876543");

        // Execute the update
        boolean result = customerDAO.updateCustomer(customer);

        // Assert that the update was successful
        assertTrue("The customer should be updated successfully.", result);

        // Optional: Verify the update by fetching the data again
        Customer updatedCustomer = customerDAO.getCustomerById(1);
        assertEquals("The full name should be updated.", "Kamal Perera Updated", updatedCustomer.getFullName());
    }

    /**
     * TDD Test - Red Phase: test deleting a customer.
     * This will fail as deleteCustomer() does not exist in the DAO.
     */
    @Test
    public void testDeleteCustomer() {
        CustomerDAO customerDAO = new CustomerDAO();
        int customerIdToDelete = 1; // Assuming a customer with ID 1 exists.

        // Execute the delete operation
        boolean result = customerDAO.deleteCustomer(customerIdToDelete);

        // Assert that the deletion was reported as successful
        assertTrue("The customer should be deleted successfully.", result);

        // Verify by trying to fetch the deleted customer, it should be null
        Customer customer = customerDAO.getCustomerById(customerIdToDelete);
        assertNull("The customer should no longer exist in the database.", customer);
    }
}