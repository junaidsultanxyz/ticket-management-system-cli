package com.junaidsultan.util;

import com.junaidsultan.entity.Ticket;
import com.junaidsultan.entity.User;
import com.junaidsultan.enums.Priority;
import com.junaidsultan.enums.Role;
import com.junaidsultan.repository.TicketRepository;
import com.junaidsultan.repository.UserRepository;

/**
 * Database Seeder - Creates initial test data for development and testing.
 * This class seeds the database with sample users and tickets.
 */
public class DatabaseSeeder {
    
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    
    public DatabaseSeeder() {
        this.userRepository = new UserRepository();
        this.ticketRepository = new TicketRepository();
    }
    
    /**
     * Seeds the database with initial data if it's empty.
     */
    public void seed() {
        // Check if data already exists
        if (!userRepository.findAll().isEmpty()) {
            System.out.println("Database already contains data. Skipping seeding.");
            return;
        }
        
        System.out.println("Seeding database with initial data...");
        
        // Create Admin user
        User admin = new User(
            "admin",
            "admin@university.edu",
            "System Administrator",
            PasswordUtil.hashPassword("admin123"),
            Role.ADMIN
        );
        userRepository.save(admin);
        System.out.println("  ✓ Created admin user (username: admin, password: admin123)");
        
        // Create Staff users
        User staff1 = new User(
            "staff1",
            "staff1@university.edu",
            "John Smith",
            PasswordUtil.hashPassword("staff123"),
            Role.STAFF
        );
        userRepository.save(staff1);
        
        User staff2 = new User(
            "staff2",
            "staff2@university.edu",
            "Jane Doe",
            PasswordUtil.hashPassword("staff123"),
            Role.STAFF
        );
        userRepository.save(staff2);
        System.out.println("  ✓ Created 2 staff users (password: staff123)");
        
        // Create Student users
        User student1 = new User(
            "student1",
            "student1@university.edu",
            "Alice Johnson",
            PasswordUtil.hashPassword("student123"),
            Role.STUDENT
        );
        userRepository.save(student1);
        
        User student2 = new User(
            "student2",
            "student2@university.edu",
            "Bob Williams",
            PasswordUtil.hashPassword("student123"),
            Role.STUDENT
        );
        userRepository.save(student2);
        
        User student3 = new User(
            "student3",
            "student3@university.edu",
            "Charlie Brown",
            PasswordUtil.hashPassword("student123"),
            Role.STUDENT
        );
        userRepository.save(student3);
        System.out.println("  ✓ Created 3 student users (password: student123)");
        
        // Create some sample tickets
        Ticket ticket1 = new Ticket(
            "WiFi Not Working in Library",
            "The WiFi connection in the main library building has been down for 2 days.",
            Priority.HIGH,
            "Technical",
            student1.getId()
        );
        ticketRepository.save(ticket1);
        
        Ticket ticket2 = new Ticket(
            "Air Conditioning Issue",
            "The AC in Room 301 is not cooling properly during afternoon classes.",
            Priority.MEDIUM,
            "Facility",
            student2.getId()
        );
        ticketRepository.save(ticket2);
        
        Ticket ticket3 = new Ticket(
            "Course Registration Error",
            "Unable to register for COMP301. System shows an error when trying to enroll.",
            Priority.HIGH,
            "Academic",
            student1.getId()
        );
        ticketRepository.save(ticket3);
        
        Ticket ticket4 = new Ticket(
            "Parking Permit Issue",
            "My parking permit is showing as expired even though I renewed it last week.",
            Priority.LOW,
            "Administrative",
            student3.getId()
        );
        ticketRepository.save(ticket4);
        
        Ticket ticket5 = new Ticket(
            "Missing Grade",
            "My grade for the midterm exam in MATH201 is not showing in the portal.",
            Priority.MEDIUM,
            "Academic",
            student2.getId()
        );
        ticketRepository.save(ticket5);
        
        // Assign some tickets to staff
        ticketRepository.assignToStaff(ticket1.getId(), staff1.getId());
        ticketRepository.assignToStaff(ticket3.getId(), staff2.getId());
        
        System.out.println("  ✓ Created 5 sample tickets (2 assigned to staff)");
        
        System.out.println("\nDatabase seeding complete!");
        System.out.println("═══════════════════════════════════════════");
        System.out.println("Test Accounts:");
        System.out.println("  Admin:   admin / admin123");
        System.out.println("  Staff:   staff1 / staff123");
        System.out.println("  Student: student1 / student123");
        System.out.println("═══════════════════════════════════════════\n");
    }
    
    /**
     * Force re-seed the database (clears existing data first).
     * Use with caution!
     */
    public void forceSeed() {
        System.out.println("Warning: This will clear all existing data!");
        // Clear existing data
        for (User user : userRepository.findAll()) {
            userRepository.deleteById(user.getId());
        }
        for (Ticket ticket : ticketRepository.findAll()) {
            ticketRepository.deleteById(ticket.getId());
        }
        // Now seed
        seed();
    }
}
