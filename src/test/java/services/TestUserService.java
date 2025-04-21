package services;

import models.impl.UserModelImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.helpers.TestUserRepository;
import services.impl.UserServiceImpl;
import services.interfaces.BaseService;

import static org.junit.jupiter.api.Assertions.*;

public class TestUserService {
    private final TestUserRepository repository = new TestUserRepository();
    private BaseService<UserModelImpl> service;

    @BeforeEach
    public void setUp() {
        service = new UserServiceImpl(repository);
        repository.clear();
    }

    @AfterEach
    public void tearDown() {
        repository.clear();
    }

    @Test
    void testCreateUser() {
        var user = new UserModelImpl();
        user.setUsername("testuser");
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPasswordHash("password123");

        var created = service.create(user);
        assertNotNull(created);
        assertNotNull(created.getId());
        assertEquals("testuser", created.getUsername());
        assertEquals("Test User", created.getName());
        assertEquals("test@example.com", created.getEmail());
    }

    @Test
    void testGetUser() {
        var user = new UserModelImpl();
        user.setUsername("testuser");
        user.setName("Test User");
        user.setEmail("test@example.com");
        var created = service.create(user);

        var retrieved = service.getById(created.getId());
        assertNotNull(retrieved);
        assertEquals("testuser", retrieved.getUsername());
        assertEquals("Test User", retrieved.getName());
        assertEquals("test@example.com", retrieved.getEmail());
    }

    @Test
    void testGetAllUsers() {
        var user1 = new UserModelImpl();
        user1.setUsername("user1");
        user1.setName("User 1");
        user1.setEmail("user1@example.com");
        var created1 = service.create(user1);

        var user2 = new UserModelImpl();
        user2.setUsername("user2");
        user2.setName("User 2");
        user2.setEmail("user2@example.com");
        var created2 = service.create(user2);

        var users = service.getAll();
        assertNotNull(users);
        assertEquals(2, users.size());
        assertTrue(users.stream().anyMatch(u -> created1.getId().equals(u.getId())));
        assertTrue(users.stream().anyMatch(u -> created2.getId().equals(u.getId())));
    }

    @Test
    void testDeleteUser() {
        var user = new UserModelImpl();
        user.setUsername("testuser");
        user.setName("Test User");
        user.setEmail("test@example.com");
        var created = service.create(user);

        service.delete(created.getId());
        var retrieved = service.getById(created.getId());
        assertNull(retrieved);
    }
}
