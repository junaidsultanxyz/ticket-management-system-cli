package com.junaidsultan;

import com.junaidsultan.config.DBConnection;
import com.junaidsultan.ui.core.input.InputReader;
import com.junaidsultan.ui.core.screen.Screen;
import com.junaidsultan.ui.dashboards.auth.LoginPage;
import com.junaidsultan.ui.scene_manager.Page;
import com.junaidsultan.util.DatabaseSeeder;

/**
 * Main Application Entry Point.
 * Ticket Management System - A CLI-based issue tracking system.
 * 
 * Features:
 * - Role-based access control (Admin, Staff, Student)
 * - Ticket creation, assignment, and tracking
 * - Notification system
 * - User management (Admin only)
 * 
 * Design Patterns Used:
 * - Singleton Pattern (DBConnection, ServiceLocator)
 * - Repository Pattern (Data access abstraction)
 * - Service Layer Pattern (Business logic separation)
 * - Page Navigation Pattern (UI state management)
 * 
 * @version 1.0
 */
public class Application {
    
    public static void main(String[] args) {
        System.out.println("""
            ╔══════════════════════════════════════════════════╗
            ║                                                  ║
            ║       TICKET MANAGEMENT SYSTEM                   ║
            ║       Version 1.0                                ║
            ║                                                  ║
            ╚══════════════════════════════════════════════════╝
            """);
        
        // Initialize database connection
        DBConnection.getInstance();
        
        // Seed database with initial data (only if empty)
        DatabaseSeeder seeder = new DatabaseSeeder();
        seeder.seed();
        
        // Initialize UI components
        Screen screen = new Screen(40);
        InputReader input = new InputReader();

        // Start at Login Page
        Page currentPage = new LoginPage();

        // Main Application Loop (Page Navigation Engine)
        while (currentPage != null) {
            // Transfer control to the current page
            // Each page returns the next page to show
            currentPage = currentPage.show(screen, input);
        }

        System.out.println("""
            
            ══════════════════════════════════════════════════
            System Shutting Down. Goodbye!
            ══════════════════════════════════════════════════
            """);
    }
}
