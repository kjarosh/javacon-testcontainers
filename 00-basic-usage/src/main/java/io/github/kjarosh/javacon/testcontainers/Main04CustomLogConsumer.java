package io.github.kjarosh.javacon.testcontainers;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.OutputFrame;

import java.util.function.Consumer;

/**
 * @author Kamil Jarosz
 */
public class Main04CustomLogConsumer {
    public static void main(String[] args) {
        try (GenericContainer<?> container = new GenericContainer<>("redis:6.2.1")
                .withLogConsumer(new CustomLogConsumer("redis"))) {
            container.start();
        }
    }

    private static class CustomLogConsumer implements Consumer<OutputFrame> {
        private final String name;

        public CustomLogConsumer(String name) {
            this.name = name;
        }

        @Override
        public void accept(OutputFrame outputFrame) {
            String line = outputFrame.getUtf8String().stripTrailing();
            System.out.println(name + ": " + line);
        }
    }
}
