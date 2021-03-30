package io.github.kjarosh.javacon.testcontainers;

import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;

import java.time.Duration;

/**
 * @author Kamil Jarosz
 */
@Slf4j(topic = "main")
public class Main03Logs {
    public static void main(String[] args) throws InterruptedException {
        GenericContainer<?> container = new GenericContainer<>("redis:6.2.1")
                .withLogConsumer(new Slf4jLogConsumer(log)
                        .withPrefix("redis"));
        container.start();

        Thread.sleep(Duration.ofMinutes(5).toMillis());
    }
}
