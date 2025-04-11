package controller;

import config.MongoConfig;
import daos.impl.MongoRecipeRepository;
import java.util.Objects;
import models.Category;
import models.Recipe;
import services.RecipeService;

import java.time.LocalDateTime;
import java.util.List;

public class RecipeController {

  private final RecipeService service;

  public RecipeController() {
    this.service = new RecipeService(
        //should there be some type of centralized validation?
        new MongoRecipeRepository(Objects.requireNonNull(MongoConfig.getDatabase()))
    );
  }

  public void start() {
    System.out.println("Creating a sample recipe...");

    Recipe recipe = new Recipe();
    recipe.setUserId("12345");
    recipe.setTitle("Spaghetti Bolognese");
    recipe.setDescription("A classic Italian pasta dish.");
    recipe.setCreatedAt(LocalDateTime.now());
    recipe.setImageUrl("https://example.com/image.jpg");
    recipe.setCategory(new Category("cat123", "Dinner"));

    Recipe saved = service.createRecipe(recipe);
    System.out.println("Saved Recipe ID: " + saved.getId());

    List<Recipe> allRecipes = service.getAllRecipes();
    System.out.println("Total Recipes: " + allRecipes.size());
  }
}