package com.ecommerce.tests;

import com.ecommerce.pages.PageObjectManager;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public abstract class BaseTest {
    protected WebDriver driver;
    protected PageObjectManager pages;

    @BeforeEach
    void setUp() {
        String username = requiredEnvironmentVariable("ECOM_USERNAME");
        String password = requiredEnvironmentVariable("ECOM_PASSWORD");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1440,1000", "--disable-dev-shm-usage", "--no-sandbox");
        if (!"false".equalsIgnoreCase(System.getenv("ECOM_HEADLESS"))) {
            options.addArguments("--headless=new");
        }

        String chromeBinary = System.getenv("CHROME_BIN");
        if (chromeBinary != null && Files.isExecutable(Path.of(chromeBinary))) {
            options.setBinary(chromeBinary);
        }

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ZERO);
        pages = new PageObjectManager(driver);
        pages.loginPage().open();
        pages.loginPage().login(username, password);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private String requiredEnvironmentVariable(String name) {
        String value = System.getenv(name);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Required environment variable is not set: " + name);
        }
        return value;
    }
}
