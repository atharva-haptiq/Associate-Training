package Assignment2.hrda.src.hrda.service;


import Assignment2.hrda.src.hrda.model.Employee;

import java.sql.SQLException;
import java.util.List;

/**
 * Service interface for handling Employee-related operations.
 */
public interface EmployeeService {

    /**
     * Retrieves all employees from the database.
     *
     * @return a list of all employees
     * @throws SQLException if a database access error occurs
     */
    List<Employee> getAllEmployee() throws SQLException;

    /**
     * Retrieves employees whose salary matches the specified value.
     *
     * @param salary the salary to filter employees by
     * @return a list of employees with the specified salary
     * @throws SQLException if a database access error occurs
     */
    List<Employee> getEmployeesBySalary(Double salary) throws SQLException;

    /**
     * Calculates the average salary of employees in the given department.
     *
     * @param departmentName the name of the department
     * @return the average salary of the department's employees
     * @throws SQLException if a database access error occurs
     */
    Double averageSalaryOfDepartment(String departmentName) throws SQLException;

    /**
     * Retrieves employees based on experience and salary criteria.
     *
     * @param expInYears the minimum years of experience
     * @param salary     the salary threshold
     * @return a list of employees matching the experience and salary criteria
     */
    List<Employee> getEmployeesByExpAndSalary(Integer expInYears, Double salary);

    /**
     * Adds a new employee to the database.
     *
     * @param employeeName       the name of the employee
     * @param department         the department name
     * @param newSalary          the salary of the employee
     * @param employeeExperience the experience of the employee in years
     * @return true if the employee was added successfully, false otherwise
     */
    boolean addEmployee(String employeeName, String department, Double newSalary, Integer employeeExperience);
}
