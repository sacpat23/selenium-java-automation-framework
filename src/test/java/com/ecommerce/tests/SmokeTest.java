package com.ecommerce.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class SmokeTest extends BaseTest {
    @Test
    void userCanSignInAndViewProducts() {
        assertTrue(pages.dashboardPage().isProductListVisible(),
                "Product dashboard should be visible after sign in");
    }
}
