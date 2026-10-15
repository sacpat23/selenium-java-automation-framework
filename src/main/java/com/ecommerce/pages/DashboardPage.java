package com.ecommerce.pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class DashboardPage extends BasePage {
    static final By PRODUCT_CARDS = By.cssSelector(".card-body");
    private static final By CART_LINK = By.cssSelector("[routerlink*='cart']");
    private static final By LOADING_OVERLAY = By.cssSelector(".ngx-spinner-overlay");

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
                waitForLoadingToFinish();
                return;
            }
        }
        throw new NoSuchElementException("Product not found on dashboard: " + productName);
    }

    public void openCart() {
        waitForLoadingToFinish();
        wait.until(ExpectedConditions.elementToBeClickable(CART_LINK)).click();
    }

    private void waitForLoadingToFinish() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(LOADING_OVERLAY));
    }
}
