package daos.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import daos.interfaces.BaseRepository;
import models.impl.IngredientModelImpl;

import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.*;
import static com.mongodb.client.model.Filters.eq;

/**
 * MongoDB implementation of the Ingredient repository.
 * This class provides CRUD operations for Ingredient entities using MongoDB as the backend.
 */
public class MongoIngredientRepositoryImpl implements BaseRepository<IngredientModelImpl> {
    private final MongoCollection<Document> collection;

    /**
     * Creates a new instance of the ingredient repository.
     * 
     * @param database The MongoDB database instance to use for operations
     */
    public MongoIngredientRepositoryImpl(MongoDatabase database) {
        this.collection = database.getCollection("ingredients");
    }

    /**
     * Saves a new ingredient to the database.
     * If the ingredient already has an ID, it will be updated instead of creating a new record.
     * 
     * @param ingredient The ingredient to save
     * @return The saved ingredient with its ID set
     */
    public IngredientModelImpl save(IngredientModelImpl ingredient) {
        Document doc = new Document("name", ingredient.getName())
                .append("unit", ingredient.getUnit());

        collection.insertOne(doc);
        ingredient.setId(doc.getObjectId("_id").toHexString());
        return ingredient;
    }

    /**
     * Retrieves an ingredient by its ID.
     * 
     * @param id The ID of the ingredient to retrieve
     * @return An Optional containing the ingredient if found, or empty if not found
     */
    public Optional<IngredientModelImpl> findById(String id) {
        Document doc = collection.find(eq("_id", new ObjectId(id))).first();
        if (doc == null) return Optional.empty();

        IngredientModelImpl ingredient = new IngredientModelImpl();
        ingredient.setId(doc.getObjectId("_id").toHexString());
        ingredient.setName(doc.getString("name"));
        ingredient.setUnit(doc.getString("unit"));
        return Optional.of(ingredient);
    }

    /**
     * Retrieves all ingredients from the database.
     * 
     * @return A list of all ingredients in the database
     */
    public List<IngredientModelImpl> findAll() {
        List<IngredientModelImpl> ingredients = new ArrayList<>();
        for (Document doc : collection.find()) {
            IngredientModelImpl ingredient = new IngredientModelImpl();
            ingredient.setId(doc.getObjectId("_id").toHexString());
            ingredient.setName(doc.getString("name"));
            ingredient.setUnit(doc.getString("unit"));
            ingredients.add(ingredient);
        }
        return ingredients;
    }

    /**
     * Deletes an ingredient by its ID.
     * 
     * @param id The ID of the ingredient to delete
     */
    public void delete(String id) {
        collection.deleteOne(eq("_id", new ObjectId(id)));
    }
}
