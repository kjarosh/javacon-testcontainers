package io.github.kjarosh.javacon.testcontainers;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Kamil Jarosz
 */
@Testcontainers
public class BasicJUnitIntegrationTest {
    private static final HttpClient client = HttpClient.newHttpClient();

    @Container
    private static final GenericContainer<?> httpdContainer =
            new GenericContainer<>("httpd:2.4")
                    .withExposedPorts(80);

    @Test
    void containerRunning() {
        assertTrue(httpdContainer.isRunning());
    }

    @Test
    void httpdHealthCheck() throws Exception {
        String host = httpdContainer.getHost();
        int port = httpdContainer.getFirstMappedPort();

        // perform simple HTTP GET and log results
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://" + host + ":" + port))
                .build();
        String result = client.send(request, BodyHandlers.ofString())
                .body();
        assertEquals("<html><body><h1>It works!</h1></body></html>\n", result);
    }
}
