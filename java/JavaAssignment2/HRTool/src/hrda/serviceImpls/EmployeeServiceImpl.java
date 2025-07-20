package hrda.serviceImpls;

import hrda.model.Department;
import hrda.model.Employee;
import hrda.repository.DBConnection;
import hrda.service.EmployeeService;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class EmployeeServiceImpl implements EmployeeService {

    Stream stream;

    private final DBConnection dbConnection;

    private final DepartmentService departmentService;

    public EmployeeServiceImpl(DBConnection dbConnection, DepartmentService departmentService) {
        this.dbConnection = dbConnection;
        this.departmentService = departmentService;
    }

    @Override
    public List<Employee> getAllEmployee() throws SQLException {
        List<Employee> employeeList = new ArrayList<>();

        String query = "SELECT * FROM Employee";

        try (Connection connection = dbConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String employeeName = resultSet.getString("employee_name");
                Long departmentId = resultSet.getObject("department_id", Long.class);
                String departmentName = null;
                if (departmentId != null && departmentService != null) {
                    departmentName = departmentService.getDepartmentName(departmentId);
                }

                Double salary = resultSet.getDouble("salary");
                Integer experienceYears = resultSet.getInt("experience_years");

                Department department = new Department(departmentId, departmentName);
                Employee employee = new Employee(id, employeeName, department, salary, experienceYears);
                employeeList.add(employee);
            }
        }
        //No catch clause because I have already handled exception in methods declaration


        return employeeList;
    }

    @Override
    public List<Employee> getEmployeesBySalary(Double salary) throws SQLException {
        List<Employee> employeeList = new ArrayList<>();
        String query = "SELECT * FROM Employee WHERE salary >= 50000";
        try(
                Connection connection = dbConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()){
            while (resultSet.next()){
                Long id  = resultSet.getLong("id");
                String employeeName = resultSet.getString("employee_name");
                Long departmentId = resultSet.getObject("department_id", Long.class);
                String departmentName = null;
                if(departmentId != null && departmentService != null){
                    departmentName = departmentService.getDepartmentName(departmentId);
                }
                Double employeeSalary = resultSet.getDouble("salary");
                Integer experienceInYears = resultSet.getInt("experience_years");
                Department department = new Department(departmentId, departmentName);
                Employee employee = new Employee(id, employeeName, department, salary, experienceInYears);
                employeeList.add(employee);
            }

        }
        //No catch clause because I have already handled exception in methods declaration

        return employeeList;
    }

    @Override
    public Double averageSalaryOfDepartment(String departmentName) throws SQLException{
        Double averageSalary = null;
        String query = "SELECT AVG(e.salary) AS average_salary " +
                "FROM Employee e " +
                "JOIN Department d ON e.department_id = d.id " +
                "WHERE d.department = ? " +
                "GROUP BY d.department";
        try (
                Connection connection = dbConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ){
            preparedStatement.setString(1, departmentName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                averageSalary = resultSet.getDouble("average_salary");
            }
        }

        return averageSalary;
    }

    @Override
    public List<Employee> getEmployeesByExpAndSalary(Integer expInYears, Double Salary) {
        return Collections.emptyList();
    }

    @Override
    public boolean addEmployee(String employeeName, String department,Double newSalary,Integer employeeExperience) {
        String sql = "INSERT INTO Employee (employee_name, department_id, salary, experience_years) VALUES (?, ?, ?, ?)";

        Department departmentByName = departmentService.findDepartmentByName(department);
        if(departmentByName == null) {
            departmentService.addDepartment(department);
            departmentByName = departmentService.findDepartmentByName(department);
        }
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, employeeName);
            statement.setObject(2, departmentByName.getId());
            statement.setDouble(3, newSalary);
            statement.setInt(4, employeeExperience);

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
