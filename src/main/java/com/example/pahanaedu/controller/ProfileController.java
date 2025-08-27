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
            currentUser.setFullName(request.getParameter("fullName"));
            currentUser.setUsername(request.getParameter("username"));

            userService.updateUser(currentUser);

            // Set a success message and redirect
            session.setAttribute("successMessage", "Your details have been updated successfully!");
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