package com.example.pahanaedu.model;

import java.util.List;
import java.util.Map;

public class DashboardData {
    private Map<String, Integer> statistics;
    private List<Notification> notifications;

    // --- Add Getters and Setters ---
    public Map<String, Integer> getStatistics() {
        return statistics;
    }

    public void setStatistics(Map<String, Integer> statistics) {
        this.statistics = statistics;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
}