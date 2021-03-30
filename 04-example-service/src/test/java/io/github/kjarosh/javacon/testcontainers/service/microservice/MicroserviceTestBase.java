package io.github.kjarosh.javacon.testcontainers.service.microservice;

import org.junit.jupiter.api.BeforeEach;

/**
 * @author Kamil Jarosz
 */
public class MicroserviceTestBase {
    protected TestcontainersEnvironment environment;

    @BeforeEach
    void setUp() {
        environment = TestcontainersEnvironment.getInstance();
    }
}
