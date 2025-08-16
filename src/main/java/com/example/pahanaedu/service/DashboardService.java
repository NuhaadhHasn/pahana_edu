package com.example.pahanaedu.service;

import com.example.pahanaedu.dao.CustomerDAO;
import com.example.pahanaedu.dao.ItemDAO;
import java.util.HashMap;
import java.util.Map;

/**
 * Service layer responsible for gathering data for the main dashboard.
 */
public class DashboardService {

    private final CustomerDAO customerDAO;
    private final ItemDAO itemDAO;

    public DashboardService() {
        this.customerDAO = new CustomerDAO();
        this.itemDAO = new ItemDAO();
    }

    /**
     * Gathers various statistics for the dashboard API.
     * @return A Map containing the dashboard data.
     */
    public Map<String, Integer> getDashboardStatistics() {
        Map<String, Integer> stats = new HashMap<>();

        // Get the data from the respective DAOs
        int customerCount = customerDAO.getCustomerCount();
        int totalStock = itemDAO.getTotalStockCount();

        // Put the data into the map
        stats.put("customerCount", customerCount);
        stats.put("totalStock", totalStock);

        // In the future, we could add more stats here, like "billsIssuedToday", etc.

        return stats;
    }
}