package com.example.pahanaedu.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;

public class Bill {
    private int billId;
    private int customerId;
    private LocalDateTime billDate;
    private double subTotal;
    private double discountAmount;
    private double totalAmount;
    private String status;

    // This is the important part: A Bill object will contain a list of BillItem objects.
    private List<BillItem> billItems;
    private double taxRateApplied;
    private Customer customer;
    private double serviceCharge;

    // --- Getters and Setters for all fields ---

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public LocalDateTime getBillDate() {
        return billDate;
    }

    public void setBillDate(LocalDateTime billDate) {
        this.billDate = billDate;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<BillItem> getBillItems() {
        return billItems;
    }

    public void setBillItems(List<BillItem> billItems) {
        this.billItems = billItems;
    }

    public double getTaxRateApplied() {
        return taxRateApplied;
    }

    public void setTaxRateApplied(double taxRateApplied) {
        this.taxRateApplied = taxRateApplied;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public double getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(double serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    /**
     * Helper method to convert the modern LocalDateTime to the legacy java.util.Date
     * required by the JSTL fmt tag.
     *
     * @return The bill date as a java.util.Date object.
     */
    public Date getBillDateAsDate() {
        if (this.billDate == null) {
            return null;
        }
        return java.sql.Timestamp.valueOf(this.billDate);
    }
}