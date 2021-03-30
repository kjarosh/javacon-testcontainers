package io.github.kjarosh.javacon.testcontainers.service.database.config;

import io.github.kjarosh.javacon.testcontainers.service.database.mappers.EventLogMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;

/**
 * @author Kamil Jarosz
 */
@Service
public class DatabaseSessions {
    private final SqlSession session;

    public DatabaseSessions(MyBatisConfiguration configuration) {
        this.session = configuration.getSessionFactory().openSession();
    }

    public EventLogMapper getEventLogMapper() {
        return this.session.getMapper(EventLogMapper.class);
    }

    @PreDestroy
    public void destroy() {
        if (this.session != null) {
            this.session.close();
        }
    }
}
