import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewAllInformation extends JDialog {
    private JTable table;

    public ViewAllInformation(Frame owner) {
        super(owner, "All Information", true);
        setSize(800, 560);
        setLocationRelativeTo(owner);

        setLayout(new BorderLayout());
        table = new JTable();
        UIStyle.styleTable(table);
        JScrollPane sp = new JScrollPane(table);
        sp.getViewport().setBackground(UIStyle.BG_DARK);
        add(sp, BorderLayout.CENTER);

        JButton refresh = new JButton("Refresh");
        refresh.addActionListener(e -> loadData());
        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        UIStyle.styleRoot(south);
        south.add(refresh);
        UIStyle.stylePrimaryButton(refresh);
        add(south, BorderLayout.SOUTH);

        loadData();
    }

    private void loadData() {
        String[] columns = new String[]{
                "customer_id", "customer_name", "phone",
                "vehicle_id", "model", "plate",
                "damage_id", "description", "cost"
        };
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };

        String sql = "SELECT c.customer_id, c.name, c.phone, v.vehicle_id, v.model, v.plate, d.damage_id, d.description, d.cost " +
                "FROM customers c " +
                "LEFT JOIN vehicles v ON v.customer_id = c.customer_id " +
                "LEFT JOIN damages d ON d.vehicle_id = v.vehicle_id " +
                "ORDER BY c.customer_id, v.vehicle_id, d.damage_id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Object[] row = new Object[]{
                        rs.getObject(1), rs.getObject(2), rs.getObject(3),
                        rs.getObject(4), rs.getObject(5), rs.getObject(6),
                        rs.getObject(7), rs.getObject(8), rs.getObject(9)
                };
                model.addRow(row);
            }
            table.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to load data: " + e.getMessage());
        }
    }
}


