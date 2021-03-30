package io.github.kjarosh.javacon.testcontainers.service.integration;

import io.github.kjarosh.javacon.testcontainers.service.database.config.DataSourceProducer;
import io.github.kjarosh.javacon.testcontainers.service.database.config.LiquibaseMigrationService;
import liquibase.exception.LiquibaseException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Kamil Jarosz
 */
@Testcontainers
public class DatabaseIntegrationTestBase {
    @Container
    protected static final PostgreSQLContainer<?> database =
            new PostgreSQLContainer<>("postgres:12.6")
                    .withReuse(true);

    protected static DataSourceProducer dataSourceProducer;
    protected static DataSource dataSource;
    protected static LiquibaseMigrationService liquibaseMigrationService;
    protected Connection connection;

    @BeforeAll
    static void setUpDatabase() throws SQLException, LiquibaseException {
        dataSourceProducer = new DataSourceProducer(
                database.getJdbcUrl(),
                database.getUsername(),
                database.getPassword());
        dataSource = dataSourceProducer.createDataSource();
        liquibaseMigrationService = new LiquibaseMigrationService(dataSource);
        liquibaseMigrationService.performMigrations();
    }

    @BeforeEach
    void setUpConnection() throws SQLException {
        connection = dataSource.getConnection();
    }

    @AfterEach
    void tearDownConnection() throws SQLException {
        connection.close();
    }

    @SneakyThrows
    protected List<String> queryTables(String namePattern) {
        List<String> tables = new ArrayList<>();
        ResultSet rs = connection.getMetaData().getTables(
                null, null,
                namePattern,
                new String[]{"TABLE"});

        while (rs.next()) {
            tables.add(rs.getString(3));
        }
        return tables;
    }

    @SneakyThrows
    protected String selectOneValue(String query) {
        ResultSet rs = connection.createStatement().executeQuery(query);
        assertThat(rs.next()).isTrue();
        String value = rs.getString(1);
        assertThat(rs.next()).isFalse();
        return value;
    }
}
