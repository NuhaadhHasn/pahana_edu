package com.example.pahanaedu.service;

import com.example.pahanaedu.model.Notification;

import java.util.List;

public interface INotificationService {
    void createNotification(String message);

    List<Notification> getUnreadNotifications();

    List<Notification> getAllNotifications();

    boolean markAsRead(int notificationId);
}