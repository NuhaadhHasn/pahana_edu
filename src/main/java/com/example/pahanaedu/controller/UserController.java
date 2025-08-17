package com.example.pahanaedu.controller;

import com.example.pahanaedu.model.User;
import com.example.pahanaedu.service.IUserService;
import com.example.pahanaedu.service.ServiceFactory;
import com.example.pahanaedu.model.Customer;
import com.example.pahanaedu.service.ICustomerService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Controller Servlet for handling all user management web requests (Admin only).
 */
@WebServlet("/users")
public class UserController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IUserService userService;
    private ICustomerService customerService;

    @Override
    public void init() {
        userService = ServiceFactory.getUserService();
        customerService = ServiceFactory.getCustomerService();
    }

    /**
     * Handles the submission of the "Add User" form.
     */

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        action = (action == null) ? "add" : action;
        switch (action) {
            case "update":
                updateUser(request, response);
                break;
            default:
                addUser(request, response);
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        action = (action == null) ? "list" : action;
        switch (action) {
            case "new": // <-- ADD THIS NEW CASE
                showNewForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteUser(request, response);
                break;
            default:
                listUsers(request, response);
                break;
        }
    }

    private void listUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the list of users from the service.
        List<User> userList = userService.getAllUsers();

        // THE FIX: Set the list as an attribute in the request object.
        request.setAttribute("userList", userList);

        // Now forward to the JSP.
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("add-user-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User existingUser = userService.getUserById(id);

        if ("CUSTOMER".equals(existingUser.getRole())) {
            Customer customerDetails = customerService.getCustomerByUserId(id);
            request.setAttribute("customer", customerDetails);
        }

        request.setAttribute("user", existingUser);
        RequestDispatcher dispatcher = request.getRequestDispatcher("edit-user-form.jsp");
        dispatcher.forward(request, response);
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String fullName = request.getParameter("fullName");
        String role = request.getParameter("role");

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setFullName(fullName);
        newUser.setRole(role);

        User createdUser = userService.addUser(newUser);

        if (createdUser == null) {
            // If user creation failed (e.g., duplicate username), stop here.
            request.setAttribute("errorMessage", "Failed to add user. The username may already exist.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // --- NEW LOGIC: If the role is CUSTOMER, also create the customer record ---
        if ("CUSTOMER".equals(role)) {
            Customer newCustomer = new Customer();
            newCustomer.setAccountNumber(request.getParameter("accountNumber"));
            newCustomer.setAddress(request.getParameter("address"));
            newCustomer.setPhoneNumber(request.getParameter("phoneNumber"));
            newCustomer.setFullName(fullName); // Use the same full name
            newCustomer.setUserId(createdUser.getUserId()); // Link to the user we just created

            customerService.addCustomer(newCustomer);
        }

        // Redirect to the user list on success
        response.sendRedirect(request.getContextPath() + "/users");
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1. Get all the data from the submitted form
        int userId = Integer.parseInt(request.getParameter("userId"));
        String username = request.getParameter("username");
        String fullName = request.getParameter("fullName");
        String role = request.getParameter("role");

        // 2. Create a User object with the updated data
        User user = new User();
        user.setUserId(userId);
        user.setUsername(username);
        user.setFullName(fullName);
        user.setRole(role);

        // 3. Call the service to perform the update
        userService.updateUser(user);

        String newPassword = request.getParameter("newPassword");
        if (newPassword != null && !newPassword.isEmpty()) {
            userService.changePassword(userId, newPassword);
        }
        
        // --- NEW LOGIC: If the user is a customer, also update their customer record ---
        if ("CUSTOMER".equals(role)) {
            Customer customer = customerService.getCustomerByUserId(userId);
            // It's possible a customer record doesn't exist yet, so we check for null
            if (customer != null) {
                customer.setAccountNumber(request.getParameter("accountNumber"));
                customer.setAddress(request.getParameter("address"));
                customer.setPhoneNumber(request.getParameter("phoneNumber"));
                customer.setFullName(fullName);
                customerService.updateCustomer(customer);
            }
        }

        // 4. Redirect back to the user list to see the changes
        response.sendRedirect(request.getContextPath() + "/users");
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        userService.deleteUser(id);
        response.sendRedirect(request.getContextPath() + "/users");
    }
}