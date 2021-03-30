package io.github.kjarosh.javacon.testcontainers;

import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.GenericContainer;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;

/**
 * @author Kamil Jarosz
 */
@Slf4j(topic = "main")
public class Main01HttpGet {
    private static final HttpClient client = HttpClient.newHttpClient();

    public static void main(String[] args) throws Exception {
        GenericContainer<?> container = new GenericContainer<>("httpd:2.4")
                .withExposedPorts(80);
        container.start();

        String host = container.getHost();
        int port = container.getMappedPort(80);
        int port2 = container.getFirstMappedPort();
        assert port == port2;

        // perform simple HTTP GET and log results
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://" + host + ":" + port))
                .build();
        String result = client.send(request, BodyHandlers.ofString())
                .body();
        log.info(result);
    }
}
