package Assignment2.chatSimulator.src;

import generics.Message;
import entity.User;
import service.UserService;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserService<String> userService = new UserService<>();

        while (true) {

            System.out.println("==========================================");
            System.out.println("                 Chat Out                     ");
            System.out.println("==========================================");
            System.out.println("1. Join Chat");
            System.out.println("2. Send Message");
            System.out.println("3. View Messages");
            System.out.println("4. View Active Users");
            System.out.println("5. Exit");
            System.out.println("------------------------------------------");

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.print("Enter username to join chat: ");
                    String username = scanner.nextLine().trim();
                    userService.joinChat(username);
                    break;

                case "2":
                    System.out.print("Enter from's username: ");
                    String senderName = scanner.nextLine().trim();
                    System.out.print("Enter recipients username you want to send message: ");
                    String receiverName = scanner.nextLine().trim();
                    System.out.print("Enter message: ");
                    String content = scanner.nextLine().trim();

                    User sender = new User(senderName);
                    User receiver = new User(receiverName);

                    if (sender != null && receiver != null) {
                        Message<String> message = new Message<>(sender,content,receiver);
                        userService.sendMessage(message);
                        System.out.println("Message sent!");
                    } else {
                        System.out.println("Sender or receiver does not exist.");
                    }
                    break;

                case "3":
                    System.out.print("Enter your username to check messages: ");
                    String currentUser = scanner.nextLine();
                    System.out.print("Enter the recipients name you want to check message for: ");
                    String fromUser = scanner.nextLine();
                    List<Message<String>> messages = userService.getMessages(fromUser, currentUser);

                    if (messages == null || messages.isEmpty()) {
                        System.out.println("No new messages.");
                    } else {
                        System.out.println("Messages received:");
                        for (Message<String> msg : messages) {
                            System.out.println("From " + msg.getSender().getUsername() + ": " + "time: "+msg.getTimestamp()+" :" + msg.getContent());
                        }
                    }
                    break;

                case "4":
                    System.out.println("Active Users:");
                    for (User user : userService.getActiveUsers()) {
                        System.out.println("- " + user.getUsername());
                    }
                    break;

                case "5":
                    System.out.println("Exiting chat. Goodbye!");
                    return;

                default:
                    System.out.println("❗ Invalid choice. Try again.");
            }
        }
    }
}