package services.impl;

import java.util.List;

import daos.interfaces.InstructionRepository;
import models.impl.InstructionModelImpl;
import services.interfaces.InstructionService;

public class InstructionServiceImpl implements InstructionService {
  private final InstructionRepository repository;

  public InstructionServiceImpl(InstructionRepository repository) {
    this.repository = repository;
  }

  public InstructionModelImpl create(InstructionModelImpl instruction) {
    return repository.save(instruction);
  }

  public List<InstructionModelImpl> getAll() {
    return repository.findAll();
  }

  public InstructionModelImpl getById(String id) {
    return repository.findById(id).orElse(null);
  }

  public void delete(String id) {
    repository.delete(id);
  }

  public void deleteByRecipeId(String recipeId) {
    List<InstructionModelImpl> instructions = getAll();
    for (InstructionModelImpl instruction : instructions) {
      delete(instruction.getId());
    }
  }

  public List<InstructionModelImpl> getInstructionsByRecipe(String recipeId) {
    return repository.findByRecipeId(recipeId);
  }
}
