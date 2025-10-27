import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Login");
        frame.setSize(800, 560);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        UIStyle.styleRoot(panel);
        frame.add(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 6, 4, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel userLabel = new JLabel("Username");
        UIStyle.styleLabel(userLabel);
        panel.add(userLabel, gbc);

        gbc.gridx = 1;
        JTextField userText = new JTextField(20);
        UIStyle.styleField(userText);
        panel.add(userText, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel passwordLabel = new JLabel("Password");
        UIStyle.styleLabel(passwordLabel);
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        JPasswordField passwordText = new JPasswordField(20);
        UIStyle.styleField(passwordText);
        panel.add(passwordText, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JButton loginButton = new JButton("Login");
        UIStyle.stylePrimaryButton(loginButton);
        panel.add(loginButton, gbc);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passwordText.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter both username and password.");
                    return;
                }

                if (performLogin(username, password, frame)) {
                    MainDashboard dash = new MainDashboard(username);
                    dash.setVisible(true);
                    frame.dispose();
                }
            }
        });
    }
    
    private static boolean performLogin(String username, String password, JFrame frame) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn == null) {
                JOptionPane.showMessageDialog(frame, "Database connection failed. Please try again.");
                return false;
            }

            PreparedStatement stmt = conn.prepareStatement("SELECT id FROM users WHERE username = ? AND password = ?");
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                rs.close();
                stmt.close();
                conn.close();
                return true;
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid username or password.");
                rs.close();
                stmt.close();
                conn.close();
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Login failed due to a database error: " + ex.getMessage());
            return false;
        }
    }
}

