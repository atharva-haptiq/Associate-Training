package Assignment1.src;

import Assignment1.src.studentReports.ReportUtil;
import Assignment1.src.studentReports.Student;
import Assignment1.src.studentReports.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

/**
 * Entry point for the Student Report Management application.
 * <p>
 * Provides a console menu to:
 * <ul>
 *   <li>Add students</li>
 *   <li>Enter marks for subjects</li>
 *   <li>Calculate student results</li>
 *   <li>Save reports to text files</li>
 *   <li>Exit the application</li>
 * </ul>
 */
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    /**
     * Starts the student report management system.
     *
     * @param args command-line arguments (not used).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentService studentService = new StudentService();
        int choice = -1;

        while (choice != 4) {
            logger.info("\n-----------------------Student Report Menu-----------------------");
            logger.info("1. Add Student");
            logger.info("2. Enter Marks");
            logger.info("3. Calculate Result");
            logger.info("4. Exit");
            logger.info("5. Save Report");
            logger.info("Enter your choice: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                logger.error("Invalid input! Please enter a number (1–5).");
                scanner.nextLine();
                choice = -1;
            }

            switch (choice) {
                case 1 -> {
                    logger.info("Enter Student's Name: ");
                    String studentName = scanner.nextLine();
                    String addedName = studentService.addStudent(studentName);
                    logger.info("Student {} added successfully!", addedName);
                }

                case 2 -> {
                    logger.info("Enter Student's Name to add marks:");
                    String nameForMarks = scanner.nextLine();
                    Student studentForMarks = studentService.getStudentByName(nameForMarks);

                    if (studentForMarks == null) {
                        logger.warn("Student not found!");
                        break;
                    }

                    Map<String, Integer> marks = new HashMap<>();
                    logger.info("Enter Java marks: ");
                    marks.put("Java", scanner.nextInt());

                    logger.info("Enter JavaScript marks: ");
                    marks.put("JavaScript", scanner.nextInt());

                    logger.info("Enter SQL marks: ");
                    marks.put("SQL", scanner.nextInt());

                    logger.info("Enter DevOps marks: ");
                    marks.put("DevOps", scanner.nextInt());

                    scanner.nextLine();
                    studentService.registerMarks(studentForMarks, marks);
                    logger.info("Marks added successfully!");
                }

                case 3 -> {
                    logger.info("Enter Student's Name to calculate result:");
                    String nameForResult = scanner.nextLine();
                    Student studentForResult = studentService.getStudentByName(nameForResult);

                    if (studentForResult == null) {
                        logger.warn("Student not found!");
                        break;
                    }

                    studentService.calculateResult(studentForResult);
                }

                case 4 -> logger.info("Exiting...");

                case 5 -> {
                    logger.info("Enter filename to save as text (e.g., alice.txt): ");
                    String textFile = scanner.nextLine();

                    logger.info("Enter student name: ");
                    String studentNameForReport = scanner.nextLine();
                    Student studentForReport = studentService.getStudentByName(studentNameForReport);

                    if (studentForReport == null) {
                        logger.warn("Student not found!");
                        break;
                    }

                    ReportUtil.saveReportAsText(studentForReport, textFile);
                }

                default -> logger.warn("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}
