package com.junaidsultan;

import com.junaidsultan.ui.core.input.InputReader;
import com.junaidsultan.ui.core.screen.Screen;
import com.junaidsultan.ui.dashboards.auth.LoginPage;
import com.junaidsultan.ui.scene_manager.Page;

public class Application {
    static void main(String[] args) {
        Screen screen = new Screen(40);
        InputReader input = new InputReader();

        // Start at Login
        Page currentPage = new LoginPage();

        // The Engine
        while (currentPage != null) {
            // Transfer control to the page
            currentPage = currentPage.show(screen, input);
        }

        System.out.println("System Shutting Down. Goodbye!");
    }
}