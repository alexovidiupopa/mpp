package repository;

import model.BaseEntity;
import utils.Sort;

import java.io.Serializable;
import java.sql.SQLException;

public interface SortingRepository<ID extends Serializable,
        T extends BaseEntity<ID>> extends RepositoryInterface<ID, T> {

    Iterable<T> getAll(Sort sort) throws SQLException, ClassNotFoundException;

    //TODO: insert sorting-related code here
}