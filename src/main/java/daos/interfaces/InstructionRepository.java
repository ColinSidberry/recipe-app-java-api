package daos.interfaces;

import java.util.List;

import models.impl.InstructionModelImpl;

/**
 * Interface for managing Instruction entities in the data store.
 * Extends BaseRepository with an additional method for querying instructions by recipe.
 */
public interface InstructionRepository extends BaseRepository<InstructionModelImpl> {
    /**
     * Retrieves all instructions for a specific recipe.
     * 
     * @param recipeId The ID of the recipe to retrieve instructions for
     * @return A list of instructions for the specified recipe
     */
    List<InstructionModelImpl> findByRecipeId(String recipeId);
}
