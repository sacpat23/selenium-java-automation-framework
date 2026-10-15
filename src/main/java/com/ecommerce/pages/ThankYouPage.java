package com.ecommerce.pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ThankYouPage extends BasePage {
    private static final By THANK_YOU_MESSAGE = By.cssSelector(".hero-primary");
    private static final By ORDER_IDS = By.cssSelector(".em-spacer-1 .ng-star-inserted");
    private static final By MY_ORDERS_LINK = By.cssSelector("button[routerlink*='myorders']");

    public ThankYouPage(WebDriver driver) {
        super(driver);
    }

    public String getConfirmationMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(THANK_YOU_MESSAGE)).getText().trim();
    }

    public String getOrderId() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(ORDER_IDS));
        List<WebElement> orderIds = driver.findElements(ORDER_IDS);
        return orderIds.get(orderIds.size() - 1).getText().trim();
    }

    public void openMyOrders() {
        wait.until(ExpectedConditions.elementToBeClickable(MY_ORDERS_LINK)).click();
    }
}
