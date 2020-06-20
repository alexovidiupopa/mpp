package ro.ubb.catalog.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ubb.catalog.core.model.Sensor;
import ro.ubb.catalog.core.repository.SensorRepository;

import java.util.List;


@Service
public class SensorServiceImpl implements SensorService {

    @Autowired
    private SensorRepository sensorRepository;

    @Override
    public List<Sensor> getSensors()
    {
        List<Sensor> sensors=sensorRepository.findAll();
        return sensors;
    }
    @Override
    public List<String> getSensorNames()
    {
        return sensorRepository.findAllSensorNames();
    }

    @Override
    public List<Sensor> getLast4Measurements(String name)
    {
        return sensorRepository.findTop4ByNameOrderByTimeDesc(name);
    }


}
