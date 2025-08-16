package com.example.pahanaedu.dao;

import com.example.pahanaedu.model.Bill;
import com.example.pahanaedu.model.BillItem;

import org.junit.Test;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BillingDAOTest {

    /**
     * TDD Test - Red Phase: test saving a complete bill (with its items) to the database.
     * This will fail because the BillingDAO and its saveBill() method do not exist yet.
     */
    @Test
    public void testSaveBill() {
        // 1. Setup: Create a complete bill object to save
        Bill billToSave = new Bill();
        billToSave.setCustomerId(5);
        billToSave.setBillDate(LocalDateTime.now());
        billToSave.setSubTotal(1000.00);
        billToSave.setTaxRateApplied(0.05);
        billToSave.setDiscountAmount(0.0);
        billToSave.setTotalAmount(1050.00);
        billToSave.setStatus("PAID");

        // Create some items for the bill
        List<BillItem> billItems = new ArrayList<>();
        BillItem item1 = new BillItem();
        item1.setItemId(1); // Assuming item with ID 1 exists
        item1.setQuantity(1);
        item1.setPriceAtPurchase(1000.00);
        billItems.add(item1);

        billToSave.setBillItems(billItems);

        // 2. Execution
        BillingDAO billingDAO = new BillingDAO();
        boolean result = billingDAO.saveBill(billToSave);

        // 3. Assertion
        assertTrue("The bill should be saved successfully.", result);
    }

    /**
     * TDD Test - Red Phase: test retrieving all saved bills.
     * This will fail because the getAllBills() method does not exist in BillingDAO yet.
     */
    @Test
    public void testGetAllBills() {
        // 1. Setup
        BillingDAO billingDAO = new BillingDAO();

        // 2. Execution
        List<Bill> allBills = billingDAO.getAllBills();

        // 3. Assertion
        assertNotNull("The list of bills should not be null.", allBills);
        // Assuming we just saved a bill in the previous test, the list shouldn't be empty.
        assertFalse("The list of bills should not be empty.", allBills.isEmpty());
    }
}