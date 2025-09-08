package Assignment2.hrda.src.hrda.model;

/**
 * Represents an Employee entity.
 */
public class Employee {

    private Long id;
    private String employeeName;
    private Department department;
    private Double salary;
    private Integer experienceYears;

    /**
     * Default constructor.
     */
    public Employee() {
    }

    /**
     * Constructs an Employee with the specified details.
     *
     * @param employeeName      the name of the employee
     * @param departmentName    the name of the department
     * @param newSalary         the salary of the employee
     * @param employeeExperience the experience of the employee in years
     */
    public Employee(String employeeName, String departmentName, Double newSalary, Integer employeeExperience) {
        this.employeeName = employeeName;
        this.department = new Department(departmentName);
        this.salary = newSalary;
        this.experienceYears = employeeExperience;
    }

    /**
     * Constructs an Employee with the specified details including ID and Department object.
     *
     * @param id               the ID of the employee
     * @param employeeName     the name of the employee
     * @param department       the Department object of the employee
     * @param salary           the salary of the employee
     * @param experienceYears  the experience of the employee in years
     */
    public Employee(Long id, String employeeName, Department department, Double salary, Integer experienceYears) {
        this.id = id;
        this.employeeName = employeeName;
        this.department = department;
        this.salary = salary;
        this.experienceYears = experienceYears;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Integer getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears;
    }
}
