package Assignment2.chatSimulator.src;

import Assignment2.chatSimulator.src.entity.User;
import Assignment2.chatSimulator.src.generics.Message;
import Assignment2.chatSimulator.src.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;

/**
 * Entry point for the Chat Simulator application.
 * <p>
 * Provides a console-based interface for users to:
 * <ul>
 *     <li>Join chat</li>
 *     <li>Send messages</li>
 *     <li>View messages</li>
 *     <li>View active users</li>
 *     <li>Exit chat</li>
 * </ul>
 */
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    /**
     * Starts the chat simulator console application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserService<String> userService = new UserService<>();

        while (true) {
            logger.info("==========================================");
            logger.info("                 Chat Out                 ");
            logger.info("==========================================");
            logger.info("1. Join Chat");
            logger.info("2. Send Message");
            logger.info("3. View Messages");
            logger.info("4. View Active Users");
            logger.info("5. Exit");
            logger.info("------------------------------------------");
            logger.info("Enter your choice: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> {
                    logger.info("Enter username to join chat: ");
                    String username = scanner.nextLine().trim();
                    userService.joinChat(username);
                    logger.info("User {} joined the chat.", username);
                }

                case "2" -> {
                    logger.info("Enter sender's username: ");
                    String senderName = scanner.nextLine().trim();
                    logger.info("Enter recipient's username: ");
                    String receiverName = scanner.nextLine().trim();
                    logger.info("Enter message: ");
                    String content = scanner.nextLine().trim();

                    User sender = new User(senderName);
                    User receiver = new User(receiverName);

                    if (sender != null && receiver != null) {
                        Message<String> message = new Message<>(sender, content, receiver);
                        userService.sendMessage(message);
                        logger.info("Message sent from {} to {}.", senderName, receiverName);
                    } else {
                        logger.warn("Sender or receiver does not exist.");
                    }
                }

                case "3" -> {
                    logger.info("Enter your username to check messages: ");
                    String currentUser = scanner.nextLine();
                    logger.info("Enter the sender's username whose messages you want to check: ");
                    String fromUser = scanner.nextLine();
                    List<Message<String>> messages = userService.getMessages(fromUser, currentUser);

                    if (messages == null || messages.isEmpty()) {
                        logger.info("No new messages for user {}", currentUser);
                    } else {
                        logger.info("Messages received for {}:", currentUser);
                        for (Message<String> msg : messages) {
                            logger.info("From {} at {}: {}",
                                    msg.getSender().getUsername(),
                                    msg.getTimestamp(),
                                    msg.getContent());
                        }
                    }
                }

                case "4" -> {
                    logger.info("Active Users:");
                    for (User user : userService.getActiveUsers()) {
                        logger.info("- {}", user.getUsername());
                    }
                }

                case "5" -> {
                    logger.info("Exiting chat. Goodbye!");
                    return;
                }

                default -> logger.warn("❗ Invalid choice. Try again.");
            }
        }
    }
}
