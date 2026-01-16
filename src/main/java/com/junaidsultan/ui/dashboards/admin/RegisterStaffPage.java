package com.junaidsultan.ui.dashboards.admin;

import com.junaidsultan.entity.User;
import com.junaidsultan.service.IUserService;
import com.junaidsultan.service.ServiceLocator;
import com.junaidsultan.ui.core.input.InputReader;
import com.junaidsultan.ui.core.screen.Screen;
import com.junaidsultan.ui.scene_manager.Page;

/**
 * Admin page for registering new staff members.
 */
public class RegisterStaffPage implements Page {
    
    private final IUserService userService;
    
    public RegisterStaffPage() {
        this.userService = ServiceLocator.getInstance().getUserService();
    }
    
    @Override
    public Page show(Screen screen, InputReader input) {
        String info = """
            ══════════════════════════════════════
                    REGISTER NEW STAFF
            ══════════════════════════════════════
            
            Enter '0' at any prompt to cancel.
            """;
        
        screen.refresh("Register Staff", info, "");
        
        // Get username
        String username = input.readString("Username:");
        if (username.equals("0")) return new AdminDashboardPage();
        
        if (!userService.isUsernameAvailable(username)) {
            System.out.println("\n✗ Username already taken.");
            input.pause();
            return this;
        }
        
        // Get email
        String email = input.readString("Email:");
        if (email.equals("0")) return new AdminDashboardPage();
        
        if (!userService.isEmailAvailable(email)) {
            System.out.println("\n✗ Email already registered.");
            input.pause();
            return this;
        }
        
        // Get full name
        String name = input.readString("Full Name:");
        if (name.equals("0")) return new AdminDashboardPage();
        
        // Get password
        String password = input.readString("Password:");
        if (password.equals("0")) return new AdminDashboardPage();
        
        // Confirm password
        String confirmPassword = input.readString("Confirm Password:");
        if (confirmPassword.equals("0")) return new AdminDashboardPage();
        
        if (!password.equals(confirmPassword)) {
            System.out.println("\n✗ Passwords do not match.");
            input.pause();
            return this;
        }
        
        // Register staff
        User newStaff = userService.registerStaff(username, email, name, password);
        
        if (newStaff != null) {
            System.out.println("\n✓ Staff member registered successfully!");
            System.out.println("  Name: " + newStaff.getName());
            System.out.println("  Username: " + newStaff.getUsername());
        } else {
            System.out.println("\n✗ Failed to register staff member.");
        }
        
        input.pause();
        return new AdminDashboardPage();
    }
}
