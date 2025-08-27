package com.example.pahanaedu.controller;

import com.example.pahanaedu.service.IReportingService;
import com.example.pahanaedu.service.ServiceFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/api/reports/top-items")
public class ReportsApiController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IReportingService reportingService;

    @Override
    public void init() {
        reportingService = ServiceFactory.getReportingService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the data for the top 5 selling items
        List<Map<String, Object>> topItems = reportingService.getTopSellingItems(5);

        // We need to format this data into a structure that Chart.js understands.
        // Chart.js needs separate arrays for labels (item names) and data (quantities).
        JSONArray labels = new JSONArray();
        JSONArray data = new JSONArray();

        for (Map<String, Object> item : topItems) {
            labels.put(item.get("itemName"));
            data.put(item.get("totalQuantity"));
        }

        // Create a final JSON object to send back to the browser
        JSONObject chartData = new JSONObject();
        chartData.put("labels", labels);
        chartData.put("data", data);

        // Set headers and send the response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(chartData.toString());
    }
}