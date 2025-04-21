package daos.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import daos.interfaces.RecipeRepository;
import models.impl.RecipeModelImpl;

import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.*;
import static com.mongodb.client.model.Filters.eq;

/**
 * MongoDB implementation of the Recipe repository.
 * This class provides CRUD operations for Recipe entities using MongoDB as the backend.
 * It includes additional methods for querying recipes by user and category.
 */
public class MongoRecipeRepositoryImpl implements RecipeRepository {
  private final MongoCollection<Document> collection;

  /**
   * Creates a new instance of the recipe repository.
   * 
   * @param database The MongoDB database instance to use for operations
   */
  public MongoRecipeRepositoryImpl(MongoDatabase database) {
    this.collection = database.getCollection("recipes");
  }

  /**
   * Saves a new recipe to the database.
   * If the recipe already has an ID, it will be updated instead of creating a new record.
   * 
   * @param recipe The recipe to save
   * @return The saved recipe with its ID set
   */
  public RecipeModelImpl save(RecipeModelImpl recipe) {
    Document doc = new Document("userId", recipe.getUserId())
        .append("title", recipe.getTitle())
        .append("description", recipe.getDescription())
        .append("createdAt", recipe.getCreatedAt().toString())
        .append("imageUrl", recipe.getImageUrl())
        .append("categoryId", recipe.getCategory() != null ? recipe.getCategory().getId() : null);

    collection.insertOne(doc);
    recipe.setId(doc.getObjectId("_id").toHexString());
    return recipe;
  }

  /**
   * Retrieves a recipe by its ID.
   * 
   * @param id The ID of the recipe to retrieve
   * @return An Optional containing the recipe if found, or empty if not found
   */
  public Optional<RecipeModelImpl> findById(String id) {
    Document doc = collection.find(eq("_id", new ObjectId(id))).first();
    if (doc == null) return Optional.empty();

    RecipeModelImpl recipe = new RecipeModelImpl();
    recipe.setId(doc.getObjectId("_id").toHexString());
    recipe.setUserId(doc.getString("userId"));
    recipe.setTitle(doc.getString("title"));
    recipe.setDescription(doc.getString("description"));
    recipe.setImageUrl(doc.getString("imageUrl"));
    // Optional: Map category from DB here if needed
    return Optional.of(recipe);
  }

  /**
   * Retrieves all recipes from the database.
   * 
   * @return A list of all recipes in the database
   */
  public List<RecipeModelImpl> findAll() {
    List<RecipeModelImpl> list = new ArrayList<>();
    for (Document doc : collection.find()) {
      RecipeModelImpl recipe = new RecipeModelImpl();
      recipe.setId(doc.getObjectId("_id").toHexString());
      recipe.setTitle(doc.getString("title"));
      list.add(recipe);
    }
    return list;
  }

  /**
   * Deletes a recipe by its ID.
   * 
   * @param id The ID of the recipe to delete
   */
  public void delete(String id) {
    collection.deleteOne(eq("_id", new ObjectId(id)));
  }

  /**
   * Retrieves all recipes created by a specific user.
   * 
   * @param userId The ID of the user whose recipes to retrieve
   * @return A list of recipes created by the specified user
   */
  public List<RecipeModelImpl> findByUser(String userId) {
    List<RecipeModelImpl> list = new ArrayList<>();
    for (Document doc : collection.find(Filters.eq("userId", userId))) {
      RecipeModelImpl recipe = new RecipeModelImpl();
      recipe.setId(doc.getObjectId("_id").toHexString());
      recipe.setUserId(userId);
      recipe.setTitle(doc.getString("title"));
      list.add(recipe);
    }
    return list;
  }

  /**
   * Retrieves all recipes in a specific category.
   * 
   * @param categoryId The ID of the category to filter by
   * @return A list of recipes in the specified category
   */
  public List<RecipeModelImpl> findByCategory(String categoryId) {
    List<RecipeModelImpl> list = new ArrayList<>();
    for (Document doc : collection.find(Filters.eq("categoryId", categoryId))) {
      RecipeModelImpl recipe = new RecipeModelImpl();
      recipe.setId(doc.getObjectId("_id").toHexString());
      recipe.setUserId(doc.getString("userId"));
      recipe.setTitle(doc.getString("title"));
      recipe.setDescription(doc.getString("description"));
      recipe.setImageUrl(doc.getString("imageUrl"));
      list.add(recipe);
    }
    return list;
  }
}
