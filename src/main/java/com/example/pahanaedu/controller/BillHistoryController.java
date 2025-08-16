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
        List<Bill> billList = billingService.getAllBills();
        request.setAttribute("billList", billList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("bill-history.jsp");
        dispatcher.forward(request, response);
    }
}