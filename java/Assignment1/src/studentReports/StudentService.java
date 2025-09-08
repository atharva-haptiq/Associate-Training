package Assignment1.src.studentReports;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Service class to manage student records and their results.
 */
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    /** Maximum possible marks across all subjects. */
    private static final int TOTAL_MARKS = 400;

    /** List of all registered students. */
    private final List<Student> students = new ArrayList<>();

    /**
     * Adds a new student to the system.
     *
     * @param name the name of the student
     * @return the name of the added student
     */
    public String addStudent(String name) {
        if (name == null || name.trim().isEmpty()) {
            logger.warn("Name cannot be null or empty.");
            return null;
        }
        Student student = new Student();
        student.setStudentName(name);
        students.add(student);
        logger.info("Student {} added successfully.", name);
        return student.getStudentName();
    }

    /**
     * Retrieves a student by name (case-insensitive).
     *
     * @param name the name of the student
     * @return the student object, or {@code null} if not found
     */
    public Student getStudentByName(String name) {
        for (Student student : students) {
            if (student.getStudentName().equalsIgnoreCase(name)) {
                return student;
            }
        }
        return null;
    }

    /**
     * Registers marks for a student, validating that each mark is within range (0–100).
     *
     * @param student the student for whom marks are registered
     * @param marks   a map of subject and marks
     * @return the updated subject marks map
     */
    public Map<String, Integer> registerMarks(Student student, Map<String, Integer> marks) {
        for (Map.Entry<String, Integer> mark : marks.entrySet()) {
            if (mark.getValue() >= 0 && mark.getValue() <= 100) {
                student.getSubjectMarks().put(mark.getKey(), mark.getValue());
            } else {
                logger.error("Invalid marks entered for {}: {}", mark.getKey(), mark.getValue());
            }
        }
        logger.info("Marks registered for student: {}", student.getStudentName());
        return student.getSubjectMarks();
    }

    /**
     * Calculates the result for a given student, including total marks, percentage,
     * pass/fail status, and average marks.
     *
     * @param student the student whose result is calculated
     */
    public void calculateResult(Student student) {
        Map<String, Integer> marks = student.getSubjectMarks();

        int obtainedMarks = 0;
        for (Map.Entry<String, Integer> mark : marks.entrySet()) {
            obtainedMarks += mark.getValue();
        }

        logger.info("----------------------------------------");
        logger.info("Total obtained marks: {}", obtainedMarks);
        logger.info("----------------------------------------");

        double studentPercentage = ((double) obtainedMarks / TOTAL_MARKS) * 100;
        logger.info("Percentage of {} is {}%", student.getStudentName(), studentPercentage);
        logger.info("----------------------------------------");

        if (studentPercentage >= 35) {
            student.setPassStatus(true);
            logger.info("{} has passed with {}%", student.getStudentName(), studentPercentage);
        } else {
            student.setPassStatus(false);
            logger.info("{} has failed with {}%", student.getStudentName(), studentPercentage);
        }
        logger.info("----------------------------------------");

        int studentsAverage = obtainedMarks / marks.size();
        logger.info("{}'s average marks is {}", student.getStudentName(), studentsAverage);
        logger.info("----------------------------------------");

        // Save results into the student object
        student.setPercentage(studentPercentage);
        student.setAverageMarks(studentsAverage);
    }
}
