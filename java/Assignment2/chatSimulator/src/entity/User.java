package Assignment2.chatSimulator.src.entity;

import java.util.Objects;

/**
 * Represents a user in the chat system.
 * <p>
 * Each user has a unique username and can be compared, checked for equality,
 * and represented as a string.
 */
public class User implements Comparable<User> {

    /** The username of the user. */
    private String username;

    /**
     * Constructs a user with the given username.
     *
     * @param username the username of the user
     */
    public User(String username) {
        this.username = username;
    }

    /** Default constructor. */
    public User() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Compares this user to another user by username (case-insensitive).
     *
     * @param other the other user to compare
     * @return a negative integer, zero, or a positive integer if this user's
     *         username is lexicographically less than, equal to, or greater than the other username
     */
    @Override
    public int compareTo(User other) {
        return this.username.compareToIgnoreCase(other.username);
    }

    /**
     * Checks equality based on username (case-insensitive).
     *
     * @param o the object to compare
     * @return {@code true} if both users have the same username, ignoring case
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return username != null && username.equalsIgnoreCase(user.username);
    }

    /**
     * Ensures consistency with equals by using case-insensitive hashing.
     *
     * @return hash code based on lowercase username
     */
    @Override
    public int hashCode() {
        return username == null ? 0 : username.toLowerCase().hashCode();
    }

    /**
     * Returns the username as the string representation of the user.
     *
     * @return the username
     */
    @Override
    public String toString() {
        return username;
    }
}
