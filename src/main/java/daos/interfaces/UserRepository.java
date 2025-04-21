package daos.interfaces;

import java.util.Optional;

import models.impl.UserModelImpl;

/**
 * Interface for managing User entities in the data store.
 * Extends BaseRepository with an additional method for finding users by email.
 */
public interface UserRepository extends BaseRepository<UserModelImpl> {
    /**
     * Retrieves a user by their email address.
     * 
     * @param email The email address of the user to retrieve
     * @return An Optional containing the user if found, or empty if not found
     */
    Optional<UserModelImpl> findByEmail(String email);
}
