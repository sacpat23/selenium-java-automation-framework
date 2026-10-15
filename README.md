# Selenium E-commerce Automation

A Java and Selenium WebDriver automation framework for an e-commerce application. It follows the Page Object Model (POM) and uses JUnit 5 for smoke and regression tests.

## Coverage

- Sign in and confirm the product dashboard is displayed
- Find a product and add it to the cart
- Verify the cart, complete checkout, and place the order
- Verify the order ID on the My Orders page

## Technology

- Java 17+
- Selenium WebDriver 4
- JUnit 5
- Maven

## Project structure

```text
src/
├── main/
│   ├── java/com/ecommerce/
│   │   ├── config/         # Application configuration
│   │   └── pages/          # Page objects and PageObjectManager
│   └── resources/          # Application properties
└── test/
    ├── java/com/ecommerce/tests/  # BaseTest, smoke and order-flow tests
    └── resources/                 # Test data
pom.xml
Jenkinsfile
```

Page objects are production sources under `src/main/java`; JUnit test classes and test data are isolated under `src/test`.

## Prerequisites

- Java 17 or newer
- Maven 3.8 or newer
- Google Chrome (Selenium Manager resolves the matching driver)
- Test-account credentials for the demo application

Set the credentials in your shell; do not commit real credentials:

```bash
export ECOM_USERNAME="your-test-account-email"
export ECOM_PASSWORD="your-test-account-password"
```

On Windows PowerShell:

```powershell
$env:ECOM_USERNAME = "your-test-account-email"
$env:ECOM_PASSWORD = "your-test-account-password"
```

## Run tests

Run all tests:

```bash
mvn test
```

Run only the smoke test:

```bash
mvn -Dtest=SmokeTest test
```

Run the order flow for one or more products:

```bash
mvn -Dtest=EcommerceOrderFlowTest test
```

Tests run headless by default. Set `ECOM_HEADLESS=false` to show the browser. Set `CHROME_BIN` only when Chrome/Chromium is installed in a non-standard location.

## Design

Each page object owns its page locators and user actions. `PageObjectManager` creates the page objects for a test, and `BaseTest` creates and closes a fresh WebDriver instance for every test invocation. Explicit waits are used for asynchronous page transitions and dynamic content. The application URL is configured in `src/main/resources/application.properties` and can be overridden with `ECOM_BASE_URL`.

The regression test reads its product list from `src/test/resources/products.txt`. Browser and test dependencies are managed by Maven, and the GitHub Actions workflow runs the same `mvn test` command used locally.

## Jenkins

The `Jenkinsfile` supports `SmokeTest` (default), `EcommerceOrderFlowTest`, and `AllTests`. Configure the Jenkins agent with Java 17+, Maven 3.8+, and Google Chrome/Chromium. In Jenkins, add a **Username with password** credential with ID `ecom-test-account` for the demo application. Do not put account credentials in the Jenkinsfile or source control. Selenium Manager resolves the matching browser driver.
