package entity;

import java.util.Objects;

public class User implements Comparable<User> {
    private String username;

    public User(String username) {
        this.username = username;
    }

    public User() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public int compareTo(User other) {
        return this.username.compareToIgnoreCase(other.username);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return username.equalsIgnoreCase(user.username);
    }

//    @Override
//    public int hashCode() {
//        return Objects.hash(username.toLowerCase());
//    }

    @Override
    public String toString() {
        return username;
    }
}
