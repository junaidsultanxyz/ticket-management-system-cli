package com.junaidsultan.ui.core.screen;

/**
 * Screen - Handles console output and screen rendering.
 * Simplified implementation for cleaner CLI display.
 */
public class Screen {
    private final int screenWidth;

    public Screen(int width) {
        this.screenWidth = width;
    }

    /**
     * Clear the console screen.
     */
    public void clear() {
        // Try to clear console (works in most terminals)
        System.out.print("\033[H\033[2J");
        System.out.flush();
        // Fallback: print newlines
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    /**
     * Render a screen with header, body content, and optional footer prompt.
     */
    public void refresh(String header, String body, String footer) {
        clear();
        printHeader(header);
        System.out.println(body);
        if (footer != null && !footer.isEmpty()) {
            printFooter(footer);
        }
    }

    /**
     * Print a styled header.
     */
    private void printHeader(String title) {
        String line = "═".repeat(screenWidth);
        System.out.println(line);
        System.out.println("  " + title.toUpperCase());
        System.out.println(line);
        System.out.println();
    }

    /**
     * Print a footer/prompt.
     */
    private void printFooter(String text) {
        System.out.println();
        System.out.print(">> " + text + " ");
    }

    /**
     * Print a divider line.
     */
    public void printDivider() {
        System.out.println("─".repeat(screenWidth));
    }

    /**
     * Print a message with formatting.
     */
    public void printMessage(String message) {
        System.out.println("  " + message);
    }

    public int getWidth() {
        return screenWidth;
    }
}
