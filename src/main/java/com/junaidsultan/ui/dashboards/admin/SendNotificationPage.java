package com.junaidsultan.ui.dashboards.admin;

import com.junaidsultan.entity.User;
import com.junaidsultan.enums.Role;
import com.junaidsultan.service.INotificationService;
import com.junaidsultan.service.IUserService;
import com.junaidsultan.service.ServiceLocator;
import com.junaidsultan.ui.core.input.InputReader;
import com.junaidsultan.ui.core.screen.Screen;
import com.junaidsultan.ui.scene_manager.Page;
import com.junaidsultan.ui.scene_manager.Session;
import com.junaidsultan.ui.shared.DisplayHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Admin page for sending notifications to users.
 * Can send to individuals, roles, or all users.
 */
public class SendNotificationPage implements Page {
    
    private final INotificationService notificationService;
    private final IUserService userService;
    
    public SendNotificationPage() {
        this.notificationService = ServiceLocator.getInstance().getNotificationService();
        this.userService = ServiceLocator.getInstance().getUserService();
    }
    
    @Override
    public Page show(Screen screen, InputReader input) {
        String menu = """
            ══════════════════════════════════════
                   SEND NOTIFICATION
            ══════════════════════════════════════
            
            Send to:
            1. A Specific User
            2. All Students
            3. All Staff
            4. All Users
            0. Back
            """;
        
        screen.refresh("Send Notification", menu, "");
        int choice = input.readInt("");
        
        return switch (choice) {
            case 1 -> sendToUser(input);
            case 2 -> sendToRole(Role.STUDENT, input);
            case 3 -> sendToRole(Role.STAFF, input);
            case 4 -> sendToAll(input);
            case 0 -> new AdminDashboardPage();
            default -> {
                System.out.println("Invalid option.");
                input.pause();
                yield this;
            }
        };
    }
    
    private Page sendToUser(InputReader input) {
        // First, list all users to select from
        List<User> allUsers = new ArrayList<>();
        allUsers.addAll(userService.getAllStudents());
        allUsers.addAll(userService.getAllStaff());
        
        if (allUsers.isEmpty()) {
            System.out.println("No users available.");
            input.pause();
            return this;
        }
        
        System.out.println("\nSelect a user:");
        System.out.println(DisplayHelper.formatUserList(allUsers));
        
        int userChoice = input.readInt("Enter user number (0 to cancel):");
        
        if (userChoice == 0) return this;
        
        if (userChoice > 0 && userChoice <= allUsers.size()) {
            User selectedUser = allUsers.get(userChoice - 1);
            return composeAndSend(List.of(selectedUser.getId()), input);
        }
        
        System.out.println("Invalid selection.");
        input.pause();
        return this;
    }
    
    private Page sendToRole(Role role, InputReader input) {
        List<User> users = userService.findByRole(role);
        
        if (users.isEmpty()) {
            System.out.println("No " + role.name().toLowerCase() + "s found.");
            input.pause();
            return this;
        }
        
        List<String> userIds = users.stream().map(User::getId).toList();
        return composeAndSend(userIds, input);
    }
    
    private Page sendToAll(InputReader input) {
        List<User> allUsers = new ArrayList<>();
        allUsers.addAll(userService.getAllStudents());
        allUsers.addAll(userService.getAllStaff());
        
        if (allUsers.isEmpty()) {
            System.out.println("No users found.");
            input.pause();
            return this;
        }
        
        List<String> userIds = allUsers.stream().map(User::getId).toList();
        return composeAndSend(userIds, input);
    }
    
    private Page composeAndSend(List<String> receiverIds, InputReader input) {
        System.out.println("\nCompose Notification:");
        System.out.println("(Enter '0' to cancel)\n");
        
        String title = input.readString("Title:");
        if (title.equals("0")) return this;
        
        String message = input.readString("Message:");
        if (message.equals("0")) return this;
        
        String createdBy = Session.getCurrentUser().getId();
        int sent = notificationService.sendBulkNotification(receiverIds, title, message, createdBy);
        
        System.out.println("\n✓ Notification sent to " + sent + " user(s).");
        input.pause();
        
        return new AdminDashboardPage();
    }
}
