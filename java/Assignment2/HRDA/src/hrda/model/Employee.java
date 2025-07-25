package hrda.model;

public class Employee {
    private Long id;
    private String employeeName;
    private Department department;
    private Double salary;
    private Integer experienceYears;

    public Employee() {
    }

    public Employee(String employeeName, String departmentName, Double newSalary, Integer employeeExperience) {
        this.employeeName = employeeName;
        this.department = new Department(departmentName); // assuming such a constructor exists
        this.salary = newSalary;
        this.experienceYears = employeeExperience;
    }

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

    public double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears;
    }
}
