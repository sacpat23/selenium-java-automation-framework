package com.ecommerce.pages;

import com.ecommerce.config.AppConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {
    private static final By EMAIL = By.id("userEmail");
    private static final By PASSWORD = By.id("userPassword");
    private static final By SIGN_IN = By.id("login");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get(AppConfig.get("ecom.base.url", "ECOM_BASE_URL"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(EMAIL));
    }

    public void login(String username, String password) {
        driver.findElement(EMAIL).sendKeys(username);
        driver.findElement(PASSWORD).sendKeys(password);
        driver.findElement(SIGN_IN).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(DashboardPage.PRODUCT_CARDS));
    }
}
