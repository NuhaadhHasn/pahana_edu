package com.example.pahanaedu.controller;

import com.example.pahanaedu.model.Bill;
import com.example.pahanaedu.service.IBillingService;
import com.example.pahanaedu.service.ServiceFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Handles the request to view the full details of a single bill.
 */
@WebServlet("/view-bill-details")
public class ViewBillDetailsController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IBillingService billingService;

    @Override
    public void init() {
        billingService = ServiceFactory.getBillingService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int billId = Integer.parseInt(request.getParameter("id"));

            // Fetch the full bill object, including its items and customer info
            Bill bill = billingService.getBillById(billId);

            if (bill != null) {
                // Note: A security check should be here to ensure the logged-in customer owns this bill.
                // We will add this with the RBAC filter later.
                request.setAttribute("bill", bill);
                request.setAttribute("customer", bill.getCustomer());

                // We can reuse the same view-bill.jsp that the staff sees
                RequestDispatcher dispatcher = request.getRequestDispatcher("view-bill.jsp");
                dispatcher.forward(request, response);
            } else {
                response.getWriter().println("Error: Bill not found.");
            }
        } catch (NumberFormatException e) {
            response.getWriter().println("Error: Invalid Bill ID.");
        }
    }
}