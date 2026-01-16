package com.junaidsultan.service;

/**
 * Service Locator Pattern implementation.
 * Provides centralized access to all services.
 * Follows the Service Locator design pattern for managing dependencies.
 * 
 * Benefits:
 * - Centralized service management
 * - Easy to swap implementations
 * - Lazy initialization
 * - Thread-safe singleton access
 */
public class ServiceLocator {
    
    private static volatile ServiceLocator instance;
    
    private IUserService userService;
    private ITicketService ticketService;
    private INotificationService notificationService;
    
    private ServiceLocator() {
        // Initialize with default implementations
        this.userService = new UserService();
        this.ticketService = new TicketService();
        this.notificationService = new NotificationService();
    }
    
    public static ServiceLocator getInstance() {
        if (instance == null) {
            synchronized (ServiceLocator.class) {
                if (instance == null) {
                    instance = new ServiceLocator();
                }
            }
        }
        return instance;
    }
    
    // Getters for services
    public IUserService getUserService() {
        return userService;
    }
    
    public ITicketService getTicketService() {
        return ticketService;
    }
    
    public INotificationService getNotificationService() {
        return notificationService;
    }
    
    // Setters for testing or swapping implementations
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }
    
    public void setTicketService(ITicketService ticketService) {
        this.ticketService = ticketService;
    }
    
    public void setNotificationService(INotificationService notificationService) {
        this.notificationService = notificationService;
    }
    
    /**
     * Reset the service locator (useful for testing).
     */
    public static void reset() {
        instance = null;
    }
}
