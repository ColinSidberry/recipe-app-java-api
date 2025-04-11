package daos;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T> {
  T save(T t);
  Optional<T> findById(String id); // Question: What does the optional mean?
  List<T> findAll();
  void delete(String id);
}
