package com.junaidsultan.service;

import com.junaidsultan.entity.Notification;
import com.junaidsultan.repository.INotificationRepository;
import com.junaidsultan.repository.NotificationRepository;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of INotificationService.
 * Contains business logic for notification operations.
 */
public class NotificationService implements INotificationService {
    
    private final INotificationRepository notificationRepository;
    
    public NotificationService() {
        this.notificationRepository = new NotificationRepository();
    }
    
    // Constructor for dependency injection
    public NotificationService(INotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }
    
    @Override
    public Notification sendNotification(String receiverId, String title, String message, String createdBy) {
        Notification notification = new Notification(receiverId, title, message, createdBy);
        return notificationRepository.save(notification);
    }
    
    @Override
    public int sendBulkNotification(List<String> receiverIds, String title, String message, String createdBy) {
        int count = 0;
        for (String receiverId : receiverIds) {
            Notification notification = sendNotification(receiverId, title, message, createdBy);
            if (notification != null) {
                count++;
            }
        }
        return count;
    }
    
    @Override
    public List<Notification> getNotifications(String userId) {
        return notificationRepository.findByReceiverId(userId);
    }
    
    @Override
    public List<Notification> getUnreadNotifications(String userId) {
        return notificationRepository.findUnreadByReceiverId(userId);
    }
    
    @Override
    public int getUnreadCount(String userId) {
        return notificationRepository.countUnreadByReceiverId(userId);
    }
    
    @Override
    public boolean markAsRead(String notificationId) {
        return notificationRepository.markAsRead(notificationId);
    }
    
    @Override
    public int markAllAsRead(String userId) {
        return notificationRepository.markAllAsRead(userId);
    }
    
    @Override
    public boolean deleteNotification(String notificationId) {
        return notificationRepository.deleteById(notificationId);
    }
    
    @Override
    public int deleteAllNotifications(String userId) {
        return notificationRepository.deleteAllByReceiverId(userId);
    }
    
    @Override
    public Optional<Notification> findById(String notificationId) {
        return notificationRepository.findById(notificationId);
    }
}
