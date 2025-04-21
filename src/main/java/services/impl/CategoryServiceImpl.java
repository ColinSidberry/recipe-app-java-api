package services.impl;

import daos.interfaces.BaseRepository;
import models.impl.CategoryModelImpl;

import java.util.List;
import java.util.Optional;
import services.interfaces.CategoryService;

/**
 * Service class for managing Category-related operations.
 */
public class CategoryServiceImpl implements CategoryService {
    private final BaseRepository<CategoryModelImpl> repository;

    /**
     * Constructs a new CategoryService with the given repository.
     *
     * @param repository The CategoryRepository to use for data access
     */
    public CategoryServiceImpl(BaseRepository<CategoryModelImpl> repository) {
        this.repository = repository;
    }

    /**
     * Creates a new category.
     *
     * @param category The category to create
     * @return The created category with its ID set
     */
    public CategoryModelImpl create(CategoryModelImpl category) {
        return repository.save(category);
    }

    /**
     * Retrieves all categories.
     *
     * @return A list of all categories
     */
    public List<CategoryModelImpl> getAll() {
        return repository.findAll();
    }

    /**
     * Retrieves a category by its ID.
     *
     * @param id The ID of the category to retrieve
     * @return The category if found, null otherwise
     */
    public CategoryModelImpl getById(String id) {
        Optional<CategoryModelImpl> category = repository.findById(id);
        return category.orElse(null);
    }

    /**
     * Deletes a category by its ID.
     *
     * @param id The ID of the category to delete
     */
    public void delete(String id) {
        repository.delete(id);
    }
}
