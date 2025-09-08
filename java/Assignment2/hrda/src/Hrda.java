package Assignment2.hrda.src;

import Assignment2.hrda.src.hrda.model.Employee;
import Assignment2.hrda.src.hrda.repository.DBConnection;
import Assignment2.hrda.src.hrda.service.EmployeeService;
import Assignment2.hrda.src.hrda.serviceImpls.DepartmentService;
import Assignment2.hrda.src.hrda.serviceImpls.EmployeeServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * Entry point for the HRDA (Human Resource Data Application).
 * Provides a menu-driven interface for managing employees and departments.
 */
public class Hrda {

    private static final Logger log = LoggerFactory.getLogger(Hrda.class);

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int choice;

        DBConnection dbConnection = new DBConnection();
        DepartmentService departmentService = new DepartmentService(dbConnection);
        EmployeeService employeeService = new EmployeeServiceImpl(dbConnection, departmentService);

        while (true) {
            printMenu();

            log.info("Enter your choice: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> {
                        log.info("Showing all employees...");
                        List<Employee> employees = employeeService.getAllEmployee();
                        employees.forEach(Hrda::printEmployeeInfo);
                    }
                    case 2 -> {
                        log.info("Showing employees earning more than ₹50,000...");
                        employeeService.getAllEmployee()
                                .stream()
                                .filter(emp -> emp.getSalary() > 50000)
                                .forEach(Hrda::printEmployeeInfo);
                    }
                    case 3 -> {
                        log.info("Grouping employees by department...");
                        log.info("Enter the department name:");
                        String departmentName = scanner.nextLine();
                        employeeService.getAllEmployee()
                                .stream()
                                .filter(emp -> emp.getDepartment().getDepartment().equalsIgnoreCase(departmentName))
                                .forEach(Hrda::printEmployeeInfo);
                    }
                    case 4 -> {
                        log.info("Calculating average salary per department...");
                        log.info("Enter department name: ");
                        String departmentName = scanner.nextLine();
                        Double avgSalary = employeeService.averageSalaryOfDepartment(departmentName);
                        log.info("Average salary of {} department is: {}", departmentName, avgSalary);
                    }
                    case 5 -> {
                        log.info("Sorting employees by experience and salary...");
                        log.info("Enter the minimum experience: ");
                        int minExp = scanner.nextInt();
                        log.info("Enter the minimum salary: ");
                        double minSalary = scanner.nextDouble();
                        scanner.nextLine();

                        employeeService.getAllEmployee()
                                .stream()
                                .filter(emp -> emp.getSalary() >= minSalary && emp.getExperienceYears() >= minExp)
                                .forEach(Hrda::printEmployeeInfo);
                    }
                    case 6 -> {
                        log.info("Adding a new employee...");
                        log.info("Enter employee name: ");
                        String empName = scanner.nextLine();
                        log.info("Enter department: ");
                        String deptName = scanner.nextLine();
                        log.info("Enter salary: ");
                        double salary = scanner.nextDouble();
                        log.info("Enter experience (years): ");
                        int exp = scanner.nextInt();
                        scanner.nextLine();

                        if (employeeService.addEmployee(empName, deptName, salary, exp)) {
                            log.info("✅ Employee added successfully!");
                        } else {
                            log.warn("❌ Failed to add employee.");
                        }
                    }
                    case 7 -> {
                        log.info("Adding a new department...");
                        log.info("Enter department name: ");
                        String deptName = scanner.nextLine();
                        if (departmentService.addDepartment(deptName)) {
                            log.info("✅ Department added successfully!");
                        } else {
                            log.warn("❌ Failed to add department.");
                        }
                    }
                    case 8 -> {
                        log.info("Exiting application...");
                        System.exit(0);
                    }
                    default -> log.warn("Invalid choice. Please try again.");
                }

            } else {
                log.warn("Invalid input. Please enter a number.");
                scanner.nextLine();
            }

            log.info("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }

    /**
     * Prints detailed information about an employee.
     *
     * @param employee the employee whose details are to be printed
     */
    private static void printEmployeeInfo(Employee employee) {
        log.info("------------ Employee Info ------------------");
        log.info("Name: {}", employee.getEmployeeName());
        log.info("Department: {}", employee.getDepartment().getDepartment());
        log.info("Salary: {}", employee.getSalary());
        log.info("Experience (years): {}", employee.getExperienceYears());
        log.info("---------------------------------------------");
    }

    /**
     * Displays the main application menu.
     */
    private static void printMenu() {
        log.info("==========================================");
        log.info("                 HRDA                     ");
        log.info("==========================================");
        log.info("1. Show all employees");
        log.info("2. Show employees earning more than ₹50,000");
        log.info("3. Group employees by department");
        log.info("4. Show average salary per department");
        log.info("5. Sort employees by experience and salary");
        log.info("6. Add an Employee");
        log.info("7. Add a Department");
        log.info("8. Exit");
        log.info("------------------------------------------");
    }
}
