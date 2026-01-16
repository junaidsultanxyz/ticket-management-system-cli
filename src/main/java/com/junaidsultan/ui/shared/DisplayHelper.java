package com.junaidsultan.ui.shared;

import com.junaidsultan.entity.Ticket;
import com.junaidsultan.entity.Notification;
import com.junaidsultan.entity.User;

import java.util.List;

/**
 * Utility class for formatting entities for display in CLI.
 * Follows Single Responsibility Principle (SRP).
 */
public class DisplayHelper {
    
    /**
     * Format a single ticket for display.
     */
    public static String formatTicket(Ticket ticket) {
        return String.format(
            "[%s] %s\n  Priority: %s | Status: %s\n  Category: %s",
            ticket.getId().substring(0, 8),
            ticket.getTitle(),
            ticket.getPriority(),
            ticket.getStatus(),
            ticket.getCategory() != null ? ticket.getCategory() : "N/A"
        );
    }
    
    /**
     * Format a ticket for list view (compact).
     */
    public static String formatTicketCompact(Ticket ticket, int index) {
        return String.format(
            "  [%d] [%s] %s - %s (%s)",
            index,
            ticket.getStatus(),
            ticket.getTitle(),
            ticket.getPriority(),
            ticket.getId().substring(0, 8)
        );
    }
    
    /**
     * Format a list of tickets for display.
     */
    public static String formatTicketList(List<Ticket> tickets) {
        if (tickets.isEmpty()) {
            return "  No tickets found.";
        }
        
        StringBuilder sb = new StringBuilder();
        int index = 1;
        for (Ticket ticket : tickets) {
            sb.append(formatTicketCompact(ticket, index++)).append("\n");
        }
        return sb.toString();
    }
    
    /**
     * Format ticket details for full view.
     */
    public static String formatTicketDetails(Ticket ticket) {
        return String.format("""
            +--------------------------------------+
            |         TICKET DETAILS               |
            +--------------------------------------+
              ID:          %s
              Title:       %s
              Description: %s
              Priority:    %s
              Status:      %s
              Category:    %s
              Created By:  %s
              Assigned To: %s
            +--------------------------------------+
            """,
            ticket.getId(),
            ticket.getTitle(),
            ticket.getDescription(),
            ticket.getPriority(),
            ticket.getStatus(),
            ticket.getCategory() != null ? ticket.getCategory() : "N/A",
            ticket.getCreatedBy(),
            ticket.getAssignedTo() != null ? ticket.getAssignedTo() : "Unassigned"
        );
    }
    
    /**
     * Format a notification for display.
     */
    public static String formatNotification(Notification notification, int index) {
        String readStatus = notification.isRead() ? "   " : "[*]";
        return String.format(
            "  [%d] %s %s\n       %s",
            index,
            readStatus,
            notification.getTitle(),
            notification.getMessage()
        );
    }
    
    /**
     * Format a list of notifications for display.
     */
    public static String formatNotificationList(List<Notification> notifications) {
        if (notifications.isEmpty()) {
            return "  No notifications.";
        }
        
        StringBuilder sb = new StringBuilder();
        int index = 1;
        for (Notification notification : notifications) {
            sb.append(formatNotification(notification, index++)).append("\n");
        }
        return sb.toString();
    }
    
    /**
     * Format a user for display (compact).
     */
    public static String formatUserCompact(User user, int index) {
        return String.format(
            "  [%d] %s (%s) - %s [%s]",
            index,
            user.getName(),
            user.getUsername(),
            user.getEmail(),
            user.getRole()
        );
    }
    
    /**
     * Format a list of users for display.
     */
    public static String formatUserList(List<User> users) {
        if (users.isEmpty()) {
            return "  No users found.";
        }
        
        StringBuilder sb = new StringBuilder();
        int index = 1;
        for (User user : users) {
            sb.append(formatUserCompact(user, index++)).append("\n");
        }
        return sb.toString();
    }
    
    /**
     * Format user details for full view.
     */
    public static String formatUserDetails(User user) {
        return String.format("""
            +--------------------------------------+
            |           USER DETAILS               |
            +--------------------------------------+
              ID:       %s
              Username: %s
              Name:     %s
              Email:    %s
              Role:     %s
            +--------------------------------------+
            """,
            user.getId(),
            user.getUsername(),
            user.getName(),
            user.getEmail(),
            user.getRole()
        );
    }
    
    /**
     * Create a header with notification count.
     */
    public static String createHeader(String title, int notificationCount) {
        String notification = notificationCount > 0 
            ? String.format(" [%d notifications]", notificationCount) 
            : "";
        return title + notification;
    }
}
