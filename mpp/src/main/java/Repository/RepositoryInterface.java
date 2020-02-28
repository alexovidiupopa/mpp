package Repository;

import Model.BaseEntity;
import Model.Exceptions.ValidatorException;

import java.util.Optional;

public interface RepositoryInterface<ID, T extends BaseEntity<ID>> {

    Optional<T> add(T entity) throws ValidatorException;

    Optional<T> delete(ID id);

    Optional<T> update(T entity) throws ValidatorException;

    Optional<T> findById(ID id);

    Iterable<T> getAll();

}
