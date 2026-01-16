package com.junaidsultan.entity;

import java.util.UUID;

public class Notification {
    private String id;
    private String receiverId;
    private String title;
    private String message;
    private boolean isRead;
    private String createdBy; // User ID or NULL for system

    public Notification(String receiverId, String title, String message, String createdBy) {
        this.id = UUID.randomUUID().toString();
        this.receiverId = receiverId;
        this.title = title;
        this.message = message;
        this.createdBy = createdBy;
        this.isRead = false;
    }

    // Getters
    public String getId() { return id; }
    public String getReceiverId() { return receiverId; }
    public String getTitle() { return title; }
    public String getMessage() { return message; }
    public boolean isRead() { return isRead; }

    public void markAsRead() { this.isRead = true; }
}