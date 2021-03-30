package io.github.kjarosh.javacon.testcontainers;

import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.images.builder.ImageFromDockerfile;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;

/**
 * @author Kamil Jarosz
 */
@Slf4j(topic = "main")
public class Main00DockerfileBuilder {
    private static final HttpClient client = HttpClient.newHttpClient();

    public static void main(String[] args) throws Exception {
        ImageFromDockerfile image = new ImageFromDockerfile()
                .withDockerfileFromBuilder(builder -> builder
                        .from("alpine:3.2")
                        .run("apk add --update nginx")
                        .cmd("nginx", "-g", "daemon off;")
                        .build());
        GenericContainer<?> container = new GenericContainer<>(image)
                .withExposedPorts(80);
        container.start();

        String host = container.getHost();
        int port = container.getMappedPort(80);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://" + host + ":" + port))
                .build();
        String result = client.send(request, BodyHandlers.ofString())
                .body();
        log.info(result);
    }
}
