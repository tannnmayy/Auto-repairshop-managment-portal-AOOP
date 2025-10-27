import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccessInformation extends JDialog {
    private JTable table;
    private JComboBox<String> entityCombo;

    public AccessInformation(Frame owner) {
        super(owner, "Access Information", true);
        setSize(800, 560);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        UIStyle.styleRoot(top);
        JLabel entityLbl = new JLabel("Entity:");
        UIStyle.styleLabel(entityLbl);
        top.add(entityLbl);
        entityCombo = new JComboBox<>(new String[]{"customers", "vehicles", "damages", "appointments"});
        entityCombo.setBackground(UIStyle.FIELD_BG);
        entityCombo.setForeground(UIStyle.TEXT_LIGHT);
        top.add(entityCombo);
        JButton load = new JButton("Load");
        UIStyle.stylePrimaryButton(load);
        top.add(load);
        JButton delete = new JButton("Delete Selected");
        UIStyle.styleDangerButton(delete);
        top.add(delete);
        add(top, BorderLayout.NORTH);

        table = new JTable();
        UIStyle.styleTable(table);
        JScrollPane sp = new JScrollPane(table);
        sp.getViewport().setBackground(UIStyle.BG_DARK);
        add(sp, BorderLayout.CENTER);

        load.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadData();
            }
        });
        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteSelected();
            }
        });

        loadData();
    }

    private void loadData() {
        String entity = (String) entityCombo.getSelectedItem();
        if (entity == null) return;

        try (Connection conn = DatabaseConnection.getConnection()) {
            DefaultTableModel model = new DefaultTableModel();
            PreparedStatement stmt;
            ResultSet rs;

            switch (entity) {
                case "customers":
                    stmt = conn.prepareStatement("SELECT customer_id, name, phone FROM customers ORDER BY customer_id");
                    rs = stmt.executeQuery();
                    model.setColumnIdentifiers(new String[]{"customer_id", "name", "phone"});
                    while (rs.next()) model.addRow(new Object[]{rs.getInt(1), rs.getString(2), rs.getString(3)});
                    break;
                case "vehicles":
                    stmt = conn.prepareStatement("SELECT vehicle_id, customer_id, model, plate FROM vehicles ORDER BY vehicle_id");
                    rs = stmt.executeQuery();
                    model.setColumnIdentifiers(new String[]{"vehicle_id", "customer_id", "model", "plate"});
                    while (rs.next()) model.addRow(new Object[]{rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4)});
                    break;
                case "damages":
                    stmt = conn.prepareStatement("SELECT damage_id, vehicle_id, description, cost, status FROM damages ORDER BY damage_id");
                    rs = stmt.executeQuery();
                    model.setColumnIdentifiers(new String[]{"damage_id", "vehicle_id", "description", "cost", "status"});
                    while (rs.next()) model.addRow(new Object[]{rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getBigDecimal(4), rs.getString(5)});
                    break;
                case "appointments":
                    stmt = conn.prepareStatement("SELECT appointment_id, customer_name, appointment_at, details, status FROM appointments ORDER BY appointment_at DESC");
                    rs = stmt.executeQuery();
                    model.setColumnIdentifiers(new String[]{"appointment_id", "customer_name", "appointment_at", "details", "status"});
                    while (rs.next()) model.addRow(new Object[]{rs.getInt(1), rs.getString(2), rs.getTimestamp(3), rs.getString(4), rs.getString(5)});
                    break;
                default:
                    return;
            }

            table.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to load data: " + e.getMessage());
        }
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a row to delete.");
            return;
        }
        String entity = (String) entityCombo.getSelectedItem();
        if (entity == null) return;

        Object idVal = table.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Delete selected " + entity + " record?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        String sql;
        switch (entity) {
            case "customers":
                sql = "DELETE FROM customers WHERE customer_id = ?";
                break;
            case "vehicles":
                sql = "DELETE FROM vehicles WHERE vehicle_id = ?";
                break;
            case "damages":
                sql = "DELETE FROM damages WHERE damage_id = ?";
                break;
            case "appointments":
                sql = "DELETE FROM appointments WHERE appointment_id = ?";
                break;
            default:
                return;
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, idVal);
            int affected = stmt.executeUpdate();
            if (affected > 0) {
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "No rows deleted.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Delete failed: " + e.getMessage());
        }
    }
}


