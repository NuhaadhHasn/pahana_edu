package com.example.pahanaedu.service;

import com.example.pahanaedu.dao.PaymentDAO;
import com.example.pahanaedu.model.Payment;
import com.example.pahanaedu.service.IBillingService;
import com.example.pahanaedu.service.ServiceFactory;

public class PaymentService implements IPaymentService {

    private final PaymentDAO paymentDAO;
    private final IBillingService billingService;

    public PaymentService() {
        this.paymentDAO = new PaymentDAO();
        this.billingService = ServiceFactory.getBillingService();
    }

    @Override
    public boolean recordPayment(Payment payment) {
        if (payment.getAmount() <= 0) {
            return false;
        }

        // Step 1: Record the payment
        boolean paymentRecorded = paymentDAO.recordPayment(payment);

        if (paymentRecorded) {
            // Step 2: If payment was successful, update the bill's status
            return billingService.updateBillStatus(payment.getBillId(), "PAID");
        }

        return false;
    }
}