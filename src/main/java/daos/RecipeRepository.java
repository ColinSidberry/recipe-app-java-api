package daos;

import models.Recipe;
import java.util.List;

public interface RecipeRepository extends BaseRepository<Recipe> {
  List<Recipe> findByUserId(String userId);
}
