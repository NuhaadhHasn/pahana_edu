package com.example.pahanaedu.strategy;

/**
 * This represents the abstraction for any discount calculation.
 * This is the "base type" for our Liskov Substitution Principle demonstration.
 */
public interface DiscountStrategy {
    double applyDiscount(double subtotal);
}