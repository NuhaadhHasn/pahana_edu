package com.example.pahanaedu.controller;

import com.example.pahanaedu.model.Bill;
import com.example.pahanaedu.service.BillingService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Controller to handle viewing the list of all saved bills.
 */
@WebServlet("/bill-history")
public class BillHistoryController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BillingService billingService;

    @Override
    public void init() {
        billingService = new BillingService();
    }

    /**
     * Fetches the bill history and forwards it to the JSP for display.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        action = (action == null) ? "list" : action;

        switch (action) {
            case "showPaymentForm":
                showPaymentForm(request, response);
                break;
            case "list":
            default:
                listBillHistory(request, response);
                break;
        }
    }

    private void listBillHistory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Bill> billList = billingService.getAllBills();
        request.setAttribute("billList", billList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("bill-history.jsp");
        dispatcher.forward(request, response);
    }

    // --- ADD THIS NEW METHOD ---
    private void showPaymentForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int billId = Integer.parseInt(request.getParameter("id"));
        Bill bill = billingService.getBillById(billId); // We need a getBillById method!
        request.setAttribute("bill", bill);
        RequestDispatcher dispatcher = request.getRequestDispatcher("record-payment.jsp");
        dispatcher.forward(request, response);
    }
}