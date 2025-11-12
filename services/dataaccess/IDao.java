package services.dataaccess;

import java.util.List;

/**
 * Data Access Object interface providing CRUD operations for entities.
 * This generic interface defines the contract for data persistence operations.
 * 
 * @param <T> The entity type this DAO manages
 */
public interface IDao<T> {

    /**
     * Retrieves all entities from the data source.
     * 
     * @return A list of all entities, or an empty list if none exist
     */
    List<T> getAll();

    /**
     * Saves a new entity or updates an existing one.
     * 
     * @param entity The entity to be saved
     */
    void save(T entity);

    void saveAll(List<T> entities);

    void delete(T entity);

    List<T> resetData();

}
