package io.github.kjarosh.javacon.testcontainers.service.database.config;

import io.github.kjarosh.javacon.testcontainers.service.database.mappers.EventLogMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.managed.ManagedTransactionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

/**
 * @author Kamil Jarosz
 */
@Service
@Scope("singleton")
@RequiredArgsConstructor
public class MyBatisConfiguration {
    @Qualifier("persistence")
    private final DataSource dataSource;

    private SqlSessionFactory sessionFactory;

    @PostConstruct
    public void setupSessionFactory() {
        Environment environment = new Environment(
                dataSource.toString(),
                new ManagedTransactionFactory(),
                dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(EventLogMapper.class);
        sessionFactory = new SqlSessionFactoryBuilder().build(configuration);
    }

    public SqlSessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
