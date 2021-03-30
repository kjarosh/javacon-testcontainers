package io.github.kjarosh.javacon.testcontainers.service.rest;

import io.github.kjarosh.javacon.testcontainers.service.database.config.DatabaseSessions;
import io.github.kjarosh.javacon.testcontainers.service.database.dto.EventLogDto;
import io.github.kjarosh.javacon.testcontainers.service.rest.entity.EventLogEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kamil Jarosz
 */
@Slf4j
@RestController
public class EventLogController {
    private final DatabaseSessions sessions;

    public EventLogController(DatabaseSessions sessions) {
        this.sessions = sessions;
    }

    @RequestMapping(value = "/events", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void addEvent(@RequestBody EventLogEntity eventLog) {
        log.info("Adding a new event");
        EventLogDto dto = new EventLogDto(
                eventLog.getId(),
                eventLog.getType(),
                eventLog.getTime(),
                eventLog.getContent());
        sessions.getEventLogMapper().insertLog(dto);
    }

    @RequestMapping("/events")
    @ResponseBody
    public List<EventLogEntity> getEvents() {
        log.info("Listing all events");

        List<EventLogDto> logs = sessions.getEventLogMapper().getLogs();
        List<EventLogEntity> entities = new ArrayList<>();
        logs.forEach(log -> entities.add(EventLogEntity.builder()
                .id(log.getId())
                .type(log.getType())
                .time(log.getTime())
                .content(log.getContent())
                .build()));
        return entities;
    }
}
