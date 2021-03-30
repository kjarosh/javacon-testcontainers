package io.github.kjarosh.javacon.testcontainers.service.microservice;

import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.images.builder.ImageFromDockerfile;

import java.nio.file.Paths;

/**
 * @author Kamil Jarosz
 */
@Slf4j
public class TestcontainersEnvironment {
    private static final TestcontainersEnvironment instance = new TestcontainersEnvironment();

    private final PostgreSQLContainer<?> database;
    private final GenericContainer<?> exampleService;

    public static TestcontainersEnvironment getInstance() {
        return instance;
    }

    private TestcontainersEnvironment() {
        Network network = Network.newNetwork();
        database = new PostgreSQLContainer<>("postgres:12.6")
                .withNetwork(network)
                .withNetworkAliases("database");
        exampleService = new GenericContainer<>(new ImageFromDockerfile()
                .withDockerfile(Paths.get("Dockerfile").toAbsolutePath()))
                .withNetwork(network)
                .withLogConsumer(new Slf4jLogConsumer(log).withPrefix("example-service"))
                .withExposedPorts(8080)
                .withEnv("CONFIG_JDBC_URL", "jdbc:postgresql://database/test?loggerLevel=OFF")
                .withEnv("CONFIG_JDBC_USERNAME", "test")
                .withEnv("CONFIG_JDBC_PASSWORD", "test")
                .dependsOn(database);

        log.info("Starting environment...");
        exampleService.start();
        log.info("Environment ready");

        RestAssured.baseURI = "http://" + exampleService.getHost() + ":" + exampleService.getFirstMappedPort();
    }
}
