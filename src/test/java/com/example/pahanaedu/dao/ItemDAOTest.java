package com.example.pahanaedu.dao;

import com.example.pahanaedu.model.Item;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

public class ItemDAOTest {

    /**
     * TDD Test - Red Phase: test adding a new item.
     * This test will fail to compile because the Item model and ItemDAO do not exist yet.
     */
    @Test
    public void testAddItem() {
        // 1. Setup
        ItemDAO itemDAO = new ItemDAO();
        Item newItem = new Item();
        newItem.setItemName("Advanced Java Programming");
        newItem.setDescription("A book for university students.");
        newItem.setPrice(3500.00);
        newItem.setStockQuantity(50);

        // 2. Execution
        boolean result = itemDAO.addItem(newItem);

        // 3. Assertion
        assertTrue("The item should be added successfully.", result);
    }

    /**
     * TDD Test - Red Phase: test retrieving all items.
     * This will fail because getAllItems() does not exist in ItemDAO yet.
     */
    @Test
    public void testGetAllItems() {
        // 1. Setup
        ItemDAO itemDAO = new ItemDAO();

        // 2. Execution
        List<Item> items = itemDAO.getAllItems();

        // 3. Assertion
        assertNotNull("The item list should not be null.", items);
        assertFalse("The item list should not be empty, as we've added one.", items.isEmpty());
    }

    /**
     * TDD Test - Red Phase: test fetching a single item by its ID.
     * This will fail as getItemById() does not exist in the DAO.
     */
    @Test
    public void testGetItemById() {
        ItemDAO itemDAO = new ItemDAO();
        // Assuming an item with ID 1 exists from our sample data.
        Item item = itemDAO.getItemById(1);

        assertNotNull("Item should not be null for a valid ID.", item);
        assertEquals("The item ID should be 1.", 1, item.getItemId());
    }

    /**
     * TDD Test - Red Phase: test updating an existing item's details.
     * This will fail as updateItem() does not exist in the DAO.
     */
    @Test
    public void testUpdateItem() {
        ItemDAO itemDAO = new ItemDAO();
        // First, get an existing item to update.
        Item item = itemDAO.getItemById(1);
        assertNotNull("Setup failed: Could not retrieve item to update.", item);

        // Change some details
        String updatedDescription = "An updated description for the Java book.";
        item.setDescription(updatedDescription);
        item.setPrice(3750.00);

        // Execute the update
        boolean result = itemDAO.updateItem(item);

        // Assert that the update was successful
        assertTrue("The item should be updated successfully.", result);

        // Verify the update by fetching the data again
        Item updatedItem = itemDAO.getItemById(1);
        assertEquals("The description should be updated.", updatedDescription, updatedItem.getDescription());
        assertEquals("The price should be updated.", 3750.00, updatedItem.getPrice(), 0.001); // Using a delta for double comparison
    }

    /**
     * TDD Test - Red Phase: test deleting an item.
     * This will fail as deleteItem() does not exist in the DAO.
     */
    @Test
    public void testDeleteItem() {
        ItemDAO itemDAO = new ItemDAO();
        int itemIdToDelete = 5; // Assuming an item with ID 1 exists.

        // Execute the delete operation
        boolean result = itemDAO.deleteItem(itemIdToDelete);

        // Assert that the deletion was reported as successful
        assertTrue("The item should be deleted successfully.", result);

        // Verify by trying to fetch the deleted item, it should be null
        Item item = itemDAO.getItemById(itemIdToDelete);
        assertNull("The item should no longer exist in the database.", item);
    }
}