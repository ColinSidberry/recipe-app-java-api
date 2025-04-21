package daos.interfaces;

import java.util.List;

import models.impl.RecipeIngredientModelImpl;

/**
 * Interface for managing RecipeIngredient entities in the data store.
 * Extends BaseRepository with additional methods for querying and deleting ingredients by recipe.
 */
public interface RecipeIngredientRepository extends BaseRepository<RecipeIngredientModelImpl> {
    /**
     * Retrieves all ingredients for a specific recipe.
     * 
     * @param recipeId The ID of the recipe to retrieve ingredients for
     * @return A list of ingredients for the specified recipe
     */
    List<RecipeIngredientModelImpl> findByRecipeId(String recipeId);

    /**
     * Deletes all ingredients for a specific recipe.
     * 
     * @param recipeId The ID of the recipe whose ingredients to delete
     */
    void deleteByRecipeId(String recipeId);
}
