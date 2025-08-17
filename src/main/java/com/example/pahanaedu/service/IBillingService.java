package com.example.pahanaedu.service;

import com.example.pahanaedu.model.Bill;
import com.example.pahanaedu.model.BillItem;
import com.example.pahanaedu.model.Customer;
import com.example.pahanaedu.strategy.DiscountStrategy;

import java.util.List;

public interface IBillingService {
    Bill calculateAndSaveBill(Customer customer, List<BillItem> itemsToBill, double taxRate, double serviceCharge, DiscountStrategy discountStrategy);

    List<Bill> getAllBills();

    List<Bill> getBillsByCustomerId(int customerId);
}