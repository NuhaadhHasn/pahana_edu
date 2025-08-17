package com.example.pahanaedu.service;

import com.example.pahanaedu.model.Customer;

import java.util.List;

public interface ICustomerService {
    boolean addCustomer(Customer customer);

    Customer createCustomerForBilling(Customer customer);

    List<Customer> getAllCustomers();

    Customer getCustomerByUserId(int userId);

    Customer getCustomerById(int id);

    boolean updateCustomer(Customer customer);

    boolean deleteCustomer(int id);
}