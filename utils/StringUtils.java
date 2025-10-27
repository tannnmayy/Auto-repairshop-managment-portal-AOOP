package utils;

import interfaces.IStringProcessor;
import exceptions.ValidationException;

/**
 * Utility class for string operations
 * Demonstrates string handling functions and implements IStringProcessor interface
 */
public class StringUtils implements IStringProcessor {
    
    /**
     * Default constructor
     */
    public StringUtils() {
        // Simple constructor for utility class
    }
    
    /**
     * Formats a string for display (capitalizes first letter)
     * @param input the input string
     * @return formatted string
     */
    @Override
    public String formatString(String input) {
        if (input == null || input.trim().isEmpty()) {
            return "";
        }
        String trimmed = input.trim();
        return trimmed.substring(0, 1).toUpperCase() + trimmed.substring(1).toLowerCase();
    }
    
    /**
     * Validates string format (checks for empty/null strings)
     * @param input the input string
     * @return true if string format is valid
     */
    @Override
    public boolean validateStringFormat(String input) {
        return input != null && !input.trim().isEmpty();
    }
    
    /**
     * Sanitizes string input (removes extra spaces and special characters)
     * @param input the input string
     * @return sanitized string
     */
    @Override
    public String sanitizeString(String input) {
        if (input == null) {
            return "";
        }
        return input.trim().replaceAll("[^a-zA-Z0-9\\s]", "");
    }
    
    /**
     * Validates phone number format
     * @param phoneNumber the phone number to validate
     * @return true if phone number is valid
     */
    public boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return false;
        }
        // Simple phone validation - allows digits, spaces, hyphens, and parentheses
        return phoneNumber.matches("^[\\d\\s\\-\\(\\)]+$") && phoneNumber.replaceAll("[^\\d]", "").length() >= 10;
    }
    
    /**
     * Validates email format
     * @param email the email to validate
     * @return true if email is valid
     */
    public boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }
    
    /**
     * Validates license plate format
     * @param plate the license plate to validate
     * @return true if license plate is valid
     */
    public boolean isValidLicensePlate(String plate) {
        if (plate == null || plate.trim().isEmpty()) {
            return false;
        }
        // Simple license plate validation - allows alphanumeric characters
        return plate.matches("^[a-zA-Z0-9]+$") && plate.length() >= 3;
    }
    
    /**
     * Converts string to proper case (Title Case)
     * @param input the input string
     * @return string in proper case
     */
    public String toProperCase(String input) {
        if (input == null || input.trim().isEmpty()) {
            return "";
        }
        String[] words = input.trim().split("\\s+");
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < words.length; i++) {
            if (i > 0) {
                result.append(" ");
            }
            if (words[i].length() > 0) {
                result.append(words[i].substring(0, 1).toUpperCase())
                      .append(words[i].substring(1).toLowerCase());
            }
        }
        return result.toString();
    }
    
    /**
     * Truncates string to specified length
     * @param input the input string
     * @param maxLength maximum length
     * @return truncated string
     */
    public String truncateString(String input, int maxLength) {
        if (input == null) {
            return "";
        }
        if (input.length() <= maxLength) {
            return input;
        }
        return input.substring(0, maxLength - 3) + "...";
    }
    
    /**
     * Validates and formats customer name
     * @param name the customer name
     * @return formatted name
     * @throws ValidationException if name is invalid
     */
    public String validateAndFormatCustomerName(String name) throws ValidationException {
        if (!validateStringFormat(name)) {
            throw new ValidationException("Customer name cannot be empty", "name", name);
        }
        
        String sanitized = sanitizeString(name);
        if (sanitized.length() < 2) {
            throw new ValidationException("Customer name must be at least 2 characters long", "name", name);
        }
        
        return toProperCase(sanitized);
    }
}

