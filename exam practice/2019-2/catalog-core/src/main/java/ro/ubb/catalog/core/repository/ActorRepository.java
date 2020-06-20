package ro.ubb.catalog.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.ubb.catalog.core.model.Actor;


public interface ActorRepository extends JpaRepository<Actor,Long> {

}
