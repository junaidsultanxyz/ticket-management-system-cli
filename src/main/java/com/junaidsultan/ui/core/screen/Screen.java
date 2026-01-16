package com.junaidsultan.ui.core.screen;

import com.junaidsultan.ui.core.panels.BoxedPanel;
import com.junaidsultan.ui.core.panels.ConsolePanel;
import com.junaidsultan.ui.core.panels.FooterPanel;

public class Screen {
    private ConsolePanel header;
    private ConsolePanel body;
    private ConsolePanel footer;
    private final int screenWidth;

    public Screen(int width) {
        this.screenWidth = width;
        // Default implementations
        this.header = new BoxedPanel();
        this.body = new BoxedPanel();
        this.footer = new FooterPanel();

        // Apply width settings
        this.header.setWidth(width);
        this.body.setWidth(width);
        this.footer.setWidth(width);
    }

    // Allow swapping panels dynamically
    public void setBody(ConsolePanel newBody) {
        this.body = newBody;
        this.body.setWidth(screenWidth);
    }

    // The main render method
    public void refresh(String headerText, String bodyText, String footerText) {
        // Clear console (optional, might not work in all IDEs)
        // System.out.print("\033[H\033[2J");

        header.print(headerText);
        body.print(bodyText);
        footer.print(footerText);
    }
}