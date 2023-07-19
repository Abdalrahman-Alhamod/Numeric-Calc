import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class DarkMode{
    private static HashMap<String, Color> darkModeColors;
    private static HashMap<String, Color> lightModeColors;

    public DarkMode() {
        initDarkModeColors();
        initLightModeColors();
    }

    private void initDarkModeColors() {
        darkModeColors = new HashMap<>();
        darkModeColors.put("control", new Color(30, 40, 60));
        darkModeColors.put("info", new Color(128, 128, 128));
        darkModeColors.put("nimbusBase", new Color(11, 7, 13));
        darkModeColors.put("nimbusFocus", new Color(46, 91, 161));
        darkModeColors.put("nimbusBorder", new Color(49, 49, 49));
        darkModeColors.put("nimbusLightBackground", new Color(30, 40, 60));
        darkModeColors.put("nimbusSelectionBackground", new Color(64, 80, 128));
        darkModeColors.put("text", new Color(230, 230, 230));
        darkModeColors.put("nimbusDisabledText", new Color(11, 7, 13));
        darkModeColors.put("TextField.foreground", new Color(230, 230, 230));
        darkModeColors.put("TextField.caretForeground", Color.WHITE);
        darkModeColors.put("FormattedTextField.foreground", new Color(230, 230, 230));
        darkModeColors.put("PasswordField.foreground", new Color(230, 230, 230));
        darkModeColors.put("TextArea.foreground", new Color(230, 230, 230));
        darkModeColors.put("TextArea.caretForeground", Color.WHITE);
        darkModeColors.put("Button.foreground", Color.WHITE);
        darkModeColors.put("Button.background", new Color(40, 70, 100));
        darkModeColors.put("ToggleButton.foreground", Color.WHITE);
        darkModeColors.put("ToggleButton.background", new Color(40, 70, 100));
        darkModeColors.put("RadioButton.foreground", Color.WHITE);
        darkModeColors.put("RadioButton.background", new Color(30, 40, 60));
        darkModeColors.put("CheckBox.foreground", Color.WHITE);
        darkModeColors.put("CheckBox.background", new Color(30, 40, 60));
        darkModeColors.put("MenuBar:Menu[Selected].textForeground", Color.WHITE);
        darkModeColors.put("MenuItem.foreground", Color.WHITE);
        darkModeColors.put("MenuItem.background", new Color(40, 70, 100));
        darkModeColors.put("Menu.foreground", Color.WHITE);
        darkModeColors.put("Menu.background", new Color(30, 40, 60));
        darkModeColors.put("PopupMenu.border", new Color(49, 49, 49));
        darkModeColors.put("PopupMenu.background", new Color(30, 40, 60));
        darkModeColors.put("ScrollBar.thumb", new Color(64, 80, 128));
        darkModeColors.put("ScrollBar.track", new Color(30, 40, 60));
        darkModeColors.put("ScrollPane.background", new Color(30, 40, 60));
        darkModeColors.put("Viewport.background", new Color(30, 40, 60));
        darkModeColors.put("List.foreground", Color.WHITE);
        darkModeColors.put("List.background", new Color(30, 40, 60));
        darkModeColors.put("Table.foreground", Color.WHITE);
        darkModeColors.put("Table.background", new Color(30, 40, 60));
        darkModeColors.put("Table.gridColor", new Color(49, 49, 49));
        darkModeColors.put("TableHeader.foreground", Color.WHITE);
        darkModeColors.put("TableHeader.background", new Color(46, 91, 161));
        darkModeColors.put("TabbedPane.foreground", Color.WHITE);
        darkModeColors.put("TabbedPane.background", new Color(30, 40, 60));
        darkModeColors.put("TabbedPane.selected", new Color(64, 80, 128));
        darkModeColors.put("TabbedPane.darkShadow", new Color(30, 40, 60));
        darkModeColors.put("TabbedPane.shadow", new Color(30, 40, 60));
        darkModeColors.put("TabbedPane.focus", new Color(30, 40, 60));
        darkModeColors.put("TabbedPane.borderHightlightColor", new Color(30, 40, 60));
    }

    private void initLightModeColors() {
        lightModeColors = new HashMap<>();
        // Iterate over the keys and values in the original UIManager code
        for (Map.Entry<Object, Object> entry : UIManager.getLookAndFeelDefaults().entrySet()) {
            if (entry.getKey() instanceof String key && entry.getValue() instanceof Color color) {

                // Store the key and color in colorMap
                lightModeColors.put(key, color);

            }
        }
    }

    public static void enableDarkMode() {
        try {
            setNewColors(darkModeColors);
            updateUIComponents();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void disableDarkMode() {
        try {
            setNewColors(lightModeColors);
            updateUIComponents();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void updateUIComponents() {
        for (Window window : Window.getWindows()) {
            SwingUtilities.updateComponentTreeUI(window);
            if (window instanceof JFrame frame) {
                frame.pack();
            }
        }
    }

    private static void setNewColors(HashMap<String, Color> newColors) {
        // Now, to set the default colors in UIManager, use the lightThemeColors HashMap
        for (Map.Entry<String, Color> entry : newColors.entrySet()) {
            String key = entry.getKey();
            Color color = entry.getValue();

            UIManager.put(key, color);
        }
    }
}
