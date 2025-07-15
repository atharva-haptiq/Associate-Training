package studentReports;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class ReportUtil {
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

            System.out.println("Report saved as text to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving report: " + e.getMessage());
        }
    }

}


