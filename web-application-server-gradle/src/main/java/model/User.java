package model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class User {
    private final String userId;
    private final String password;
    private final String name;
    private final String email;

    @Builder
    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public static User of(String userId, String password, String name, String email) {
        return new User(userId, password, name, email);
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
    }

    public boolean samePassword(String password) {
        return this.password.equals(password);
    }
}
