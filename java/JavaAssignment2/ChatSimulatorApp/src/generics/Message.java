package generics;

import model.User;

import java.time.LocalDateTime;

public class Message<T> implements Comparable<Message<T>> {
    private final User sender;
    private final T content;
    private final LocalDateTime timestamp;

    public Message(User sender, T content) {
        this.sender = sender;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    public User getSender() {
        return sender;
    }

    public T getContent() {
        return content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public int compareTo(Message<T> other) {
        return this.timestamp.compareTo(other.timestamp);
    }

    @Override
    public String toString() {
        return "[" + timestamp.toString() + "] " + sender.getUsername() + ": " + content;
    }
}
