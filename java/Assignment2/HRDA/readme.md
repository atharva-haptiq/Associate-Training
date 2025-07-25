# HRDA (Human Resource Data Application)

## Project Overview

HRDA is a console-based Human Resource Management system designed to manage employee and department data efficiently. This project demonstrates core concepts of Java programming, object-oriented design, and database interaction by implementing essential HR functionalities such as viewing employee data, filtering by salary, grouping by department, calculating average salaries, and adding new records.

The application provides a user-friendly menu-driven interface to perform various operations, making it an ideal project for showcasing practical skills in backend development and service-oriented architecture.

---

## Features & Functionalities

1. **View All Employees**  
   Displays detailed information for all employees stored in the database, including their name, department, salary, and years of experience.

2. **Filter Employees by Salary**  
   Lists employees earning more than ₹50,000, allowing quick identification of high earners.

3. **Group Employees by Department**  
   Enables users to input a department name and view all employees belonging to that department, facilitating easy grouping and department-wise analysis.

4. **Calculate Average Salary per Department**  
   Computes and displays the average salary for employees within a specified department, useful for payroll analysis.

5. **Sort Employees by Experience and Salary** *(Placeholder - to be implemented)*  
   Intended to sort employees based on experience and salary, helping identify senior and well-compensated employees.

6. **Add New Employee**  
   Allows users to add new employees by entering their name, department, salary, and experience, expanding the workforce data dynamically.

7. **Add New Department**  
   Supports addition of new departments, enhancing organizational structure flexibility.

8. **Exit**  
   Cleanly exits the application.

---

## Implementation Details and Flow

### 1. **Architecture and Design**

- The system follows a modular architecture:
    - **Model Layer:** Represents core entities `Employee` and `Department`.
    - **Service Layer:** Contains business logic and data processing (`EmployeeService`, `DepartmentService`).
    - **Repository/DB Layer:** Manages database connections and queries (`DBConnection`).
    - **Main Application Layer:** Provides a user interface via command line and integrates all services.

### 2. **Main Loop and User Interaction**

- The `Main` class runs an infinite loop presenting a menu of options.
- It reads user input to select operations.
- Depending on the choice, the app calls respective service methods to fetch or manipulate data.
- User input is validated, and invalid entries are handled gracefully.

### 3. **Employee and Department Management**

- The **EmployeeServiceImpl** handles CRUD operations for employees.
- The **DepartmentService** manages department-related data.
- The services leverage the **DBConnection** for database interactions, abstracting SQL and connection management.
- Stream APIs are used for filtering and processing collections efficiently.

### 4. **Key Operations**

- **Displaying Employees:**  
  Employee data is fetched via `employeeService.getAllEmployee()`. The results are printed in a formatted manner.

- **Filtering Employees by Salary:**  
  Uses Java Streams to filter employees with salary > 50,000 before printing.

- **Grouping by Department:**  
  Filters employees by the department entered by the user.

- **Average Salary Calculation:**  
  Delegates to `employeeService.averageSalaryOfDepartment(departmentName)` to compute averages.

- **Adding Records:**  
  User inputs are collected and passed to service methods (`addEmployee`, `addDepartment`) which handle database insertion and return success status.

---

## Technology Stack

- **Language:** Java 8+
- **Database:** Relational database (connected via `DBConnection`)
- **Libraries:** Java Standard Library, JDBC for database operations
- **Design Principles:** Object-oriented programming, Single Responsibility Principle, Modular design

---

