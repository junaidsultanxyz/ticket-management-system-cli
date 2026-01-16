package com.junaidsultan.ui.dashboards.admin;

import com.junaidsultan.entity.User;
import com.junaidsultan.enums.Role;
import com.junaidsultan.service.IUserService;
import com.junaidsultan.service.ServiceLocator;
import com.junaidsultan.ui.core.input.InputReader;
import com.junaidsultan.ui.core.screen.Screen;
import com.junaidsultan.ui.scene_manager.Page;
import com.junaidsultan.ui.shared.DisplayHelper;

import java.util.List;

/**
 * Admin page for managing users (students or staff).
 * Allows viewing, updating, and deleting users.
 */
public class ManageUsersPage implements Page {
    
    public enum UserType {
        STUDENT, STAFF
    }
    
    private final IUserService userService;
    private final UserType userType;
    
    public ManageUsersPage(UserType userType) {
        this.userService = ServiceLocator.getInstance().getUserService();
        this.userType = userType;
    }
    
    @Override
    public Page show(Screen screen, InputReader input) {
        List<User> users = userType == UserType.STUDENT 
            ? userService.getAllStudents() 
            : userService.getAllStaff();
        
        String title = userType == UserType.STUDENT ? "Manage Students" : "Manage Staff";
        String userList = DisplayHelper.formatUserList(users);
        
        String content = String.format("""
            ══════════════════════════════════════
            %s
            ══════════════════════════════════════
            Total: %d user(s)
            
            %s
            ══════════════════════════════════════
            
            Enter user number to manage,
            or 0 to go back.
            """, title, users.size(), userList);
        
        screen.refresh(title, content, "");
        int choice = input.readInt("");
        
        if (choice == 0) {
            return new AdminDashboardPage();
        }
        
        if (choice > 0 && choice <= users.size()) {
            User selectedUser = users.get(choice - 1);
            return new UserDetailsPage(selectedUser.getId(), userType);
        }
        
        System.out.println("Invalid selection.");
        input.pause();
        return this;
    }
}
