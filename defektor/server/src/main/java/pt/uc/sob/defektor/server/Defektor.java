package pt.uc.sob.defektor.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class Defektor {

    public static void main(String[] args) {
        System.out.println("🐳 Defektor - Write once, run away");

        new SpringApplication(Defektor.class).run(args);
    }
}
