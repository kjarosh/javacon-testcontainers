package io.github.kjarosh.javacon.testcontainers;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Kamil Jarosz
 */
public class SingletonContainerTest {
    private static final GenericContainer<?> httpdContainer =
            new GenericContainer<>("httpd:2.4");

    static {
        httpdContainer.start();
    }

    private static final HttpClient client = HttpClient.newHttpClient();

    @Test
    void containerRunning() {
        assertTrue(httpdContainer.isRunning());
    }

    @Test
    void httpdHealthCheck() throws Exception {
        String host = httpdContainer.getHost();
        int port = httpdContainer.getMappedPort(80);

        // perform simple HTTP GET and log results
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://" + host + ":" + port))
                .build();
        String result = client.send(request, BodyHandlers.ofString())
                .body();
        assertEquals("<html><body><h1>It works!</h1></body></html>\n", result);
    }
}
