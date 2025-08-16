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

@WebServlet("/billing_old")
public class BillingControllerOld extends HttpServlet {
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

        RequestDispatcher dispatcher = request.getRequestDispatcher("create-bill-old.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Processes the submitted form to calculate and display the bill.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Get the selected customer
        int customerId = Integer.parseInt(request.getParameter("customerId"));
        Customer selectedCustomer = customerService.getCustomerById(customerId);

        String applyTaxParam = request.getParameter("applyTax");
        double taxRate = 0.0; // Default to 0 tax
        if (applyTaxParam != null && applyTaxParam.equals("on")) {
            // If the checkbox was checked, get the tax rate from the form
            taxRate = Double.parseDouble(request.getParameter("taxRate")) / 100.0; // Convert percentage to decimal
        }

        double serviceCharge = Double.parseDouble(request.getParameter("serviceCharge"));
        // 2. Build the list of items being purchased
        List<BillItem> itemsToBill = new ArrayList<>();
        // The form will submit an array of item IDs and a corresponding array of quantities
        String[] itemIds = request.getParameterValues("itemIds");
        String[] quantities = request.getParameterValues("quantities");

        if (itemIds != null && quantities != null) {
            for (int i = 0; i < itemIds.length; i++) {
                int quantity = Integer.parseInt(quantities[i]);
                // Only add items where the quantity is greater than 0
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

        // 3. Call the billing service to perform the calculation
        Bill finalBill = billingService.calculateBill(selectedCustomer, itemsToBill, taxRate, serviceCharge);

        // 4. Forward the final bill and customer info to the view page
        request.setAttribute("bill", finalBill);
        request.setAttribute("customer", selectedCustomer);
        RequestDispatcher dispatcher = request.getRequestDispatcher("view-bill.jsp");
        dispatcher.forward(request, response);
    }
}