package daos.interfaces;

import java.util.List;
import java.util.Optional;

/**
 * Base interface for all repository implementations.
 * Defines common CRUD operations for managing entities in the data store.
 * 
 * @param <T> The type of entity being managed by this repository
 */
public interface BaseRepository<T> {
  /**
   * Saves a new entity to the data store.
   * If the entity already has an ID, it will be updated instead of creating a new record.
   * 
   * @param t The entity to save
   * @return The saved entity with its ID set
   */
  T save(T t);

  /**
   * Retrieves an entity by its ID.
   * 
   * @param id The ID of the entity to retrieve
   * @return An Optional containing the entity if found, or empty if not found
   */
  Optional<T> findById(String id);

  /**
   * Retrieves all entities from the data store.
   * 
   * @return A list of all entities in the data store
   */
  List<T> findAll();

  /**
   * Deletes an entity by its ID.
   * 
   * @param id The ID of the entity to delete
   */
  void delete(String id);
}
