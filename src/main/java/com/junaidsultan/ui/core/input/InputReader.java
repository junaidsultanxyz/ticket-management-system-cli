package com.junaidsultan.ui.core.input;

import java.util.Scanner;

public class InputReader {
    private final Scanner scanner;

    public InputReader() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Reads a generic string from the user.
     * Prevents empty inputs if required.
     */
    public String readString(String message) {
        System.out.print(message + " "); // Print the prompt (e.g., ">> ")
        String input = scanner.nextLine().trim();

        // Loop until user types something (optional validation)
        while (input.isEmpty()) {
            System.out.print("Input cannot be empty. Try again: ");
            input = scanner.nextLine().trim();
        }
        return input;
    }

    /**
     * Safely reads an integer.
     * If user types "abc", it asks them to try again instead of crashing.
     */
    public int readInt(String message) {
        while (true) {
            String input = readString(message);
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter a valid digit.");
            }
        }
    }

    // Simple "Press Enter to Continue" pause
    public void pause() {
        System.out.println("\nPress [Enter] to continue...");
        scanner.nextLine();
    }
}