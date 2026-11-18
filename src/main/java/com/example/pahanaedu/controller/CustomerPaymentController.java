package com.example.pahanaedu.controller;

import com.example.pahanaedu.model.Bill;
import com.example.pahanaedu.model.Customer;
import com.example.pahanaedu.model.Payment;
import com.example.pahanaedu.model.User;
import com.example.pahanaedu.service.IPaymentService;
import com.example.pahanaedu.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/customer-make-payment")
public class CustomerPaymentController extends HttpServlet {
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
            response.sendRedirect(request.getContextPath() + "/my-bills");
            return;
        }
        String paymentMethod = request.getParameter("paymentMethod");

        // A customer may only pay their own bills.
        User currentUser = (User) request.getSession().getAttribute("user");
        Customer own = currentUser == null ? null : ServiceFactory.getCustomerService().getCustomerByUserId(currentUser.getUserId());
        Bill bill = ServiceFactory.getBillingService().getBillById(billId);
        if (own == null || bill == null || bill.getCustomerId() != own.getCustomerId()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "This bill does not belong to you.");
            return;
        }

        Payment payment = new Payment();
        payment.setBillId(billId);
        payment.setAmount(amount);
        payment.setPaymentMethod(paymentMethod);
        payment.setPaymentDate(LocalDateTime.now());

        if (!paymentService.recordPayment(payment)) {
            request.getSession().setAttribute("paymentError", "The payment was not recorded. The bill must still be unpaid and the amount must equal the bill total.");
        }

        // After payment, redirect the customer back to their bill history to see the "PAID" status
        response.sendRedirect(request.getContextPath() + "/my-bills");
    }
}