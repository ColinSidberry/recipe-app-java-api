package daos.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import daos.interfaces.BaseRepository;
import models.impl.CategoryModelImpl;

import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.*;
import static com.mongodb.client.model.Filters.eq;

/**
 * MongoDB implementation of the Category repository.
 * This class provides CRUD operations for Category entities using MongoDB as the backend.
 */
public class MongoCategoryRepositoryImpl implements BaseRepository<CategoryModelImpl> {
    private final MongoCollection<Document> collection;

    /**
     * Creates a new instance of the category repository.
     * 
     * @param database The MongoDB database instance to use for operations
     */
    public MongoCategoryRepositoryImpl(MongoDatabase database) {
        this.collection = database.getCollection("categories");
    }

    /**
     * Saves a new category to the database.
     * If the category already has an ID, it will be updated instead of creating a new record.
     * 
     * @param category The category to save
     * @return The saved category with its ID set
     */
    public CategoryModelImpl save(CategoryModelImpl category) {
        Document doc = new Document("name", category.getName())
                .append("description", category.getDescription());

        collection.insertOne(doc);
        category.setId(doc.getObjectId("_id").toHexString());
        return category;
    }

    /**
     * Retrieves a category by its ID.
     * 
     * @param id The ID of the category to retrieve
     * @return An Optional containing the category if found, or empty if not found
     */
    public Optional<CategoryModelImpl> findById(String id) {
        Document doc = collection.find(eq("_id", new ObjectId(id))).first();
        if (doc == null) return Optional.empty();

        CategoryModelImpl category = new CategoryModelImpl();
        category.setId(doc.getObjectId("_id").toHexString());
        category.setName(doc.getString("name"));
        category.setDescription(doc.getString("description"));
        return Optional.of(category);
    }

    /**
     * Retrieves all categories from the database.
     * 
     * @return A list of all categories in the database
     */
    public List<CategoryModelImpl> findAll() {
        List<CategoryModelImpl> categories = new ArrayList<>();
        for (Document doc : collection.find()) {
            CategoryModelImpl category = new CategoryModelImpl();
            category.setId(doc.getObjectId("_id").toHexString());
            category.setName(doc.getString("name"));
            category.setDescription(doc.getString("description"));
            categories.add(category);
        }
        return categories;
    }

    /**
     * Deletes a category by its ID.
     * 
     * @param id The ID of the category to delete
     */
    public void delete(String id) {
        collection.deleteOne(eq("_id", new ObjectId(id)));
    }
}
