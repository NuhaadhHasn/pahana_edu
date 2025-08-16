package com.example.pahanaedu.controller;

import com.example.pahanaedu.model.Bill;
import com.example.pahanaedu.model.BillItem;
import com.example.pahanaedu.model.Customer;
import com.example.pahanaedu.model.Item;
import com.example.pahanaedu.service.BillingService;
import com.example.pahanaedu.service.CustomerService;
import com.example.pahanaedu.service.ItemService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/billing")
public class BillingController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private CustomerService customerService;
    private ItemService itemService;
    private BillingService billingService;

    @Override
    public void init() {
        customerService = new CustomerService();
        itemService = new ItemService();
        billingService = new BillingService();
    }

    /**
     * Prepares the data needed for the "Create Bill" form.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Fetch all customers to populate a dropdown list.
        List<Customer> customers = customerService.getAllCustomers();
        // Fetch all available items for the user to choose from.
        List<Item> items = itemService.getAllItems();

        request.setAttribute("customers", customers);
        request.setAttribute("items", items);

        RequestDispatcher dispatcher = request.getRequestDispatcher("create-bill.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Processes the submitted form to calculate and display the bill.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // ... (The customer selection logic is the same) ...
        String walkinCustomerName = request.getParameter("walkinCustomerName");
        Customer selectedCustomer;
        if (walkinCustomerName != null && !walkinCustomerName.trim().isEmpty()) {
            selectedCustomer = new Customer();
            selectedCustomer.setFullName(walkinCustomerName);
            selectedCustomer.setAccountNumber(request.getParameter("walkinCustomerAccount"));
            selectedCustomer.setAddress(request.getParameter("walkinCustomerAddress"));
        } else {
            int customerId = Integer.parseInt(request.getParameter("customerId"));
            selectedCustomer = customerService.getCustomerById(customerId);
        }

        // --- NEW TAX LOGIC ---
        String applyTaxParam = request.getParameter("applyTax");
        double taxRate = 0.0; // Default to 0 tax
        if (applyTaxParam != null && applyTaxParam.equals("on")) {
            // If the checkbox was checked, get the tax rate from the form
            taxRate = Double.parseDouble(request.getParameter("taxRate")) / 100.0; // Convert percentage to decimal
        }

        double serviceCharge = Double.parseDouble(request.getParameter("serviceCharge"));

        // ... (The item processing logic is the same) ...
        List<BillItem> itemsToBill = new ArrayList<>();
        // ... (code to populate itemsToBill) ...
        String[] itemIds = request.getParameterValues("itemIds");
        String[] quantities = request.getParameterValues("quantities");
        if (itemIds != null && quantities != null) {
            for (int i = 0; i < itemIds.length; i++) {
                int quantity = Integer.parseInt(quantities[i]);
                if (quantity > 0) {
                    int itemId = Integer.parseInt(itemIds[i]);
                    Item itemDetails = itemService.getItemById(itemId);
                    BillItem billItem = new BillItem();
                    billItem.setItemId(itemId);
                    billItem.setItemName(itemDetails.getItemName());
                    billItem.setQuantity(quantity);
                    billItem.setPriceAtPurchase(itemDetails.getPrice());
                    itemsToBill.add(billItem);
                }
            }
        }

        // Call the service with the new taxRate parameter
        Bill finalBill = billingService.calculateBill(selectedCustomer, itemsToBill, taxRate, serviceCharge);

        request.setAttribute("bill", finalBill);
        request.setAttribute("customer", selectedCustomer);
        RequestDispatcher dispatcher = request.getRequestDispatcher("view-bill.jsp");
        dispatcher.forward(request, response);
    }
}