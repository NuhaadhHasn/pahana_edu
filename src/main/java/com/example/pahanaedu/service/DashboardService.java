package com.example.pahanaedu.service;

import com.example.pahanaedu.dao.CustomerDAO;
import com.example.pahanaedu.dao.ItemDAO;
import com.example.pahanaedu.model.DashboardData;
import com.example.pahanaedu.model.Notification;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service layer responsible for gathering data for the main dashboard.
 */
public class DashboardService {

    private final CustomerDAO customerDAO;
    private final ItemDAO itemDAO;
    private final NotificationService notificationService;

    public DashboardService() {
        this.customerDAO = new CustomerDAO();
        this.itemDAO = new ItemDAO();
        this.notificationService = new NotificationService();
    }

    /**
     * Gathers various statistics for the dashboard API.
     *
     * @return A Map containing the dashboard data.
     */

    public DashboardData getDashboardData() {
        // Get Stats
        Map<String, Integer> stats = new HashMap<>();
        stats.put("customerCount", customerDAO.getCustomerCount());
        stats.put("totalStock", itemDAO.getTotalStockCount());

        // Get Notifications
        List<Notification> notifications = notificationService.getUnreadNotifications();

        // Combine into a single data object
        DashboardData dashboardData = new DashboardData();
        dashboardData.setStatistics(stats);
        dashboardData.setNotifications(notifications);

        return dashboardData;
    }
}