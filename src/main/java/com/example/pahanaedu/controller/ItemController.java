package com.example.pahanaedu.controller;

import com.example.pahanaedu.model.Item;
import com.example.pahanaedu.service.ItemService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Controller Servlet for handling all item-related web requests.
 */
@WebServlet("/items") // We will use this base URL for all item actions
public class ItemController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ItemService itemService;

	@Override
	public void init() {
		itemService = new ItemService();
	}

	/**
	 * Handles the submission of the "Add Item" form.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		action = (action == null) ? "add" : action; // Default to add

		switch (action) {
			case "update":
				updateItem(request, response);
				break;
			case "add":
			default:
				addItem(request, response);
				break;
		}
	}

	/**
	 * Handles GET requests. By default, it will list all items.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		action = (action == null) ? "list" : action; // Default to list

		switch (action) {
			case "edit":
				showEditForm(request, response);
				break;
			case "delete":
				deleteItem(request, response);
				break;
			case "list":
			default:
				listItems(request, response);
				break;
		}
	}

	private void listItems(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Item> itemList = itemService.getAllItems();
		request.setAttribute("itemList", itemList);
		RequestDispatcher dispatcher = request.getRequestDispatcher("item-list.jsp");
		dispatcher.forward(request, response);
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Item existingItem = itemService.getItemById(id);
		request.setAttribute("item", existingItem);
		RequestDispatcher dispatcher = request.getRequestDispatcher("item-form.jsp");
		dispatcher.forward(request, response);
	}

	private void addItem(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String itemName = request.getParameter("itemName");
		String description = request.getParameter("description");
		double price = Double.parseDouble(request.getParameter("price"));
		int stockQuantity = Integer.parseInt(request.getParameter("stockQuantity"));

		Item newItem = new Item();
		newItem.setItemName(itemName);
		newItem.setDescription(description);
		newItem.setPrice(price);
		newItem.setStockQuantity(stockQuantity);

		String  errorMessage  = itemService.addItem(newItem);

		if (errorMessage.isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/items");
		} else {
			request.setAttribute("errorMessage", errorMessage);
			RequestDispatcher dispatcher = request.getRequestDispatcher("item-form.jsp");
			dispatcher.forward(request, response);
		}
	}

	private void updateItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int id = Integer.parseInt(request.getParameter("itemId"));
		String itemName = request.getParameter("itemName");
		String description = request.getParameter("description");
		double price = Double.parseDouble(request.getParameter("price"));
		int stockQuantity = Integer.parseInt(request.getParameter("stockQuantity"));

		Item item = new Item();
		item.setItemId(id);
		item.setItemName(itemName);
		item.setDescription(description);
		item.setPrice(price);
		item.setStockQuantity(stockQuantity);

		itemService.updateItem(item);
		response.sendRedirect(request.getContextPath() + "/items");
	}
	// Add this new private method to the ItemController class
	private void deleteItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		itemService.deleteItem(id);
		response.sendRedirect(request.getContextPath() + "/items");
	}
}