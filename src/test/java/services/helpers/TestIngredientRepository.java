package services.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import models.impl.IngredientModelImpl;
import daos.interfaces.BaseRepository;

public class TestIngredientRepository implements BaseRepository<IngredientModelImpl> {
    private final List<IngredientModelImpl> ingredients = new ArrayList<>();

    @Override
    public IngredientModelImpl save(IngredientModelImpl ingredient) {
        if (ingredient.getId() == null) {
            ingredient.setId(java.util.UUID.randomUUID().toString());
        }
        ingredients.removeIf(i -> i.getId().equals(ingredient.getId()));
        ingredients.add(ingredient);
        return ingredient;
    }

    @Override
    public List<IngredientModelImpl> findAll() {
        return new ArrayList<>(ingredients);
    }

    @Override
    public Optional<IngredientModelImpl> findById(String id) {
        return ingredients.stream()
                .filter(i -> i.getId().equals(id))
                .findFirst();
    }

    @Override
    public void delete(String id) {
        ingredients.removeIf(i -> i.getId().equals(id));
    }

    public void clear() {
        ingredients.clear();
    }
}
