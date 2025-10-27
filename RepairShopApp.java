import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class RepairShopApp {
    // Connections now retrieved via DatabaseConnection

    // GUI components
    private JFrame frame;
    private JTextField nameField, phoneField, modelField, plateField, damageDescField, damageCostField;
    private JButton addCustomerBtn, removeCustomerBtn, addVehicleBtn, addDamageBtn, clearAllDataBtn;

    public RepairShopApp() {
        // Create and set up the window
        frame = new JFrame("Automobile Repair Shop");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 560);

        JPanel panel = new JPanel();
        UIStyle.styleRoot(panel);
        frame.add(panel);
        placeComponents(panel);

        // TAdd event listeners for buttons
        addCustomerBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addCustomer();
            }
        });

        removeCustomerBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeCustomer();
            }
        });

        addVehicleBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addVehicle();
            }
        });

        addDamageBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addDamage();
            }
        });

        clearAllDataBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearAllData();
            }
        });

        // Display the window
        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel nameLabel = new JLabel("Customer Name:");
        UIStyle.styleLabel(nameLabel);
        nameLabel.setBounds(10, 20, 120, 25);
        panel.add(nameLabel);

        nameField = new JTextField(20);
        UIStyle.styleField(nameField);
        nameField.setBounds(150, 20, 160, 25);
        panel.add(nameField);

        JLabel phoneLabel = new JLabel("Phone:");
        UIStyle.styleLabel(phoneLabel);
        phoneLabel.setBounds(10, 50, 120, 25);
        panel.add(phoneLabel);

        phoneField = new JTextField(20);
        UIStyle.styleField(phoneField);
        phoneField.setBounds(150, 50, 160, 25);
        panel.add(phoneField);

        addCustomerBtn = new JButton("Add Customer");
        UIStyle.stylePrimaryButton(addCustomerBtn);
        addCustomerBtn.setBounds(10, 80, 150, 25);
        panel.add(addCustomerBtn);

        removeCustomerBtn = new JButton("Remove Customer");
        UIStyle.styleDangerButton(removeCustomerBtn);
        removeCustomerBtn.setBounds(170, 80, 150, 25);
        panel.add(removeCustomerBtn);

        JLabel modelLabel = new JLabel("Vehicle Model:");
        UIStyle.styleLabel(modelLabel);
        modelLabel.setBounds(10, 120, 120, 25);
        panel.add(modelLabel);

        modelField = new JTextField(20);
        UIStyle.styleField(modelField);
        modelField.setBounds(150, 120, 160, 25);
        panel.add(modelField);

        JLabel plateLabel = new JLabel("License Plate:");
        UIStyle.styleLabel(plateLabel);
        plateLabel.setBounds(10, 150, 120, 25);
        panel.add(plateLabel);

        plateField = new JTextField(20);
        UIStyle.styleField(plateField);
        plateField.setBounds(150, 150, 160, 25);
        panel.add(plateField);

        addVehicleBtn = new JButton("Add Vehicle");
        UIStyle.stylePrimaryButton(addVehicleBtn);
        addVehicleBtn.setBounds(10, 180, 150, 25);
        panel.add(addVehicleBtn);

        JLabel damageDescLabel = new JLabel("Damage Description:");
        UIStyle.styleLabel(damageDescLabel);
        damageDescLabel.setBounds(10, 220, 120, 25);
        panel.add(damageDescLabel);

        damageDescField = new JTextField(20);
        UIStyle.styleField(damageDescField);
        damageDescField.setBounds(150, 220, 160, 25);
        panel.add(damageDescField);

        JLabel damageCostLabel = new JLabel("Repair Cost:");
        UIStyle.styleLabel(damageCostLabel);
        damageCostLabel.setBounds(10, 250, 120, 25);
        panel.add(damageCostLabel);

        damageCostField = new JTextField(20);
        UIStyle.styleField(damageCostField);
        damageCostField.setBounds(150, 250, 160, 25);
        panel.add(damageCostField);

        addDamageBtn = new JButton("Add Damage");
        UIStyle.stylePrimaryButton(addDamageBtn);
        addDamageBtn.setBounds(10, 280, 150, 25);
        panel.add(addDamageBtn);

        clearAllDataBtn = new JButton("Clear All Data");
        UIStyle.styleDangerButton(clearAllDataBtn);
        clearAllDataBtn.setBounds(10, 320, 150, 25);
        panel.add(clearAllDataBtn);
    }

    // Styling moved to UIStyle to ensure consistency across screens

    // Function to clear all data from tables
    private void clearAllData() {
        int confirm = JOptionPane.showConfirmDialog(
                frame,
                "Are you sure you want to delete all data? This action cannot be undone.",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = DatabaseConnection.getConnection()) {
                // Disable foreign key checks (necessary for deleting parent records)
                Statement stmt = conn.createStatement();
                stmt.execute("SET FOREIGN_KEY_CHECKS=0");

                // Clear all data from tables
                stmt.execute("TRUNCATE TABLE damages");
                stmt.execute("TRUNCATE TABLE vehicles");
                stmt.execute("TRUNCATE TABLE customers");

                // Enable foreign key checks again
                stmt.execute("SET FOREIGN_KEY_CHECKS=1");

                JOptionPane.showMessageDialog(frame, "All data cleared successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error clearing data.");
            }
        }
    }

    // Method to add a customer
    private void addCustomer() {
        String name = nameField.getText();
        String phone = phoneField.getText();

        if (name == null || name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter a customer name.");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO customers (name, phone) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, phone);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(frame, "Customer added successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error adding customer");
        }
    }

    // Method to remove a customer
    private void removeCustomer() {
        String name = nameField.getText();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM customers WHERE name = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(frame, "Customer removed successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error removing customer");
        }
    }

    // Method to add a vehicle
    private void addVehicle() {
        String model = modelField.getText();
        String plate = plateField.getText();
        String customerName = nameField.getText();

        if (customerName == null || customerName.trim().isEmpty() ||
                model == null || model.trim().isEmpty() ||
                plate == null || plate.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter customer name, vehicle model, and plate.");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO vehicles (customer_id, model, plate) VALUES ((SELECT customer_id FROM customers WHERE name = ?), ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, customerName);
            stmt.setString(2, model);
            stmt.setString(3, plate);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(frame, "Vehicle added successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error adding vehicle");
        }
    }

    // Method to add damage details
    private void addDamage() {
        String description = damageDescField.getText();
        String costText = damageCostField.getText();
        String plate = plateField.getText();

        if (description == null || description.trim().isEmpty() ||
                costText == null || costText.trim().isEmpty() ||
                plate == null || plate.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter plate, description, and cost.");
            return;
        }

        java.math.BigDecimal cost;
        try {
            cost = new java.math.BigDecimal(costText.trim());
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid numeric cost (e.g., 120.00).");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO damages (vehicle_id, description, cost) VALUES ((SELECT vehicle_id FROM vehicles WHERE plate = ?), ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, plate);
            stmt.setString(2, description);
            stmt.setBigDecimal(3, cost);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(frame, "Damage added successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error adding damage");
        }
    }

    // Entry point moved to Login class to ensure the application opens on the login page
}
