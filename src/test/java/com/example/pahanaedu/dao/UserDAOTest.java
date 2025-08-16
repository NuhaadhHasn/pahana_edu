package com.example.pahanaedu.dao;

import com.example.pahanaedu.model.User;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserDAOTest {

    /**
     * TDD Test - Red Phase: test user login validation.
     * This test defines the functionality for a user login.
     * It will fail to compile until the User model and UserDAO are created.
     */
    @Test
    public void testValidateLogin() {
        UserDAO userDAO = new UserDAO();

        // Test case 1: Successful login with valid credentials from our SQL script
        User validUser = userDAO.validate("admin", "admin123");
        assertNotNull("A valid user should be returned for correct credentials.", validUser);
        assertEquals("The username should be 'admin'.", "admin", validUser.getUsername());

        // Test case 2: Failed login with invalid password
        User invalidUser = userDAO.validate("admin", "wrongpassword");
        assertNull("Null should be returned for incorrect credentials.", invalidUser);
    }

    /**
     * TDD Test - Red Phase: test adding a new user.
     * This will fail because the addUser() method does not exist in the DAO.
     */
    @Test
    public void testAddUser() {
        // 1. Setup
        UserDAO userDAO = new UserDAO();
        User newUser = new User();
        newUser.setUsername("newstaff");
        newUser.setPassword("staffpass");
        newUser.setFullName("New Staff Member");
        newUser.setRole("STAFF");

        // 2. Execution
        boolean result = userDAO.addUser(newUser);

        // 3. Assertion
        assertTrue("The new user should be added successfully.", result);

        // Optional: Verify by trying to log in as the new user
        User createdUser = userDAO.validate("newstaff", "staffpass");
        assertNotNull("Should be able to validate the newly created user.", createdUser);
    }
}