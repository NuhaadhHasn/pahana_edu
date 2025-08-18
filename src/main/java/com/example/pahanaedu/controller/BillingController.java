package com.example.pahanaedu.controller;

import com.example.pahanaedu.model.Bill;
import com.example.pahanaedu.model.BillItem;
import com.example.pahanaedu.model.Customer;
import com.example.pahanaedu.model.Item;
import com.example.pahanaedu.model.User;
import com.example.pahanaedu.model.Promotion;
import com.example.pahanaedu.service.IBillingService;
import com.example.pahanaedu.service.ICustomerService;
import com.example.pahanaedu.service.IItemService;
import com.example.pahanaedu.service.IUserService;
import com.example.pahanaedu.service.ServiceFactory;
import com.example.pahanaedu.service.PromotionService;

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

    private ICustomerService customerService;
    private IItemService itemService;
    private IBillingService billingService;
    private IUserService userService;
    private PromotionService promotionService;

    @Override
    public void init() {
        customerService = ServiceFactory.getCustomerService();
        itemService = ServiceFactory.getItemService();
        billingService = ServiceFactory.getBillingService();
        userService = ServiceFactory.getUserService();
        promotionService = new PromotionService();
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
        List<Promotion> promotions = promotionService.getAllPromotions();

        request.setAttribute("customers", customers);
        request.setAttribute("items", items);
        request.setAttribute("promotions", promotions);

        RequestDispatcher dispatcher = request.getRequestDispatcher("create-bill.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Processes the submitted form to calculate and display the bill.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // ... (The customer selection logic is the same) ...
        String isWalkinParam = request.getParameter("isWalkin");
        boolean isWalkin = (isWalkinParam != null && isWalkinParam.equals("on"));
        Customer billCustomer = null;

        if (isWalkin) {
            // --- HANDLE NEW CUSTOMER CREATION ---
            // 1. Create the new Customer object from the form
            Customer newCustomer = new Customer();
            newCustomer.setFullName(request.getParameter("walkinFullName"));
            newCustomer.setAccountNumber(request.getParameter("walkinAccountNumber"));
            newCustomer.setAddress(request.getParameter("walkinAddress"));
            newCustomer.setPhoneNumber(request.getParameter("walkinPhone"));

            // 2. Check if a login account should also be created
            String createAccountParam = request.getParameter("createAccount");
            if (createAccountParam != null && createAccountParam.equals("on")) {
                User newUser = new User();
                newUser.setUsername(request.getParameter("walkinUsername"));
                newUser.setPassword(request.getParameter("walkinPassword"));
                newUser.setFullName(newCustomer.getFullName());
                newUser.setRole("CUSTOMER");

                // Save the new user and get their ID
                User createdUser = userService.addUser(newUser);
                if (createdUser != null) {
                    newCustomer.setUserId(createdUser.getUserId());
                }
            }

            // 3. Save the new customer and get the final object back with its new ID
            billCustomer = customerService.createCustomerForBilling(newCustomer);

        } else {
            // --- HANDLE EXISTING CUSTOMER ---
            int customerId = Integer.parseInt(request.getParameter("customerId"));
            billCustomer = customerService.getCustomerById(customerId);
        }

        // If customer creation failed or wasn't found, stop here.
        if (billCustomer == null) {
            response.getWriter().println("<h1>Error: Could not find or create customer.</h1>");
            return;
        }

        String promoIdStr = request.getParameter("promoId");
        Promotion selectedPromotion = null; // Default to no promotion
        if (promoIdStr != null && !promoIdStr.equals("0")) {
            int promoId = Integer.parseInt(promoIdStr);
            selectedPromotion = promotionService.getPromotionById(promoId);
        }

        // --- NEW TAX LOGIC ---
        double taxRate = 0.0; // Default to zero tax
        String taxRateParam = request.getParameter("taxRate");
        if (taxRateParam != null && !taxRateParam.isEmpty()) {
            double taxPercentage = Double.parseDouble(taxRateParam);
            if (taxPercentage > 0) {
                // Convert the user's input (e.g., 10) into a decimal for calculation (e.g., 0.10)
                taxRate = taxPercentage / 100.0;
            }
        }

        double serviceCharge = Double.parseDouble(request.getParameter("serviceCharge"));

        List<BillItem> itemsToBill = new ArrayList<>();
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

        Bill finalBill = billingService.calculateAndSaveBill(billCustomer, itemsToBill, taxRate, serviceCharge, selectedPromotion);
        if (finalBill == null) {
            response.getWriter().println("<h1>Error: Could not save the bill. Check server logs for details.</h1>");
            return;
        }
        request.setAttribute("bill", finalBill);
        request.setAttribute("customer", billCustomer);
        RequestDispatcher dispatcher = request.getRequestDispatcher("view-bill.jsp");
        dispatcher.forward(request, response);
    }
}