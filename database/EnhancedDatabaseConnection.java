package database;

import interfaces.IDatabaseOperations;
import exceptions.DatabaseConnectionException;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Enhanced database connection class
 * Demonstrates interface implementation and exception handling
 */
public class EnhancedDatabaseConnection implements IDatabaseOperations {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/repairshop";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    
    private Connection connection;
    private boolean isConnected;
    
    /**
     * Default constructor
     */
    public EnhancedDatabaseConnection() {
        this.connection = null;
        this.isConnected = false;
    }
    
    /**
     * Constructor with connection parameters
     * @param url database URL
     * @param username database username
     * @param password database password
     */
    public EnhancedDatabaseConnection(String url, String username, String password) {
        this.connection = null;
        this.isConnected = false;
    }
    
    /**
     * Establishes database connection
     * Implements interface method with exception handling
     * @return Connection object
     * @throws SQLException if connection fails
     */
    @Override
    public Connection getConnection() throws SQLException, DatabaseConnectionException {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                isConnected = true;
            }
            return connection;
        } catch (SQLException e) {
            isConnected = false;
            throw new DatabaseConnectionException(
                "Failed to establish database connection", 
                "getConnection", 
                e
            );
        }
    }
    
    /**
     * Validates database connection
     * Implements interface method
     * @return true if connection is valid
     */
    @Override
    public boolean validateConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                return connection.isValid(5); // 5 second timeout
            }
            return false;
        } catch (SQLException e) {
            isConnected = false;
            return false;
        }
    }
    
    /**
     * Closes database connection
     * Implements interface method with exception handling
     * @throws SQLException if closing fails
     */
    @Override
    public void closeConnection() throws SQLException, DatabaseConnectionException {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                isConnected = false;
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException(
                "Failed to close database connection", 
                "closeConnection", 
                e
            );
        }
    }
    
    /**
     * Gets connection with error handling and user notification
     * @return Connection object or null if failed
     */
    public Connection getConnectionWithErrorHandling() {
        try {
            return getConnection();
        } catch (SQLException | DatabaseConnectionException e) {
            handleDatabaseError("Database connection failed", e);
            return null;
        }
    }
    
    /**
     * Handles database errors with user notification
     * @param message error message
     * @param exception the exception that occurred
     */
    private void handleDatabaseError(String message, Exception exception) {
        String errorMessage = message + ": " + exception.getMessage();
        System.err.println(errorMessage);
        JOptionPane.showMessageDialog(
            null, 
            errorMessage, 
            "Database Error", 
            JOptionPane.ERROR_MESSAGE
        );
    }
    
    /**
     * Tests database connection
     * @return true if connection test is successful
     */
    public boolean testConnection() {
        try {
            Connection testConn = getConnection();
            if (testConn != null) {
                testConn.close();
                return true;
            }
            return false;
        } catch (SQLException | DatabaseConnectionException e) {
            handleDatabaseError("Connection test failed", e);
            return false;
        }
    }
    
    /**
     * Gets connection status
     * @return true if connected
     */
    public boolean isConnected() {
        return isConnected && validateConnection();
    }
    
    /**
     * Method overloading - gets connection with retry attempts
     * @param maxRetries maximum number of retry attempts
     * @return Connection object or null if failed
     */
    public Connection getConnection(int maxRetries) {
        for (int i = 0; i < maxRetries; i++) {
            try {
                return getConnection();
            } catch (SQLException | DatabaseConnectionException e) {
                if (i == maxRetries - 1) {
                    handleDatabaseError("Failed after " + maxRetries + " attempts", e);
                } else {
                    try {
                        Thread.sleep(1000); // Wait 1 second before retry
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        }
        return null;
    }
    
    /**
     * Method overloading - gets connection with custom timeout
     * @param timeoutSeconds timeout in seconds
     * @return Connection object or null if failed
     */
    public Connection getConnectionWithTimeout(int timeoutSeconds) {
        try {
            Connection conn = getConnection();
            if (conn != null) {
                conn.setNetworkTimeout(null, timeoutSeconds * 1000);
            }
            return conn;
        } catch (SQLException | DatabaseConnectionException e) {
            handleDatabaseError("Connection timeout", e);
            return null;
        }
    }
}
