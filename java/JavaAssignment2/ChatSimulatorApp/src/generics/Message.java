package generics;

import entity.User;

import java.time.LocalDateTime;

public class Message<T> implements Comparable<Message<T>> {
    private final User sender;
    private final T  content;
    private final LocalDateTime timestamp;
    private final User receiver;

    public Message(User sender, T content, User receiver) {
        this.sender = sender;
        this.content = content;
        this.timestamp = LocalDateTime.now();
        this.receiver = receiver;
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
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
