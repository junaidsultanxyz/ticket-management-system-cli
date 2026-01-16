package com.junaidsultan.repository;

import com.junaidsultan.entity.Ticket;
import com.junaidsultan.enums.Priority;
import com.junaidsultan.enums.Status;

import java.util.List;

/**
 * Ticket-specific repository interface extending the base repository.
 * Provides ticket-specific query methods.
 */
public interface ITicketRepository extends BaseRepository<Ticket, String> {
    
    /**
     * Find all tickets created by a specific user.
     * @param userId The creator's user ID
     * @return List of tickets created by the user
     */
    List<Ticket> findByCreatedBy(String userId);
    
    /**
     * Find all tickets assigned to a specific staff member.
     * @param staffId The staff member's user ID
     * @return List of assigned tickets
     */
    List<Ticket> findByAssignedTo(String staffId);
    
    /**
     * Find all tickets with a specific status.
     * @param status The status to filter by
     * @return List of tickets with the specified status
     */
    List<Ticket> findByStatus(Status status);
    
    /**
     * Find tickets by creator and status.
     * @param userId The creator's user ID
     * @param status The status to filter by
     * @return List of matching tickets
     */
    List<Ticket> findByCreatedByAndStatus(String userId, Status status);
    
    /**
     * Find tickets by assignee and status.
     * @param staffId The staff member's user ID
     * @param status The status to filter by
     * @return List of matching tickets
     */
    List<Ticket> findByAssignedToAndStatus(String staffId, Status status);
    
    /**
     * Find all tickets with a specific priority.
     * @param priority The priority to filter by
     * @return List of tickets with the specified priority
     */
    List<Ticket> findByPriority(Priority priority);
    
    /**
     * Update the status of a ticket.
     * @param ticketId The ticket's ID
     * @param status The new status
     * @return true if updated successfully
     */
    boolean updateStatus(String ticketId, Status status);
    
    /**
     * Update the priority of a ticket.
     * @param ticketId The ticket's ID
     * @param priority The new priority
     * @return true if updated successfully
     */
    boolean updatePriority(String ticketId, Priority priority);
    
    /**
     * Assign a ticket to a staff member.
     * @param ticketId The ticket's ID
     * @param staffId The staff member's user ID
     * @return true if assigned successfully
     */
    boolean assignToStaff(String ticketId, String staffId);
    
    /**
     * Find all unassigned tickets.
     * @return List of unassigned tickets
     */
    List<Ticket> findUnassigned();
}
