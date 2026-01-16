package com.junaidsultan.ui.core.panels;

public interface ConsolePanel {
    // The width of the CONTENT area (excluding borders)
    void setWidth(int width);

    // The method to draw the panel with specific text
    void print(String content);
}