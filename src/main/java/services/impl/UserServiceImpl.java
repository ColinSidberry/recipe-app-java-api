package services.impl;

import java.util.List;

import daos.interfaces.BaseRepository;
import models.impl.UserModelImpl;
import services.interfaces.UserService;

public class UserServiceImpl implements UserService {
  private final BaseRepository<UserModelImpl> repository;

  public UserServiceImpl(BaseRepository<UserModelImpl> repository) {
    this.repository = repository;
  }

  public UserModelImpl create(UserModelImpl user) {
    return repository.save(user);
  }

  public List<UserModelImpl> getAll() {
    return repository.findAll();
  }

  public UserModelImpl getById(String id) {
    return repository.findById(id).orElse(null);
  }
  
  public void delete(String id) {
    repository.delete(id);
  }

  public UserModelImpl findByUsername(String username) {
    return repository.findAll().stream()
        .filter(user -> user.getUsername().equals(username))
        .findFirst()
        .orElse(null);
  }
}
