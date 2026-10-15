package com.ecommerce.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class AppConfig {
    private static final Properties PROPERTIES = loadProperties();

    private AppConfig() {
    }

    public static String get(String propertyName, String environmentVariable) {
        String override = System.getenv(environmentVariable);
        if (override != null && !override.isBlank()) {
            return override;
        }

        String value = PROPERTIES.getProperty(propertyName);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing application property: " + propertyName);
        }
        return value;
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = AppConfig.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new IllegalStateException("Missing application.properties on the classpath");
            }
            properties.load(input);
            return properties;
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to load application.properties", exception);
        }
    }
}
