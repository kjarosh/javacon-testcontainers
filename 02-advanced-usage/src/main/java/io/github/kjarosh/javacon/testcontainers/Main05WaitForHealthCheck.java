package io.github.kjarosh.javacon.testcontainers;

import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.images.builder.ImageFromDockerfile;

import java.nio.file.Paths;

/**
 * @author Kamil Jarosz
 */
@Slf4j(topic = "main")
public class Main05WaitForHealthCheck {
    public static void main(String[] args) {
        ImageFromDockerfile postgresSlow = new ImageFromDockerfile()
                .withDockerfile(Paths.get("images/postgres-slow/Dockerfile"));
        GenericContainer<?> container = new GenericContainer<>(postgresSlow)
                .withLogConsumer(new Slf4jLogConsumer(log))
                .waitingFor(Wait.forHealthcheck());
        container.start();

        log.info("Wait finished!");
    }
}
