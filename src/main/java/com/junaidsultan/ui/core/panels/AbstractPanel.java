package com.junaidsultan.ui.core.panels;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPanel implements ConsolePanel {
    protected int width = 50; // Default width

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Splits a long string into a list of lines that fit within the width.
     */
    protected List<String> wrapText(String text) {
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {
            // Check if adding the next word exceeds width
            if (currentLine.length() + word.length() + 1 > width) {
                // If line is not empty, add it to list and clear buffer
                if (currentLine.length() > 0) {
                    lines.add(padRight(currentLine.toString(), width));
                    currentLine.setLength(0);
                }
            }
            if (currentLine.length() > 0) {
                currentLine.append(" ");
            }
            currentLine.append(word);
        }
        // Add the last line
        if (currentLine.length() > 0) {
            lines.add(padRight(currentLine.toString(), width));
        }
        return lines;
    }

    // Helper to fill empty space with spaces
    private String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }
}