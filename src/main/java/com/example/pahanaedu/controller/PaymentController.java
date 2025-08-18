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
        int billId = Integer.parseInt(request.getParameter("billId"));
        double amount = Double.parseDouble(request.getParameter("amount"));
        String paymentMethod = request.getParameter("paymentMethod");

        Payment payment = new Payment();
        payment.setBillId(billId);
        payment.setAmount(amount);
        payment.setPaymentMethod(paymentMethod);
        payment.setPaymentDate(LocalDateTime.now());

        boolean success = paymentService.recordPayment(payment);

        // After recording the payment, redirect back to the bill history page
        // to see the updated "PAID" status.
        response.sendRedirect(request.getContextPath() + "/bill-history");
    }
}