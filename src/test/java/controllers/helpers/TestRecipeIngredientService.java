package controllers.helpers;

import services.interfaces.RecipeIngredientService;
import models.impl.RecipeIngredientModelImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TestRecipeIngredientService implements RecipeIngredientService {
    private final List<RecipeIngredientModelImpl> ingredients = new ArrayList<>();
    private int nextId = 1;

    public RecipeIngredientModelImpl create(RecipeIngredientModelImpl ingredient) {
        ingredient.setId("ri" + nextId++);
        ingredients.add(ingredient);
        return ingredient;
    }

    public List<RecipeIngredientModelImpl> getAll() {
        return new ArrayList<>(ingredients);
    }

    public RecipeIngredientModelImpl getById(String id) {
        return ingredients.stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void delete(String id) {
        ingredients.removeIf(i -> i.getId().equals(id));
    }

    public List<RecipeIngredientModelImpl> getIngredientsByRecipe(String recipeId) {
        return ingredients.stream()
                .filter(i -> i.getRecipeId().equals(recipeId))
                .collect(Collectors.toList());
    }

    public void deleteByRecipeId(String recipeId) {
        ingredients.removeIf(i -> i.getRecipeId().equals(recipeId));
    }
}
