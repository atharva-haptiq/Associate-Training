package Assignment2.chatSimulator.src.service;

import Assignment2.chatSimulator.src.entity.User;
import Assignment2.chatSimulator.src.generics.Message;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service layer to manage users and message history for the chat system.
 *
 * @param <T> the type of message content (e.g., String, Image, File, etc.)
 */
public class UserService<T> {

    /** Active users in the chat (sorted by username). */
    private final Set<User> userSet = new TreeSet<>();

    /** Stores message history for each user. */
    private final Map<User, List<Message<T>>> messageHistory = new HashMap<>();

    /**
     * Adds a user to the chat system.
     *
     * @param username the username of the user joining the chat
     */
    public void joinChat(String username) {
        if (username == null || username.isBlank()) {
            System.out.println("❌ Username cannot be empty.");
            return;
        }
        User user = new User(username);
        userSet.add(user);
        System.out.println("✅ " + username.toUpperCase() + " has joined the chat.");
    }

    /**
     * Sends a message from one user to another and stores it in history.
     *
     * @param message the message to send
     */
    public void sendMessage(Message<T> message) {
        if (message == null || message.getSender() == null || message.getReceiver() == null) {
            System.out.println("❌ Invalid message or user.");
            return;
        }
        messageHistory.computeIfAbsent(message.getSender(), k -> new ArrayList<>()).add(message);
    }

    /**
     * Retrieves all messages sent from one user to another.
     *
     * @param from the sender's username
     * @param to   the receiver's username
     * @return a list of messages, or an empty list if none exist
     */
    public List<Message<T>> getMessages(String from, String to) {
        if (from == null || to == null) return Collections.emptyList();

        List<Message<T>> pendingMessages = messageHistory.values().stream()
                .flatMap(List::stream)
                .filter(message -> message.getReceiver().getUsername().equalsIgnoreCase(to)
                        && message.getSender().getUsername().equalsIgnoreCase(from))
                .collect(Collectors.toList());

        return pendingMessages;
    }

    /**
     * Gets all active users in the chat.
     *
     * @return a set of active users
     */
    public Set<User> getActiveUsers() {
        return Collections.unmodifiableSet(userSet);
    }
}
