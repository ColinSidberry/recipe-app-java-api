package services.impl;

import daos.interfaces.BaseRepository;
import models.impl.IngredientModelImpl;

import java.util.List;
import services.interfaces.IngredientService;

public class IngredientServiceImpl implements IngredientService {
  private final BaseRepository<IngredientModelImpl> repository;

  public IngredientServiceImpl(BaseRepository<IngredientModelImpl> repository) {
    this.repository = repository;
  }

  public IngredientModelImpl create(IngredientModelImpl ingredient) {
    return repository.save(ingredient);
  }

  public List<IngredientModelImpl> getAll() {
    return repository.findAll();
  }

  public IngredientModelImpl getById(String id) {
    return repository.findById(id).orElse(null);
  }

  public void delete(String id) {
    repository.delete(id);
  }
}
