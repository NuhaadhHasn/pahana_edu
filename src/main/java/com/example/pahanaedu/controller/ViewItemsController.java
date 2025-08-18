package com.example.pahanaedu.controller;

import com.example.pahanaedu.model.Item;
import com.example.pahanaedu.service.IItemService;
import com.example.pahanaedu.service.ServiceFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Controller for the customer-facing "View Available Items" page.
 */
@WebServlet("/view-items")
public class ViewItemsController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IItemService itemService;

    @Override
    public void init() {
        itemService = ServiceFactory.getItemService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // We reuse the existing service method to get all items
        List<Item> itemList = itemService.getAllItems();
        request.setAttribute("itemList", itemList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("view-items.jsp");
        dispatcher.forward(request, response);
    }
}