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
    private static final String SEARCH_ITEMS_BY_NAME_SQL = "SELECT * FROM items WHERE item_name LIKE ?;";
    private static final String UPDATE_STOCK_SQL = "UPDATE items SET stock_quantity = stock_quantity - ? WHERE item_id = ?;";

    /**
     * Adds a new item to the database.
     *
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
     *
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
     *
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
     *
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
     *
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

    /**
     * Calculates the sum of the stock_quantity for all items.
     *
     * @return The total number of items in stock.
     */
    public int getTotalStockCount() {
        int totalStock = 0;
        String sql = "SELECT SUM(stock_quantity) FROM items;";
        Connection connection = null;

        try {
            connection = DBConnection.getInstance().getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return 0;
        }

        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            if (rs.next()) {
                totalStock = rs.getInt(1); // Get the sum from the first column
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalStock;
    }

    /**
     * Searches for items in the database with a name containing the search term.
     *
     * @param name The search term to look for in item names.
     * @return A List of matching Item objects.
     */
    public List<Item> searchItemsByName(String name) {
        List<Item> items = new ArrayList<>();
        Connection connection = null;

        try {
            connection = DBConnection.getInstance().getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return items; // Return empty list on connection failure
        }

        // The '%' are wildcards for the SQL LIKE clause, meaning "match any characters"
        String searchTerm = "%" + name + "%";

        try (PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_ITEMS_BY_NAME_SQL)) {
            preparedStatement.setString(1, searchTerm);
            ResultSet rs = preparedStatement.executeQuery();

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
     * Checks if an item with the given name already exists in the database.
     * This is used for validation to prevent duplicate item names.
     *
     * @param name The name of the item to check.
     * @return true if an item with that name exists, false otherwise.
     */
    public boolean itemExists(String name) {
        boolean exists = false;
        String sql = "SELECT COUNT(*) FROM items WHERE item_name = ?;";
        Connection connection = null;

        try {
            connection = DBConnection.getInstance().getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return true; // Failsafe: assume it exists if DB connection fails
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                exists = rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }

    public boolean updateStock(int itemId, int quantitySold) {
        boolean rowUpdated = false;
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STOCK_SQL)) {
                preparedStatement.setInt(1, quantitySold);
                preparedStatement.setInt(2, itemId);
                rowUpdated = preparedStatement.executeUpdate() > 0;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }
}