package com.example.pahanaedu.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Handles the user logout process.
 */
@WebServlet("/logout") // Mapped to the /logout URL
public class LogoutController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * The logout action is simple and doesn't require a form, so we use doGet.
     * Clicking the "Logout" link will trigger this method.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Get the current session. Do not create a new one if it doesn't exist.
        HttpSession session = request.getSession(false);

        if (session != null) {
            // 2. Invalidate the session, removing all attributes (like the "user" object).
            session.invalidate();
        }

        // 3. Redirect the user back to the login page.
        response.sendRedirect("login");
    }
}