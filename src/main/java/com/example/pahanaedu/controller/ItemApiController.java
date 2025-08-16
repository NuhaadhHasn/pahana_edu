package com.example.pahanaedu.controller;

import com.example.pahanaedu.model.Item;
import com.example.pahanaedu.service.ItemService;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * A Web Service / API endpoint to provide item search results as JSON data.
 */
@WebServlet("/api/search-items")
public class ItemApiController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ItemService itemService;

    @Override
    public void init() {
        itemService = new ItemService();
    }

    /**
     * Handles the GET request for searching items.
     * Expects a URL parameter 'term' (e.g., /api/search-items?term=Java)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Get the search term from the URL parameter.
        String searchTerm = request.getParameter("term");

        // 2. Use the service to find matching items.
        List<Item> foundItems = itemService.searchItemsByName(searchTerm);

        // 3. Convert the List of Item objects into a JSON Array.
        JSONArray jsonArray = new JSONArray();
        for (Item item : foundItems) {
            JSONObject itemJson = new JSONObject();
            itemJson.put("id", item.getItemId());
            itemJson.put("name", item.getItemName());
            itemJson.put("price", item.getPrice());
            jsonArray.put(itemJson);
        }

        // 4. Set the response headers and send the JSON data back.
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonArray.toString());
    }
}