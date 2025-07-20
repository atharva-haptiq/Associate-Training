package hrda.serviceImpls;

import hrda.model.Department;
import hrda.model.Employee;
import hrda.repository.DBConnection;

import java.sql.*;
import java.util.List;

public class DepartmentService {

    Department department = new Department();
    private final DBConnection dbConnection;

    public DepartmentService(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public String getDepartmentName(Long departmentId) throws SQLException {
        String departmentName = null;
        String query = "SELECT * FROM Department WHERE id = ?";
        Connection connection = dbConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, departmentId);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
//            Long id = resultSet.getLong("id");
            departmentName = resultSet.getString("department");
//            System.out.println("----------------------");
//            System.out.println("Id: "+id);
//            System.out.println("Department: "+departmentName);
        }
        return departmentName;
    }

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
            e.printStackTrace();
        }

        return null;
    }

    public boolean addDepartment(String departmentName) {
        String sql = "INSERT INTO Department (department) VALUES (?)";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, departmentName);

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }




}
