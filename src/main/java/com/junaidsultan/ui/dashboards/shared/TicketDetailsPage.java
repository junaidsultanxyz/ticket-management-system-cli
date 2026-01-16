package com.junaidsultan.ui.dashboards.shared;

import com.junaidsultan.entity.Ticket;
import com.junaidsultan.entity.User;
import com.junaidsultan.enums.Priority;
import com.junaidsultan.enums.Role;
import com.junaidsultan.enums.Status;
import com.junaidsultan.service.ITicketService;
import com.junaidsultan.service.IUserService;
import com.junaidsultan.service.ServiceLocator;
import com.junaidsultan.ui.core.input.InputReader;
import com.junaidsultan.ui.core.screen.Screen;
import com.junaidsultan.ui.scene_manager.Page;
import com.junaidsultan.ui.scene_manager.Session;
import com.junaidsultan.ui.shared.DisplayHelper;

import java.util.List;
import java.util.Optional;

/**
 * Ticket Details Page - Shows full ticket info with actions.
 * Different actions available based on user role.
 */
public class TicketDetailsPage implements Page {
    
    private final String ticketId;
    private final Page previousPage;
    private final ITicketService ticketService;
    private final IUserService userService;
    
    public TicketDetailsPage(String ticketId, Page previousPage) {
        this.ticketId = ticketId;
        this.previousPage = previousPage;
        this.ticketService = ServiceLocator.getInstance().getTicketService();
        this.userService = ServiceLocator.getInstance().getUserService();
    }
    
    @Override
    public Page show(Screen screen, InputReader input) {
        Optional<Ticket> ticketOpt = ticketService.findById(ticketId);
        
        if (ticketOpt.isEmpty()) {
            System.out.println("Ticket not found.");
            input.pause();
            return previousPage;
        }
        
        Ticket ticket = ticketOpt.get();
        Role userRole = Session.getCurrentUser().getRole();
        
        String details = DisplayHelper.formatTicketDetails(ticket);
        String menu = getMenuForRole(userRole, ticket);
        
        screen.refresh("Ticket Details", details + "\n" + menu, "");
        int choice = input.readInt("");
        
        return handleChoice(choice, ticket, userRole, input);
    }
    
    private String getMenuForRole(Role role, Ticket ticket) {
        StringBuilder menu = new StringBuilder();
        menu.append("Actions:\n");
        
        switch (role) {
            case STUDENT -> {
                menu.append("0. Back\n");
            }
            case STAFF -> {
                menu.append("1. Change Status\n");
                menu.append("2. Resolve Ticket\n");
                menu.append("3. Close Ticket\n");
                menu.append("0. Back\n");
            }
            case ADMIN -> {
                menu.append("1. Change Status\n");
                menu.append("2. Change Priority\n");
                menu.append("3. Assign to Staff\n");
                menu.append("4. Resolve Ticket\n");
                menu.append("5. Close Ticket\n");
                menu.append("6. Delete Ticket\n");
                menu.append("0. Back\n");
            }
        }
        
        return menu.toString();
    }
    
    private Page handleChoice(int choice, Ticket ticket, Role role, InputReader input) {
        if (choice == 0) return previousPage;
        
        switch (role) {
            case STUDENT -> {
                return previousPage; // Students can only view
            }
            case STAFF -> {
                return handleStaffChoice(choice, ticket, input);
            }
            case ADMIN -> {
                return handleAdminChoice(choice, ticket, input);
            }
        }
        
        return this;
    }
    
    private Page handleStaffChoice(int choice, Ticket ticket, InputReader input) {
        switch (choice) {
            case 1 -> changeStatus(ticket, input);
            case 2 -> resolveTicket(ticket, input);
            case 3 -> closeTicket(ticket, input);
            default -> {
                System.out.println("Invalid option.");
                input.pause();
            }
        }
        return this;
    }
    
    private Page handleAdminChoice(int choice, Ticket ticket, InputReader input) {
        switch (choice) {
            case 1 -> changeStatus(ticket, input);
            case 2 -> changePriority(ticket, input);
            case 3 -> assignToStaff(ticket, input);
            case 4 -> resolveTicket(ticket, input);
            case 5 -> closeTicket(ticket, input);
            case 6 -> {
                if (deleteTicket(ticket, input)) {
                    return previousPage;
                }
            }
            default -> {
                System.out.println("Invalid option.");
                input.pause();
            }
        }
        return this;
    }
    
    private void changeStatus(Ticket ticket, InputReader input) {
        System.out.println("\nAvailable statuses: OPEN, RESOLVED, CLOSED, ON_HOLD");
        String statusStr = input.readString("Enter new status:").toUpperCase();
        
        try {
            Status newStatus = Status.valueOf(statusStr);
            if (ticketService.changeStatus(ticket.getId(), newStatus)) {
                System.out.println("[OK] Status updated successfully.");
            } else {
                System.out.println("[X] Failed to update status.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("[X] Invalid status.");
        }
        input.pause();
    }
    
    private void changePriority(Ticket ticket, InputReader input) {
        System.out.println("\nAvailable priorities: LOW, MEDIUM, HIGH");
        String priorityStr = input.readString("Enter new priority:").toUpperCase();
        
        try {
            Priority newPriority = Priority.valueOf(priorityStr);
            if (ticketService.changePriority(ticket.getId(), newPriority)) {
                System.out.println("[OK] Priority updated successfully.");
            } else {
                System.out.println("[X] Failed to update priority.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("[X] Invalid priority.");
        }
        input.pause();
    }
    
    private void assignToStaff(Ticket ticket, InputReader input) {
        List<User> staffList = userService.getAllStaff();
        
        if (staffList.isEmpty()) {
            System.out.println("No staff members available.");
            input.pause();
            return;
        }
        
        System.out.println("\nAvailable Staff:");
        System.out.println(DisplayHelper.formatUserList(staffList));
        
        int staffChoice = input.readInt("Select staff number (0 to cancel):");
        
        if (staffChoice == 0) return;
        
        if (staffChoice > 0 && staffChoice <= staffList.size()) {
            User selectedStaff = staffList.get(staffChoice - 1);
            if (ticketService.assignToStaff(ticket.getId(), selectedStaff.getId())) {
                System.out.println("[OK] Ticket assigned to " + selectedStaff.getName());
            } else {
                System.out.println("[X] Failed to assign ticket.");
            }
        } else {
            System.out.println("[X] Invalid selection.");
        }
        input.pause();
    }
    
    private void resolveTicket(Ticket ticket, InputReader input) {
        if (ticketService.resolveTicket(ticket.getId())) {
            System.out.println("[OK] Ticket resolved successfully.");
        } else {
            System.out.println("[X] Failed to resolve ticket.");
        }
        input.pause();
    }
    
    private void closeTicket(Ticket ticket, InputReader input) {
        if (ticketService.closeTicket(ticket.getId())) {
            System.out.println("[OK] Ticket closed successfully.");
        } else {
            System.out.println("[X] Failed to close ticket.");
        }
        input.pause();
    }
    
    private boolean deleteTicket(Ticket ticket, InputReader input) {
        String confirm = input.readString("Are you sure you want to delete this ticket? (yes/no):");
        
        if (confirm.equalsIgnoreCase("yes")) {
            if (ticketService.deleteTicket(ticket.getId())) {
                System.out.println("[OK] Ticket deleted successfully.");
                input.pause();
                return true;
            } else {
                System.out.println("[X] Failed to delete ticket.");
            }
        } else {
            System.out.println("Deletion cancelled.");
        }
        input.pause();
        return false;
    }
}
