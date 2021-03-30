package io.github.kjarosh.javacon.testcontainers;

import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.AuthToken;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.testcontainers.containers.Neo4jContainer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kamil Jarosz
 */
@Slf4j(topic = "main")
public class Main01Neo4j {
    public static void main(String[] args) {
        Neo4jContainer<?> container = new Neo4jContainer<>("neo4j:4.2.4")
                // dedicated methods for configuring the image
                .withNeo4jConfig("dbms.transaction.timeout", "5m");
        container.start();

        String httpUrl = container.getHttpUrl();
        String httpsUrl = container.getHttpsUrl();
        String boltUrl = container.getBoltUrl();
        String adminPassword = container.getAdminPassword();

        log.info("HTTP URL: " + httpUrl);
        log.info("HTTPS URL: " + httpsUrl);
        log.info("Bolt URL: " + boltUrl);
        log.info("Admin password: " + adminPassword);


        AuthToken auth = AuthTokens.basic("neo4j", adminPassword);
        try (
                Driver driver = GraphDatabase.driver(boltUrl, auth);
                Session session = driver.session()) {
            String query = "CREATE (a:Greeting) " +
                    "SET a.message = $message " +
                    "RETURN a.message + ', from node ' + id(a)";

            Map<String, Object> params = new HashMap<>();
            params.put("message", "It works!");

            String greeting = session.writeTransaction(tx -> {
                Result result = tx.run(query, params);
                return result.single().get(0).asString();
            });
            log.info(greeting);
        }
    }
}
