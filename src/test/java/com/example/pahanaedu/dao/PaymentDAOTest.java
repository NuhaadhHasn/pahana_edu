package com.example.pahanaedu.dao;

import com.example.pahanaedu.model.Payment;
import org.junit.Test;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

public class PaymentDAOTest {

    @Test
    public void testRecordPayment() {
        PaymentDAO paymentDAO = new PaymentDAO();
        Payment payment = new Payment();
        payment.setBillId(2); // Assuming bill with ID 2 exists
        payment.setPaymentDate(LocalDateTime.now());
        payment.setAmount(1050.00);
        payment.setPaymentMethod("CASH");

        boolean result = paymentDAO.recordPayment(payment);
        assertTrue("Payment should be recorded successfully.", result);
    }
}