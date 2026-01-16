package com.junaidsultan.repository;

import com.junaidsultan.config.DBConnection;
import com.junaidsultan.entity.Notification;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Concrete implementation of INotificationRepository.
 * Handles all database operations for Notification entity.
 */
public class NotificationRepository implements INotificationRepository {
    
    private final Connection connection;
    
    public NotificationRepository() {
        this.connection = DBConnection.getInstance().getConnection();
    }
    
    @Override
    public Notification save(Notification notification) {
        String sql = """
            INSERT INTO notifications (id, receiver_id, title, message, is_read, created_by, created_at)
            VALUES (?, ?, ?, ?, ?, ?, datetime('now'))
        """;
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, notification.getId());
            stmt.setString(2, notification.getReceiverId());
            stmt.setString(3, notification.getTitle());
            stmt.setString(4, notification.getMessage());
            stmt.setInt(5, notification.isRead() ? 1 : 0);
            stmt.setString(6, notification.getCreatedBy());
            
            stmt.executeUpdate();
            return notification;
        } catch (SQLException e) {
            System.err.println("Error saving notification: " + e.getMessage());
            return null;
        }
    }
    
    @Override
    public Notification update(Notification notification) {
        String sql = "UPDATE notifications SET title = ?, message = ?, is_read = ? WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, notification.getTitle());
            stmt.setString(2, notification.getMessage());
            stmt.setInt(3, notification.isRead() ? 1 : 0);
            stmt.setString(4, notification.getId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0 ? notification : null;
        } catch (SQLException e) {
            System.err.println("Error updating notification: " + e.getMessage());
            return null;
        }
    }
    
    @Override
    public Optional<Notification> findById(String id) {
        String sql = "SELECT * FROM notifications WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return Optional.of(mapResultSetToNotification(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error finding notification by id: " + e.getMessage());
        }
        return Optional.empty();
    }
    
    @Override
    public List<Notification> findAll() {
        List<Notification> notifications = new ArrayList<>();
        String sql = "SELECT * FROM notifications ORDER BY created_at DESC";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                notifications.add(mapResultSetToNotification(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error finding all notifications: " + e.getMessage());
        }
        return notifications;
    }
    
    @Override
    public boolean deleteById(String id) {
        String sql = "DELETE FROM notifications WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting notification: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean existsById(String id) {
        String sql = "SELECT 1 FROM notifications WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            return stmt.executeQuery().next();
        } catch (SQLException e) {
            System.err.println("Error checking notification existence: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public List<Notification> findByReceiverId(String userId) {
        List<Notification> notifications = new ArrayList<>();
        String sql = "SELECT * FROM notifications WHERE receiver_id = ? ORDER BY created_at DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                notifications.add(mapResultSetToNotification(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error finding notifications by receiver: " + e.getMessage());
        }
        return notifications;
    }
    
    @Override
    public List<Notification> findUnreadByReceiverId(String userId) {
        List<Notification> notifications = new ArrayList<>();
        String sql = "SELECT * FROM notifications WHERE receiver_id = ? AND is_read = 0 ORDER BY created_at DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                notifications.add(mapResultSetToNotification(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error finding unread notifications: " + e.getMessage());
        }
        return notifications;
    }
    
    @Override
    public int countUnreadByReceiverId(String userId) {
        String sql = "SELECT COUNT(*) FROM notifications WHERE receiver_id = ? AND is_read = 0";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error counting unread notifications: " + e.getMessage());
        }
        return 0;
    }
    
    @Override
    public boolean markAsRead(String notificationId) {
        String sql = "UPDATE notifications SET is_read = 1 WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, notificationId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error marking notification as read: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public int markAllAsRead(String userId) {
        String sql = "UPDATE notifications SET is_read = 1 WHERE receiver_id = ? AND is_read = 0";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, userId);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error marking all notifications as read: " + e.getMessage());
            return 0;
        }
    }
    
    @Override
    public int deleteAllByReceiverId(String userId) {
        String sql = "DELETE FROM notifications WHERE receiver_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, userId);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting all notifications: " + e.getMessage());
            return 0;
        }
    }
    
    /**
     * Helper method to map ResultSet to Notification entity.
     */
    private Notification mapResultSetToNotification(ResultSet rs) throws SQLException {
        Notification notification = new Notification(
            rs.getString("receiver_id"),
            rs.getString("title"),
            rs.getString("message"),
            rs.getString("created_by")
        );
        // Set id via reflection or add constructor - for now we'll create a proper constructor
        return new Notification(
            rs.getString("id"),
            rs.getString("receiver_id"),
            rs.getString("title"),
            rs.getString("message"),
            rs.getInt("is_read") == 1,
            rs.getString("created_by")
        );
    }
}
