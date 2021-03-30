package io.github.kjarosh.javacon.testcontainers;

import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author Kamil Jarosz
 */
@Slf4j(topic = "main")
public class Main00Postgres {
    public static void main(String[] args) throws Exception {
        PostgreSQLContainer<?> container =
                new PostgreSQLContainer<>("postgres:12.6");
        container.start();

        // there are dedicated methods for JDBC containers
        String databaseName = container.getDatabaseName();
        String driverClassName = container.getDriverClassName();
        String jdbcUrl = container.getJdbcUrl();
        String username = container.getUsername();
        String password = container.getPassword();
        String testQueryString = container.getTestQueryString();

        log.info("Database name: " + databaseName);
        log.info("Driver class name: " + driverClassName);
        log.info("JDBC URL: " + jdbcUrl);
        log.info("Username: " + username);
        log.info("Password: " + password);
        log.info("Test query string: " + testQueryString);

        // let's try connecting to the database
        try (Connection conn = DriverManager.getConnection(
                jdbcUrl, username, password)) {
            boolean success = conn.createStatement()
                    .execute(testQueryString);
            if (success) {
                log.info("Query succeeded!");
            } else {
                log.error("Query failed");
            }
        }
    }
}
