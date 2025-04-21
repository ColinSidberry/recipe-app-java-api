package services.interfaces;

import java.util.List;

import models.impl.InstructionModelImpl;

public interface InstructionService extends BaseService<InstructionModelImpl> {
    public List<InstructionModelImpl> getInstructionsByRecipe(String recipeId);
    public void deleteByRecipeId(String recipeId);
}
