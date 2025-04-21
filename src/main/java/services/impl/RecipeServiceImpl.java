package services.impl;

import java.util.List;

import daos.interfaces.BaseRepository;
import models.impl.RecipeModelImpl;
import services.interfaces.UserService;
import services.interfaces.CategoryService;
import services.interfaces.IngredientService;
import services.interfaces.RecipeService;

public class RecipeServiceImpl implements RecipeService {
  private final BaseRepository<RecipeModelImpl> repository;
  private final UserService userService;
  private final CategoryService categoryService;
  private final IngredientService ingredientService;

  public RecipeServiceImpl(
      BaseRepository<RecipeModelImpl> repository,
      UserService userService,
      CategoryService categoryService,
      IngredientService ingredientService
    ) {
      this.repository = repository;
      this.userService = userService;
      this.categoryService = categoryService;
      this.ingredientService = ingredientService;
    }

  public RecipeModelImpl create(RecipeModelImpl recipe) {
    // Validate user exists
    if (recipe.getUserId() != null) {
      userService.getById(recipe.getUserId());
    }

    // Validate category exists
    if (recipe.getCategoryId() != null) {
      categoryService.getById(recipe.getCategoryId());
    }

    // Validate ingredients exist
    if (recipe.getIngredients() != null) {
      for (var ingredient : recipe.getIngredients()) {
        ingredientService.getById(ingredient.getIngredientId());
      }
    }

    return repository.save(recipe);
  }

  public List<RecipeModelImpl> getAll() {
    return repository.findAll();
  }

  public RecipeModelImpl getById(String id) {
    return repository.findById(id).orElse(null);
  }

  public List<RecipeModelImpl> findByUserId(String userId) {
    return repository.findAll().stream()
        .filter(recipe -> recipe.getUserId() != null && recipe.getUserId().equals(userId))
        .toList();
  }

  public List<RecipeModelImpl> findByCategoryId(String categoryId) {
    return repository.findAll().stream()
        .filter(recipe -> recipe.getCategoryId() != null && recipe.getCategoryId().equals(categoryId))
        .toList();
  }

  public void delete(String id) {
    repository.delete(id);
  }
}