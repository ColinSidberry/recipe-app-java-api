package daos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import models.impl.CategoryModelImpl;
import daos.helpers.TestMongoRepositoryHelper;
import daos.impl.MongoCategoryRepositoryImpl;
import daos.interfaces.BaseRepository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;

import static org.junit.jupiter.api.Assertions.*;

class TestMongoCategoryRepositoryImpl {

  private BaseRepository<CategoryModelImpl> mongoRepo;

  @BeforeEach
  void setup() {
    mongoRepo = new MongoCategoryRepositoryImpl(TestMongoRepositoryHelper.getDatabase());
    TestMongoRepositoryHelper.clearCollection("categories");
  }

  @AfterEach
  void cleanup() {
    TestMongoRepositoryHelper.clearCollection("categories");
  }

  @Test
  void testSave() {
    CategoryModelImpl category = new CategoryModelImpl("Test Category", "Test description");
    CategoryModelImpl result = mongoRepo.save(category);

    assertNotNull(result.getId());
    assertEquals("Test Category", result.getName());
    assertEquals("Test description", result.getDescription());
  }

  @Test
  void testFindById_found() {
    CategoryModelImpl category = new CategoryModelImpl("Test Category", "Test description");
    CategoryModelImpl saved = mongoRepo.save(category);

    Optional<CategoryModelImpl> result = mongoRepo.findById(saved.getId());

    assertTrue(result.isPresent());
    assertEquals("Test Category", result.get().getName());
    assertEquals("Test description", result.get().getDescription());
  }

  @Test
  void testFindById_notFound() {
    // Generate a random ObjectId that doesn't exist in the database
    String nonExistentId = new ObjectId().toString();
    Optional<CategoryModelImpl> result = mongoRepo.findById(nonExistentId);
    assertFalse(result.isPresent());
  }

  @Test
  void testFindAll() {
    CategoryModelImpl category1 = new CategoryModelImpl("Category 1", "Description 1");
    mongoRepo.save(category1);

    CategoryModelImpl category2 = new CategoryModelImpl("Category 2", "Description 2");
    mongoRepo.save(category2);

    List<CategoryModelImpl> categories = mongoRepo.findAll();
    assertEquals(2, categories.size());
  }

  @Test
  void testDelete() {
    CategoryModelImpl category = new CategoryModelImpl("Test Category", "Test description");
    CategoryModelImpl saved = mongoRepo.save(category);

    mongoRepo.delete(saved.getId());

    Optional<CategoryModelImpl> result = mongoRepo.findById(saved.getId());
    assertFalse(result.isPresent());
  }
}
