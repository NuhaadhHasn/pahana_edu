package com.example.pahanaedu.controller;

import com.example.pahanaedu.model.Payment;
import com.example.pahanaedu.service.IPaymentService;
import com.example.pahanaedu.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/record-payment")
public class PaymentController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IPaymentService paymentService;

    @Override
    public void init() {
        paymentService = ServiceFactory.getPaymentService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int billId;
        double amount;
        try {
            billId = Integer.parseInt(request.getParameter("billId"));
            amount = Double.parseDouble(request.getParameter("amount"));
        } catch (NumberFormatException | NullPointerException e) {
            request.getSession().setAttribute("paymentError", "The payment was not recorded: the bill number or the amount is not a number.");
            response.sendRedirect(request.getContextPath() + "/bill-history");
            return;
        }
        String paymentMethod = request.getParameter("paymentMethod");

        Payment payment = new Payment();
        payment.setBillId(billId);
        payment.setAmount(amount);
        payment.setPaymentMethod(paymentMethod);
        payment.setPaymentDate(LocalDateTime.now());

        boolean success = paymentService.recordPayment(payment);
        if (!success) {
            request.getSession().setAttribute("paymentError", "The payment was not recorded. The bill must exist, still be unpaid, and the amount must equal the bill total.");
        }

        // After recording the payment, redirect back to the bill history page
        // to see the updated "PAID" status.
        response.sendRedirect(request.getContextPath() + "/bill-history");
    }
}