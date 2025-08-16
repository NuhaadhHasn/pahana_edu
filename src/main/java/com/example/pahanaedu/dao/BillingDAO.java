package com.example.pahanaedu.dao;

import com.example.pahanaedu.model.Bill;
import com.example.pahanaedu.model.BillItem;
import com.example.pahanaedu.model.Customer;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class BillingDAO {

    private static final String INSERT_BILL_SQL = "INSERT INTO bills (customer_id, bill_date, sub_total, discount_amount, total_amount, status) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String INSERT_BILL_ITEMS_SQL = "INSERT INTO bill_items (bill_id, item_id, quantity, price_at_purchase) VALUES (?, ?, ?, ?);";
    private static final String SELECT_ALL_BILLS_SQL = "SELECT * FROM v_bill_details ORDER BY bill_date DESC;";

    public boolean saveBill(Bill bill) {
        Connection connection = null;
        boolean success = false;

        try {
            connection = DBConnection.getInstance().getConnection();
            // --- Start Transaction ---
            // We turn off auto-commit to manually control the transaction.
            connection.setAutoCommit(false);

            // --- Step 1: Insert into the 'bills' table ---
            // We ask the PreparedStatement to return the auto-generated keys (the new bill_id).
            try (PreparedStatement billStmt = connection.prepareStatement(INSERT_BILL_SQL, Statement.RETURN_GENERATED_KEYS)) {
                billStmt.setInt(1, bill.getCustomerId());
                billStmt.setTimestamp(2, Timestamp.valueOf(bill.getBillDate()));
                billStmt.setDouble(3, bill.getSubTotal());
                billStmt.setDouble(4, bill.getDiscountAmount());
                billStmt.setDouble(5, bill.getTotalAmount());
                billStmt.setString(6, bill.getStatus());

                int affectedRows = billStmt.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Creating bill failed, no rows affected.");
                }

                // --- Step 2: Get the newly created bill_id ---
                try (ResultSet generatedKeys = billStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int billId = generatedKeys.getInt(1);

                        // --- Step 3: Insert into the 'bill_items' table ---
                        try (PreparedStatement billItemsStmt = connection.prepareStatement(INSERT_BILL_ITEMS_SQL)) {
                            for (BillItem item : bill.getBillItems()) {
                                billItemsStmt.setInt(1, billId); // Use the ID from the new bill
                                billItemsStmt.setInt(2, item.getItemId());
                                billItemsStmt.setInt(3, item.getQuantity());
                                billItemsStmt.setDouble(4, item.getPriceAtPurchase());
                                billItemsStmt.addBatch(); // Add this statement to a batch for efficient execution
                            }
                            billItemsStmt.executeBatch(); // Execute all statements in the batch
                        }
                    } else {
                        throw new SQLException("Creating bill failed, no ID obtained.");
                    }
                }
            }

            // --- End Transaction ---
            // If all steps above were successful, we commit the changes to the database.
            connection.commit();
            success = true;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            // If any error occurred, we roll back the entire transaction.
            // This ensures we don't have a bill header with no items, for example.
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            // Always set auto-commit back to true to not affect other DAOs.
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

        return success;
    }

    /**
     * Retrieves a list of all bills from the database using the v_bill_details view.
     *
     * @return A List of Bill objects (partially populated for the list view).
     */
    public List<Bill> getAllBills() {
        List<Bill> bills = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            try (Statement statement = connection.createStatement();
                 ResultSet rs = statement.executeQuery(SELECT_ALL_BILLS_SQL)) {

                while (rs.next()) {
                    Bill bill = new Bill();
                    bill.setBillId(rs.getInt("bill_id"));
                    bill.setBillDate(rs.getTimestamp("bill_date").toLocalDateTime());
                    bill.setTotalAmount(rs.getDouble("bill_total"));
                    bill.setStatus(rs.getString("bill_status"));
                    // We also need the customer ID for the object
                    bill.setCustomerId(rs.getInt("customer_id"));

                    // Create and attach a simple Customer object for display purposes
                    Customer customer = new Customer();
                    customer.setFullName(rs.getString("customer_name"));
                    customer.setAccountNumber(rs.getString("account_number"));
                    bill.setCustomer(customer); // Attach the customer to the bill

                    bills.add(bill);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return bills;
    }

}