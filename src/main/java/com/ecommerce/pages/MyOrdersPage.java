package com.ecommerce.pages;

import java.util.List;
import java.util.Locale;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MyOrdersPage extends BasePage {
    private static final By ORDER_ROWS = By.cssSelector("tbody tr");
    private static final By ORDER_DETAILS = By.cssSelector(".col-text");

    public MyOrdersPage(WebDriver driver) {
        super(driver);
    }

    public void verifyOrder(String expectedOrderId) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("tbody")));
        List<WebElement> rows = driver.findElements(ORDER_ROWS);
        for (WebElement row : rows) {
            String rowOrderId = row.findElement(By.tagName("th")).getText().trim();
            if (normalize(rowOrderId).equals(normalize(expectedOrderId))) {
                row.findElement(By.tagName("button")).click();
                String detailsOrderId = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(ORDER_DETAILS)).getText().trim();
                if (!normalize(detailsOrderId).equals(normalize(expectedOrderId))) {
                    throw new AssertionError("Order details ID did not match: " + detailsOrderId);
                }
                return;
            }
        }
        throw new NoSuchElementException("Order not found in My Orders: " + expectedOrderId);
    }

    private String normalize(String orderId) {
        return orderId.replaceAll("[^a-zA-Z0-9]", "").toLowerCase(Locale.ROOT);
    }
}
