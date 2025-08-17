package com.example.pahanaedu.service;

import com.example.pahanaedu.dao.ItemDAO;
import com.example.pahanaedu.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Service layer for item-related business logic.
 */
public class ItemService implements IItemService {

    private final ItemDAO itemDAO;

    public ItemService() {
        this.itemDAO = new ItemDAO();
    }

    /**
     * Handles the business logic for adding a new item.
     *
     * @param item The item object to add.
     * @return true if the item was added successfully, false otherwise.
     */
    @Override
    public String addItem(Item item) {
        // --- Business Logic Validation ---
        // Rule 1: Price and stock cannot be negative.
        if (item.getPrice() < 0 || item.getStockQuantity() < 0) {
            return "Validation failed: Price or stock cannot be negative.";
        }
        // Rule 2: Item name cannot already exist.
        if (itemDAO.itemExists(item.getItemName())) {
            return "Validation failed: An item with this name already exists.";
        }

        boolean success = itemDAO.addItem(item);

        return success ? "" : "An unexpected error occurred in the database.";
    }

    /**
     * Handles the business logic for retrieving all items.
     *
     * @return A List of all Item objects.
     */
    @Override
    public List<Item> getAllItems() {
        // Business logic could be added here, such as sorting the items by name.
        return itemDAO.getAllItems();
    }

    /**
     * Handles the business logic for retrieving a single item by its ID.
     *
     * @param id The ID of the item to retrieve.
     * @return The Item object if found, otherwise null.
     */
    @Override
    public Item getItemById(int id) {
        // Business logic could be added here.
        return itemDAO.getItemById(id);
    }

    /**
     * Handles the business logic for updating an existing item.
     *
     * @param item The item object with updated details.
     * @return true if the update was successful, false otherwise.
     */
    @Override
    public boolean updateItem(Item item) {
        // Validation Rule 1: Price and stock cannot be negative.
        if (item.getPrice() < 0 || item.getStockQuantity() < 0) {
            System.err.println("Validation failed: Price or stock cannot be negative.");
            return false;
        }

        // Validation Rule 2: Check for duplicate name, but allow the item to keep its own name.
        Item existingItem = itemDAO.getItemById(item.getItemId());
        // If the name was changed AND the new name already exists in the database...
        if (!existingItem.getItemName().equals(item.getItemName()) && itemDAO.itemExists(item.getItemName())) {
            System.err.println("Validation failed: Another item with this new name already exists.");
            return false;
        }

        // If validation passes, proceed to update.
        return itemDAO.updateItem(item);
    }

    /**
     * Handles the business logic for deleting an item.
     *
     * @param id The ID of the item to delete.
     * @return true if the deletion was successful, false otherwise.
     */
    @Override
    public boolean deleteItem(int id) {
        // Business logic could be added here, e.g., checking if the item is part of
        // an existing customer bill before allowing deletion.
        return itemDAO.deleteItem(id);
    }

    /**
     * Handles the business logic for searching items by name.
     *
     * @param name The search term.
     * @return A List of matching Item objects.
     */
    @Override
    public List<Item> searchItemsByName(String name) {
        // Business logic could be added here, such as logging the search query
        // or handling empty search terms.
        if (name == null || name.trim().isEmpty()) {
            return new ArrayList<>(); // Return an empty list if search term is empty
        }
        return itemDAO.searchItemsByName(name);
    }

    public boolean updateStock(int itemId, int quantitySold) {
        // Business logic could go here, e.g., ensuring quantitySold isn't negative.
        return itemDAO.updateStock(itemId, quantitySold);
    }
}