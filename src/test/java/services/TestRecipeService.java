package services;

import models.impl.CategoryModelImpl;
import models.impl.IngredientModelImpl;
import models.impl.RecipeIngredientModelImpl;
import models.impl.InstructionModelImpl;
import models.impl.RecipeModelImpl;
import models.impl.UserModelImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.interfaces.RecipeService;
import services.interfaces.UserService;
import services.interfaces.CategoryService;
import services.interfaces.IngredientService;
import services.helpers.TestRecipeRepository;
import services.helpers.TestUserRepository;
import services.helpers.TestCategoryRepository;
import services.helpers.TestIngredientRepository;
import services.impl.RecipeServiceImpl;
import services.impl.UserServiceImpl;
import services.impl.CategoryServiceImpl;
import services.impl.IngredientServiceImpl;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestRecipeService {
    private final TestRecipeRepository recipeRepository = new TestRecipeRepository();
    private final TestUserRepository userRepository = new TestUserRepository();
    private final TestCategoryRepository categoryRepository = new TestCategoryRepository();
    private final TestIngredientRepository ingredientRepository = new TestIngredientRepository();
    private RecipeService service;
    private UserService userService;
    private CategoryService categoryService;
    private IngredientService ingredientService;

    @BeforeEach
    public void setUp() {
        userService = new UserServiceImpl(userRepository);
        categoryService = new CategoryServiceImpl(categoryRepository);
        ingredientService = new IngredientServiceImpl(ingredientRepository);
        service = new RecipeServiceImpl(recipeRepository, userService, categoryService, ingredientService);
        
        recipeRepository.clear();
        userRepository.clear();
        categoryRepository.clear();
        ingredientRepository.clear();
    }

    @AfterEach
    public void tearDown() {
        recipeRepository.clear();
        userRepository.clear();
        categoryRepository.clear();
        ingredientRepository.clear();
    }

    @Test
    void testCreateRecipe() {
        var user = new UserModelImpl();
        user.setId("test-user");
        
        var createdUser = userService.create(user);

        var category = new CategoryModelImpl();
        category.setId("test-category");
        
        var createdCategory = categoryService.create(category);
        
        var ingredient = new IngredientModelImpl();
        ingredient.setId("test-ingredient");
        
        var createdIngredient = ingredientService.create(ingredient);
        
        var recipeIngredient = new RecipeIngredientModelImpl();
        recipeIngredient.setRecipeId("test-recipe");
        recipeIngredient.setIngredientId(createdIngredient.getId());
        recipeIngredient.setQuantity(1.0);
        recipeIngredient.setUnit("cup");
        
        var instruction = new InstructionModelImpl();
        instruction.setRecipeId("test-recipe");
        instruction.setStepNumber(1);
        instruction.setDescription("Test instruction");
        
        var recipe = new RecipeModelImpl();
        recipe.setId("test-recipe");
        recipe.setUserId(createdUser.getId());
        recipe.setCategoryId(createdCategory.getId());
        recipe.setTitle("Test Recipe");
        recipe.setDescription("Test recipe description");
        recipe.setCreatedAt(LocalDateTime.now());
        recipe.setIngredients(List.of(recipeIngredient));
        recipe.setInstructions(List.of(instruction));
        recipe.setImageUrl("test-image.jpg");
        recipe.setCategoryId(createdCategory.getId());
        recipe.setPrepTime(30);
        recipe.setCookTime(60);
        recipe.setServings(4);
        recipe.setDifficulty("medium");

        var created = service.create(recipe);
        assertNotNull(created);
        assertNotNull(created.getId());
        assertEquals("Test Recipe", created.getTitle());
        assertEquals("Test recipe description", created.getDescription());
        assertEquals(30, created.getPrepTime());
        assertEquals(60, created.getCookTime());
        assertEquals(4, created.getServings());
        assertEquals("medium", created.getDifficulty());
    }

    @Test
    void testGetRecipe() {
        var user = new UserModelImpl();
        user.setId("test-user");
        
        var createdUser = userService.create(user);

        var category = new CategoryModelImpl();
        category.setId("test-category");
        
        var createdCategory = categoryService.create(category);
        
        var ingredient = new IngredientModelImpl();
        ingredient.setId("test-ingredient");
        
        var createdIngredient = ingredientService.create(ingredient);
        
        var recipeIngredient = new RecipeIngredientModelImpl();
        recipeIngredient.setRecipeId("test-recipe");
        recipeIngredient.setIngredientId(createdIngredient.getId());
        recipeIngredient.setQuantity(1.0);
        recipeIngredient.setUnit("cup");
        
        var instruction = new InstructionModelImpl();
        instruction.setRecipeId("test-recipe");
        instruction.setStepNumber(1);
        instruction.setDescription("Test instruction");
        
        var recipe = new RecipeModelImpl();
        recipe.setId("test-recipe");
        recipe.setUserId(createdUser.getId());
        recipe.setCategoryId(createdCategory.getId());
        recipe.setTitle("Test Recipe");
        recipe.setDescription("Test recipe description");
        recipe.setCreatedAt(LocalDateTime.now());
        recipe.setIngredients(List.of(recipeIngredient));
        recipe.setInstructions(List.of(instruction));
        recipe.setImageUrl("test-image.jpg");
        recipe.setCategoryId(createdCategory.getId());
        recipe.setPrepTime(30);
        recipe.setCookTime(60);
        recipe.setServings(4);
        recipe.setDifficulty("medium");
        
        service.create(recipe);

        var retrieved = service.getById("test-recipe");
        assertNotNull(retrieved);
        assertEquals("Test Recipe", retrieved.getTitle());
        assertEquals("Test recipe description", retrieved.getDescription());
        assertEquals(30, retrieved.getPrepTime());
        assertEquals(60, retrieved.getCookTime());
        assertEquals(4, retrieved.getServings());
        assertEquals("medium", retrieved.getDifficulty());
    }

    @Test
    void testGetAllRecipes() {
        var user = new UserModelImpl();
        user.setId("test-user");
        
        var createdUser = userService.create(user);

        var category = new CategoryModelImpl();
        category.setId("test-category");
        
        var createdCategory = categoryService.create(category);
        
        var recipe1 = new RecipeModelImpl();
        recipe1.setId("recipe1");
        recipe1.setUserId(createdUser.getId());
        recipe1.setCategoryId(createdCategory.getId());
        recipe1.setTitle("Recipe 1");
        recipe1.setDescription("Recipe 1 description");
        recipe1.setCreatedAt(LocalDateTime.now());
        recipe1.setCategory(createdCategory);
        recipe1.setPrepTime(15);
        recipe1.setCookTime(30);
        recipe1.setServings(2);
        recipe1.setDifficulty("easy");
        
        service.create(recipe1);

        var recipe2 = new RecipeModelImpl();
        recipe2.setId("recipe2");
        recipe2.setUserId(createdUser.getId());
        recipe2.setCategoryId(createdCategory.getId());
        recipe2.setTitle("Recipe 2");
        recipe2.setDescription("Recipe 2 description");
        recipe2.setCreatedAt(LocalDateTime.now());
        recipe2.setCategory(createdCategory);
        recipe2.setPrepTime(45);
        recipe2.setCookTime(90);
        recipe2.setServings(6);
        recipe2.setDifficulty("hard");
        
        service.create(recipe2);

        var recipes = service.getAll();
        assertNotNull(recipes);
        assertEquals(2, recipes.size());
    }

    @Test
    void testDeleteRecipe() {
        var user = new UserModelImpl();
        user.setUsername("testuser");
        user.setName("Test User");
        user.setEmail("test@example.com");
        var createdUser = userService.create(user);

        var category = new CategoryModelImpl();
        category.setName("Test Category");
        var createdCategory = categoryService.create(category);

        var recipe = new RecipeModelImpl();
        recipe.setUserId(createdUser.getId());
        recipe.setCategoryId(createdCategory.getId());
        recipe.setTitle("Test Recipe");
        recipe.setCreatedAt(LocalDateTime.now());
        var createdRecipe = service.create(recipe);

        var retrieved = service.getById(createdRecipe.getId());
        assertNotNull(retrieved);
        assertEquals("Test Recipe", retrieved.getTitle());
        assertEquals(createdUser.getId(), retrieved.getUserId());
        assertEquals(createdCategory.getId(), retrieved.getCategoryId());

        service.delete(createdRecipe.getId());
        retrieved = service.getById(createdRecipe.getId());
        assertNull(retrieved);
    }

    @Test
    void testGetRecipesByCategoryId() {
        var user = new UserModelImpl();
        user.setId("test-user");
        
        var category = new CategoryModelImpl();
        category.setId("test-category");
        
        var recipe1 = new RecipeModelImpl();
        recipe1.setUserId(user.getId());
        recipe1.setCategoryId(category.getId());
        recipe1.setTitle("Recipe 1");
        recipe1.setCreatedAt(LocalDateTime.now());
        service.create(recipe1);

        var recipe2 = new RecipeModelImpl();
        recipe2.setUserId(user.getId());
        recipe2.setCategoryId(category.getId());
        recipe2.setTitle("Recipe 2");
        recipe2.setCreatedAt(LocalDateTime.now());
        service.create(recipe2);

        var recipes = service.getAll();
        assertNotNull(recipes);
        assertEquals(2, recipes.size());
        assertTrue(recipes.stream().anyMatch(r -> r.getTitle().equals("Recipe 1")));
        assertTrue(recipes.stream().anyMatch(r -> r.getTitle().equals("Recipe 2")));
        assertEquals(2, recipes.stream().filter(r -> r.getUserId().equals(user.getId())).count());
        assertEquals(2, recipes.stream().filter(r -> r.getCategoryId().equals(category.getId())).count());
    }
}
