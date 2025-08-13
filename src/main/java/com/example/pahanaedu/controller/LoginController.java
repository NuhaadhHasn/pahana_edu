package com.example.pahanaedu.controller;

import com.example.pahanaedu.model.User;
import com.example.pahanaedu.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Handles user login and logout requests.
 */
@WebServlet("/login") // Mapped to the /login URL
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService;

    @Override
    public void init() {
        userService = new UserService();
    }

    /**
     * Handles the submission of the login form.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Use the service to authenticate the user
        User user = userService.loginUser(username, password);

        if (user != null) {
            // --- Login Successful ---
            // Create a new session for the user (or get the existing one)
            HttpSession session = request.getSession();

            // Store the user object in the session. This is how we "remember" they are logged in.
            session.setAttribute("user", user);

            // Redirect to a main dashboard or home page after login
            response.sendRedirect(request.getContextPath() + "/dashboard.jsp");
        } else {
            // --- Login Failed ---
            // Set an error message to display on the login page
            request.setAttribute("errorMessage", "Invalid username or password. Please try again.");

            // Forward the request back to the login page to show the error
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    /**
     * Handles requests to view the login page.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // When a user navigates to /login, just show them the login page.
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}