package io.github.kjarosh.javacon.testcontainers.service.selenium;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.containers.BrowserWebDriverContainer.VncRecordingMode;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.VncRecordingContainer.VncRecordingFormat;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Kamil Jarosz
 */
@Testcontainers
@Tag("manual")
public class ITSeleniumJupyterNotebook {
    private static final Network network = Network.newNetwork();

    @Container
    public static GenericContainer<?> jupyter = new GenericContainer<>("jupyter/minimal-notebook:4d9c9bd9ced0")
            .withNetwork(network)
            .withNetworkAliases("jupyter-server")
            .withExposedPorts(8888)
            .withCommand("start.sh jupyter notebook --NotebookApp.token=''");

    @Container
    public static BrowserWebDriverContainer<?> firefox = new BrowserWebDriverContainer<>()
            .withNetwork(network)
            .withCapabilities(new FirefoxOptions())
            .withRecordingMode(
                    VncRecordingMode.RECORD_ALL,
                    SeleniumUtils.createTargetDirectory().toFile(),
                    VncRecordingFormat.FLV);

    @Test
    public void testJupyterNotebook() {
        WebDriver driver = firefox.getWebDriver();
        JavascriptExecutor js = (JavascriptExecutor) driver;

        driver.get("http://jupyter-server:8888/");

        // New > Python 3 notebook
        driver.findElement(By.id("new-dropdown-button")).click();
        driver.findElement(By.id("kernel-python3")).click();

        // New notebook opens in a new tab
        Set<String> tabs = new HashSet<>(driver.getWindowHandles());
        tabs.remove(driver.getWindowHandle());

        assertThat(tabs).hasSize(1);
        driver.switchTo().window(tabs.iterator().next());

        new WebDriverWait(driver, 10).until(
                webDriver -> !webDriver.getTitle().isEmpty() &&
                        !webDriver.getTitle().startsWith("Jupyter")
        );

        assertThat(driver.getTitle())
                .isEqualTo("Untitled - Jupyter Notebook");

        // Rename file
        driver.findElement(By.id("notebook_name")).click();
        WebElement renameInput = driver.findElement(By.cssSelector(".rename-message ~ input"));
        renameInput.clear();
        renameInput.sendKeys("hello_world");
        driver.findElement(By.cssSelector(".modal-footer > button.btn-primary")).click();

        new WebDriverWait(driver, 10).until(
                webDriver -> !webDriver.getTitle().startsWith("Untitled")
        );

        assertThat(driver.getTitle())
                .isEqualTo("hello_world - Jupyter Notebook");

        // Write some code
        String code = "print('Hello World!')";
        WebElement queryInput = driver.findElement(By.cssSelector("div.CodeMirror"));
        js.executeScript("arguments[0].CodeMirror.setValue(\"" + code + "\");", queryInput);

        // Execute Python script
        WebElement runButton = driver.findElement(By.cssSelector("#run_int > button[aria-label='Run']"));
        js.executeScript("arguments[0].click();", runButton);

        // Check output
        String output = driver.findElement(By.cssSelector(".output_area > .output_text > pre")).getText();
        assertThat(output)
                .isEqualTo("Hello World!");
    }
}
