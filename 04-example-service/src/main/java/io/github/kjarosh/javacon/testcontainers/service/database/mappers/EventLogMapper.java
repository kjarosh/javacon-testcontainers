package io.github.kjarosh.javacon.testcontainers.service.database.mappers;

import io.github.kjarosh.javacon.testcontainers.service.database.dto.EventLogDto;

import java.util.List;

/**
 * @author Kamil Jarosz
 */
public interface EventLogMapper {
    void insertLog(EventLogDto eventLog);

    List<EventLogDto> getLogs();
}
