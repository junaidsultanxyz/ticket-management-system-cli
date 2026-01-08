package com.junaidsultan.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

    private static volatile DBConnection instance;

    // The actual JDBC connection object
    private Connection connection;

    private static final String DB_URL = "jdbc:sqlite:tms.db";

    private DBConnection() {
        try {
            // Load the SQLite JDBC driver (optional in newer Java, but good for safety)
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection(DB_URL);
            System.out.println("✓ Connected to SQLite database.");

            initializeDatabase();

        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("✗ Database Connection Failed: " + e.getMessage());
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    // The only way to get the instance
    public static DBConnection getInstance() {
        if (instance == null) {
            synchronized (DBConnection.class) {
                if (instance == null) {
                    instance = new DBConnection();
                }
            }
        }
        return instance;
    }

    // 6. Getter for the actual SQL Connection object
    public Connection getConnection() {
        return connection;
    }

    // Creates tables if they don't exist
    private void initializeDatabase() {
        try (Statement stmt = this.connection.createStatement()) {

            String userTable = """
                CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT UNIQUE NOT NULL,
                    password_hash TEXT NOT NULL,
                    role TEXT NOT NULL
                );
            """;
            stmt.execute(userTable);

            String ticketTable = """
                CREATE TABLE IF NOT EXISTS tickets (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    title TEXT NOT NULL,
                    description TEXT,
                    priority TEXT DEFAULT 'MEDIUM',
                    status TEXT DEFAULT 'OPEN',
                    creator_id INTEGER,
                    FOREIGN KEY (creator_id) REFERENCES users(id)
                );
            """;
            stmt.execute(ticketTable);

        } catch (SQLException e) {
            System.err.println("Error creating tables: " + e.getMessage());
        }
    }
}