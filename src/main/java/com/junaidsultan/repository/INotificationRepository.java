package com.junaidsultan.repository;

import com.junaidsultan.entity.Notification;

import java.util.List;

/**
 * Notification-specific repository interface extending the base repository.
 * Provides notification-specific query methods.
 */
public interface INotificationRepository extends BaseRepository<Notification, String> {
    
    /**
     * Find all notifications for a specific user.
     * @param userId The receiver's user ID
     * @return List of notifications for the user
     */
    List<Notification> findByReceiverId(String userId);
    
    /**
     * Find all unread notifications for a specific user.
     * @param userId The receiver's user ID
     * @return List of unread notifications
     */
    List<Notification> findUnreadByReceiverId(String userId);
    
    /**
     * Count unread notifications for a specific user.
     * @param userId The receiver's user ID
     * @return Number of unread notifications
     */
    int countUnreadByReceiverId(String userId);
    
    /**
     * Mark a notification as read.
     * @param notificationId The notification's ID
     * @return true if marked successfully
     */
    boolean markAsRead(String notificationId);
    
    /**
     * Mark all notifications as read for a specific user.
     * @param userId The receiver's user ID
     * @return Number of notifications marked as read
     */
    int markAllAsRead(String userId);
    
    /**
     * Delete all notifications for a specific user.
     * @param userId The receiver's user ID
     * @return Number of notifications deleted
     */
    int deleteAllByReceiverId(String userId);
}
