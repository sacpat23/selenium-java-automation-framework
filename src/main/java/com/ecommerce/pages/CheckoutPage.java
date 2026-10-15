package com.ecommerce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckoutPage extends BasePage {
    private static final By CHECKOUT = By.xpath("//button[normalize-space()='Checkout']");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public void verifyProductInCart(String productName) {
        wait.until(currentDriver -> currentDriver.findElements(By.cssSelector("h3")).stream()
                .anyMatch(element -> element.getText().trim().equals(productName)));
    }

    public void proceedToOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(CHECKOUT)).click();
    }
}
