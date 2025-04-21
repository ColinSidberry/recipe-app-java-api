package models.helpers;

import org.bson.types.ObjectId;
import models.impl.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestModelHelper {
    
    public static UserModelImpl createUser() {
        return new UserModelImpl(
            "test-user",
            "Test User",
            "test@example.com",
            "password123"
        );
    }

    public static CategoryModelImpl createCategory() {
        return new CategoryModelImpl(
            "test-category",
            "Test Category",
            "Test category description"
        );
    }

    public static IngredientModelImpl createIngredient() {
        return new IngredientModelImpl(
            "Test Ingredient",
            "cup"
        );
    }

    public static InstructionModelImpl createInstruction() {
        return new InstructionModelImpl(
            null,
            "test-recipe",
            1,
            "Test instruction description"
        );
    }

    public static RecipeModelImpl createRecipe(UserModelImpl user, CategoryModelImpl category) {
        List<RecipeIngredientModelImpl> ingredients = new ArrayList<>();
        ingredients.add(createRecipeIngredient(null, createIngredient()));
        
        List<InstructionModelImpl> instructions = new ArrayList<>();
        instructions.add(createInstruction());
        
        return new RecipeModelImpl(
            null,
            user.getId(),
            category.getId(),
            "Test Recipe",
            "Test recipe description",
            LocalDateTime.now(),
            ingredients,
            instructions,
            null,
            category,
            30,
            45,
            4,
            "medium"
        );
    }

    public static RecipeIngredientModelImpl createRecipeIngredient(RecipeModelImpl recipe, IngredientModelImpl ingredient) {
        return new RecipeIngredientModelImpl(
            null,
            recipe != null ? recipe.getId() : "test-recipe",
            ingredient.getId(),
            1.0,
            "cup"
        );
    }

    public static ObjectId generateValidObjectId() {
        return new ObjectId();
    }
}
