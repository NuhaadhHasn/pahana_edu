package com.example.pahanaedu.controller;

import com.example.pahanaedu.model.DashboardData;
import com.example.pahanaedu.service.DashboardService;
import org.json.JSONObject; // Import the JSONObject class from the org.json library

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * A Web Service / API endpoint to provide dashboard statistics as JSON data.
 */
@WebServlet("/api/dashboard-stats")
public class DashboardApiController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DashboardService dashboardService;

    @Override
    public void init() {
        dashboardService = new DashboardService();
    }

    /**
     * Handles the GET request for dashboard statistics.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Get the statistics from the service layer.
        DashboardData dashboardData = dashboardService.getDashboardData();

        // 2. Convert the Map to a JSON object using the org.json library.
        JSONObject jsonResponse = new JSONObject(dashboardData);

        // 3. Set the response headers to indicate that we are sending JSON data.
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // 4. Write the JSON string to the response output stream.
        response.getWriter().write(jsonResponse.toString());
    }
}