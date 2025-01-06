package com.library.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class for managing database connections and operations.
 * This class provides centralized database connection management
 * and connection pooling functionality.
 * @author 22ENG 143,149,50
 */
public class DatabaseUtil {
    /** Database connection URL */
    private static final String URL = "jdbc:mysql://localhost/librarydb";
    
    /** Database username */
    private static final String USERNAME = "librarydbuser";
    
    /** Database password */
    private static final String PASSWORD = "Library@Co2210";
    
    /** Shared database connection instance */
    private static Connection connection;
    
    /**
     * Gets or creates a database connection.
     * Implements connection pooling by reusing an existing connection if available.
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (ClassNotFoundException e) {
                throw new SQLException("MySQL JDBC Driver not found.", e);
            }
        }
        return connection;
    }
    
    /**
     * Closes the current database connection if open.
     * Should be called when the application shuts down or
     * when the connection is no longer needed.
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}