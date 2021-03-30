package io.github.kjarosh.javacon.testcontainers.service.integration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Kamil Jarosz
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ITLiquibase extends DatabaseIntegrationTestBase {
    @Test
    void checkChangelog() {
        List<String> tables = queryTables("databasechangelog%");

        assertThat(tables).contains(
                "databasechangelog",
                "databasechangeloglock");
    }

    @Test
    void checkMigratedTables() {
        List<String> tables = queryTables("event_log");

        assertThat(tables).containsExactly("event_log");
    }
}
