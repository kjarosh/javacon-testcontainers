package io.github.kjarosh.javacon.testcontainers.service.database.dto;

import lombok.Data;

import java.time.Instant;

/**
 * @author Kamil Jarosz
 */
@Data
public class EventLogDto {
    private final String id;
    private final String type;
    private final Instant time;
    private final String content;
}
