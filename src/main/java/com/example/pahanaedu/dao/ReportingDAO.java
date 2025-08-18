package com.example.pahanaedu.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportingDAO {

    /**
     * Calculates the total sales for a given period (e.g., today).
     *
     * @return The total sum of all 'PAID' bills.
     */
    public double getTotalSales() {
        double totalSales = 0;
        // For simplicity, we calculate total sales of all time.
        // A more advanced version could filter by date.
        String sql = "SELECT SUM(total_amount) FROM bills WHERE status = 'PAID';";
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            try (Statement statement = connection.createStatement();
                 ResultSet rs = statement.executeQuery(sql)) {
                if (rs.next()) {
                    totalSales = rs.getDouble(1);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return totalSales;
    }

    /**
     * Finds the top N best-selling items by quantity sold.
     *
     * @param limit The number of top items to return (e.g., 5).
     * @return A List of Maps, where each map contains item_name and total_quantity.
     */
    public List<Map<String, Object>> getTopSellingItems(int limit) {
        List<Map<String, Object>> topItems = new ArrayList<>();
        String sql = "SELECT i.item_name, SUM(bi.quantity) AS total_quantity " +
                "FROM bill_items bi " +
                "JOIN items i ON bi.item_id = i.item_id " +
                "GROUP BY i.item_name " +
                "ORDER BY total_quantity DESC " +
                "LIMIT ?;";
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, limit);
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    Map<String, Object> itemData = new HashMap<>();
                    itemData.put("itemName", rs.getString("item_name"));
                    itemData.put("totalQuantity", rs.getInt("total_quantity"));
                    topItems.add(itemData);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return topItems;
    }
}