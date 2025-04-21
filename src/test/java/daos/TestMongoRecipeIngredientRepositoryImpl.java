package daos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import daos.helpers.TestMongoRepositoryHelper;
import daos.impl.MongoRecipeIngredientRepositoryImpl;

import org.junit.jupiter.api.AfterEach;

import models.impl.RecipeIngredientModelImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TestMongoRecipeIngredientRepositoryImpl {
    private daos.interfaces.RecipeIngredientRepository mongoRepo;

    @BeforeEach
    void setup() {
        mongoRepo = new MongoRecipeIngredientRepositoryImpl(TestMongoRepositoryHelper.getDatabase());
        TestMongoRepositoryHelper.clearCollection("recipe_ingredients");
    }

    @AfterEach
    void cleanup() {
        TestMongoRepositoryHelper.clearCollection("recipe_ingredients");
    }

    @Test
    void testSave() {
        RecipeIngredientModelImpl ingredient = new RecipeIngredientModelImpl();
        ingredient.setRecipeId("test-recipe");
        ingredient.setIngredientId("test-ingredient");
        ingredient.setQuantity(1.0);
        ingredient.setUnit("cup");

        RecipeIngredientModelImpl saved = mongoRepo.save(ingredient);

        assertNotNull(saved.getId());
        assertEquals("test-recipe", saved.getRecipeId());
        assertEquals("test-ingredient", saved.getIngredientId());
        assertEquals(1.0, saved.getQuantity());
        assertEquals("cup", saved.getUnit());
    }

    @Test
    void testFindById_found() {
        RecipeIngredientModelImpl ingredient = new RecipeIngredientModelImpl();
        ingredient.setRecipeId("test-recipe");
        ingredient.setIngredientId("test-ingredient");
        ingredient.setQuantity(1.0);
        ingredient.setUnit("cup");
        RecipeIngredientModelImpl saved = mongoRepo.save(ingredient);

        Optional<RecipeIngredientModelImpl> result = mongoRepo.findById(saved.getId());

        assertTrue(result.isPresent());
        assertEquals("test-recipe", result.get().getRecipeId());
        assertEquals("test-ingredient", result.get().getIngredientId());
        assertEquals(1.0, result.get().getQuantity());
        assertEquals("cup", result.get().getUnit());
    }

    @Test
    void testFindById_notFound() {
        // Generate a random ObjectId that doesn't exist in the database
        String nonExistentId = new org.bson.types.ObjectId().toString();
        Optional<RecipeIngredientModelImpl> result = mongoRepo.findById(nonExistentId);
        assertFalse(result.isPresent());
    }

    @Test
    void testFindAll() {
        RecipeIngredientModelImpl ingredient1 = new RecipeIngredientModelImpl();
        ingredient1.setRecipeId("recipe-1");
        ingredient1.setIngredientId("ingredient-1");
        ingredient1.setQuantity(1.0);
        ingredient1.setUnit("cup");
        mongoRepo.save(ingredient1);

        RecipeIngredientModelImpl ingredient2 = new RecipeIngredientModelImpl();
        ingredient2.setRecipeId("recipe-2");
        ingredient2.setIngredientId("ingredient-2");
        ingredient2.setQuantity(2.0);
        ingredient2.setUnit("teaspoon");
        mongoRepo.save(ingredient2);

        List<RecipeIngredientModelImpl> ingredients = mongoRepo.findAll();
        assertEquals(2, ingredients.size());
    }

    @Test
    void testFindByRecipeId() {
        RecipeIngredientModelImpl ingredient1 = new RecipeIngredientModelImpl();
        ingredient1.setRecipeId("test-recipe");
        ingredient1.setIngredientId("ingredient-1");
        ingredient1.setQuantity(1.0);
        ingredient1.setUnit("cup");
        mongoRepo.save(ingredient1);

        RecipeIngredientModelImpl ingredient2 = new RecipeIngredientModelImpl();
        ingredient2.setRecipeId("test-recipe");
        ingredient2.setIngredientId("ingredient-2");
        ingredient2.setQuantity(2.0);
        ingredient2.setUnit("teaspoon");
        mongoRepo.save(ingredient2);

        List<RecipeIngredientModelImpl> ingredients = mongoRepo.findByRecipeId("test-recipe");
        assertEquals(2, ingredients.size());
        assertEquals("ingredient-1", ingredients.get(0).getIngredientId());
        assertEquals(1.0, ingredients.get(0).getQuantity());
        assertEquals("ingredient-2", ingredients.get(1).getIngredientId());
        assertEquals(2.0, ingredients.get(1).getQuantity());
    }

    @Test
    void testFindByRecipeId_notFound() {
        List<RecipeIngredientModelImpl> ingredients = mongoRepo.findByRecipeId("nonexistent");
        assertTrue(ingredients.isEmpty());
    }

    @Test
    void testDeleteByRecipeId() {
        RecipeIngredientModelImpl ingredient1 = new RecipeIngredientModelImpl();
        ingredient1.setRecipeId("test-recipe");
        ingredient1.setIngredientId("ingredient-1");
        ingredient1.setQuantity(1.0);
        ingredient1.setUnit("cup");
        mongoRepo.save(ingredient1);

        RecipeIngredientModelImpl ingredient2 = new RecipeIngredientModelImpl();
        ingredient2.setRecipeId("test-recipe");
        ingredient2.setIngredientId("ingredient-2");
        ingredient2.setQuantity(2.0);
        ingredient2.setUnit("teaspoon");
        mongoRepo.save(ingredient2);

        mongoRepo.deleteByRecipeId("test-recipe");

        List<RecipeIngredientModelImpl> ingredients = mongoRepo.findByRecipeId("test-recipe");
        assertTrue(ingredients.isEmpty());
    }
}
