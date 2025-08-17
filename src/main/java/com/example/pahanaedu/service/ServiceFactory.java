package com.example.pahanaedu.service;

/**
 * Factory Design Pattern Implementation.
 * This class provides a central, static way to get instances of our service implementations.
 * It allows the controllers to depend only on the service interfaces, not the concrete classes,
 * which is the core of the Dependency Inversion Principle.
 */
public class ServiceFactory {

    // Private constructor to prevent anyone from creating an instance of the factory
    private ServiceFactory() {
    }

    public static ICustomerService getCustomerService() {
        return new CustomerService();
    }

    public static IItemService getItemService() {
        return new ItemService();
    }

    public static IUserService getUserService() {
        return new UserService();
    }

    public static IBillingService getBillingService() {
        return new BillingService();
    }

    public static INotificationService getNotificationService() {
        return new NotificationService();
    }
    // We can add more services here in the future.
}