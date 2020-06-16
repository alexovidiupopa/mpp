package ro.ubb.movieapp.core.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.movieapp.core.model.BaseEntity;
import ro.ubb.movieapp.core.model.Movie;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
@Transactional
public interface MovieAppRepository<T extends BaseEntity<ID>, ID extends Serializable>
        extends JpaRepository<T, ID> {


}
