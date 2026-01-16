package com.junaidsultan.entity;

import com.junaidsultan.enums.Role;

import java.util.UUID;

public class User {
    private String id;
    private String username;
    private String email;
    private String name;
    private String passwordHash;
    private Role role;

    public User(String username, String email, String name, String passwordHash, Role role) {
        this.id = UUID.randomUUID().toString(); // Auto-generate ID
        this.username = username;
        this.email = email;
        this.name = name;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    // Constructor for loading from DB (where ID already exists)
    public User(String id, String username, String email, String name, String passwordHash, String roleStr) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.name = name;
        this.passwordHash = passwordHash;
        this.role = Role.valueOf(roleStr); // Convert String to Enum
    }

    // Getters and Setters
    public String getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getName() { return name; }
    public String getPasswordHash() { return passwordHash; }
    public Role getRole() { return role; }
}