package com.junaidsultan.entity;

import java.util.UUID;
import java.time.LocalDateTime;

public class Ticket {
    private String id;
    private String title;
    private String description;
    private Priority priority;
    private Status status;
    private String category;
    private String createdBy; // User ID
    private String assignedTo; // User ID
    private LocalDateTime createdAt;

    public enum Priority { LOW, MEDIUM, HIGH }
    public enum Status { OPEN, RESOLVED, CLOSED, ON_HOLD }

    // Constructor for creating a NEW ticket
    public Ticket(String title, String description, Priority priority, String category, String createdBy) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = Status.OPEN; // Default
        this.category = category;
        this.createdBy = createdBy;
        this.createdAt = LocalDateTime.now();
    }

    // Constructor for loading from DB
    public Ticket(String id, String title, String description, String priority, String status,
                  String category, String createdBy, String assignedTo, String createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = Priority.valueOf(priority);
        this.status = Status.valueOf(status);
        this.category = category;
        this.createdBy = createdBy;
        this.assignedTo = assignedTo;
        // Parsing logic for date string would happen in the Service/DAO, keeping Entity simple
        // For simplicity here, we assume String or handle conversion in DAO
    }

    // Getters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Priority getPriority() { return priority; }
    public Status getStatus() { return status; }
    public String getCategory() { return category; }
    public String getCreatedBy() { return createdBy; }
    public String getAssignedTo() { return assignedTo; }

    // Setters for updating
    public void setStatus(Status status) { this.status = status; }
    public void setAssignedTo(String assignedTo) { this.assignedTo = assignedTo; }
}