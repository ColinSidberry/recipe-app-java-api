package models.impl;

import java.time.LocalDateTime;
import java.util.Objects;
import models.interfaces.UserModel;

/**
 * Represents a user in the recipe application.
 * Contains user authentication and profile information.
 */
public class UserModelImpl implements UserModel {
    private String id;
    private String name;
    private String email;
    private String username;
    private String passwordHash;
    private LocalDateTime createdAt;

    /**
     * Default constructor.
     */
    public UserModelImpl() {
        this.createdAt = LocalDateTime.now();
    }

    /**
     * Constructor with essential fields.
     *
     * @param name The user's display name
     * @param email The user's email address
     * @param passwordHash The hashed password
     */
    public UserModelImpl(String username, String name, String email, String passwordHash) {
        this();
        this.username = username;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return passwordHash;
    }

    public void setPassword(String password) {
        this.passwordHash = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserModelImpl that = (UserModelImpl) o;
        return username.equals(that.username) &&
                name.equals(that.name) &&
                email.equals(that.email) &&
                passwordHash.equals(that.passwordHash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, name, email, passwordHash);
    }

    @Override
    public String toString() {
        return String.format(
            "UserModelImpl{id=%s, username=%s, name=%s, email=%s}",
            id,
            username,
            name,
            email
        );
    }
}
