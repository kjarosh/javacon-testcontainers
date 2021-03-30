package io.github.kjarosh.javacon.testcontainers;

import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;

/**
 * @author Kamil Jarosz
 */
@Slf4j(topic = "main")
public class Main04WaitForHttp {
    public static void main(String[] args) {
        GenericContainer<?> slowHttpd = new GenericContainer<>("httpd:2.4")
                .withLogConsumer(new Slf4jLogConsumer(log))
                .withExposedPorts(80)
                .withCommand("sh", "-c", "sleep 10 && " +
                        "echo Starting && " +
                        "httpd-foreground")
                .waitingFor(Wait.forHttp("/"));
        slowHttpd.start();

        log.info("Wait finished!");
    }
}
