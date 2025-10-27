package entities;

import utils.StringUtils;
import exceptions.ValidationException;

/**
 * Customer entity class
 * Demonstrates inheritance from BaseEntity and method overriding
 */
public class Customer extends BaseEntity {
    private String name;
    private String phone;
    private StringUtils stringUtils;
    
    /**
     * Default constructor
     */
    public Customer() {
        super();
        this.name = "";
        this.phone = "";
        this.stringUtils = new StringUtils();
    }
    
    /**
     * Constructor with name
     * @param name customer name
     */
    public Customer(String name) {
        super();
        this.name = name;
        this.phone = "";
        this.stringUtils = new StringUtils();
    }
    
    /**
     * Constructor with name and phone
     * @param name customer name
     * @param phone customer phone
     */
    public Customer(String name, String phone) {
        super();
        this.name = name;
        this.phone = phone;
        this.stringUtils = new StringUtils();
    }
    
    /**
     * Constructor with all parameters
     * @param id customer ID
     * @param name customer name
     * @param phone customer phone
     */
    public Customer(int id, String name, String phone) {
        super(id);
        this.name = name;
        this.phone = phone;
        this.stringUtils = new StringUtils();
    }
    
    /**
     * Gets customer name
     * @return customer name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets customer name with validation
     * @param name customer name
     * @throws ValidationException if name is invalid
     */
    public void setName(String name) throws ValidationException {
        this.name = stringUtils.validateAndFormatCustomerName(name);
    }
    
    /**
     * Gets customer phone
     * @return customer phone
     */
    public String getPhone() {
        return phone;
    }
    
    /**
     * Sets customer phone with validation
     * @param phone customer phone
     * @throws ValidationException if phone is invalid
     */
    public void setPhone(String phone) throws ValidationException {
        if (phone != null && !phone.trim().isEmpty() && !stringUtils.isValidPhoneNumber(phone)) {
            throw new ValidationException("Invalid phone number format", "phone", phone);
        }
        this.phone = phone;
    }
    
    /**
     * Validates customer data
     * Overrides the abstract method from BaseEntity
     * @return true if customer is valid
     */
    @Override
    public boolean isValid() {
        clearValidationErrors();
        
        if (name == null || name.trim().isEmpty()) {
            setValidationError("Customer name is required");
            return false;
        }
        
        if (phone != null && !phone.trim().isEmpty() && !stringUtils.isValidPhoneNumber(phone)) {
            setValidationError("Invalid phone number format");
            return false;
        }
        
        return true;
    }
    
    /**
     * Gets customer display name
     * Overrides the abstract method from BaseEntity
     * @return display name
     */
    @Override
    public String getDisplayName() {
        return "Customer: " + name;
    }
    
    /**
     * Gets customer information as string
     * Overrides toString method from BaseEntity
     * @return customer information
     */
    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", createdAt=" + getFormattedCreatedDate() +
                '}';
    }
    
    /**
     * Method overloading - creates customer with just name
     * @param name customer name
     * @return new Customer instance
     */
    public static Customer createCustomer(String name) {
        return new Customer(name);
    }
    
    /**
     * Method overloading - creates customer with name and phone
     * @param name customer name
     * @param phone customer phone
     * @return new Customer instance
     */
    public static Customer createCustomer(String name, String phone) {
        return new Customer(name, phone);
    }
}

