package com.junaidsultan.ui.dashboards.auth;

import com.junaidsultan.entity.User;
import com.junaidsultan.service.IUserService;
import com.junaidsultan.service.ServiceLocator;
import com.junaidsultan.ui.core.input.InputReader;
import com.junaidsultan.ui.core.screen.Screen;
import com.junaidsultan.ui.scene_manager.Page;

/**
 * Registration page for new student users.
 * Only students can self-register. Staff/Admin accounts are created by Admin.
 */
public class RegisterPage implements Page {
    
    private final IUserService userService;
    
    public RegisterPage() {
        this.userService = ServiceLocator.getInstance().getUserService();
    }
    
    @Override
    public Page show(Screen screen, InputReader input) {
        String info = """
            
            Note: Only students can register here.
            Staff accounts are created by Admin.
            
            Enter '0' at any prompt to cancel.
            """;
        
        screen.refresh("STUDENT REGISTRATION", info, "");
        
        // Get username
        String username = input.readString("Username:");
        if (username.equals("0")) return new LoginPage();
        
        // Check username availability
        if (!userService.isUsernameAvailable(username)) {
            System.out.println("\n[X] Username already taken. Please try another.");
            input.pause();
            return this;
        }
        
        // Get email
        String email = input.readString("Email:");
        if (email.equals("0")) return new LoginPage();
        
        // Check email availability
        if (!userService.isEmailAvailable(email)) {
            System.out.println("\n[X] Email already registered. Please use a different email.");
            input.pause();
            return this;
        }
        
        // Get full name
        String name = input.readString("Full Name:");
        if (name.equals("0")) return new LoginPage();
        
        // Get password
        String password = input.readString("Password:");
        if (password.equals("0")) return new LoginPage();
        
        // Confirm password
        String confirmPassword = input.readString("Confirm Password:");
        if (confirmPassword.equals("0")) return new LoginPage();
        
        if (!password.equals(confirmPassword)) {
            System.out.println("\n[X] Passwords do not match. Please try again.");
            input.pause();
            return this;
        }
        
        // Validate password strength (basic)
        if (password.length() < 4) {
            System.out.println("\n[X] Password must be at least 4 characters long.");
            input.pause();
            return this;
        }
        
        // Register the student
        User newUser = userService.registerStudent(username, email, name, password);
        
        if (newUser != null) {
            System.out.println("\n[OK] Registration successful!");
            System.out.println("     You can now login with your credentials.");
            input.pause();
            return new LoginPage();
        } else {
            System.out.println("\n[X] Registration failed. Please try again.");
            input.pause();
            return this;
        }
    }
}
