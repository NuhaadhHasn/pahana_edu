package com.example.pahanaedu.controller;

import com.example.pahanaedu.service.INotificationService;
import com.example.pahanaedu.service.ServiceFactory; // Assuming you have refactored

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/notifications/mark-as-read")
public class NotificationApiController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private INotificationService notificationService;

    @Override
    public void init() {
        notificationService = ServiceFactory.getNotificationService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("--- NOTIFICATION API: doPost method started ---"); // DEBUG

        String idParam = request.getParameter("id"); // Get the raw parameter
        System.out.println("Received 'id' parameter from request: " + idParam); // DEBUG

        if (idParam == null || idParam.trim().isEmpty()) {
            System.err.println("ERROR: 'id' parameter is null or empty."); // DEBUG
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "'id' parameter is missing.");
            return;
        }

        try {
            int notificationId = Integer.parseInt(idParam);
            System.out.println("Parsed notificationId: " + notificationId); // DEBUG

            boolean success = notificationService.markAsRead(notificationId);
            System.out.println("Result from service's markAsRead: " + success); // DEBUG

            if (success) {
                System.out.println("Sending 200 OK response."); // DEBUG
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                System.err.println("ERROR: Service returned false. Failed to update in database."); // DEBUG
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to update notification in database.");
            }
        } catch (NumberFormatException e) {
            System.err.println("ERROR: Could not parse 'id' parameter to an integer."); // DEBUG
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid notification ID format.");
        }
    }
}