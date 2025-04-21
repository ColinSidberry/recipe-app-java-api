package daos.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import daos.interfaces.RecipeIngredientRepository;
import models.impl.RecipeIngredientModelImpl;

import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.*;
import static com.mongodb.client.model.Filters.eq;

/**
 * MongoDB implementation of the RecipeIngredient repository.
 * This class provides CRUD operations for RecipeIngredient entities using MongoDB as the backend.
 * It includes additional methods for querying recipe ingredients by recipe ID.
 */
public class MongoRecipeIngredientRepositoryImpl implements RecipeIngredientRepository {
    private final MongoCollection<Document> collection;

    /**
     * Creates a new instance of the recipe ingredient repository.
     * 
     * @param database The MongoDB database instance to use for operations
     */
    public MongoRecipeIngredientRepositoryImpl(MongoDatabase database) {
        this.collection = database.getCollection("recipe_ingredients");
    }

    /**
     * Saves a new recipe ingredient to the database.
     * If the recipe ingredient already has an ID, it will be updated instead of creating a new record.
     * 
     * @param recipeIngredient The recipe ingredient to save
     * @return The saved recipe ingredient with its ID set
     */
    public RecipeIngredientModelImpl save(RecipeIngredientModelImpl recipeIngredient) {
        Document doc = new Document("recipeId", recipeIngredient.getRecipeId())
                .append("ingredientId", recipeIngredient.getIngredientId())
                .append("quantity", recipeIngredient.getQuantity())
                .append("unit", recipeIngredient.getUnit());
        collection.insertOne(doc);
        recipeIngredient.setId(doc.getObjectId("_id").toHexString());
        return recipeIngredient;
    }

    /**
     * Retrieves a recipe ingredient by its ID.
     * 
     * @param id The ID of the recipe ingredient to retrieve
     * @return An Optional containing the recipe ingredient if found, or empty if not found
     */
    public Optional<RecipeIngredientModelImpl> findById(String id) {
        Document doc = collection.find(eq("_id", new ObjectId(id))).first();
        if (doc == null) return Optional.empty();

        RecipeIngredientModelImpl recipeIngredient = new RecipeIngredientModelImpl();
        recipeIngredient.setId(doc.getObjectId("_id").toHexString());
        recipeIngredient.setRecipeId(doc.getString("recipeId"));
        recipeIngredient.setIngredientId(doc.getString("ingredientId"));
        recipeIngredient.setQuantity(doc.getDouble("quantity"));
        recipeIngredient.setUnit(doc.getString("unit"));
        return Optional.of(recipeIngredient);
    }

    /**
     * Retrieves all recipe ingredients from the database.
     * 
     * @return A list of all recipe ingredients in the database
     */
    public List<RecipeIngredientModelImpl> findAll() {
        List<RecipeIngredientModelImpl> recipeIngredients = new ArrayList<>();
        for (Document doc : collection.find()) {
            RecipeIngredientModelImpl recipeIngredient = new RecipeIngredientModelImpl();
            recipeIngredient.setId(doc.getObjectId("_id").toHexString());
            recipeIngredient.setRecipeId(doc.getString("recipeId"));
            recipeIngredient.setIngredientId(doc.getString("ingredientId"));
            recipeIngredients.add(recipeIngredient);
        }
        return recipeIngredients;
    }

    /**
     * Retrieves all ingredients for a specific recipe.
     * 
     * @param recipeId The ID of the recipe to retrieve ingredients for
     * @return A list of ingredients for the specified recipe
     */
    public List<RecipeIngredientModelImpl> findByRecipeId(String recipeId) {
        List<RecipeIngredientModelImpl> recipeIngredients = new ArrayList<>();
        for (Document doc : collection.find(Filters.eq("recipeId", recipeId))) {
            RecipeIngredientModelImpl recipeIngredient = new RecipeIngredientModelImpl();
            recipeIngredient.setId(doc.getObjectId("_id").toHexString());
            recipeIngredient.setRecipeId(recipeId);
            recipeIngredient.setIngredientId(doc.getString("ingredientId"));
            recipeIngredient.setQuantity(doc.getDouble("quantity"));
            recipeIngredient.setUnit(doc.getString("unit"));
            recipeIngredients.add(recipeIngredient);
        }
        return recipeIngredients;
    }

    /**
     * Deletes all ingredients for a specific recipe.
     * 
     * @param recipeId The ID of the recipe whose ingredients to delete
     */
    public void deleteByRecipeId(String recipeId) {
        collection.deleteMany(Filters.eq("recipeId", recipeId));
    }

    /**
     * Deletes a recipe ingredient by its ID.
     * 
     * @param id The ID of the recipe ingredient to delete
     */
    public void delete(String id) {
        collection.deleteOne(eq("_id", new ObjectId(id)));
    }
}
