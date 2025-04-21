package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import models.impl.*;
import models.helpers.TestModelHelper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestRecipeModelImpl {
    private RecipeModelImpl recipe;
    private UserModelImpl user;
    private CategoryModelImpl category;
    private IngredientModelImpl ingredient;
    private InstructionModelImpl instruction;

    @BeforeEach
    void setup() {
        user = TestModelHelper.createUser();
        category = TestModelHelper.createCategory();
        ingredient = TestModelHelper.createIngredient();
        instruction = TestModelHelper.createInstruction();
        recipe = TestModelHelper.createRecipe(user, category);
    }

    @AfterEach
    void cleanup() {
        recipe = null;
        user = null;
        category = null;
        ingredient = null;
        instruction = null;
    }

    @Test
    void testConstructor() {
        assertNotNull(recipe);
        assertEquals(user.getId(), recipe.getUserId());
        assertEquals("Test Recipe", recipe.getTitle());
        assertEquals("Test recipe description", recipe.getDescription());
        assertNotNull(recipe.getCreatedAt());
        assertEquals(category, recipe.getCategory());
        assertEquals(1, recipe.getIngredients().size());
        assertEquals(1, recipe.getInstructions().size());
    }

    @Test
    void testSetters() {
        recipe.setUserId("new-user-id");
        recipe.setTitle("New Recipe");
        recipe.setDescription("New recipe description");
        recipe.setCategory(category);
        recipe.setIngredients(List.of(TestModelHelper.createRecipeIngredient(recipe, ingredient)));
        recipe.setInstructions(List.of(instruction));

        assertEquals("new-user-id", recipe.getUserId());
        assertEquals("New Recipe", recipe.getTitle());
        assertEquals("New recipe description", recipe.getDescription());
        assertEquals(category, recipe.getCategory());
        assertEquals(1, recipe.getIngredients().size());
        assertEquals(1, recipe.getInstructions().size());
    }

    @Test
    void testGetters() {
        assertEquals(user.getId(), recipe.getUserId());
        assertEquals("Test Recipe", recipe.getTitle());
        assertEquals("Test recipe description", recipe.getDescription());
        assertNotNull(recipe.getCreatedAt());
        assertEquals(category, recipe.getCategory());
        assertEquals(1, recipe.getIngredients().size());
        assertEquals(1, recipe.getInstructions().size());
    }

    @Test
    void testToString() {
        String expected = String.format(
            "RecipeModelImpl{id=%s, userId=%s, title=%s, description=%s, createdAt=%s, category=%s}",
            recipe.getId(),
            user.getId(),
            "Test Recipe",
            "Test recipe description",
            recipe.getCreatedAt(),
            category
        );
        assertEquals(expected, recipe.toString());
    }
}
