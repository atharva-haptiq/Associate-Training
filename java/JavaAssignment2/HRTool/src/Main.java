import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        while (true) {
            printMenu();

            System.out.print("Enter your choice: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        System.out.println("Showing all employees...");

                        break;
                    case 2:
                        System.out.println("Showing employees earning more than ₹50,000...");
                        break;
                    case 3:
                        System.out.println("Grouping employees by department...");
                        break;
                    case 4:
                        System.out.println("Calculating average salary per department...");
                        break;
                    case 5:
                        System.out.println("Sorting employees by experience and salary...");
                        break;
                    case 6:
                        System.out.println("Exiting...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }

            } else {
                System.out.println("Please enter a valid number.");
                scanner.nextLine(); // Clear invalid input
            }

            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }

    private static void printMenu() {
        System.out.println("==========================================");
        System.out.println("                 HRDA                     ");
        System.out.println("==========================================");
        System.out.println("1. Show all employees");
        System.out.println("2. Show employees earning more than ₹50,000");
        System.out.println("3. Group employees by department");
        System.out.println("4. Show average salary per department");
        System.out.println("5. Sort employees by experience and salary");
        System.out.println("6. Exit");
        System.out.println("------------------------------------------");
    }
}
