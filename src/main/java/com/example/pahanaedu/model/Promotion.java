package com.example.pahanaedu.model;

import com.example.pahanaedu.strategy.DiscountStrategy;

import java.time.LocalDate;

public class Promotion implements DiscountStrategy {
    private int promoId;
    private String promoCode;
    private double discountPercentage;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active;

    public int getPromoId() {
        return promoId;
    }

    public void setPromoId(int promoId) {
        this.promoId = promoId;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * This is the concrete implementation of the applyDiscount method.
     * It calculates the discount amount based on this promotion's percentage.
     *
     * @param subtotal The bill's subtotal before discount.
     * @return The calculated discount amount in currency.
     */
    @Override
    public double applyDiscount(double subtotal) {
        // Only apply discount if the promotion is active
        if (this.isActive()) {
            return subtotal * (this.getDiscountPercentage() / 100.0);
        }
        return 0; // Return zero discount if not active
    }
}