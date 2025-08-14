package com.example.pahanaedu.service;

import com.example.pahanaedu.dao.ItemDAO;
import com.example.pahanaedu.model.Item;

import java.util.List;
/**
 * Service layer for item-related business logic.
 */
public class ItemService {

    private final ItemDAO itemDAO;

    public ItemService() {
        this.itemDAO = new ItemDAO();
    }

    /**
     * Handles the business logic for adding a new item.
     * @param item The item object to add.
     * @return true if the item was added successfully, false otherwise.
     */
    public boolean addItem(Item item) {
        // Business logic and validation can be added here.
        // For example: ensure the price is not negative and stock is not negative.
        if (item.getPrice() < 0 || item.getStockQuantity() < 0) {
            return false; // Invalid data
        }
        return itemDAO.addItem(item);
    }

    /**
     * Handles the business logic for retrieving all items.
     * @return A List of all Item objects.
     */
    public List<Item> getAllItems() {
        // Business logic could be added here, such as sorting the items by name.
        return itemDAO.getAllItems();
    }

    /**
     * Handles the business logic for retrieving a single item by its ID.
     * @param id The ID of the item to retrieve.
     * @return The Item object if found, otherwise null.
     */
    public Item getItemById(int id) {
        // Business logic could be added here.
        return itemDAO.getItemById(id);
    }

    /**
     * Handles the business logic for updating an existing item.
     * @param item The item object with updated details.
     * @return true if the update was successful, false otherwise.
     */
    public boolean updateItem(Item item) {
        // We can reuse the same validation logic from our addItem method.
        if (item.getPrice() < 0 || item.getStockQuantity() < 0) {
            return false; // Invalid data
        }
        return itemDAO.updateItem(item);
    }

    /**
     * Handles the business logic for deleting an item.
     * @param id The ID of the item to delete.
     * @return true if the deletion was successful, false otherwise.
     */
    public boolean deleteItem(int id) {
        // Business logic could be added here, e.g., checking if the item is part of
        // an existing customer bill before allowing deletion.
        return itemDAO.deleteItem(id);
    }

}