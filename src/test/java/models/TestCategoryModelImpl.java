package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import models.impl.CategoryModelImpl;
import models.helpers.TestModelHelper;

import static org.junit.jupiter.api.Assertions.*;

class TestCategoryModelImpl {
    private CategoryModelImpl category;

    @BeforeEach
    void setup() {
        category = TestModelHelper.createCategory();
    }

    @AfterEach
    void cleanup() {
        category = null;
    }

    @Test
    void testConstructor() {
        assertNotNull(category);
        assertEquals("test-category", category.getId());
        assertEquals("Test Category", category.getName());
        assertEquals("Test category description", category.getDescription());
    }

    @Test
    void testSetters() {
        category.setId("new-category");
        category.setName("New Category");
        category.setDescription("New category description");

        assertEquals("new-category", category.getId());
        assertEquals("New Category", category.getName());
        assertEquals("New category description", category.getDescription());
    }

    @Test
    void testGetters() {
        assertEquals("test-category", category.getId());
        assertEquals("Test Category", category.getName());
        assertEquals("Test category description", category.getDescription());
    }
}
