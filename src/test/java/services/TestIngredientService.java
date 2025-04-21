package services;

import models.impl.IngredientModelImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.helpers.TestIngredientRepository;
import services.impl.IngredientServiceImpl;
import services.interfaces.BaseService;


import static org.junit.jupiter.api.Assertions.*;

public class TestIngredientService {
    private final TestIngredientRepository repository = new TestIngredientRepository();
    private BaseService<IngredientModelImpl> service;

    @BeforeEach
    public void setUp() {
        service = new IngredientServiceImpl(repository);
        repository.clear();
    }

    @AfterEach
    public void tearDown() {
        repository.clear();
    }

    @Test
    void testCreateIngredient() {
        var ingredient = new IngredientModelImpl();
        ingredient.setId("test-ingredient");
        ingredient.setName("Test Ingredient");
        ingredient.setUnit("piece");

        var created = service.create(ingredient);
        assertNotNull(created);
        assertNotNull(created.getId());
        assertEquals("Test Ingredient", created.getName());
        assertEquals("piece", created.getUnit());
    }

    @Test
    void testGetIngredient() {
        var ingredient = new IngredientModelImpl();
        ingredient.setId("test-ingredient");
        ingredient.setName("Test Ingredient");
        ingredient.setUnit("piece");
        service.create(ingredient);

        var retrieved = service.getById("test-ingredient");
        assertNotNull(retrieved);
        assertEquals("Test Ingredient", retrieved.getName());
        assertEquals("piece", retrieved.getUnit());
    }

    @Test
    void testGetAllIngredients() {
        var ingredient1 = new IngredientModelImpl();
        ingredient1.setId("ingredient1");
        ingredient1.setName("Ingredient 1");
        ingredient1.setUnit("cup");
        service.create(ingredient1);

        var ingredient2 = new IngredientModelImpl();
        ingredient2.setId("ingredient2");
        ingredient2.setName("Ingredient 2");
        ingredient2.setUnit("gram");
        service.create(ingredient2);

        var ingredients = service.getAll();
        assertNotNull(ingredients);
        assertEquals(2, ingredients.size());
    }

    @Test
    void testDeleteIngredient() {
        var ingredient = new IngredientModelImpl();
        ingredient.setId("test-ingredient");
        ingredient.setName("Test Ingredient");
        ingredient.setUnit("piece");
        service.create(ingredient);

        service.delete("test-ingredient");
        var retrieved = service.getById("test-ingredient");
        assertNull(retrieved);
    }
}
