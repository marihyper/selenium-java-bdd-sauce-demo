package com.saucedemo.hooks;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import static com.codeborne.selenide.Selenide.closeWebDriver;

/**
 * Per-scenario lifecycle. The driver binary is resolved (and kept up to date)
 * by Bonigarcia WebDriverManager, then handed to Selenide via WebDriverRunner.
 */
public class Hooks {

    @Before
    public void setUp() {
        Configuration.baseUrl = "https://sauce-demo.myshopify.com";
        Configuration.timeout = 10_000;
        Configuration.pageLoadTimeout = 30_000;
        Configuration.browserSize = "1366x768";

        boolean headless = Boolean.parseBoolean(System.getProperty("selenide.headless", "true"));
        String browser = System.getProperty("browser", "chrome").toLowerCase();

        WebDriver driver;
        switch (browser) {
            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions options = new FirefoxOptions();
                if (headless) {
                    options.addArguments("-headless");
                }
                options.addArguments("--width=1366", "--height=768");
                driver = new FirefoxDriver(options);
            }
            default -> {
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                if (headless) {
                    options.addArguments("--headless=new");
                }
                options.addArguments("--no-sandbox", "--disable-gpu", "--window-size=1366,768");
                driver = new ChromeDriver(options);
            }
        }
        WebDriverRunner.setWebDriver(driver);
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed() && WebDriverRunner.hasWebDriverStarted()) {
            byte[] screenshot = ((TakesScreenshot) WebDriverRunner.getWebDriver())
                    .getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", scenario.getName());
        }
        closeWebDriver();
    }
}
