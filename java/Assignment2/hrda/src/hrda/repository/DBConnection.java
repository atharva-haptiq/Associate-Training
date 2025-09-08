package Assignment2.hrda.src.hrda.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for managing database connections.
 */
public class DBConnection {

    private static final Logger LOGGER = Logger.getLogger(DBConnection.class.getName());

    private final String url = "jdbc:mysql://localhost:3306/hrda";
    private final String user = "atharva.haptiq";
    private final String password = "pass1234";

    /**
     * Establishes and returns a new connection to the database.
     *
     * @return a Connection object
     * @throws SQLException if a database access error occurs
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * Main method to test database connection.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        DBConnection db = new DBConnection();
        try (Connection connection = db.getConnection()) {
            LOGGER.info("Database connected successfully!");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error connecting to the database", e);
        }
    }
}
