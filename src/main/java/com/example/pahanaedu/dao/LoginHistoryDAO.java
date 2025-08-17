package com.example.pahanaedu.dao;

import com.example.pahanaedu.model.LoginHistory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class LoginHistoryDAO {

    private static final String INSERT_LOG_SQL = "INSERT INTO login_history (user_id, status) VALUES (?, ?);";
    private static final String SELECT_ALL_LOGS_SQL = "SELECT * FROM login_history ORDER BY login_time DESC;";

    public boolean logAttempt(int userId, String status) {
        boolean rowInserted = false;
        Connection connection = null;

        try {
            connection = DBConnection.getInstance().getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_LOG_SQL)) {
            // If the user ID is valid (>0), set it. Otherwise, set it to NULL (for failed attempts on unknown usernames).
            if (userId > 0) {
                preparedStatement.setInt(1, userId);
            } else {
                preparedStatement.setNull(1, java.sql.Types.INTEGER);
            }
            preparedStatement.setString(2, status);

            rowInserted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowInserted;
    }

    public List<LoginHistory> getAllLoginHistory() {
        List<LoginHistory> logs = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            try (Statement statement = connection.createStatement();
                 ResultSet rs = statement.executeQuery(SELECT_ALL_LOGS_SQL)) {
                while (rs.next()) {
                    LoginHistory log = new LoginHistory();
                    log.setLogId(rs.getInt("log_id"));
                    log.setUserId(rs.getInt("user_id"));
                    log.setLoginTime(rs.getTimestamp("login_time").toLocalDateTime());
                    log.setStatus(rs.getString("status"));
                    logs.add(log);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return logs;
    }
}