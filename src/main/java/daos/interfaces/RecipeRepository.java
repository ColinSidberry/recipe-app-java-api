package daos.interfaces;

import java.util.List;

import models.impl.RecipeModelImpl;

/**
 * Interface for managing Recipe entities in the data store.
 * Extends BaseRepository with additional methods for querying recipes by user and category.
 */
public interface RecipeRepository extends BaseRepository<RecipeModelImpl> {
  /**
   * Retrieves all recipes for a specific user.
   * 
   * @param userId The ID of the user whose recipes to retrieve
   * @return A list of recipes belonging to the specified user
   */
  List<RecipeModelImpl> findByUser(String userId);

  /**
   * Retrieves all recipes in a specific category.
   * 
   * @param categoryId The ID of the category to retrieve recipes for
   * @return A list of recipes in the specified category
   */
  List<RecipeModelImpl> findByCategory(String categoryId);
}
