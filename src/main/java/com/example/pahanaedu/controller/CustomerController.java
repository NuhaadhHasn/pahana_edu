package com.example.pahanaedu.controller;

import com.example.pahanaedu.model.Customer;
import com.example.pahanaedu.service.CustomerService;

// You will need to import these classes. IntelliJ will prompt you or you can add them manually.
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controller Servlet for handling customer-related web requests.
 * This servlet will receive data from web forms, use the CustomerService to
 * perform business operations, and then decide which view (JSP page) to show the user.
 */
@WebServlet("/customer-add") // Maps this servlet to the URL: http://.../pahana-edu/customer-add
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
        // 1. Retrieve data from the HTML form
        String accountNumber = request.getParameter("accountNumber");
        String fullName = request.getParameter("fullName");
        String address = request.getParameter("address");
        String phoneNumber = request.getParameter("phoneNumber");

        // 2. Create a Customer model object with the form data
        Customer newCustomer = new Customer();
        newCustomer.setAccountNumber(accountNumber);
        newCustomer.setFullName(fullName);
        newCustomer.setAddress(address);
        newCustomer.setPhoneNumber(phoneNumber);

        // 3. Call the service layer to perform the business logic
        boolean success = customerService.addCustomer(newCustomer);

        // 4. Redirect the user to a success or failure page
        if (success) {
            // If successful, redirect to a simple success page.
            response.sendRedirect("customer-success.jsp");
        } else {
            // If there was an error, redirect to an error page.
            response.sendRedirect("customer-error.jsp");
        }
    }
}