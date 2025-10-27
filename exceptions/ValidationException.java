package exceptions;

/**
 * Custom exception for validation errors
 * Demonstrates exception handling for data validation
 */
public class ValidationException extends Exception {
    private final String fieldName;
    private final String invalidValue;
    
    /**
     * Constructor with message
     * @param message error message
     */
    public ValidationException(String message) {
        super(message);
        this.fieldName = "Unknown";
        this.invalidValue = null;
    }
    
    /**
     * Constructor with message and field name
     * @param message error message
     * @param fieldName the field that failed validation
     */
    public ValidationException(String message, String fieldName) {
        super(message);
        this.fieldName = fieldName;
        this.invalidValue = null;
    }
    
    /**
     * Constructor with message, field name and invalid value
     * @param message error message
     * @param fieldName the field that failed validation
     * @param invalidValue the value that was invalid
     */
    public ValidationException(String message, String fieldName, String invalidValue) {
        super(message);
        this.fieldName = fieldName;
        this.invalidValue = invalidValue;
    }
    
    /**
     * Gets the field name that failed validation
     * @return field name
     */
    public String getFieldName() {
        return fieldName;
    }
    
    /**
     * Gets the invalid value
     * @return invalid value
     */
    public String getInvalidValue() {
        return invalidValue;
    }
    
    @Override
    public String getMessage() {
        String baseMessage = super.getMessage();
        if (invalidValue != null) {
            return "Validation Error in field '" + fieldName + "': " + baseMessage + " (Invalid value: " + invalidValue + ")";
        }
        return "Validation Error in field '" + fieldName + "': " + baseMessage;
    }
}

