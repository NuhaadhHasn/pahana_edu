package com.example.pahanaedu.controller;

import com.example.pahanaedu.model.User;
import com.example.pahanaedu.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controller Servlet for handling all user management web requests (Admin only).
 */
@WebServlet("/users")
public class UserController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService;

    @Override
    public void init() {
        userService = new UserService();
    }

    /**
     * Handles the submission of the "Add User" form.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String fullName = request.getParameter("fullName");
        String role = request.getParameter("role");

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password); // Note: In a real app, we would hash this password.
        newUser.setFullName(fullName);
        newUser.setRole(role);

        boolean success = userService.addUser(newUser);

        if (success) {
            // After adding, redirect to the user list to see the new entry.
            response.sendRedirect(request.getContextPath() + "/users");
        } else {
            // If it fails (e.g., duplicate username), show the form again with an error.
            request.setAttribute("errorMessage", "Failed to add user. The username may already exist.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
            dispatcher.forward(request, response);
        }
    }
}