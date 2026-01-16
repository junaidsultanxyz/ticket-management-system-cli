package com.junaidsultan.service;

import com.junaidsultan.entity.Ticket;
import com.junaidsultan.enums.Priority;
import com.junaidsultan.enums.Status;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for Ticket-related business logic.
 */
public interface ITicketService {
    
    /**
     * Create a new ticket.
     * @param title The ticket title
     * @param description The ticket description
     * @param priority The ticket priority
     * @param category The ticket category
     * @param createdBy The ID of the user creating the ticket
     * @return The created ticket, or null if creation failed
     */
    Ticket createTicket(String title, String description, Priority priority, String category, String createdBy);
    
    /**
     * Find a ticket by its ID.
     * @param ticketId The ticket ID
     * @return Optional containing the ticket if found
     */
    Optional<Ticket> findById(String ticketId);
    
    /**
     * Get all tickets.
     * @return List of all tickets
     */
    List<Ticket> getAllTickets();
    
    /**
     * Get tickets created by a specific user.
     * @param userId The creator's user ID
     * @return List of tickets created by the user
     */
    List<Ticket> getTicketsByCreator(String userId);
    
    /**
     * Get tickets assigned to a specific staff member.
     * @param staffId The staff member's user ID
     * @return List of assigned tickets
     */
    List<Ticket> getTicketsByAssignee(String staffId);
    
    /**
     * Get open tickets for a user.
     * @param userId The user ID
     * @return List of open tickets
     */
    List<Ticket> getOpenTickets(String userId);
    
    /**
     * Get closed tickets for a user.
     * @param userId The user ID
     * @return List of closed tickets
     */
    List<Ticket> getClosedTickets(String userId);
    
    /**
     * Get tickets on hold for a user.
     * @param userId The user ID
     * @return List of tickets on hold
     */
    List<Ticket> getTicketsOnHold(String userId);
    
    /**
     * Get resolved tickets for a user.
     * @param userId The user ID
     * @return List of resolved tickets
     */
    List<Ticket> getResolvedTickets(String userId);
    
    /**
     * Change the status of a ticket.
     * @param ticketId The ticket ID
     * @param newStatus The new status
     * @return true if status was changed successfully
     */
    boolean changeStatus(String ticketId, Status newStatus);
    
    /**
     * Resolve a ticket (sets status to RESOLVED).
     * @param ticketId The ticket ID
     * @return true if resolved successfully
     */
    boolean resolveTicket(String ticketId);
    
    /**
     * Close a ticket (sets status to CLOSED).
     * @param ticketId The ticket ID
     * @return true if closed successfully
     */
    boolean closeTicket(String ticketId);
    
    /**
     * Put a ticket on hold (sets status to ON_HOLD).
     * @param ticketId The ticket ID
     * @return true if put on hold successfully
     */
    boolean putOnHold(String ticketId);
    
    /**
     * Reopen a ticket (sets status to OPEN).
     * @param ticketId The ticket ID
     * @return true if reopened successfully
     */
    boolean reopenTicket(String ticketId);
    
    /**
     * Change the priority of a ticket.
     * @param ticketId The ticket ID
     * @param newPriority The new priority
     * @return true if priority was changed successfully
     */
    boolean changePriority(String ticketId, Priority newPriority);
    
    /**
     * Assign a ticket to a staff member.
     * @param ticketId The ticket ID
     * @param staffId The staff member's user ID
     * @return true if assigned successfully
     */
    boolean assignToStaff(String ticketId, String staffId);
    
    /**
     * Delete a ticket.
     * @param ticketId The ticket ID
     * @return true if deleted successfully
     */
    boolean deleteTicket(String ticketId);
    
    /**
     * Get all unassigned tickets.
     * @return List of unassigned tickets
     */
    List<Ticket> getUnassignedTickets();
    
    /**
     * Get tickets by status.
     * @param status The status to filter by
     * @return List of tickets with the specified status
     */
    List<Ticket> getTicketsByStatus(Status status);
}
