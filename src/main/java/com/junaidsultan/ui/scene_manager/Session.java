package com.junaidsultan.ui.scene_manager;

import com.junaidsultan.entity.User;

public class Session {
    private static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        Session.currentUser = currentUser;
    }

    public static void logout() {
        currentUser = null;
    }
}
