package controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controllers.helpers.TestInstructionService;
import controllers.helpers.TestRecipeIngredientService;
import controllers.helpers.TestRecipeService;
import models.impl.InstructionModelImpl;
import models.impl.RecipeModelImpl;
import models.impl.RecipeIngredientModelImpl;
import services.interfaces.BaseService;
import services.interfaces.InstructionService;
import services.interfaces.RecipeIngredientService;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class TestRecipeController {

    private TestRecipeService testRecipeService;
    private TestInstructionService testInstructionService;
    private TestRecipeIngredientService testRecipeIngredientService;
    private RecipeController controller;

    @BeforeEach
    void setup() {
        testRecipeService = new TestRecipeService();
        testInstructionService = new TestInstructionService();
        testRecipeIngredientService = new TestRecipeIngredientService();
        controller = new RecipeController(
            (BaseService<RecipeModelImpl>) testRecipeService,
            (InstructionService) testInstructionService,
            (RecipeIngredientService) testRecipeIngredientService
        );
    }

    @Test
    void testCreateRecipe() {
        RecipeModelImpl recipe = new RecipeModelImpl();
        recipe.setUserId("test-user");
        recipe.setTitle("Test Recipe");
        recipe.setDescription("Test description");

        RecipeModelImpl result = controller.createRecipe(recipe);

        assertNotNull(result.getId());
        assertEquals("test-user", result.getUserId());
        assertEquals("Test Recipe", result.getTitle());
        assertEquals("Test description", result.getDescription());
        assertNotNull(result.getCreatedAt());
    }

    @Test
    void testGetAllRecipes() {
        RecipeModelImpl recipe = new RecipeModelImpl();
        recipe.setId("r1");

        testRecipeService.create(recipe);
        
        InstructionModelImpl instruction = new InstructionModelImpl();
        instruction.setRecipeId(recipe.getId());
        testInstructionService.create(instruction);
        
        RecipeIngredientModelImpl ingredient = new RecipeIngredientModelImpl();
        ingredient.setRecipeId(recipe.getId());
        testRecipeIngredientService.create(ingredient);

        List<RecipeModelImpl> recipes = controller.getAllRecipes();

        assertEquals(1, recipes.size());
        assertEquals("r1", recipes.get(0).getId());
        assertFalse(recipes.get(0).getInstructions().isEmpty());
        assertFalse(recipes.get(0).getIngredients().isEmpty());
    }

    @Test
    void testGetRecipeById_found() {
        RecipeModelImpl recipe = new RecipeModelImpl();
        recipe.setId("r1");

        testRecipeService.create(recipe);
        
        InstructionModelImpl instruction = new InstructionModelImpl();
        instruction.setRecipeId(recipe.getId());
        testInstructionService.create(instruction);
        
        RecipeIngredientModelImpl ingredient = new RecipeIngredientModelImpl();
        ingredient.setRecipeId(recipe.getId());
        testRecipeIngredientService.create(ingredient);

        RecipeModelImpl result = controller.getRecipeById("r1");

        assertNotNull(result);
        assertEquals("r1", result.getId());
    }

    @Test
    void testGetRecipeById_notFound() {
        RecipeModelImpl result = controller.getRecipeById("nonexistent");
        assertNull(result);
    }

    @Test
    void testDeleteRecipe() {
        RecipeModelImpl recipe = new RecipeModelImpl();
        recipe.setId("test-recipe-id");
        recipe.setUserId("test-user");
        recipe.setTitle("Test Recipe");
        
        InstructionModelImpl instruction = new InstructionModelImpl();
        instruction.setRecipeId(recipe.getId());
        
        RecipeIngredientModelImpl ingredient = new RecipeIngredientModelImpl();
        ingredient.setRecipeId(recipe.getId());

        testRecipeService.create(recipe);
        testInstructionService.create(instruction);
        testRecipeIngredientService.create(ingredient);

        controller.deleteRecipe(recipe.getId());

        assertNull(testRecipeService.getById(recipe.getId()));
        assertTrue(testInstructionService.getInstructionsByRecipe(recipe.getId()).isEmpty());
        assertTrue(testRecipeIngredientService.getIngredientsByRecipe(recipe.getId()).isEmpty());
        assertNull(testRecipeService.getById(recipe.getId()));
        assertTrue(testInstructionService.getInstructionsByRecipe(recipe.getId()).isEmpty());
        assertTrue(testRecipeIngredientService.getIngredientsByRecipe(recipe.getId()).isEmpty());
    }

    @Test
    void testGetRecipesByUser() {
        // First check with no recipes
        List<RecipeModelImpl> allRecipes = controller.getAllRecipes();
        List<RecipeModelImpl> userRecipes = allRecipes.stream()
            .filter(r -> "u1".equals(r.getUserId()))
            .collect(Collectors.toList());
        assertEquals(0, userRecipes.size());

        // Create a recipe for user u1
        RecipeModelImpl recipe = new RecipeModelImpl();
        recipe.setUserId("u1");
        recipe.setTitle("Test Recipe");
        controller.createRecipe(recipe);

        // Check again
        allRecipes = controller.getAllRecipes();
        userRecipes = allRecipes.stream()
            .filter(r -> "u1".equals(r.getUserId()))
            .collect(Collectors.toList());
        assertEquals(1, userRecipes.size());
    }
}
