package Util;

import java.io.*;
import java.util.Properties;

public class SettingsManager {
    private static final String PROPERTIES_FILE = "config.properties";
    private final Properties properties;

    public SettingsManager() {
        properties = new Properties();
        try {
            File file = new File(PROPERTIES_FILE);
            if (!file.exists()) {
                // If the properties file does not exist, create it with default settings
                properties.setProperty("lightTheme", "Light Flat");
                properties.setProperty("darkTheme", "One Dark");
                properties.setProperty("accuracy", "10");
                saveSettings();
            }

            InputStream inputStream = new FileInputStream(file);
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveSettings() {
        try {
            OutputStream outputStream = new FileOutputStream(PROPERTIES_FILE);
            properties.store(outputStream, "Application Settings");
            outputStream.close();
        } catch (IOException e) {
            // Handle file not found or other exceptions
        }
    }

    public String getLightTheme() {
        return properties.getProperty("lightTheme", "Light Flat");
    }

    public void setLightTheme(String theme) {
        properties.setProperty("lightTheme", theme);
    }

    public String getDarkTheme() {
        return properties.getProperty("darkTheme", "One Dark");
    }

    public void setDarkTheme(String theme) {
        properties.setProperty("darkTheme", theme);
    }

    public int getAccuracy() {
        return Integer.parseInt(properties.getProperty("accuracy", "10"));
    }

    public void setAccuracy(int accuracy) {
        properties.setProperty("accuracy", Integer.toString(accuracy));
    }
}
