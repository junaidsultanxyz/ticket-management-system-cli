package com.junaidsultan.ui.dashboards.auth;

import com.junaidsultan.entity.User;
import com.junaidsultan.service.IUserService;
import com.junaidsultan.service.ServiceLocator;
import com.junaidsultan.ui.core.input.InputReader;
import com.junaidsultan.ui.core.screen.Screen;
import com.junaidsultan.ui.dashboards.admin.AdminDashboardPage;
import com.junaidsultan.ui.dashboards.staff.StaffDashboardPage;
import com.junaidsultan.ui.dashboards.student.StudentDashboardPage;
import com.junaidsultan.ui.scene_manager.Page;
import com.junaidsultan.ui.scene_manager.Session;

import java.util.Optional;

/**
 * Login page for user authentication.
 * Routes to appropriate dashboard based on user role.
 */
public class LoginPage implements Page {
    
    private final IUserService userService;
    
    public LoginPage() {
        this.userService = ServiceLocator.getInstance().getUserService();
    }
    
    @Override
    public Page show(Screen screen, InputReader input) {
        String menu = """
            
            Welcome to the Ticket Management System
            
            [1] Login
            [2] Register (Students Only)
            [3] Forgot Password
            [0] Exit
            """;
        
        screen.refresh("TICKET MANAGEMENT SYSTEM", menu, "Select Option");
        int choice = input.readInt("");
        
        switch (choice) {
            case 1 -> {
                return handleLogin(screen, input);
            }
            case 2 -> {
                return new RegisterPage();
            }
            case 3 -> {
                return new ForgotPasswordPage();
            }
            case 0 -> {
                return null; // Exit application
            }
            default -> {
                System.out.println("\n[!] Invalid option. Please try again.");
                input.pause();
                return this;
            }
        }
    }
    
    private Page handleLogin(Screen screen, InputReader input) {
        screen.refresh("LOGIN", "\nPlease enter your credentials:\n", "");
        
        String username = input.readString("Username:");
        String password = input.readString("Password:");
        
        Optional<User> userOpt = userService.login(username, password);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            Session.setCurrentUser(user);
            
            System.out.println("\n[OK] Login Successful! Welcome, " + user.getName());
            input.pause();
            
            // Route to appropriate dashboard based on role
            return switch (user.getRole()) {
                case ADMIN -> new AdminDashboardPage();
                case STAFF -> new StaffDashboardPage();
                case STUDENT -> new StudentDashboardPage();
            };
        } else {
            System.out.println("\n[X] Invalid username or password.");
            input.pause();
            return this;
        }
    }
}
