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

            // Writing subject marks
            Map<String, Integer> marks = student.getSubjectMarks();
            for (Map.Entry<String, Integer> entry : marks.entrySet()) {
                writer.println("  " + entry.getKey() + ": " + entry.getValue());
            }

            // Optionally, you can calculate the total, average, and percentage if required
            System.out.println("Report saved as text to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving report: " + e.getMessage());
        }
    }

}


