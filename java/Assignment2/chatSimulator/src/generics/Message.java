package Assignment2.chatSimulator.src.generics;

import Assignment2.chatSimulator.src.entity.User;
import java.time.LocalDateTime;

/**
 * Represents a generic message exchanged between users in the chat.
 *
 * @param <T> the type of message content (e.g., String, Image, File, etc.)
 */
public class Message<T> implements Comparable<Message<T>> {

    /** The user who sends the message. */
    private final User sender;

    /** The content of the message (generic type). */
    private final T content;

    /** The timestamp when the message was sent. */
    private final LocalDateTime timestamp;

    /** The user who receives the message. */
    private final User receiver;

    /**
     * Constructs a new message with the given sender, content, and receiver.
     * The timestamp is automatically set to the current time.
     *
     * @param sender   the user sending the message
     * @param content  the message content
     * @param receiver the user receiving the message
     */
    public Message(User sender, T content, User receiver) {
        this.sender = sender;
        this.content = content;
        this.timestamp = LocalDateTime.now();
        this.receiver = receiver;
    }

    /** @return the sender of the message */
    public User getSender() {
        return sender;
    }

    /** @return the receiver of the message */
    public User getReceiver() {
        return receiver;
    }

    /** @return the content of the message */
    public T getContent() {
        return content;
    }

    /** @return the timestamp when the message was created */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Compares two messages based on their timestamp.
     *
     * @param other the other message to compare with
     * @return a negative integer, zero, or a positive integer if this message
     *         was sent before, at the same time, or after the other message
     */
    @Override
    public int compareTo(Message<T> other) {
        return this.timestamp.compareTo(other.timestamp);
    }

    /**
     * Returns a string representation of the message in the format:
     * [timestamp] sender: content
     *
     * @return formatted message string
     */
    @Override
    public String toString() {
        return "[" + timestamp + "] " + sender.getUsername() + ": " + content;
    }
}
