package com.example.pahanaedu.controller;

import com.example.pahanaedu.model.Bill;
import com.example.pahanaedu.model.Customer;
import com.example.pahanaedu.model.User;
import com.example.pahanaedu.service.IBillingService;
import com.example.pahanaedu.service.ServiceFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
                // A customer may only open their own bills.
                HttpSession session = request.getSession(false);
                User currentUser = session != null ? (User) session.getAttribute("user") : null;
                if (currentUser != null && "CUSTOMER".equals(currentUser.getRole())) {
                    Customer own = ServiceFactory.getCustomerService().getCustomerByUserId(currentUser.getUserId());
                    if (own == null || own.getCustomerId() != bill.getCustomerId()) {
                        response.sendError(HttpServletResponse.SC_FORBIDDEN, "This bill belongs to another customer.");
                        return;
                    }
                }
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