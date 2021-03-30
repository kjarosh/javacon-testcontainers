package io.github.kjarosh.javacon.testcontainers.service.integration;

import io.github.kjarosh.javacon.testcontainers.service.database.dto.EventLogDto;
import io.github.kjarosh.javacon.testcontainers.service.database.mappers.EventLogMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Kamil Jarosz
 */
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ITEventLog extends DatabaseMapperTestBase {
    private EventLogMapper mapper;

    @BeforeEach
    void setUpMappers() {
        mapper = session.getMapper(EventLogMapper.class);
    }

    @Test
    void insertLog() throws SQLException {
        String id = UUID.randomUUID().toString();
        String type = "example";
        Instant time = Instant.now();
        String content = "example content";

        EventLogDto dto = new EventLogDto(
                id,
                type,
                time,
                content);

        mapper.insertLog(dto);

        try (PreparedStatement statement = connection.prepareStatement(
                "select type, time from event_log where id = ?")) {
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();

            assertThat(rs.next()).isTrue();
            assertThat(rs.getString(1)).isEqualTo(type);
            assertThat(rs.getTimestamp(2)).isEqualTo(Timestamp.from(time));
            assertThat(rs.next()).isFalse();
        }
    }
}
