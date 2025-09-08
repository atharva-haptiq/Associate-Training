package Assignment2.hrda.src.hrda.serviceImpls;

import Assignment2.hrda.src.hrda.model.Department;
import Assignment2.hrda.src.hrda.model.Employee;
import Assignment2.hrda.src.hrda.repository.DBConnection;
import Assignment2.hrda.src.hrda.service.EmployeeService;
import Assignment2.hrda.src.hrda.serviceImpls.DepartmentService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of the {@link EmployeeService} interface.
 * <p>
 * This class provides business logic for employee-related operations such as:
 * - Fetching employees
 * - Filtering employees
 * - Calculating department salary averages
 * - Adding employees
 * </p>
 * <p>
 * It interacts with the database via {@link DBConnection} and also depends on
 * {@link DepartmentService} for department-related lookups.
 * </p>
 */
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final DBConnection dbConnection;
    private final DepartmentService departmentService;

    /**
     * Constructs an EmployeeServiceImpl with database connection and department service.
     *
     * @param dbConnection     the database connection provider
     * @param departmentService the service for department-related operations
     */
    public EmployeeServiceImpl(DBConnection dbConnection, DepartmentService departmentService) {
        this.dbConnection = dbConnection;
        this.departmentService = departmentService;
    }

    /**
     * Retrieves all employees from the database.
     *
     * @return a list of all employees
     * @throws SQLException if any SQL error occurs
     */
    @Override
    public List<Employee> getAllEmployee() throws SQLException {
        List<Employee> employeeList = new ArrayList<>();
        String query = "SELECT * FROM Employee";

        log.debug("Executing query: {}", query);

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

            log.info("Fetched {} employees from database.", employeeList.size());
        }

        return employeeList;
    }

    /**
     * Retrieves employees who earn at least the given salary.
     *
     * @param salary minimum salary threshold
     * @return list of employees matching the salary criteria
     * @throws SQLException if any SQL error occurs
     */
    @Override
    public List<Employee> getEmployeesBySalary(Double salary) throws SQLException {
        List<Employee> employeeList = new ArrayList<>();
        String query = "SELECT * FROM Employee WHERE salary >= ?";

        log.debug("Executing query: {} with minSalary={}", query, salary);

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setDouble(1, salary);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String employeeName = resultSet.getString("employee_name");
                Long departmentId = resultSet.getObject("department_id", Long.class);

                String departmentName = null;
                if (departmentId != null && departmentService != null) {
                    departmentName = departmentService.getDepartmentName(departmentId);
                }

                Double employeeSalary = resultSet.getDouble("salary");
                Integer experienceInYears = resultSet.getInt("experience_years");

                Department department = new Department(departmentId, departmentName);
                Employee employee = new Employee(id, employeeName, department, employeeSalary, experienceInYears);
                employeeList.add(employee);
            }

            log.info("Found {} employees with salary >= {}", employeeList.size(), salary);
        }

        return employeeList;
    }

    /**
     * Calculates the average salary for a given department.
     *
     * @param departmentName the department name
     * @return average salary, or {@code null} if no employees are found
     * @throws SQLException if any SQL error occurs
     */
    @Override
    public Double averageSalaryOfDepartment(String departmentName) throws SQLException {
        Double averageSalary = null;
        String query = "SELECT AVG(e.salary) AS average_salary " +
                "FROM Employee e " +
                "JOIN Department d ON e.department_id = d.id " +
                "WHERE d.department = ? " +
                "GROUP BY d.department";

        log.debug("Executing query: {} with department={}", query, departmentName);

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, departmentName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                averageSalary = resultSet.getDouble("average_salary");
            }

            log.info("Average salary for department '{}' is {}", departmentName, averageSalary);
        }

        return averageSalary;
    }

    /**
     * Retrieves employees by minimum experience and salary.
     * <p>
     * ⚠ Currently not implemented — returns an empty list.
     * </p>
     *
     * @param expInYears minimum experience in years
     * @param salary     minimum salary
     * @return list of employees matching criteria (currently empty)
     */
    @Override
    public List<Employee> getEmployeesByExpAndSalary(Integer expInYears, Double salary) {
        log.warn("Method getEmployeesByExpAndSalary is not yet implemented. Returning empty list.");
        return Collections.emptyList();
    }

    /**
     * Adds a new employee to the database.
     * <p>
     * If the department does not exist, it will be created automatically.
     * </p>
     *
     * @param employeeName      employee's name
     * @param department        employee's department
     * @param newSalary         employee's salary
     * @param employeeExperience employee's years of experience
     * @return true if the employee was successfully added, false otherwise
     */
    @Override
    public boolean addEmployee(String employeeName, String department, Double newSalary, Integer employeeExperience) {
        String sql = "INSERT INTO Employee (employee_name, department_id, salary, experience_years) VALUES (?, ?, ?, ?)";

        log.debug("Preparing to insert employee: name={}, department={}, salary={}, exp={}",
                employeeName, department, newSalary, employeeExperience);

        Department departmentByName = departmentService.findDepartmentByName(department);

        if (departmentByName == null) {
            log.info("Department '{}' not found. Creating new department...", department);
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

            if (rowsInserted > 0) {
                log.info("✅ Employee '{}' added successfully to department '{}'.", employeeName, department);
                return true;
            } else {
                log.warn("❌ Failed to add employee '{}'.", employeeName);
                return false;
            }

        } catch (SQLException e) {
            log.error("Error while adding employee '{}': {}", employeeName, e.getMessage(), e);
            return false;
        }
    }
}
