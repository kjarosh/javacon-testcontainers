package io.github.kjarosh.javacon.testcontainers;

import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.startupcheck.IndefiniteWaitOneShotStartupCheckStrategy;
import org.testcontainers.containers.startupcheck.OneShotStartupCheckStrategy;

import java.time.Duration;

/**
 * @author Kamil Jarosz
 */
@Slf4j(topic = "main")
public class Main01StartupIndefiniteOneShot {
    public static void main(String[] args) {
        GenericContainer<?> container = new GenericContainer<>("bash:5.1.4")
                .withLogConsumer(new Slf4jLogConsumer(log))
                .withCommand("-c", "echo Doing something... && " +
                        "sleep 60 && " +
                        "echo Done")
                .withStartupCheckStrategy(
                        new IndefiniteWaitOneShotStartupCheckStrategy()
                );
        container.start();

        log.info("Finished");
    }
}
