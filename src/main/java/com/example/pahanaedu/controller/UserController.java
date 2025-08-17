package com.example.pahanaedu.controller;

import com.example.pahanaedu.model.User;
import com.example.pahanaedu.service.IUserService;
import com.example.pahanaedu.service.ServiceFactory;

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

    @Override
    public void init() {

        userService = ServiceFactory.getUserService();
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
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        request.setAttribute("user", userService.getUserById(id));
        request.getRequestDispatcher("user-form.jsp").forward(request, response);
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

        if (createdUser != null) {
            response.sendRedirect(request.getContextPath() + "/users");
        } else {
            request.setAttribute("errorMessage", "Failed to add user. The username may already exist.");
            // We'll create user-form.jsp in a later step
            RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1. Get all the data from the submitted form
        int id = Integer.parseInt(request.getParameter("userId"));
        String username = request.getParameter("username");
        String fullName = request.getParameter("fullName");
        String role = request.getParameter("role");

        // 2. Create a User object with the updated data
        User user = new User();
        user.setUserId(id);
        user.setUsername(username);
        user.setFullName(fullName);
        user.setRole(role);

        // 3. Call the service to perform the update
        userService.updateUser(user);

        // 4. Redirect back to the user list to see the changes
        response.sendRedirect(request.getContextPath() + "/users");
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        userService.deleteUser(id);
        response.sendRedirect(request.getContextPath() + "/users");
    }
}