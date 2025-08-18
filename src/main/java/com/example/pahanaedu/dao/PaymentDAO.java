package com.example.pahanaedu.dao;

import com.example.pahanaedu.model.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class PaymentDAO {

    private static final String INSERT_PAYMENT_SQL = "INSERT INTO payments (bill_id, payment_date, amount, payment_method) VALUES (?, ?, ?, ?);";

    public boolean recordPayment(Payment payment) {
        boolean rowInserted = false;
        Connection connection = null;

        try {
            connection = DBConnection.getInstance().getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PAYMENT_SQL)) {
            preparedStatement.setInt(1, payment.getBillId());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(payment.getPaymentDate()));
            preparedStatement.setDouble(3, payment.getAmount());
            preparedStatement.setString(4, payment.getPaymentMethod());

            rowInserted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowInserted;
    }
}