package services.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import daos.interfaces.BaseRepository;
import models.impl.RecipeModelImpl;

public class TestRecipeRepository implements BaseRepository<RecipeModelImpl> {
    private final List<RecipeModelImpl> recipes = new ArrayList<>();

    @Override
    public RecipeModelImpl save(RecipeModelImpl recipe) {
        if (recipe.getId() == null) {
            recipe.setId(java.util.UUID.randomUUID().toString());
        }
        recipes.removeIf(r -> r.getId().equals(recipe.getId()));
        recipes.add(recipe);
        return recipe;
    }

    @Override
    public List<RecipeModelImpl> findAll() {
        return new ArrayList<>(recipes);
    }

    @Override
    public Optional<RecipeModelImpl> findById(String id) {
        return recipes.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst();
    }

    @Override
    public void delete(String id) {
        recipes.removeIf(r -> r.getId().equals(id));
    }

    public void clear() {
        recipes.clear();
    }
}
