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
import java.util.List;

/**
 * Controller for the customer-facing "My Bills" page.
 */
@WebServlet("/my-bills")
public class MyBillsController extends HttpServlet {
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
        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute("user");

        if (currentUser != null && "CUSTOMER".equals(currentUser.getRole())) {
            // Find the customer record linked to this user
            Customer customer = customerService.getCustomerByUserId(currentUser.getUserId());
            if (customer != null) {
                // Fetch only the bills for this specific customer
                List<Bill> billList = billingService.getBillsByCustomerId(customer.getCustomerId());
                request.setAttribute("billList", billList);
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher("my-bills.jsp");
            dispatcher.forward(request, response);
        } else {
            // If not a customer or not logged in, redirect to login
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}