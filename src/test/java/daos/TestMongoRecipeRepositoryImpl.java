package daos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import models.impl.RecipeModelImpl;
import daos.helpers.TestMongoRepositoryHelper;
import daos.impl.MongoRecipeRepositoryImpl;
import daos.interfaces.BaseRepository;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TestMongoRecipeRepositoryImpl {
    private BaseRepository<RecipeModelImpl> mongoRepo;

    @BeforeEach
    void setup() {
        mongoRepo = new MongoRecipeRepositoryImpl(TestMongoRepositoryHelper.getDatabase());
        TestMongoRepositoryHelper.clearCollection("recipes");
    }

    @AfterEach
    void cleanup() {
        TestMongoRepositoryHelper.clearCollection("recipes");
    }

    @Test
    void testSave() {
        RecipeModelImpl recipe = new RecipeModelImpl();
        recipe.setUserId("test-user");
        recipe.setTitle("Test Recipe");
        recipe.setDescription("Test description");
        recipe.setCreatedAt(LocalDateTime.now());
        recipe.setIngredients(List.of());
        recipe.setInstructions(List.of());

        RecipeModelImpl result = mongoRepo.save(recipe);

        assertNotNull(result.getId());
        assertEquals("test-user", result.getUserId());
        assertEquals("Test Recipe", result.getTitle());
        assertEquals("Test description", result.getDescription());
    }

    @Test
    void testFindById_found() {
        RecipeModelImpl recipe = new RecipeModelImpl();
        recipe.setUserId("test-user");
        recipe.setTitle("Test Recipe");
        recipe.setDescription("Test description");
        recipe.setCreatedAt(LocalDateTime.now());
        RecipeModelImpl saved = mongoRepo.save(recipe);

        Optional<RecipeModelImpl> result = mongoRepo.findById(saved.getId());

        assertTrue(result.isPresent());
        assertEquals(saved.getUserId(), result.get().getUserId());
        assertEquals(saved.getTitle(), result.get().getTitle());
        assertEquals(saved.getDescription(), result.get().getDescription());
    }

    @Test
    void testFindById_notFound() {
        // Generate a random ObjectId that doesn't exist in the database
        String nonExistentId = new org.bson.types.ObjectId().toString();
        Optional<RecipeModelImpl> result = mongoRepo.findById(nonExistentId);
        assertFalse(result.isPresent());
    }

    @Test
    void testFindAll() {
        RecipeModelImpl recipe1 = new RecipeModelImpl();
        recipe1.setUserId("test-user");
        recipe1.setTitle("Recipe 1");
        recipe1.setDescription("Description 1");
        recipe1.setCreatedAt(LocalDateTime.now());
        mongoRepo.save(recipe1);

        RecipeModelImpl recipe2 = new RecipeModelImpl();
        recipe2.setUserId("test-user");
        recipe2.setTitle("Recipe 2");
        recipe2.setDescription("Description 2");
        recipe2.setCreatedAt(LocalDateTime.now());
        mongoRepo.save(recipe2);

        List<RecipeModelImpl> recipes = mongoRepo.findAll();
        assertEquals(2, recipes.size());
    }

    @Test
    void testDelete() {
        RecipeModelImpl recipe = new RecipeModelImpl();
        recipe.setUserId("test-user");
        recipe.setTitle("Test Recipe");
        recipe.setDescription("Test description");
        recipe.setCreatedAt(LocalDateTime.now());
        RecipeModelImpl saved = mongoRepo.save(recipe);

        mongoRepo.delete(saved.getId());

        Optional<RecipeModelImpl> result = mongoRepo.findById(saved.getId());
        assertFalse(result.isPresent());
    }
}
