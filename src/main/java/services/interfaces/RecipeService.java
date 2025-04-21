package services.interfaces;

import java.util.List;

import models.impl.RecipeModelImpl;

public interface RecipeService extends BaseService<RecipeModelImpl> {
    List<RecipeModelImpl> findByUserId(String userId);
    List<RecipeModelImpl> findByCategoryId(String categoryId);
}
