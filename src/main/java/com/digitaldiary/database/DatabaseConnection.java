package com.digitaldiary.database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnection {
    private static DatabaseConnection instance;

    private final String url;
    private Connection connection;

    private DatabaseConnection(Path databasePath) {
        try {
            Path parent = databasePath.toAbsolutePath().getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
            Class.forName("org.sqlite.JDBC");
            this.url = "jdbc:sqlite:" + databasePath.toAbsolutePath();
        } catch (ClassNotFoundException exception) {
            throw new IllegalStateException("SQLite JDBC driver not found. Check lib/sqlite-jdbc.jar.", exception);
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to prepare database directory.", exception);
        }
    }

    public static synchronized DatabaseConnection getInstance(Path databasePath) {
        if (instance == null) {
            instance = new DatabaseConnection(databasePath);
        }
        return instance;
    }

    public synchronized Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(url);
        }
        return connection;
    }

    public synchronized void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ignored) {
                // The application can continue closing even if the driver reports a late close error.
            }
        }
    }

    public static synchronized void resetForTests() {
        if (instance != null) {
            instance.close();
            instance = null;
        }
    }
}
