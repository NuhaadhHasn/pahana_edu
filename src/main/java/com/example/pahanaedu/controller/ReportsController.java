package com.example.pahanaedu.controller;

import com.example.pahanaedu.service.IReportingService;
import com.example.pahanaedu.service.ServiceFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/reports")
public class ReportsController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IReportingService reportingService;

    @Override
    public void init() {
        reportingService = ServiceFactory.getReportingService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Fetch the data for the reports
        double totalSales = reportingService.getTotalSales();
        List<Map<String, Object>> topItems = reportingService.getTopSellingItems(5); // Get top 5 items

        // Set the data as request attributes
        request.setAttribute("totalSales", totalSales);
        request.setAttribute("topItems", topItems);

        // Forward to the JSP view
        RequestDispatcher dispatcher = request.getRequestDispatcher("reports.jsp");
        dispatcher.forward(request, response);
    }
}