import hrda.model.Department;
import hrda.model.Employee;
import hrda.repository.DBConnection;
import hrda.service.EmployeeService;
import hrda.serviceImpls.DepartmentService;
import hrda.serviceImpls.EmployeeServiceImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int choice;

        DBConnection dbConnection = new DBConnection();
        DepartmentService departmentService = new DepartmentService(dbConnection);
        EmployeeService employeeService = new EmployeeServiceImpl(dbConnection,departmentService);

        while (true) {
            printMenu();

            System.out.print("Enter your choice: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
                List<Employee> employeeList;
                String departmentName;
                switch (choice) {
                    case 1:
                        System.out.println("Showing all employees...");
                        employeeList = employeeService.getAllEmployee();
                        employeeList
                                .stream()
                                .forEach(employee -> {
                                    System.out.println("------------Employee's Info------------------");
                                    System.out.println("Employee name: "+employee.getEmployeeName());
                                    System.out.println("Employee's department: "+employee.getDepartment().getDepartment());
                                    System.out.println("Employee's salary: "+employee.getSalary());
                                    System.out.println("Employee's experience in years: "+employee.getExperienceYears());
                                    System.out.println("-------------------------------");
                                });
                        break;
                    case 2:
                        System.out.println("Showing employees earning more than ₹50,000...");
                        employeeList = employeeService.getAllEmployee();
                        employeeList
                                .stream()
                                .filter(employee -> employee.getSalary() > 50000)
                                .forEach(employee -> {
                                    System.out.println("------------Employee's Info------------------");
                                    System.out.println("Employee name: "+employee.getEmployeeName());
                                    System.out.println("Employee's department: "+employee.getDepartment().getDepartment());
                                    System.out.println("Employee's salary: "+employee.getSalary());
                                    System.out.println("Employee's experience in years: "+employee.getExperienceYears());
                                    System.out.println("-------------------------------");
                                });

                        break;
                    case 3:
                        System.out.println("Grouping employees by department...");
                        System.out.println("Enter the department name you want to group accordingly");
                        departmentName = scanner.nextLine();
                        employeeList = employeeService.getAllEmployee();
                        String finalDepartmentName = departmentName;
                        employeeList
                                .stream()
                                .filter(employee -> employee.getDepartment().getDepartment().equals(finalDepartmentName))
                                .forEach(employee -> {
                                    System.out.println("------------Employee's Info------------------");
                                    System.out.println("Employee name: "+employee.getEmployeeName());
                                    System.out.println("Employee's department: "+employee.getDepartment().getDepartment());
                                    System.out.println("Employee's salary: "+employee.getSalary());
                                    System.out.println("Employee's experience in years: "+employee.getExperienceYears());
                                    System.out.println("-------------------------------");
                                });
                        break;
                    case 4:
                        System.out.println("Calculating average salary per department...");
                        System.out.println("Enter department name: ");
                        departmentName = scanner.nextLine();
                        Double averageSalary = employeeService.averageSalaryOfDepartment(departmentName);
                        System.out.println(" ");
                        System.out.println("Average Salary of "+departmentName+" department is: "+averageSalary);
                        break;
                    case 5:
                        System.out.println("Sorting employees by experience and salary...");
                        System.out.println("Enter the minimum experience of employee: ");
                        Integer minExp = scanner.nextInt();
                        System.out.println("Enter a minimum salary you want to sort for:");
                        Double minSalary = scanner.nextDouble();
                        employeeList = employeeService.getAllEmployee();
                        employeeList
                                .stream()
                                .filter(employee -> employee.getSalary() >= minSalary && employee.getExperienceYears()>= minExp )
                                .forEach(employee -> {
                                    System.out.println("------------Employee's Info------------------");
                                    System.out.println("Employee name: "+employee.getEmployeeName());
                                    System.out.println("Employee's department: "+employee.getDepartment().getDepartment());
                                    System.out.println("Employee's salary: "+employee.getSalary());
                                    System.out.println("Employee's experience in years: "+employee.getExperienceYears());
                                    System.out.println("-------------------------------");
                                });
                        break;
                    case 6:
                        System.out.println("Adding an empoloyee...");
                        System.out.println("Enter an employee name you want to add");
                        String employeeName = scanner.nextLine();
                        System.out.println("Enter employee's department");
                        departmentName = scanner.nextLine();
                        System.out.println("Enter employee's salary");
                        Double employeeSalary = scanner.nextDouble();
                        System.out.println("Enter Employee's Experience in years");
                        Integer experience = scanner.nextInt();

                        if(employeeService.addEmployee(employeeName, departmentName, employeeSalary, experience)) System.out.println("Employee has been added successfully!");
                        else System.out.println("Employee failed to add");
                        break;
                    case 7:
                        System.out.println("Adding deprtment...");
                        System.out.println("Enter the department's name you want to add");
                        departmentName = scanner.nextLine();
                        if(departmentService.addDepartment(departmentName)) System.out.println("Department has been added successfully!");
                        else System.out.println("Department failed to add");
                        break;
                    case 8:
                        System.out.println("Exiting...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }

            } else {
                System.out.println("Please enter a valid number.");
                scanner.nextLine();
            }

            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }

    private static void printMenu() {
        System.out.println("==========================================");
        System.out.println("                 HRDA                     ");
        System.out.println("==========================================");
        System.out.println("1. Show all employees");
        System.out.println("2. Show employees earning more than ₹50,000");
        System.out.println("3. Group employees by department");
        System.out.println("4. Show average salary per department");
        System.out.println("5. Sort employees by experience and salary");
        System.out.println("6. Add an Employee");
        System.out.println("7. Add a Department");
        System.out.println("8. Exit");
        System.out.println("------------------------------------------");
    }


}