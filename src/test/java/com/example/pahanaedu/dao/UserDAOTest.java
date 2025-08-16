package com.example.pahanaedu.dao;

import com.example.pahanaedu.model.User;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.List;

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

    @Test
    public void testGetAllUsers() {
        UserDAO userDAO = new UserDAO();
        List<User> users = userDAO.getAllUsers();
        assertNotNull("User list should not be null.", users);
        assertFalse("User list should not be empty.", users.isEmpty());
    }

    @Test
    public void testGetUserById() {
        UserDAO userDAO = new UserDAO();
        // Assuming a user with ID 1 (admin) exists.
        User user = userDAO.getUserById(1);
        assertNotNull("User should not be null for a valid ID.", user);
        assertEquals("The user ID should be 1.", 1, user.getUserId());
    }

    @Test
    public void testUpdateUser() {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserById(2); // Get the 'staff' user
        assertNotNull("Could not retrieve user to update.", user);

        user.setFullName("Updated Staff Name");
        boolean result = userDAO.updateUser(user);
        assertTrue("User should be updated successfully.", result);

        User updatedUser = userDAO.getUserById(2);
        assertEquals("Full name should be updated.", "Updated Staff Name", updatedUser.getFullName());
    }

    @Test
    public void testDeleteUser() {
        UserDAO userDAO = new UserDAO();
        // Add a temporary user to delete
        User tempUser = new User();
        tempUser.setUsername("todelete");
        tempUser.setPassword("deletepass");
        tempUser.setFullName("To Be Deleted");
        tempUser.setRole("STAFF");
        userDAO.addUser(tempUser);

        // Now find and delete them
        User userToDelete = userDAO.validate("todelete", "deletepass");
        assertNotNull("Could not find temp user to delete", userToDelete);

        boolean result = userDAO.deleteUser(userToDelete.getUserId());
        assertTrue("User should be deleted successfully.", result);

        User deletedUser = userDAO.getUserById(userToDelete.getUserId());
        assertNull("Deleted user should not be found.", deletedUser);
    }
}