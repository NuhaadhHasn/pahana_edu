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
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/profile")
public class ProfileController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IUserService userService;

    @Override
    public void init() {
        userService = ServiceFactory.getUserService();
    }

    /**
     * Handles the request to view the profile page.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("profile.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Handles submissions from both the "Update Details" and "Change Password" forms.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute("user");

        if ("updateDetails".equals(action)) {
            // --- Handle Profile Detail Update ---
            // Work on a copy, so the logged-in user object only changes when the database accepted the change
            // (a username that is already taken makes the update fail; it used to be reported as a success).
            String newFullName = request.getParameter("fullName");
            String newUsername = request.getParameter("username");
            if (newFullName == null || newFullName.trim().isEmpty() || newUsername == null || newUsername.trim().isEmpty()) {
                session.setAttribute("errorMessage", "The full name and the username cannot be empty.");
                response.sendRedirect(request.getContextPath() + "/profile");
                return;
            }
            User changed = new User();
            changed.setUserId(currentUser.getUserId());
            changed.setRole(currentUser.getRole());
            changed.setPassword(currentUser.getPassword());
            changed.setFullName(newFullName.trim());
            changed.setUsername(newUsername.trim());

            if (userService.updateUser(changed)) {
                currentUser.setFullName(changed.getFullName());
                currentUser.setUsername(changed.getUsername());
                // Set a success message and redirect
                session.setAttribute("successMessage", "Your details have been updated successfully!");
            } else {
                session.setAttribute("errorMessage", "Your details were not saved. That username may already be taken.");
            }
            response.sendRedirect(request.getContextPath() + "/profile");

        } else if ("changePassword".equals(action)) {
            // --- Handle Password Change ---
            String currentPassword = request.getParameter("currentPassword");
            String newPassword = request.getParameter("newPassword");
            String confirmPassword = request.getParameter("confirmPassword");

            // 1. Verify the new passwords match
            if (!newPassword.equals(confirmPassword)) {
                session.setAttribute("errorMessage", "New passwords do not match.");
                response.sendRedirect(request.getContextPath() + "/profile");
                return;
            }

            // 2. Verify the user's current password is correct
            if (!userService.verifyPassword(currentUser.getUserId(), currentPassword)) {
                session.setAttribute("errorMessage", "Incorrect current password.");
                response.sendRedirect(request.getContextPath() + "/profile");
                return;
            }

            // 3. If all checks pass, update the password
            userService.changePassword(currentUser.getUserId(), newPassword);
            session.setAttribute("successMessage", "Your password has been changed successfully!");
            response.sendRedirect(request.getContextPath() + "/profile");

        } else {
            // Handle unknown action
            response.sendRedirect(request.getContextPath() + "/profile");
        }
    }
}