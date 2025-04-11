package services;

import daos.RecipeRepository;
import models.Recipe;
import java.util.List;

public class RecipeService {
  private final RecipeRepository repository;

  public RecipeService(RecipeRepository repository) {
    this.repository = repository;
  }

  public Recipe createRecipe(Recipe recipe) {
    return repository.save(recipe);
  }

  public List<Recipe> getRecipesByUser(String userId) {
    return repository.findByUserId(userId);
  }

  public List<Recipe> getAllRecipes() {
    return repository.findAll();
  }

  public void deleteRecipe(String id) {
    repository.delete(id);
  }
}