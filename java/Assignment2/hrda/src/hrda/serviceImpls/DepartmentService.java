package Assignment2.hrda.src.hrda.serviceImpls;

import Assignment2.hrda.src.hrda.model.Department;
import Assignment2.hrda.src.hrda.repository.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service class for handling Department-related database operations.
 */
public class DepartmentService {

    private static final Logger LOGGER = Logger.getLogger(DepartmentService.class.getName());
    private final DBConnection dbConnection;

    /**
     * Constructs a DepartmentService with the specified DBConnection.
     *
     * @param dbConnection the database connection handler
     */
    public DepartmentService(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    /**
     * Retrieves the name of a department by its ID.
     *
     * @param departmentId the ID of the department
     * @return the name of the department, or null if not found
     * @throws SQLException if a database access error occurs
     */
    public String getDepartmentName(Long departmentId) throws SQLException {
        String departmentName = null;
        String query = "SELECT department FROM Department WHERE id = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, departmentId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    departmentName = resultSet.getString("department");
                    LOGGER.log(Level.INFO, "Retrieved department name: {0}", departmentName);
                }
            }
        }

        return departmentName;
    }

    /**
     * Finds a department by its name.
     *
     * @param departmentName the name of the department
     * @return the Department object if found, or null if not found
     */
    public Department findDepartmentByName(String departmentName) {
        String sql = "SELECT id, department FROM Department WHERE department = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, departmentName);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Long id = rs.getLong("id");
                    String name = rs.getString("department");
                    return new Department(id, name);
                }
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding department by name", e);
        }

        return null;
    }

    /**
     * Adds a new department to the database.
     *
     * @param departmentName the name of the department to add
     * @return true if the department was added successfully, false otherwise
     */
    public boolean addDepartment(String departmentName) {
        String sql = "INSERT INTO Department (department) VALUES (?)";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, departmentName);

            int rowsInserted = statement.executeUpdate();
            boolean success = rowsInserted > 0;
            LOGGER.log(Level.INFO, "Department '{0}' added: {1}", new Object[]{departmentName, success});
            return success;

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding department", e);
            return false;
        }
    }
}
