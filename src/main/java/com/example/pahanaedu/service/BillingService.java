package com.example.pahanaedu.service;

import com.example.pahanaedu.util.ConfigLoader;
import com.example.pahanaedu.model.Bill;
import com.example.pahanaedu.model.BillItem;
import com.example.pahanaedu.model.Customer;
import com.example.pahanaedu.model.Item;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Handles the core business logic for creating and calculating bills.
 */
public class BillingService {

    // For this scenario, we'll define the billing logic directly here.
    // In a more complex app, these might come from a database.
    private static final double TAX_RATE = 0.05; // 5% tax
    private static final double SERVICE_CHARGE = 100.00; // A fixed service charge

    /**
     * Calculates the total bill amount based on a list of items.
     * This method encapsulates the entire billing business logic.
     *
     * @param customer The customer for whom the bill is being generated.
     * @param itemsToBill A list of BillItem objects representing the purchase.
     * @return A fully calculated Bill object.
     */
    public Bill calculateBill(Customer customer, List<BillItem> itemsToBill, double taxRate, double serviceCharge) {
        // 1. Calculate the Subtotal
        double subTotal = 0.0;
        for (BillItem billItem : itemsToBill) {
            subTotal += billItem.getPriceAtPurchase() * billItem.getQuantity();
        }

        // 2. Apply Business Rules using the provided taxRate
        double discountAmount = 0.0;
        double taxAmount = subTotal * taxRate; // Calculate tax based on the parameter

        // 3. Calculate the Final Total
        double finalTotal = subTotal + taxAmount + serviceCharge - discountAmount;

        // 4. Assemble the final Bill object
        Bill finalBill = new Bill();
        if (customer.getCustomerId() > 0) {
            finalBill.setCustomerId(customer.getCustomerId());
        }
        finalBill.setBillDate(LocalDateTime.now());
        finalBill.setSubTotal(subTotal);
        finalBill.setDiscountAmount(discountAmount);
        finalBill.setTotalAmount(finalTotal);
        finalBill.setTaxRateApplied(taxRate); // Store the tax rate that was used
        finalBill.setStatus("ISSUED");
        finalBill.setBillItems(itemsToBill);

        return finalBill;
    }
}