package com.junaidsultan.ui.dashboards.admin;

import com.junaidsultan.service.INotificationService;
import com.junaidsultan.service.ServiceLocator;
import com.junaidsultan.ui.core.input.InputReader;
import com.junaidsultan.ui.core.screen.Screen;
import com.junaidsultan.ui.dashboards.auth.LoginPage;
import com.junaidsultan.ui.dashboards.shared.CreateTicketPage;
import com.junaidsultan.ui.dashboards.shared.NotificationsPage;
import com.junaidsultan.ui.dashboards.shared.ViewTicketsPage;
import com.junaidsultan.ui.scene_manager.Page;
import com.junaidsultan.ui.scene_manager.Session;

/**
 * Admin Dashboard - Main menu for administrators.
 * Admins have full access to all system features.
 */
public class AdminDashboardPage implements Page {
    
    private final INotificationService notificationService;
    
    public AdminDashboardPage() {
        this.notificationService = ServiceLocator.getInstance().getNotificationService();
    }
    
    @Override
    public Page show(Screen screen, InputReader input) {
        String userId = Session.getCurrentUser().getId();
        int unreadCount = notificationService.getUnreadCount(userId);
        
        String notifBadge = unreadCount > 0 ? " (" + unreadCount + " new)" : "";
        
        String menu = String.format("""
            
            Welcome, %s (Admin)
            
            TICKET MANAGEMENT:
            [1]  View All Tickets
            [2]  View Open Tickets
            [3]  View Resolved Tickets
            [4]  View Closed Tickets
            [5]  View Unassigned Tickets
            [6]  Create New Ticket
            
            USER MANAGEMENT:
            [7]  Register Staff
            [8]  Register Student
            [9]  View/Manage Students
            [10] View/Manage Staff
            
            OTHER:
            [11] Send Notification
            [12] My Notifications%s
            [0]  Logout
            """,
            Session.getCurrentUser().getName(),
            notifBadge
        );
        
        screen.refresh("ADMIN DASHBOARD", menu, "Select Option");
        int choice = input.readInt("");
        
        return switch (choice) {
            case 1 -> new ViewTicketsPage(ViewTicketsPage.TicketFilter.ALL);
            case 2 -> new ViewTicketsPage(ViewTicketsPage.TicketFilter.OPEN);
            case 3 -> new ViewTicketsPage(ViewTicketsPage.TicketFilter.RESOLVED);
            case 4 -> new ViewTicketsPage(ViewTicketsPage.TicketFilter.CLOSED);
            case 5 -> new ViewTicketsPage(ViewTicketsPage.TicketFilter.UNASSIGNED);
            case 6 -> new CreateTicketPage();
            case 7 -> new RegisterStaffPage();
            case 8 -> new RegisterStudentPage();
            case 9 -> new ManageUsersPage(ManageUsersPage.UserType.STUDENT);
            case 10 -> new ManageUsersPage(ManageUsersPage.UserType.STAFF);
            case 11 -> new SendNotificationPage();
            case 12 -> new NotificationsPage();
            case 0 -> {
                Session.logout();
                System.out.println("\n[OK] Logged out successfully.");
                input.pause();
                yield new LoginPage();
            }
            default -> {
                System.out.println("[!] Invalid option. Please try again.");
                input.pause();
                yield this;
            }
        };
    }
}

