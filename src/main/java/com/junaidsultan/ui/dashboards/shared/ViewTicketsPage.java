package com.junaidsultan.ui.dashboards.shared;

import com.junaidsultan.entity.Ticket;
import com.junaidsultan.enums.Role;
import com.junaidsultan.enums.Status;
import com.junaidsultan.service.ITicketService;
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
 * View Tickets Page - Displays tickets based on filter.
 * Used by Students, Staff, and Admins with different contexts.
 */
public class ViewTicketsPage implements Page {
    
    public enum TicketFilter {
        ALL, OPEN, CLOSED, RESOLVED, ON_HOLD, ASSIGNED, UNASSIGNED
    }
    
    private final ITicketService ticketService;
    private final TicketFilter filter;
    private final boolean isAssignedView; // For staff viewing assigned tickets
    
    public ViewTicketsPage(TicketFilter filter) {
        this.ticketService = ServiceLocator.getInstance().getTicketService();
        this.filter = filter;
        this.isAssignedView = false;
    }
    
    public ViewTicketsPage(TicketFilter filter, boolean isAssignedView) {
        this.ticketService = ServiceLocator.getInstance().getTicketService();
        this.filter = filter;
        this.isAssignedView = isAssignedView;
    }
    
    @Override
    public Page show(Screen screen, InputReader input) {
        String userId = Session.getCurrentUser().getId();
        Role userRole = Session.getCurrentUser().getRole();
        
        List<Ticket> tickets = getTickets(userId, userRole);
        
        String title = getTitle();
        String ticketList = DisplayHelper.formatTicketList(tickets);
        
        String content = String.format("""
            ══════════════════════════════════════
            %s
            ══════════════════════════════════════
            Total: %d ticket(s)
            
            %s
            ══════════════════════════════════════
            
            Enter ticket number to view details,
            or 0 to go back.
            """, title, tickets.size(), ticketList);
        
        screen.refresh(title, content, "");
        int choice = input.readInt("");
        
        if (choice == 0) {
            return getBackPage();
        }
        
        if (choice > 0 && choice <= tickets.size()) {
            Ticket selectedTicket = tickets.get(choice - 1);
            return new TicketDetailsPage(selectedTicket.getId(), this);
        }
        
        System.out.println("Invalid selection.");
        input.pause();
        return this;
    }
    
    private List<Ticket> getTickets(String userId, Role role) {
        // Admin sees all tickets based on filter
        if (role == Role.ADMIN) {
            return switch (filter) {
                case OPEN -> ticketService.getTicketsByStatus(Status.OPEN);
                case CLOSED -> ticketService.getTicketsByStatus(Status.CLOSED);
                case RESOLVED -> ticketService.getTicketsByStatus(Status.RESOLVED);
                case ON_HOLD -> ticketService.getTicketsByStatus(Status.ON_HOLD);
                case UNASSIGNED -> ticketService.getUnassignedTickets();
                default -> ticketService.getAllTickets();
            };
        }
        
        // Staff sees assigned tickets
        if (role == Role.STAFF) {
            if (isAssignedView) {
                return switch (filter) {
                    case OPEN -> ticketService.getTicketsByAssignee(userId).stream()
                        .filter(t -> t.getStatus() == Status.OPEN).toList();
                    case CLOSED -> ticketService.getTicketsByAssignee(userId).stream()
                        .filter(t -> t.getStatus() == Status.CLOSED).toList();
                    case RESOLVED -> ticketService.getTicketsByAssignee(userId).stream()
                        .filter(t -> t.getStatus() == Status.RESOLVED).toList();
                    case ON_HOLD -> ticketService.getTicketsByAssignee(userId).stream()
                        .filter(t -> t.getStatus() == Status.ON_HOLD).toList();
                    default -> ticketService.getTicketsByAssignee(userId);
                };
            }
        }
        
        // Students see their own tickets
        return switch (filter) {
            case OPEN -> ticketService.getOpenTickets(userId);
            case CLOSED -> ticketService.getClosedTickets(userId);
            case RESOLVED -> ticketService.getResolvedTickets(userId);
            case ON_HOLD -> ticketService.getTicketsOnHold(userId);
            default -> ticketService.getTicketsByCreator(userId);
        };
    }
    
    private String getTitle() {
        return switch (filter) {
            case OPEN -> "Open Tickets";
            case CLOSED -> "Closed Tickets";
            case RESOLVED -> "Resolved Tickets";
            case ON_HOLD -> "Tickets On Hold";
            case UNASSIGNED -> "Unassigned Tickets";
            case ASSIGNED -> "Assigned Tickets";
            default -> "All Tickets";
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
