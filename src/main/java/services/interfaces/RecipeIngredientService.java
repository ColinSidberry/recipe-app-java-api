package services.interfaces;

import java.util.List;

import models.impl.RecipeIngredientModelImpl;

public interface RecipeIngredientService extends BaseService<RecipeIngredientModelImpl> {
    public List<RecipeIngredientModelImpl> getIngredientsByRecipe(String recipeId);
    public void deleteByRecipeId(String recipeId);
}
