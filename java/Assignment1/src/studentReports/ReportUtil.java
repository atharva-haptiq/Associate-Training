package Assignment1.src.studentReports;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;


/**
 * Utility class for generating and saving student reports.
 */
public class ReportUtil {

    private static final Logger logger = LoggerFactory.getLogger(ReportUtil.class);

    /**
     * Saves a student's report as a plain text file.
     *
     * @param student  the student whose report is to be saved
     * @param filename the name of the file to save the report in
     */
    public static void saveReportAsText(Student student, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Student Name: " + student.getStudentName());
            writer.println("Marks:");

            Map<String, Integer> marks = student.getSubjectMarks();
            for (Map.Entry<String, Integer> entry : marks.entrySet()) {
                writer.println("  " + entry.getKey() + ": " + entry.getValue());
            }

            writer.println("Average Marks: " + student.getAverageMarks());
            writer.println("Percentage: " + student.getPercentage() + "%");
            writer.println("Result: " + (student.isPassStatus() ? "Pass" : "Fail"));

            logger.info("Report saved as text to {}", filename);
        } catch (IOException e) {
            logger.error("Error saving report: {}", e.getMessage());
        }
    }
}
