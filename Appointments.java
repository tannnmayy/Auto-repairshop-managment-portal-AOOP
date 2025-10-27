import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Appointments extends JDialog {
    private JTextField customerField;
    private JTextField dateTimeField;
    private JTextField detailsField;
    private JComboBox<String> statusCombo;
    private JComboBox<String> existingCombo;

    public Appointments(Frame owner, String currentUser) {
        super(owner, "Manage Appointments", true);
        setSize(800, 560);
        setLocationRelativeTo(owner);

        JPanel root = new JPanel();
        root.setLayout(null);
        UIStyle.styleRoot(root);
        add(root);

        JLabel title = new JLabel("Appointments");
        UIStyle.styleTitle(title);
        title.setBounds(24, 16, 300, 30);
        root.add(title);

        JLabel customerLabel = new JLabel("Customer Name:");
        UIStyle.styleLabel(customerLabel);
        customerLabel.setBounds(24, 66, 150, 24);
        root.add(customerLabel);

        customerField = new JTextField();
        UIStyle.styleField(customerField);
        customerField.setBounds(24, 92, 550, 28);
        root.add(customerField);

        JLabel dtLabel = new JLabel("Appointment Date (e.g., 2024-10-06 10:00):");
        UIStyle.styleLabel(dtLabel);
        dtLabel.setBounds(24, 130, 400, 24);
        root.add(dtLabel);

        dateTimeField = new JTextField();
        UIStyle.styleField(dateTimeField);
        dateTimeField.setBounds(24, 156, 550, 28);
        root.add(dateTimeField);

        JLabel detailsLabel = new JLabel("Details:");
        UIStyle.styleLabel(detailsLabel);
        detailsLabel.setBounds(24, 194, 150, 24);
        root.add(detailsLabel);

        detailsField = new JTextField();
        UIStyle.styleField(detailsField);
        detailsField.setBounds(24, 220, 550, 28);
        root.add(detailsField);

        JLabel statusLabel = new JLabel("Appointment Status:");
        UIStyle.styleLabel(statusLabel);
        statusLabel.setBounds(24, 258, 200, 24);
        root.add(statusLabel);

        statusCombo = new JComboBox<>(new String[]{"Scheduled", "In Progress", "Completed", "Cancelled"});
        statusCombo.setBounds(24, 284, 550, 60);
        statusCombo.setBackground(UIStyle.FIELD_BG);
        statusCombo.setForeground(UIStyle.TEXT_LIGHT);
        root.add(statusCombo);

        JButton addBtn = new JButton("Add Appointment");
        UIStyle.styleDangerButton(addBtn);
        addBtn.setBounds(24, 362, 200, 40);
        root.add(addBtn);

        JButton editBtn = new JButton("Edit Appointment");
        UIStyle.stylePrimaryButton(editBtn);
        editBtn.setBounds(240, 362, 200, 40);
        root.add(editBtn);

        JLabel selectLabel = new JLabel("Select an appointment to edit:");
        UIStyle.styleLabel(selectLabel);
        selectLabel.setBounds(24, 416, 300, 24);
        root.add(selectLabel);

        existingCombo = new JComboBox<>(new String[]{});
        existingCombo.setBounds(24, 442, 550, 60);
        existingCombo.setBackground(UIStyle.FIELD_BG);
        existingCombo.setForeground(UIStyle.TEXT_LIGHT);
        root.add(existingCombo);

        // Load existing appointments
        loadAppointments();

        addBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addAppointment();
            }
        });
        editBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editAppointment();
            }
        });
    }

    private void loadAppointments() {
        existingCombo.removeAllItems();
        try (java.sql.Connection conn = DatabaseConnection.getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(
                     "SELECT appointment_id, customer_name, DATE_FORMAT(appointment_at, '%Y-%m-%d %H:%i'), status FROM appointments ORDER BY appointment_at DESC");
             java.sql.ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String display = rs.getInt(1) + ": " + rs.getString(2) + " - " + rs.getString(3) + " (" + rs.getString(4) + ")";
                existingCombo.addItem(display);
            }
        } catch (java.sql.SQLException ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "Failed to load appointments: " + ex.getMessage());
        }
    }

    private void addAppointment() {
        String name = customerField.getText();
        String dt = dateTimeField.getText();
        String details = detailsField.getText();
        String status = (String) statusCombo.getSelectedItem();

        if (name == null || name.trim().isEmpty() || dt == null || dt.trim().isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Enter customer name and appointment date/time.");
            return;
        }

        String sql = "INSERT INTO appointments (customer_name, appointment_at, details, status) VALUES (?, ?, ?, ?)";
        try (java.sql.Connection conn = DatabaseConnection.getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, dt);
            stmt.setString(3, details);
            stmt.setString(4, status);
            stmt.executeUpdate();
            javax.swing.JOptionPane.showMessageDialog(this, "Appointment added.");
            loadAppointments();
        } catch (java.sql.SQLException ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "Add failed: " + ex.getMessage());
        }
    }

    private void editAppointment() {
        int idx = existingCombo.getSelectedIndex();
        if (idx < 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "Select an appointment to edit.");
            return;
        }
        String selected = (String) existingCombo.getSelectedItem();
        if (selected == null || !selected.contains(":")) return;
        int id = Integer.parseInt(selected.substring(0, selected.indexOf(":"))); 

        String name = customerField.getText();
        String dt = dateTimeField.getText();
        String details = detailsField.getText();
        String status = (String) statusCombo.getSelectedItem();

        if (name == null || name.trim().isEmpty() || dt == null || dt.trim().isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Enter customer name and appointment date/time.");
            return;
        }

        String sql = "UPDATE appointments SET customer_name = ?, appointment_at = ?, details = ?, status = ? WHERE appointment_id = ?";
        try (java.sql.Connection conn = DatabaseConnection.getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, dt);
            stmt.setString(3, details);
            stmt.setString(4, status);
            stmt.setInt(5, id);
            stmt.executeUpdate();
            javax.swing.JOptionPane.showMessageDialog(this, "Appointment updated.");
            loadAppointments();
        } catch (java.sql.SQLException ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "Update failed: " + ex.getMessage());
        }
    }
}