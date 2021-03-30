package io.github.kjarosh.javacon.testcontainers.service.integration;

import io.github.kjarosh.javacon.testcontainers.service.database.config.MyBatisConfiguration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/**
 * @author Kamil Jarosz
 */
public class DatabaseMapperTestBase extends DatabaseIntegrationTestBase {
    protected MyBatisConfiguration config;
    protected SqlSessionFactory sessionFactory;
    protected SqlSession session;

    @BeforeEach
    void setupMyBatis() {
        config = new MyBatisConfiguration(dataSource);
        config.setupSessionFactory();
        sessionFactory = config.getSessionFactory();
        session = sessionFactory.openSession(connection);
    }

    @AfterEach
    void tearDownMyBatis() {
        session.close();
    }
}
