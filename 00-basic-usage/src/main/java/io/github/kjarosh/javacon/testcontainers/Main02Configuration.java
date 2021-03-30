package io.github.kjarosh.javacon.testcontainers;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.images.PullPolicy;

import java.time.Duration;

/**
 * @author Kamil Jarosz
 */
public class Main02Configuration {
    public static void main(String[] args) throws Exception {
        GenericContainer<?> container = new GenericContainer<>("alpine:3.13")
                .withLabel("javacon", "true")
                .withEnv("NAME", "config example")
                .withWorkingDirectory("/opt")
                .withCommand("sleep 300")
                .withImagePullPolicy(PullPolicy.alwaysPull());
        container.start();

        Thread.sleep(Duration.ofMinutes(5).toMillis());
    }
}
