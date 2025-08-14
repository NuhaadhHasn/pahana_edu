package com.example.pahanaedu.dao;

import com.example.pahanaedu.model.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 * Data Access Object for Item-related database operations.
 */
public class ItemDAO {

    private static final String INSERT_ITEM_SQL = "INSERT INTO items (item_name, description, price, stock_quantity) VALUES (?, ?, ?, ?);";
    private static final String SELECT_ALL_ITEMS_SQL = "SELECT * FROM items;";
    private static final String SELECT_ITEM_BY_ID_SQL = "SELECT * FROM items WHERE item_id = ?;";
    private static final String UPDATE_ITEM_SQL = "UPDATE items SET item_name = ?, description = ?, price = ?, stock_quantity = ? WHERE item_id = ?;";
    private static final String DELETE_ITEM_SQL = "DELETE FROM items WHERE item_id = ?;";

    /**
     * Adds a new item to the database.
     * @param item The Item object to add.
     * @return true if the item was added successfully, false otherwise.
     */
    public boolean addItem(Item item) {
        boolean rowInserted = false;
        Connection connection = null;

        try {
            connection = DBConnection.getInstance().getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ITEM_SQL)) {
            preparedStatement.setString(1, item.getItemName());
            preparedStatement.setString(2, item.getDescription());
            preparedStatement.setDouble(3, item.getPrice());
            preparedStatement.setInt(4, item.getStockQuantity());

            rowInserted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowInserted;
    }

    /**
     * Retrieves a list of all items from the database.
     * @return A List of Item objects.
     */
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        Connection connection = null;

        try {
            connection = DBConnection.getInstance().getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return items; // Return empty list on connection failure
        }

        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(SELECT_ALL_ITEMS_SQL)) {

            while (rs.next()) {
                Item item = new Item();
                item.setItemId(rs.getInt("item_id"));
                item.setItemName(rs.getString("item_name"));
                item.setDescription(rs.getString("description"));
                item.setPrice(rs.getDouble("price"));
                item.setStockQuantity(rs.getInt("stock_quantity"));
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    /**
     * Retrieves a single item from the database based on its ID.
     * @param id The ID of the item to retrieve.
     * @return An Item object, or null if not found.
     */
    public Item getItemById(int id) {
        Item item = null;
        Connection connection = null;

        try {
            connection = DBConnection.getInstance().getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ITEM_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                item = new Item();
                item.setItemId(rs.getInt("item_id"));
                item.setItemName(rs.getString("item_name"));
                item.setDescription(rs.getString("description"));
                item.setPrice(rs.getDouble("price"));
                item.setStockQuantity(rs.getInt("stock_quantity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    /**
     * Updates an existing item's record in the database.
     * @param item The Item object containing the updated information.
     * @return true if the update was successful, false otherwise.
     */
    public boolean updateItem(Item item) {
        boolean rowUpdated = false;
        Connection connection = null;

        try {
            connection = DBConnection.getInstance().getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ITEM_SQL)) {
            preparedStatement.setString(1, item.getItemName());
            preparedStatement.setString(2, item.getDescription());
            preparedStatement.setDouble(3, item.getPrice());
            preparedStatement.setInt(4, item.getStockQuantity());
            preparedStatement.setInt(5, item.getItemId());

            rowUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    /**
     * Deletes an item from the database.
     * @param id The ID of the item to delete.
     * @return true if the deletion was successful, false otherwise.
     */
    public boolean deleteItem(int id) {
        boolean rowDeleted = false;
        Connection connection = null;

        try {
            connection = DBConnection.getInstance().getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ITEM_SQL)) {
            preparedStatement.setInt(1, id);
            rowDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }
}