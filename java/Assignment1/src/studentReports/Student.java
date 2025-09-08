package Assignment1.src.studentReports;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a student with their personal details and academic performance.
 */
public class Student {

    /** The name of the student. */
    private String studentName;

    /** A map of subject names and the corresponding marks obtained by the student. */
    private Map<String, Integer> subjectMarks;

    /** The percentage score of the student across all subjects. */
    private double percentage;

    /** The average marks scored by the student. */
    private int averageMarks;

    /** Indicates whether the student has passed or failed. */
    private boolean passStatus;

    /**
     * Constructs a student with a given name and subject marks.
     *
     * @param studentName  the name of the student
     * @param subjectMarks the subject marks of the student
     */
    public Student(String studentName, Map<String, Integer> subjectMarks) {
        this.studentName = studentName;
        this.subjectMarks = subjectMarks;
    }

    /**
     * Constructs a student with no initial data.
     * Initializes an empty map for subject marks.
     */
    public Student() {
        this.subjectMarks = new HashMap<>();
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Map<String, Integer> getSubjectMarks() {
        return subjectMarks;
    }

    public void setSubjectMarks(Map<String, Integer> subjectMarks) {
        this.subjectMarks = subjectMarks;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public int getAverageMarks() {
        return averageMarks;
    }

    public void setAverageMarks(int averageMarks) {
        this.averageMarks = averageMarks;
    }

    public boolean isPassStatus() {
        return passStatus;
    }

    public void setPassStatus(boolean passStatus) {
        this.passStatus = passStatus;
    }
}
