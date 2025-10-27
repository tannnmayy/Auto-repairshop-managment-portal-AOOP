import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GenerateBill extends JDialog {
    private JTextField customerIdField;
    private JTextArea previewArea;

    public GenerateBill(Frame owner) {
        super(owner, "Generate Bill", true);
        setSize(800, 560);
        setLocationRelativeTo(owner);

        JPanel root = new JPanel(new BorderLayout());
        UIStyle.styleRoot(root);
        add(root);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        UIStyle.styleRoot(top);
        JLabel lbl = new JLabel("Customer ID:");
        UIStyle.styleLabel(lbl);
        top.add(lbl);
        customerIdField = new JTextField(10);
        UIStyle.styleField(customerIdField);
        top.add(customerIdField);
        JButton loadBtn = new JButton("Preview");
        UIStyle.stylePrimaryButton(loadBtn);
        top.add(loadBtn);
        // PDF export removed per request
        root.add(top, BorderLayout.NORTH);

        previewArea = new JTextArea();
        previewArea.setEditable(false);
        previewArea.setBackground(UIStyle.BG_DARK);
        previewArea.setForeground(UIStyle.TEXT_LIGHT);
        previewArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        root.add(new JScrollPane(previewArea), BorderLayout.CENTER);

        loadBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                previewBill();
            }
        });
        // No PDF export button/action
    }

    private void previewBill() {
        String cidText = customerIdField.getText();
        if (cidText == null || cidText.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a customer ID.");
            return;
        }
        int customerId;
        try { customerId = Integer.parseInt(cidText.trim()); } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Customer ID must be a number.");
            return;
        }

        BillData data = BillDataLoader.loadForCustomer(customerId);
        if (data == null) {
            JOptionPane.showMessageDialog(this, "No data found for customer.");
            return;
        }
        previewArea.setText(BillFormatter.formatText(data));
    }

    // PDF export removed
}

class BillData {
    String customerName;
    String phone;
    java.util.List<String> vehicleLines = new java.util.ArrayList<>();
    java.util.List<String> repairLines = new java.util.ArrayList<>();
    java.math.BigDecimal subtotal = java.math.BigDecimal.ZERO;
    int priorVisits = 0;
}

class BillDataLoader {
    public static BillData loadForCustomer(int customerId) {
        String customerSql = "SELECT name, phone FROM customers WHERE customer_id = ?";
        String vehiclesSql = "SELECT model, plate FROM vehicles WHERE customer_id = ?";
        String repairsSql = "SELECT d.description, d.cost FROM damages d JOIN vehicles v ON v.vehicle_id = d.vehicle_id WHERE v.customer_id = ?";
        String visitsSql = "SELECT COUNT(*) FROM vehicles WHERE customer_id = ?";

        try (java.sql.Connection conn = DatabaseConnection.getConnection()) {
            BillData data = new BillData();

            try (java.sql.PreparedStatement s = conn.prepareStatement(customerSql)) {
                s.setInt(1, customerId);
                try (java.sql.ResultSet rs = s.executeQuery()) {
                    if (!rs.next()) return null;
                    data.customerName = rs.getString(1);
                    data.phone = rs.getString(2);
                }
            }

            try (java.sql.PreparedStatement s = conn.prepareStatement(vehiclesSql);
                 java.sql.PreparedStatement s2 = conn.prepareStatement(repairsSql)) {
                s.setInt(1, customerId);
                try (java.sql.ResultSet rs = s.executeQuery()) {
                    while (rs.next()) {
                        data.vehicleLines.add(rs.getString(1) + " (" + rs.getString(2) + ")");
                    }
                }

                s2.setInt(1, customerId);
                try (java.sql.ResultSet rs = s2.executeQuery()) {
                    while (rs.next()) {
                        String desc = rs.getString(1);
                        java.math.BigDecimal cost = rs.getBigDecimal(2);
                        data.repairLines.add(desc + " - $" + cost);
                        if (cost != null) data.subtotal = data.subtotal.add(cost);
                    }
                }
            }

            try (java.sql.PreparedStatement s = conn.prepareStatement(visitsSql)) {
                s.setInt(1, customerId);
                try (java.sql.ResultSet rs = s.executeQuery()) {
                    if (rs.next()) data.priorVisits = Math.max(0, rs.getInt(1) - 1);
                }
            }

            return data;
        } catch (java.sql.SQLException e) {
            javax.swing.JOptionPane.showMessageDialog(null, "Failed to load bill data: " + e.getMessage());
            return null;
        }
    }
}

class BillFormatter {
    public static String formatText(BillData data) {
        StringBuilder sb = new StringBuilder();
        sb.append("AUTO REPAIR - BILL\n");
        sb.append("Date: ").append(java.time.LocalDateTime.now()).append("\n\n");
        sb.append("Customer: ").append(data.customerName).append("\n");
        sb.append("Phone: ").append(data.phone).append("\n\n");
        sb.append("Vehicles:\n");
        for (String v : data.vehicleLines) sb.append("  - ").append(v).append("\n");
        sb.append("\nRepairs:\n");
        for (String r : data.repairLines) sb.append("  - ").append(r).append("\n");
        sb.append("\nSubtotal: $").append(data.subtotal).append("\n");
        java.math.BigDecimal discountRate = new java.math.BigDecimal(data.priorVisits).multiply(new java.math.BigDecimal("0.10"));
        if (discountRate.compareTo(new java.math.BigDecimal("0.50")) > 0) discountRate = new java.math.BigDecimal("0.50");
        java.math.BigDecimal discount = data.subtotal.multiply(discountRate);
        java.math.BigDecimal total = data.subtotal.subtract(discount);
        sb.append("Loyalty discount (10% x ").append(data.priorVisits).append("): -$").append(discount).append("\n");
        sb.append("Total: $").append(total).append("\n");
        return sb.toString();
    }
}

// PDF writer removed


