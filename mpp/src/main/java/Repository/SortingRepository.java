package Repository;

import Model.BaseEntity;
import Utils.Sort;

import java.io.Serializable;

public interface SortingRepository<ID extends Serializable,
        T extends BaseEntity<ID>> extends RepositoryInterface<ID, T> {

    Iterable<T> findAll(Sort sort);

    //TODO: insert sorting-related code here
}