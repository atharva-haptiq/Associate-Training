package studentReports;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class ReportUtil {

    public static void saveReportAsText(List<Student> students, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Student student : students) {
                writer.println("Student Name: " + student.getStudentName());
                writer.println("Marks:");
                int total = 0;
                for (Map.Entry<String, Integer> entry : student.getSubjectMarks().entrySet()) {
                    writer.println("  " + entry.getKey() + ": " + entry.getValue());
                    total += entry.getValue();
                }

                int subjects = student.getSubjectMarks().size();
                double percentage = subjects == 0 ? 0 : (double) total / (subjects * 100) * 100;
                int average = subjects == 0 ? 0 : total / subjects;

                writer.println("Total Marks: " + total);
                writer.println("Average: " + average);
                writer.println("Percentage: " + percentage + "%");
                writer.println("Result: " + (percentage >= 35 ? "PASS" : "FAIL"));
                writer.println("-------------------------------------");
            }

            System.out.println("Report saved as text to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving report: " + e.getMessage());
        }
    }
}


