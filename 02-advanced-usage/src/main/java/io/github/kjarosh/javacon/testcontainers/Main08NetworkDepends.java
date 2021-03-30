package io.github.kjarosh.javacon.testcontainers;

import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.startupcheck.OneShotStartupCheckStrategy;

/**
 * @author Kamil Jarosz
 */
@Slf4j(topic = "main")
public class Main08NetworkDepends {
    public static void main(String[] args) {
        Network network = Network.newNetwork();

        GenericContainer<?> httpd = new GenericContainer<>("httpd:2.4")
                .withNetwork(network)
                .withNetworkAliases("test-server")
                .withLogConsumer(new Slf4jLogConsumer(log).withPrefix("httpd"));

        GenericContainer<?> client = new GenericContainer<>("curlimages/curl:7.76.0")
                .withNetwork(network)
                .withNetworkAliases("curl-client")
                .withCommand("curl", "-s", "http://test-server")
                .withStartupCheckStrategy(new OneShotStartupCheckStrategy())
                .withLogConsumer(new Slf4jLogConsumer(log).withPrefix("curl"))
                .dependsOn(httpd);

        client.start();
    }
}
