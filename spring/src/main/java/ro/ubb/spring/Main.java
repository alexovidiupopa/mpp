package ro.ubb.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.ubb.spring.ui.Console;

public class Main {
    public static void main(String[] args) {
        System.out.println("hello");

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(
                        "ro.ubb.spring"
                );

        context.getBean(Console.class).run();

        System.out.println("bye");
    }
}
