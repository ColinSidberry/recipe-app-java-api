package daos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import daos.helpers.TestMongoRepositoryHelper;
import daos.impl.MongoUserRepositoryImpl;

import org.junit.jupiter.api.AfterEach;

import models.impl.UserModelImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TestMongoUserRepositoryImpl {
    private daos.interfaces.UserRepository mongoRepo;

    @BeforeEach
    void setup() {
        mongoRepo = new MongoUserRepositoryImpl(TestMongoRepositoryHelper.getDatabase());
        TestMongoRepositoryHelper.clearCollection("users");
    }

    @AfterEach
    void cleanup() {
        TestMongoRepositoryHelper.clearCollection("users");
    }

    @Test
    void testSave() {
        UserModelImpl user = new UserModelImpl("test-user", "Test User", "test@example.com", "password123");

        UserModelImpl saved = mongoRepo.save(user);

        assertNotNull(saved.getId());
        assertEquals("test-user", saved.getUsername());
        assertEquals("Test User", saved.getName());
        assertEquals("test@example.com", saved.getEmail());
        assertEquals("password123", saved.getPasswordHash());
    }

    @Test
    void testFindById_found() {
        UserModelImpl user = new UserModelImpl("test-user", "Test User", "test@example.com", "password123");
        UserModelImpl saved = mongoRepo.save(user);

        Optional<UserModelImpl> result = mongoRepo.findById(saved.getId());

        assertTrue(result.isPresent());
        assertEquals("test-user", result.get().getUsername());
        assertEquals("Test User", result.get().getName());
        assertEquals("test@example.com", result.get().getEmail());
        assertEquals("password123", result.get().getPasswordHash());
    }

    @Test
    void testFindById_notFound() {
        // Generate a random ObjectId that doesn't exist in the database
        String nonExistentId = new org.bson.types.ObjectId().toString();
        Optional<UserModelImpl> result = mongoRepo.findById(nonExistentId);
        assertFalse(result.isPresent());
    }

    @Test
    void testFindAll() {
        UserModelImpl user1 = new UserModelImpl("user1", "User One", "user1@example.com", "password1");
        mongoRepo.save(user1);

        UserModelImpl user2 = new UserModelImpl("user2", "User Two", "user2@example.com", "password2");
        mongoRepo.save(user2);

        List<UserModelImpl> users = mongoRepo.findAll();
        assertEquals(2, users.size());
    }

    @Test
    void testFindByEmail_found() {
        UserModelImpl user = new UserModelImpl("test-user", "Test User", "test@example.com", "password123");
        mongoRepo.save(user);

        Optional<UserModelImpl> result = mongoRepo.findByEmail("test@example.com");

        assertTrue(result.isPresent());
        assertEquals("test-user", result.get().getUsername());
        assertEquals("Test User", result.get().getName());
        assertEquals("test@example.com", result.get().getEmail());
        assertEquals("password123", result.get().getPasswordHash());
    }

    @Test
    void testFindByEmail_notFound() {
        Optional<UserModelImpl> result = mongoRepo.findByEmail("nonexistent@example.com");
        assertFalse(result.isPresent());
    }

    @Test
    void testDelete() {
        UserModelImpl user = new UserModelImpl("test-user", "Test User", "test@example.com", "password123");
        UserModelImpl saved = mongoRepo.save(user);

        mongoRepo.delete(saved.getId());

        Optional<UserModelImpl> result = mongoRepo.findById(saved.getId());
        assertFalse(result.isPresent());
    }
}
