package ro.ubb.catalog.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.ubb.catalog.core.model.Sensor;

import java.util.List;


public interface SensorRepository extends JpaRepository<Sensor,Long> {
    
    @Query("Select distinct sensor.name from Sensor sensor")
    List<String> findAllSensorNames();

    List<Sensor> findTop4ByNameOrderByTimeDesc(String name);
}
