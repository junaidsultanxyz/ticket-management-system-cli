package com.junaidsultan.ui.dashboards.auth;

import com.junaidsultan.service.IUserService;
import com.junaidsultan.service.ServiceLocator;
import com.junaidsultan.ui.core.input.InputReader;
import com.junaidsultan.ui.core.screen.Screen;
import com.junaidsultan.ui.scene_manager.Page;

/**
 * Forgot Password page for password recovery.
 * Only available for students. Staff/Admin cannot reset passwords here.
 */
public class ForgotPasswordPage implements Page {
    
    private final IUserService userService;
    
    public ForgotPasswordPage() {
        this.userService = ServiceLocator.getInstance().getUserService();
    }
    
    @Override
    public Page show(Screen screen, InputReader input) {
        String info = """
            
            Note: Password reset is only available
            for student accounts.
            
            Enter '0' at any prompt to cancel.
            """;
        
        screen.refresh("FORGOT PASSWORD", info, "");
        
        // Get email
        String email = input.readString("Enter your registered email:");
        if (email.equals("0")) return new LoginPage();
        
        // Get new password
        String newPassword = input.readString("Enter new password:");
        if (newPassword.equals("0")) return new LoginPage();
        
        // Confirm new password
        String confirmPassword = input.readString("Confirm new password:");
        if (confirmPassword.equals("0")) return new LoginPage();
        
        if (!newPassword.equals(confirmPassword)) {
            System.out.println("\n[X] Passwords do not match. Please try again.");
            input.pause();
            return this;
        }
        
        // Validate password strength
        if (newPassword.length() < 4) {
            System.out.println("\n[X] Password must be at least 4 characters long.");
            input.pause();
            return this;
        }
        
        // Reset password
        boolean success = userService.resetPassword(email, newPassword);
        
        if (success) {
            System.out.println("\n[OK] Password reset successful!");
            System.out.println("     You can now login with your new password.");
            input.pause();
            return new LoginPage();
        } else {
            System.out.println("\n[X] Password reset failed.");
            System.out.println("    Make sure the email belongs to a student account.");
            input.pause();
            return this;
        }
    }
}
