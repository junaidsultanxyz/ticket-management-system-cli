package com.junaidsultan.repository;

import com.junaidsultan.config.DBConnection;
import com.junaidsultan.entity.Ticket;
import com.junaidsultan.enums.Priority;
import com.junaidsultan.enums.Status;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Concrete implementation of ITicketRepository.
 * Handles all database operations for Ticket entity.
 */
public class TicketRepository implements ITicketRepository {
    
    private final Connection connection;
    
    public TicketRepository() {
        this.connection = DBConnection.getInstance().getConnection();
    }
    
    @Override
    public Ticket save(Ticket ticket) {
        String sql = """
            INSERT INTO tickets (id, title, description, priority, status, category, created_by, assigned_to, created_at)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, datetime('now'))
        """;
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, ticket.getId());
            stmt.setString(2, ticket.getTitle());
            stmt.setString(3, ticket.getDescription());
            stmt.setString(4, ticket.getPriority().name());
            stmt.setString(5, ticket.getStatus().name());
            stmt.setString(6, ticket.getCategory());
            stmt.setString(7, ticket.getCreatedBy());
            stmt.setString(8, ticket.getAssignedTo());
            
            stmt.executeUpdate();
            return ticket;
        } catch (SQLException e) {
            System.err.println("Error saving ticket: " + e.getMessage());
            return null;
        }
    }
    
    @Override
    public Ticket update(Ticket ticket) {
        String sql = """
            UPDATE tickets SET title = ?, description = ?, priority = ?, status = ?, 
            category = ?, assigned_to = ?, updated_at = datetime('now') WHERE id = ?
        """;
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, ticket.getTitle());
            stmt.setString(2, ticket.getDescription());
            stmt.setString(3, ticket.getPriority().name());
            stmt.setString(4, ticket.getStatus().name());
            stmt.setString(5, ticket.getCategory());
            stmt.setString(6, ticket.getAssignedTo());
            stmt.setString(7, ticket.getId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0 ? ticket : null;
        } catch (SQLException e) {
            System.err.println("Error updating ticket: " + e.getMessage());
            return null;
        }
    }
    
    @Override
    public Optional<Ticket> findById(String id) {
        String sql = "SELECT * FROM tickets WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return Optional.of(mapResultSetToTicket(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error finding ticket by id: " + e.getMessage());
        }
        return Optional.empty();
    }
    
    @Override
    public List<Ticket> findAll() {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM tickets ORDER BY created_at DESC";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                tickets.add(mapResultSetToTicket(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error finding all tickets: " + e.getMessage());
        }
        return tickets;
    }
    
    @Override
    public boolean deleteById(String id) {
        String sql = "DELETE FROM tickets WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting ticket: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean existsById(String id) {
        String sql = "SELECT 1 FROM tickets WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            return stmt.executeQuery().next();
        } catch (SQLException e) {
            System.err.println("Error checking ticket existence: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public List<Ticket> findByCreatedBy(String userId) {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM tickets WHERE created_by = ? ORDER BY created_at DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                tickets.add(mapResultSetToTicket(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error finding tickets by creator: " + e.getMessage());
        }
        return tickets;
    }
    
    @Override
    public List<Ticket> findByAssignedTo(String staffId) {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM tickets WHERE assigned_to = ? ORDER BY created_at DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, staffId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                tickets.add(mapResultSetToTicket(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error finding tickets by assignee: " + e.getMessage());
        }
        return tickets;
    }
    
    @Override
    public List<Ticket> findByStatus(Status status) {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM tickets WHERE status = ? ORDER BY created_at DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status.name());
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                tickets.add(mapResultSetToTicket(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error finding tickets by status: " + e.getMessage());
        }
        return tickets;
    }
    
    @Override
    public List<Ticket> findByCreatedByAndStatus(String userId, Status status) {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM tickets WHERE created_by = ? AND status = ? ORDER BY created_at DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, userId);
            stmt.setString(2, status.name());
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                tickets.add(mapResultSetToTicket(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error finding tickets by creator and status: " + e.getMessage());
        }
        return tickets;
    }
    
    @Override
    public List<Ticket> findByAssignedToAndStatus(String staffId, Status status) {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM tickets WHERE assigned_to = ? AND status = ? ORDER BY created_at DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, staffId);
            stmt.setString(2, status.name());
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                tickets.add(mapResultSetToTicket(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error finding tickets by assignee and status: " + e.getMessage());
        }
        return tickets;
    }
    
    @Override
    public List<Ticket> findByPriority(Priority priority) {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM tickets WHERE priority = ? ORDER BY created_at DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, priority.name());
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                tickets.add(mapResultSetToTicket(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error finding tickets by priority: " + e.getMessage());
        }
        return tickets;
    }
    
    @Override
    public boolean updateStatus(String ticketId, Status status) {
        String sql = "UPDATE tickets SET status = ?, updated_at = datetime('now') WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status.name());
            stmt.setString(2, ticketId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating ticket status: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean updatePriority(String ticketId, Priority priority) {
        String sql = "UPDATE tickets SET priority = ?, updated_at = datetime('now') WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, priority.name());
            stmt.setString(2, ticketId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating ticket priority: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean assignToStaff(String ticketId, String staffId) {
        String sql = "UPDATE tickets SET assigned_to = ?, updated_at = datetime('now') WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, staffId);
            stmt.setString(2, ticketId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error assigning ticket to staff: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public List<Ticket> findUnassigned() {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM tickets WHERE assigned_to IS NULL ORDER BY created_at DESC";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                tickets.add(mapResultSetToTicket(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error finding unassigned tickets: " + e.getMessage());
        }
        return tickets;
    }
    
    /**
     * Helper method to map ResultSet to Ticket entity.
     */
    private Ticket mapResultSetToTicket(ResultSet rs) throws SQLException {
        return new Ticket(
            rs.getString("id"),
            rs.getString("title"),
            rs.getString("description"),
            rs.getString("priority"),
            rs.getString("status"),
            rs.getString("category"),
            rs.getString("created_by"),
            rs.getString("assigned_to"),
            rs.getString("created_at")
        );
    }
}
