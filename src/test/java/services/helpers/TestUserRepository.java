package services.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import daos.interfaces.BaseRepository;

import models.impl.UserModelImpl;

public class TestUserRepository implements BaseRepository<UserModelImpl> {
    private final List<UserModelImpl> users = new ArrayList<>();

    @Override
    public UserModelImpl save(UserModelImpl user) {
        if (user.getId() == null) {
            user.setId(java.util.UUID.randomUUID().toString());
        }
        users.removeIf(u -> u.getId().equals(user.getId()));
        users.add(user);
        return user;
    }

    @Override
    public List<UserModelImpl> findAll() {
        return new ArrayList<>(users);
    }

    @Override
    public Optional<UserModelImpl> findById(String id) {
        return users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
    }

    @Override
    public void delete(String id) {
        users.removeIf(u -> u.getId().equals(id));
    }

    public void clear() {
        users.clear();
    }
}
