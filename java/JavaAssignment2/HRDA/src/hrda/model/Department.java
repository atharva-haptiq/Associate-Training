package hrda.model;

public class Department {

    private Long id;
    private String department;

    public Department() {
    }

    public Department(Long id, String department) {
        this.id = id;
        this.department = department;
    }

    public Department(String departmentName) {
        this.department = departmentName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
