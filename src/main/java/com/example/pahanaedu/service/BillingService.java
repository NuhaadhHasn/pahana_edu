package com.example.pahanaedu.service;

import com.example.pahanaedu.dao.BillingDAO;
import com.example.pahanaedu.model.Bill;
import com.example.pahanaedu.model.BillItem;
import com.example.pahanaedu.model.Customer;
import com.example.pahanaedu.model.Promotion;
import com.example.pahanaedu.model.Item;
import com.example.pahanaedu.util.ConfigLoader;
import com.example.pahanaedu.strategy.DiscountStrategy;

import java.time.LocalDateTime;
import java.util.List;

public class BillingService implements IBillingService {

    // Add an instance of the new BillingDAO
    private final BillingDAO billingDAO;
    private final ItemService itemService;
    private final NotificationService notificationService;

    public BillingService() {
        // Initialize it in the constructor
        this.billingDAO = new BillingDAO();
        this.itemService = new ItemService();
        this.notificationService = new NotificationService();
    }

    @Override
    public Bill calculateAndSaveBill(Customer customer, List<BillItem> itemsToBill, double taxRate, double serviceCharge, DiscountStrategy discountStrategy) {

        double subTotal = 0.0;
        for (BillItem billItem : itemsToBill) {
            subTotal += billItem.getPriceAtPurchase() * billItem.getQuantity();
        }

        double discountAmount = 0.0;
        if (discountStrategy != null) {
            discountAmount = discountStrategy.applyDiscount(subTotal);
        }
        double taxAmount = subTotal * taxRate;
        double finalTotal = subTotal + taxAmount + serviceCharge - discountAmount;

        Bill finalBill = new Bill();

        if (discountStrategy instanceof Promotion) {
            finalBill.setPromoId(((Promotion) discountStrategy).getPromoId());
        }

        finalBill.setCustomerId(customer.getCustomerId());
        finalBill.setBillDate(LocalDateTime.now());
        finalBill.setSubTotal(subTotal);
        finalBill.setDiscountAmount(discountAmount);
        finalBill.setTotalAmount(finalTotal);
        finalBill.setTaxRateApplied(taxRate);
        finalBill.setBillItems(itemsToBill);
        finalBill.setServiceCharge(serviceCharge);
        finalBill.setStatus("ISSUED");


        // 5. --- NEW STEP: Save the bill to the database ---
        boolean success = billingDAO.saveBill(finalBill);

        if (!success) {
            System.err.println("CRITICAL: Failed to save bill to the database!");
            return null;
        }

        updateInventory(itemsToBill);
        checkStockLevelsAndCreateAlerts(itemsToBill);

        return finalBill;
    }

    /**
     * Handles the business logic for retrieving all saved bills.
     *
     * @return A List of all Bill objects.
     */
    @Override
    public List<Bill> getAllBills() {
        // Business logic could be added here, like fetching full customer details for each bill.
        // For now, we will pass the data directly from the DAO.
        return billingDAO.getAllBills();
    }

    private void checkStockLevelsAndCreateAlerts(List<BillItem> billedItems) {
        final int LOW_STOCK_THRESHOLD = 10; // Define our low stock level

        for (BillItem billedItem : billedItems) {
            // Get the most up-to-date information for the item that was just sold
            Item item = itemService.getItemById(billedItem.getItemId());

            if (item != null && item.getStockQuantity() < LOW_STOCK_THRESHOLD) {
                String message = String.format(
                        "Stock Alert: Stock for item '%s' (ID: %d) is low. Only %d remaining.",
                        item.getItemName(),
                        item.getItemId(),
                        item.getStockQuantity()
                );
                notificationService.createNotification(message);
            }
        }
    }

    private void updateInventory(List<BillItem> billedItems) {
        for (BillItem billedItem : billedItems) {
            itemService.updateStock(billedItem.getItemId(), billedItem.getQuantity());
        }
    }
}