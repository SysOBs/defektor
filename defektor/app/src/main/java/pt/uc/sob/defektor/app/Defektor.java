package pt.uc.sob.defektor.app;

import org.springframework.boot.SpringApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import pt.uc.sob.defektor.server.Server;

/**
 * Defektor main class.
 */
public class Defektor {

    /**
     * Main class for defektor.
     *
     * @param args - None.
     */
    public static void main(String[] args) {
        System.out.println("🐳 Defektor - Write once, run away");

        new SpringApplication(Server.class).run(args);
    }
}
