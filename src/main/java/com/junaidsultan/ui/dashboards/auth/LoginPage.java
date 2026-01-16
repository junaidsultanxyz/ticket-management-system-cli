package com.junaidsultan.ui.dashboards.auth;

import com.junaidsultan.entity.User;
import com.junaidsultan.enums.Role;
import com.junaidsultan.ui.core.input.InputReader;
import com.junaidsultan.ui.core.screen.Screen;
import com.junaidsultan.ui.dashboards.admin.AdminDashboardPage;
import com.junaidsultan.ui.dashboards.student.StudentDashboardPage;
import com.junaidsultan.ui.scene_manager.Page;
import com.junaidsultan.ui.scene_manager.Session;

public class LoginPage implements Page {
    @Override
    public Page show(Screen screen, InputReader input) {
        screen.refresh("Login", "Please enter your credentials.", "Enter Username");

        String username = input.readString("Username:");
        String password = input.readString("Password:");

        // TODO: Call UserService.login(username, password) here
        // For now, let's simulate a successful login
        User mockUser = new User("admin", "admin@test.com", "Admin", "hash", Role.ADMIN);
        Session.setCurrentUser(mockUser);

        System.out.println("Login Successful! Welcome " + mockUser.getName());
        input.pause();

        // ROUTING LOGIC: Return the correct dashboard based on Role
        if (mockUser.getRole() == Role.ADMIN) {
            return new AdminDashboardPage();
        }
        else if (mockUser.getRole() == Role.STUDENT) {
            return new StudentDashboardPage();
        }
        else  {
            return new LoginPage();
        }
    }
}