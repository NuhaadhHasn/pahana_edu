package com.example.pahanaedu.service;

import com.example.pahanaedu.dao.NotificationDAO;
import com.example.pahanaedu.model.Notification;

import java.util.List;

public class NotificationService implements INotificationService {

    private final NotificationDAO notificationDAO;

    public NotificationService() {
        this.notificationDAO = new NotificationDAO();
    }

    /**
     * Handles the business logic for creating a new notification.
     *
     * @param message The alert message to be saved.
     */
    @Override
    public void createNotification(String message) {
        if (message == null || message.trim().isEmpty()) {
            return; // Don't save empty notifications
        }
        Notification notification = new Notification();
        notification.setMessage(message);
        notificationDAO.addNotification(notification);
    }

    @Override
    public List<Notification> getUnreadNotifications() {
        return notificationDAO.getUnreadNotifications();
    }

    @Override
    public boolean markAsRead(int notificationId) {
        return notificationDAO.markAsRead(notificationId);
    }

    @Override
    public List<Notification> getAllNotifications() {
        return notificationDAO.getAllNotifications();
    }
}