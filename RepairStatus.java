import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RepairStatus extends JDialog {
    private JComboBox<String> recordCombo;
    private JComboBox<String> statusCombo;

    public RepairStatus(Frame owner) {
        super(owner, "Edit Repair Status", true);
        setSize(800, 560);
        setLocationRelativeTo(owner);

        JPanel root = new JPanel();
        root.setLayout(null);
        UIStyle.styleRoot(root);
        add(root);

        JLabel selectLabel = new JLabel("Select a record to edit the repair status:");
        UIStyle.styleLabel(selectLabel);
        selectLabel.setBounds(24, 16, 400, 24);
        root.add(selectLabel);

        recordCombo = new JComboBox<>();
        recordCombo.setBounds(24, 42, 620, 40);
        recordCombo.setBackground(UIStyle.FIELD_BG);
        recordCombo.setForeground(UIStyle.TEXT_LIGHT);
        root.add(recordCombo);

        JLabel statusLabel = new JLabel("Select new repair status:");
        UIStyle.styleLabel(statusLabel);
        statusLabel.setBounds(24, 92, 400, 24);
        root.add(statusLabel);

        statusCombo = new JComboBox<>(new String[]{"Pending", "In Progress", "Completed", "Cancelled"});
        statusCombo.setBounds(24, 118, 620, 40);
        statusCombo.setBackground(UIStyle.FIELD_BG);
        statusCombo.setForeground(UIStyle.TEXT_LIGHT);
        root.add(statusCombo);

        JButton updateBtn = new JButton("Update Status");
        UIStyle.stylePrimaryButton(updateBtn);
        updateBtn.setBounds(24, 170, 180, 36);
        root.add(updateBtn);

        updateBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateStatus();
            }
        });

        loadRecords();
    }

    private void loadRecords() {
        recordCombo.removeAllItems();
        String sql = "SELECT d.damage_id, c.name, v.model, v.plate, d.status " +
                "FROM damages d " +
                "JOIN vehicles v ON v.vehicle_id = d.vehicle_id " +
                "JOIN customers c ON c.customer_id = v.customer_id " +
                "ORDER BY d.damage_id DESC";
        try (java.sql.Connection conn = DatabaseConnection.getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
             java.sql.ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String display = rs.getInt(1) + ": " + rs.getString(2) + " (" + rs.getString(3) + " - " + rs.getString(4) + ")";
                recordCombo.addItem(display);
            }
        } catch (java.sql.SQLException ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "Failed to load records: " + ex.getMessage());
        }
    }

    private void updateStatus() {
        int idx = recordCombo.getSelectedIndex();
        if (idx < 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "Select a record first.");
            return;
        }
        String selected = (String) recordCombo.getSelectedItem();
        if (selected == null || !selected.contains(":")) return;
        int id = Integer.parseInt(selected.substring(0, selected.indexOf(":")));
        String newStatus = (String) statusCombo.getSelectedItem();

        String sql = "UPDATE damages SET status = ? WHERE damage_id = ?";
        try (java.sql.Connection conn = DatabaseConnection.getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, id);
            int affected = stmt.executeUpdate();
            if (affected > 0) {
                javax.swing.JOptionPane.showMessageDialog(this, "Status updated.");
                loadRecords();
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "No changes made.");
            }
        } catch (java.sql.SQLException ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "Update failed: " + ex.getMessage());
        }
    }
}


