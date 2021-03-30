package io.github.kjarosh.javacon.testcontainers.service.microservice;

import io.github.kjarosh.javacon.testcontainers.service.rest.entity.EventLogEntity;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;

/**
 * @author Kamil Jarosz
 */
public class ITEventLog extends MicroserviceTestBase {
    @Test
    void createEventLog() {
        EventLogEntity entity = EventLogEntity.builder()
                .id(UUID.randomUUID().toString())
                .type("test")
                .time(Instant.now())
                .content("test content")
                .build();

        RestAssured.given()
                .body(entity)
                .contentType(ContentType.JSON)
                .post("/events")
                .then()
                .statusCode(201);

        RestAssured.get("/events")
                .then()
                .statusCode(200)
                .body("id[0]", equalTo(entity.getId()))
                .body("type[0]", equalTo(entity.getType()))
                .body("time[0]", equalTo(entity.getTime().toString()))
                .body("content[0]", equalTo(entity.getContent()));
    }
}
