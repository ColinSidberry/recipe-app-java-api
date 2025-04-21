package services.impl;

import java.util.List;

import daos.interfaces.RecipeIngredientRepository;
import models.impl.RecipeIngredientModelImpl;
import services.interfaces.RecipeIngredientService;

public class RecipeIngredientServiceImpl implements RecipeIngredientService {
  private final RecipeIngredientRepository repository;

  public RecipeIngredientServiceImpl(RecipeIngredientRepository repository) {
    this.repository = repository;
  }

  public RecipeIngredientModelImpl create(RecipeIngredientModelImpl recipeIngredient) {
    return repository.save(recipeIngredient);
  }

  public List<RecipeIngredientModelImpl> getAll() {
    return repository.findAll();
  }

  public RecipeIngredientModelImpl getById(String id) {
    return repository.findById(id).orElse(null);
  }

  public void delete(String id) {
    repository.delete(id);
  }

  public void deleteByRecipeId(String recipeId) {
    repository.deleteByRecipeId(recipeId);
  }

  public List<RecipeIngredientModelImpl> getIngredientsByRecipe(String recipeId) {
    return repository.findByRecipeId(recipeId);
  }
}
