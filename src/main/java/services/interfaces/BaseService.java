package services.interfaces;

import java.util.List;

public interface BaseService<T> {
    public T create(T entity);
    public List<T> getAll();
    public T getById(String id);
    public void delete(String id);
}
