package entities;

import interfaces.IValidatable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Base class for all entities in the repair shop system
 * Demonstrates inheritance - all entities inherit from this base class
 */
public abstract class BaseEntity implements IValidatable {
    protected int id;
    protected LocalDateTime createdAt;
    protected String validationError;
    
    /**
     * Default constructor
     */
    public BaseEntity() {
        this.id = 0;
        this.createdAt = LocalDateTime.now();
        this.validationError = null;
    }
    
    /**
     * Constructor with ID
     * @param id the entity ID
     */
    public BaseEntity(int id) {
        this.id = id;
        this.createdAt = LocalDateTime.now();
        this.validationError = null;
    }
    
    /**
     * Constructor with ID and creation time
     * @param id the entity ID
     * @param createdAt creation timestamp
     */
    public BaseEntity(int id, LocalDateTime createdAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.validationError = null;
    }
    
    /**
     * Gets the entity ID
     * @return entity ID
     */
    public int getId() {
        return id;
    }
    
    /**
     * Sets the entity ID
     * @param id the entity ID
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Gets the creation timestamp
     * @return creation timestamp
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    /**
     * Sets the creation timestamp
     * @param createdAt creation timestamp
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    /**
     * Gets formatted creation date
     * @return formatted date string
     */
    public String getFormattedCreatedDate() {
        return createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    
    /**
     * Abstract method for entity-specific validation
     * Must be implemented by subclasses
     * @return true if entity is valid
     */
    public abstract boolean isValid();
    
    /**
     * Gets validation error message
     * @return error message if invalid, null if valid
     */
    @Override
    public String getValidationError() {
        return validationError;
    }
    
    /**
     * Sets validation error message
     * @param error the error message
     */
    protected void setValidationError(String error) {
        this.validationError = error;
    }
    
    /**
     * Clears validation errors
     */
    @Override
    public void clearValidationErrors() {
        this.validationError = null;
    }
    
    /**
     * Abstract method for getting entity display name
     * Must be implemented by subclasses
     * @return display name
     */
    public abstract String getDisplayName();
    
    /**
     * Gets entity information as string
     * @return entity information
     */
    @Override
    public String toString() {
        return getDisplayName() + " (ID: " + id + ", Created: " + getFormattedCreatedDate() + ")";
    }
}

