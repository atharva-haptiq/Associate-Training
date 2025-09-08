package Assignment2.hrda.src.hrda.model;

/**
 * Represents a Department entity.
 */
public class Department {

    private Long id;
    private String department;

    /**
     * Default constructor.
     */
    public Department() {
    }

    /**
     * Constructs a Department with the specified ID and name.
     *
     * @param id         the ID of the department
     * @param department the name of the department
     */
    public Department(Long id, String department) {
        this.id = id;
        this.department = department;
    }

    /**
     * Constructs a Department with the specified name.
     *
     * @param departmentName the name of the department
     */
    public Department(String departmentName) {
        this.department = departmentName;
    }

    /**
     * Returns the ID of the department.
     *
     * @return the department ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the department.
     *
     * @param id the department ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the name of the department.
     *
     * @return the department name
     */
    public String getDepartment() {
        return department;
    }

    /**
     * Sets the name of the department.
     *
     * @param department the department name
     */
    public void setDepartment(String department) {
        this.department = department;
    }
}
