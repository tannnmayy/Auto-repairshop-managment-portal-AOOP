import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class MainDashboard extends JFrame {
    private final String currentUser;

    public MainDashboard(String currentUser) {
        super("Repair Shop Management");
        this.currentUser = currentUser == null ? "Admin" : currentUser;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 560);
        setLocationRelativeTo(null);

        JPanel root = new JPanel();
        root.setLayout(new BorderLayout());
        UIStyle.styleRoot(root);
        add(root);

        JPanel header = buildHeader();
        root.add(header, BorderLayout.NORTH);

        JPanel content = buildContent();
        root.add(content, BorderLayout.CENTER);

        // Footer with Logout
        JPanel footer = new JPanel(new BorderLayout());
        UIStyle.styleRoot(footer);
        footer.setBorder(BorderFactory.createEmptyBorder(12, 24, 24, 24));
        JButton logoutBtn = new JButton("Logout");
        UIStyle.styleDangerButton(logoutBtn);
        logoutBtn.setPreferredSize(new Dimension(140, 40));
        footer.add(logoutBtn, BorderLayout.EAST);
        root.add(footer, BorderLayout.SOUTH);

        logoutBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        Login.main(new String[]{});
                    }
                });
            }
        });
    }

    private JPanel buildHeader() {
        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());
        container.setBorder(BorderFactory.createEmptyBorder(24, 24, 16, 24));
        UIStyle.styleRoot(container);

        // Logo row
        JLabel logo = new JLabel("AUTO REPAIR", SwingConstants.CENTER);
        UIStyle.styleTitle(logo);
        logo.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
        container.add(logo, BorderLayout.NORTH);

        // Date/User pill
        JPanel pill = new JPanel(new BorderLayout());
        pill.setBackground(new Color(40, 40, 40));
        pill.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        JLabel meta = new JLabel("", SwingConstants.CENTER);
        meta.setFont(new Font("SansSerif", Font.BOLD, 16));
        meta.setForeground(Color.WHITE);
        pill.add(meta, BorderLayout.CENTER);
        container.add(pill, BorderLayout.CENTER);

        // Live updater for day/time and customers today
        javax.swing.Timer t = new javax.swing.Timer(1000, new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                String now = java.time.ZonedDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("EEEE, MMM d yyyy  h:mm:ss a"));
                int customersToday = fetchCustomersToday();
                meta.setText(now + "    Customers today: " + customersToday);
            }
        });
        t.setRepeats(true);
        t.start();

        return container;
    }

    private JPanel buildContent() {
        JPanel panel = new JPanel();
        UIStyle.styleRoot(panel);
        panel.setBorder(BorderFactory.createEmptyBorder(8, 24, 24, 24));
        panel.setLayout(new GridLayout(4, 1, 0, 16));

        panel.add(primaryButton("Add Customer, Vehicle, and Repair Info", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new RepairShopApp();
            }
        }));

        panel.add(primaryButton("View All Information", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ViewAllInformation(MainDashboard.this).setVisible(true);
            }
        }));

        panel.add(primaryButton("Access Information", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AccessInformation(MainDashboard.this).setVisible(true);
            }
        }));

        panel.add(primaryButton("Manage Appointments", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Appointments(MainDashboard.this, currentUser).setVisible(true);
            }
        }));

        panel.add(primaryButton("Edit Repair Status", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new RepairStatus(MainDashboard.this).setVisible(true);
            }
        }));

        panel.add(primaryButton("Generate Bill", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new GenerateBill(MainDashboard.this).setVisible(true);
            }
        }));

        return panel;
    }

    private JComponent primaryButton(String text, ActionListener listener) {
        JButton btn = new JButton(text);
        btn.setBackground(Color.BLACK);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 16));
        btn.setPreferredSize(new Dimension(0, 56));
        btn.addActionListener(listener);

        JPanel wrapper = new JPanel(new BorderLayout());
        UIStyle.styleRoot(wrapper);
        wrapper.add(btn, BorderLayout.CENTER);
        return wrapper;
    }

    private int fetchCustomersToday() {
        String sql = "SELECT COUNT(*) FROM customers WHERE DATE(created_at) = CURDATE()";
        try (java.sql.Connection conn = DatabaseConnection.getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
             java.sql.ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (java.sql.SQLException ignored) {}
        return 0;
    }
}


