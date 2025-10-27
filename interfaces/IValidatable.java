package interfaces;

/**
 * Interface for objects that can be validated
 * Demonstrates interface usage for validation functionality
 */
public interface IValidatable {
    /**
     * Validates the object's data
     * @return true if object is valid
     */
    boolean isValid();
    
    /**
     * Gets validation error message
     * @return error message if invalid, null if valid
     */
    String getValidationError();
    
    /**
     * Clears validation errors
     */
    void clearValidationErrors();
}

