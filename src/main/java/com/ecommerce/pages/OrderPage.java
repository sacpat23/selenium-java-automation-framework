package com.ecommerce.pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class OrderPage extends BasePage {
    private static final By ORDER_FORM = By.cssSelector("form");
    private static final By COUNTRY = By.cssSelector("input[placeholder='Select Country']");
    private static final By COUNTRY_OPTIONS = By.cssSelector(".ta-results button");
    private static final By SUBMIT = By.cssSelector(".action__submit");

    public OrderPage(WebDriver driver) {
        super(driver);
    }

    public void selectIndia() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(ORDER_FORM));
        WebElement countryInput = wait.until(ExpectedConditions.elementToBeClickable(COUNTRY));
        countryInput.click();
        countryInput.sendKeys("ind");

        WebElement india = wait.until(currentDriver -> {
            List<WebElement> options = currentDriver.findElements(COUNTRY_OPTIONS);
            return options.stream()
                    .filter(option -> option.getText().trim().equals("India"))
                    .findFirst()
                    .orElse(null);
        });
        india.click();
    }

    public void submitOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(SUBMIT)).click();
    }
}
