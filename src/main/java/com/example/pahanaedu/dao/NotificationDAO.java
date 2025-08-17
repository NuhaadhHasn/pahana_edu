package com.example.pahanaedu.dao;

import com.example.pahanaedu.model.Notification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {

    private static final String INSERT_NOTIFICATION_SQL = "INSERT INTO notifications (message) VALUES (?);";
    private static final String SELECT_UNREAD_NOTIFICATIONS_SQL = "SELECT * FROM notifications WHERE is_read = FALSE ORDER BY created_at DESC;";
    private static final String MARK_NOTIFICATION_AS_READ_SQL = "UPDATE notifications SET is_read = TRUE WHERE notification_id = ?;";
    private static final String SELECT_ALL_NOTIFICATIONS_SQL = "SELECT * FROM notifications ORDER BY created_at DESC;";

    public boolean addNotification(Notification notification) {
        boolean rowInserted = false;
        Connection connection = null;

        try {
            connection = DBConnection.getInstance().getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NOTIFICATION_SQL)) {
            preparedStatement.setString(1, notification.getMessage());
            rowInserted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowInserted;
    }

    public List<Notification> getUnreadNotifications() {
        List<Notification> notifications = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            try (Statement statement = connection.createStatement();
                 ResultSet rs = statement.executeQuery(SELECT_UNREAD_NOTIFICATIONS_SQL)) {
                while (rs.next()) {
                    // Use the helper method here
                    notifications.add(mapResultSetToNotification(rs));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return notifications;
    }

    public boolean markAsRead(int notificationId) {
        boolean rowUpdated = false;
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement(MARK_NOTIFICATION_AS_READ_SQL)) {
                preparedStatement.setInt(1, notificationId);
                rowUpdated = preparedStatement.executeUpdate() > 0;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    public List<Notification> getAllNotifications() {
        List<Notification> notifications = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            try (Statement statement = connection.createStatement();
                 ResultSet rs = statement.executeQuery(SELECT_ALL_NOTIFICATIONS_SQL)) {
                while (rs.next()) {
                    notifications.add(mapResultSetToNotification(rs));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return notifications;
    }

    private Notification mapResultSetToNotification(ResultSet rs) throws SQLException {
        Notification notification = new Notification();
        notification.setNotificationId(rs.getInt("notification_id"));
        notification.setMessage(rs.getString("message"));
        notification.setRead(rs.getBoolean("is_read"));
        notification.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return notification;
    }
}