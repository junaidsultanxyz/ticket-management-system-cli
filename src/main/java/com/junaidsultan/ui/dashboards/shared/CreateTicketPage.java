package com.junaidsultan.ui.dashboards.shared;

import com.junaidsultan.entity.Ticket;
import com.junaidsultan.enums.Priority;
import com.junaidsultan.enums.Role;
import com.junaidsultan.service.ITicketService;
import com.junaidsultan.service.ServiceLocator;
import com.junaidsultan.ui.core.input.InputReader;
import com.junaidsultan.ui.core.screen.Screen;
import com.junaidsultan.ui.dashboards.admin.AdminDashboardPage;
import com.junaidsultan.ui.dashboards.student.StudentDashboardPage;
import com.junaidsultan.ui.scene_manager.Page;
import com.junaidsultan.ui.scene_manager.Session;

/**
 * Create Ticket Page - Used by both Students and Admins.
 * Students create tickets for their issues.
 * Admins can create tickets on behalf of students.
 */
public class CreateTicketPage implements Page {
    
    private final ITicketService ticketService;
    
    public CreateTicketPage() {
        this.ticketService = ServiceLocator.getInstance().getTicketService();
    }
    
    @Override
    public Page show(Screen screen, InputReader input) {
        String info = """
            ══════════════════════════════════════
                     CREATE NEW TICKET
            ══════════════════════════════════════
            
            Enter '0' at any prompt to cancel.
            """;
        
        screen.refresh("New Ticket", info, "");
        
        // 1. Title Input
        String title = input.readString("Title:");
        if (title.equals("0")) return getBackPage();
        
        // 2. Description Input
        String description = input.readString("Description:");
        if (description.equals("0")) return getBackPage();
        
        // 3. Category Input
        String category = input.readString("Category (e.g., Technical, Academic, Facility):");
        if (category.equals("0")) return getBackPage();
        
        // 4. Priority Input with Validation
        Priority priority = null;
        while (priority == null) {
            String priorityStr = input.readString("Priority (LOW, MEDIUM, HIGH):").toUpperCase();
            if (priorityStr.equals("0")) return getBackPage();
            
            try {
                priority = Priority.valueOf(priorityStr);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid Priority. Please type LOW, MEDIUM, or HIGH.");
            }
        }
        
        // 5. Create the ticket
        String createdBy = Session.getCurrentUser().getId();
        Ticket ticket = ticketService.createTicket(title, description, priority, category, createdBy);
        
        if (ticket != null) {
            System.out.println("\n✓ Ticket created successfully!");
            System.out.println("  Ticket ID: " + ticket.getId().substring(0, 8));
        } else {
            System.out.println("\n✗ Failed to create ticket. Please try again.");
        }
        
        input.pause();
        return getBackPage();
    }
    
    /**
     * Returns the appropriate dashboard based on user role.
     */
    private Page getBackPage() {
        return switch (Session.getCurrentUser().getRole()) {
            case ADMIN -> new AdminDashboardPage();
            case STUDENT -> new StudentDashboardPage();
            default -> new StudentDashboardPage();
        };
    }
}
