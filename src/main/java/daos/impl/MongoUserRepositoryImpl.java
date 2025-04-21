package daos.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import daos.interfaces.UserRepository;
import models.impl.UserModelImpl;

import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.*;
import static com.mongodb.client.model.Filters.eq;

/**
 * MongoDB implementation of the User repository.
 * This class provides CRUD operations for User entities using MongoDB as the backend.
 * It includes additional methods for querying users by email.
 */
public class MongoUserRepositoryImpl implements UserRepository {
    private final MongoCollection<Document> collection;

    /**
     * Creates a new instance of the user repository.
     * 
     * @param database The MongoDB database instance to use for operations
     */
    public MongoUserRepositoryImpl(MongoDatabase database) {
        this.collection = database.getCollection("users");
    }

    /**
     * Saves a new user to the database.
     * If the user already has an ID, it will be updated instead of creating a new record.
     * 
     * @param user The user to save
     * @return The saved user with its ID set
     */
    public UserModelImpl save(UserModelImpl user) {
        Document doc = new Document("username", user.getUsername())
                .append("email", user.getEmail())
                .append("name", user.getName())
                .append("passwordHash", user.getPasswordHash());

        collection.insertOne(doc);
        user.setId(doc.getObjectId("_id").toHexString());
        return user;
    }

    /**
     * Retrieves a user by their ID.
     * 
     * @param id The ID of the user to retrieve
     * @return An Optional containing the user if found, or empty if not found
     */
    public Optional<UserModelImpl> findById(String id) {
        Document doc = collection.find(eq("_id", new ObjectId(id))).first();
        if (doc == null) return Optional.empty();

        UserModelImpl user = new UserModelImpl();
        user.setId(doc.getObjectId("_id").toHexString());
        user.setUsername(doc.getString("username"));
        user.setEmail(doc.getString("email"));
        user.setName(doc.getString("name"));
        user.setPasswordHash(doc.getString("passwordHash"));
        return Optional.of(user);
    }

    /**
     * Retrieves a user by their email address.
     * 
     * @param email The email address of the user to retrieve
     * @return An Optional containing the user if found, or empty if not found
     */
    public Optional<UserModelImpl> findByEmail(String email) {
        Document doc = collection.find(eq("email", email)).first();
        if (doc == null) return Optional.empty();

        UserModelImpl user = new UserModelImpl();
        user.setId(doc.getObjectId("_id").toHexString());
        user.setUsername(doc.getString("username"));
        user.setEmail(doc.getString("email"));
        user.setName(doc.getString("name"));
        user.setPasswordHash(doc.getString("passwordHash"));
        return Optional.of(user);
    }

    /**
     * Retrieves all users from the database.
     * 
     * @return A list of all users in the database
     */
    public List<UserModelImpl> findAll() {
        List<UserModelImpl> users = new ArrayList<>();
        for (Document doc : collection.find()) {
            UserModelImpl user = new UserModelImpl();
            user.setId(doc.getObjectId("_id").toHexString());
            user.setEmail(doc.getString("email"));
            user.setName(doc.getString("name"));
            users.add(user);
        }
        return users;
    }

    /**
     * Updates an existing user in the database.
     * Only updates the fields that are provided in the user object.
     * 
     * @param user The user to update
     * @return The updated user object
     */
    public UserModelImpl update(UserModelImpl user) {
        Document updates = new Document()
                .append("email", user.getEmail())
                .append("name", user.getName());
        
        if (user.getPasswordHash() != null) {
            updates.append("passwordHash", user.getPasswordHash());
        }

        collection.updateOne(
            eq("_id", new ObjectId(user.getId())),
            new Document("$set", updates)
        );
        return user;
    }

    /**
     * Deletes a user by their ID.
     * 
     * @param id The ID of the user to delete
     */
    public void delete(String id) {
        collection.deleteOne(eq("_id", new ObjectId(id)));
    }
}
