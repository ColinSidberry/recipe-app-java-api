package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import models.impl.*;
import models.helpers.TestModelHelper;

import static org.junit.jupiter.api.Assertions.*;

class TestRecipeIngredientModelImpl {
    private RecipeIngredientModelImpl recipeIngredient;
    private RecipeModelImpl recipe;
    private IngredientModelImpl ingredient;

    @BeforeEach
    void setup() {
        ingredient = TestModelHelper.createIngredient();
        recipe = TestModelHelper.createRecipe(TestModelHelper.createUser(), TestModelHelper.createCategory());
        recipeIngredient = TestModelHelper.createRecipeIngredient(recipe, ingredient);
    }

    @AfterEach
    void cleanup() {
        recipeIngredient = null;
        recipe = null;
        ingredient = null;
    }

    @Test
    void testConstructor() {
        assertNotNull(recipeIngredient);
        assertEquals(recipe.getId(), recipeIngredient.getRecipeId());
        assertEquals(ingredient.getId(), recipeIngredient.getIngredientId());
        assertEquals(1.0, recipeIngredient.getQuantity());
        assertEquals("cup", recipeIngredient.getUnit());
    }

    @Test
    void testSetters() {
        recipeIngredient.setRecipeId("new-recipe-id");
        recipeIngredient.setIngredientId("new-ingredient-id");
        recipeIngredient.setQuantity(2.0);
        recipeIngredient.setUnit("tablespoon");

        assertEquals("new-recipe-id", recipeIngredient.getRecipeId());
        assertEquals("new-ingredient-id", recipeIngredient.getIngredientId());
        assertEquals(2.0, recipeIngredient.getQuantity());
        assertEquals("tablespoon", recipeIngredient.getUnit());
    }

    @Test
    void testGetters() {
        assertEquals(recipe.getId(), recipeIngredient.getRecipeId());
        assertEquals(ingredient.getId(), recipeIngredient.getIngredientId());
        assertEquals(1.0, recipeIngredient.getQuantity());
        assertEquals("cup", recipeIngredient.getUnit());
    }
}
