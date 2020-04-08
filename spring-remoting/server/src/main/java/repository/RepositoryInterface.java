package repository;

import model.BaseEntity;
import model.Exceptions.ValidatorException;
import utils.Sort;

import java.util.Optional;

public interface RepositoryInterface<ID, T extends BaseEntity<ID>> {

    /**
     * Adds the given entity.
     *
     * @param entity
     *            must not be null.
     * @return an {@code Optional} - null if the entity was saved otherwise (e.g. id already exists) returns the entity.
     * @throws IllegalArgumentException
     *             if the given entity is null.
     * @throws ValidatorException
     *             if the entity is not valid.
     */
    Optional<T> add(T entity);

    /**
     * Removes the entity with the given id.
     *
     * @param id
     *            must not be null.
     * @return an {@code Optional} - null if there is no entity with the given id, otherwise the removed entity.
     * @throws IllegalArgumentException
     *             if the given id is null.
     */
    Optional<T> delete(ID id);

    /**
     * Updates the given entity.
     *
     * @param entity
     *            must not be null.
     * @return an {@code Optional} - null if the entity was updated otherwise (e.g. id doesn't exist) returns the
     *         entity.
     * @throws IllegalArgumentException
     *             if the given entity is null.
     */
    Optional<T> update(T entity);

    /**
     * Find the entity with the given {@code id}.
     *
     * @param id
     *            must be not null.
     * @return an {@code Optional} encapsulating the entity with the given id.
     * @throws IllegalArgumentException
     *             if the given id is null.
     */
    Optional<T> findById(ID id);

    /**
     *
     * @return all entities.
     */
    Iterable<T> getAll();

    /**
     *
     * @return all entities sorted by the given rule.
     */
    Iterable<T> getAll(Sort sort);
}