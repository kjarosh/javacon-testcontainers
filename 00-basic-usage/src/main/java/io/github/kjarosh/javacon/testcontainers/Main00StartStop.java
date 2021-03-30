package io.github.kjarosh.javacon.testcontainers;

import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.GenericContainer;

import java.time.Duration;

/**
 * @author Kamil Jarosz
 */
@Slf4j(topic = "main")
public class Main00StartStop {
    public static void main(String[] args) throws Exception {
        GenericContainer<?> container = new GenericContainer<>("httpd:2.4");

        container.start();
        log.info("Container started!");

        // do something with the container
        Thread.sleep(Duration.ofMinutes(10).toMillis());

        log.info("Stopping container...");
        container.stop(); // optional
    }
}
