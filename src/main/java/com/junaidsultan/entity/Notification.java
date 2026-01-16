package com.junaidsultan.entity;

import java.util.UUID;

public class Notification {
    private String id;
    private String receiverId;
    private String title;
    private String message;
    private boolean isRead;
    private String createdBy; // User ID or NULL for system

    // Constructor for creating a NEW notification
    public Notification(String receiverId, String title, String message, String createdBy) {
        this.id = UUID.randomUUID().toString();
        this.receiverId = receiverId;
        this.title = title;
        this.message = message;
        this.createdBy = createdBy;
        this.isRead = false;
    }

    // Constructor for loading from DB (where ID already exists)
    public Notification(String id, String receiverId, String title, String message, boolean isRead, String createdBy) {
        this.id = id;
        this.receiverId = receiverId;
        this.title = title;
        this.message = message;
        this.isRead = isRead;
        this.createdBy = createdBy;
    }

    // Getters
    public String getId() { return id; }
    public String getReceiverId() { return receiverId; }
    public String getTitle() { return title; }
    public String getMessage() { return message; }
    public boolean isRead() { return isRead; }
    public String getCreatedBy() { return createdBy; }

    public void markAsRead() { this.isRead = true; }
}
