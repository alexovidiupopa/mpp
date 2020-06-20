package ro.ubb.catalog.core.service;

import ro.ubb.catalog.core.model.Sensor;

import java.util.List;

public interface SensorService {
    List<Sensor> getSensors();
    List<String> getSensorNames();
    List<Sensor> getLast4Measurements(String name);

}
