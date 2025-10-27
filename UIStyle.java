import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class UIStyle {
    public static final Color BG_DARK = new Color(18, 18, 18);
    public static final Color FIELD_BG = new Color(28, 28, 28);
    public static final Color TEXT_LIGHT = new Color(230, 230, 230);
    public static final Color BORDER_DARK = new Color(60, 60, 60);
    public static final Color PRIMARY_BG = Color.BLACK;
    public static final Color DANGER_BG = new Color(190, 30, 45);

    public static void styleRoot(JComponent comp) {
        comp.setBackground(BG_DARK);
    }

    public static void styleLabel(JLabel label) {
        label.setForeground(Color.WHITE);
        label.setFont(new Font("SansSerif", Font.PLAIN, 13));
    }

    public static void styleTitle(JLabel label) {
        label.setForeground(Color.WHITE);
        label.setFont(new Font("SansSerif", Font.BOLD, 24));
    }

    public static void styleField(JTextField field) {
        field.setBackground(FIELD_BG);
        field.setForeground(TEXT_LIGHT);
        field.setCaretColor(TEXT_LIGHT);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_DARK),
                BorderFactory.createEmptyBorder(4, 6, 4, 6)));
        field.setFont(new Font("SansSerif", Font.PLAIN, 13));
    }

    public static void stylePrimaryButton(JButton button) {
        button.setBackground(PRIMARY_BG);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(80, 80, 80)),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public static void styleDangerButton(JButton button) {
        button.setBackground(DANGER_BG);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(150, 20, 35)),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public static void styleTable(JTable table) {
        table.setBackground(FIELD_BG);
        table.setForeground(TEXT_LIGHT);
        table.setGridColor(BORDER_DARK);
        table.setSelectionBackground(new Color(45, 45, 45));
        table.setSelectionForeground(Color.WHITE);
        JTableHeader header = table.getTableHeader();
        header.setBackground(PRIMARY_BG);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("SansSerif", Font.BOLD, 13));
    }
}



