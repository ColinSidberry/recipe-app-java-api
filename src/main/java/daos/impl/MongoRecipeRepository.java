package daos.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import daos.RecipeRepository;
import models.Recipe;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.*;
import static com.mongodb.client.model.Filters.eq;

public class MongoRecipeRepository implements RecipeRepository {
  private final MongoCollection<Document> collection;

  public MongoRecipeRepository(MongoDatabase database) {
    this.collection = database.getCollection("recipes");
  }

  @Override
  public Recipe save(Recipe recipe) {
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

  @Override
  public Optional<Recipe> findById(String id) {
    Document doc = collection.find(eq("_id", new ObjectId(id))).first();
    if (doc == null) return Optional.empty();

    Recipe recipe = new Recipe();
    recipe.setId(doc.getObjectId("_id").toHexString());
    recipe.setUserId(doc.getString("userId"));
    recipe.setTitle(doc.getString("title"));
    recipe.setDescription(doc.getString("description"));
    recipe.setImageUrl(doc.getString("imageUrl"));
    // Optional: Map category from DB here if needed
    return Optional.of(recipe);
  }

  @Override
  public List<Recipe> findAll() {
    List<Recipe> list = new ArrayList<>();
    for (Document doc : collection.find()) {
      Recipe recipe = new Recipe();
      recipe.setId(doc.getObjectId("_id").toHexString());
      recipe.setTitle(doc.getString("title"));
      list.add(recipe);
    }
    return list;
  }

  @Override
  public void delete(String id) {
    collection.deleteOne(eq("_id", new ObjectId(id)));
  }

  @Override
  public List<Recipe> findByUserId(String userId) {
    List<Recipe> list = new ArrayList<>();
    for (Document doc : collection.find(Filters.eq("userId", userId))) {
      Recipe recipe = new Recipe();
      recipe.setId(doc.getObjectId("_id").toHexString());
      recipe.setUserId(userId);
      recipe.setTitle(doc.getString("title"));
      list.add(recipe);
    }
    return list;
  }
}
