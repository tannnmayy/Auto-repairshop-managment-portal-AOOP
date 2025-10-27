import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // this is the Database credentials (how we connected the project to the database)
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/repairshop";

    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    // this is how we got the connection to the database
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database connection failed. Please check your settings.");
        }
        return connection;
    }
}
