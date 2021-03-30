package io.github.kjarosh.javacon.testcontainers.service.database.config;

import lombok.RequiredArgsConstructor;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

/**
 * @author Kamil Jarosz
 */
@Service
@RequiredArgsConstructor
public class DataSourceProducer {
    @Value("${app.jdbc.url}")
    private final String url;
    @Value("${app.jdbc.username}")
    private final String username;
    @Value("${app.jdbc.password}")
    private final String password;

    @Bean
    @Qualifier("persistence")
    public DataSource createDataSource() {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setUrl(url);
        ds.setUser(username);
        ds.setPassword(password);
        return ds;
    }
}
