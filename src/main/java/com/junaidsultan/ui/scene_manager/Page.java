package com.junaidsultan.ui.scene_manager;

import com.junaidsultan.ui.core.input.InputReader;
import com.junaidsultan.ui.core.screen.Screen;

// A Page does its work and returns the NEXT page to show.
// If it returns null, the application exits.
public interface Page {
    Page show(Screen screen, InputReader input);
}
