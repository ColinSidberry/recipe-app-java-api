package daos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import models.impl.IngredientModelImpl;
import daos.helpers.TestMongoRepositoryHelper;
import daos.impl.MongoIngredientRepositoryImpl;
import daos.interfaces.BaseRepository;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TestMongoIngredientRepositoryImpl {
    private BaseRepository<IngredientModelImpl> mongoRepo;

    @BeforeEach
    void setup() {
        mongoRepo = new MongoIngredientRepositoryImpl(TestMongoRepositoryHelper.getDatabase());
        TestMongoRepositoryHelper.clearCollection("ingredients");
    }

    @AfterEach
    void cleanup() {
        TestMongoRepositoryHelper.clearCollection("ingredients");
    }

    @Test
    void testSave() {
        IngredientModelImpl ingredient = new IngredientModelImpl("Flour", "cup");

        IngredientModelImpl saved = mongoRepo.save(ingredient);

        assertNotNull(saved.getId());
        assertEquals("Flour", saved.getName());
        assertEquals("cup", saved.getUnit());
    }

    @Test
    void testFindById_found() {
        IngredientModelImpl ingredient = new IngredientModelImpl("Flour", "cup");
        IngredientModelImpl saved = mongoRepo.save(ingredient);

        Optional<IngredientModelImpl> result = mongoRepo.findById(saved.getId());

        assertTrue(result.isPresent());
        assertEquals("Flour", result.get().getName());
        assertEquals("cup", result.get().getUnit());
    }

    @Test
    void testFindById_notFound() {
        // Generate a random ObjectId that doesn't exist in the database
        String nonExistentId = new ObjectId().toString();
        Optional<IngredientModelImpl> result = mongoRepo.findById(nonExistentId);
        assertFalse(result.isPresent());
    }

    @Test
    void testFindAll() {
        IngredientModelImpl ingredient1 = new IngredientModelImpl("Flour", "cup");
        mongoRepo.save(ingredient1);

        IngredientModelImpl ingredient2 = new IngredientModelImpl("Sugar", "teaspoon");
        mongoRepo.save(ingredient2);

        List<IngredientModelImpl> ingredients = mongoRepo.findAll();
        assertEquals(2, ingredients.size());
    }

    @Test
    void testDelete() {
        IngredientModelImpl ingredient = new IngredientModelImpl("Flour", "cup");
        IngredientModelImpl saved = mongoRepo.save(ingredient);

        mongoRepo.delete(saved.getId());

        Optional<IngredientModelImpl> result = mongoRepo.findById(saved.getId());
        assertFalse(result.isPresent());
    }
}
