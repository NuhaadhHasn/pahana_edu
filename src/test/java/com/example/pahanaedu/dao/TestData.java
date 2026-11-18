package com.example.pahanaedu.dao;

import com.example.pahanaedu.model.Bill;
import com.example.pahanaedu.model.BillItem;
import com.example.pahanaedu.model.Customer;
import com.example.pahanaedu.model.Item;
import com.example.pahanaedu.model.Promotion;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates the rows a test needs, with names that cannot clash, so that no test depends on
 * rows that happen to exist in one particular database or on the order the tests run in.
 */
final class TestData {

    private TestData() {
    }

    static String unique(String prefix) {
        return prefix + Long.toString(System.nanoTime() % 1_000_000_000L);
    }

    static Item newItem(int stock) {
        Item item = new Item();
        item.setItemName(unique("Test item "));
        item.setDescription("Created by a test");
        item.setPrice(1000.00);
        item.setStockQuantity(stock);
        ItemDAO dao = new ItemDAO();
        if (!dao.addItem(item)) {
            throw new IllegalStateException("could not create the test item");
        }
        for (Item stored : dao.getAllItems()) {
            if (stored.getItemName().equals(item.getItemName())) {
                return stored;
            }
        }
        throw new IllegalStateException("the test item was not found after it was added");
    }

    static Promotion newPromotion() {
        Promotion promo = new Promotion();
        promo.setPromoCode(unique("T"));
        promo.setDiscountPercentage(20.0);
        promo.setActive(true);
        PromotionDAO dao = new PromotionDAO();
        if (!dao.addPromotion(promo)) {
            throw new IllegalStateException("could not create the test promotion");
        }
        for (Promotion stored : dao.getAllPromotions()) {
            if (stored.getPromoCode().equals(promo.getPromoCode())) {
                return stored;
            }
        }
        throw new IllegalStateException("the test promotion was not found after it was added");
    }

    static Customer newCustomer() {
        Customer customer = new Customer();
        customer.setAccountNumber(unique("T"));
        customer.setFullName("Test Customer");
        customer.setAddress("1 Test Road");
        customer.setPhoneNumber("0110000000");
        Customer stored = new CustomerDAO().addCustomerAndReturn(customer);
        if (stored == null) {
            throw new IllegalStateException("could not create the test customer");
        }
        return stored;
    }

    /** Saves a bill of one item for a new customer and returns it, with its new bill id. */
    static Bill newBill(String status) {
        Customer customer = newCustomer();
        Item item = newItem(10);

        Bill bill = new Bill();
        bill.setCustomerId(customer.getCustomerId());
        bill.setBillDate(LocalDateTime.now());
        bill.setSubTotal(1000.00);
        bill.setTaxRateApplied(0.05);
        bill.setDiscountAmount(0.0);
        bill.setTotalAmount(1050.00);
        bill.setStatus(status);

        List<BillItem> billItems = new ArrayList<>();
        BillItem billItem = new BillItem();
        billItem.setItemId(item.getItemId());
        billItem.setQuantity(1);
        billItem.setPriceAtPurchase(1000.00);
        billItems.add(billItem);
        bill.setBillItems(billItems);

        if (!new BillingDAO().saveBill(bill) || bill.getBillId() <= 0) {
            throw new IllegalStateException("could not create the test bill");
        }
        return bill;
    }
}
