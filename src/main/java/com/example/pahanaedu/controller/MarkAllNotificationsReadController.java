package com.example.pahanaedu.controller;

import com.example.pahanaedu.service.INotificationService;
import com.example.pahanaedu.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Handles the specific action of marking all notifications as read.
 */
@WebServlet("/api/notifications/mark-all-as-read")
public class MarkAllNotificationsReadController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private INotificationService notificationService;

    @Override
    public void init() {
        notificationService = ServiceFactory.getNotificationService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean success = notificationService.markAllAsRead();

        if (success) {
            response.setStatus(HttpServletResponse.SC_OK); // Send a 200 OK status
        } else {
            // If no rows were updated (e.g., no unread notifications), it's still a success.
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }
}