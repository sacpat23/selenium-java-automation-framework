package com.ecommerce.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class EcommerceOrderFlowTest extends BaseTest {
    @ParameterizedTest
    @MethodSource("products")
    void userCanPlaceAndVerifyAnOrder(String productName) {
        pages.dashboardPage().addProductToCart(productName);
        pages.dashboardPage().openCart();

        pages.checkoutPage().verifyProductInCart(productName);
        pages.checkoutPage().proceedToOrder();

        pages.orderPage().selectIndia();
        pages.orderPage().submitOrder();

        String confirmation = pages.thankYouPage().getConfirmationMessage();
        assertTrue(confirmation.contains("THANKYOU FOR THE ORDER"),
                "Expected order confirmation, but found: " + confirmation);
        String orderId = pages.thankYouPage().getOrderId();
        assertTrue(!orderId.isBlank(), "Order ID should be present on the confirmation page");

        pages.thankYouPage().openMyOrders();
        pages.myOrdersPage().verifyOrder(orderId);
    }

    static Stream<String> products() throws IOException {
        try (InputStream input = EcommerceOrderFlowTest.class.getResourceAsStream("/products.txt")) {
            if (input == null) {
                throw new IllegalStateException("Missing test data resource: products.txt");
            }
            List<String> products;
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(input, StandardCharsets.UTF_8))) {
                products = reader.lines()
                        .map(String::trim)
                        .filter(product -> !product.isEmpty() && !product.startsWith("#"))
                        .toList();
            }
            if (products.isEmpty()) {
                throw new IllegalStateException("No products configured in products.txt");
            }
            return products.stream();
        }
    }
}
