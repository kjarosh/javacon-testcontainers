package io.github.kjarosh.javacon.testcontainers.service.selenium;

import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Kamil Jarosz
 */
class SeleniumUtils {
    @SneakyThrows
    public static Path createTargetDirectory() {
        Path dir = Paths.get("target/selenium-recordings");
        Files.createDirectories(dir);
        return dir;
    }
}
