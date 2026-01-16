package com.junaidsultan.ui.dashboards.student;

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
import com.junaidsultan.ui.shared.DisplayHelper;

/**
 * Student Dashboard - Main menu for students.
 * Students can: create tickets, view their tickets, view notifications.
 */
public class StudentDashboardPage implements Page {
    
    private final INotificationService notificationService;
    
    public StudentDashboardPage() {
        this.notificationService = ServiceLocator.getInstance().getNotificationService();
    }
    
    @Override
    public Page show(Screen screen, InputReader input) {
        String userId = Session.getCurrentUser().getId();
        int unreadCount = notificationService.getUnreadCount(userId);
        
        String header = DisplayHelper.createHeader("Student Dashboard", unreadCount);
        
        String menu = String.format("""
            ══════════════════════════════════════
            Welcome, %s!
            ══════════════════════════════════════
            
            1. Create New Ticket
            2. View My Open Tickets
            3. View My Resolved Tickets
            4. View My Closed Tickets
            5. View Tickets On Hold
            6. View All My Tickets
            7. Notifications %s
            0. Logout
            """,
            Session.getCurrentUser().getName(),
            unreadCount > 0 ? "(" + unreadCount + " new)" : ""
        );
        
        screen.refresh(header, menu, "Select Option");
        int choice = input.readInt("");
        
        return switch (choice) {
            case 1 -> new CreateTicketPage();
            case 2 -> new ViewTicketsPage(ViewTicketsPage.TicketFilter.OPEN);
            case 3 -> new ViewTicketsPage(ViewTicketsPage.TicketFilter.RESOLVED);
            case 4 -> new ViewTicketsPage(ViewTicketsPage.TicketFilter.CLOSED);
            case 5 -> new ViewTicketsPage(ViewTicketsPage.TicketFilter.ON_HOLD);
            case 6 -> new ViewTicketsPage(ViewTicketsPage.TicketFilter.ALL);
            case 7 -> new NotificationsPage();
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

