package hrda.service;

import hrda.model.Employee;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployee() throws SQLException;
    List<Employee> getEmployeesBySalary(Double salary) throws SQLException;
    Double averageSalaryOfDepartment(String departmentName) throws SQLException;
    List<Employee> getEmployeesByExpAndSalary(Integer expInYears, Double Salary);
    boolean addEmployee(String employeeName, String department,Double newSalary,Integer employeeExperience);
}
