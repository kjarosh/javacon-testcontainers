package io.github.kjarosh.javacon.testcontainers.service.selenium;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.containers.BrowserWebDriverContainer.VncRecordingMode;
import org.testcontainers.containers.VncRecordingContainer.VncRecordingFormat;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Kamil Jarosz
 */
@Testcontainers
@Tag("manual")
public class ITSeleniumDuckDuckGo {
    @Container
    public static BrowserWebDriverContainer<?> chrome = new BrowserWebDriverContainer<>()
            .withCapabilities(new ChromeOptions())
            .withRecordingMode(
                    VncRecordingMode.RECORD_ALL,
                    SeleniumUtils.createTargetDirectory().toFile(),
                    VncRecordingFormat.FLV);

    @Test
    public void testGoogleSearch() {
        WebDriver driver = chrome.getWebDriver();

        driver.get("https://duckduckgo.com/");

        WebElement search = driver.findElement(By.name("q"));
        search.sendKeys("testcontainers and selenium");
        search.submit();

        new WebDriverWait(driver, 10).until(
                webDriver -> webDriver.getTitle()
                        .startsWith("testcontainers and selenium")
        );

        assertThat(driver.getTitle())
                .isEqualTo("testcontainers and selenium at DuckDuckGo");
    }
}
