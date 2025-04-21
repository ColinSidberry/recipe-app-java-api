package services.interfaces;

import models.impl.UserModelImpl;

public interface UserService extends BaseService<UserModelImpl> {
    UserModelImpl findByUsername(String username);
}
