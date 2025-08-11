package com.example.pahanaedu.dao;

import com.example.pahanaedu.model.Customer;

import org.junit.Test;
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
}