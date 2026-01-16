package com.junaidsultan.ui.dashboards.shared;

import com.junaidsultan.entity.Notification;
import com.junaidsultan.enums.Role;
import com.junaidsultan.service.INotificationService;
import com.junaidsultan.service.ServiceLocator;
import com.junaidsultan.ui.core.input.InputReader;
import com.junaidsultan.ui.core.screen.Screen;
import com.junaidsultan.ui.dashboards.admin.AdminDashboardPage;
import com.junaidsultan.ui.dashboards.staff.StaffDashboardPage;
import com.junaidsultan.ui.dashboards.student.StudentDashboardPage;
import com.junaidsultan.ui.scene_manager.Page;
import com.junaidsultan.ui.scene_manager.Session;
import com.junaidsultan.ui.shared.DisplayHelper;

import java.util.List;

/**
 * Notifications Page - View and manage notifications.
 * Available to all user roles.
 */
public class NotificationsPage implements Page {
    
    private final INotificationService notificationService;
    
    public NotificationsPage() {
        this.notificationService = ServiceLocator.getInstance().getNotificationService();
    }
    
    @Override
    public Page show(Screen screen, InputReader input) {
        String userId = Session.getCurrentUser().getId();
        List<Notification> notifications = notificationService.getNotifications(userId);
        int unreadCount = notificationService.getUnreadCount(userId);
        
        String notificationList = DisplayHelper.formatNotificationList(notifications);
        
        String content = String.format("""
            ══════════════════════════════════════
                      NOTIFICATIONS
            ══════════════════════════════════════
            Unread: %d | Total: %d
            
            %s
            ══════════════════════════════════════
            
            Actions:
            1. Mark all as read
            2. View notification details
            3. Delete all notifications
            0. Back
            """, unreadCount, notifications.size(), notificationList);
        
        screen.refresh("Notifications", content, "");
        int choice = input.readInt("");
        
        return switch (choice) {
            case 1 -> {
                int marked = notificationService.markAllAsRead(userId);
                System.out.println("✓ " + marked + " notification(s) marked as read.");
                input.pause();
                yield this;
            }
            case 2 -> {
                if (notifications.isEmpty()) {
                    System.out.println("No notifications to view.");
                    input.pause();
                    yield this;
                }
                int notifChoice = input.readInt("Enter notification number to view:");
                if (notifChoice > 0 && notifChoice <= notifications.size()) {
                    Notification notif = notifications.get(notifChoice - 1);
                    notificationService.markAsRead(notif.getId());
                    System.out.println("\n" + notif.getTitle());
                    System.out.println("-".repeat(40));
                    System.out.println(notif.getMessage());
                }
                input.pause();
                yield this;
            }
            case 3 -> {
                String confirm = input.readString("Delete all notifications? (yes/no):");
                if (confirm.equalsIgnoreCase("yes")) {
                    int deleted = notificationService.deleteAllNotifications(userId);
                    System.out.println("✓ " + deleted + " notification(s) deleted.");
                }
                input.pause();
                yield this;
            }
            case 0 -> getBackPage();
            default -> {
                System.out.println("Invalid option.");
                input.pause();
                yield this;
            }
        };
    }
    
    private Page getBackPage() {
        return switch (Session.getCurrentUser().getRole()) {
            case ADMIN -> new AdminDashboardPage();
            case STAFF -> new StaffDashboardPage();
            case STUDENT -> new StudentDashboardPage();
        };
    }
}
