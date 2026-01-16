package com.junaidsultan.ui.dashboards.admin;

import com.junaidsultan.ui.core.input.InputReader;
import com.junaidsultan.ui.core.screen.Screen;
import com.junaidsultan.ui.dashboards.auth.LoginPage;
import com.junaidsultan.ui.scene_manager.Page;
import com.junaidsultan.ui.scene_manager.Session;

public class AdminDashboardPage implements Page {
    @Override
    public Page show(Screen screen, InputReader input) {
        String menu = """
            1. View All Tickets
            2. Create Notification
            3. Logout
            """;

        screen.refresh("Admin Dashboard", menu, "Select Option");
        int choice = input.readInt("");

        switch (choice) {
//            case 1: return new TicketListPage(); // Go to Ticket List
//            case 2: return new CreateNotificationPage(); // Go to Notification
//            case 3:
//                Session.logout();
//                return new LoginPage(); // Go back to start
            default:
                System.out.println("Invalid option.");
                input.pause();
                return this; // RETURN SELF to stay on this screen
        }
    }
}
