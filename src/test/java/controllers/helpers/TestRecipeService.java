package controllers.helpers;

import services.interfaces.BaseService;
import models.impl.RecipeModelImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestRecipeService implements BaseService<RecipeModelImpl> {
    private final List<RecipeModelImpl> recipes = new ArrayList<>();
    private int nextId = 1;

    @Override
    public RecipeModelImpl create(RecipeModelImpl recipe) {
        recipe.setId("r" + nextId++);
        recipe.setCreatedAt(LocalDateTime.now());
        recipes.add(recipe);
        return recipe;
    }

    @Override
    public List<RecipeModelImpl> getAll() {
        return new ArrayList<>(recipes);
    }

    @Override
    public RecipeModelImpl getById(String id) {
        return recipes.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void delete(String id) {
        recipes.removeIf(r -> r.getId().equals(id));
    }
}
