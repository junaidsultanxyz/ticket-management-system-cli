package com.junaidsultan.ui.dashboards.staff;

import com.junaidsultan.service.INotificationService;
import com.junaidsultan.service.ServiceLocator;
import com.junaidsultan.ui.core.input.InputReader;
import com.junaidsultan.ui.core.screen.Screen;
import com.junaidsultan.ui.dashboards.auth.LoginPage;
import com.junaidsultan.ui.dashboards.shared.NotificationsPage;
import com.junaidsultan.ui.dashboards.shared.ViewTicketsPage;
import com.junaidsultan.ui.scene_manager.Page;
import com.junaidsultan.ui.scene_manager.Session;
import com.junaidsultan.ui.shared.DisplayHelper;

/**
 * Staff Dashboard - Main menu for staff members.
 * Staff can: view assigned tickets, change ticket status, resolve/close tickets.
 */
public class StaffDashboardPage implements Page {
    
    private final INotificationService notificationService;
    
    public StaffDashboardPage() {
        this.notificationService = ServiceLocator.getInstance().getNotificationService();
    }
    
    @Override
    public Page show(Screen screen, InputReader input) {
        String userId = Session.getCurrentUser().getId();
        int unreadCount = notificationService.getUnreadCount(userId);
        
        String header = DisplayHelper.createHeader("Staff Dashboard", unreadCount);
        
        String menu = String.format("""
            ══════════════════════════════════════
            Welcome, %s!
            ══════════════════════════════════════
            
            ASSIGNED TICKETS:
            1. View All Assigned Tickets
            2. View Open Tickets
            3. View Resolved Tickets
            4. View Closed Tickets
            5. View Tickets On Hold
            
            OTHER:
            6. Notifications %s
            0. Logout
            """,
            Session.getCurrentUser().getName(),
            unreadCount > 0 ? "(" + unreadCount + " new)" : ""
        );
        
        screen.refresh(header, menu, "Select Option");
        int choice = input.readInt("");
        
        return switch (choice) {
            case 1 -> new ViewTicketsPage(ViewTicketsPage.TicketFilter.ALL, true);
            case 2 -> new ViewTicketsPage(ViewTicketsPage.TicketFilter.OPEN, true);
            case 3 -> new ViewTicketsPage(ViewTicketsPage.TicketFilter.RESOLVED, true);
            case 4 -> new ViewTicketsPage(ViewTicketsPage.TicketFilter.CLOSED, true);
            case 5 -> new ViewTicketsPage(ViewTicketsPage.TicketFilter.ON_HOLD, true);
            case 6 -> new NotificationsPage();
            case 0 -> {
                Session.logout();
                System.out.println("\n✓ Logged out successfully.");
                input.pause();
                yield new LoginPage();
            }
            default -> {
                System.out.println("Invalid option. Please try again.");
                input.pause();
                yield this;
            }
        };
    }
}
