package interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import exceptions.DatabaseConnectionException;

/**
 * Interface for database operations
 * Demonstrates interface usage for common database functionality
 */
public interface IDatabaseOperations {
    /**
     * Establishes database connection
     * @return Connection object
     * @throws SQLException if connection fails
     * @throws DatabaseConnectionException if custom database error occurs
     */
    Connection getConnection() throws SQLException, DatabaseConnectionException;
    
    /**
     * Validates database connection
     * @return true if connection is valid
     */
    boolean validateConnection();
    
    /**
     * Closes database connection
     * @throws SQLException if closing fails
     * @throws DatabaseConnectionException if custom database error occurs
     */
    void closeConnection() throws SQLException, DatabaseConnectionException;
}
