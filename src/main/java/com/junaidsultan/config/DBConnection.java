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
                    id TEXT PRIMARY KEY,
                    username TEXT UNIQUE NOT NULL,
                    email TEXT UNIQUE NOT NULL,
                    name TEXT NOT NULL,
                    password_hash TEXT NOT NULL,
                    role TEXT NOT NULL CHECK(role IN ('STUDENT', 'ADMIN', 'STAFF')),
                    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
                );
            """;
            stmt.execute(userTable);

            String ticketTable = """
                CREATE TABLE IF NOT EXISTS tickets (
                    id TEXT PRIMARY KEY,
                    title TEXT NOT NULL,
                    description TEXT NOT NULL,
                    priority TEXT DEFAULT 'MEDIUM' CHECK(priority IN ('LOW', 'MEDIUM', 'HIGH')),
                    status TEXT DEFAULT 'OPEN' CHECK(status IN ('OPEN', 'RESOLVED', 'CLOSED', 'ON_HOLD')),
                    category TEXT,
                    created_by TEXT NOT NULL,
                    assigned_to TEXT,
                    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (created_by) REFERENCES users(id),
                    FOREIGN KEY (assigned_to) REFERENCES users(id)
                );
            """;
            stmt.execute(ticketTable);

            String notificationTable = """
                CREATE TABLE IF NOT EXISTS notifications (
                    id TEXT PRIMARY KEY,
                    receiver_id TEXT NOT NULL,
                    title TEXT NOT NULL,
                    message TEXT,
                    is_read INTEGER DEFAULT 0,
                    created_by TEXT,
                    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (receiver_id) REFERENCES users(id),
                    FOREIGN KEY (created_by) REFERENCES users(id)
                );
            """;
            stmt.execute(notificationTable);

        } catch (SQLException e) {
            System.err.println("Error creating tables: " + e.getMessage());
        }
    }
}