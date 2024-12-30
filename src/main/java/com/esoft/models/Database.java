package com.esoft.models;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Database {
    private static Properties properties = new Properties();

    static {
        try (InputStream inputStream = Database.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (inputStream == null) {
                System.err.println("Sorry, unable to find db.properties");
            }
            properties.load(inputStream);
        } catch (IOException e) {
            System.err.println("Failed to load database properties file: " + e.getMessage());
        }

    }

    public static String getUrl() {
        return properties.getProperty("db.url");
    }

    public static String getUsername() {
        return properties.getProperty("db.username");
    }

    public static String getPassword() {
        return properties.getProperty("db.password");
    }

    public static String getDriver() {
        return properties.getProperty("db.driver");
    }

    public static int getPoolSize() {
        return Integer.parseInt(properties.getProperty("db.pool.size", "10"));
    }
}
