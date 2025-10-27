package entities;

import utils.StringUtils;
import exceptions.ValidationException;

/**
 * Vehicle entity class
 * Demonstrates inheritance from BaseEntity and method overriding
 */
public class Vehicle extends BaseEntity {
    private int customerId;
    private String model;
    private String plate;
    private StringUtils stringUtils;
    
    /**
     * Default constructor
     */
    public Vehicle() {
        super();
        this.customerId = 0;
        this.model = "";
        this.plate = "";
        this.stringUtils = new StringUtils();
    }
    
    /**
     * Constructor with customer ID and model
     * @param customerId customer ID
     * @param model vehicle model
     */
    public Vehicle(int customerId, String model) {
        super();
        this.customerId = customerId;
        this.model = model;
        this.plate = "";
        this.stringUtils = new StringUtils();
    }
    
    /**
     * Constructor with all parameters
     * @param customerId customer ID
     * @param model vehicle model
     * @param plate license plate
     */
    public Vehicle(int customerId, String model, String plate) {
        super();
        this.customerId = customerId;
        this.model = model;
        this.plate = plate;
        this.stringUtils = new StringUtils();
    }
    
    /**
     * Constructor with ID and all parameters
     * @param id vehicle ID
     * @param customerId customer ID
     * @param model vehicle model
     * @param plate license plate
     */
    public Vehicle(int id, int customerId, String model, String plate) {
        super(id);
        this.customerId = customerId;
        this.model = model;
        this.plate = plate;
        this.stringUtils = new StringUtils();
    }
    
    /**
     * Gets customer ID
     * @return customer ID
     */
    public int getCustomerId() {
        return customerId;
    }
    
    /**
     * Sets customer ID
     * @param customerId customer ID
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    
    /**
     * Gets vehicle model
     * @return vehicle model
     */
    public String getModel() {
        return model;
    }
    
    /**
     * Sets vehicle model with validation
     * @param model vehicle model
     * @throws ValidationException if model is invalid
     */
    public void setModel(String model) throws ValidationException {
        if (!stringUtils.validateStringFormat(model)) {
            throw new ValidationException("Vehicle model cannot be empty", "model", model);
        }
        this.model = stringUtils.toProperCase(model);
    }
    
    /**
     * Gets license plate
     * @return license plate
     */
    public String getPlate() {
        return plate;
    }
    
    /**
     * Sets license plate with validation
     * @param plate license plate
     * @throws ValidationException if plate is invalid
     */
    public void setPlate(String plate) throws ValidationException {
        if (!stringUtils.validateStringFormat(plate)) {
            throw new ValidationException("License plate cannot be empty", "plate", plate);
        }
        if (!stringUtils.isValidLicensePlate(plate)) {
            throw new ValidationException("Invalid license plate format", "plate", plate);
        }
        this.plate = plate.toUpperCase();
    }
    
    /**
     * Validates vehicle data
     * Overrides the abstract method from BaseEntity
     * @return true if vehicle is valid
     */
    @Override
    public boolean isValid() {
        clearValidationErrors();
        
        if (customerId <= 0) {
            setValidationError("Customer ID must be greater than 0");
            return false;
        }
        
        if (model == null || model.trim().isEmpty()) {
            setValidationError("Vehicle model is required");
            return false;
        }
        
        if (plate == null || plate.trim().isEmpty()) {
            setValidationError("License plate is required");
            return false;
        }
        
        if (!stringUtils.isValidLicensePlate(plate)) {
            setValidationError("Invalid license plate format");
            return false;
        }
        
        return true;
    }
    
    /**
     * Gets vehicle display name
     * Overrides the abstract method from BaseEntity
     * @return display name
     */
    @Override
    public String getDisplayName() {
        return "Vehicle: " + model + " (" + plate + ")";
    }
    
    /**
     * Gets vehicle information as string
     * Overrides toString method from BaseEntity
     * @return vehicle information
     */
    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", model='" + model + '\'' +
                ", plate='" + plate + '\'' +
                ", createdAt=" + getFormattedCreatedDate() +
                '}';
    }
    
    /**
     * Method overloading - creates vehicle with customer ID and model
     * @param customerId customer ID
     * @param model vehicle model
     * @return new Vehicle instance
     */
    public static Vehicle createVehicle(int customerId, String model) {
        return new Vehicle(customerId, model);
    }
    
    /**
     * Method overloading - creates vehicle with all parameters
     * @param customerId customer ID
     * @param model vehicle model
     * @param plate license plate
     * @return new Vehicle instance
     */
    public static Vehicle createVehicle(int customerId, String model, String plate) {
        return new Vehicle(customerId, model, plate);
    }
}

