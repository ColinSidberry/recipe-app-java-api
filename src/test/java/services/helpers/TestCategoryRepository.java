package services.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import daos.interfaces.BaseRepository;
import models.impl.CategoryModelImpl;

public class TestCategoryRepository implements BaseRepository<CategoryModelImpl> {
    private final List<CategoryModelImpl> categories = new ArrayList<>();

    @Override
    public CategoryModelImpl save(CategoryModelImpl category) {
        if (category.getId() == null) {
            category.setId(java.util.UUID.randomUUID().toString());
        }
        categories.removeIf(c -> c.getId().equals(category.getId()));
        categories.add(category);
        return category;
    }

    @Override
    public List<CategoryModelImpl> findAll() {
        return new ArrayList<>(categories);
    }

    @Override
    public Optional<CategoryModelImpl> findById(String id) {
        return categories.stream()
            .filter(c -> c.getId().equals(id))
            .findFirst();
    }

    @Override
    public void delete(String id) {
        categories.removeIf(c -> c.getId().equals(id));
    }

    public void clear() {
        categories.clear();
    }
}
