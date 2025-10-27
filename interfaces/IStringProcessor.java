package interfaces;

/**
 * Interface for string processing operations
 * Demonstrates interface usage for string handling functionality
 */
public interface IStringProcessor {
    /**
     * Formats a string for display
     * @param input the input string
     * @return formatted string
     */
    String formatString(String input);
    
    /**
     * Validates string format
     * @param input the input string
     * @return true if string format is valid
     */
    boolean validateStringFormat(String input);
    
    /**
     * Sanitizes string input
     * @param input the input string
     * @return sanitized string
     */
    String sanitizeString(String input);
}

