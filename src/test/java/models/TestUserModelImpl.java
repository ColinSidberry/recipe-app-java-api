package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import models.impl.UserModelImpl;
import models.helpers.TestModelHelper;

import static org.junit.jupiter.api.Assertions.*;

class TestUserModelImpl {
    private UserModelImpl user;

    @BeforeEach
    void setup() {
        user = TestModelHelper.createUser();
    }

    @AfterEach
    void cleanup() {
        user = null;
    }

    @Test
    void testConstructor() {
        assertNotNull(user);
        assertEquals("test-user", user.getUsername());
        assertEquals("Test User", user.getName());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("password123", user.getPasswordHash());
    }

    @Test
    void testSetters() {
        user.setUsername("new-user");
        user.setName("New Name");
        user.setEmail("new@example.com");
        user.setPasswordHash("newpassword123");

        assertEquals("new-user", user.getUsername());
        assertEquals("New Name", user.getName());
        assertEquals("new@example.com", user.getEmail());
        assertEquals("newpassword123", user.getPasswordHash());
    }

    @Test
    void testGetters() {
        assertEquals("test-user", user.getUsername());
        assertEquals("Test User", user.getName());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("password123", user.getPasswordHash());
    }
}
