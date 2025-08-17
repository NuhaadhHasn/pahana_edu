package com.example.pahanaedu.model;

import java.time.LocalDateTime;

import java.util.Date;

public class Notification {
    private int notificationId;
    private String message;
    private boolean read;
    private LocalDateTime createdAt;

    // --- Add Getters and Setters for all fields ---

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Helper method to convert the modern LocalDateTime to the legacy java.util.Date
     * required by the JSTL fmt tag.
     *
     * @return The creation date as a java.util.Date object.
     */
    public Date getCreatedAtAsDate() {
        if (this.createdAt == null) {
            return null;
        }
        // Convert LocalDateTime to a format the old Date object can understand
        return java.sql.Timestamp.valueOf(this.createdAt);
    }
}