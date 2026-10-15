package com.ecommerce.pages;

import org.openqa.selenium.WebDriver;

public class PageObjectManager {
    private final LoginPage loginPage;
    private final DashboardPage dashboardPage;
    private final CheckoutPage checkoutPage;
    private final OrderPage orderPage;
    private final ThankYouPage thankYouPage;
    private final MyOrdersPage myOrdersPage;

    public PageObjectManager(WebDriver driver) {
        loginPage = new LoginPage(driver);
        dashboardPage = new DashboardPage(driver);
        checkoutPage = new CheckoutPage(driver);
        orderPage = new OrderPage(driver);
        thankYouPage = new ThankYouPage(driver);
        myOrdersPage = new MyOrdersPage(driver);
    }

    public LoginPage loginPage() {
        return loginPage;
    }

    public DashboardPage dashboardPage() {
        return dashboardPage;
    }

    public CheckoutPage checkoutPage() {
        return checkoutPage;
    }

    public OrderPage orderPage() {
        return orderPage;
    }

    public ThankYouPage thankYouPage() {
        return thankYouPage;
    }

    public MyOrdersPage myOrdersPage() {
        return myOrdersPage;
    }
}
