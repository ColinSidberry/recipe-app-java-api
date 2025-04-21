package services;

import models.impl.CategoryModelImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.helpers.TestCategoryRepository;
import services.impl.CategoryServiceImpl;
import services.interfaces.BaseService;
import daos.interfaces.BaseRepository;


import static org.junit.jupiter.api.Assertions.*;

public class TestCategoryService {
    private final TestCategoryRepository repository = new TestCategoryRepository();
    private BaseService<CategoryModelImpl> service;

    @BeforeEach
    public void setUp() {
        service = new CategoryServiceImpl((BaseRepository<CategoryModelImpl>) repository);
        repository.clear();
    }

    @AfterEach
    public void tearDown() {
        repository.clear();
    }

    @Test
    void testCreateCategory() {
        var category = new CategoryModelImpl();
        category.setId("test-category");
        category.setName("Test Category");
        category.setDescription("Test category description");

        var created = service.create(category);
        assertNotNull(created);
        assertNotNull(created.getId());
        assertEquals("Test Category", created.getName());
        assertEquals("Test category description", created.getDescription());
    }

    @Test
    void testGetCategory() {
        var category = new CategoryModelImpl();
        category.setId("test-category");
        category.setName("Test Category");
        category.setDescription("Test category description");
        service.create(category);

        var retrieved = service.getById("test-category");
        assertNotNull(retrieved);
        assertEquals("Test Category", retrieved.getName());
        assertEquals("Test category description", retrieved.getDescription());
    }

    @Test
    void testGetAllCategories() {
        var category1 = new CategoryModelImpl();
        category1.setId("test-category-1");
        category1.setName("Test Category 1");
        category1.setDescription("Test category 1 description");
        service.create(category1);

        var category2 = new CategoryModelImpl();
        category2.setId("test-category-2");
        category2.setName("Test Category 2");
        category2.setDescription("Test category 2 description");
        service.create(category2);

        var categories = service.getAll();
        assertNotNull(categories);
        assertEquals(2, categories.size());
    }

    @Test
    void testDeleteCategory() {
        var category = new CategoryModelImpl();
        category.setId("test-category");
        category.setName("Test Category");
        category.setDescription("Test category description");
        service.create(category);

        service.delete("test-category");
        var retrieved = service.getById("test-category");
        assertNull(retrieved);
    }
}
