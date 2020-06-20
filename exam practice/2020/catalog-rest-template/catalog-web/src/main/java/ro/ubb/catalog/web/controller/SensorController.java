package ro.ubb.catalog.web.controller;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.ubb.catalog.core.model.Sensor;
import ro.ubb.catalog.core.service.SensorService;

import java.io.*;
import java.net.Socket;
import java.util.List;

@RestController
public class SensorController {

    public static final int PORT = 6666;
    public static final String HOST = "localhost";
    @Autowired
    private SensorService sensorService;

    @CrossOrigin
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    private List<Sensor> getAll()
    {
        return sensorService.getSensors();
    }


    @CrossOrigin
    @RequestMapping(value = "/sensors",method = RequestMethod.GET)
    private List<String> getSensorNames()
    {
        return sensorService.getSensorNames();
    }

    @CrossOrigin
    @RequestMapping(value = "/sensors/{name}",method = RequestMethod.GET)
    private List<Sensor> getSensorNames(@PathVariable String name)
    {
        return sensorService.getLast4Measurements(name);
    }

    @CrossOrigin
    @RequestMapping(value = "/kill/{name}",method=RequestMethod.GET)
    public void kill(@PathVariable String name) {
        System.out.println("i wanna kill " + name);
        try (Socket socket = new Socket(HOST, PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
        ) {
            out.println("kill");
            out.println(name);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
