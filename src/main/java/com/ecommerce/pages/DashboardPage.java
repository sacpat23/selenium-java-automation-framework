package com.ecommerce.pages;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class DashboardPage extends BasePage {
    static final By PRODUCT_CARDS = By.cssSelector(".card-body");
    private static final By CART_LINK = By.cssSelector("[routerlink*='cart']");
    private static final String CART_CENTER_IS_UNOBSTRUCTED = """
            const element = arguments[0];
            const rect = element.getBoundingClientRect();
            if (rect.width === 0 || rect.height === 0) return false;
            const topElement = document.elementFromPoint(
                rect.left + rect.width / 2,
                rect.top + rect.height / 2
            );
            return topElement === element || element.contains(topElement);
            """;

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public void waitForProducts() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(PRODUCT_CARDS));
    }

    public boolean isProductListVisible() {
        waitForProducts();
        return driver.findElement(PRODUCT_CARDS).isDisplayed();
    }

    public void addProductToCart(String productName) {
        waitForProducts();
        List<WebElement> products = driver.findElements(PRODUCT_CARDS);
        for (WebElement product : products) {
            String title = product.findElement(By.tagName("b")).getText().trim();
            if (title.equals(productName)) {
                product.findElement(By.xpath(".//button[contains(normalize-space(.), 'Add To Cart')]")).click();
                waitForCartToBeUnobstructed();
                return;
            }
        }
        throw new NoSuchElementException("Product not found on dashboard: " + productName);
    }

    public void openCart() {
        waitForCartToBeUnobstructed();
        wait.until(ExpectedConditions.elementToBeClickable(CART_LINK)).click();
    }

    private void waitForCartToBeUnobstructed() {
        long[] unobstructedSince = {0};
        wait.until(currentDriver -> {
            WebElement cartLink = currentDriver.findElement(CART_LINK);
            boolean unobstructed = cartLink.isDisplayed()
                    && cartLink.isEnabled()
                    && Boolean.TRUE.equals(((JavascriptExecutor) currentDriver)
                            .executeScript(CART_CENTER_IS_UNOBSTRUCTED, cartLink));
            if (!unobstructed) {
                unobstructedSince[0] = 0;
                return false;
            }

            long now = System.nanoTime();
            if (unobstructedSince[0] == 0) {
                unobstructedSince[0] = now;
            }
            return now - unobstructedSince[0] >= Duration.ofSeconds(1).toNanos();
        });
    }
}
