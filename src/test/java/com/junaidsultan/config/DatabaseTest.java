package com.junaidsultan.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseTest {

    @Test
    @DisplayName("Should connect to DB and create tables")
    public void testDatabaseConnectionAndTables() {
        System.out.println("--- TEST START: Database Connectivity ---");

        // 1. Get Instance
        DBConnection dbInstance = DBConnection.getInstance();

        // ASSERTION: The instance should not be null
        Assertions.assertNotNull(dbInstance, "DBConnection instance should be created.");

        // 2. Get Connection
        Connection conn = dbInstance.getConnection();

        // ASSERTION: Connection object should exist
        Assertions.assertNotNull(conn, "Connection object should not be null.");

        // 3. Check if file exists on disk
        File dbFile = new File("tms.db");
        Assertions.assertTrue(dbFile.exists(), "tms.db file should exist in project root.");

        // 4. Verify Tables
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table';")) {

            boolean usersFound = false;
            boolean ticketsFound = false;

            while (rs.next()) {
                String tableName = rs.getString("name");
                if ("users".equals(tableName)) usersFound = true;
                if ("tickets".equals(tableName)) ticketsFound = true;
            }

            // ASSERTION: Both tables must exist
            Assertions.assertTrue(usersFound, "Users table is missing.");
            Assertions.assertTrue(ticketsFound, "Tickets table is missing.");

            System.out.println("✅ Database tables verified successfully.");

        } catch (Exception e) {
            Assertions.fail("SQL Exception occurred: " + e.getMessage());
        }
    }
}