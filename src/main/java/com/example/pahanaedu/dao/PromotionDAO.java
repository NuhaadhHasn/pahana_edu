package com.example.pahanaedu.dao;

import com.example.pahanaedu.model.Promotion;

import java.sql.Connection;
import java.sql.Date; // Use java.sql.Date for JDBC
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PromotionDAO {

    private static final String INSERT_PROMOTION_SQL = "INSERT INTO promotions (promo_code, discount_percentage, start_date, end_date, is_active) VALUES (?, ?, ?, ?, ?);";
    private static final String SELECT_ALL_PROMOTIONS_SQL = "SELECT * FROM promotions;";
    private static final String SELECT_PROMOTION_BY_ID_SQL = "SELECT * FROM promotions WHERE promo_id = ?;";
    private static final String UPDATE_PROMOTION_SQL = "UPDATE promotions SET promo_code = ?, discount_percentage = ?, start_date = ?, end_date = ?, is_active = ? WHERE promo_id = ?;";
    private static final String DELETE_PROMOTION_SQL = "DELETE FROM promotions WHERE promo_id = ?;";

    public boolean addPromotion(Promotion promotion) {
        boolean rowInserted = false;
        Connection connection = null;

        try {
            connection = DBConnection.getInstance().getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PROMOTION_SQL)) {
            preparedStatement.setString(1, promotion.getPromoCode());
            preparedStatement.setDouble(2, promotion.getDiscountPercentage());

            // Convert java.time.LocalDate to java.sql.Date for JDBC
            preparedStatement.setDate(3, promotion.getStartDate() != null ? Date.valueOf(promotion.getStartDate()) : null);
            preparedStatement.setDate(4, promotion.getEndDate() != null ? Date.valueOf(promotion.getEndDate()) : null);

            preparedStatement.setBoolean(5, promotion.isActive());

            rowInserted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowInserted;
    }

    public List<Promotion> getAllPromotions() {
        List<Promotion> promotions = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            try (Statement statement = connection.createStatement();
                 ResultSet rs = statement.executeQuery(SELECT_ALL_PROMOTIONS_SQL)) {
                while (rs.next()) {
                    promotions.add(mapResultSetToPromotion(rs));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return promotions;
    }

    public Promotion getPromotionById(int id) {
        Promotion promotion = null;
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PROMOTION_BY_ID_SQL)) {
                preparedStatement.setInt(1, id);
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    promotion = mapResultSetToPromotion(rs);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return promotion;
    }

    public boolean updatePromotion(Promotion promotion) {
        boolean rowUpdated = false;
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PROMOTION_SQL)) {
                preparedStatement.setString(1, promotion.getPromoCode());
                preparedStatement.setDouble(2, promotion.getDiscountPercentage());
                preparedStatement.setDate(3, promotion.getStartDate() != null ? Date.valueOf(promotion.getStartDate()) : null);
                preparedStatement.setDate(4, promotion.getEndDate() != null ? Date.valueOf(promotion.getEndDate()) : null);
                preparedStatement.setBoolean(5, promotion.isActive());
                preparedStatement.setInt(6, promotion.getPromoId());
                rowUpdated = preparedStatement.executeUpdate() > 0;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    public boolean deletePromotion(int id) {
        boolean rowDeleted = false;
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PROMOTION_SQL)) {
                preparedStatement.setInt(1, id);
                rowDeleted = preparedStatement.executeUpdate() > 0;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }

    // Helper method to avoid duplicating code for mapping a ResultSet row to a Promotion object
    private Promotion mapResultSetToPromotion(ResultSet rs) throws SQLException {
        Promotion promotion = new Promotion();
        promotion.setPromoId(rs.getInt("promo_id"));
        promotion.setPromoCode(rs.getString("promo_code"));
        promotion.setDiscountPercentage(rs.getDouble("discount_percentage"));
        // Handle nullable dates
        Date startDate = rs.getDate("start_date");
        if (startDate != null) {
            promotion.setStartDate(startDate.toLocalDate());
        }
        Date endDate = rs.getDate("end_date");
        if (endDate != null) {
            promotion.setEndDate(endDate.toLocalDate());
        }
        promotion.setActive(rs.getBoolean("is_active"));
        return promotion;
    }
}