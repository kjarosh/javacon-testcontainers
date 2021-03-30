package io.github.kjarosh.javacon.testcontainers;

import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.startupcheck.OneShotStartupCheckStrategy;

import java.time.Duration;

/**
 * @author Kamil Jarosz
 */
@Slf4j(topic = "main")
public class Main00StartupOneShot {
    public static void main(String[] args) {
        GenericContainer<?> container = new GenericContainer<>("busybox:1.32.1")
                .withLogConsumer(new Slf4jLogConsumer(log))
                .withCommand("nslookup -type=a wikipedia.org")
                .withStartupCheckStrategy(
                        new OneShotStartupCheckStrategy()
                                .withTimeout(Duration.ofSeconds(30))
                );
        container.start();

        log.info("Finished");
    }
}
