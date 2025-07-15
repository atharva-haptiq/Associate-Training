import studentReports.Student;
import studentReports.StudentService;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        StudentService studentService = new StudentService();
        int choice = -1;

        while (choice != 4) {
            System.out.println("\n===== Student Report Menu =====");
            System.out.println("1. Add Student");
            System.out.println("2. Enter Marks");
            System.out.println("3. Calculate Result");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Enter Student's Name: ");
                    String studentName = scanner.nextLine();
                    String addedName = studentService.addStudent(studentName);
                    System.out.println("Student " + addedName + " added successfully!");
                    break;

                case 2:
                    System.out.println("Enter Student's Name to add marks:");
                    String nameForMarks = scanner.nextLine();
                    Student studentForMarks = studentService.getStudentByName(nameForMarks);

                    if (studentForMarks == null) {
                        System.out.println("Student not found!");
                        break;
                    }

                    Map<String, Integer> marks = new HashMap<>();
                    System.out.print("Enter Java marks: ");
                    marks.put("Java", scanner.nextInt());

                    System.out.print("Enter JavaScript marks: ");
                    marks.put("JavaScript", scanner.nextInt());

                    System.out.print("Enter SQL marks: ");
                    marks.put("SQL", scanner.nextInt());

                    System.out.print("Enter DevOps marks: ");
                    marks.put("DevOps", scanner.nextInt());

                    scanner.nextLine();
                    studentService.registerMarks(studentForMarks, marks);
                    System.out.println("Marks added successfully!");
                    break;

                case 3:
                    System.out.println("Enter Student's Name to calculate result:");
                    String nameForResult = scanner.nextLine();
                    Student studentForResult = studentService.getStudentByName(nameForResult);

                    if (studentForResult == null) {
                        System.out.println("Student not found!");
                        break;
                    }

                    studentService.calculateResult(studentForResult);
                    break;

                case 4:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}
