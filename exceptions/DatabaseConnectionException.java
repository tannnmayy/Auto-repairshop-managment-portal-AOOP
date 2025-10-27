package exceptions;

/**
 * Custom exception for database connection errors
 * Demonstrates exception handling with custom exceptions
 */
public class DatabaseConnectionException extends Exception {
    private final String operation;
    
    /**
     * Constructor with message
     * @param message error message
     */
    public DatabaseConnectionException(String message) {
        super(message);
        this.operation = "Unknown";
    }
    
    /**
     * Constructor with message and operation
     * @param message error message
     * @param operation the operation that failed
     */
    public DatabaseConnectionException(String message, String operation) {
        super(message);
        this.operation = operation;
    }
    
    /**
     * Constructor with message, operation and cause
     * @param message error message
     * @param operation the operation that failed
     * @param cause the underlying exception
     */
    public DatabaseConnectionException(String message, String operation, Throwable cause) {
        super(message, cause);
        this.operation = operation;
    }
    
    /**
     * Gets the operation that failed
     * @return operation name
     */
    public String getOperation() {
        return operation;
    }
    
    @Override
    public String getMessage() {
        return "Database Connection Error in " + operation + ": " + super.getMessage();
    }
}

