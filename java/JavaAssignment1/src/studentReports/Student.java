package studentReports;

import java.util.HashMap;
import java.util.Map;

public class Student {

    private String studentName;
    private Map<String, Integer> subjectMarks;

    public Student(String studentName, Map<String, Integer> subjectMarks) {
        this.studentName = studentName;
        this.subjectMarks = subjectMarks;
    }

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
}
