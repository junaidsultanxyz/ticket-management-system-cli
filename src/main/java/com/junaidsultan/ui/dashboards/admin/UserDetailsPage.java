package com.junaidsultan.ui.dashboards.admin;

import com.junaidsultan.entity.User;
import com.junaidsultan.service.IUserService;
import com.junaidsultan.service.ServiceLocator;
import com.junaidsultan.ui.core.input.InputReader;
import com.junaidsultan.ui.core.screen.Screen;
import com.junaidsultan.ui.scene_manager.Page;
import com.junaidsultan.ui.shared.DisplayHelper;

import java.util.Optional;

/**
 * Admin page for viewing and managing a specific user.
 */
public class UserDetailsPage implements Page {
    
    private final IUserService userService;
    private final String userId;
    private final ManageUsersPage.UserType userType;
    
    public UserDetailsPage(String userId, ManageUsersPage.UserType userType) {
        this.userService = ServiceLocator.getInstance().getUserService();
        this.userId = userId;
        this.userType = userType;
    }
    
    @Override
    public Page show(Screen screen, InputReader input) {
        Optional<User> userOpt = userService.findById(userId);
        
        if (userOpt.isEmpty()) {
            System.out.println("User not found.");
            input.pause();
            return new ManageUsersPage(userType);
        }
        
        User user = userOpt.get();
        String details = DisplayHelper.formatUserDetails(user);
        
        String menu = """
            Actions:
            1. Update User Information
            2. Delete User
            0. Back
            """;
        
        screen.refresh("User Details", details + "\n" + menu, "");
        int choice = input.readInt("");
        
        return switch (choice) {
            case 1 -> updateUser(user, input);
            case 2 -> deleteUser(user, input);
            case 0 -> new ManageUsersPage(userType);
            default -> {
                System.out.println("Invalid option.");
                input.pause();
                yield this;
            }
        };
    }
    
    private Page updateUser(User user, InputReader input) {
        System.out.println("\nUpdate User Information");
        System.out.println("(Press Enter to keep current value)\n");
        
        // For now, we'll implement a simple update
        // In a full implementation, you'd allow editing individual fields
        
        String newName = input.readString("New Name [" + user.getName() + "]:");
        if (newName.isEmpty()) {
            newName = user.getName();
        }
        
        String newEmail = input.readString("New Email [" + user.getEmail() + "]:");
        if (newEmail.isEmpty()) {
            newEmail = user.getEmail();
        } else if (!userService.isEmailAvailable(newEmail) && !newEmail.equals(user.getEmail())) {
            System.out.println("[X] Email already in use.");
            input.pause();
            return this;
        }
        
        // Create updated user (maintaining ID, username, password, role)
        User updatedUser = new User(
            user.getId(),
            user.getUsername(),
            newEmail,
            newName,
            user.getPasswordHash(),
            user.getRole().name()
        );
        
        User result = userService.updateUser(updatedUser);
        
        if (result != null) {
            System.out.println("\n[OK] User updated successfully.");
        } else {
            System.out.println("\n[X] Failed to update user.");
        }
        
        input.pause();
        return this;
    }
    
    private Page deleteUser(User user, InputReader input) {
        String confirm = input.readString("Are you sure you want to delete " + user.getName() + "? (yes/no):");
        
        if (confirm.equalsIgnoreCase("yes")) {
            if (userService.deleteUser(user.getId())) {
                System.out.println("\n[OK] User deleted successfully.");
                input.pause();
                return new ManageUsersPage(userType);
            } else {
                System.out.println("\n[X] Failed to delete user.");
            }
        } else {
            System.out.println("Deletion cancelled.");
        }
        
        input.pause();
        return this;
    }
}
