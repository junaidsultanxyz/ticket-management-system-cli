package com.junaidsultan.service;

import com.junaidsultan.entity.Ticket;
import com.junaidsultan.enums.Priority;
import com.junaidsultan.enums.Status;
import com.junaidsultan.repository.ITicketRepository;
import com.junaidsultan.repository.TicketRepository;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of ITicketService.
 * Contains business logic for ticket operations.
 */
public class TicketService implements ITicketService {
    
    private final ITicketRepository ticketRepository;
    
    public TicketService() {
        this.ticketRepository = new TicketRepository();
    }
    
    // Constructor for dependency injection
    public TicketService(ITicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }
    
    @Override
    public Ticket createTicket(String title, String description, Priority priority, String category, String createdBy) {
        Ticket ticket = new Ticket(title, description, priority, category, createdBy);
        return ticketRepository.save(ticket);
    }
    
    @Override
    public Optional<Ticket> findById(String ticketId) {
        return ticketRepository.findById(ticketId);
    }
    
    @Override
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }
    
    @Override
    public List<Ticket> getTicketsByCreator(String userId) {
        return ticketRepository.findByCreatedBy(userId);
    }
    
    @Override
    public List<Ticket> getTicketsByAssignee(String staffId) {
        return ticketRepository.findByAssignedTo(staffId);
    }
    
    @Override
    public List<Ticket> getOpenTickets(String userId) {
        return ticketRepository.findByCreatedByAndStatus(userId, Status.OPEN);
    }
    
    @Override
    public List<Ticket> getClosedTickets(String userId) {
        return ticketRepository.findByCreatedByAndStatus(userId, Status.CLOSED);
    }
    
    @Override
    public List<Ticket> getTicketsOnHold(String userId) {
        return ticketRepository.findByCreatedByAndStatus(userId, Status.ON_HOLD);
    }
    
    @Override
    public List<Ticket> getResolvedTickets(String userId) {
        return ticketRepository.findByCreatedByAndStatus(userId, Status.RESOLVED);
    }
    
    @Override
    public boolean changeStatus(String ticketId, Status newStatus) {
        return ticketRepository.updateStatus(ticketId, newStatus);
    }
    
    @Override
    public boolean resolveTicket(String ticketId) {
        return ticketRepository.updateStatus(ticketId, Status.RESOLVED);
    }
    
    @Override
    public boolean closeTicket(String ticketId) {
        return ticketRepository.updateStatus(ticketId, Status.CLOSED);
    }
    
    @Override
    public boolean putOnHold(String ticketId) {
        return ticketRepository.updateStatus(ticketId, Status.ON_HOLD);
    }
    
    @Override
    public boolean reopenTicket(String ticketId) {
        return ticketRepository.updateStatus(ticketId, Status.OPEN);
    }
    
    @Override
    public boolean changePriority(String ticketId, Priority newPriority) {
        return ticketRepository.updatePriority(ticketId, newPriority);
    }
    
    @Override
    public boolean assignToStaff(String ticketId, String staffId) {
        return ticketRepository.assignToStaff(ticketId, staffId);
    }
    
    @Override
    public boolean deleteTicket(String ticketId) {
        return ticketRepository.deleteById(ticketId);
    }
    
    @Override
    public List<Ticket> getUnassignedTickets() {
        return ticketRepository.findUnassigned();
    }
    
    @Override
    public List<Ticket> getTicketsByStatus(Status status) {
        return ticketRepository.findByStatus(status);
    }
}
