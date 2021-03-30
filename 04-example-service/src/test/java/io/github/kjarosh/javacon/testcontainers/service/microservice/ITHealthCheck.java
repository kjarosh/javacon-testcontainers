package io.github.kjarosh.javacon.testcontainers.service.microservice;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

/**
 * @author Kamil Jarosz
 */
public class ITHealthCheck extends MicroserviceTestBase {
    @Test
    void healthCheck() {
        RestAssured.get("/healthcheck")
                .then()
                .statusCode(200);
    }
}
