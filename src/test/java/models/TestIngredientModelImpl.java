package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import models.impl.IngredientModelImpl;
import models.helpers.TestModelHelper;

import static org.junit.jupiter.api.Assertions.*;

class TestIngredientModelImpl {
    private IngredientModelImpl ingredient;

    @BeforeEach
    void setup() {
        ingredient = TestModelHelper.createIngredient();
    }

    @AfterEach
    void cleanup() {
        ingredient = null;
    }

    @Test
    void testConstructor() {
        assertNotNull(ingredient);
        assertEquals("Test Ingredient", ingredient.getName());
        assertEquals("cup", ingredient.getUnit());
    }

    @Test
    void testSetters() {
        ingredient.setName("New Ingredient");
        ingredient.setUnit("tablespoon");

        assertEquals("New Ingredient", ingredient.getName());
        assertEquals("tablespoon", ingredient.getUnit());
    }

    @Test
    void testGetters() {
        assertEquals("Test Ingredient", ingredient.getName());
        assertEquals("cup", ingredient.getUnit());
    }
}
