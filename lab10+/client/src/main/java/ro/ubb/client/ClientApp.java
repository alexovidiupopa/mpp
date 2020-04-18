package ro.ubb.client;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.client.RestTemplate;
import ro.ubb.client.ui.Console;

public class ClientApp {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("ro.ubb.client.config");
        RestTemplate restTemplate = context.getBean(RestTemplate.class);
        Console console = new Console(restTemplate);
        console.run();
    }
}
