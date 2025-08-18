package com.example.pahanaedu.controller;

import com.example.pahanaedu.model.Bill;
import com.example.pahanaedu.model.Customer;
import com.example.pahanaedu.model.User;
import com.example.pahanaedu.service.IBillingService;
import com.example.pahanaedu.service.ICustomerService;
import com.example.pahanaedu.service.ServiceFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/make-payment")
public class MakePaymentController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IBillingService billingService;
    private ICustomerService customerService;

    @Override
    public void init() {
        billingService = ServiceFactory.getBillingService();
        customerService = ServiceFactory.getCustomerService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("--- MakePaymentController: doGet started ---"); // DEBUG
        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute("user");

        if (currentUser != null && "CUSTOMER".equals(currentUser.getRole())) {
            System.out.println("User is a customer: " + currentUser.getUsername());
            Customer customer = customerService.getCustomerByUserId(currentUser.getUserId());
            if (customer != null) {
                System.out.println("Found customer record for user. Customer ID: " + customer.getCustomerId());
                // Get all bills for the customer
                List<Bill> allBills = billingService.getBillsByCustomerId(customer.getCustomerId());
                System.out.println("Found " + allBills.size() + " total bills for this customer.");

                List<Bill> unpaidBills = new ArrayList<>();
                for (Bill bill : allBills) {
                    // If the bill's status is NOT "PAID", add it to our new list.
                    if (!"PAID".equals(bill.getStatus())) {
                        unpaidBills.add(bill);
                    }
                }
                System.out.println("Found " + unpaidBills.size() + " UNPAID bills for this customer.");
                request.setAttribute("unpaidBills", unpaidBills);
            } else {
                System.out.println("CRITICAL: Could not find customer record for logged-in user ID: " + currentUser.getUserId()); // DEBUG
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher("make-payment.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}