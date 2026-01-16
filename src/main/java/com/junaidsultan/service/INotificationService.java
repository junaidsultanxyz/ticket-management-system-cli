package com.junaidsultan.service;

import com.junaidsultan.entity.Notification;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for Notification-related business logic.
 */
public interface INotificationService {
    
    /**
     * Create and send a notification to a user.
     * @param receiverId The receiver's user ID
     * @param title The notification title
     * @param message The notification message
     * @param createdBy The sender's user ID (or null for system)
     * @return The created notification, or null if creation failed
     */
    Notification sendNotification(String receiverId, String title, String message, String createdBy);
    
    /**
     * Send a notification to multiple users.
     * @param receiverIds List of receiver user IDs
     * @param title The notification title
     * @param message The notification message
     * @param createdBy The sender's user ID (or null for system)
     * @return Number of notifications sent successfully
     */
    int sendBulkNotification(List<String> receiverIds, String title, String message, String createdBy);
    
    /**
     * Get all notifications for a user.
     * @param userId The user ID
     * @return List of notifications for the user
     */
    List<Notification> getNotifications(String userId);
    
    /**
     * Get unread notifications for a user.
     * @param userId The user ID
     * @return List of unread notifications
     */
    List<Notification> getUnreadNotifications(String userId);
    
    /**
     * Get the count of unread notifications for a user.
     * @param userId The user ID
     * @return Number of unread notifications
     */
    int getUnreadCount(String userId);
    
    /**
     * Mark a notification as read.
     * @param notificationId The notification ID
     * @return true if marked successfully
     */
    boolean markAsRead(String notificationId);
    
    /**
     * Mark all notifications as read for a user.
     * @param userId The user ID
     * @return Number of notifications marked as read
     */
    int markAllAsRead(String userId);
    
    /**
     * Delete a notification.
     * @param notificationId The notification ID
     * @return true if deleted successfully
     */
    boolean deleteNotification(String notificationId);
    
    /**
     * Delete all notifications for a user.
     * @param userId The user ID
     * @return Number of notifications deleted
     */
    int deleteAllNotifications(String userId);
    
    /**
     * Find a notification by its ID.
     * @param notificationId The notification ID
     * @return Optional containing the notification if found
     */
    Optional<Notification> findById(String notificationId);
}
