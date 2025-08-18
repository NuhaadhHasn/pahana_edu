package com.example.pahanaedu.dao;

import com.example.pahanaedu.model.Bill;
import com.example.pahanaedu.model.BillItem;
import com.example.pahanaedu.model.Customer;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class BillingDAO {
    private static final String INSERT_BILL_SQL = "INSERT INTO bills (customer_id, bill_date, sub_total, discount_amount, total_amount, status, promo_id, tax_rate_applied, service_charge) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String INSERT_BILL_ITEMS_SQL = "INSERT INTO bill_items (bill_id, item_id, quantity, price_at_purchase) VALUES (?, ?, ?, ?);";
    private static final String SELECT_ALL_BILLS_SQL = "SELECT * FROM v_bill_details ORDER BY bill_date DESC;";
    private static final String SELECT_BILLS_BY_CUSTOMER_ID_SQL = "SELECT * FROM v_bill_details WHERE customer_id = ? ORDER BY bill_date DESC;";
    private static final String UPDATE_BILL_STATUS_SQL = "UPDATE bills SET status = ? WHERE bill_id = ?;";
    private static final String SELECT_BILL_BY_ID_SQL = "SELECT * FROM v_bill_details WHERE bill_id = ?;";
    private static final String SELECT_BILL_ITEMS_BY_BILL_ID_SQL = "SELECT bi.*, i.item_name FROM bill_items bi JOIN items i ON bi.item_id = i.item_id WHERE bi.bill_id = ?;";

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

                if (bill.getPromoId() > 0) {
                    billStmt.setInt(7, bill.getPromoId());
                } else {
                    billStmt.setNull(7, java.sql.Types.INTEGER);
                }

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
                    bill.setSubTotal(rs.getDouble("sub_total"));
                    bill.setDiscountAmount(rs.getDouble("discount_amount"));
                    bill.setTotalAmount(rs.getDouble("total_amount")); // <-- CHANGE THIS
                    bill.setStatus(rs.getString("status"));           // <-- CHANGE THIS
                    bill.setPromoId(rs.getInt("promo_id"));
                    bill.setCustomerId(rs.getInt("customer_id"));

                    // --- THE FIX: Add the new columns from the view ---
                    bill.setTaxRateApplied(rs.getDouble("tax_rate_applied"));
                    bill.setServiceCharge(rs.getDouble("service_charge"));

                    // Create and attach a simple Customer object for display purposes
                    Customer customer = new Customer();
                    customer.setFullName(rs.getString("customer_name"));
                    customer.setAccountNumber(rs.getString("account_number"));
                    bill.setCustomer(customer);

                    bills.add(bill);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return bills;
    }

    public List<Bill> getBillsByCustomerId(int customerId) {
        List<Bill> bills = new ArrayList<>();
        Connection connection = null;

        try {
            connection = DBConnection.getInstance().getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return bills;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BILLS_BY_CUSTOMER_ID_SQL)) {
            preparedStatement.setInt(1, customerId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Bill bill = new Bill();
                bill.setBillId(rs.getInt("bill_id"));
                bill.setBillDate(rs.getTimestamp("bill_date").toLocalDateTime());
                bill.setTotalAmount(rs.getDouble("total_amount"));
                bill.setStatus(rs.getString("status"));
                bill.setCustomerId(rs.getInt("customer_id"));

                Customer customer = new Customer();
                customer.setFullName(rs.getString("customer_name"));
                customer.setAccountNumber(rs.getString("account_number"));
                bill.setCustomer(customer);

                bills.add(bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bills;
    }

    /**
     * Updates the status of a specific bill in the database.
     *
     * @param billId    The ID of the bill to update.
     * @param newStatus The new status (e.g., "PAID", "CANCELLED").
     * @return true if the update was successful, false otherwise.
     */
    public boolean updateBillStatus(int billId, String newStatus) {
        boolean rowUpdated = false;
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BILL_STATUS_SQL)) {
            preparedStatement.setString(1, newStatus);
            preparedStatement.setInt(2, billId);
            rowUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    public Bill getBillById(int billId) {
        System.out.println("--- BillingDAO: getBillById called for ID: " + billId + " ---"); // DEBUG
        Bill bill = null;
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BILL_BY_ID_SQL)) {
                preparedStatement.setInt(1, billId);
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    bill = new Bill(); // We'll use a helper for this later
                    bill.setBillId(rs.getInt("bill_id"));
                    bill.setSubTotal(rs.getDouble("sub_total"));
                    bill.setDiscountAmount(rs.getDouble("discount_amount"));
                    bill.setTaxRateApplied(rs.getDouble("tax_rate_applied"));
                    bill.setServiceCharge(rs.getDouble("service_charge"));
                    bill.setTotalAmount(rs.getDouble("total_amount"));
                    bill.setPromoId(rs.getInt("promo_id"));

                    System.out.println("DAO FOUND BILL: ID=" + rs.getInt("bill_id"));
                    System.out.println("DAO Subtotal=" + rs.getDouble("sub_total"));
                    System.out.println("DAO Discount=" + rs.getDouble("discount_amount"));
                    System.out.println("DAO Total=" + rs.getDouble("total_amount"));

                    bill.setBillDate(rs.getTimestamp("bill_date").toLocalDateTime());
                    bill.setStatus(rs.getString("status"));
                    bill.setCustomerId(rs.getInt("customer_id"));

                    Customer customer = new Customer();
                    customer.setFullName(rs.getString("customer_name"));
                    customer.setAccountNumber(rs.getString("account_number"));
                    bill.setCustomer(customer);
                } else {
                    System.out.println("DAO did NOT find a bill for ID: " + billId); // DEBUG
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return bill;
    }

    public List<BillItem> getBillItemsByBillId(int billId) {
        List<BillItem> billItems = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BILL_ITEMS_BY_BILL_ID_SQL)) {
                preparedStatement.setInt(1, billId);
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    BillItem item = new BillItem();
                    item.setBillItemId(rs.getInt("bill_item_id"));
                    item.setBillId(rs.getInt("bill_id"));
                    item.setItemId(rs.getInt("item_id"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setPriceAtPurchase(rs.getDouble("price_at_purchase"));
                    item.setItemName(rs.getString("item_name")); // From the JOIN
                    billItems.add(item);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return billItems;
    }
}