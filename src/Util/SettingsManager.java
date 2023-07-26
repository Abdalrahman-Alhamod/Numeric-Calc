package Util;

import java.io.*;
import java.util.Properties;

/**
 * The SettingsManager class handles reading and writing application settings to a properties file.
 * It provides methods to get and set various settings such as light theme, dark theme, and accuracy.
 * Settings are saved to a file named "config.properties" in the current directory.
 */
@SuppressWarnings("all")
public class SettingsManager {
    private static final String PROPERTIES_FILE = "config.properties";
    private final Properties properties;

    /**
     * Constructs a new SettingsManager instance.
     * Initializes the properties object and loads settings from the "config.properties" file if it exists.
     * If the file does not exist, default settings are used, and a new "config.properties" file is created.
     */
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

    /**
     * Saves the current settings to the "config.properties" file.
     * If an I/O exception occurs during the save process, it is caught and ignored.
     */
    public void saveSettings() {
        try {
            OutputStream outputStream = new FileOutputStream(PROPERTIES_FILE);
            properties.store(outputStream, "Application Settings");
            outputStream.close();
        } catch (IOException e) {
            // Handle file not found or other exceptions
        }
    }

    /**
     * Gets the currently set light theme.
     *
     * @return The name of the current light theme, or "Light Flat" as the default if not set.
     */
    public String getLightTheme() {
        return properties.getProperty("lightTheme", "Light Flat");
    }

    /**
     * Sets the light theme.
     *
     * @param theme The name of the light theme to set.
     */
    public void setLightTheme(String theme) {
        properties.setProperty("lightTheme", theme);
    }

    /**
     * Gets the currently set dark theme.
     *
     * @return The name of the current dark theme, or "One Dark" as the default if not set.
     */
    public String getDarkTheme() {
        return properties.getProperty("darkTheme", "One Dark");
    }

    /**
     * Sets the dark theme.
     *
     * @param theme The name of the dark theme to set.
     */
    public void setDarkTheme(String theme) {
        properties.setProperty("darkTheme", theme);
    }

    /**
     * Gets the currently set accuracy value.
     *
     * @return The current accuracy value, or 10 as the default if not set.
     */
    public int getAccuracy() {
        return Integer.parseInt(properties.getProperty("accuracy", "10"));
    }

    /**
     * Sets the accuracy value.
     *
     * @param accuracy The accuracy value to set.
     */
    public void setAccuracy(int accuracy) {
        properties.setProperty("accuracy", Integer.toString(accuracy));
    }
}
