package com.example.pahanaedu.controller;

import com.example.pahanaedu.model.Customer;
import com.example.pahanaedu.service.CustomerService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Controller Servlet for handling customer-related web requests.
 * This servlet will receive data from web forms, use the CustomerService to
 * perform business operations, and then decide which view (JSP page) to show the user.
 */
@WebServlet("/customers") // Maps this servlet to the URL: http://.../pahana-edu/customer-add
public class CustomerController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CustomerService customerService;

    // The init() method is called by the server once when the servlet is first loaded.
    // It's a great place to initialize our service.
    @Override
    public void init() {
        customerService = new CustomerService();
    }

    /**
     * The doPost method will be called when the user submits the "Add Customer" form
     * because the form's method will be "POST".
     */

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check for a hidden "action" field to decide whether to add or update
        String action = request.getParameter("action");
        action = (action == null) ? "add" : action; // Default to "add" if not specified

        switch (action) {
            case "update":
                updateCustomer(request, response);
                break;
            case "add":
            default:
                addCustomer(request, response);
                break;
        }
    }

    /**
     * Handles GET requests. By default, it will list all customers.
     */

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check for an "action" parameter in the URL
        String action = request.getParameter("action");
        action = (action == null) ? "list" : action; // Default to "list" if no action is specified

        switch (action) {
            case "new":
                showNewForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteCustomer(request, response);
                break;
            case "list":
            default:
                listCustomers(request, response);
                break;
        }
    }

    private void listCustomers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Customer> customerList = customerService.getAllCustomers();
        request.setAttribute("customerList", customerList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("customer-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("customer-form.jsp");
        dispatcher.forward(request, response);
    }
    
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Customer existingCustomer = customerService.getCustomerById(id);
        request.setAttribute("customer", existingCustomer);
        RequestDispatcher dispatcher = request.getRequestDispatcher("customer-form.jsp");
        dispatcher.forward(request, response);
    }

    private void addCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String accountNumber = request.getParameter("accountNumber");
        String fullName = request.getParameter("fullName");
        String address = request.getParameter("address");
        String phoneNumber = request.getParameter("phoneNumber");

        Customer newCustomer = new Customer();
        newCustomer.setAccountNumber(accountNumber);
        newCustomer.setFullName(fullName);
        newCustomer.setAddress(address);
        newCustomer.setPhoneNumber(phoneNumber);

        boolean success = customerService.addCustomer(newCustomer);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/customers");
        } else {
            request.setAttribute("errorMessage", "Failed to add customer. Please try again.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("add-customer.jsp"); // We might merge this later
            dispatcher.forward(request, response);
        }
    }

    private void updateCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("customerId"));
        String accountNumber = request.getParameter("accountNumber");
        String fullName = request.getParameter("fullName");
        String address = request.getParameter("address");
        String phoneNumber = request.getParameter("phoneNumber");

        Customer customer = new Customer();
        customer.setCustomerId(id);
        customer.setAccountNumber(accountNumber);
        customer.setFullName(fullName);
        customer.setAddress(address);
        customer.setPhoneNumber(phoneNumber);

        customerService.updateCustomer(customer);
        response.sendRedirect(request.getContextPath() + "/customers");
    }

    private void deleteCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Get the ID from the URL parameter
        int id = Integer.parseInt(request.getParameter("id"));

        // Call the service to delete the customer
        customerService.deleteCustomer(id);

        // Redirect back to the customer list page to show the result
        response.sendRedirect(request.getContextPath() + "/customers");
    }
}

