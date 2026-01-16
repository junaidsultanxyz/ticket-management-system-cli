package com.junaidsultan.ui.dashboards.shared;

import com.junaidsultan.enums.Role;
import com.junaidsultan.ui.core.input.InputReader;
import com.junaidsultan.ui.core.screen.Screen;
import com.junaidsultan.ui.dashboards.admin.AdminDashboardPage;
import com.junaidsultan.ui.dashboards.student.StudentDashboardPage;
import com.junaidsultan.ui.scene_manager.Page;
import com.junaidsultan.ui.scene_manager.Session;

public class CreateTicketPage implements Page {
    @Override
    public Page show(Screen screen, InputReader input) {
        screen.refresh("New Ticket", "Fill in details below.", "Title");

        // 1. Title Input
        String title = input.readString("Title:");

        // 2. Priority Input with Validation
        String priority = "";
        while (true) {
            priority = input.readString("Priority (LOW, MEDIUM, HIGH):").toUpperCase();
            if (priority.equals("LOW") || priority.equals("MEDIUM") || priority.equals("HIGH")) {
                break; // Valid!
            }
            System.out.println("Invalid Priority. Please type LOW, MEDIUM, or HIGH.");
        }

        // 3. Save Logic
        System.out.println("Creating ticket: " + title + " [" + priority + "]");
        // TicketService.create(...)
        input.pause();

        // 4. Return to Dashboard (using Session to know which one)
        if (Session.getCurrentUser().getRole() == Role.ADMIN) {
            return new AdminDashboardPage();
        }
        return new StudentDashboardPage();
    }
}