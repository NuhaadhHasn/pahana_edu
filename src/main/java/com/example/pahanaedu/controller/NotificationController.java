package com.example.pahanaedu.controller;

import com.example.pahanaedu.model.Notification;
import com.example.pahanaedu.service.INotificationService;
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
 * Controller to handle viewing the full history of notifications.
 */
@WebServlet("/notifications")
public class NotificationController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private INotificationService notificationService;

    @Override
    public void init() {
        notificationService = ServiceFactory.getNotificationService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Notification> notificationList = notificationService.getAllNotifications();
        request.setAttribute("notificationList", notificationList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("notification-list.jsp");
        dispatcher.forward(request, response);
    }
}