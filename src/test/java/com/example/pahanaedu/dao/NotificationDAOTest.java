package com.example.pahanaedu.dao;

import com.example.pahanaedu.model.Notification;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;


public class NotificationDAOTest {

    @Test
    public void testAddNotification() {
        NotificationDAO notificationDAO = new NotificationDAO();
        Notification notification = new Notification();
        notification.setMessage("Test: Stock for item 'Test Book' is low (3 remaining).");

        boolean result = notificationDAO.addNotification(notification);

        assertTrue("Notification should be added successfully.", result);
    }

    @Test
    public void testGetUnreadNotifications() {
        NotificationDAO notificationDAO = new NotificationDAO();

        // Ensure there's at least one notification to find
        Notification notification = new Notification();
        notification.setMessage("Unread test message.");
        notificationDAO.addNotification(notification);

        List<Notification> notifications = notificationDAO.getUnreadNotifications();

        assertNotNull("The notification list should not be null.", notifications);
        assertFalse("The notification list should not be empty.", notifications.isEmpty());
    }

    @Test
    public void testGetAllNotifications() {
        NotificationDAO notificationDAO = new NotificationDAO();

        // Ensure there's at least one notification to find
        Notification notification = new Notification();
        notification.setMessage("Get all test message.");
        notificationDAO.addNotification(notification);

        List<Notification> notifications = notificationDAO.getAllNotifications();

        assertNotNull("The notification list should not be null.", notifications);
        assertFalse("The notification list should not be empty.", notifications.isEmpty());
    }
}