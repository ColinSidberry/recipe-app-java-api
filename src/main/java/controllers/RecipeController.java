package controllers;

import services.interfaces.BaseService;
import services.interfaces.InstructionService;
import services.interfaces.RecipeIngredientService;
import java.time.LocalDateTime;
import java.util.List;

import models.impl.InstructionModelImpl;
import models.impl.RecipeModelImpl;
import models.impl.RecipeIngredientModelImpl;

/**
 * Controller for managing recipe-related operations.
 */
public class RecipeController {
    private final BaseService<RecipeModelImpl> recipeService;
    private final InstructionService instructionService;
    private final RecipeIngredientService recipeIngredientService;

    public RecipeController(
            BaseService<RecipeModelImpl> recipeService,
            InstructionService instructionService,
            RecipeIngredientService recipeIngredientService) {
        this.recipeService = recipeService;
        this.instructionService = instructionService;
        this.recipeIngredientService = recipeIngredientService;
    }

    /**
     * Create a new recipe with all its components.
     */
    public RecipeModelImpl createRecipe(RecipeModelImpl recipe) {
        // Set creation time
        recipe.setCreatedAt(LocalDateTime.now());

        // Save the recipe first
        RecipeModelImpl savedRecipe = recipeService.create(recipe);
        String recipeId = savedRecipe.getId();

        // Save instructions with recipe ID
        if (recipe.getInstructions() != null) {
            for (InstructionModelImpl instruction : recipe.getInstructions()) {
                instruction.setRecipeId(recipeId);
                instructionService.create(instruction);
            }
        }

        // Save recipe ingredients
        if (recipe.getIngredients() != null) {
            for (RecipeIngredientModelImpl recipeIngredient : recipe.getIngredients()) {
                recipeIngredientService.create(recipeIngredient);
            }
        }

        return savedRecipe;
    }

    /**
     * Get all recipes.
     */
    public List<RecipeModelImpl> getAllRecipes() {
        List<RecipeModelImpl> recipes = recipeService.getAll();

        // Load ingredients and instructions for each recipe
        for (RecipeModelImpl recipe : recipes) {
            // Load instructions
            List<InstructionModelImpl> instructions = instructionService.getInstructionsByRecipe(recipe.getId());
            recipe.setInstructions(instructions);

            // Load ingredients
            List<RecipeIngredientModelImpl> ingredients = recipeIngredientService.getIngredientsByRecipe(recipe.getId());
            recipe.setIngredients(ingredients);
        }

        return recipes;
    }

    /**
     * Get a recipe by ID with all its components.
     */
    public RecipeModelImpl getRecipeById(String id) {
        RecipeModelImpl recipe = recipeService.getById(id);
        if (recipe == null) {
            return null;
        }

        // Load instructions
        List<InstructionModelImpl> instructions = instructionService.getInstructionsByRecipe(id);
        recipe.setInstructions(instructions);

        // Load ingredients
        List<RecipeIngredientModelImpl> ingredients = recipeIngredientService.getIngredientsByRecipe(id);
        recipe.setIngredients(ingredients);

        return recipe;
    }

    /**
     * Delete a recipe and all its components.
     */
    public void deleteRecipe(String id) {
        // Delete related components first
        instructionService.deleteByRecipeId(id);
        recipeIngredientService.deleteByRecipeId(id);

        // Delete the recipe itself
        recipeService.delete(id);
    }
}
