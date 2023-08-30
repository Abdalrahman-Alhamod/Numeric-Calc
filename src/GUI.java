import Functions.ExpressionFunction;
import Functions.PointsFunction;
import Functions.Polynomial;
import Numerics.*;
import Util.Accuracy;
import Util.EvaluateString;
import Util.SettingsManager;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.*;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatAtomOneDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatAtomOneLightIJTheme;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.URI;
import java.util.*;
import java.util.function.Consumer;

/**
 * GUI Class
 * <p>
 * This class represents the graphical user interface of the application. It provides
 * a visual interface for the user to interact with and perform various mathematical
 * operations such as interpolation, integration, differentiation, solving equations, etc.
 */
@SuppressWarnings("all")
public class GUI {

    /**
     * The font used for displaying text in Arabic.
     */
    private final String arabicFont = "Segoe UI";

    /**
     * The font used for displaying text in English.
     */
    private final String englishFont = "Calibri";

    /**
     * A boolean representing whether the dark mode theme is enabled or not.
     * If set to true, the application is in dark mode; otherwise, it's in light mode.
     */
    private boolean darkModeEnabled;

    /**
     * The settings manager responsible for managing application settings.
     */
    private SettingsManager settings;

    /**
     * The text and layout orientation of the component, such as left-to-right or right-to-left.
     */
    private ComponentOrientation orientation;
    /**
     * The main font used in the GUI.
     */
    private String mainFont;
    /**
     * The secondary font used in the GUI.
     */
    private String secondFont;
    /**
     * The font used for buttons.
     */
    private String buttonFont;
    /**
     * The main frame of the application.
     */
    private JFrame mainFrame;
    /**
     * The panel displayed at the start of the application.
     */
    private JPanel startPanel;
    /**
     * The panel for choosing a function type.
     */
    private JPanel chooseFunctionPanel;
    /**
     * The panel for performing interpolation.
     */
    private JPanel interpolationPanel;
    /**
     * The panel for performing integration.
     */
    private JPanel integralPanel;
    /**
     * The panel for performing differentiation.
     */
    private JPanel differentiationPanel;
    /**
     * The panel for solving differential equations.
     */
    private JPanel differentialEquationsPanel;
    /**
     * The panel for solving non-linear equations.
     */
    private JPanel nonLinearEquationsPanel;
    /**
     * The panel for solving systems of non-linear equations.
     */
    private JPanel systemOfNonLinearEquationsPanel;
    /**
     * The panel for polynomial operations.
     */
    private JPanel polynomialsPanel;
    /**
     * The panel for entering expression functions.
     */
    private JPanel expressionFunctionPanel;
    /**
     * The panel for entering points functions.
     */
    private JPanel pointsFunctionPanel;
    /**
     * The panel for entering polynomial functions.
     */
    private JPanel polynomialFunctionPanel;
    /**
     * The main application icon.
     */
    private ImageIcon mainIcon;
    /**
     * The icon for the back button.
     */
    private ImageIcon backIcon;
    /**
     * The icon for the home button.
     */
    private ImageIcon homeIcon;
    /**
     * The icon for the information button.
     */
    private ImageIcon infoIcon;
    /**
     * The icon for the solution message.
     */
    private ImageIcon solutionIcon;
    /**
     * The icon for the keyboard (size 64x64).
     */
    private ImageIcon keyboardIcon64;
    /**
     * The icon for the keyboard (size 128x128).
     */
    private ImageIcon keyboardIcon128;
    /**
     * The icon for the innovation image.
     */
    private ImageIcon innovationIcon;
    /**
     * The icon for the Dark Mode image.
     */
    private ImageIcon moonIcon;
    /**
     * The icon for the setting.
     */
    private ImageIcon settingsIcon;
    /**
     * The icon for the setting.
     */
    private ImageIcon settingsKeyIcon;
    /**
     * The icon for the bulb Image.
     */
    private ImageIcon bulbIcon;
    /**
     * The back button for navigation.
     */
    private JButton backButton;
    /**
     * The home button for navigation.
     */
    private JButton homeButton;
    /**
     * A stack to manage the panel navigation.
     */
    private Stack<JPanel> panelsStack;
    /**
     * The current points function.
     */
    private PointsFunction function;
    /**
     * The current polynomial.
     */
    private Polynomial polynomial;
    /**
     * The action to perform on the points function.
     */
    private Consumer<PointsFunction> doAction;
    /**
     * The main light theme selected by the user.
     * The available light theme options are:
     * "Light Flat", "Arc Light", "Arc Orange", "Cyan Light", "Solarized Light", "Gray", "Atom One Light".
     * By default, it is set to "Light Flat".
     */
    private String mainLightTheme = "Light Flat";
    /**
     * The main dark theme selected by the user.
     * The available dark theme options are:
     * "One Dark", "Arc Dark", "Arc Dark Orange", "Carbon", "Dark Flat", "Dark Purple", "Dracula", "Atom One Dark".
     * By default, it is set to "One Dark".
     */
    private String mainDarkTheme = "One Dark";
    /**
     * A consumer function used to update the theme.
     * It takes a FlatLaf instance as a parameter to set the theme accordingly.
     * The theme can be either a light or dark theme based on the user's selection.
     * This function is responsible for applying the selected theme throughout the application.
     * It is typically passed to UI components for dynamic theme updates when the user changes the theme.
     * The specific theme setup methods (e.g., FlatLightLaf.setup(), FlatOneDarkIJTheme.setup(), etc.) can be called
     * using this consumer to change the theme at runtime.
     */
    private Consumer<FlatLaf> updateTheme;

    /**
     * The user's selected locale for language.
     */
    private Locale local;

    /**
     * ResourceBundle for language-related strings and messages.
     */
    private ResourceBundle languageBundle;

    /**
     * The message displayed for invalid inputs.
     */
    private String invalidInputs;

    /**
     * The error message.
     */
    private String error;

    /**
     * The label text for a cancel action.
     */
    private String cancel;

    /**
     * The label text for a continue action.
     */
    private String Continue;

    /**
     * The label text for a confirm action.
     */
    private String Confirm;

    /**
     * The label text for an enter action.
     */
    private String enter;

    /**
     * The label text for an OK action.
     */
    private String OK;

    /**
     * The label text for a solve action.
     */
    private String Solve;

    /**
     * The label text for a generate action.
     */
    private String Generate;

    /**
     * The prompt text for entering degree requirements for a polynomial.
     */
    private String enterDegreeReqPoly;

    /**
     * The prompt text for continuing with polynomial settings.
     */
    private String continueWithPoly;

    /**
     * Constructs a new instance of the GUI class.
     */
    public GUI() {
        try {
            loadSettings();
            initStrings();
            setLightTheme();
            initIcons();
            initMainFrame();
            initMenuBar();
            initPanels();
            updateMainPanel();
            mainFrame.pack();
            mainFrame.setVisible(true);
        } catch (Exception ex) {
            throw ex;
            //JOptionPane.showMessageDialog(null, ex.getMessage(), error, JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Creates a JButton with a hyperlink-like appearance.
     * When clicked, it opens the specified link in the default web browser.
     *
     * @param text the text to display on the button
     * @param link the URL or URI to open when the button is clicked
     * @return the created hyperlink button
     */
    private static JButton createHyperlinkButton(String text, String link) {
        JButton button = new JButton();
        button.setText("<html><a href=\"" + link + "\"><b><font color=\"white\">" + text + "</font></b></a></html>");
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Add an action listener to handle the button click event
        button.addActionListener(e -> {
            try {
                Desktop.getDesktop().browse(new URI(link));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        return button;
    }

    /**
     * Initializes various strings used in the application by retrieving them from the language bundle.
     * These strings include messages, button labels, and user prompts.
     * The retrieved strings are formatted as HTML for proper display in UI components.
     */
    private void initStrings() {
        invalidInputs = "<html>" + languageBundle.getString("invalidInputs") + "</html>";
        error = "<html>" + languageBundle.getString("error") + "</html>";
        cancel = "<html>" + languageBundle.getString("cancelButton") + "</html>";
        enter = "<html>" + languageBundle.getString("enterButton") + "</html>";
        Continue = languageBundle.getString("continueButton");
        Confirm = "<html>" + languageBundle.getString("confirmButton") + "</html>";
        continueWithPoly = "<html>" + languageBundle.getString("continueWithPolyButton") + "</html>";
        OK = "<html>" + languageBundle.getString("okButton") + "</html>";
        Solve = "<html>" + languageBundle.getString("solveButton") + "</html>";
        Generate = languageBundle.getString("generateButton");
        enterDegreeReqPoly = "<html>" + languageBundle.getString("enterDegreeReqPoly") + "</html>";
    }

    /**
     * Initializes the icons used in the GUI.
     * Loads the required image resources and creates ImageIcon instances.
     * Uses best practices for handling resource loading and exception handling.
     */
    private void initIcons() {
        try {
            mainIcon = new ImageIcon(Objects.requireNonNull(GUI.class.getClassLoader().getResource("Icons/function.png")));
            backIcon = new ImageIcon(Objects.requireNonNull(GUI.class.getClassLoader().getResource("Icons/left-arrow.png")));
            homeIcon = new ImageIcon(Objects.requireNonNull(GUI.class.getClassLoader().getResource("Icons/home-button.png")));
            infoIcon = new ImageIcon(Objects.requireNonNull(GUI.class.getClassLoader().getResource("Icons/info.png")));
            solutionIcon = new ImageIcon(Objects.requireNonNull(GUI.class.getClassLoader().getResource("Icons/solution.png")));
            keyboardIcon64 = new ImageIcon(Objects.requireNonNull(GUI.class.getClassLoader().getResource("Icons/keyboard_64.png")));
            keyboardIcon128 = new ImageIcon(Objects.requireNonNull(GUI.class.getClassLoader().getResource("Icons/keyboard_128.png")));
            innovationIcon = new ImageIcon(Objects.requireNonNull(GUI.class.getClassLoader().getResource("Icons/innovation.png")));
            moonIcon = new ImageIcon(Objects.requireNonNull(GUI.class.getClassLoader().getResource("Icons/moon.png")));
            settingsIcon = new ImageIcon(Objects.requireNonNull(GUI.class.getClassLoader().getResource("Icons/settings.png")));
            settingsKeyIcon = new ImageIcon(Objects.requireNonNull(GUI.class.getClassLoader().getResource("Icons/settingsKey.png")));
            bulbIcon = new ImageIcon(Objects.requireNonNull(GUI.class.getClassLoader().getResource("Icons/bulb.png")));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), error, JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Initializes the main frame of the GUI.
     * Configures various properties of the main frame, such as title, icon, size, and behavior.
     * Follows best practices for setting up a JFrame and ensures a consistent user experience.
     */
    private void initMainFrame() {
        mainFrame = new JFrame();
        mainFrame.setTitle("Numerical Analysis Calculator");
        mainFrame.setMinimumSize(new Dimension(940, 620));
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Save settings when the main frame is closed
                saveSettings();
                // Then exit the application
                System.exit(0);
            }
        });
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    /**
     * Initializes the menu bar of the main frame.
     * Configures the back, home, and info buttons, and their respective actions.
     * Create a custom option dialog with clickable links for about and contact information.
     * Follows best practices for setting up a menu bar and creating interactive components.
     */
    private void initMenuBar() {
        JMenuBar mainMenuBar = new JMenuBar();
        mainMenuBar.setLayout(new FlowLayout());

        backButton = new JButton();
        backButton.addActionListener(e -> {
            panelsStack.pop();
            updateMainPanel();
        });
        backButton.setPreferredSize(new Dimension(50, 50));
        backButton.setIcon(backIcon);
        backButton.setFocusPainted(false);

        homeButton = new JButton();
        homeButton.addActionListener(e -> {
            while (panelsStack.size() != 1) {
                panelsStack.pop();
            }
            updateMainPanel();
        });
        homeButton.setPreferredSize(new Dimension(50, 50));
        homeButton.setIcon(homeIcon);
        homeButton.setFocusPainted(false);

        JButton infoButton = new JButton();

        // Create a custom JPanel for the option dialog
        JPanel panel = new JPanel(new GridLayout(0, 1, 0, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setComponentOrientation(orientation);

        JPanel aboutPanel = new JPanel();
        if (local.getLanguage().equals("en"))
            aboutPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        else
            aboutPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JLabel aboutLabel = new JLabel(languageBundle.getString("aboutDescription"));
        aboutLabel.setComponentOrientation(orientation);
        aboutLabel.setFont(new Font(mainFont, Font.BOLD, 15));
        aboutLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        aboutPanel.add(aboutLabel);
        JPanel contactPanel = new JPanel();
        if (local.getLanguage().equals("en"))
            contactPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        else
            contactPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JLabel contactLabel = new JLabel("<html>" + languageBundle.getString("contact") + " : " + "</html>");
        contactLabel.setComponentOrientation(orientation);
        contactLabel.setFont(new Font(mainFont, Font.BOLD, 15));
        contactLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        contactPanel.add(contactLabel);
        // Create JLabels with hyperlink functionality for each link
        JButton docLabel = createHyperlinkButton("Github.com/Abdalrahman-Alhamod/Numeric-Calc", "https://github.com/Abdalrahman-Alhamod/Numeric-Calc");
        docLabel.setBackground(new Color(0, 102, 204)); // Set color to light blue

        JButton emailLabel = createHyperlinkButton("E-mail", "mailto:abd.alrrahman.alhamod@gmail.com");
        emailLabel.setBackground(new Color(204, 0, 0)); // Set color to dark red

        JButton githubLabel = createHyperlinkButton("Github", "https://github.com/Abdalrahman-Alhamod");
        githubLabel.setBackground(new Color(0, 153, 0)); // Set color to light green

        JButton linkedInLabel = createHyperlinkButton("LinkedIn", "https://www.linkedin.com/in/abd-alrrahman-alhamod/");
        linkedInLabel.setBackground(new Color(0, 102, 153)); // Set color to dark blue

        JButton telegramLabel = createHyperlinkButton("Telegram", "https://t.me/Abd_Alrhman_Alhamod");
        telegramLabel.setBackground(new Color(30, 87, 153)); // Set color to a Telegram-like blue


        JPanel links = new JPanel(new GridLayout(2, 2));
        links.setComponentOrientation(orientation);
        links.add(emailLabel);
        links.add(githubLabel);
        links.add(linkedInLabel);
        links.add(telegramLabel);

        // Add the labels to the panel
        panel.add(aboutPanel);
        panel.add(docLabel);
        panel.add(contactPanel);
        panel.add(links);

        // Show the custom option dialog
        String aboutTitle = "<html>" + languageBundle.getString("aboutTitle") + "</html>";
        infoButton.addActionListener(e -> JOptionPane.showOptionDialog(null, panel, aboutTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, innovationIcon, new Object[]{}, null));
        infoButton.setPreferredSize(new Dimension(50, 50));
        infoButton.setIcon(infoIcon);
        infoButton.setFocusPainted(false);

        JButton darkModeButton = new JButton();
        darkModeButton.setPreferredSize(new Dimension(50, 50));
        darkModeButton.setIcon(moonIcon);
        darkModeButton.setFocusPainted(false);
        updateTheme = theme -> {
            try {
                if (!darkModeEnabled) {
                    setLightTheme();
                    aboutLabel.setForeground(UIManager.getColor("Label.foreground"));
                    contactLabel.setForeground(UIManager.getColor("Label.foreground"));
                } else {
                    setDarkTheme();
                    aboutLabel.setForeground(UIManager.getColor("Label.foreground"));
                    contactLabel.setForeground(UIManager.getColor("Label.foreground"));
                }
                SwingUtilities.updateComponentTreeUI(mainFrame); // Replace "yourMainFrame" with your top-level JFrame or JDialog
                mainFrame.revalidate();
                mainFrame.repaint();
                for (JPanel jpanel : getPanels()) {
                    updatePanelUI(jpanel);
                    jpanel.revalidate();
                    jpanel.repaint();
                }
            } catch (Exception ex) {
                // Log the error instead of showing a dialog in the event handler
                JOptionPane.showMessageDialog(null, ex.getMessage(), error, JOptionPane.ERROR_MESSAGE);
            }
        };

        darkModeButton.addActionListener(e -> {
            darkModeEnabled = !darkModeEnabled; // Toggle the dark mode state
            updateTheme.accept(new FlatLightLaf());
        });

        JButton settingsButton = new JButton();
        settingsButton.setPreferredSize(new Dimension(50, 50));
        settingsButton.setIcon(settingsIcon);
        settingsButton.setFocusPainted(false);
        settingsButton.addActionListener(settings -> {
            // inputs : degree
            JLabel enterAccuracyTitle = new JLabel("<html>" + languageBundle.getString("setAccuracy") + " : " + "</html>");
            enterAccuracyTitle.setComponentOrientation(orientation);
            enterAccuracyTitle.setFont(new Font(mainFont, Font.PLAIN, 15));
            enterAccuracyTitle.setHorizontalAlignment(SwingConstants.LEFT);
            enterAccuracyTitle.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));

            SpinnerNumberModel spinnerModel = new SpinnerNumberModel(Accuracy.getValue(), 1, 30, 1);
            JSpinner enterAccuracySp = new JSpinner(spinnerModel);
            JSpinner.NumberEditor editor = (JSpinner.NumberEditor) enterAccuracySp.getEditor();
            editor.getTextField().setColumns(2); // Adjust the width as needed

            JPanel enterAccuracy = new JPanel();
            if (local.getLanguage().equals("en")) {
                enterAccuracy.setLayout(new FlowLayout(FlowLayout.LEFT));
                enterAccuracy.add(enterAccuracyTitle);
                enterAccuracy.add(enterAccuracySp);
            } else {
                enterAccuracy.setLayout(new FlowLayout(FlowLayout.RIGHT));
                enterAccuracy.add(enterAccuracySp);
                enterAccuracy.add(enterAccuracyTitle);
            }
            //enterAccuracy.setBorder(BorderFactory.createEmptyBorder(10, 0, -10, 0));

            JLabel lightThemeLabel = new JLabel("<html>" + languageBundle.getString("setLightTheme") + " : " + "</html>");
            lightThemeLabel.setComponentOrientation(orientation);
            lightThemeLabel.setFont(new Font(mainFont, Font.PLAIN, 15));
            lightThemeLabel.setHorizontalAlignment(SwingConstants.LEFT);

            JComboBox<String> lightThemeCombo = new JComboBox<>();
            lightThemeCombo.addItem("Light Flat");
            lightThemeCombo.addItem("Arc Light");
            lightThemeCombo.addItem("Arc Orange");
            lightThemeCombo.addItem("Cyan Light");
            lightThemeCombo.addItem("Solarized Light");
            lightThemeCombo.addItem("Gray");
            lightThemeCombo.addItem("Atom One Light");
            lightThemeCombo.setSelectedItem(mainLightTheme);

            JPanel lightThemePanel;
            if (local.getLanguage().equals("en")) {
                lightThemePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                lightThemePanel.add(lightThemeLabel);
                lightThemePanel.add(lightThemeCombo);
            } else {
                lightThemePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                lightThemePanel.add(lightThemeCombo);
                lightThemePanel.add(lightThemeLabel);
            }

            JLabel darkThemeLabel = new JLabel("<html>" + languageBundle.getString("setDarkTheme") + " : " + "</html>");
            darkThemeLabel.setComponentOrientation(orientation);
            darkThemeLabel.setFont(new Font(mainFont, Font.PLAIN, 15));
            darkThemeLabel.setHorizontalAlignment(SwingConstants.LEFT);

            JComboBox<String> darkThemeCombo = new JComboBox<>();
            darkThemeCombo.addItem("One Dark");
            darkThemeCombo.addItem("Arc Dark");
            darkThemeCombo.addItem("Arc Dark Orange");
            darkThemeCombo.addItem("Carbon");
            darkThemeCombo.addItem("Dark Flat");
            darkThemeCombo.addItem("Dark Purple");
            darkThemeCombo.addItem("Dracula");
            darkThemeCombo.addItem("Atom One Dark");
            darkThemeCombo.setSelectedItem(mainDarkTheme);

            JPanel darkThemePanel;
            if (local.getLanguage().equals("en")) {
                darkThemePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                darkThemePanel.add(darkThemeLabel);
                darkThemePanel.add(darkThemeCombo);
            } else {
                darkThemePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                darkThemePanel.add(darkThemeCombo);
                darkThemePanel.add(darkThemeLabel);
            }

            JLabel languageLabel = new JLabel("<html>" + languageBundle.getString("language") + " : " + "</html>");
            languageLabel.setComponentOrientation(orientation);
            languageLabel.setFont(new Font(mainFont, Font.PLAIN, 15));
            languageLabel.setHorizontalAlignment(SwingConstants.LEFT);

            JComboBox<String> languageCombo = new JComboBox<>();
            languageCombo.addItem("English - الإنكليزية");
            languageCombo.addItem("Arabic - العربية");
            String selectedLanguage;
            if (local.getLanguage().equals("en"))
                selectedLanguage = "English - الإنكليزية";
            else
                selectedLanguage = "Arabic - العربية";
            languageCombo.setSelectedItem(selectedLanguage);
            boolean[] isLangaugeChanged = {false};
            languageCombo.addActionListener(action -> {
                isLangaugeChanged[0] = true;
            });

            JPanel languagePanel;
            if (local.getLanguage().equals("en")) {
                languagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                languagePanel.add(languageLabel);
                languagePanel.add(languageCombo);
            } else {
                languagePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                languagePanel.add(languageCombo);
                languagePanel.add(languageLabel);
            }

            JPanel settingsPanel = new JPanel(new GridBagLayout());
            settingsPanel.setComponentOrientation(orientation);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(5, 5, 5, 5);
            settingsPanel.add(enterAccuracy, gbc);
            gbc.gridy++;
            if (local.getLanguage().equals("en"))
                gbc.insets = new Insets(5, -163, 5, 5);
            else
                gbc.insets = new Insets(5, 20, 5, -27);
            settingsPanel.add(lightThemePanel, gbc);
            if (local.getLanguage().equals("en"))
                gbc.insets = new Insets(5, -162, 5, 5);
            else
                gbc.insets = new Insets(5, 20, 5, -25);
            gbc.gridy++;
            settingsPanel.add(darkThemePanel, gbc);
            if (local.getLanguage().equals("en"))
                gbc.insets = new Insets(5, -195, 5, 5);
            else
                gbc.insets = new Insets(5, 20, 5, -125);
            gbc.gridy++;
            settingsPanel.add(languagePanel, gbc);

            JButton confirmButton = new JButton(Confirm);
            confirmButton.setFocusPainted(false);
            Color customGreen = new Color(34, 139, 34);  // RGB values for green color
            confirmButton.setBackground(customGreen);
            confirmButton.setForeground(Color.white);  // Set the text color to white for better visibility

            JButton defaultButton = new JButton("<html>" + languageBundle.getString("defaultButton") + "</html>");
            defaultButton.setFocusPainted(false);
            defaultButton.setBackground(Color.lightGray);
            defaultButton.setForeground(Color.black);  // Set the text color to white for better visibility

            String settingsTitle = "<html>" + languageBundle.getString("settingTitle") + "</html>";
            JButton cancelButton = new JButton(cancel);
            cancelButton.setFocusPainted(false);

            Object[] buttons = {cancelButton, defaultButton, confirmButton};

            cancelButton.addActionListener(cancel -> {
                Window optionDialog = SwingUtilities.getWindowAncestor(enterAccuracy);
                optionDialog.dispose();
            });
            confirmButton.addActionListener(solve -> {
                Window optionDialog = SwingUtilities.getWindowAncestor(enterAccuracy);
                int newAccuracy = (int) enterAccuracySp.getValue();
                Accuracy.setValue(newAccuracy);
                mainLightTheme = String.valueOf(lightThemeCombo.getSelectedItem());
                mainDarkTheme = String.valueOf(darkThemeCombo.getSelectedItem());
                if (isLangaugeChanged[0]) {
                    String restartMessage = "<html><div style='padding: 10px;'>" + languageBundle.getString("restartMessage") + "</div></html>";
                    Object[] options = {OK, cancel};
                    int result = JOptionPane.showOptionDialog(null, restartMessage, settingsTitle, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    if (result == 0) {
                        String lang;
                        if (languageCombo.getSelectedItem().equals("English - الإنكليزية"))
                            lang = "en";
                        else
                            lang = "ar";
                        local = new Locale(lang);
                        optionDialog.dispose();
                        saveSettings();
                        mainFrame.dispose();
                        SwingUtilities.invokeLater(GUI::new);
                    }
                } else {
                    optionDialog.dispose();
                    updateTheme.accept(new FlatLightLaf());
                }
            });

            defaultButton.addActionListener(defaultSettings -> {
                Window optionDialog = SwingUtilities.getWindowAncestor(enterAccuracy);
                optionDialog.dispose();
                Accuracy.setValue(10);
                mainLightTheme = "Light Flat";
                mainDarkTheme = "One Dark";
                updateTheme.accept(new FlatLightLaf());
            });
            JOptionPane.showOptionDialog(null, settingsPanel, settingsTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, settingsKeyIcon, buttons, null);

        });

        JLabel mainIconLabel = new JLabel();
        mainIconLabel.setIcon(mainIcon);

        mainMenuBar.add(mainIconLabel);
        mainMenuBar.add(backButton);
        mainMenuBar.add(homeButton);
        mainMenuBar.add(darkModeButton);
        mainMenuBar.add(settingsButton);
        mainMenuBar.add(infoButton);
        mainFrame.setJMenuBar(mainMenuBar);
    }

    /**
     * Initializes the panels used in the GUI.
     * Calls individual methods to initialize each panel.
     * Creates a stack to keep track of the panels in the GUI.
     * Set the start panel as the initial panel in the stack.
     * Follows best practices for initializing panels and managing panel stack.
     */
    private void initPanels() {
        initStartPanel();
        initChooseFunctionPanel();
        initInterpolationPanel();
        initIntegralPanel();
        initDiffPanel();
        initDiffEQPanel();
        initNonLinEQPanel();
        initPolyPanel();
        initExpFuncPanel();
        iniPtsFuncPanel();
        initPolyFuncPanel();
        initSysNonLinEqPanel();
        panelsStack = new Stack<>();
        panelsStack.add(startPanel);
    }

    /**
     * Initializes the start panel in the GUI.
     * Sets the name, size, and background color of the panel.
     * Defines the layout for the panel and adds cards for different functionalities.
     * Each card represents a specific functionality with a title, description, and enter button.
     * Adds action listeners to the enter buttons to handle panel navigation.
     * Follows best practices for initializing panels and adding components.
     */
    private void initStartPanel() {
        startPanel = new JPanel();
        startPanel.setName("Start");
        startPanel.setPreferredSize(mainFrame.getSize());
        startPanel.setBackground(new Color(100, 100, 100));
        startPanel.setLayout(new BorderLayout(10, 5));
        GridLayout startLayout = new GridLayout(3, 2, 5, 5);
        JPanel cardsPanel = new JPanel(startLayout);
        cardsPanel.setBackground(new Color(100, 100, 100));
        cardsPanel.setComponentOrientation(orientation);
        //***********************************************************************

        //init Interpolation Card
        String title = "<html>" + languageBundle.getString("interpolationTitle") + "</html>";
        String description = "<html>" + languageBundle.getString("interpolationDescription") + "</html>";
        String button = "<html>" + languageBundle.getString("enterButton") + "</html>";
        ActionListener enterInterpolation = e -> {
            panelsStack.add(interpolationPanel);
            updateMainPanel();
        };
        JPanel interpolationCard = createCard(title, description, button, enterInterpolation);
        startPanel.add(interpolationCard, BorderLayout.NORTH);
        //***********************************************************************

        //init Integral Card
        title = "<html>" + languageBundle.getString("integralTitle") + "</html>";
        description = "<html>" + languageBundle.getString("integralDescription") + "</html>";
        ActionListener enterIntegral = e -> {
            panelsStack.add(integralPanel);
            updateMainPanel();
        };
        JPanel integralCard = createCard(title, description, button, enterIntegral);
        cardsPanel.add(integralCard);

        //***********************************************************************

        //init Differentiation Card
        title = "<html>" + languageBundle.getString("differentiationTitle") + "</html>";
        description = "<html>" + languageBundle.getString("differentiationDescription") + "</html>";
        ;
        ActionListener enterDifferentiation = e -> {
            panelsStack.add(differentiationPanel);
            updateMainPanel();
        };
        JPanel diffCard = createCard(title, description, button, enterDifferentiation);
        cardsPanel.add(diffCard);

        //***********************************************************************

        //init Differential Equations Card
        title = "<html>" + languageBundle.getString("differentialTitle") + "</html>";
        description = "<html>" + languageBundle.getString("differentialDescription") + "</html>";
        ActionListener enterDiffEQ = e -> {
            panelsStack.add(differentialEquationsPanel);
            updateMainPanel();
        };
        JPanel diffEQCard = createCard(title, description, button, enterDiffEQ);
        cardsPanel.add(diffEQCard);

        //***********************************************************************

        //init Non-Linear Equations Card
        title = "<html>" + languageBundle.getString("nonLinearEquationsTitle") + "</html>";
        description = "<html>" + languageBundle.getString("nonLinearEquationsDescription") + "</html>";
        ActionListener enterNonLinEQ = e -> {
            panelsStack.add(nonLinearEquationsPanel);
            updateMainPanel();
        };
        JPanel nonLinEQCard = createCard(title, description, button, enterNonLinEQ);
        cardsPanel.add(nonLinEQCard);


        //***********************************************************************

        //init System Of Non-Linear Equations Card
        title = "<html>" + languageBundle.getString("systemOfNonLinearEquationsTitle") + "</html>";
        description = "<html>" + languageBundle.getString("systemOfNonLinearEquationsDescription") + "</html>";
        ActionListener enterSysNonLinEQ = e -> {
            panelsStack.add(systemOfNonLinearEquationsPanel);
            updateMainPanel();
        };
        JPanel sysOfNonLinEQCard = createCard(title, description, button, enterSysNonLinEQ);
        cardsPanel.add(sysOfNonLinEQCard);


        //***********************************************************************

        //init Differential Equations Card
        title = "<html>" + languageBundle.getString("polynomialsTitle") + "</html>";
        description = "<html>" + languageBundle.getString("polynomialsDescription") + "</html>";
        ActionListener enterPolys = e -> {
            polynomial = null;
            panelsStack.add(polynomialsPanel);
            updateMainPanel();
        };
        JPanel polysCard = createCard(title, description, button, enterPolys);
        cardsPanel.add(polysCard);

        //***********************************************************************

        startPanel.add(cardsPanel, BorderLayout.CENTER);

    }

    /**
     * Initializes the Choose Function panel.
     * Sets the name, size, and background color of the panel.
     * Creates cards for different function types with corresponding action listeners.
     * Adds the cards to the panel.
     */
    private void initChooseFunctionPanel() {
        chooseFunctionPanel = new JPanel();
        chooseFunctionPanel.setName("Choose Function");
        chooseFunctionPanel.setPreferredSize(mainFrame.getSize());
        chooseFunctionPanel.setBackground(new Color(100, 100, 100));
        GridLayout startLayout = new GridLayout(3, 1);
        startLayout.setHgap(5);
        startLayout.setVgap(5);
        chooseFunctionPanel.setLayout(startLayout);


        ActionListener enterExpressionFunction = e -> {
            panelsStack.add(expressionFunctionPanel);
            updateMainPanel();
        };
        String title = "<html>" + languageBundle.getString("expressionFunctionTitle") + "</html>";
        String description = "<html>" + languageBundle.getString("expressionFunctionDescription") + "</html>";
        String button = "<html>" + languageBundle.getString("enterButton") + "</html>";
        JPanel expressionFunctionCard = createCard(title, description, button, enterExpressionFunction);


        ActionListener enterPointsFunction = e -> {
            panelsStack.add(pointsFunctionPanel);
            updateMainPanel();
        };
        title = "<html>" + languageBundle.getString("pointsFunctionTitle") + "</html>";
        description = "<html>" + languageBundle.getString("pointsFunctionDescription") + "</html>";
        JPanel pointsFunctionCard = createCard(title, description, button, enterPointsFunction);

        ActionListener enterPolynomialFunction = e -> {
            panelsStack.add(polynomialFunctionPanel);
            updateMainPanel();
        };
        title = "<html>" + languageBundle.getString("polynomialFunctionTitle") + "</html>";
        description = "<html>" + languageBundle.getString("polynomialFunctionDescription") + "</html>";
        JPanel polynomialFunctionCard = createCard(title, description, button, enterPolynomialFunction);


        chooseFunctionPanel.add(expressionFunctionCard);
        chooseFunctionPanel.add(pointsFunctionCard);
        chooseFunctionPanel.add(polynomialFunctionCard);
    }

    /**
     * Initializes the Interpolation panel.
     * Sets the name, size, and background color of the panel.
     * Creates cards for Interpolation methods with corresponding action listeners.
     * Adds the cards to the panel.
     */
    private void initInterpolationPanel() {
        interpolationPanel = new JPanel();
        interpolationPanel.setName("Interpolation");
        interpolationPanel.setPreferredSize(mainFrame.getSize());
        interpolationPanel.setBackground(new Color(100, 100, 100));
        GridLayout startLayout = new GridLayout(4, 2);
        startLayout.setHgap(5);
        startLayout.setVgap(5);
        interpolationPanel.setLayout(startLayout);
        interpolationPanel.setComponentOrientation(orientation);
        String interpolationTitle = "<html>" + languageBundle.getString("interpolationTitle") + "</html>";
        String interpolationNSH = "<html>" + languageBundle.getString("interpolationAnswerNoSH") + " : " + "</html>";

        //***********************************************************************

        //init General Method Card
        String title = "<html>" + languageBundle.getString("generalMethodTitle") + "</html>";
        String description = "<html>" + languageBundle.getString("generalMethodDescription") + "</html>";
        String button = "<html>" + languageBundle.getString("enterButton") + "</html>";

        ActionListener enterGeneralMethod = e -> {
            doAction = pointsFunction -> {
                try {
                    Polynomial ans = Interpolation.GeneralMethod.getIFAP(pointsFunction);
                    //create title
                    JLabel interTitle = new JLabel();
                    interTitle.setText("<html>" + languageBundle.getString("interpolationAnswer") + " " + languageBundle.getString("generalMethodTitle") + " : " + "</html>");
                    interTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    //create ans scrolled
                    JTextArea polyAns = new JTextArea();
                    polyAns.append("P(x) : ");
                    polyAns.append(ans.toString());
                    polyAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    polyAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(polyAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(250, 65));

                    //create content panel
                    JPanel contentPanel = new JPanel(new GridLayout(2, 1));
                    contentPanel.add(interTitle);
                    contentPanel.add(polyAnsScrollPane);
                    String[] response = {cancel, continueWithPoly};

                    int feed = JOptionPane.showOptionDialog(null, contentPanel, interpolationTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon, response, response[1]);
                    if (feed == 1) {
                        polynomial = ans;
                        panelsStack.add(polynomialsPanel);
                        updateMainPanel();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? invalidInputs : ex.getMessage(), error, JOptionPane.ERROR_MESSAGE);
                }
            };
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel generalMethodCard = createCard(title, button, enterGeneralMethod, description);
        interpolationPanel.add(generalMethodCard);

        //***********************************************************************

        //init Lagrange Card
        title = "<html>" + languageBundle.getString("lagrangeTitle") + "</html>";
        description = "<html>" + languageBundle.getString("lagrangeDescription") + "</html>";
        ActionListener enterLagrange = e -> {
            doAction = pointsFunction -> {
                try {
                    Polynomial ans = Interpolation.Lagrange.getIFAP(pointsFunction);

                    //create answer title
                    JLabel interTitle = new JLabel();
                    interTitle.setText("<html>" + languageBundle.getString("interpolationAnswer") + " " + languageBundle.getString("lagrangeTitle") + " : " + "</html>");
                    interTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    //create answer scrolled
                    JTextArea polyAns = new JTextArea();
                    polyAns.append("P(x) : ");
                    polyAns.append(ans.toString());
                    polyAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    polyAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(polyAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(300, 70));

                    //create ans no shorthand title
                    JLabel shortPolyTitle = new JLabel();
                    shortPolyTitle.setText(interpolationNSH);
                    shortPolyTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    String asnNSH = Interpolation.Lagrange.getIFASNS(pointsFunction);

                    //create answer no shorthand scrolled
                    JTextArea polyAnsNSH = new JTextArea();
                    polyAnsNSH.append("P(x) : ");
                    polyAnsNSH.append(asnNSH);
                    polyAnsNSH.setFont(new Font(mainFont, Font.PLAIN, 20));
                    polyAnsNSH.setEditable(false);
                    JScrollPane polyAnsNSHScrollPane = new JScrollPane(polyAnsNSH);
                    polyAnsNSHScrollPane.setPreferredSize(new Dimension(300, 70));

                    //create content panel
                    JPanel contentPanel = new JPanel(new GridLayout(4, 1, 0, -20));
                    contentPanel.setBorder(BorderFactory.createEmptyBorder(-20, 0, 0, 0));
                    contentPanel.add(interTitle);
                    contentPanel.add(polyAnsScrollPane);
                    contentPanel.add(shortPolyTitle);
                    contentPanel.add(polyAnsNSHScrollPane);


                    String[] response = {cancel, continueWithPoly};

                    int feed = JOptionPane.showOptionDialog(null, contentPanel, interpolationTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon, response, response[1]);
                    if (feed == 1) {
                        polynomial = ans;
                        panelsStack.add(polynomialsPanel);
                        updateMainPanel();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? invalidInputs : ex.getMessage(), error, JOptionPane.ERROR_MESSAGE);
                }
            };
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel lagrangeCard = createCard(title, button, enterLagrange, description);
        interpolationPanel.add(lagrangeCard);

        //***********************************************************************

        //init Newton-Gregory Forward Subtraction Card
        title = "<html>" + languageBundle.getString("NGFSTitle") + "</html>";
        description = languageBundle.getString("NGFSDescription");
        ActionListener enterNGFS = e -> {
            doAction = pointsFunction -> {
                try {

                    // inputs : degree
                    JLabel enterDegreeTitle = new JLabel(enterDegreeReqPoly);
                    enterDegreeTitle.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
                    enterDegreeTitle.setHorizontalAlignment(SwingConstants.LEFT);

                    SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 100, 1);
                    JSpinner enterDegreeSp = new JSpinner(spinnerModel);
                    JSpinner.NumberEditor editor = (JSpinner.NumberEditor) enterDegreeSp.getEditor();
                    editor.getTextField().setColumns(2); // Adjust the width as needed

                    JPanel enterDegree = new JPanel();
                    if (local.getLanguage().equals("en")) {
                        enterDegree.setLayout(new FlowLayout(FlowLayout.LEFT));
                        enterDegree.add(enterDegreeTitle);
                        enterDegree.add(enterDegreeSp);
                    } else {
                        enterDegree.setLayout(new FlowLayout(FlowLayout.RIGHT));
                        enterDegree.add(enterDegreeSp);
                        enterDegree.add(enterDegreeTitle);
                    }
                    enterDegree.setBorder(BorderFactory.createEmptyBorder(10, 0, -10, 0));

                    JButton solveButton = new JButton(Solve);
                    solveButton.setFocusPainted(false);
                    Color customGreen = new Color(34, 139, 34);  // RGB values for green color
                    solveButton.setBackground(customGreen);
                    solveButton.setForeground(Color.white);  // Set the text color to white for better visibility

                    JButton cancelButton = new JButton(cancel);
                    cancelButton.setFocusPainted(false);

                    Object[] buttons = {cancelButton, solveButton};

                    final boolean[] cancelPressed = {false};
                    cancelButton.addActionListener(cancel -> {
                        cancelPressed[0] = true;
                        Window optionDialog = SwingUtilities.getWindowAncestor(enterDegree);
                        optionDialog.dispose();
                    });

                    final int[] degree = {0};

                    solveButton.addActionListener(solve -> {
                        Window optionDialog = SwingUtilities.getWindowAncestor(enterDegree);
                        optionDialog.dispose();
                        degree[0] = (int) enterDegreeSp.getValue();
                    });

                    JOptionPane.showOptionDialog(null, enterDegree, interpolationTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon64, buttons, null);
                    if (cancelPressed[0]) return;

                    ArrayList<BigDecimal> values = Interpolation.Newton_GregoryForwardSubtractions.getUDV(function);

                    //create table values title
                    JLabel tableTitle = new JLabel();
                    tableTitle.setText("<html>" + languageBundle.getString("NFGSUDT") + " : " + "</html>");
                    tableTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    //create table value scrolled
                    JTextArea tableAns = new JTextArea();
                    tableAns.append(values.toString());
                    tableAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    tableAns.setEditable(false);
                    JScrollPane tableAnsScrollPane = new JScrollPane(tableAns);
                    tableAnsScrollPane.setPreferredSize(new Dimension(300, 70));

                    Polynomial ans = Interpolation.Newton_GregoryForwardSubtractions.getIFAP(pointsFunction, degree[0]);

                    JLabel interTitle = new JLabel();
                    interTitle.setText("<html>" + languageBundle.getString("interpolationAnswer") + " " + languageBundle.getString("NGFSTitle") + " : " + "</html>");
                    interTitle.setFont(new Font(mainFont, Font.PLAIN, 20));


                    JTextArea polyAns = new JTextArea();
                    polyAns.append("P(x) : ");
                    polyAns.append(ans.toString());
                    polyAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    polyAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(polyAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(300, 70));

                    JLabel shortPolyTitle = new JLabel();
                    shortPolyTitle.setText(interpolationNSH);
                    shortPolyTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    String asnNSH = Interpolation.Newton_GregoryForwardSubtractions.getIFASNS(pointsFunction, degree[0]);

                    JTextArea polyAnsNSH = new JTextArea();
                    polyAnsNSH.append("P(x) : ");
                    polyAnsNSH.append(asnNSH);
                    polyAnsNSH.setFont(new Font(mainFont, Font.PLAIN, 20));
                    polyAnsNSH.setEditable(false);
                    JScrollPane polyAnsNSHScrollPane = new JScrollPane(polyAnsNSH);
                    polyAnsNSHScrollPane.setPreferredSize(new Dimension(300, 70));


                    JPanel contentPanel = new JPanel(new GridLayout(6, 1, 0, -20));
                    contentPanel.setBorder(BorderFactory.createEmptyBorder(-20, 0, 0, 0));
                    contentPanel.add(tableTitle);
                    contentPanel.add(tableAnsScrollPane);
                    contentPanel.add(interTitle);
                    contentPanel.add(polyAnsScrollPane);
                    contentPanel.add(shortPolyTitle);
                    contentPanel.add(polyAnsNSHScrollPane);


                    String[] response = {cancel, continueWithPoly};

                    int feed = JOptionPane.showOptionDialog(null, contentPanel, interpolationTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon, response, response[1]);
                    if (feed == 1) {
                        polynomial = ans;
                        panelsStack.add(polynomialsPanel);
                        updateMainPanel();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? invalidInputs : ex.getMessage(), error, JOptionPane.ERROR_MESSAGE);
                }
            };
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel NGFSCard = createCard(title, button, enterNGFS, description);
        interpolationPanel.add(NGFSCard);

        //***********************************************************************

        //init Newton-Gregory Backward Subtraction Card
        title = "<html>" + languageBundle.getString("NGBSTitle") + "</html>";
        description = languageBundle.getString("NGBSDescription");
        ActionListener enterNGBS = e -> {
            doAction = pointsFunction -> {
                try {

                    // inputs : degree
                    JLabel enterDegreeTitle = new JLabel(enterDegreeReqPoly);
                    enterDegreeTitle.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
                    enterDegreeTitle.setHorizontalAlignment(SwingConstants.LEFT);

                    SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 100, 1);
                    JSpinner enterDegreeSp = new JSpinner(spinnerModel);
                    JSpinner.NumberEditor editor = (JSpinner.NumberEditor) enterDegreeSp.getEditor();
                    editor.getTextField().setColumns(2); // Adjust the width as needed

                    JPanel enterDegree = new JPanel();
                    if (local.getLanguage().equals("en")) {
                        enterDegree.setLayout(new FlowLayout(FlowLayout.LEFT));
                        enterDegree.add(enterDegreeTitle);
                        enterDegree.add(enterDegreeSp);
                    } else {
                        enterDegree.setLayout(new FlowLayout(FlowLayout.RIGHT));
                        enterDegree.add(enterDegreeSp);
                        enterDegree.add(enterDegreeTitle);
                    }
                    enterDegree.setBorder(BorderFactory.createEmptyBorder(10, 0, -10, 0));

                    JButton solveButton = new JButton(Solve);
                    solveButton.setFocusPainted(false);
                    Color customGreen = new Color(34, 139, 34);  // RGB values for green color
                    solveButton.setBackground(customGreen);
                    solveButton.setForeground(Color.white);  // Set the text color to white for better visibility

                    JButton cancelButton = new JButton(cancel);
                    cancelButton.setFocusPainted(false);

                    Object[] buttons = {cancelButton, solveButton};

                    final boolean[] cancelPressed = {false};
                    cancelButton.addActionListener(cancel -> {
                        cancelPressed[0] = true;
                        Window optionDialog = SwingUtilities.getWindowAncestor(enterDegree);
                        optionDialog.dispose();
                    });

                    final int[] degree = {0};

                    solveButton.addActionListener(solve -> {
                        Window optionDialog = SwingUtilities.getWindowAncestor(enterDegree);
                        optionDialog.dispose();
                        degree[0] = (int) enterDegreeSp.getValue();
                    });

                    JOptionPane.showOptionDialog(null, enterDegree, interpolationTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon64, buttons, null);
                    if (cancelPressed[0]) return;


                    ArrayList<BigDecimal> values = Interpolation.Newton_GregoryBackwardSubtractions.getLDV(function);

                    //create table values title
                    JLabel tableTitle = new JLabel();
                    tableTitle.setText("<html>" + languageBundle.getString("NGBSLDT") + " : " + "</html>");
                    tableTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    //create table value scrolled
                    JTextArea tableAns = new JTextArea();
                    tableAns.append(values.toString());
                    tableAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    tableAns.setEditable(false);
                    JScrollPane tableAnsScrollPane = new JScrollPane(tableAns);
                    tableAnsScrollPane.setPreferredSize(new Dimension(300, 70));

                    Polynomial ans = Interpolation.Newton_GregoryBackwardSubtractions.getIFAP(pointsFunction, degree[0]);

                    JLabel interTitle = new JLabel();
                    interTitle.setText("<html>" + languageBundle.getString("interpolationAnswer") + " " + languageBundle.getString("NGBSTitle") + " : " + "</html>");
                    interTitle.setFont(new Font(mainFont, Font.PLAIN, 20));


                    JTextArea polyAns = new JTextArea();
                    polyAns.append("P(x) : ");
                    polyAns.append(ans.toString());
                    polyAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    polyAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(polyAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(300, 70));

                    JLabel shortPolyTitle = new JLabel();
                    shortPolyTitle.setText(interpolationNSH);
                    shortPolyTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    String asnNSH = Interpolation.Newton_GregoryBackwardSubtractions.getIFASNS(pointsFunction, degree[0]);

                    JTextArea polyAnsNSH = new JTextArea();
                    polyAnsNSH.append("P(x) : ");
                    polyAnsNSH.append(asnNSH);
                    polyAnsNSH.setFont(new Font(mainFont, Font.PLAIN, 20));
                    polyAnsNSH.setEditable(false);
                    JScrollPane polyAnsNSHScrollPane = new JScrollPane(polyAnsNSH);
                    polyAnsNSHScrollPane.setPreferredSize(new Dimension(300, 70));


                    JPanel contentPanel = new JPanel(new GridLayout(6, 1, 0, -20));
                    contentPanel.setBorder(BorderFactory.createEmptyBorder(-20, 0, 0, 0));
                    contentPanel.add(tableTitle);
                    contentPanel.add(tableAnsScrollPane);
                    contentPanel.add(interTitle);
                    contentPanel.add(polyAnsScrollPane);
                    contentPanel.add(shortPolyTitle);
                    contentPanel.add(polyAnsNSHScrollPane);


                    String[] response = {cancel, continueWithPoly};

                    int feed = JOptionPane.showOptionDialog(null, contentPanel, interpolationTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon, response, response[1]);
                    if (feed == 1) {
                        polynomial = ans;
                        panelsStack.add(polynomialsPanel);
                        updateMainPanel();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? invalidInputs : ex.getMessage(), error, JOptionPane.ERROR_MESSAGE);
                }
            };
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel NGBSCard = createCard(title, button, enterNGBS, description);
        interpolationPanel.add(NGBSCard);

        //***********************************************************************

        //init Newton Forward Divided Subtractions Equations Card
        title = "<html>" + languageBundle.getString("NFDSTitle") + "</html>";
        description = languageBundle.getString("NFDSDescription");
        ActionListener enterNFDS = e -> {
            doAction = pointsFunction -> {
                try {

                    // inputs : degree
                    JLabel enterDegreeTitle = new JLabel(enterDegreeReqPoly);
                    enterDegreeTitle.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
                    enterDegreeTitle.setHorizontalAlignment(SwingConstants.LEFT);

                    SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 100, 1);
                    JSpinner enterDegreeSp = new JSpinner(spinnerModel);
                    JSpinner.NumberEditor editor = (JSpinner.NumberEditor) enterDegreeSp.getEditor();
                    editor.getTextField().setColumns(2); // Adjust the width as needed

                    JPanel enterDegree = new JPanel();
                    if (local.getLanguage().equals("en")) {
                        enterDegree.setLayout(new FlowLayout(FlowLayout.LEFT));
                        enterDegree.add(enterDegreeTitle);
                        enterDegree.add(enterDegreeSp);
                    } else {
                        enterDegree.setLayout(new FlowLayout(FlowLayout.RIGHT));
                        enterDegree.add(enterDegreeSp);
                        enterDegree.add(enterDegreeTitle);
                    }
                    enterDegree.setBorder(BorderFactory.createEmptyBorder(10, 0, -10, 0));

                    JButton solveButton = new JButton(Solve);
                    solveButton.setFocusPainted(false);
                    Color customGreen = new Color(34, 139, 34);  // RGB values for green color
                    solveButton.setBackground(customGreen);
                    solveButton.setForeground(Color.white);  // Set the text color to white for better visibility

                    JButton cancelButton = new JButton(cancel);
                    cancelButton.setFocusPainted(false);

                    Object[] buttons = {cancelButton, solveButton};

                    final boolean[] cancelPressed = {false};
                    cancelButton.addActionListener(cancel -> {
                        cancelPressed[0] = true;
                        Window optionDialog = SwingUtilities.getWindowAncestor(enterDegree);
                        optionDialog.dispose();
                    });

                    final int[] degree = {0};

                    solveButton.addActionListener(solve -> {
                        Window optionDialog = SwingUtilities.getWindowAncestor(enterDegree);
                        optionDialog.dispose();
                        degree[0] = (int) enterDegreeSp.getValue();
                    });

                    JOptionPane.showOptionDialog(null, enterDegree, interpolationTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon64, buttons, null);
                    if (cancelPressed[0]) return;


                    ArrayList<BigDecimal> values = Interpolation.NewtonForwardDividedSubtractions.getUDV(function);

                    //create table values title
                    JLabel tableTitle = new JLabel();
                    tableTitle.setText("<html>" + languageBundle.getString("NFDSUDT") + " : " + "</html>");
                    tableTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    //create table value scrolled
                    JTextArea tableAns = new JTextArea();
                    tableAns.append(values.toString());
                    tableAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    tableAns.setEditable(false);
                    tableAns.setDisabledTextColor(Color.BLACK);
                    JScrollPane tableAnsScrollPane = new JScrollPane(tableAns);
                    tableAnsScrollPane.setPreferredSize(new Dimension(300, 70));

                    Polynomial ans = Interpolation.NewtonForwardDividedSubtractions.getIFAP(pointsFunction, degree[0]);

                    JLabel interTitle = new JLabel();
                    interTitle.setText("<html>" + languageBundle.getString("interpolationAnswer") + " " + languageBundle.getString("NFDSTitle") + " : " + "</html>");
                    interTitle.setFont(new Font(mainFont, Font.PLAIN, 20));


                    JTextArea polyAns = new JTextArea();
                    polyAns.append("P(x) : ");
                    polyAns.append(ans.toString());
                    polyAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    polyAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(polyAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(300, 70));

                    JLabel shortPolyTitle = new JLabel();
                    shortPolyTitle.setText(interpolationNSH);
                    shortPolyTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    String asnNSH = Interpolation.NewtonForwardDividedSubtractions.getIFASNS(pointsFunction, degree[0]);

                    JTextArea polyAnsNSH = new JTextArea();
                    polyAnsNSH.append("P(x) : ");
                    polyAnsNSH.append(asnNSH);
                    polyAnsNSH.setFont(new Font(mainFont, Font.PLAIN, 20));
                    polyAnsNSH.setEditable(false);
                    JScrollPane polyAnsNSHScrollPane = new JScrollPane(polyAnsNSH);
                    polyAnsNSHScrollPane.setPreferredSize(new Dimension(300, 70));


                    JPanel contentPanel = new JPanel(new GridLayout(6, 1, 0, -20));
                    contentPanel.setBorder(BorderFactory.createEmptyBorder(-20, 0, 0, 0));
                    contentPanel.add(tableTitle);
                    contentPanel.add(tableAnsScrollPane);
                    contentPanel.add(interTitle);
                    contentPanel.add(polyAnsScrollPane);
                    contentPanel.add(shortPolyTitle);
                    contentPanel.add(polyAnsNSHScrollPane);


                    String[] response = {cancel, continueWithPoly};

                    int feed = JOptionPane.showOptionDialog(null, contentPanel, interpolationTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon, response, response[1]);
                    if (feed == 1) {
                        polynomial = ans;
                        panelsStack.add(polynomialsPanel);
                        updateMainPanel();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? invalidInputs : ex.getMessage(), error, JOptionPane.ERROR_MESSAGE);
                }
            };
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel NFDSCard = createCard(title, button, enterNFDS, description);
        interpolationPanel.add(NFDSCard);

        //***********************************************************************

        //init Newton Backward Divided Subtractions Equations Card
        title = "<html>" + languageBundle.getString("NBDSTitle") + "</html>";
        description = languageBundle.getString("NBDSDescription");
        ActionListener enterNDBS = e -> {
            doAction = pointsFunction -> {
                try {

                    // inputs : degree
                    JLabel enterDegreeTitle = new JLabel(enterDegreeReqPoly);
                    enterDegreeTitle.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
                    enterDegreeTitle.setHorizontalAlignment(SwingConstants.LEFT);

                    SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 100, 1);
                    JSpinner enterDegreeSp = new JSpinner(spinnerModel);
                    JSpinner.NumberEditor editor = (JSpinner.NumberEditor) enterDegreeSp.getEditor();
                    editor.getTextField().setColumns(2); // Adjust the width as needed

                    JPanel enterDegree = new JPanel();
                    if (local.getLanguage().equals("en")) {
                        enterDegree.setLayout(new FlowLayout(FlowLayout.LEFT));
                        enterDegree.add(enterDegreeTitle);
                        enterDegree.add(enterDegreeSp);
                    } else {
                        enterDegree.setLayout(new FlowLayout(FlowLayout.RIGHT));
                        enterDegree.add(enterDegreeSp);
                        enterDegree.add(enterDegreeTitle);
                    }
                    enterDegree.setBorder(BorderFactory.createEmptyBorder(10, 0, -10, 0));

                    JButton solveButton = new JButton(Solve);
                    solveButton.setFocusPainted(false);
                    Color customGreen = new Color(34, 139, 34);  // RGB values for green color
                    solveButton.setBackground(customGreen);
                    solveButton.setForeground(Color.white);  // Set the text color to white for better visibility

                    JButton cancelButton = new JButton(cancel);
                    cancelButton.setFocusPainted(false);

                    Object[] buttons = {cancelButton, solveButton};

                    final boolean[] cancelPressed = {false};
                    cancelButton.addActionListener(cancel -> {
                        cancelPressed[0] = true;
                        Window optionDialog = SwingUtilities.getWindowAncestor(enterDegree);
                        optionDialog.dispose();
                    });

                    final int[] degree = {0};

                    solveButton.addActionListener(solve -> {
                        Window optionDialog = SwingUtilities.getWindowAncestor(enterDegree);
                        optionDialog.dispose();
                        degree[0] = (int) enterDegreeSp.getValue();
                    });

                    JOptionPane.showOptionDialog(null, enterDegree, interpolationTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon64, buttons, null);
                    if (cancelPressed[0]) return;


                    ArrayList<BigDecimal> values = Interpolation.NewtonBackwardDividedSubtractions.getLDV(function);

                    //create table values title
                    JLabel tableTitle = new JLabel();
                    tableTitle.setText("<html>" + languageBundle.getString("NBDSLDT") + " : " + "</html>");
                    tableTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    //create table value scrolled
                    JTextArea tableAns = new JTextArea();
                    tableAns.append(values.toString());
                    tableAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    tableAns.setEditable(false);
                    JScrollPane tableAnsScrollPane = new JScrollPane(tableAns);
                    tableAnsScrollPane.setPreferredSize(new Dimension(300, 70));

                    Polynomial ans = Interpolation.NewtonBackwardDividedSubtractions.getIFAP(pointsFunction, degree[0]);

                    JLabel interTitle = new JLabel();
                    interTitle.setText("<html>" + languageBundle.getString("interpolationAnswer") + " " + languageBundle.getString("NBDSTitle") + " : " + "</html>");
                    interTitle.setFont(new Font(mainFont, Font.PLAIN, 20));


                    JTextArea polyAns = new JTextArea();
                    polyAns.append("P(x) : ");
                    polyAns.append(ans.toString());
                    polyAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    polyAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(polyAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(300, 70));

                    JLabel shortPolyTitle = new JLabel();
                    shortPolyTitle.setText(interpolationNSH);
                    shortPolyTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    String asnNSH = Interpolation.NewtonBackwardDividedSubtractions.getIFASNS(pointsFunction, degree[0]);

                    JTextArea polyAnsNSH = new JTextArea();
                    polyAnsNSH.append("P(x) : ");
                    polyAnsNSH.append(asnNSH);
                    polyAnsNSH.setFont(new Font(mainFont, Font.PLAIN, 20));
                    polyAnsNSH.setEditable(false);
                    JScrollPane polyAnsNSHScrollPane = new JScrollPane(polyAnsNSH);
                    polyAnsNSHScrollPane.setPreferredSize(new Dimension(300, 70));


                    JPanel contentPanel = new JPanel(new GridLayout(6, 1, 0, -20));
                    contentPanel.setBorder(BorderFactory.createEmptyBorder(-20, 0, 0, 0));
                    contentPanel.add(tableTitle);
                    contentPanel.add(tableAnsScrollPane);
                    contentPanel.add(interTitle);
                    contentPanel.add(polyAnsScrollPane);
                    contentPanel.add(shortPolyTitle);
                    contentPanel.add(polyAnsNSHScrollPane);


                    String[] response = {cancel, continueWithPoly};

                    int feed = JOptionPane.showOptionDialog(null, contentPanel, interpolationTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon, response, response[1]);
                    if (feed == 1) {
                        polynomial = ans;
                        panelsStack.add(polynomialsPanel);
                        updateMainPanel();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? invalidInputs : ex.getMessage(), error, JOptionPane.ERROR_MESSAGE);
                }
            };
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel NFBSCard = createCard(title, button, enterNDBS, description);
        interpolationPanel.add(NFBSCard);

        //***********************************************************************

        //init Least-Squares Card
        title = "<html>" + languageBundle.getString("leastSquaresTitle") + "</html>";
        description = "<html>" + languageBundle.getString("leastSquaresDescription") + "</html>";
        ActionListener enterLeastSquares = e -> {
            doAction = pointsFunction -> {
                try {

                    // inputs : degree
                    JLabel enterDegreeTitle = new JLabel(enterDegreeReqPoly);
                    enterDegreeTitle.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
                    enterDegreeTitle.setHorizontalAlignment(SwingConstants.LEFT);

                    SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 100, 1);
                    JSpinner enterDegreeSp = new JSpinner(spinnerModel);
                    JSpinner.NumberEditor editor = (JSpinner.NumberEditor) enterDegreeSp.getEditor();
                    editor.getTextField().setColumns(2); // Adjust the width as needed

                    JPanel enterDegree = new JPanel();
                    if (local.getLanguage().equals("en")) {
                        enterDegree.setLayout(new FlowLayout(FlowLayout.LEFT));
                        enterDegree.add(enterDegreeTitle);
                        enterDegree.add(enterDegreeSp);
                    } else {
                        enterDegree.setLayout(new FlowLayout(FlowLayout.RIGHT));
                        enterDegree.add(enterDegreeSp);
                        enterDegree.add(enterDegreeTitle);
                    }
                    enterDegree.setBorder(BorderFactory.createEmptyBorder(10, 0, -10, 0));

                    JButton solveButton = new JButton(Solve);
                    solveButton.setFocusPainted(false);
                    Color customGreen = new Color(34, 139, 34);  // RGB values for green color
                    solveButton.setBackground(customGreen);
                    solveButton.setForeground(Color.white);  // Set the text color to white for better visibility

                    JButton cancelButton = new JButton(cancel);
                    cancelButton.setFocusPainted(false);

                    Object[] buttons = {cancelButton, solveButton};

                    final boolean[] cancelPressed = {false};
                    cancelButton.addActionListener(cancel -> {
                        cancelPressed[0] = true;
                        Window optionDialog = SwingUtilities.getWindowAncestor(enterDegree);
                        optionDialog.dispose();
                    });

                    final int[] degree = {0};

                    solveButton.addActionListener(solve -> {
                        Window optionDialog = SwingUtilities.getWindowAncestor(enterDegree);
                        optionDialog.dispose();
                        degree[0] = (int) enterDegreeSp.getValue();
                    });

                    JOptionPane.showOptionDialog(null, enterDegree, interpolationTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon64, buttons, null);
                    if (cancelPressed[0]) return;


                    Polynomial ans = Interpolation.LeastSquares.getIFAP(pointsFunction, degree[0]);

                    //create title
                    JLabel interTitle = new JLabel();
                    interTitle.setText("<html>" + languageBundle.getString("interpolationAnswer") + " " + languageBundle.getString("leastSquaresTitle") + " : " + "</html>");
                    interTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    //create ans scrolled
                    JTextArea polyAns = new JTextArea();
                    polyAns.append("P(x) : ");
                    polyAns.append(ans.toString());
                    polyAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    polyAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(polyAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(250, 65));

                    //create content panel
                    JPanel contentPanel = new JPanel(new GridLayout(2, 1));
                    contentPanel.add(interTitle);
                    contentPanel.add(polyAnsScrollPane);

                    String[] response = {cancel, continueWithPoly};

                    int feed = JOptionPane.showOptionDialog(null, contentPanel, interpolationTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon, response, response[1]);
                    if (feed == 1) {
                        polynomial = ans;
                        panelsStack.add(polynomialsPanel);
                        updateMainPanel();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? invalidInputs : ex.getMessage(), error, JOptionPane.ERROR_MESSAGE);
                }
            };
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel leastSquaresCard = createCard(title, button, enterLeastSquares, description);
        interpolationPanel.add(leastSquaresCard);


        //***********************************************************************

        //init Spline Card
        title = "<html>" + languageBundle.getString("splineTitle") + "</html>";
        description = "<html>" + languageBundle.getString("splineDescription") + "</html>";
        ActionListener enterSpline = e -> {
            doAction = pointsFunction -> {
                try {
                    ArrayList<Polynomial> ans = Interpolation.Spline.getIFAPs(pointsFunction);
                    //create title
                    JLabel interTitle = new JLabel();
                    interTitle.setText("<html>" + languageBundle.getString("interpolationAnswer") + " " + languageBundle.getString("splineTitle") + " : " + "</html>");
                    interTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    //create ans scrolled
                    JTextArea polyAns = new JTextArea();
                    polyAns.append("S(x) : ");
                    polyAns.append("\n");
                    for (int i = 0; i < ans.size(); i++) {
                        polyAns.append("S" + i + "(x) = ");
                        polyAns.append(ans.get(i).toString());
                        polyAns.append("\n");
                    }
                    polyAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    polyAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(polyAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(380, 100));

                    //create content panel
                    JPanel contentPanel = new JPanel(new GridLayout(2, 1, 0, -20));
                    contentPanel.setBorder(BorderFactory.createEmptyBorder(-30, 0, 0, 0));
                    contentPanel.add(interTitle);
                    contentPanel.add(polyAnsScrollPane);

                    String[] response = {OK};

                    JOptionPane.showOptionDialog(null, contentPanel, interpolationTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon, response, response[0]);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? invalidInputs : ex.getMessage(), error, JOptionPane.ERROR_MESSAGE);
                }
            };
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel splineCard = createCard(title, button, enterSpline, description);
        interpolationPanel.add(splineCard);

    }

    /**
     * Initializes the Integral panel.
     * Sets the name, size, and background color of the panel.
     * Creates cards for different integration methods with corresponding action listeners.
     * Adds the cards to the panel.
     */
    private void initIntegralPanel() {
        integralPanel = new JPanel();
        integralPanel.setName("Integral");
        integralPanel.setPreferredSize(mainFrame.getSize());
        integralPanel.setBackground(new Color(100, 100, 100));
        integralPanel.setLayout(new BorderLayout());
        GridLayout startLayout = new GridLayout(2, 2, 5, 5);
        JPanel cardsPanel = new JPanel(startLayout);
        cardsPanel.setComponentOrientation(orientation);
        cardsPanel.setBackground(new Color(100, 100, 100));
        String integralTitle = "<html>" + languageBundle.getString("integralTitle") + "</html>";
        //***********************************************************************

        //init Rectangular Card
        String title = "<html>" + languageBundle.getString("rectangularTitle") + "</html>";
        String description = "<html>" + languageBundle.getString("rectangularDescription") + "</html>";
        String button = enter;
        ActionListener enterRect = e -> {
            doAction = pointsFunction -> {
                try {

                    BigDecimal a = function.getXp().get(0);
                    BigDecimal b = function.getXp().get(function.getXp().size() - 1);
                    int n = function.getXp().size() - 1;
                    BigDecimal ans = Integral.getRect(function, a, b, n);

                    //create title
                    JLabel interTitle = new JLabel();
                    interTitle.setText("<html>" + languageBundle.getString("integrationAnswer") + " " + languageBundle.getString("rectangularTitle") + " : " + "</html>");
                    interTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    //create ans scrolled
                    JTextArea polyAns = new JTextArea();
                    polyAns.append("Answer = ");
                    polyAns.append(String.valueOf(fixAccuracy(ans)));
                    polyAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    polyAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(polyAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(100, 50));

                    //create content panel
                    JPanel contentPanel = new JPanel(new GridLayout(2, 1));
                    contentPanel.add(interTitle);
                    contentPanel.add(polyAnsScrollPane);

                    String[] response = {OK};

                    JOptionPane.showOptionDialog(null, contentPanel, integralTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon, response, response[0]);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? invalidInputs : ex.getMessage(), error, JOptionPane.ERROR_MESSAGE);
                }
            };
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel rectCard = createCard(title, description, button, enterRect);
        cardsPanel.add(rectCard);

        //***********************************************************************

        //init Trapezoidal Card
        title = "<html>" + languageBundle.getString("trapezoidalTitle") + "</html>";
        description = "<html>" + languageBundle.getString("trapezoidalDescription") + "</html>";
        button = enter;
        ActionListener enterTraps = e -> {
            doAction = pointsFunction -> {
                try {

                    BigDecimal a = function.getXp().get(0);
                    BigDecimal b = function.getXp().get(function.getXp().size() - 1);
                    int n = function.getXp().size() - 1;
                    BigDecimal ans = Integral.getTraps(function, a, b, n);

                    //create title
                    JLabel interTitle = new JLabel();
                    interTitle.setText("<html>" + languageBundle.getString("integrationAnswer") + " " + languageBundle.getString("trapezoidalTitle") + " : " + "</html>");
                    interTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    //create ans scrolled
                    JTextArea polyAns = new JTextArea();
                    polyAns.append("Answer = ");
                    polyAns.append(String.valueOf(fixAccuracy(ans)));
                    polyAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    polyAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(polyAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(100, 50));

                    //create content panel
                    JPanel contentPanel = new JPanel(new GridLayout(2, 1));
                    contentPanel.add(interTitle);
                    contentPanel.add(polyAnsScrollPane);

                    String[] response = {OK};

                    JOptionPane.showOptionDialog(null, contentPanel, integralTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon, response, response[0]);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? invalidInputs : ex.getMessage(), error, JOptionPane.ERROR_MESSAGE);
                }
            };
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel trapsCard = createCard(title, description, button, enterTraps);
        cardsPanel.add(trapsCard);

        //***********************************************************************

        //init Simpson 1/3 Card
        title = "<html>" + languageBundle.getString("simpson3Title") + "</html>";
        description = "<html>" + languageBundle.getString("simpson3Description") + "</html>";
        button = enter;
        ActionListener enterSimpson3 = e -> {
            doAction = pointsFunction -> {
                try {

                    BigDecimal a = function.getXp().get(0);
                    BigDecimal b = function.getXp().get(function.getXp().size() - 1);
                    int n = function.getXp().size() - 1;
                    BigDecimal ans = Integral.getSimpson3(function, a, b, n);

                    //create title
                    JLabel interTitle = new JLabel();
                    interTitle.setText("<html>" + languageBundle.getString("integrationAnswer") + " " + languageBundle.getString("simpson3Title") + " : " + "</html>");
                    interTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    //create ans scrolled
                    JTextArea polyAns = new JTextArea();
                    polyAns.append("Answer = ");
                    polyAns.append(String.valueOf(fixAccuracy(ans)));
                    polyAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    polyAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(polyAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(100, 50));

                    //create content panel
                    JPanel contentPanel = new JPanel(new GridLayout(2, 1));
                    contentPanel.add(interTitle);
                    contentPanel.add(polyAnsScrollPane);

                    String[] response = {OK};

                    JOptionPane.showOptionDialog(null, contentPanel, integralTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon, response, response[0]);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? invalidInputs : ex.getMessage(), error, JOptionPane.ERROR_MESSAGE);
                }
            };
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel simpson3Card = createCard(title, description, button, enterSimpson3);
        cardsPanel.add(simpson3Card);

        //***********************************************************************

        //init Simpson 3/8 Card
        title = "<html>" + languageBundle.getString("simpson8Title") + "</html>";
        description = "<html>" + languageBundle.getString("simpson8Description") + "</html>";
        button = enter;
        ActionListener enterSimpson8 = e -> {
            doAction = pointsFunction -> {
                try {

                    BigDecimal a = function.getXp().get(0);
                    BigDecimal b = function.getXp().get(function.getXp().size() - 1);
                    int n = function.getXp().size() - 1;
                    BigDecimal ans = Integral.getSimpson8(function, a, b, n);

                    //create title
                    JLabel interTitle = new JLabel();
                    interTitle.setText("<html>" + languageBundle.getString("integrationAnswer") + " " + languageBundle.getString("simpson8Title") + " : " + "</html>");
                    interTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    //create ans scrolled
                    JTextArea polyAns = new JTextArea();
                    polyAns.append("Answer = ");
                    polyAns.append(String.valueOf(fixAccuracy(ans)));
                    polyAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    polyAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(polyAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(100, 50));

                    //create content panel
                    JPanel contentPanel = new JPanel(new GridLayout(2, 1));
                    contentPanel.add(interTitle);
                    contentPanel.add(polyAnsScrollPane);

                    String[] response = {OK};

                    JOptionPane.showOptionDialog(null, contentPanel, integralTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon, response, response[0]);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? invalidInputs : ex.getMessage(), error, JOptionPane.ERROR_MESSAGE);
                }
            };
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel simpson8Card = createCard(title, description, button, enterSimpson8);
        cardsPanel.add(simpson8Card);

        //***********************************************************************

        //init Paul Card
        title = "<html>" + languageBundle.getString("paulTitle") + "</html>";
        description = "<html>" + languageBundle.getString("paulDescription") + "</html>";
        button = enter;
        ActionListener enterPaul = e -> {
            doAction = pointsFunction -> {
                try {

                    BigDecimal a = function.getXp().get(0);
                    BigDecimal b = function.getXp().get(function.getXp().size() - 1);
                    int n = function.getXp().size() - 1;
                    BigDecimal ans = Integral.getPaul(function, a, b, n);

                    //create title
                    JLabel interTitle = new JLabel();
                    interTitle.setText("<html>" + languageBundle.getString("integrationAnswer") + " " + languageBundle.getString("paulTitle") + " : " + "</html>");
                    interTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    //create ans scrolled
                    JTextArea polyAns = new JTextArea();
                    polyAns.append("Answer = ");
                    polyAns.append(String.valueOf(fixAccuracy(ans)));
                    polyAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    polyAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(polyAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(100, 50));

                    //create content panel
                    JPanel contentPanel = new JPanel(new GridLayout(2, 1));
                    contentPanel.add(interTitle);
                    contentPanel.add(polyAnsScrollPane);

                    String[] response = {OK};

                    JOptionPane.showOptionDialog(null, contentPanel, integralTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon, response, response[0]);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? invalidInputs : ex.getMessage(), error, JOptionPane.ERROR_MESSAGE);
                }
            };
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel paulCard = createCard(title, description, button, enterPaul);
        integralPanel.add(paulCard, BorderLayout.SOUTH);

        //***********************************************************************

        integralPanel.add(cardsPanel, BorderLayout.CENTER);

    }

    /**
     * Initializes the Differentiation panel.
     * Sets the name, size, and background color of the panel.
     * Creates cards for Differentiation methods with corresponding action listeners.
     * Adds the cards to the panel.
     */
    private void initDiffPanel() {
        differentiationPanel = new JPanel();
        differentiationPanel.setName("Differential");
        differentiationPanel.setPreferredSize(mainFrame.getSize());
        differentiationPanel.setBackground(new Color(100, 100, 100));
        GridLayout startLayout = new GridLayout(4, 1);
        startLayout.setHgap(5);
        startLayout.setVgap(5);
        differentiationPanel.setLayout(startLayout);
        differentiationPanel.setComponentOrientation(orientation);
        String differentiationTitle = "<html>" + languageBundle.getString("differentiationTitle") + "</html>";
        String enterRankDiff = "<html>" + languageBundle.getString("enterRankDiff") + "</html>";

        //***********************************************************************

        //init Lagrange Card
        String title = "<html>" + languageBundle.getString("lagrangeDiffTitle") + "</html>";
        String description = "<html>" + languageBundle.getString("lagrangeDiffDescription") + "</html>";
        String button = enter;
        ActionListener enterLagrange = e -> {
            doAction = pointsFunction -> {
                try {
                    Polynomial ans = Differentiation.Lagrange.getDFAP(pointsFunction);

                    //create answer title
                    JLabel interTitle = new JLabel();
                    interTitle.setText("<html>" + languageBundle.getString("differentialAnswer") + " " + languageBundle.getString("lagrangeDiffTitle") + " : " + "</html>");
                    interTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    //create answer scrolled
                    JTextArea polyAns = new JTextArea();
                    polyAns.append("P(x) : ");
                    polyAns.append(ans.toString());
                    polyAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    polyAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(polyAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(300, 70));

                    //create content panel
                    JPanel contentPanel = new JPanel(new GridLayout(2, 1, 0, -20));
                    contentPanel.setBorder(BorderFactory.createEmptyBorder(-20, 0, 0, 0));
                    contentPanel.add(interTitle);
                    contentPanel.add(polyAnsScrollPane);

                    String[] response = {cancel, continueWithPoly};

                    int feed = JOptionPane.showOptionDialog(null, contentPanel, differentiationTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon, response, response[1]);
                    if (feed == 1) {
                        polynomial = ans;
                        panelsStack.add(polynomialsPanel);
                        updateMainPanel();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? invalidInputs : ex.getMessage(), error, JOptionPane.ERROR_MESSAGE);
                }
            };
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel lagrangeCard = createCard(title, description, button, enterLagrange);
        differentiationPanel.add(lagrangeCard);

        //***********************************************************************

        //init  Newton-Gregory Forward Subtractions Card
        title = "<html>" + languageBundle.getString("NGFSDiffTitle") + "</html>";
        description = "<html>" + languageBundle.getString("NGFSDiffDescription") + "</html>";
        button = enter;
        ActionListener enterNGFS = e -> {
            doAction = pointsFunction -> {
                try {

                    // inputs : degree
                    JLabel enterDegreeTitle = new JLabel(enterDegreeReqPoly);
                    enterDegreeTitle.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
                    enterDegreeTitle.setHorizontalAlignment(SwingConstants.LEFT);

                    SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 100, 1);
                    JSpinner enterDegreeSp = new JSpinner(spinnerModel);
                    JSpinner.NumberEditor editor = (JSpinner.NumberEditor) enterDegreeSp.getEditor();
                    editor.getTextField().setColumns(2); // Adjust the width as needed

                    JPanel enterDegree = new JPanel();
                    if (local.getLanguage().equals("en")) {
                        enterDegree.setLayout(new FlowLayout(FlowLayout.LEFT));
                        enterDegree.add(enterDegreeTitle);
                        enterDegree.add(enterDegreeSp);
                    } else {
                        enterDegree.setLayout(new FlowLayout(FlowLayout.RIGHT));
                        enterDegree.add(enterDegreeSp);
                        enterDegree.add(enterDegreeTitle);
                    }
                    //enterDegree.setBorder(BorderFactory.createEmptyBorder(10,0,-10,0));

                    // inputs : rank
                    JLabel enterRankTitle = new JLabel(enterRankDiff);
                    enterRankTitle.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
                    enterRankTitle.setHorizontalAlignment(SwingConstants.LEFT);

                    SpinnerNumberModel spinnerModelRank = new SpinnerNumberModel(1, 1, 100, 1);
                    JSpinner enterRankSp = new JSpinner(spinnerModelRank);
                    JSpinner.NumberEditor editorRank = (JSpinner.NumberEditor) enterRankSp.getEditor();
                    editorRank.getTextField().setColumns(2); // Adjust the width as needed

                    JPanel enterRank = new JPanel();
                    if (local.getLanguage().equals("en")) {
                        enterRank.setLayout(new FlowLayout(FlowLayout.LEFT));
                        enterRank.add(enterRankTitle);
                        enterRank.add(enterRankSp);
                    } else {
                        enterRank.setLayout(new FlowLayout(FlowLayout.RIGHT));
                        enterRank.add(enterRankSp);
                        enterRank.add(enterRankTitle);
                    }
                    //enterRank.setBorder(BorderFactory.createEmptyBorder(10,0,-10,0));

                    JPanel inputs = new JPanel(new GridLayout(2, 1));
                    inputs.add(enterDegree);
                    inputs.add(enterRank);

                    JButton solveButton = new JButton(Solve);
                    solveButton.setFocusPainted(false);
                    Color customGreen = new Color(34, 139, 34);  // RGB values for green color
                    solveButton.setBackground(customGreen);
                    solveButton.setForeground(Color.white);  // Set the text color to white for better visibility

                    JButton cancelButton = new JButton(cancel);
                    cancelButton.setFocusPainted(false);

                    Object[] buttons = {cancelButton, solveButton};

                    final boolean[] cancelPressed = {false};
                    cancelButton.addActionListener(cancel -> {
                        cancelPressed[0] = true;
                        Window optionDialog = SwingUtilities.getWindowAncestor(inputs);
                        optionDialog.dispose();
                    });

                    final int[] degree = {0};
                    final int[] rank = {0};
                    solveButton.addActionListener(solve -> {
                        Window optionDialog = SwingUtilities.getWindowAncestor(inputs);
                        optionDialog.dispose();
                        degree[0] = (int) enterDegreeSp.getValue();
                        rank[0] = (int) enterRankSp.getValue();
                    });

                    JOptionPane.showOptionDialog(null, inputs, differentiationTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon64, buttons, null);
                    if (cancelPressed[0]) return;


                    Polynomial ans = Differentiation.Newton_GregoryForwardSubtractions.getIFAP(pointsFunction, degree[0], rank[0]);

                    JLabel interTitle = new JLabel();
                    interTitle.setText("<html>" + languageBundle.getString("differentialAnswer") + " " + languageBundle.getString("NGFSDiffTitle") + " : " + "</html>");
                    interTitle.setFont(new Font(mainFont, Font.PLAIN, 20));


                    JTextArea polyAns = new JTextArea();
                    polyAns.append("P(x) : ");
                    polyAns.append(ans.toString());
                    polyAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    polyAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(polyAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(300, 70));


                    JPanel contentPanel = new JPanel(new GridLayout(2, 1, 0, -20));
                    contentPanel.setBorder(BorderFactory.createEmptyBorder(-20, 0, 0, 0));
                    contentPanel.add(interTitle);
                    contentPanel.add(polyAnsScrollPane);


                    String[] response = {cancel, continueWithPoly};

                    int feed = JOptionPane.showOptionDialog(null, contentPanel, differentiationTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon, response, response[1]);
                    if (feed == 1) {
                        polynomial = ans;
                        panelsStack.add(polynomialsPanel);
                        updateMainPanel();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? invalidInputs : ex.getMessage(), error, JOptionPane.ERROR_MESSAGE);
                }
            };
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel NGFSCard = createCard(title, description, button, enterNGFS);
        differentiationPanel.add(NGFSCard);

        //***********************************************************************

        //init Newton-Gregory Backward Subtractions Card
        title = "<html>" + languageBundle.getString("NGBSDiffTitle") + "</html>";
        description = "<html>" + languageBundle.getString("NGFBDiffDescription") + "</html>";
        button = enter;
        ActionListener enterNGBS = e -> {
            doAction = pointsFunction -> {
                try {

                    // inputs : degree
                    JLabel enterDegreeTitle = new JLabel(enterDegreeReqPoly);
                    enterDegreeTitle.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
                    enterDegreeTitle.setHorizontalAlignment(SwingConstants.LEFT);

                    SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 100, 1);
                    JSpinner enterDegreeSp = new JSpinner(spinnerModel);
                    JSpinner.NumberEditor editor = (JSpinner.NumberEditor) enterDegreeSp.getEditor();
                    editor.getTextField().setColumns(2); // Adjust the width as needed

                    JPanel enterDegree = new JPanel();
                    if (local.getLanguage().equals("en")) {
                        enterDegree.setLayout(new FlowLayout(FlowLayout.LEFT));
                        enterDegree.add(enterDegreeTitle);
                        enterDegree.add(enterDegreeSp);
                    } else {
                        enterDegree.setLayout(new FlowLayout(FlowLayout.RIGHT));
                        enterDegree.add(enterDegreeSp);
                        enterDegree.add(enterDegreeTitle);
                    }
                    //enterDegree.setBorder(BorderFactory.createEmptyBorder(10,0,-10,0));

                    // inputs : rank
                    JLabel enterRankTitle = new JLabel(enterRankDiff);
                    enterRankTitle.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
                    enterRankTitle.setHorizontalAlignment(SwingConstants.LEFT);

                    SpinnerNumberModel spinnerModelRank = new SpinnerNumberModel(1, 1, 100, 1);
                    JSpinner enterRankSp = new JSpinner(spinnerModelRank);
                    JSpinner.NumberEditor editorRank = (JSpinner.NumberEditor) enterRankSp.getEditor();
                    editorRank.getTextField().setColumns(2); // Adjust the width as needed

                    JPanel enterRank = new JPanel();
                    if (local.getLanguage().equals("en")) {
                        enterRank.setLayout(new FlowLayout(FlowLayout.LEFT));
                        enterRank.add(enterRankTitle);
                        enterRank.add(enterRankSp);
                    } else {
                        enterRank.setLayout(new FlowLayout(FlowLayout.RIGHT));
                        enterRank.add(enterRankSp);
                        enterRank.add(enterRankTitle);
                    }
                    //enterRank.setBorder(BorderFactory.createEmptyBorder(10,0,-10,0));

                    JPanel inputs = new JPanel(new GridLayout(2, 1));
                    inputs.add(enterDegree);
                    inputs.add(enterRank);

                    JButton solveButton = new JButton(Solve);
                    solveButton.setFocusPainted(false);
                    Color customGreen = new Color(34, 139, 34);  // RGB values for green color
                    solveButton.setBackground(customGreen);
                    solveButton.setForeground(Color.white);  // Set the text color to white for better visibility

                    JButton cancelButton = new JButton(cancel);
                    cancelButton.setFocusPainted(false);

                    Object[] buttons = {cancelButton, solveButton};

                    final boolean[] cancelPressed = {false};
                    cancelButton.addActionListener(cancel -> {
                        cancelPressed[0] = true;
                        Window optionDialog = SwingUtilities.getWindowAncestor(inputs);
                        optionDialog.dispose();
                    });

                    final int[] degree = {0};
                    final int[] rank = {0};
                    solveButton.addActionListener(solve -> {
                        Window optionDialog = SwingUtilities.getWindowAncestor(inputs);
                        optionDialog.dispose();
                        degree[0] = (int) enterDegreeSp.getValue();
                        rank[0] = (int) enterRankSp.getValue();
                    });

                    JOptionPane.showOptionDialog(null, inputs, differentiationTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon64, buttons, null);
                    if (cancelPressed[0]) return;


                    Polynomial ans = Differentiation.Newton_GregoryForwardSubtractions.getIFAP(pointsFunction, degree[0], rank[0]);

                    JLabel interTitle = new JLabel();
                    interTitle.setText("<html>" + languageBundle.getString("differentialAnswer") + " " + languageBundle.getString("NGBSDiffTitle") + " : " + "</html>");
                    interTitle.setFont(new Font(mainFont, Font.PLAIN, 20));


                    JTextArea polyAns = new JTextArea();
                    polyAns.append("P(x) : ");
                    polyAns.append(ans.toString());
                    polyAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    polyAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(polyAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(300, 70));


                    JPanel contentPanel = new JPanel(new GridLayout(2, 1, 0, -20));
                    contentPanel.setBorder(BorderFactory.createEmptyBorder(-20, 0, 0, 0));
                    contentPanel.add(interTitle);
                    contentPanel.add(polyAnsScrollPane);


                    String[] response = {cancel, continueWithPoly};

                    int feed = JOptionPane.showOptionDialog(null, contentPanel, differentiationTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon, response, response[1]);
                    if (feed == 1) {
                        polynomial = ans;
                        panelsStack.add(polynomialsPanel);
                        updateMainPanel();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? invalidInputs : ex.getMessage(), error, JOptionPane.ERROR_MESSAGE);
                }
            };
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel NGBSCard = createCard(title, description, button, enterNGBS);
        differentiationPanel.add(NGBSCard);


        //***********************************************************************

        //init Central,Forward,Backward Subtractions Card
        title = "<html>" + languageBundle.getString("CFBSTitle") + "</html>";
        description = "<html>" + languageBundle.getString("CFBDescription") + "</html>";
        button = enter;
        ActionListener enterCFBS = e -> {
            doAction = pointsFunction -> {
                try {

                    JLabel enterXLabel = new JLabel("<html>" + languageBundle.getString("enterXDiff") + " : " + "</html>");
                    enterXLabel.setFont(new Font(mainFont, Font.PLAIN, 15));
                    enterXLabel.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));

                    JTextField enterXField = new JTextField();
                    enterXField.setColumns(23);

                    JLabel enterMethodLabel = new JLabel("<html>" + languageBundle.getString("chooseMethod") + " : " + "</html>");
                    enterMethodLabel.setFont(new Font(mainFont, Font.PLAIN, 15));
                    enterMethodLabel.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));

                    JComboBox<String> enterMethodCombo = new JComboBox<>();
                    String BS = "<html>" + languageBundle.getString("BS") + "</html>";
                    String CS = "<html>" + languageBundle.getString("CF") + "</html>";
                    String FS = "<html>" + languageBundle.getString("FS") + "</html>";
                    enterMethodCombo.addItem(BS);
                    enterMethodCombo.addItem(CS);
                    enterMethodCombo.addItem(FS);

                    JPanel inputs = new JPanel(new GridBagLayout());
                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    gbc.insets = new Insets(5, -22, 5, 5);
                    inputs.add(enterXLabel, gbc);
                    gbc.gridy++;
                    gbc.insets = new Insets(5, 5, 5, 5);
                    inputs.add(enterXField, gbc);
                    gbc.gridy++;
                    if(local.getLanguage().equals("en")) {
                        gbc.insets = new Insets(5, -150, 5, 5);
                        inputs.add(enterMethodLabel, gbc);
                        gbc.gridx++;
                        inputs.add(enterMethodCombo, gbc);
                    }else{
                        gbc.insets = new Insets(5, -100, 5, 5);
                        inputs.add(enterMethodCombo, gbc);
                        gbc.gridx++;
                        inputs.add(enterMethodLabel, gbc);
                    }

                    JButton solveButton = new JButton(Solve);
                    solveButton.setFocusPainted(false);
                    Color customGreen = new Color(34, 139, 34);  // RGB values for green color
                    solveButton.setBackground(customGreen);
                    solveButton.setForeground(Color.white);  // Set the text color to white for better visibility
                    solveButton.setEnabled(false);

                    ArrayList<JTextField> fields = new ArrayList<>();
                    fields.add(enterXField);
                    addDocumentListenerToFields(solveButton, fields);

                    JButton cancelButton = new JButton(cancel);
                    cancelButton.setFocusPainted(false);

                    Object[] buttons = {cancelButton, solveButton};

                    cancelButton.addActionListener(cancel -> {
                        Window optionDialog = SwingUtilities.getWindowAncestor(inputs);
                        optionDialog.dispose();
                    });

                    final BigDecimal[] x = {new BigDecimal(0)};
                    final String[] method = {""};
                    final boolean[] solvePressed = {false};
                    solveButton.addActionListener(solve -> {
                        solvePressed[0] = true;
                        Window optionDialog = SwingUtilities.getWindowAncestor(inputs);
                        optionDialog.dispose();
                        x[0] = EvaluateString.evaluate(enterXField.getText());
                        method[0] = String.valueOf(enterMethodCombo.getSelectedItem());
                    });

                    JOptionPane.showOptionDialog(null, inputs, differentiationTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon64, buttons, null);
                    if (!solvePressed[0]) return;

                    BigDecimal ans;
                    if (method[0].equals(BS)) {
                        ans = Differentiation.Subtractions.Backward.getValueAt(function, x[0]);
                    } else if (method[0].equals(CS)) {
                        ans = Differentiation.Subtractions.Central.getValueAt(function, x[0]);
                    } else if (method[0].equals(FS)) {
                        ans = Differentiation.Subtractions.Forward.getValueAt(function, x[0]);
                    } else {
                        throw new Exception(invalidInputs);
                    }


                    JLabel interTitle = new JLabel();
                    interTitle.setText("<html>" + languageBundle.getString("differentialAnswer") + " " + method[0] + " : " + "</html>");
                    interTitle.setFont(new Font(mainFont, Font.PLAIN, 20));


                    JTextArea polyAns = new JTextArea();
                    polyAns.append("Answer = ");
                    polyAns.append(String.valueOf(fixAccuracy(ans)));
                    polyAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    polyAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(polyAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(100, 70));


                    JPanel contentPanel = new JPanel(new GridLayout(2, 1, 0, -20));
                    contentPanel.setBorder(BorderFactory.createEmptyBorder(-20, 0, 0, 0));
                    contentPanel.add(interTitle);
                    contentPanel.add(polyAnsScrollPane);


                    String[] response = {OK};

                    JOptionPane.showOptionDialog(null, contentPanel, differentiationTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon, response, null);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? invalidInputs : ex.getMessage(), error, JOptionPane.ERROR_MESSAGE);
                }
            };
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel CFBSCard = createCard(title, description, button, enterCFBS);
        differentiationPanel.add(CFBSCard);

        //***********************************************************************
    }

    /**
     * Initializes the Differential Equations panel.
     * Sets the name, size, and background color of the panel.
     * Creates cards for Differential Equations methods with corresponding action listeners.
     * Adds the cards to the panel.
     */
    private void initDiffEQPanel() {
        differentialEquationsPanel = new JPanel();
        differentialEquationsPanel.setName("Differential Equations");
        differentialEquationsPanel.setPreferredSize(mainFrame.getSize());
        differentialEquationsPanel.setBackground(new Color(100, 100, 100));
        GridLayout startLayout = new GridLayout(3, 2);
        startLayout.setHgap(5);
        startLayout.setVgap(5);
        differentialEquationsPanel.setLayout(startLayout);
        differentialEquationsPanel.setComponentOrientation(orientation);
        String diffEqTitle = "<html>" + languageBundle.getString("differentialTitle") + "</html>";

        //***********************************************************************

        //init Euler Card
        String title = "<html>" + languageBundle.getString("eulerTitle") + "</html>";
        String description = "<html>" + languageBundle.getString("eulerDescription") + "</html>";
        String button = enter;
        ActionListener enterEuler = e -> {
            JLabel eulerTitle = new JLabel("<html>" + languageBundle.getString("eulerMethod") + "</html>");
            eulerTitle.setComponentOrientation(orientation);
            eulerTitle.setFont(new Font(mainFont, Font.BOLD, 25));

            JLabel enterYTitle = new JLabel("<html>" + languageBundle.getString("enterButton") + " Y ' : " + "</html>");
            enterYTitle.setComponentOrientation(orientation);
            enterYTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterYField = new JTextField();
            enterYField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterYField.setPreferredSize(new Dimension(250, 50));

            JLabel enterX0Title = new JLabel("<html>" + languageBundle.getString("enterButton") + " X0 : " + "</html>");
            enterX0Title.setComponentOrientation(orientation);
            enterX0Title.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterX0Field = new JTextField();
            enterX0Field.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterX0Field.setPreferredSize(new Dimension(250, 50));

            JLabel enterY0Title = new JLabel("<html>" + languageBundle.getString("enterButton") + " Y0 : " + "</html>");
            enterY0Title.setComponentOrientation(orientation);
            enterY0Title.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterY0Field = new JTextField();
            enterY0Field.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterY0Field.setPreferredSize(new Dimension(250, 50));

            JLabel enterHTitle = new JLabel("<html>" + languageBundle.getString("enterButton") + " h : " + "</html>");
            enterHTitle.setComponentOrientation(orientation);
            enterHTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterHField = new JTextField();
            enterHField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterHField.setPreferredSize(new Dimension(250, 50));

            JLabel enterXTitle = new JLabel("<html>" + languageBundle.getString("enterButton") + " X : " + "</html>");
            enterXTitle.setComponentOrientation(orientation);
            enterXTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterXField = new JTextField();
            enterXField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterXField.setPreferredSize(new Dimension(250, 50));

            JPanel contentPanel;
            if (local.getLanguage().equals("en"))
                contentPanel = new JPanel(new GridLayout(6, 2, -150, 0));
            else {
                contentPanel = new JPanel(new GridLayout(6, 2, 20, 0));
                contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, -120));
            }
            contentPanel.setComponentOrientation(orientation);
            if (local.getLanguage().equals("en")) {
                contentPanel.add(eulerTitle);
                contentPanel.add(new JPanel());
                contentPanel.add(enterYTitle);
                contentPanel.add(enterYField);
                contentPanel.add(enterX0Title);
                contentPanel.add(enterX0Field);
                contentPanel.add(enterY0Title);
                contentPanel.add(enterY0Field);
                contentPanel.add(enterHTitle);
                contentPanel.add(enterHField);
                contentPanel.add(enterXTitle);
                contentPanel.add(enterXField);
            } else {
                contentPanel.add(new JPanel());
                contentPanel.add(eulerTitle);
                contentPanel.add(enterYField);
                contentPanel.add(enterYTitle);
                contentPanel.add(enterX0Field);
                contentPanel.add(enterX0Title);
                contentPanel.add(enterY0Field);
                contentPanel.add(enterY0Title);
                contentPanel.add(enterHField);
                contentPanel.add(enterHTitle);
                contentPanel.add(enterXField);
                contentPanel.add(enterXTitle);
            }

            JButton solveButton = new JButton(Solve);
            solveButton.setFocusPainted(false);
            solveButton.setPreferredSize(new Dimension(80, 40));
            solveButton.setFont(new Font("Arial", Font.PLAIN, 17));
            Color customGreen = new Color(34, 139, 34);  // RGB values for green color
            solveButton.setBackground(customGreen);
            solveButton.setForeground(Color.white);  // Set the text color to white for better visibility
            solveButton.setEnabled(false);

            JButton cancelButton = new JButton(cancel);
            cancelButton.setFocusPainted(false);
            cancelButton.setPreferredSize(new Dimension(100, 40));
            cancelButton.setFont(new Font("Arial", Font.PLAIN, 17));

            Object[] buttons = {cancelButton, solveButton};

            ArrayList<JTextField> fields = new ArrayList<>();
            fields.add(enterYField);
            fields.add(enterX0Field);
            fields.add(enterY0Field);
            fields.add(enterHField);
            fields.add(enterXField);
            addDocumentListenerToFields(solveButton, fields);

            cancelButton.addActionListener(cancel -> {
                Window optionDialog = SwingUtilities.getWindowAncestor(contentPanel);
                optionDialog.dispose();
            });

            solveButton.addActionListener(solve -> {
                Window optionDialog = SwingUtilities.getWindowAncestor(contentPanel);
                optionDialog.dispose();
                try {
                    String difeq = enterYField.getText();
                    BigDecimal x0 = EvaluateString.evaluate(enterX0Field.getText());
                    BigDecimal y0 = EvaluateString.evaluate(enterY0Field.getText());
                    BigDecimal h = EvaluateString.evaluate(enterHField.getText());
                    BigDecimal x = EvaluateString.evaluate(enterXField.getText());
                    DifferentialEquation eq = new DifferentialEquation(difeq);
                    BigDecimal ans = DifferentialEquation.Euler.solve(eq, x0, y0, h, x);

                    //create title
                    JLabel difeqTitle = new JLabel();
                    difeqTitle.setText("<html>" + languageBundle.getString("eulerDescription") + " : " + "</html>");
                    difeqTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    //create ans scrolled
                    JTextArea difeqAns = new JTextArea();
                    difeqAns.append("Answer = ");
                    difeqAns.append(String.valueOf(fixAccuracy(ans)));
                    difeqAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    difeqAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(difeqAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(100, 50));

                    //create content panel
                    JPanel ansContentPanel = new JPanel(new GridLayout(2, 1));
                    ansContentPanel.add(difeqTitle);
                    ansContentPanel.add(polyAnsScrollPane);

                    JOptionPane.showConfirmDialog(null, ansContentPanel, diffEqTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? invalidInputs : ex.getMessage(), error, JOptionPane.ERROR_MESSAGE);
                }
            });

            JOptionPane.showOptionDialog(null, contentPanel, diffEqTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon128, buttons, null);

        };
        JPanel eulerCard = createCard(title, description, button, enterEuler);
        differentialEquationsPanel.add(eulerCard);

        //***********************************************************************

        //init Taylor Card
        title = "<html>" + languageBundle.getString("taylorTitle") + "</html>";
        description = "<html>" + languageBundle.getString("taylorDescription") + "</html>";
        button = enter;
        ActionListener enterTaylor = e -> {
            JLabel taylorTitleLabel = new JLabel("<html>" + languageBundle.getString("taylorMethod") + "</html>");
            taylorTitleLabel.setFont(new Font(mainFont, Font.BOLD, 25));
            JPanel taylotTitle = new JPanel();
            taylotTitle.add(taylorTitleLabel);
            taylotTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

            // inputs : n
            JLabel enterNTitle = new JLabel("<html>" + languageBundle.getString("enterNTermTylor") + "</html>");
            enterNTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            enterNTitle.setHorizontalAlignment(SwingConstants.LEFT);

            SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 100, 1);
            JSpinner enterNSp = new JSpinner(spinnerModel);
            JSpinner.NumberEditor editor = (JSpinner.NumberEditor) enterNSp.getEditor();
            editor.getTextField().setColumns(2); // Adjust the width as needed

            JButton confirmButton = new JButton(Confirm);
            confirmButton.setPreferredSize(new Dimension(80, 30));

            JPanel enterN = new JPanel();
            if (local.getLanguage().equals("en")) {
                enterN.setLayout(new FlowLayout(FlowLayout.LEFT));
                enterN.add(enterNTitle);
                enterN.add(enterNSp);
                enterN.add(confirmButton);
            } else {
                enterN.setLayout(new FlowLayout(FlowLayout.RIGHT));
                enterN.add(confirmButton);
                enterN.add(enterNSp);
                enterN.add(enterNTitle);
            }


            // input
            JLabel enterDivsTitle = new JLabel("<html>" + languageBundle.getString("enterDerivatives") + " : " + "</html>");
            enterDivsTitle.setComponentOrientation(orientation);
            enterDivsTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            enterDivsTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0)); // Adjust the top and bottom padding

            JScrollPane enterDivsScroll = new JScrollPane();
            enterDivsScroll.setPreferredSize(new Dimension(500, 100));


            // inputs panel
            JPanel inputsPanel = new JPanel(new GridBagLayout());
            inputsPanel.setComponentOrientation(orientation);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            if (local.getLanguage().equals("en"))
                gbc.anchor = GridBagConstraints.WEST;
            else
                gbc.anchor = GridBagConstraints.EAST;
            gbc.insets = new Insets(5, 0, 5, 0); // Add vertical spacing between components

            inputsPanel.add(enterN, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            gbc.gridwidth = 2;
            inputsPanel.add(enterDivsTitle, gbc);

            gbc.gridy++;
            inputsPanel.add(enterDivsScroll, gbc);


            JLabel enterX0Title = new JLabel("<html>" + languageBundle.getString("enterButton") + " X0 : " + "</html>");
            enterX0Title.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterX0Field = new JTextField();
            enterX0Field.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterX0Field.setPreferredSize(new Dimension(200, 50));

            JLabel enterY0Title = new JLabel("<html>" + languageBundle.getString("enterButton") + " Y0 : " + "</html>");
            enterY0Title.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterY0Field = new JTextField();
            enterY0Field.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterY0Field.setPreferredSize(new Dimension(200, 50));

            JLabel enterHTitle = new JLabel("<html>" + languageBundle.getString("enterButton") + " h : " + "</html>");
            enterHTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterHField = new JTextField();
            enterHField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterHField.setPreferredSize(new Dimension(200, 50));

            JLabel enterXTitle = new JLabel("<html>" + languageBundle.getString("enterButton") + " X : " + "</html>");
            enterXTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterXField = new JTextField();
            enterXField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterXField.setPreferredSize(new Dimension(200, 50));

            JPanel contentPanel = new JPanel();
            GridBagLayout layout = new GridBagLayout();
            contentPanel.setLayout(layout);
            contentPanel.setComponentOrientation(orientation);

            GridBagConstraints constraints = new GridBagConstraints();
            constraints.fill = GridBagConstraints.HORIZONTAL;
            if (local.getLanguage().equals("en"))
                constraints.insets = new Insets(5, -130, 5, 10); // Adjust the padding as needed
            else constraints.insets = new Insets(5, 0, 5, 10); // Adjust the padding as needed


            if (local.getLanguage().equals("en"))
                constraints.gridx = 0;
            else
                constraints.gridx = 1;
            constraints.gridy = 0;
            contentPanel.add(enterX0Title, constraints);

            if (local.getLanguage().equals("en"))
                constraints.gridx = 1;
            else
                constraints.gridx = 0;
            constraints.weightx = 1.0; // Make the text field expand horizontally
            contentPanel.add(enterX0Field, constraints);

            constraints.gridy++;

            if (local.getLanguage().equals("en"))
                constraints.gridx = 0;
            else
                constraints.gridx = 1;
            contentPanel.add(enterY0Title, constraints);

            if (local.getLanguage().equals("en"))
                constraints.gridx = 1;
            else
                constraints.gridx = 0;
            constraints.weightx = 1.0; // Make the text field expand horizontally
            contentPanel.add(enterY0Field, constraints);

            constraints.gridy++;

            if (local.getLanguage().equals("en"))
                constraints.gridx = 0;
            else
                constraints.gridx = 1;
            contentPanel.add(enterHTitle, constraints);

            if (local.getLanguage().equals("en"))
                constraints.gridx = 1;
            else
                constraints.gridx = 0;
            constraints.weightx = 1.0; // Make the text field expand horizontally
            contentPanel.add(enterHField, constraints);

            constraints.gridy++;

            if (local.getLanguage().equals("en"))
                constraints.gridx = 0;
            else
                constraints.gridx = 1;
            contentPanel.add(enterXTitle, constraints);

            if (local.getLanguage().equals("en"))
                constraints.gridx = 1;
            else
                constraints.gridx = 0;
            constraints.weightx = 1.0; // Make the text field expand horizontally
            contentPanel.add(enterXField, constraints);

            contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));

            JPanel showPanel = new JPanel(new BorderLayout());
            showPanel.add(taylotTitle, BorderLayout.NORTH);
            showPanel.add(inputsPanel, BorderLayout.CENTER);
            showPanel.add(contentPanel, BorderLayout.SOUTH);

            JButton solveButton = new JButton(Solve);
            solveButton.setFocusPainted(false);
            solveButton.setPreferredSize(new Dimension(80, 40));
            solveButton.setFont(new Font("Arial", Font.PLAIN, 17));
            Color customGreen = new Color(34, 139, 34);  // RGB values for green color
            solveButton.setBackground(customGreen);
            solveButton.setForeground(Color.white);  // Set the text color to white for better visibility
            solveButton.setEnabled(false);

            ArrayList<JTextField> divsFields = new ArrayList<>();
            confirmButton.addActionListener(confirm -> {
                solveButton.setEnabled(false);
                // Handle confirm button action
                int n = (int) enterNSp.getValue();
                JPanel divPanel = new JPanel(new GridBagLayout());
                GridBagConstraints gbc2 = new GridBagConstraints();
                gbc2.gridx = 0;
                gbc2.gridy = 0;
                gbc2.anchor = GridBagConstraints.WEST;
                gbc2.insets = new Insets(5, 0, 5, 10); // Add spacing between components

                divsFields.clear();

                for (int i = 1; i <= n; i++) {
                    JLabel divLabel = new JLabel("y^(" + i + ") = ");
                    divLabel.setFont(new Font(mainFont, Font.PLAIN, 20));
                    JTextField textFieldDiv = new JTextField(10);
                    textFieldDiv.setFont(new Font(secondFont, Font.PLAIN, 20));

                    textFieldDiv.setName("y" + i);

                    divsFields.add(textFieldDiv);

                    JPanel componentPanel = new JPanel(new GridBagLayout());
                    GridBagConstraints gbcComponent = new GridBagConstraints();
                    gbcComponent.gridx = 0;
                    gbcComponent.gridy = 0;
                    gbcComponent.anchor = GridBagConstraints.WEST;
                    gbcComponent.insets = new Insets(5, 0, 5, 10); // Add spacing between components

                    componentPanel.add(divLabel, gbcComponent);

                    gbcComponent.gridx++;
                    componentPanel.add(textFieldDiv, gbcComponent);

                    gbc2.gridy++;
                    divPanel.add(componentPanel, gbc2);
                }
                enterDivsScroll.setViewportView(divPanel); // Set the pointsPanel as the viewport's view component
                enterDivsScroll.revalidate(); // Revalidate the scroll pane to update its content

                // Add a DocumentListener to each text field;
                ArrayList<JTextField> fields = new ArrayList<>(divsFields);
                fields.add(enterX0Field);
                fields.add(enterY0Field);
                fields.add(enterHField);
                fields.add(enterXField);
                addDocumentListenerToFields(solveButton, fields);
                addDocumentListenerToFields(solveButton, fields);
            });

            JButton cancelButton = new JButton(cancel);
            cancelButton.setFocusPainted(false);
            cancelButton.setPreferredSize(new Dimension(100, 40));
            cancelButton.setFont(new Font("Arial", Font.PLAIN, 17));

            Object[] buttons = {cancelButton, solveButton};


            cancelButton.addActionListener(cancel -> {
                Window optionDialog = SwingUtilities.getWindowAncestor(contentPanel);
                optionDialog.dispose();
            });

            solveButton.addActionListener(solve -> {
                Window optionDialog = SwingUtilities.getWindowAncestor(contentPanel);
                optionDialog.dispose();
                try {
                    ArrayList<DifferentialEquation> eqs = new ArrayList<>();
                    for (JTextField field : divsFields) {
                        String eqS = field.getText();
                        DifferentialEquation eq = new DifferentialEquation(eqS);
                        eqs.add(eq);
                    }
                    BigDecimal x0 = EvaluateString.evaluate(enterX0Field.getText());
                    BigDecimal y0 = EvaluateString.evaluate(enterY0Field.getText());
                    BigDecimal h = EvaluateString.evaluate(enterHField.getText());
                    BigDecimal x = EvaluateString.evaluate(enterXField.getText());
                    BigDecimal ans = DifferentialEquation.Taylor.solve(eqs, x0, y0, h, x);

                    //create title
                    JLabel difeqTitle = new JLabel();
                    difeqTitle.setText("<html>" + languageBundle.getString("taylorDescription") + " : " + "</html>");
                    difeqTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    //create ans scrolled
                    JTextArea difeqAns = new JTextArea();
                    difeqAns.append("Answer = ");
                    difeqAns.append(String.valueOf(fixAccuracy(ans)));
                    difeqAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    difeqAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(difeqAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(100, 50));

                    //create content panel
                    JPanel ansContentPanel = new JPanel(new GridLayout(2, 1));
                    ansContentPanel.add(difeqTitle);
                    ansContentPanel.add(polyAnsScrollPane);

                    JOptionPane.showConfirmDialog(null, ansContentPanel, diffEqTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? invalidInputs : ex.getMessage(), error, JOptionPane.ERROR_MESSAGE);
                }
            });

            JOptionPane.showOptionDialog(null, showPanel, diffEqTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon128, buttons, null);
        };
        JPanel taylorCard = createCard(title, description, button, enterTaylor);
        differentialEquationsPanel.add(taylorCard);

        //***********************************************************************

        //init Modified Euler Card
        title = "<html>" + languageBundle.getString("modifiedEulerTitle") + "</html>";
        description = "<html>" + languageBundle.getString("modifiedEulerDescription") + "</html>";
        button = enter;
        ActionListener enterModifiedEuler = e -> {
            JLabel modEulerTitleLabel = new JLabel("<html>" + languageBundle.getString("midpointEuler") + "</html>");
            modEulerTitleLabel.setComponentOrientation(orientation);
            modEulerTitleLabel.setFont(new Font(mainFont, Font.BOLD, 25));
            JPanel modEulerTitle = new JPanel();
            modEulerTitle.add(modEulerTitleLabel);
            modEulerTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

            JLabel enterYTitle = new JLabel("<html>" + languageBundle.getString("enterButton") + " Y ' : " + "</html>");
            enterYTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterYField = new JTextField();
            enterYField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterYField.setPreferredSize(new Dimension(250, 50));

            JLabel enterX0Title = new JLabel("<html>" + languageBundle.getString("enterButton") + " X0 : " + "</html>");
            enterX0Title.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterX0Field = new JTextField();
            enterX0Field.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterX0Field.setPreferredSize(new Dimension(250, 50));

            JLabel enterY0Title = new JLabel("<html>" + languageBundle.getString("enterButton") + " Y0 : " + "</html>");
            enterY0Title.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterY0Field = new JTextField();
            enterY0Field.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterY0Field.setPreferredSize(new Dimension(250, 50));

            JLabel enterHTitle = new JLabel("<html>" + languageBundle.getString("enterButton") + " h : " + "</html>");
            enterHTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterHField = new JTextField();
            enterHField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterHField.setPreferredSize(new Dimension(250, 50));

            JLabel enterXTitle = new JLabel("<html>" + languageBundle.getString("enterButton") + " X : " + "</html>");
            enterXTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterXField = new JTextField();
            enterXField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterXField.setPreferredSize(new Dimension(250, 50));

            JPanel contentPanel;
            if (local.getLanguage().equals("en")) {
                contentPanel = new JPanel(new GridLayout(5, 2, -180, 0));
                contentPanel.add(enterYTitle);
                contentPanel.add(enterYField);
                contentPanel.add(enterX0Title);
                contentPanel.add(enterX0Field);
                contentPanel.add(enterY0Title);
                contentPanel.add(enterY0Field);
                contentPanel.add(enterHTitle);
                contentPanel.add(enterHField);
                contentPanel.add(enterXTitle);
                contentPanel.add(enterXField);
            } else {
                contentPanel = new JPanel(new GridLayout(5, 2, 20, 0));
                contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, -120));
                contentPanel.add(enterYField);
                contentPanel.add(enterYTitle);
                contentPanel.add(enterX0Field);
                contentPanel.add(enterX0Title);
                contentPanel.add(enterY0Field);
                contentPanel.add(enterY0Title);
                contentPanel.add(enterHField);
                contentPanel.add(enterHTitle);
                contentPanel.add(enterXField);
                contentPanel.add(enterXTitle);
            }

            JPanel showPanel = new JPanel(new BorderLayout());
            showPanel.add(modEulerTitle, BorderLayout.NORTH);
            showPanel.add(contentPanel, BorderLayout.CENTER);

            JButton solveButton = new JButton(Solve);
            solveButton.setFocusPainted(false);
            solveButton.setPreferredSize(new Dimension(80, 40));
            solveButton.setFont(new Font("Arial", Font.PLAIN, 17));
            Color customGreen = new Color(34, 139, 34);  // RGB values for green color
            solveButton.setBackground(customGreen);
            solveButton.setForeground(Color.white);  // Set the text color to white for better visibility
            solveButton.setEnabled(false);

            JButton cancelButton = new JButton(cancel);
            cancelButton.setFocusPainted(false);
            cancelButton.setPreferredSize(new Dimension(100, 40));
            cancelButton.setFont(new Font("Arial", Font.PLAIN, 17));

            Object[] buttons = {cancelButton, solveButton};

            ArrayList<JTextField> fields = new ArrayList<>();
            fields.add(enterYField);
            fields.add(enterX0Field);
            fields.add(enterY0Field);
            fields.add(enterHField);
            fields.add(enterXField);
            addDocumentListenerToFields(solveButton, fields);

            cancelButton.addActionListener(cancel -> {
                Window optionDialog = SwingUtilities.getWindowAncestor(contentPanel);
                optionDialog.dispose();
            });

            solveButton.addActionListener(solve -> {
                Window optionDialog = SwingUtilities.getWindowAncestor(contentPanel);
                optionDialog.dispose();
                try {
                    String difeq = enterYField.getText();
                    BigDecimal x0 = EvaluateString.evaluate(enterX0Field.getText());
                    BigDecimal y0 = EvaluateString.evaluate(enterY0Field.getText());
                    BigDecimal h = EvaluateString.evaluate(enterHField.getText());
                    BigDecimal x = EvaluateString.evaluate(enterXField.getText());
                    DifferentialEquation eq = new DifferentialEquation(difeq);
                    BigDecimal ans = DifferentialEquation.MidPoint.ModifiedEuler.solve(eq, x0, y0, h, x);

                    //create title
                    JLabel difeqTitle = new JLabel();
                    difeqTitle.setText("<html>" + languageBundle.getString("modifiedEulerDescription") + " : " + "</html>");
                    difeqTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    //create ans scrolled
                    JTextArea difeqAns = new JTextArea();
                    difeqAns.append("Answer = ");
                    difeqAns.append(String.valueOf(fixAccuracy(ans)));
                    difeqAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    difeqAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(difeqAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(100, 50));

                    //create content panel
                    JPanel ansContentPanel = new JPanel(new GridLayout(2, 1));
                    ansContentPanel.add(difeqTitle);
                    ansContentPanel.add(polyAnsScrollPane);

                    JOptionPane.showConfirmDialog(null, ansContentPanel, diffEqTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? invalidInputs : ex.getMessage(), error, JOptionPane.ERROR_MESSAGE);
                }
            });

            JOptionPane.showOptionDialog(null, showPanel, diffEqTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon128, buttons, null);
        };
        JPanel modifiedEulerCard = createCard(title, description, button, enterModifiedEuler);
        differentialEquationsPanel.add(modifiedEulerCard);


        //***********************************************************************

        //init Hein Card
        title = "<html>" + languageBundle.getString("heinTitle") + "</html>";
        description = "<html>" + languageBundle.getString("heinDescription") + "</html>";
        button = enter;
        ActionListener enterHein = e -> {
            JLabel heinTitleLabel = new JLabel("<html>" + languageBundle.getString("midpointHein") + "</html>");
            heinTitleLabel.setComponentOrientation(orientation);
            heinTitleLabel.setFont(new Font(mainFont, Font.BOLD, 25));
            JPanel heinTitle = new JPanel();
            heinTitle.add(heinTitleLabel);
            heinTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

            JLabel enterYTitle = new JLabel("<html>" + languageBundle.getString("enterButton") + " Y ' : " + "</html>");
            enterYTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterYField = new JTextField();
            enterYField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterYField.setPreferredSize(new Dimension(200, 50));

            JLabel enterX0Title = new JLabel("<html>" + languageBundle.getString("enterButton") + " X0 : " + "</html>");
            enterX0Title.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterX0Field = new JTextField();
            enterX0Field.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterX0Field.setPreferredSize(new Dimension(200, 50));

            JLabel enterY0Title = new JLabel("<html>" + languageBundle.getString("enterButton") + " Y0 : " + "</html>");
            enterY0Title.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterY0Field = new JTextField();
            enterY0Field.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterY0Field.setPreferredSize(new Dimension(200, 50));

            JLabel enterHTitle = new JLabel("<html>" + languageBundle.getString("enterButton") + " h : " + "</html>");
            enterHTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterHField = new JTextField();
            enterHField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterHField.setPreferredSize(new Dimension(200, 50));

            JLabel enterXTitle = new JLabel("<html>" + languageBundle.getString("enterButton") + " X : " + "</html>");
            enterXTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterXField = new JTextField();
            enterXField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterXField.setPreferredSize(new Dimension(200, 50));

            JPanel contentPanel;
            if (local.getLanguage().equals("en")) {
                contentPanel = new JPanel(new GridLayout(5, 2, -100, 0));
                contentPanel.add(enterYTitle);
                contentPanel.add(enterYField);
                contentPanel.add(enterX0Title);
                contentPanel.add(enterX0Field);
                contentPanel.add(enterY0Title);
                contentPanel.add(enterY0Field);
                contentPanel.add(enterHTitle);
                contentPanel.add(enterHField);
                contentPanel.add(enterXTitle);
                contentPanel.add(enterXField);
            } else {
                contentPanel = new JPanel(new GridLayout(5, 2, 20, 0));
                contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, -100));
                contentPanel.add(enterYField);
                contentPanel.add(enterYTitle);
                contentPanel.add(enterX0Field);
                contentPanel.add(enterX0Title);
                contentPanel.add(enterY0Field);
                contentPanel.add(enterY0Title);
                contentPanel.add(enterHField);
                contentPanel.add(enterHTitle);
                contentPanel.add(enterXField);
                contentPanel.add(enterXTitle);
            }

            JPanel showPanel = new JPanel(new BorderLayout());
            showPanel.add(heinTitle, BorderLayout.NORTH);
            showPanel.add(contentPanel, BorderLayout.CENTER);

            JButton solveButton = new JButton(Solve);
            solveButton.setFocusPainted(false);
            solveButton.setPreferredSize(new Dimension(80, 40));
            solveButton.setFont(new Font("Arial", Font.PLAIN, 17));
            Color customGreen = new Color(34, 139, 34);  // RGB values for green color
            solveButton.setBackground(customGreen);
            solveButton.setForeground(Color.white);  // Set the text color to white for better visibility
            solveButton.setEnabled(false);

            JButton cancelButton = new JButton(cancel);
            cancelButton.setFocusPainted(false);
            cancelButton.setPreferredSize(new Dimension(100, 40));
            cancelButton.setFont(new Font("Arial", Font.PLAIN, 17));

            Object[] buttons = {cancelButton, solveButton};

            ArrayList<JTextField> fields = new ArrayList<>();
            fields.add(enterYField);
            fields.add(enterX0Field);
            fields.add(enterY0Field);
            fields.add(enterHField);
            fields.add(enterXField);
            addDocumentListenerToFields(solveButton, fields);

            cancelButton.addActionListener(cancel -> {
                Window optionDialog = SwingUtilities.getWindowAncestor(contentPanel);
                optionDialog.dispose();
            });

            solveButton.addActionListener(solve -> {
                Window optionDialog = SwingUtilities.getWindowAncestor(contentPanel);
                optionDialog.dispose();
                try {
                    String difeq = enterYField.getText();
                    BigDecimal x0 = EvaluateString.evaluate(enterX0Field.getText());
                    BigDecimal y0 = EvaluateString.evaluate(enterY0Field.getText());
                    BigDecimal h = EvaluateString.evaluate(enterHField.getText());
                    BigDecimal x = EvaluateString.evaluate(enterXField.getText());
                    DifferentialEquation eq = new DifferentialEquation(difeq);
                    BigDecimal ans = DifferentialEquation.MidPoint.Heun.solve(eq, x0, y0, h, x);

                    //create title
                    JLabel difeqTitle = new JLabel();
                    difeqTitle.setText("<html>" + languageBundle.getString("heinDescription") + " : " + "</html>");
                    difeqTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    //create ans scrolled
                    JTextArea difeqAns = new JTextArea();
                    difeqAns.append("Answer = ");
                    difeqAns.append(String.valueOf(fixAccuracy(ans)));
                    difeqAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    difeqAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(difeqAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(100, 50));

                    //create content panel
                    JPanel ansContentPanel = new JPanel(new GridLayout(2, 1));
                    ansContentPanel.add(difeqTitle);
                    ansContentPanel.add(polyAnsScrollPane);

                    JOptionPane.showConfirmDialog(null, ansContentPanel, diffEqTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? invalidInputs : ex.getMessage(), error, JOptionPane.ERROR_MESSAGE);
                }
            });

            JOptionPane.showOptionDialog(null, showPanel, diffEqTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon128, buttons, null);
        };
        JPanel heinCard = createCard(title, description, button, enterHein);
        differentialEquationsPanel.add(heinCard);

        //***********************************************************************

        //init Ralston Card
        title = "<html>" + languageBundle.getString("ralstonTitle") + "</html>";
        description = "<html>" + languageBundle.getString("ralstonDescription") + "</html>";
        button = enter;
        ActionListener enterRalston = e -> {
            JLabel ralstonTitleLabel = new JLabel("<html>" + languageBundle.getString("midpointRalston") + "</html>");
            ralstonTitleLabel.setComponentOrientation(orientation);
            ralstonTitleLabel.setFont(new Font(mainFont, Font.BOLD, 25));
            JPanel ralstonTitle = new JPanel();
            ralstonTitle.add(ralstonTitleLabel);
            ralstonTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

            JLabel enterYTitle = new JLabel("<html>" + languageBundle.getString("enterButton") + " Y ' : " + "</html>");
            enterYTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterYField = new JTextField();
            enterYField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterYField.setPreferredSize(new Dimension(200, 50));

            JLabel enterX0Title = new JLabel("<html>" + languageBundle.getString("enterButton") + " X0 : " + "</html>");
            enterX0Title.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterX0Field = new JTextField();
            enterX0Field.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterX0Field.setPreferredSize(new Dimension(200, 50));

            JLabel enterY0Title = new JLabel("<html>" + languageBundle.getString("enterButton") + " Y0 : " + "</html>");
            enterY0Title.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterY0Field = new JTextField();
            enterY0Field.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterY0Field.setPreferredSize(new Dimension(200, 50));

            JLabel enterHTitle = new JLabel("<html>" + languageBundle.getString("enterButton") + " h : " + "</html>");
            enterHTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterHField = new JTextField();
            enterHField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterHField.setPreferredSize(new Dimension(200, 50));

            JLabel enterXTitle = new JLabel("<html>" + languageBundle.getString("enterButton") + " X : " + "</html>");
            enterXTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterXField = new JTextField();
            enterXField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterXField.setPreferredSize(new Dimension(200, 50));

            JPanel contentPanel;
            if (local.getLanguage().equals("en")) {
                contentPanel = new JPanel(new GridLayout(5, 2, -100, 0));
                contentPanel.add(enterYTitle);
                contentPanel.add(enterYField);
                contentPanel.add(enterX0Title);
                contentPanel.add(enterX0Field);
                contentPanel.add(enterY0Title);
                contentPanel.add(enterY0Field);
                contentPanel.add(enterHTitle);
                contentPanel.add(enterHField);
                contentPanel.add(enterXTitle);
                contentPanel.add(enterXField);
            } else {
                contentPanel = new JPanel(new GridLayout(5, 2, 20, 0));
                contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, -100));
                contentPanel.add(enterYField);
                contentPanel.add(enterYTitle);
                contentPanel.add(enterX0Field);
                contentPanel.add(enterX0Title);
                contentPanel.add(enterY0Field);
                contentPanel.add(enterY0Title);
                contentPanel.add(enterHField);
                contentPanel.add(enterHTitle);
                contentPanel.add(enterXField);
                contentPanel.add(enterXTitle);
            }

            JPanel showPanel = new JPanel(new BorderLayout());
            showPanel.add(ralstonTitle, BorderLayout.NORTH);
            showPanel.add(contentPanel, BorderLayout.CENTER);

            JButton solveButton = new JButton(Solve);
            solveButton.setFocusPainted(false);
            solveButton.setPreferredSize(new Dimension(80, 40));
            solveButton.setFont(new Font("Arial", Font.PLAIN, 17));
            Color customGreen = new Color(34, 139, 34);  // RGB values for green color
            solveButton.setBackground(customGreen);
            solveButton.setForeground(Color.white);  // Set the text color to white for better visibility
            solveButton.setEnabled(false);

            JButton cancelButton = new JButton(cancel);
            cancelButton.setFocusPainted(false);
            cancelButton.setPreferredSize(new Dimension(100, 40));
            cancelButton.setFont(new Font("Arial", Font.PLAIN, 17));

            Object[] buttons = {cancelButton, solveButton};

            ArrayList<JTextField> fields = new ArrayList<>();
            fields.add(enterYField);
            fields.add(enterX0Field);
            fields.add(enterY0Field);
            fields.add(enterHField);
            fields.add(enterXField);
            addDocumentListenerToFields(solveButton, fields);

            cancelButton.addActionListener(cancel -> {
                Window optionDialog = SwingUtilities.getWindowAncestor(contentPanel);
                optionDialog.dispose();
            });

            solveButton.addActionListener(solve -> {
                Window optionDialog = SwingUtilities.getWindowAncestor(contentPanel);
                optionDialog.dispose();
                try {
                    String difeq = enterYField.getText();
                    BigDecimal x0 = EvaluateString.evaluate(enterX0Field.getText());
                    BigDecimal y0 = EvaluateString.evaluate(enterY0Field.getText());
                    BigDecimal h = EvaluateString.evaluate(enterHField.getText());
                    BigDecimal x = EvaluateString.evaluate(enterXField.getText());
                    DifferentialEquation eq = new DifferentialEquation(difeq);
                    BigDecimal ans = DifferentialEquation.MidPoint.Ralston.solve(eq, x0, y0, h, x);

                    //create title
                    JLabel difeqTitle = new JLabel();
                    difeqTitle.setText("<html>" + languageBundle.getString("ralstonDescription") + " : " + "</html>");
                    difeqTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    //create ans scrolled
                    JTextArea difeqAns = new JTextArea();
                    difeqAns.append("Answer = ");
                    difeqAns.append(String.valueOf(fixAccuracy(ans)));
                    difeqAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    difeqAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(difeqAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(100, 50));

                    //create content panel
                    JPanel ansContentPanel = new JPanel(new GridLayout(2, 1));
                    ansContentPanel.add(difeqTitle);
                    ansContentPanel.add(polyAnsScrollPane);

                    JOptionPane.showConfirmDialog(null, ansContentPanel, diffEqTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? invalidInputs : ex.getMessage(), error, JOptionPane.ERROR_MESSAGE);
                }
            });

            JOptionPane.showOptionDialog(null, showPanel, diffEqTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon128, buttons, null);
        };
        JPanel ralstonCard = createCard(title, description, button, enterRalston);
        differentialEquationsPanel.add(ralstonCard);

        //***********************************************************************

        //init Runge_Kutta Card
        title = "<html>" + languageBundle.getString("rungeKuttaTitle") + "</html>";
        description = "<html>" + languageBundle.getString("rungeKuttaDescription") + "</html>";
        button = enter;
        ActionListener enterRunge_Kutta = e -> {
            JLabel rangeTitleLabel = new JLabel("<html>" + languageBundle.getString("rungeKuttaMethod") + "</html>");
            rangeTitleLabel.setComponentOrientation(orientation);
            rangeTitleLabel.setFont(new Font(mainFont, Font.BOLD, 25));
            JPanel rangeTitle = new JPanel();
            rangeTitle.add(rangeTitleLabel);
            rangeTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

            JLabel enterYTitle = new JLabel("<html>" + languageBundle.getString("enterButton") + " Y ' : " + "</html>");
            enterYTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterYField = new JTextField();
            enterYField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterYField.setPreferredSize(new Dimension(200, 50));

            JLabel enterX0Title = new JLabel("<html>" + languageBundle.getString("enterButton") + " X0 : " + "</html>");
            enterX0Title.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterX0Field = new JTextField();
            enterX0Field.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterX0Field.setPreferredSize(new Dimension(200, 50));

            JLabel enterY0Title = new JLabel("<html>" + languageBundle.getString("enterButton") + " Y0 : " + "</html>");
            enterY0Title.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterY0Field = new JTextField();
            enterY0Field.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterY0Field.setPreferredSize(new Dimension(200, 50));

            JLabel enterHTitle = new JLabel("<html>" + languageBundle.getString("enterButton") + " h : " + "</html>");
            enterHTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterHField = new JTextField();
            enterHField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterHField.setPreferredSize(new Dimension(200, 50));

            JLabel enterXTitle = new JLabel("<html>" + languageBundle.getString("enterButton") + " X : " + "</html>");
            enterXTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterXField = new JTextField();
            enterXField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterXField.setPreferredSize(new Dimension(200, 50));

            JPanel contentPanel;
            if (local.getLanguage().equals("en")) {
                contentPanel = new JPanel(new GridLayout(5, 2, -100, 0));
                contentPanel.add(enterYTitle);
                contentPanel.add(enterYField);
                contentPanel.add(enterX0Title);
                contentPanel.add(enterX0Field);
                contentPanel.add(enterY0Title);
                contentPanel.add(enterY0Field);
                contentPanel.add(enterHTitle);
                contentPanel.add(enterHField);
                contentPanel.add(enterXTitle);
                contentPanel.add(enterXField);
            } else {
                contentPanel = new JPanel(new GridLayout(5, 2, 20, 0));
                contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, -100));
                contentPanel.add(enterYField);
                contentPanel.add(enterYTitle);
                contentPanel.add(enterX0Field);
                contentPanel.add(enterX0Title);
                contentPanel.add(enterY0Field);
                contentPanel.add(enterY0Title);
                contentPanel.add(enterHField);
                contentPanel.add(enterHTitle);
                contentPanel.add(enterXField);
                contentPanel.add(enterXTitle);
            }

            JPanel showPanel = new JPanel(new BorderLayout());
            showPanel.add(rangeTitle, BorderLayout.NORTH);
            showPanel.add(contentPanel, BorderLayout.CENTER);

            JButton solveButton = new JButton(Solve);
            solveButton.setFocusPainted(false);
            solveButton.setPreferredSize(new Dimension(80, 40));
            solveButton.setFont(new Font("Arial", Font.PLAIN, 17));
            Color customGreen = new Color(34, 139, 34);  // RGB values for green color
            solveButton.setBackground(customGreen);
            solveButton.setForeground(Color.white);  // Set the text color to white for better visibility
            solveButton.setEnabled(false);

            JButton cancelButton = new JButton(cancel);
            cancelButton.setFocusPainted(false);
            cancelButton.setPreferredSize(new Dimension(100, 40));
            cancelButton.setFont(new Font("Arial", Font.PLAIN, 17));

            Object[] buttons = {cancelButton, solveButton};

            ArrayList<JTextField> fields = new ArrayList<>();
            fields.add(enterYField);
            fields.add(enterX0Field);
            fields.add(enterY0Field);
            fields.add(enterHField);
            fields.add(enterXField);
            addDocumentListenerToFields(solveButton, fields);

            cancelButton.addActionListener(cancel -> {
                Window optionDialog = SwingUtilities.getWindowAncestor(contentPanel);
                optionDialog.dispose();
            });

            solveButton.addActionListener(solve -> {
                Window optionDialog = SwingUtilities.getWindowAncestor(contentPanel);
                optionDialog.dispose();
                try {
                    String difeq = enterYField.getText();
                    BigDecimal x0 = EvaluateString.evaluate(enterX0Field.getText());
                    BigDecimal y0 = EvaluateString.evaluate(enterY0Field.getText());
                    BigDecimal h = EvaluateString.evaluate(enterHField.getText());
                    BigDecimal x = EvaluateString.evaluate(enterXField.getText());
                    DifferentialEquation eq = new DifferentialEquation(difeq);
                    BigDecimal ans = DifferentialEquation.Runge_Kutta.solve(eq, x0, y0, h, x);

                    //create title
                    JLabel difeqTitle = new JLabel();
                    difeqTitle.setText("<html>" + languageBundle.getString("rungeKuttaDescription") + " : " + "</html>");
                    difeqTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    //create ans scrolled
                    JTextArea difeqAns = new JTextArea();
                    difeqAns.append("Answer = ");
                    difeqAns.append(String.valueOf(fixAccuracy(ans)));
                    difeqAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    difeqAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(difeqAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(100, 50));

                    //create content panel
                    JPanel ansContentPanel = new JPanel(new GridLayout(2, 1));
                    ansContentPanel.add(difeqTitle);
                    ansContentPanel.add(polyAnsScrollPane);

                    JOptionPane.showConfirmDialog(null, ansContentPanel, diffEqTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? invalidInputs : ex.getMessage(), error, JOptionPane.ERROR_MESSAGE);
                }
            });

            JOptionPane.showOptionDialog(null, showPanel, diffEqTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon128, buttons, null);
        };
        JPanel runge_KuttaCard = createCard(title, description, button, enterRunge_Kutta);
        differentialEquationsPanel.add(runge_KuttaCard);

        //***********************************************************************
    }

    /**
     * Initializes the Non-Linear Equations panel.
     * Sets the name, size, and background color of the panel.
     * Creates cards for Non-Linear Equations methods with corresponding action listeners.
     * Adds the cards to the panel.
     */
    private void initNonLinEQPanel() {
        nonLinearEquationsPanel = new JPanel();
        nonLinearEquationsPanel.setName("Non Linear Equations");
        nonLinearEquationsPanel.setPreferredSize(mainFrame.getSize());
        nonLinearEquationsPanel.setBackground(new Color(100, 100, 100));
        GridLayout startLayout = new GridLayout(3, 2);
        startLayout.setHgap(5);
        startLayout.setVgap(5);
        nonLinearEquationsPanel.setLayout(startLayout);
        nonLinearEquationsPanel.setComponentOrientation(orientation);
        String NonLineaEqsTitle = "<html>" + languageBundle.getString("nonLinearEquationsTitle") + "</html>";

        //***********************************************************************

        //init Bisection Card
        String title = "<html>" + languageBundle.getString("bisectionTitle") + "</html>";
        String description = "<html>" + languageBundle.getString("bisectionDescription") + "</html>";
        String button = enter;
        ActionListener enterBisection = e -> {
            JLabel bisTitleLabel = new JLabel("<html>" + languageBundle.getString("bisectionMethod") + "</html>");
            bisTitleLabel.setComponentOrientation(orientation);
            bisTitleLabel.setFont(new Font(mainFont, Font.BOLD, 25));
            JPanel bisTitle = new JPanel();
            bisTitle.add(bisTitleLabel);
            bisTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

            JLabel enterFTitle = new JLabel("<html>" + languageBundle.getString("enterButton") + " f(x) : " + "</html>");
            enterFTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterFField = new JTextField();
            enterFField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterFField.setPreferredSize(new Dimension(200, 50));

            JLabel enterATitle = new JLabel("<html>" + languageBundle.getString("enterButton") + " a : " + "</html>");
            enterATitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterAField = new JTextField();
            enterAField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterAField.setPreferredSize(new Dimension(200, 50));

            JLabel enterBTitle = new JLabel("<html>" + languageBundle.getString("enterButton") + " b : " + "</html>");
            enterBTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterBField = new JTextField();
            enterBField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterBField.setPreferredSize(new Dimension(200, 50));

            JLabel enterETitle = new JLabel("<html>" + languageBundle.getString("enterButton") + " e : " + "</html>");
            enterETitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterEField = new JTextField();
            enterEField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterEField.setPreferredSize(new Dimension(200, 50));


            JPanel contentPanel;
            if (local.getLanguage().equals("en")) {
                contentPanel = new JPanel(new GridLayout(4, 2, -100, 0));
                contentPanel.add(enterFTitle);
                contentPanel.add(enterFField);
                contentPanel.add(enterATitle);
                contentPanel.add(enterAField);
                contentPanel.add(enterBTitle);
                contentPanel.add(enterBField);
                contentPanel.add(enterETitle);
                contentPanel.add(enterEField);
            } else {
                contentPanel = new JPanel(new GridLayout(4, 2, 20, 0));
                contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, -100));
                contentPanel.add(enterFField);
                contentPanel.add(enterFTitle);
                contentPanel.add(enterAField);
                contentPanel.add(enterATitle);
                contentPanel.add(enterBField);
                contentPanel.add(enterBTitle);
                contentPanel.add(enterEField);
                contentPanel.add(enterETitle);
            }

            JPanel showPanel = new JPanel(new BorderLayout());
            showPanel.add(bisTitle, BorderLayout.NORTH);
            showPanel.add(contentPanel, BorderLayout.CENTER);

            JButton solveButton = new JButton(Solve);
            solveButton.setFocusPainted(false);
            solveButton.setPreferredSize(new Dimension(80, 40));
            solveButton.setFont(new Font("Arial", Font.PLAIN, 17));
            Color customGreen = new Color(34, 139, 34);  // RGB values for green color
            solveButton.setBackground(customGreen);
            solveButton.setForeground(Color.white);  // Set the text color to white for better visibility
            solveButton.setEnabled(false);

            JButton cancelButton = new JButton(cancel);
            cancelButton.setFocusPainted(false);
            cancelButton.setPreferredSize(new Dimension(100, 40));
            cancelButton.setFont(new Font("Arial", Font.PLAIN, 17));

            Object[] buttons = {cancelButton, solveButton};

            ArrayList<JTextField> fields = new ArrayList<>();
            fields.add(enterFField);
            fields.add(enterAField);
            fields.add(enterBField);
            fields.add(enterEField);
            addDocumentListenerToFields(solveButton, fields);

            cancelButton.addActionListener(cancel -> {
                Window optionDialog = SwingUtilities.getWindowAncestor(contentPanel);
                optionDialog.dispose();
            });

            solveButton.addActionListener(solve -> {
                Window optionDialog = SwingUtilities.getWindowAncestor(contentPanel);
                optionDialog.dispose();
                try {
                    String fx = enterFField.getText();
                    BigDecimal a = EvaluateString.evaluate(enterAField.getText());
                    BigDecimal b = EvaluateString.evaluate(enterBField.getText());
                    BigDecimal err = EvaluateString.evaluate(enterEField.getText());
                    ExpressionFunction func = new ExpressionFunction(fx);
                    BigDecimal ans = NonLinearEquation.Bisection.solve(func, a, b, err);

                    //create title
                    JLabel difeqTitle = new JLabel();
                    difeqTitle.setText("<html>" + languageBundle.getString("bisectionDescription") + " : " + "</html>");
                    difeqTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    //create ans scrolled
                    JTextArea difeqAns = new JTextArea();
                    difeqAns.append("Answer = ");
                    difeqAns.append(String.valueOf(fixAccuracy(ans)));
                    difeqAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    difeqAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(difeqAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(100, 50));

                    //create content panel
                    JPanel ansContentPanel = new JPanel(new GridLayout(2, 1));
                    ansContentPanel.add(difeqTitle);
                    ansContentPanel.add(polyAnsScrollPane);

                    JOptionPane.showConfirmDialog(null, ansContentPanel, NonLineaEqsTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? invalidInputs : ex.getMessage(), error, JOptionPane.ERROR_MESSAGE);
                }
            });

            JOptionPane.showOptionDialog(null, showPanel, NonLineaEqsTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon128, buttons, null);
        };
        JPanel bisectionCard = createCard(title, description, button, enterBisection);
        nonLinearEquationsPanel.add(bisectionCard);

        //***********************************************************************

        //init False Position Card
        title = "<html>" + languageBundle.getString("falsePositionTitle") + "</html>";
        description = "<html>" + languageBundle.getString("falsePositionDescription") + "</html>";
        button = enter;
        ActionListener enterFalsePosition = e -> {
            JLabel flsTitleLabel = new JLabel("<html>" + languageBundle.getString("falsePositionMethod") + "</html>");
            flsTitleLabel.setComponentOrientation(orientation);
            flsTitleLabel.setFont(new Font(mainFont, Font.BOLD, 25));
            JPanel flsTitle = new JPanel();
            flsTitle.add(flsTitleLabel);
            flsTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

            JLabel enterFTitle = new JLabel("<html>" + languageBundle.getString("enterButton") + " f(x) : " + "</html>");
            enterFTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterFField = new JTextField();
            enterFField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterFField.setPreferredSize(new Dimension(200, 50));

            JLabel enterATitle = new JLabel("<html>" + languageBundle.getString("enterButton") + " a : " + "</html>");
            enterATitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterAField = new JTextField();
            enterAField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterAField.setPreferredSize(new Dimension(200, 50));

            JLabel enterBTitle = new JLabel("<html>" + languageBundle.getString("enterButton") + " b : " + "</html>");
            enterBTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterBField = new JTextField();
            enterBField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterBField.setPreferredSize(new Dimension(200, 50));

            JLabel enterETitle = new JLabel("<html>" + languageBundle.getString("enterButton") + " e : " + "</html>");
            enterETitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterEField = new JTextField();
            enterEField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterEField.setPreferredSize(new Dimension(200, 50));


            JPanel contentPanel;
            if (local.getLanguage().equals("en")) {
                contentPanel = new JPanel(new GridLayout(4, 2, -100, 0));
                contentPanel.add(enterFTitle);
                contentPanel.add(enterFField);
                contentPanel.add(enterATitle);
                contentPanel.add(enterAField);
                contentPanel.add(enterBTitle);
                contentPanel.add(enterBField);
                contentPanel.add(enterETitle);
                contentPanel.add(enterEField);
            } else {
                contentPanel = new JPanel(new GridLayout(4, 2, 20, 0));
                contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, -100));
                contentPanel.add(enterFField);
                contentPanel.add(enterFTitle);
                contentPanel.add(enterAField);
                contentPanel.add(enterATitle);
                contentPanel.add(enterBField);
                contentPanel.add(enterBTitle);
                contentPanel.add(enterEField);
                contentPanel.add(enterETitle);
            }

            JPanel showPanel = new JPanel(new BorderLayout());
            showPanel.add(flsTitle, BorderLayout.NORTH);
            showPanel.add(contentPanel, BorderLayout.CENTER);

            JButton solveButton = new JButton(Solve);
            solveButton.setFocusPainted(false);
            solveButton.setPreferredSize(new Dimension(80, 40));
            solveButton.setFont(new Font("Arial", Font.PLAIN, 17));
            Color customGreen = new Color(34, 139, 34);  // RGB values for green color
            solveButton.setBackground(customGreen);
            solveButton.setForeground(Color.white);  // Set the text color to white for better visibility
            solveButton.setEnabled(false);

            JButton cancelButton = new JButton(cancel);
            cancelButton.setFocusPainted(false);
            cancelButton.setPreferredSize(new Dimension(100, 40));
            cancelButton.setFont(new Font("Arial", Font.PLAIN, 17));

            Object[] buttons = {cancelButton, solveButton};

            ArrayList<JTextField> fields = new ArrayList<>();
            fields.add(enterFField);
            fields.add(enterAField);
            fields.add(enterBField);
            fields.add(enterEField);
            addDocumentListenerToFields(solveButton, fields);

            cancelButton.addActionListener(cancel -> {
                Window optionDialog = SwingUtilities.getWindowAncestor(contentPanel);
                optionDialog.dispose();
            });

            solveButton.addActionListener(solve -> {
                Window optionDialog = SwingUtilities.getWindowAncestor(contentPanel);
                optionDialog.dispose();
                try {
                    String fx = enterFField.getText();
                    BigDecimal a = EvaluateString.evaluate(enterAField.getText());
                    BigDecimal b = EvaluateString.evaluate(enterBField.getText());
                    BigDecimal err = EvaluateString.evaluate(enterEField.getText());
                    ExpressionFunction func = new ExpressionFunction(fx);
                    BigDecimal ans = NonLinearEquation.FalsePosition.solve(func, a, b, err);

                    //create title
                    JLabel difeqTitle = new JLabel();
                    difeqTitle.setText("<html>" + languageBundle.getString("falsePositionDescription") + " : " + "</html>");
                    difeqTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    //create ans scrolled
                    JTextArea difeqAns = new JTextArea();
                    difeqAns.append("Answer = ");
                    difeqAns.append(String.valueOf(fixAccuracy(ans)));
                    difeqAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    difeqAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(difeqAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(100, 50));

                    //create content panel
                    JPanel ansContentPanel = new JPanel(new GridLayout(2, 1));
                    ansContentPanel.add(difeqTitle);
                    ansContentPanel.add(polyAnsScrollPane);

                    JOptionPane.showConfirmDialog(null, ansContentPanel, NonLineaEqsTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? invalidInputs : ex.getMessage(), error, JOptionPane.ERROR_MESSAGE);
                }
            });

            JOptionPane.showOptionDialog(null, showPanel, NonLineaEqsTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon128, buttons, null);
        };
        JPanel falsePositionCard = createCard(title, description, button, enterFalsePosition);
        nonLinearEquationsPanel.add(falsePositionCard);

        //***********************************************************************

        //init Secant Card
        title = "<html>" + languageBundle.getString("secantTitle") + "</html>";
        description = "<html>" + languageBundle.getString("secantDescription") + "</html>";
        button = enter;
        ActionListener enterSecant = e -> {
            JLabel secTitleLabel = new JLabel("<html>" + languageBundle.getString("secantMethod") + "</html>");
            secTitleLabel.setComponentOrientation(orientation);
            secTitleLabel.setFont(new Font(mainFont, Font.BOLD, 25));
            JPanel secTitle = new JPanel();
            secTitle.add(secTitleLabel);
            secTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

            JLabel enterFTitle = new JLabel("<html>" + languageBundle.getString("enterButton") + " f(x) : " + "</html>");
            enterFTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterFField = new JTextField();
            enterFField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterFField.setPreferredSize(new Dimension(200, 50));

            JLabel enterX0Title = new JLabel("<html>" + languageBundle.getString("enterButton") + " X0 : " + "</html>");
            enterX0Title.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterX0Field = new JTextField();
            enterX0Field.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterX0Field.setPreferredSize(new Dimension(200, 50));

            JLabel enterX1Title = new JLabel("<html>" + languageBundle.getString("enterButton") + " X1 : " + "</html>");
            enterX1Title.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterX1Field = new JTextField();
            enterX1Field.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterX1Field.setPreferredSize(new Dimension(200, 50));

            JLabel enterETitle = new JLabel("<html>" + languageBundle.getString("enterButton") + " e : " + "</html>");
            enterETitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterEField = new JTextField();
            enterEField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterEField.setPreferredSize(new Dimension(200, 50));


            JPanel contentPanel;
            if (local.getLanguage().equals("en")) {
                contentPanel = new JPanel(new GridLayout(4, 2, -100, 0));
                contentPanel.add(enterFTitle);
                contentPanel.add(enterFField);
                contentPanel.add(enterX0Title);
                contentPanel.add(enterX0Field);
                contentPanel.add(enterX1Title);
                contentPanel.add(enterX1Field);
                contentPanel.add(enterETitle);
                contentPanel.add(enterEField);
            } else {
                contentPanel = new JPanel(new GridLayout(4, 2, 20, 0));
                contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, -100));
                contentPanel.add(enterFField);
                contentPanel.add(enterFTitle);
                contentPanel.add(enterX0Field);
                contentPanel.add(enterX0Title);
                contentPanel.add(enterX1Field);
                contentPanel.add(enterX1Title);
                contentPanel.add(enterEField);
                contentPanel.add(enterETitle);
            }

            JPanel showPanel = new JPanel(new BorderLayout());
            showPanel.add(secTitle, BorderLayout.NORTH);
            showPanel.add(contentPanel, BorderLayout.CENTER);

            JButton solveButton = new JButton(Solve);
            solveButton.setFocusPainted(false);
            solveButton.setPreferredSize(new Dimension(80, 40));
            solveButton.setFont(new Font("Arial", Font.PLAIN, 17));
            Color customGreen = new Color(34, 139, 34);  // RGB values for green color
            solveButton.setBackground(customGreen);
            solveButton.setForeground(Color.white);  // Set the text color to white for better visibility
            solveButton.setEnabled(false);

            JButton cancelButton = new JButton(cancel);
            cancelButton.setFocusPainted(false);
            cancelButton.setPreferredSize(new Dimension(100, 40));
            cancelButton.setFont(new Font("Arial", Font.PLAIN, 17));

            Object[] buttons = {cancelButton, solveButton};

            ArrayList<JTextField> fields = new ArrayList<>();
            fields.add(enterFField);
            fields.add(enterX0Field);
            fields.add(enterX1Field);
            fields.add(enterEField);
            addDocumentListenerToFields(solveButton, fields);

            cancelButton.addActionListener(cancel -> {
                Window optionDialog = SwingUtilities.getWindowAncestor(contentPanel);
                optionDialog.dispose();
            });

            solveButton.addActionListener(solve -> {
                Window optionDialog = SwingUtilities.getWindowAncestor(contentPanel);
                optionDialog.dispose();
                try {
                    String fx = enterFField.getText();
                    BigDecimal x0 = EvaluateString.evaluate(enterX0Field.getText());
                    BigDecimal x1 = EvaluateString.evaluate(enterX1Field.getText());
                    BigDecimal err = EvaluateString.evaluate(enterEField.getText());
                    ExpressionFunction func = new ExpressionFunction(fx);
                    BigDecimal ans = NonLinearEquation.Secant.solve(func, x0, x1, err);

                    //create title
                    JLabel difeqTitle = new JLabel();
                    difeqTitle.setText("<html>" + languageBundle.getString("secantDescription") + " : " + "</html>");
                    difeqTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    //create ans scrolled
                    JTextArea difeqAns = new JTextArea();
                    difeqAns.append("Answer = ");
                    difeqAns.append(String.valueOf(fixAccuracy(ans)));
                    difeqAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    difeqAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(difeqAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(100, 50));

                    //create content panel
                    JPanel ansContentPanel = new JPanel(new GridLayout(2, 1));
                    ansContentPanel.add(difeqTitle);
                    ansContentPanel.add(polyAnsScrollPane);

                    JOptionPane.showConfirmDialog(null, ansContentPanel, NonLineaEqsTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? invalidInputs : ex.getMessage(), error, JOptionPane.ERROR_MESSAGE);
                }
            });

            JOptionPane.showOptionDialog(null, showPanel, NonLineaEqsTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon128, buttons, null);
        };
        JPanel secantCard = createCard(title, description, button, enterSecant);
        nonLinearEquationsPanel.add(secantCard);


        //***********************************************************************

        //init Newton_Raphson Card
        title = "<html>" + languageBundle.getString("newtonRaphsonTitle") + "</html>";
        description = "<html>" + languageBundle.getString("newtonRaphsonDescription") + "</html>";
        button = enter;
        ActionListener enterNewton_Raphson = e -> {
            JLabel newtTitleLabel = new JLabel("<html>" + languageBundle.getString("newtonRaphsonMethod") + "</html>");
            newtTitleLabel.setComponentOrientation(orientation);
            newtTitleLabel.setFont(new Font(mainFont, Font.BOLD, 25));
            JPanel newtTitle = new JPanel();
            newtTitle.add(newtTitleLabel);
            newtTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

            JLabel enterFTitle = new JLabel("<html>" + languageBundle.getString("enterButton") + " f(x) : " + "</html>");
            enterFTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterFField = new JTextField();
            enterFField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterFField.setPreferredSize(new Dimension(200, 50));

            JLabel enterDFTitle = new JLabel("<html>" + languageBundle.getString("enterButton") + " f '(x) : " + "</html>");
            enterDFTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterDFField = new JTextField();
            enterDFField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterDFField.setPreferredSize(new Dimension(200, 50));

            JLabel enterX0Title = new JLabel("<html>" + languageBundle.getString("enterButton") + " X0 : " + "</html>");
            enterX0Title.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterX0Field = new JTextField();
            enterX0Field.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterX0Field.setPreferredSize(new Dimension(200, 50));


            JLabel enterETitle = new JLabel("<html>" + languageBundle.getString("enterButton") + " e : " + "</html>");
            enterETitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterEField = new JTextField();
            enterEField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterEField.setPreferredSize(new Dimension(200, 50));


            JPanel contentPanel;
            if (local.getLanguage().equals("en")) {
                contentPanel = new JPanel(new GridLayout(4, 2, -100, 0));
                contentPanel.add(enterFTitle);
                contentPanel.add(enterFField);
                contentPanel.add(enterDFTitle);
                contentPanel.add(enterDFField);
                contentPanel.add(enterX0Title);
                contentPanel.add(enterX0Field);
                contentPanel.add(enterETitle);
                contentPanel.add(enterEField);
            } else {
                contentPanel = new JPanel(new GridLayout(4, 2, 20, 0));
                contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, -100));
                contentPanel.add(enterFField);
                contentPanel.add(enterFTitle);
                contentPanel.add(enterDFField);
                contentPanel.add(enterDFTitle);
                contentPanel.add(enterX0Field);
                contentPanel.add(enterX0Title);
                contentPanel.add(enterEField);
                contentPanel.add(enterETitle);
            }

            JPanel showPanel = new JPanel(new BorderLayout());
            showPanel.add(newtTitle, BorderLayout.NORTH);
            showPanel.add(contentPanel, BorderLayout.CENTER);

            JButton solveButton = new JButton(Solve);
            solveButton.setFocusPainted(false);
            solveButton.setPreferredSize(new Dimension(80, 40));
            solveButton.setFont(new Font("Arial", Font.PLAIN, 17));
            Color customGreen = new Color(34, 139, 34);  // RGB values for green color
            solveButton.setBackground(customGreen);
            solveButton.setForeground(Color.white);  // Set the text color to white for better visibility
            solveButton.setEnabled(false);

            JButton cancelButton = new JButton(cancel);
            cancelButton.setFocusPainted(false);
            cancelButton.setPreferredSize(new Dimension(100, 40));
            cancelButton.setFont(new Font("Arial", Font.PLAIN, 17));

            Object[] buttons = {cancelButton, solveButton};

            ArrayList<JTextField> fields = new ArrayList<>();
            fields.add(enterFField);
            fields.add(enterDFField);
            fields.add(enterX0Field);
            fields.add(enterEField);
            addDocumentListenerToFields(solveButton, fields);

            cancelButton.addActionListener(cancel -> {
                Window optionDialog = SwingUtilities.getWindowAncestor(contentPanel);
                optionDialog.dispose();
            });

            solveButton.addActionListener(solve -> {
                Window optionDialog = SwingUtilities.getWindowAncestor(contentPanel);
                optionDialog.dispose();
                try {
                    String fx = enterFField.getText();
                    String dfx = enterDFField.getText();
                    BigDecimal x0 = EvaluateString.evaluate(enterX0Field.getText());
                    BigDecimal err = EvaluateString.evaluate(enterEField.getText());
                    ExpressionFunction func = new ExpressionFunction(fx);
                    ExpressionFunction dfunc = new ExpressionFunction(dfx);
                    BigDecimal ans = NonLinearEquation.Newton_Raphson.solve(func, dfunc, x0, err);

                    //create title
                    JLabel difeqTitle = new JLabel();
                    difeqTitle.setText("<html>" + languageBundle.getString("newtonRaphsonDescription") + " : " + "</html>");
                    difeqTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    //create ans scrolled
                    JTextArea difeqAns = new JTextArea();
                    difeqAns.append("Answer = ");
                    difeqAns.append(String.valueOf(fixAccuracy(ans)));
                    difeqAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    difeqAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(difeqAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(100, 50));

                    //create content panel
                    JPanel ansContentPanel = new JPanel(new GridLayout(2, 1));
                    ansContentPanel.add(difeqTitle);
                    ansContentPanel.add(polyAnsScrollPane);

                    JOptionPane.showConfirmDialog(null, ansContentPanel, NonLineaEqsTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? invalidInputs : ex.getMessage(), error, JOptionPane.ERROR_MESSAGE);
                }
            });

            JOptionPane.showOptionDialog(null, showPanel, NonLineaEqsTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon128, buttons, null);
        };
        JPanel newton_RaphsonCard = createCard(title, description, button, enterNewton_Raphson);
        nonLinearEquationsPanel.add(newton_RaphsonCard);

        //***********************************************************************

        //init Halley Card
        title = "<html>" + languageBundle.getString("halleyTitle") + "</html>";
        description = "<html>" + languageBundle.getString("halleyDescription") + "</html>";
        button = enter;
        ActionListener enterHalley = e -> {
            JLabel hallTitleLabel = new JLabel("<html>" + languageBundle.getString("halleyMethod") + "</html>");
            hallTitleLabel.setComponentOrientation(orientation);
            hallTitleLabel.setFont(new Font(mainFont, Font.BOLD, 25));
            JPanel hallTitle = new JPanel();
            hallTitle.add(hallTitleLabel);
            hallTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

            JLabel enterFTitle = new JLabel("<html>" + languageBundle.getString("enterButton") + " f(x) : " + "</html>");
            enterFTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterFField = new JTextField();
            enterFField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterFField.setPreferredSize(new Dimension(200, 50));

            JLabel enterDFTitle = new JLabel("<html>" + languageBundle.getString("enterButton") + " f '(x) : " + "</html>");
            enterDFTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterDFField = new JTextField();
            enterDFField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterDFField.setPreferredSize(new Dimension(200, 50));

            JLabel enterD2FTitle = new JLabel("<html>" + languageBundle.getString("enterButton") + " f ''(x) : " + "</html>");
            enterD2FTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterD2FField = new JTextField();
            enterD2FField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterD2FField.setPreferredSize(new Dimension(200, 50));

            JLabel enterX0Title = new JLabel("<html>" + languageBundle.getString("enterButton") + " X0 : " + "</html>");
            enterX0Title.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterX0Field = new JTextField();
            enterX0Field.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterX0Field.setPreferredSize(new Dimension(200, 50));


            JLabel enterETitle = new JLabel("<html>" + languageBundle.getString("enterButton") + " e : " + "</html>");
            enterETitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterEField = new JTextField();
            enterEField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterEField.setPreferredSize(new Dimension(200, 50));


            JPanel contentPanel;
            if (local.getLanguage().equals("en")) {
                contentPanel = new JPanel(new GridLayout(5, 2, -100, 0));
                contentPanel.add(enterFTitle);
                contentPanel.add(enterFField);
                contentPanel.add(enterDFTitle);
                contentPanel.add(enterDFField);
                contentPanel.add(enterD2FTitle);
                contentPanel.add(enterD2FField);
                contentPanel.add(enterX0Title);
                contentPanel.add(enterX0Field);
                contentPanel.add(enterETitle);
                contentPanel.add(enterEField);
            } else {
                contentPanel = new JPanel(new GridLayout(5, 2, 20, 0));
                contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, -100));
                contentPanel.add(enterFField);
                contentPanel.add(enterFTitle);
                contentPanel.add(enterDFField);
                contentPanel.add(enterDFTitle);
                contentPanel.add(enterD2FField);
                contentPanel.add(enterD2FTitle);
                contentPanel.add(enterX0Field);
                contentPanel.add(enterX0Title);
                contentPanel.add(enterEField);
                contentPanel.add(enterETitle);
            }

            JPanel showPanel = new JPanel(new BorderLayout());
            showPanel.add(hallTitle, BorderLayout.NORTH);
            showPanel.add(contentPanel, BorderLayout.CENTER);

            JButton solveButton = new JButton(Solve);
            solveButton.setFocusPainted(false);
            solveButton.setPreferredSize(new Dimension(80, 40));
            solveButton.setFont(new Font("Arial", Font.PLAIN, 17));
            Color customGreen = new Color(34, 139, 34);  // RGB values for green color
            solveButton.setBackground(customGreen);
            solveButton.setForeground(Color.white);  // Set the text color to white for better visibility
            solveButton.setEnabled(false);

            JButton cancelButton = new JButton(cancel);
            cancelButton.setFocusPainted(false);
            cancelButton.setPreferredSize(new Dimension(100, 40));
            cancelButton.setFont(new Font("Arial", Font.PLAIN, 17));

            Object[] buttons = {cancelButton, solveButton};

            ArrayList<JTextField> fields = new ArrayList<>();
            fields.add(enterFField);
            fields.add(enterDFField);
            fields.add(enterD2FField);
            fields.add(enterX0Field);
            fields.add(enterEField);
            addDocumentListenerToFields(solveButton, fields);

            cancelButton.addActionListener(cancel -> {
                Window optionDialog = SwingUtilities.getWindowAncestor(contentPanel);
                optionDialog.dispose();
            });

            solveButton.addActionListener(solve -> {
                Window optionDialog = SwingUtilities.getWindowAncestor(contentPanel);
                optionDialog.dispose();
                try {
                    String fx = enterFField.getText();
                    String dfx = enterDFField.getText();
                    String d2fx = enterD2FField.getText();
                    BigDecimal x0 = EvaluateString.evaluate(enterX0Field.getText());
                    BigDecimal err = EvaluateString.evaluate(enterEField.getText());
                    ExpressionFunction func = new ExpressionFunction(fx);
                    ExpressionFunction dfunc = new ExpressionFunction(dfx);
                    ExpressionFunction d2func = new ExpressionFunction(d2fx);
                    BigDecimal ans = NonLinearEquation.Halley.solve(func, dfunc, d2func, x0, err);

                    //create title
                    JLabel difeqTitle = new JLabel();
                    difeqTitle.setText("<html>" + languageBundle.getString("halleyDescription") + " : " + "</html>");
                    difeqTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    //create ans scrolled
                    JTextArea difeqAns = new JTextArea();
                    difeqAns.append("Answer = ");
                    difeqAns.append(String.valueOf(fixAccuracy(ans)));
                    difeqAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    difeqAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(difeqAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(100, 50));

                    //create content panel
                    JPanel ansContentPanel = new JPanel(new GridLayout(2, 1));
                    ansContentPanel.add(difeqTitle);
                    ansContentPanel.add(polyAnsScrollPane);

                    JOptionPane.showConfirmDialog(null, ansContentPanel, NonLineaEqsTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? invalidInputs : ex.getMessage(), error, JOptionPane.ERROR_MESSAGE);
                }
            });

            JOptionPane.showOptionDialog(null, showPanel, NonLineaEqsTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon128, buttons, null);
        };
        JPanel halleyCard = createCard(title, description, button, enterHalley);
        nonLinearEquationsPanel.add(halleyCard);

        //***********************************************************************

        //init Fixed Point Iteration Card
        title = "<html>" + languageBundle.getString("fixedPointIterationTitle") + "</html>";
        description = "<html>" + languageBundle.getString("fixedPointIterationDescription") + "</html>";
        button = enter;
        ActionListener enterFixedPointIteration = e -> {
            JLabel fpiTitleLabel = new JLabel("<html>" + languageBundle.getString("fixedPointIterationMethod") + "</html>");
            fpiTitleLabel.setComponentOrientation(orientation);
            fpiTitleLabel.setFont(new Font(mainFont, Font.BOLD, 25));
            JPanel fpiTitle = new JPanel();
            fpiTitle.add(fpiTitleLabel);
            fpiTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

            JLabel enterGTitle = new JLabel("<html>" + languageBundle.getString("enterButton") + " g(x) : " + "</html>");
            enterGTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterGField = new JTextField();
            enterGField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterGField.setPreferredSize(new Dimension(200, 50));


            JLabel enterX0Title = new JLabel("<html>" + languageBundle.getString("enterButton") + " X0 : " + "</html>");
            enterX0Title.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterX0Field = new JTextField();
            enterX0Field.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterX0Field.setPreferredSize(new Dimension(200, 50));


            JLabel enterETitle = new JLabel("<html>" + languageBundle.getString("enterButton") + " e : " + "</html>");
            enterETitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterEField = new JTextField();
            enterEField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterEField.setPreferredSize(new Dimension(200, 50));


            JPanel contentPanel;
            if (local.getLanguage().equals("en")) {
                contentPanel = new JPanel(new GridLayout(3, 2, -100, 0));
                contentPanel.add(enterGTitle);
                contentPanel.add(enterGField);
                contentPanel.add(enterX0Title);
                contentPanel.add(enterX0Field);
                contentPanel.add(enterETitle);
                contentPanel.add(enterEField);
            } else {
                contentPanel = new JPanel(new GridLayout(3, 2, 20, 0));
                contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, -100));
                contentPanel.add(enterGField);
                contentPanel.add(enterGTitle);
                contentPanel.add(enterX0Field);
                contentPanel.add(enterX0Title);
                contentPanel.add(enterEField);
                contentPanel.add(enterETitle);
            }

            JPanel showPanel = new JPanel(new BorderLayout());
            showPanel.add(fpiTitle, BorderLayout.NORTH);
            showPanel.add(contentPanel, BorderLayout.CENTER);

            JButton solveButton = new JButton(Solve);
            solveButton.setFocusPainted(false);
            solveButton.setPreferredSize(new Dimension(80, 40));
            solveButton.setFont(new Font("Arial", Font.PLAIN, 17));
            Color customGreen = new Color(34, 139, 34);  // RGB values for green color
            solveButton.setBackground(customGreen);
            solveButton.setForeground(Color.white);  // Set the text color to white for better visibility
            solveButton.setEnabled(false);

            JButton cancelButton = new JButton(cancel);
            cancelButton.setFocusPainted(false);
            cancelButton.setPreferredSize(new Dimension(100, 40));
            cancelButton.setFont(new Font("Arial", Font.PLAIN, 17));

            Object[] buttons = {cancelButton, solveButton};

            ArrayList<JTextField> fields = new ArrayList<>();
            fields.add(enterGField);
            fields.add(enterX0Field);
            fields.add(enterEField);
            addDocumentListenerToFields(solveButton, fields);

            cancelButton.addActionListener(cancel -> {
                Window optionDialog = SwingUtilities.getWindowAncestor(contentPanel);
                optionDialog.dispose();
            });

            solveButton.addActionListener(solve -> {
                Window optionDialog = SwingUtilities.getWindowAncestor(contentPanel);
                optionDialog.dispose();
                try {
                    String gx = enterGField.getText();
                    BigDecimal x0 = EvaluateString.evaluate(enterX0Field.getText());
                    BigDecimal err = EvaluateString.evaluate(enterEField.getText());
                    ExpressionFunction func = new ExpressionFunction(gx);
                    BigDecimal ans = NonLinearEquation.FixedPointIteration.solve(func, x0, err);

                    //create title
                    JLabel difeqTitle = new JLabel();
                    difeqTitle.setText("<html>" + languageBundle.getString("fixedPointIterationDescription") + " : " + "</html>");
                    difeqTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    //create ans scrolled
                    JTextArea difeqAns = new JTextArea();
                    difeqAns.append("Answer = ");
                    difeqAns.append(String.valueOf(fixAccuracy(ans)));
                    difeqAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    difeqAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(difeqAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(100, 50));

                    //create content panel
                    JPanel ansContentPanel = new JPanel(new GridLayout(2, 1));
                    ansContentPanel.add(difeqTitle);
                    ansContentPanel.add(polyAnsScrollPane);

                    JOptionPane.showConfirmDialog(null, ansContentPanel, NonLineaEqsTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? invalidInputs : ex.getMessage(), error, JOptionPane.ERROR_MESSAGE);
                }
            });

            JOptionPane.showOptionDialog(null, showPanel, NonLineaEqsTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon128, buttons, null);
        };
        JPanel fixedPointIterationCard = createCard(title, description, button, enterFixedPointIteration);
        nonLinearEquationsPanel.add(fixedPointIterationCard);

        //***********************************************************************
    }

    /**
     * Initializes the Polynomials panel.
     * Sets the name, size, and background color of the panel.
     * Creates cards for Polynomials methods with corresponding action listeners.
     * Adds the cards to the panel.
     */
    private void initPolyPanel() {
        polynomialsPanel = new JPanel();
        polynomialsPanel.setName("Polynomial");
        polynomialsPanel.setPreferredSize(mainFrame.getSize());
        polynomialsPanel.setBackground(new Color(100, 100, 100));
        GridLayout startLayout = new GridLayout(3, 1);
        startLayout.setHgap(5);
        startLayout.setVgap(5);
        polynomialsPanel.setLayout(startLayout);
        polynomialsPanel.setComponentOrientation(orientation);
        String polynomialTitle = "<html>" + languageBundle.getString("polynomialsTitle") + "</html>";

        //***********************************************************************

        //init Value At x Card
        String title = "<html>" + languageBundle.getString("valueAtTitle") + "</html>";
        String description = "<html>" + languageBundle.getString("valueAtDescription") + "</html>";
        String button = enter;
        ActionListener enterValueAt = e -> {
            JLabel enterPolyLabel = new JLabel("<html>" + languageBundle.getString("enterPolys") + "</html>");
            enterPolyLabel.setComponentOrientation(orientation);
            enterPolyLabel.setFont(new Font(mainFont, Font.BOLD, 25));
            JPanel enterPoly = new JPanel();
            enterPoly.add(enterPolyLabel);


            // inputs : degree
            JLabel enterDegreeTitle = new JLabel("<html>" + languageBundle.getString("enterDegreePoly") + " : " + "</html>");
            enterDegreeTitle.setFont(new Font(secondFont, Font.PLAIN, 20));
            enterDegreeTitle.setHorizontalAlignment(SwingConstants.LEFT);

            SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, 1000, 1);
            JSpinner enterDegreeSp = new JSpinner(spinnerModel);
            JSpinner.NumberEditor editor = (JSpinner.NumberEditor) enterDegreeSp.getEditor();
            editor.getTextField().setColumns(3); // Adjust the width as needed

            JButton confirmButton = new JButton(Confirm);
            confirmButton.setPreferredSize(new Dimension(80, 30));

            JPanel enterDegree = new JPanel();
            if (local.getLanguage().equals("en")) {
                enterDegree.setLayout(new FlowLayout(FlowLayout.LEFT));
                enterDegree.add(enterDegreeTitle);
                enterDegree.add(enterDegreeSp);
                enterDegree.add(confirmButton);
            } else {
                enterDegree.setLayout(new FlowLayout(FlowLayout.RIGHT));
                enterDegree.add(confirmButton);
                enterDegree.add(enterDegreeSp);
                enterDegree.add(enterDegreeTitle);
            }


            // input coeffs
            JLabel enterCoeffsTitle = new JLabel("<html>" + languageBundle.getString("enterCoeffs") + " : " + "</html>");
            enterCoeffsTitle.setComponentOrientation(orientation);
            enterCoeffsTitle.setFont(new Font(secondFont, Font.PLAIN, 20));
            enterCoeffsTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0)); // Adjust the top and bottom padding

            JScrollPane enterCoeffsScroll = new JScrollPane();
            enterCoeffsScroll.setPreferredSize(new Dimension(220, 150));

            JPanel inputsPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            inputsPanel.setComponentOrientation(orientation);
            gbc.gridx = 0;
            gbc.gridy = 0;
            if (local.getLanguage().equals("en"))
                gbc.anchor = GridBagConstraints.WEST;
            else
                gbc.anchor = GridBagConstraints.EAST;
            gbc.insets = new Insets(5, 0, 5, 0); // Add vertical spacing between components

            inputsPanel.add(enterPoly, gbc);

            gbc.gridy++;
            inputsPanel.add(enterDegree, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            gbc.insets = new Insets(0, 5, 10, 0);
            inputsPanel.add(enterCoeffsTitle, gbc);

            gbc.gridy++;
            inputsPanel.add(enterCoeffsScroll, gbc);

            JButton continueButton = new JButton(Continue);
            continueButton.setFocusPainted(false);
            continueButton.setPreferredSize(new Dimension(100, 40));
            continueButton.setFont(new Font("Arial", Font.PLAIN, 17));
            Color customGreen = new Color(34, 139, 34);  // RGB values for green color
            continueButton.setBackground(customGreen);
            continueButton.setForeground(Color.white);  // Set the text color to white for better visibility
            continueButton.setEnabled(false);

            ArrayList<JTextField> coeffsFields = new ArrayList<>();
            confirmButton.addActionListener(confirm -> {
                // Handle confirm button action
                int n = (int) enterDegreeSp.getValue();
                JPanel coeffsPanel = new JPanel(new GridBagLayout());
                GridBagConstraints gbc2 = new GridBagConstraints();
                gbc2.gridx = 0;
                gbc2.gridy = 0;
                gbc2.anchor = GridBagConstraints.WEST;
                gbc2.insets = new Insets(5, 0, 5, 10); // Add spacing between components

                coeffsFields.clear();

                for (int i = 0; i <= n; i++) {
                    JLabel coeffLabel = new JLabel("a" + i + " = ");
                    JTextField textFieldCoeff = new JTextField(10);

                    textFieldCoeff.setName("a" + i);

                    coeffsFields.add(textFieldCoeff);

                    JPanel componentPanel = new JPanel(new GridBagLayout());
                    GridBagConstraints gbcComponent = new GridBagConstraints();
                    gbcComponent.gridx = 0;
                    gbcComponent.gridy = 0;
                    gbcComponent.anchor = GridBagConstraints.WEST;
                    gbcComponent.insets = new Insets(5, 0, 5, 10); // Add spacing between components

                    componentPanel.add(coeffLabel, gbcComponent);

                    gbcComponent.gridx++;
                    componentPanel.add(textFieldCoeff, gbcComponent);

                    gbc2.gridy++;
                    coeffsPanel.add(componentPanel, gbc2);
                }

                enterCoeffsScroll.setViewportView(coeffsPanel); // Set the pointsPanel as the viewport's view component
                enterCoeffsScroll.revalidate(); // Revalidate the scroll pane to update its content

                // Add a DocumentListener to each text field;
                ArrayList<JTextField> fields = new ArrayList<>(coeffsFields);
                addDocumentListenerToFields(continueButton, fields);
            });

            JButton cancelButton = new JButton(cancel);
            cancelButton.setFocusPainted(false);
            cancelButton.setPreferredSize(new Dimension(100, 40));
            cancelButton.setFont(new Font("Arial", Font.PLAIN, 17));

            Object[] buttons = {cancelButton, continueButton};

            cancelButton.addActionListener(cancel -> {
                try {
                    Window optionDialog = SwingUtilities.getWindowAncestor(inputsPanel);
                    optionDialog.dispose();
                } catch (Exception ignored) {
                }
            });
            final boolean[] confirmPressed = {false};
            continueButton.addActionListener(solve -> {
                Window optionDialog = SwingUtilities.getWindowAncestor(inputsPanel);
                optionDialog.dispose();
                try {
                    confirmPressed[0] = true;
                    ArrayList<BigDecimal> coeffs = new ArrayList<>();
                    for (JTextField field : coeffsFields) {
                        BigDecimal coeff = EvaluateString.evaluate(field.getText());
                        coeffs.add(coeff);
                    }
                    polynomial = new Polynomial(coeffs);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? invalidInputs : ex.getMessage(), error, JOptionPane.ERROR_MESSAGE);
                }
            });
            if (polynomial == null) {
                JOptionPane.showOptionDialog(null, inputsPanel, polynomialTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon128, buttons, null);
                if (!confirmPressed[0])
                    return;

            }

            JLabel horTitleLabel = new JLabel("<html>" + languageBundle.getString("hornerMethod") + "</html>");
            horTitleLabel.setComponentOrientation(orientation);
            horTitleLabel.setFont(new Font(mainFont, Font.BOLD, 25));
            JPanel hornerTitle = new JPanel();
            hornerTitle.add(horTitleLabel);

            //create title
            JLabel polyTitle = new JLabel();
            polyTitle.setText("<html>" + languageBundle.getString("yourPoly") + " : " + "</html>");
            polyTitle.setComponentOrientation(orientation);
            polyTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

            //create ans scrolled
            JTextArea polyAns = new JTextArea();
            polyAns.append("P(x) : ");
            if (polynomial != null) polyAns.append(polynomial.toString());
            polyAns.setFont(new Font(mainFont, Font.PLAIN, 20));
            polyAns.setEditable(false);
            JScrollPane polyAnsScrollPane = new JScrollPane(polyAns);
            polyAnsScrollPane.setPreferredSize(new Dimension(250, 65));

            // inter x label
            JLabel enterXLabel = new JLabel("<html>" + languageBundle.getString("enterButton") + " x : " + "</html>");
            enterXLabel.setFont(new Font(mainFont, Font.PLAIN, 20));

            // inter x field
            JTextField enterXField = new JTextField();
            enterXField.setColumns(10);

            //create content panel
            JPanel contentPanel = new JPanel(new GridBagLayout());
            contentPanel.setComponentOrientation(orientation);
            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            if (local.getLanguage().equals("en"))
                gbc.anchor = GridBagConstraints.WEST;
            else
                gbc.anchor = GridBagConstraints.EAST;
            gbc.insets = new Insets(10, 0, 10, 0);

            contentPanel.add(hornerTitle, gbc);
            gbc.gridy++;
            contentPanel.add(polyTitle, gbc);
            gbc.gridy++;
            contentPanel.add(polyAnsScrollPane, gbc);
            gbc.gridy++;
            contentPanel.add(enterXLabel, gbc);
            if (local.getLanguage().equals("en")) {
                gbc.insets = new Insets(10, -150, 10, 0);
                gbc.gridx++;
            } else {
                gbc.anchor = GridBagConstraints.WEST;
                gbc.insets = new Insets(10, 50, 10, 0);
            }
            contentPanel.add(enterXField, gbc);

            JButton solveButton = new JButton(Solve);
            solveButton.setFocusPainted(false);
            solveButton.setPreferredSize(new Dimension(100, 40));
            solveButton.setFont(new Font("Arial", Font.PLAIN, 17));
            solveButton.setBackground(customGreen);
            solveButton.setForeground(Color.white);  // Set the text color to white for better visibility
            solveButton.setEnabled(false);

            ArrayList<JTextField> fields = new ArrayList<>();
            fields.add(enterXField);
            addDocumentListenerToFields(solveButton, fields);

            cancelButton.addActionListener(cancel -> {
                try {
                    Window optionDialog = SwingUtilities.getWindowAncestor(contentPanel);
                    optionDialog.dispose();
                } catch (Exception ignored) {
                }
            });

            solveButton.addActionListener(solve -> {
                Window optionDialog = SwingUtilities.getWindowAncestor(contentPanel);
                optionDialog.dispose();
                try {
                    BigDecimal x = EvaluateString.evaluate(enterXField.getText());
                    BigDecimal ans = Polynomial.Horner.getValueAt(polynomial, x);

                    //create title
                    JLabel solTitle = new JLabel();
                    solTitle.setText("<html>" + languageBundle.getString("valueAtAnswer") + " : " + "</html>");
                    solTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    //create ans scrolled
                    JTextArea solAns = new JTextArea();
                    solAns.append("Answer = ");
                    solAns.append(String.valueOf(fixAccuracy(ans)));
                    solAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    solAns.setEditable(false);
                    JScrollPane solAnsScrollPane = new JScrollPane(solAns);
                    solAnsScrollPane.setPreferredSize(new Dimension(100, 50));

                    //create content panel
                    JPanel solContentPanel = new JPanel(new GridLayout(2, 1, 10, 10));
                    solContentPanel.add(solTitle);
                    solContentPanel.add(solAnsScrollPane);

                    String[] response = {OK};

                    JOptionPane.showOptionDialog(null, solContentPanel, polynomialTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon, response, response[0]);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? invalidInputs : ex.getMessage(), error, JOptionPane.ERROR_MESSAGE);
                }
            });

            Object[] solvedButtons = {cancelButton, solveButton};

            JOptionPane.showOptionDialog(null, contentPanel, polynomialTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon128, solvedButtons, null);
        };
        JPanel valueAtCard = createCard(title, description, button, enterValueAt);
        polynomialsPanel.add(valueAtCard);

        //***********************************************************************

        //init Divide on (x - a) Card
        title = "<html>" + languageBundle.getString("divideOnTitle") + "</html>";
        description = "<html>" + languageBundle.getString("divideOnDescription") + "</html>";
        button = enter;
        ActionListener enterDivideOn = e -> {
            JLabel enterPolyLabel = new JLabel("<html>" + languageBundle.getString("enterPolys") + "</html>");
            enterPolyLabel.setComponentOrientation(orientation);
            enterPolyLabel.setFont(new Font(mainFont, Font.BOLD, 25));
            JPanel enterPoly = new JPanel();
            enterPoly.add(enterPolyLabel);


            // inputs : degree
            JLabel enterDegreeTitle = new JLabel("<html>" + languageBundle.getString("enterDegreePoly") + " : " + "</html>");
            enterDegreeTitle.setComponentOrientation(orientation);
            enterDegreeTitle.setFont(new Font(secondFont, Font.PLAIN, 20));
            enterDegreeTitle.setHorizontalAlignment(SwingConstants.LEFT);

            SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, 1000, 1);
            JSpinner enterDegreeSp = new JSpinner(spinnerModel);
            JSpinner.NumberEditor editor = (JSpinner.NumberEditor) enterDegreeSp.getEditor();
            editor.getTextField().setColumns(3); // Adjust the width as needed

            JButton confirmButton = new JButton(Confirm);
            confirmButton.setPreferredSize(new Dimension(80, 30));

            JPanel enterDegree = new JPanel();
            if (local.getLanguage().equals("en")) {
                enterDegree.setLayout(new FlowLayout(FlowLayout.LEFT));
                enterDegree.add(enterDegreeTitle);
                enterDegree.add(enterDegreeSp);
                enterDegree.add(confirmButton);
            } else {
                enterDegree.setLayout(new FlowLayout(FlowLayout.RIGHT));
                enterDegree.add(confirmButton);
                enterDegree.add(enterDegreeSp);
                enterDegree.add(enterDegreeTitle);
            }


            // input coeffs
            JLabel enterCoeffsTitle = new JLabel("<html>" + languageBundle.getString("enterCoeffs") + " : " + "</html>");
            enterCoeffsTitle.setComponentOrientation(orientation);
            enterCoeffsTitle.setFont(new Font(secondFont, Font.PLAIN, 20));
            enterCoeffsTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0)); // Adjust the top and bottom padding

            JScrollPane enterCoeffsScroll = new JScrollPane();
            enterCoeffsScroll.setPreferredSize(new Dimension(220, 150));

            JPanel inputsPanel = new JPanel(new GridBagLayout());
            inputsPanel.setComponentOrientation(orientation);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            if (local.getLanguage().equals("en"))
                gbc.anchor = GridBagConstraints.WEST;
            else
                gbc.anchor = GridBagConstraints.EAST;
            gbc.insets = new Insets(5, 0, 5, 0); // Add vertical spacing between components

            inputsPanel.add(enterPoly, gbc);

            gbc.gridy++;
            inputsPanel.add(enterDegree, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            gbc.insets = new Insets(0, 5, 10, 0);
            inputsPanel.add(enterCoeffsTitle, gbc);

            gbc.gridy++;
            inputsPanel.add(enterCoeffsScroll, gbc);

            JButton continueButton = new JButton(Continue);
            continueButton.setFocusPainted(false);
            continueButton.setPreferredSize(new Dimension(100, 40));
            continueButton.setFont(new Font("Arial", Font.PLAIN, 17));
            Color customGreen = new Color(34, 139, 34);  // RGB values for green color
            continueButton.setBackground(customGreen);
            continueButton.setForeground(Color.white);  // Set the text color to white for better visibility
            continueButton.setEnabled(false);

            ArrayList<JTextField> coeffsFields = new ArrayList<>();
            confirmButton.addActionListener(confirm -> {
                // Handle confirm button action
                int n = (int) enterDegreeSp.getValue();
                JPanel coeffsPanel = new JPanel(new GridBagLayout());
                GridBagConstraints gbc2 = new GridBagConstraints();
                gbc2.gridx = 0;
                gbc2.gridy = 0;
                gbc2.anchor = GridBagConstraints.WEST;
                gbc2.insets = new Insets(5, 0, 5, 10); // Add spacing between components

                coeffsFields.clear();

                for (int i = 0; i <= n; i++) {
                    JLabel coeffLabel = new JLabel("a" + i + " = ");
                    JTextField textFieldCoeff = new JTextField(10);

                    textFieldCoeff.setName("a" + i);

                    coeffsFields.add(textFieldCoeff);

                    JPanel componentPanel = new JPanel(new GridBagLayout());
                    GridBagConstraints gbcComponent = new GridBagConstraints();
                    gbcComponent.gridx = 0;
                    gbcComponent.gridy = 0;
                    gbcComponent.anchor = GridBagConstraints.WEST;
                    gbcComponent.insets = new Insets(5, 0, 5, 10); // Add spacing between components

                    componentPanel.add(coeffLabel, gbcComponent);

                    gbcComponent.gridx++;
                    componentPanel.add(textFieldCoeff, gbcComponent);

                    gbc2.gridy++;
                    coeffsPanel.add(componentPanel, gbc2);
                }

                enterCoeffsScroll.setViewportView(coeffsPanel); // Set the pointsPanel as the viewport's view component
                enterCoeffsScroll.revalidate(); // Revalidate the scroll pane to update its content

                // Add a DocumentListener to each text field;
                ArrayList<JTextField> fields = new ArrayList<>(coeffsFields);
                addDocumentListenerToFields(continueButton, fields);
            });

            JButton cancelButton = new JButton(cancel);
            cancelButton.setFocusPainted(false);
            cancelButton.setPreferredSize(new Dimension(100, 40));
            cancelButton.setFont(new Font("Arial", Font.PLAIN, 17));

            Object[] buttons = {cancelButton, continueButton};

            cancelButton.addActionListener(cancel -> {
                try {
                    Window optionDialog = SwingUtilities.getWindowAncestor(inputsPanel);
                    optionDialog.dispose();
                } catch (Exception ignored) {
                }
            });
            final boolean[] confirmPressed = {false};
            continueButton.addActionListener(solve -> {
                Window optionDialog = SwingUtilities.getWindowAncestor(inputsPanel);
                optionDialog.dispose();
                try {
                    confirmPressed[0] = true;
                    ArrayList<BigDecimal> coeffs = new ArrayList<>();
                    for (JTextField field : coeffsFields) {
                        BigDecimal coeff = EvaluateString.evaluate(field.getText());
                        coeffs.add(coeff);
                    }
                    polynomial = new Polynomial(coeffs);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? invalidInputs : ex.getMessage(), error, JOptionPane.ERROR_MESSAGE);
                }
            });
            if (polynomial == null) {
                JOptionPane.showOptionDialog(null, inputsPanel, polynomialTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon128, buttons, null);
                if (!confirmPressed[0]) return;
            }

            JLabel horTitleLabel = new JLabel("<html>" + languageBundle.getString("hornerMethod") + "</html>");
            horTitleLabel.setComponentOrientation(orientation);
            horTitleLabel.setFont(new Font(mainFont, Font.BOLD, 25));
            JPanel hornerTitle = new JPanel();
            hornerTitle.add(horTitleLabel);

            //create title
            JLabel polyTitle = new JLabel();
            polyTitle.setText("<html>" + languageBundle.getString("yourPoly") + " : " + "</html>");
            polyTitle.setComponentOrientation(orientation);
            polyTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

            //create ans scrolled
            JTextArea polyAns = new JTextArea();
            polyAns.append("P(x) : ");
            if (polynomial != null) polyAns.append(polynomial.toString());
            polyAns.setFont(new Font(mainFont, Font.PLAIN, 20));
            polyAns.setEditable(false);
            JScrollPane polyAnsScrollPane = new JScrollPane(polyAns);
            polyAnsScrollPane.setPreferredSize(new Dimension(250, 65));

            // inter a label
            JLabel enterALabel = new JLabel("<html>" + languageBundle.getString("enterButton") + " a : " + "</html>");
            enterALabel.setComponentOrientation(orientation);
            enterALabel.setFont(new Font(mainFont, Font.PLAIN, 20));

            // inter a field
            JTextField enterAField = new JTextField();
            enterAField.setColumns(10);

            //create content panel
            JPanel contentPanel = new JPanel(new GridBagLayout());
            contentPanel.setComponentOrientation(orientation);
            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            if (local.getLanguage().equals("en"))
                gbc.anchor = GridBagConstraints.WEST;
            else
                gbc.anchor = GridBagConstraints.EAST;
            gbc.insets = new Insets(10, 0, 10, 0);

            contentPanel.add(hornerTitle, gbc);
            gbc.gridy++;
            contentPanel.add(polyTitle, gbc);
            gbc.gridy++;
            contentPanel.add(polyAnsScrollPane, gbc);
            gbc.gridy++;
            contentPanel.add(enterALabel, gbc);
            if (local.getLanguage().equals("en")) {
                gbc.insets = new Insets(10, -150, 10, 0);
                gbc.gridx++;
            } else {
                gbc.anchor = GridBagConstraints.WEST;
                gbc.insets = new Insets(10, 50, 10, 0);
            }
            contentPanel.add(enterAField, gbc);

            JButton solveButton = new JButton(Solve);
            solveButton.setFocusPainted(false);
            solveButton.setPreferredSize(new Dimension(100, 40));
            solveButton.setFont(new Font("Arial", Font.PLAIN, 17));
            solveButton.setBackground(customGreen);
            solveButton.setForeground(Color.white);  // Set the text color to white for better visibility
            solveButton.setEnabled(false);

            ArrayList<JTextField> fields = new ArrayList<>();
            fields.add(enterAField);
            addDocumentListenerToFields(solveButton, fields);

            cancelButton.addActionListener(cancel -> {
                try {
                    Window optionDialog = SwingUtilities.getWindowAncestor(contentPanel);
                    optionDialog.dispose();
                } catch (Exception ignored) {
                }
            });

            solveButton.addActionListener(solve -> {
                Window optionDialog = SwingUtilities.getWindowAncestor(contentPanel);
                optionDialog.dispose();
                try {
                    BigDecimal a = EvaluateString.evaluate(enterAField.getText());
                    Polynomial ans = Polynomial.Horner.getDivideOn(polynomial, a);

                    //create title
                    JLabel solTitle = new JLabel();
                    solTitle.setText(languageBundle.getString("divideOnAnswer"));
                    solTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    //create ans scrolled
                    JTextArea solAns = new JTextArea();
                    solAns.append("P(x) : ");
                    solAns.append(ans.toString());
                    solAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    solAns.setEditable(false);
                    JScrollPane solAnsScrollPane = new JScrollPane(solAns);
                    solAnsScrollPane.setPreferredSize(new Dimension(100, 50));

                    //create content panel
                    JPanel solContentPanel = new JPanel(new GridLayout(2, 1, 20, 20));
                    solContentPanel.add(solTitle);
                    solContentPanel.add(solAnsScrollPane);

                    String[] response = {OK};

                    JOptionPane.showOptionDialog(null, solContentPanel, polynomialTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon, response, response[0]);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? invalidInputs : ex.getMessage(), error, JOptionPane.ERROR_MESSAGE);
                }
            });

            Object[] solvedButtons = {cancelButton, solveButton};

            JOptionPane.showOptionDialog(null, contentPanel, polynomialTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon128, solvedButtons, null);
        };
        JPanel divideOnCard = createCard(title, description, button, enterDivideOn);
        polynomialsPanel.add(divideOnCard);

        //***********************************************************************

        //init Diff at x Card
        title = "<html>" + languageBundle.getString("diffAtTitle") + "</html>";
        description = "<html>" + languageBundle.getString("diffAtDescription") + "</html>";
        button = enter;
        ActionListener enterDiffAt = e -> {
            JLabel enterPolyLabel = new JLabel("<html>" + languageBundle.getString("enterPolys") + "</html>");
            enterPolyLabel.setComponentOrientation(orientation);
            enterPolyLabel.setFont(new Font(mainFont, Font.BOLD, 25));
            JPanel enterPoly = new JPanel();
            enterPoly.add(enterPolyLabel);


            // inputs : degree
            JLabel enterDegreeTitle = new JLabel("<html>" + languageBundle.getString("enterDegreePoly") + " : " + "</html>");
            enterDegreeTitle.setComponentOrientation(orientation);
            enterDegreeTitle.setFont(new Font(secondFont, Font.PLAIN, 20));
            enterDegreeTitle.setHorizontalAlignment(SwingConstants.LEFT);

            SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, 1000, 1);
            JSpinner enterDegreeSp = new JSpinner(spinnerModel);
            JSpinner.NumberEditor editor = (JSpinner.NumberEditor) enterDegreeSp.getEditor();
            editor.getTextField().setColumns(3); // Adjust the width as needed

            JButton confirmButton = new JButton(Confirm);
            confirmButton.setPreferredSize(new Dimension(80, 30));

            JPanel enterDegree = new JPanel();
            if (local.getLanguage().equals("en")) {
                enterDegree.setLayout(new FlowLayout(FlowLayout.LEFT));
                enterDegree.add(enterDegreeTitle);
                enterDegree.add(enterDegreeSp);
                enterDegree.add(confirmButton);
            } else {
                enterDegree.setLayout(new FlowLayout(FlowLayout.RIGHT));
                enterDegree.add(confirmButton);
                enterDegree.add(enterDegreeSp);
                enterDegree.add(enterDegreeTitle);
            }


            // input coeffs
            JLabel enterCoeffsTitle = new JLabel("<html>" + languageBundle.getString("enterCoeffs") + " : " + "</html>");
            enterCoeffsTitle.setComponentOrientation(orientation);
            enterCoeffsTitle.setFont(new Font(secondFont, Font.PLAIN, 20));
            enterCoeffsTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0)); // Adjust the top and bottom padding

            JScrollPane enterCoeffsScroll = new JScrollPane();
            enterCoeffsScroll.setPreferredSize(new Dimension(220, 150));

            JPanel inputsPanel = new JPanel(new GridBagLayout());
            inputsPanel.setComponentOrientation(orientation);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            if (local.getLanguage().equals("en"))
                gbc.anchor = GridBagConstraints.WEST;
            else
                gbc.anchor = GridBagConstraints.EAST;
            gbc.insets = new Insets(5, 0, 5, 0); // Add vertical spacing between components

            inputsPanel.add(enterPoly, gbc);

            gbc.gridy++;
            inputsPanel.add(enterDegree, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            gbc.insets = new Insets(0, 5, 10, 0);
            inputsPanel.add(enterCoeffsTitle, gbc);

            gbc.gridy++;
            inputsPanel.add(enterCoeffsScroll, gbc);

            JButton continueButton = new JButton(Continue);
            continueButton.setFocusPainted(false);
            continueButton.setPreferredSize(new Dimension(100, 40));
            continueButton.setFont(new Font("Arial", Font.PLAIN, 17));
            Color customGreen = new Color(34, 139, 34);  // RGB values for green color
            continueButton.setBackground(customGreen);
            continueButton.setForeground(Color.white);  // Set the text color to white for better visibility
            continueButton.setEnabled(false);

            ArrayList<JTextField> coeffsFields = new ArrayList<>();
            confirmButton.addActionListener(confirm -> {
                // Handle confirm button action
                int n = (int) enterDegreeSp.getValue();
                JPanel coeffsPanel = new JPanel(new GridBagLayout());
                GridBagConstraints gbc2 = new GridBagConstraints();
                gbc2.gridx = 0;
                gbc2.gridy = 0;
                gbc2.anchor = GridBagConstraints.WEST;
                gbc2.insets = new Insets(5, 0, 5, 10); // Add spacing between components

                coeffsFields.clear();

                for (int i = 0; i <= n; i++) {
                    JLabel coeffLabel = new JLabel("a" + i + " = ");
                    JTextField textFieldCoeff = new JTextField(10);

                    textFieldCoeff.setName("a" + i);

                    coeffsFields.add(textFieldCoeff);

                    JPanel componentPanel = new JPanel(new GridBagLayout());
                    GridBagConstraints gbcComponent = new GridBagConstraints();
                    gbcComponent.gridx = 0;
                    gbcComponent.gridy = 0;
                    gbcComponent.anchor = GridBagConstraints.WEST;
                    gbcComponent.insets = new Insets(5, 0, 5, 10); // Add spacing between components

                    componentPanel.add(coeffLabel, gbcComponent);

                    gbcComponent.gridx++;
                    componentPanel.add(textFieldCoeff, gbcComponent);

                    gbc2.gridy++;
                    coeffsPanel.add(componentPanel, gbc2);
                }

                enterCoeffsScroll.setViewportView(coeffsPanel); // Set the pointsPanel as the viewport's view component
                enterCoeffsScroll.revalidate(); // Revalidate the scroll pane to update its content

                // Add a DocumentListener to each text field;
                ArrayList<JTextField> fields = new ArrayList<>(coeffsFields);
                addDocumentListenerToFields(continueButton, fields);
            });

            JButton cancelButton = new JButton(cancel);
            cancelButton.setFocusPainted(false);
            cancelButton.setPreferredSize(new Dimension(100, 40));
            cancelButton.setFont(new Font("Arial", Font.PLAIN, 17));

            Object[] buttons = {cancelButton, continueButton};

            cancelButton.addActionListener(cancel -> {
                try {
                    Window optionDialog = SwingUtilities.getWindowAncestor(inputsPanel);
                    optionDialog.dispose();
                } catch (Exception ignored) {
                }
            });
            final boolean[] confirmPressed = {false};
            continueButton.addActionListener(solve -> {
                Window optionDialog = SwingUtilities.getWindowAncestor(inputsPanel);
                optionDialog.dispose();
                try {
                    confirmPressed[0] = true;
                    ArrayList<BigDecimal> coeffs = new ArrayList<>();
                    for (JTextField field : coeffsFields) {
                        BigDecimal coeff = EvaluateString.evaluate(field.getText());
                        coeffs.add(coeff);
                    }
                    polynomial = new Polynomial(coeffs);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? invalidInputs : ex.getMessage(), error, JOptionPane.ERROR_MESSAGE);
                }
            });
            if (polynomial == null) {
                JOptionPane.showOptionDialog(null, inputsPanel, polynomialTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon128, buttons, null);
                if (!confirmPressed[0]) return;
            }

            JLabel horTitleLabel = new JLabel("<html>" + languageBundle.getString("hornerMethod") + "</html>");
            horTitleLabel.setComponentOrientation(orientation);
            horTitleLabel.setFont(new Font(mainFont, Font.BOLD, 25));
            JPanel hornerTitle = new JPanel();
            hornerTitle.add(horTitleLabel);

            //create title
            JLabel polyTitle = new JLabel();
            polyTitle.setText("<html>" + languageBundle.getString("yourPoly") + " : " + "</html>");
            polyTitle.setComponentOrientation(orientation);
            polyTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

            //create ans scrolled
            JTextArea polyAns = new JTextArea();
            polyAns.append("P(x) : ");
            if (polynomial != null) polyAns.append(polynomial.toString());
            polyAns.setFont(new Font(mainFont, Font.PLAIN, 20));
            polyAns.setEditable(false);
            JScrollPane polyAnsScrollPane = new JScrollPane(polyAns);
            polyAnsScrollPane.setPreferredSize(new Dimension(250, 65));

            // inter x label
            JLabel enterXLabel = new JLabel("<html>" + languageBundle.getString("enterButton") + " x : " + "</html>");
            enterXLabel.setComponentOrientation(orientation);
            enterXLabel.setFont(new Font(mainFont, Font.PLAIN, 20));

            // inter x field
            JTextField enterXField = new JTextField();
            enterXField.setColumns(10);

            // inputs : degree
            JLabel enterDiffDegreeTitle = new JLabel("<html>" + languageBundle.getString("enterRankDiff") + " : " + "</html>");
            enterDiffDegreeTitle.setComponentOrientation(orientation);
            enterDiffDegreeTitle.setFont(new Font(secondFont, Font.PLAIN, 20));
            enterDiffDegreeTitle.setHorizontalAlignment(SwingConstants.LEFT);

            SpinnerNumberModel spinnerModelDiff = new SpinnerNumberModel(0, 0, 100, 1);
            JSpinner enterDiffDegreeSp = new JSpinner(spinnerModelDiff);
            JSpinner.NumberEditor editorDiff = (JSpinner.NumberEditor) enterDiffDegreeSp.getEditor();
            editorDiff.getTextField().setColumns(2); // Adjust the width as needed

            JPanel enterDiffDegree = new JPanel();
            if (local.getLanguage().equals("en")) {
                enterDiffDegree.setLayout(new FlowLayout(FlowLayout.LEFT));
                enterDiffDegree.add(enterDiffDegreeTitle);
                enterDiffDegree.add(enterDiffDegreeSp);
            } else {
                enterDiffDegree.setLayout(new FlowLayout(FlowLayout.RIGHT));
                enterDiffDegree.add(enterDiffDegreeSp);
                enterDiffDegree.add(enterDiffDegreeTitle);
            }
            enterDiffDegree.setBorder(BorderFactory.createEmptyBorder(0, -5, 0, 0));

            //create content panel
            JPanel contentPanel = new JPanel(new GridBagLayout());
            contentPanel.setComponentOrientation(orientation);
            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            if (local.getLanguage().equals("en"))
                gbc.anchor = GridBagConstraints.WEST;
            else
                gbc.anchor = GridBagConstraints.EAST;
            gbc.insets = new Insets(10, 0, 10, 0);

            contentPanel.add(hornerTitle, gbc);
            gbc.gridy++;
            contentPanel.add(polyTitle, gbc);
            gbc.gridy++;
            contentPanel.add(polyAnsScrollPane, gbc);
            gbc.gridy++;
            contentPanel.add(enterXLabel, gbc);
            contentPanel.add(enterXLabel, gbc);
            if (local.getLanguage().equals("en")) {
                gbc.insets = new Insets(10, -350, 10, 0);
                gbc.gridx++;
            } else {
                gbc.anchor = GridBagConstraints.WEST;
                gbc.insets = new Insets(10, 100, 10, 0);
            }
            contentPanel.add(enterXField, gbc);
            if (local.getLanguage().equals("en"))
                gbc.anchor = GridBagConstraints.WEST;
            else
                gbc.anchor = GridBagConstraints.EAST;
            gbc.gridx = 0;
            gbc.gridy++;
            gbc.insets = new Insets(10, 0, 10, 0);
            contentPanel.add(enterDiffDegree, gbc);

            JButton solveButton = new JButton(Solve);
            solveButton.setFocusPainted(false);
            solveButton.setPreferredSize(new Dimension(100, 40));
            solveButton.setFont(new Font("Arial", Font.PLAIN, 17));
            solveButton.setBackground(customGreen);
            solveButton.setForeground(Color.white);  // Set the text color to white for better visibility
            solveButton.setEnabled(false);

            ArrayList<JTextField> fields = new ArrayList<>();
            fields.add(enterXField);
            addDocumentListenerToFields(solveButton, fields);

            cancelButton.addActionListener(cancel -> {
                try {
                    Window optionDialog = SwingUtilities.getWindowAncestor(contentPanel);
                    optionDialog.dispose();
                } catch (Exception ignored) {
                }
            });

            solveButton.addActionListener(solve -> {
                Window optionDialog = SwingUtilities.getWindowAncestor(contentPanel);
                optionDialog.dispose();
                try {
                    BigDecimal x = EvaluateString.evaluate(enterXField.getText());
                    int diffDegree = (int) enterDiffDegreeSp.getValue();
                    BigDecimal ans = Polynomial.Horner.getDiffAt(polynomial, x, diffDegree);

                    //create title
                    JLabel solTitle = new JLabel();
                    solTitle.setText(languageBundle.getString("diffAtAnswer"));
                    solTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    //create ans scrolled
                    JTextArea solAns = new JTextArea();
                    solAns.append("Ans = ");
                    solAns.append(String.valueOf(fixAccuracy(ans)));
                    solAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    solAns.setEditable(false);
                    JScrollPane solAnsScrollPane = new JScrollPane(solAns);
                    solAnsScrollPane.setPreferredSize(new Dimension(100, 50));

                    //create content panel
                    JPanel solContentPanel = new JPanel(new GridLayout(2, 1, 20, 20));
                    solContentPanel.add(solTitle);
                    solContentPanel.add(solAnsScrollPane);

                    String[] response = {OK};

                    JOptionPane.showOptionDialog(null, solContentPanel, polynomialTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon, response, response[0]);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? invalidInputs : ex.getMessage(), error, JOptionPane.ERROR_MESSAGE);
                }
            });

            Object[] solvedButtons = {cancelButton, solveButton};

            JOptionPane.showOptionDialog(null, contentPanel, polynomialTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon128, solvedButtons, null);
        };
        JPanel diffAtCard = createCard(title, description, button, enterDiffAt);
        polynomialsPanel.add(diffAtCard);

        //***********************************************************************

    }

    /**
     * Initializes the Expression Function panel.
     * Sets the name, size, and background color of the panel.
     * Creates input and points cards for generating points from an expression function.
     * Adds the cards to the panel.
     */
    private void initExpFuncPanel() {
        expressionFunctionPanel = new JPanel();
        expressionFunctionPanel.setName("Expression Function");
        expressionFunctionPanel.setPreferredSize(mainFrame.getSize());
        expressionFunctionPanel.setBackground(new Color(100, 100, 100));
        GridLayout startLayout = new GridLayout(1, 2);
        startLayout.setHgap(5);
        startLayout.setVgap(5);
        expressionFunctionPanel.setLayout(startLayout);
        expressionFunctionPanel.setComponentOrientation(orientation);

        JPanel pointsCard = new JPanel();
        JTextArea pointsText;
        JScrollPane pointsScrollPane;
        JButton continueButton;
        {

            //points card title
            JPanel titlePanel = new JPanel();
            JLabel pointsTitle = new JLabel();
            pointsTitle.setText("<html>" + languageBundle.getString("generatedPoints") + "</html>");
            pointsTitle.setFont(new Font(mainFont, Font.BOLD, 25));
            titlePanel.add(pointsTitle, BorderLayout.CENTER);

            //point card area
            pointsText = new JTextArea();
            //pointsText.setPreferredSize(new Dimension(450, 550));
            pointsText.setFont(new Font(mainFont, Font.PLAIN, 20));
            pointsText.setEnabled(false);
            pointsScrollPane = new JScrollPane(pointsText);
            pointsScrollPane.setPreferredSize(new Dimension(450, 492));

            // Continue Button
            continueButton = new JButton(Continue);
            continueButton.setFont(new Font(buttonFont, Font.BOLD, 20));
            continueButton.setFocusPainted(false);
            continueButton.setPreferredSize(new Dimension(60, 50));
            Color customGreen = new Color(34, 139, 34);  // RGB values for green color
            continueButton.setBackground(customGreen);
            continueButton.setForeground(Color.white);  // Set the text color to white for better visibility
            continueButton.setEnabled(false); // Disable the button initially
            continueButton.addActionListener(e -> doAction.accept(function));

            // content panel
            JPanel contentPanel = new JPanel(new BorderLayout());
            contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
            contentPanel.add(titlePanel, BorderLayout.NORTH);
            contentPanel.add(pointsScrollPane, BorderLayout.CENTER);
            contentPanel.add(continueButton, BorderLayout.SOUTH);

            pointsCard.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            pointsCard.add(contentPanel, BorderLayout.CENTER);

        }

        JPanel inputCard = new JPanel();
        {
            //title
            JLabel cardTitle = new JLabel();
            cardTitle.setText("<html>" + languageBundle.getString("expressionFunctionTitle") + "</html>");
            cardTitle.setComponentOrientation(orientation);
            cardTitle.setFont(new Font(mainFont, Font.BOLD, 25));


            // description
            JLabel cardDescription = new JLabel();
            cardDescription.setText(languageBundle.getString("supportedFunction"));
            cardDescription.setComponentOrientation(orientation);
            cardDescription.setFont(new Font(secondFont, Font.PLAIN, 15));
            cardDescription.setBackground(inputCard.getBackground());

            //info panel
            JPanel infoPanel;
            if (local.getLanguage().equals("en"))
                infoPanel = new JPanel(new GridLayout(2, 1, 0, -32));
            else
                infoPanel = new JPanel(new GridLayout(2, 1, 0, -45));
            infoPanel.setBorder(BorderFactory.createEmptyBorder(-15, 0, 0, 0));
            infoPanel.add(cardTitle);
            infoPanel.add(cardDescription);

            // inputs : exp
            JLabel enterExpTitle = new JLabel("<html>" + languageBundle.getString("enterExpFun") + " : " + "</html>");
            enterExpTitle.setComponentOrientation(orientation);

            JTextField enterExpField = new JTextField();
            enterExpField.setPreferredSize(new Dimension(100, 40));

            JPanel enterExp = new JPanel();
            GridLayout inputLayout = new GridLayout(2, 1, 0, -5);
            enterExp.setLayout(inputLayout);
            enterExp.add(enterExpTitle);
            enterExp.add(enterExpField);

            // inputs : a
            JLabel enterATitle = new JLabel("<html>" + languageBundle.getString("enterA") + " : " + "</html>");
            enterATitle.setComponentOrientation(orientation);

            JTextField enterAField = new JTextField();
            //enterAField.setPreferredSize(new Dimension(100, 50));

            JPanel enterA = new JPanel();
            enterA.setLayout(inputLayout);
            enterA.add(enterATitle);
            enterA.add(enterAField);

            // inputs : b
            JLabel enterBTitle = new JLabel("<html>" + languageBundle.getString("enterB") + " : " + "</html>");
            enterBTitle.setComponentOrientation(orientation);

            JTextField enterBField = new JTextField();
            //enterBField.setPreferredSize(new Dimension(100, 50));

            JPanel enterB = new JPanel();
            enterB.setLayout(inputLayout);
            enterB.add(enterBTitle);
            enterB.add(enterBField);

            // inputs : n
            JLabel enterNTitle = new JLabel("<html>" + languageBundle.getString("enterNNumberOfPoints") + " : " + "</html>");
            enterNTitle.setComponentOrientation(orientation);
            enterNTitle.setHorizontalAlignment(SwingConstants.LEFT);
            enterNTitle.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));

            SpinnerNumberModel spinnerModel = new SpinnerNumberModel(2, 2, 1000, 1);
            JSpinner enterNSp = new JSpinner(spinnerModel);
            JSpinner.NumberEditor editor = (JSpinner.NumberEditor) enterNSp.getEditor();
            editor.getTextField().setColumns(3); // Adjust the width as needed

            JPanel enterN = new JPanel();
            enterN.setLayout(new FlowLayout(FlowLayout.LEFT));
            if (local.getLanguage().equals("en")) {
                enterN.setLayout(new FlowLayout(FlowLayout.LEFT));
                enterN.add(enterNTitle, BorderLayout.WEST);
                enterN.add(enterNSp, BorderLayout.CENTER);
            } else {
                enterN.setLayout(new FlowLayout(FlowLayout.RIGHT));
                enterN.add(enterNSp, BorderLayout.WEST);
                enterN.add(enterNTitle, BorderLayout.EAST);
            }

            // inputs panel
            JPanel inputsPanel = new JPanel(new GridLayout(4, 1, -5, 10));
            inputsPanel.add(enterExp);
            inputsPanel.add(enterA);
            inputsPanel.add(enterB);
            inputsPanel.add(enterN);

            // Generate Button
            JButton generateButton = new JButton(Generate);
            generateButton.setFont(new Font(buttonFont, Font.BOLD, 20));
            generateButton.setFocusPainted(false);
            generateButton.setPreferredSize(new Dimension(60, 50));
            generateButton.setEnabled(false); // Disable the button initially
            generateButton.addActionListener(e -> {
                String func = enterExpField.getText();
                ExpressionFunction expFunc = new ExpressionFunction(func);
                String a = enterAField.getText();
                String b = enterBField.getText();
                int n = (int) enterNSp.getValue();
                try {
                    PointsFunction pointsFunc = expFunc.toPointsFunction(a, b, n);
                    ArrayList<BigDecimal> xp = pointsFunc.getXp();
                    ArrayList<BigDecimal> yp = pointsFunc.getYp();
                    pointsText.setText("");
                    for (int i = 0; i < xp.size(); i++) {
                        String sb = "x" + i + " = " + fixAccuracy(xp.get(i)) + '\t' + "y" + i + " = " + fixAccuracy(yp.get(i));
                        pointsText.append(sb);
                        pointsText.append("\n");
                    }
                    pointsText.repaint();
                    continueButton.setEnabled(true);
                    function = pointsFunc;

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? invalidInputs : ex.getMessage(), error, JOptionPane.ERROR_MESSAGE);
                }
            });

            // Add a DocumentListener to each text field
            ArrayList<JTextField> fields = new ArrayList<>();
            fields.add(enterExpField);
            fields.add(enterAField);
            fields.add(enterBField);
            addDocumentListenerToFields(generateButton, fields);

            JPanel contentPanel = new JPanel(new BorderLayout());
            contentPanel.setBorder(BorderFactory.createEmptyBorder(-30, 10, 10, 10));

            contentPanel.add(infoPanel, BorderLayout.NORTH);
            contentPanel.add(inputsPanel, BorderLayout.CENTER);
            contentPanel.add(generateButton, BorderLayout.SOUTH);

            inputCard.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            inputCard.add(contentPanel, BorderLayout.CENTER);

        }
        expressionFunctionPanel.add(inputCard);
        expressionFunctionPanel.add(pointsCard);
    }

    /**
     * Initializes the Points Function panel.
     * Sets the name, size, and background color of the panel.
     * Creates input and points cards for generating points from x and y coordinates.
     * Adds the cards to the panel.
     */
    private void iniPtsFuncPanel() {
        pointsFunctionPanel = new JPanel();
        pointsFunctionPanel.setName("Points Function");
        pointsFunctionPanel.setPreferredSize(mainFrame.getSize());
        pointsFunctionPanel.setBackground(new Color(100, 100, 100));
        GridLayout startLayout = new GridLayout(1, 2);
        startLayout.setHgap(5);
        startLayout.setVgap(5);
        pointsFunctionPanel.setLayout(startLayout);
        pointsFunctionPanel.setComponentOrientation(orientation);

        JPanel pointsCard = new JPanel();
        JTextArea pointsText;
        JScrollPane pointsScrollPane;
        JButton continueButton;
        {

            //points card title
            JPanel titlePanel = new JPanel();
            JLabel pointsTitle = new JLabel();
            pointsTitle.setText("<html>" + languageBundle.getString("generatedPoints") + "</html>");
            pointsTitle.setFont(new Font(mainFont, Font.BOLD, 25));
            titlePanel.add(pointsTitle, BorderLayout.CENTER);

            //point card area
            pointsText = new JTextArea();
            pointsText.setFont(new Font(mainFont, Font.PLAIN, 20));
            pointsText.setEnabled(false);
            pointsScrollPane = new JScrollPane(pointsText);
            pointsScrollPane.setPreferredSize(new Dimension(450, 485));

            // Continue Button
            continueButton = new JButton(Continue);
            continueButton.setFont(new Font(buttonFont, Font.BOLD, 20));
            continueButton.setFocusPainted(false);
            continueButton.setPreferredSize(new Dimension(60, 50));
            Color customGreen = new Color(34, 139, 34);  // RGB values for green color
            continueButton.setBackground(customGreen);
            continueButton.setForeground(Color.white);  // Set the text color to white for better visibility
            continueButton.setEnabled(false); // Disable the button initially
            continueButton.addActionListener(e -> doAction.accept(function));

            // content panel
            JPanel contentPanel = new JPanel(new BorderLayout());
            contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
            contentPanel.add(titlePanel, BorderLayout.NORTH);
            contentPanel.add(pointsScrollPane, BorderLayout.CENTER);
            contentPanel.add(continueButton, BorderLayout.SOUTH);

            pointsCard.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            pointsCard.add(contentPanel, BorderLayout.CENTER);

        }

        JPanel inputCard = new JPanel();
        {
            //title
            JLabel cardTitle = new JLabel();
            cardTitle.setText("<html>" + languageBundle.getString("pointsFunctionTitle") + "</html>");
            cardTitle.setComponentOrientation(orientation);
            cardTitle.setFont(new Font(mainFont, Font.BOLD, 25));


            // description
            JLabel cardDescription = new JLabel();
            cardDescription.setText(languageBundle.getString("supportedPoints"));
            cardDescription.setComponentOrientation(orientation);
            cardDescription.setFont(new Font(secondFont, Font.PLAIN, 15));
            cardDescription.setBackground(inputCard.getBackground());

            //info panel
            JPanel infoPanel = new JPanel(new GridLayout(2, 1, 0, -15));
            infoPanel.setBorder(BorderFactory.createEmptyBorder(-15, 0, 0, 0));
            infoPanel.add(cardTitle);
            infoPanel.add(cardDescription);


            // inputs : n
            JLabel enterNTitle = new JLabel("<html>" + languageBundle.getString("enterNXPoints") + "</html>");
            enterNTitle.setComponentOrientation(orientation);
            enterNTitle.setHorizontalAlignment(SwingConstants.LEFT);
            enterNTitle.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));

            SpinnerNumberModel spinnerModel = new SpinnerNumberModel(2, 2, 1000, 1);
            JSpinner enterNSp = new JSpinner(spinnerModel);
            JSpinner.NumberEditor editor = (JSpinner.NumberEditor) enterNSp.getEditor();
            editor.getTextField().setColumns(3); // Adjust the width as needed

            JButton confirmButton = new JButton(Confirm);
            confirmButton.setPreferredSize(new Dimension(80, 30));
            JPanel enterN = new JPanel();
            if (local.getLanguage().equals("en")) {
                enterN.setLayout(new FlowLayout(FlowLayout.LEFT));
                enterN.add(enterNTitle);
                enterN.add(enterNSp);
                enterN.add(confirmButton);
            } else {
                enterN.setLayout(new FlowLayout(FlowLayout.RIGHT));
                enterN.add(confirmButton);
                enterN.add(enterNSp);
                enterN.add(enterNTitle);
            }


            // input x and y
            JLabel enterPointsTitle = new JLabel("<html>" + languageBundle.getString("enterXY") + "</html>");
            enterPointsTitle.setComponentOrientation(orientation);
            enterPointsTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0)); // Adjust the top and bottom padding

            JScrollPane enterPointsScroll = new JScrollPane();
            enterPointsScroll.setPreferredSize(new Dimension(450, 300));


            // inputs panel
            JPanel inputsPanel = new JPanel(new GridBagLayout());
            inputsPanel.setComponentOrientation(orientation);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            if (local.getLanguage().equals("en"))
                gbc.anchor = GridBagConstraints.WEST;
            else
                gbc.anchor = GridBagConstraints.EAST;
            gbc.insets = new Insets(5, 0, 5, 0); // Add vertical spacing between components

            inputsPanel.add(enterN, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            gbc.gridwidth = 2;
            inputsPanel.add(enterPointsTitle, gbc);

            gbc.gridy++;
            inputsPanel.add(enterPointsScroll, gbc);

            // Generate Button
            JButton generateButton = new JButton(Generate);
            generateButton.setFont(new Font(buttonFont, Font.BOLD, 20));
            generateButton.setFocusPainted(false);
            generateButton.setPreferredSize(new Dimension(60, 50));
            generateButton.setEnabled(false); // Disable the button initially

            // add fields to enter points
            ArrayList<JTextField> xFields = new ArrayList<>();
            ArrayList<JTextField> yFields = new ArrayList<>();
            confirmButton.addActionListener(e -> {
                // Handle confirm button action
                int n = (int) enterNSp.getValue();
                JPanel pointsPanel = new JPanel(new GridBagLayout());
                GridBagConstraints gbc2 = new GridBagConstraints();
                gbc2.gridx = 0;
                gbc2.gridy = 0;
                gbc2.anchor = GridBagConstraints.WEST;
                gbc2.insets = new Insets(5, 0, 5, 10); // Add spacing between components

                xFields.clear();
                yFields.clear();

                for (int i = 0; i < n; i++) {
                    JLabel labelX = new JLabel("x" + i + " = ");
                    JLabel labelY = new JLabel("y" + i + " = ");
                    JTextField textFieldX = new JTextField(10);
                    JTextField textFieldY = new JTextField(10);

                    textFieldX.setName("x" + i);
                    textFieldY.setName("y" + i);

                    xFields.add(textFieldX);
                    yFields.add(textFieldY);

                    JPanel componentPanel = new JPanel(new GridBagLayout());
                    GridBagConstraints gbcComponent = new GridBagConstraints();
                    gbcComponent.gridx = 0;
                    gbcComponent.gridy = 0;
                    gbcComponent.anchor = GridBagConstraints.WEST;
                    gbcComponent.insets = new Insets(5, 0, 5, 10); // Add spacing between components

                    componentPanel.add(labelX, gbcComponent);

                    gbcComponent.gridx++;
                    componentPanel.add(textFieldX, gbcComponent);

                    gbcComponent.gridx++;
                    componentPanel.add(labelY, gbcComponent);

                    gbcComponent.gridx++;
                    componentPanel.add(textFieldY, gbcComponent);

                    gbc2.gridy++;
                    pointsPanel.add(componentPanel, gbc2);
                }

                enterPointsScroll.setViewportView(pointsPanel); // Set the pointsPanel as the viewport's view component
                enterPointsScroll.revalidate(); // Revalidate the scroll pane to update its content

                // Add a DocumentListener to each text field;
                ArrayList<JTextField> fields = new ArrayList<>(xFields);
                fields.addAll(yFields);
                addDocumentListenerToFields(generateButton, fields);
            });

            generateButton.addActionListener(e -> {
                ArrayList<BigDecimal> xp = new ArrayList<>();
                ArrayList<BigDecimal> yp = new ArrayList<>();
                try {
                    for (JTextField xField : xFields) {
                        String xFiledContent = xField.getText();
                        BigDecimal x = EvaluateString.evaluate(xFiledContent);
                        xp.add(x);
                    }
                    for (JTextField yField : yFields) {
                        String yFiledContent = yField.getText();
                        BigDecimal y = EvaluateString.evaluate(yFiledContent);
                        yp.add(y);
                    }
                    PointsFunction pointsFunc = new PointsFunction(xp, yp);
                    pointsText.setText("");
                    for (int i = 0; i < xp.size(); i++) {
                        String sb = "x" + i + " = " + fixAccuracy(xp.get(i)) + '\t' + "y" + i + " = " + fixAccuracy(yp.get(i));
                        pointsText.append(sb);
                        pointsText.append("\n");
                    }
                    pointsText.repaint();
                    continueButton.setEnabled(true);
                    function = pointsFunc;

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? invalidInputs : ex.getMessage(), error, JOptionPane.ERROR_MESSAGE);
                }
            });

            JPanel contentPanel = new JPanel(new BorderLayout());
            contentPanel.setBorder(BorderFactory.createEmptyBorder(-10, 10, 10, 10));

            contentPanel.add(infoPanel, BorderLayout.NORTH);
            contentPanel.add(inputsPanel, BorderLayout.CENTER);
            contentPanel.add(generateButton, BorderLayout.SOUTH);

            inputCard.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            inputCard.add(contentPanel, BorderLayout.CENTER);

        }
        pointsFunctionPanel.add(inputCard);


        pointsFunctionPanel.add(pointsCard);
    }

    /**
     * Initializes the Polynomial Function panel.
     * Sets the name, size, and background color of the panel.
     * Creates input and points cards for generating points from a polynomial function.
     * Adds the cards to the panel.
     */
    private void initPolyFuncPanel() {
        polynomialFunctionPanel = new JPanel();
        polynomialFunctionPanel.setName("Polynomial Function");
        polynomialFunctionPanel.setPreferredSize(mainFrame.getSize());
        polynomialFunctionPanel.setBackground(new Color(100, 100, 100));
        GridLayout startLayout = new GridLayout(1, 2);
        startLayout.setHgap(5);
        startLayout.setVgap(5);
        polynomialFunctionPanel.setLayout(startLayout);
        polynomialFunctionPanel.setComponentOrientation(orientation);

        JPanel pointsCard = new JPanel();
        JTextArea pointsText;
        JScrollPane pointsScrollPane;
        JButton continueButton;
        {

            //points card title
            JPanel titlePanel = new JPanel();
            JLabel pointsTitle = new JLabel();
            pointsTitle.setText("<html>" + languageBundle.getString("generatedPoints") + "</html>");
            pointsTitle.setFont(new Font(mainFont, Font.BOLD, 25));
            titlePanel.add(pointsTitle, BorderLayout.CENTER);

            //point card area
            pointsText = new JTextArea();
            pointsText.setFont(new Font(mainFont, Font.PLAIN, 20));
            pointsText.setEnabled(false);
            pointsScrollPane = new JScrollPane(pointsText);
            pointsScrollPane.setPreferredSize(new Dimension(450, 484));

            // Continue Button
            continueButton = new JButton(Continue);
            continueButton.setFont(new Font(buttonFont, Font.BOLD, 20));
            continueButton.setFocusPainted(false);
            continueButton.setPreferredSize(new Dimension(60, 50));
            Color customGreen = new Color(34, 139, 34);  // RGB values for green color
            continueButton.setBackground(customGreen);
            continueButton.setForeground(Color.white);  // Set the text color to white for better visibility
            continueButton.setEnabled(false); // Disable the button initially
            continueButton.addActionListener(e -> doAction.accept(function));

            // content panel
            JPanel contentPanel = new JPanel(new BorderLayout());
            contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
            contentPanel.add(titlePanel, BorderLayout.NORTH);
            contentPanel.add(pointsScrollPane, BorderLayout.CENTER);
            contentPanel.add(continueButton, BorderLayout.SOUTH);

            pointsCard.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            pointsCard.add(contentPanel, BorderLayout.CENTER);

        }

        JPanel inputCard = new JPanel();
        {
            //title
            JLabel cardTitle = new JLabel();
            cardTitle.setText("<html>" + languageBundle.getString("polynomialFunctionTitle") + "</html>");
            cardTitle.setComponentOrientation(orientation);
            cardTitle.setFont(new Font(mainFont, Font.BOLD, 25));


            // description
            JLabel cardDescription = new JLabel();
            cardDescription.setText("<html>" + languageBundle.getString("supportedCoeffs") + "</html>");
            cardDescription.setComponentOrientation(orientation);
            cardDescription.setFont(new Font(secondFont, Font.PLAIN, 15));
            cardDescription.setBackground(inputCard.getBackground());

            //info panel
            JPanel infoPanel = new JPanel(new GridLayout(2, 1, 0, -25));
            infoPanel.setBorder(BorderFactory.createEmptyBorder(-10, 0, 0, 0));
            infoPanel.add(cardTitle);
            infoPanel.add(cardDescription);

            // inputs : a
            JLabel enterATitle = new JLabel("<html>" + languageBundle.getString("enterAX") + " : " + "</html>");

            JTextField enterAField = new JTextField();
            //enterAField.setPreferredSize(new Dimension(100, 50));

            JPanel enterA = new JPanel();
            GridLayout inputLayout = new GridLayout(2, 1, 0, 0);
            enterA.setLayout(inputLayout);
            enterA.setComponentOrientation(orientation);
            enterA.add(enterATitle);
            enterA.add(enterAField);

            // inputs : b
            JLabel enterBTitle = new JLabel("<html>" + languageBundle.getString("enterBX") + " : " + "</html>");

            JTextField enterBField = new JTextField();
            //enterBField.setPreferredSize(new Dimension(100, 50));

            JPanel enterB = new JPanel();
            enterB.setLayout(inputLayout);
            enterB.setComponentOrientation(orientation);
            enterB.add(enterBTitle);
            enterB.add(enterBField);

            // inputs : n
            JLabel enterNTitle = new JLabel("<html>" + languageBundle.getString("enterNXPoints") + "</html>");
            enterNTitle.setHorizontalAlignment(SwingConstants.LEFT);
            enterNTitle.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));

            SpinnerNumberModel spinnerModel = new SpinnerNumberModel(2, 2, 1000, 1);
            JSpinner enterNSp = new JSpinner(spinnerModel);
            JSpinner.NumberEditor editor = (JSpinner.NumberEditor) enterNSp.getEditor();
            editor.getTextField().setColumns(3); // Adjust the width as needed

            JPanel enterN = new JPanel();
            if (local.getLanguage().equals("en")) {
                enterN.setLayout(new FlowLayout(FlowLayout.LEFT));
                enterN.add(enterNTitle);
                enterN.add(enterNSp);
            } else {
                enterN.setLayout(new FlowLayout(FlowLayout.RIGHT));
                enterN.add(enterNSp);
                enterN.add(enterNTitle);
            }

            // inputs : degree
            JLabel enterDegreeTitle = new JLabel("<html>" + languageBundle.getString("enterDegreePoly") + "</html>");
            enterDegreeTitle.setHorizontalAlignment(SwingConstants.LEFT);
            enterDegreeTitle.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));

            spinnerModel = new SpinnerNumberModel(0, 0, 1000, 1);
            JSpinner enterDegreeSp = new JSpinner(spinnerModel);
            editor = (JSpinner.NumberEditor) enterDegreeSp.getEditor();
            editor.getTextField().setColumns(3); // Adjust the width as needed

            JButton confirmButton = new JButton(Confirm);
            confirmButton.setPreferredSize(new Dimension(80, 30));

            JPanel enterDegree = new JPanel();
            if (local.getLanguage().equals("en")) {
                enterDegree.setLayout(new FlowLayout(FlowLayout.LEFT));
                enterDegree.add(enterDegreeTitle);
                enterDegree.add(enterDegreeSp);
                enterDegree.add(confirmButton);
            } else {
                enterDegree.setLayout(new FlowLayout(FlowLayout.RIGHT));
                enterDegree.add(confirmButton);
                enterDegree.add(enterDegreeSp);
                enterDegree.add(enterDegreeTitle);
            }


            // input coeffs
            JLabel enterCoeffsTitle = new JLabel("<html>" + languageBundle.getString("enterCoeffs") + "</html>");
            enterCoeffsTitle.setComponentOrientation(orientation);
            enterCoeffsTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0)); // Adjust the top and bottom padding

            JScrollPane enterCoeffsScroll = new JScrollPane();
            enterCoeffsScroll.setPreferredSize(new Dimension(220, 150));


            JPanel inputsPanel = new JPanel(new GridBagLayout());
            inputsPanel.setComponentOrientation(orientation);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            if (local.getLanguage().equals("en"))
                gbc.anchor = GridBagConstraints.WEST;
            else
                gbc.anchor = GridBagConstraints.EAST;
            gbc.insets = new Insets(5, 0, 5, 0); // Add vertical spacing between components

            gbc.gridy++;
            inputsPanel.add(enterA, gbc);

            gbc.gridy++;
            inputsPanel.add(enterB, gbc);

            gbc.gridy++;
            inputsPanel.add(enterN, gbc);
            gbc.gridy++;
            inputsPanel.add(enterDegree, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            gbc.gridwidth = 2;
            inputsPanel.add(enterCoeffsTitle, gbc);

            gbc.gridy++;
            inputsPanel.add(enterCoeffsScroll, gbc);

            // Generate Button
            JButton generateButton = new JButton(Generate);
            generateButton.setFont(new Font(buttonFont, Font.BOLD, 20));
            generateButton.setFocusPainted(false);
            generateButton.setPreferredSize(new Dimension(60, 50));
            generateButton.setEnabled(false); // Disable the button initially

            // add fields to enter points
            ArrayList<JTextField> coeffsFields = new ArrayList<>();
            confirmButton.addActionListener(e -> {
                generateButton.setEnabled(false);
                // Handle confirm button action
                int n = (int) enterDegreeSp.getValue();
                JPanel coeffsPanel = new JPanel(new GridBagLayout());
                GridBagConstraints gbc2 = new GridBagConstraints();
                gbc2.gridx = 0;
                gbc2.gridy = 0;
                gbc2.anchor = GridBagConstraints.WEST;
                gbc2.insets = new Insets(5, 0, 5, 10); // Add spacing between components

                coeffsFields.clear();

                for (int i = 0; i <= n; i++) {
                    JLabel coeffLabel = new JLabel("a" + i + " = ");
                    JTextField textFieldCoeff = new JTextField(10);

                    textFieldCoeff.setName("a" + i);

                    coeffsFields.add(textFieldCoeff);

                    JPanel componentPanel = new JPanel(new GridBagLayout());
                    GridBagConstraints gbcComponent = new GridBagConstraints();
                    gbcComponent.gridx = 0;
                    gbcComponent.gridy = 0;
                    gbcComponent.anchor = GridBagConstraints.WEST;
                    gbcComponent.insets = new Insets(5, 0, 5, 10); // Add spacing between components

                    componentPanel.add(coeffLabel, gbcComponent);

                    gbcComponent.gridx++;
                    componentPanel.add(textFieldCoeff, gbcComponent);

                    gbc2.gridy++;
                    coeffsPanel.add(componentPanel, gbc2);
                }

                enterCoeffsScroll.setViewportView(coeffsPanel); // Set the pointsPanel as the viewport's view component
                enterCoeffsScroll.revalidate(); // Revalidate the scroll pane to update its content

                // Add a DocumentListener to each text field;
                ArrayList<JTextField> fields = new ArrayList<>(coeffsFields);
                fields.add(enterAField);
                fields.add(enterBField);
                addDocumentListenerToFields(generateButton, fields);
            });

            generateButton.addActionListener(e -> {
                ArrayList<BigDecimal> coeffs = new ArrayList<>();
                try {
                    for (JTextField coeffField : coeffsFields) {
                        String coeffFiledContent = coeffField.getText();
                        BigDecimal coeff = EvaluateString.evaluate(coeffFiledContent);
                        coeffs.add(coeff);
                    }
                    Polynomial poly = new Polynomial(coeffs);
                    String a = enterAField.getText();
                    String b = enterBField.getText();
                    int n = (int) enterNSp.getValue();
                    PointsFunction ptsFunc = poly.toPointsFunction(a, b, n);
                    ArrayList<BigDecimal> xp = ptsFunc.getXp();
                    ArrayList<BigDecimal> yp = ptsFunc.getYp();
                    pointsText.setText("");
                    for (int i = 0; i < xp.size(); i++) {
                        String sb = "x" + i + " = " + fixAccuracy(xp.get(i)) + '\t' + "y" + i + " = " + fixAccuracy(yp.get(i));
                        pointsText.append(sb);
                        pointsText.append("\n");
                    }
                    pointsText.repaint();
                    continueButton.setEnabled(true);
                    function = ptsFunc;

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? invalidInputs : ex.getMessage(), error, JOptionPane.ERROR_MESSAGE);
                }
            });

            JPanel contentPanel = new JPanel(new BorderLayout());
            contentPanel.setBorder(BorderFactory.createEmptyBorder(-10, 10, 10, 10));

            contentPanel.add(infoPanel, BorderLayout.NORTH);
            contentPanel.add(inputsPanel, BorderLayout.CENTER);
            contentPanel.add(generateButton, BorderLayout.SOUTH);

            inputCard.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            inputCard.add(contentPanel, BorderLayout.CENTER);

        }
        polynomialFunctionPanel.add(inputCard);


        polynomialFunctionPanel.add(pointsCard);
    }

    /**
     * Initializes the System of Non-Linear Equations panel.
     * Sets the name, size, and background color of the panel.
     * Creates input and points cards for solving a system of non-linear equations using the Newton-Raphson method.
     * Adds the cards to the panel.
     */
    private void initSysNonLinEqPanel() {
        systemOfNonLinearEquationsPanel = new JPanel();
        systemOfNonLinearEquationsPanel.setName("System of Non-Linear Equations");
        systemOfNonLinearEquationsPanel.setPreferredSize(mainFrame.getSize());
        systemOfNonLinearEquationsPanel.setBackground(new Color(100, 100, 100));
        GridLayout startLayout = new GridLayout(1, 2);
        startLayout.setHgap(5);
        startLayout.setVgap(5);
        systemOfNonLinearEquationsPanel.setLayout(startLayout);
        systemOfNonLinearEquationsPanel.setComponentOrientation(orientation);

        JPanel pointsCard = new JPanel();
        JTextArea pointsText;
        JScrollPane pointsScrollPane;
        JSpinner enterNSp;
        JButton solveButton;
        {

            //points card title
            JPanel titlePanel = new JPanel(new BorderLayout());
            JLabel pointsTitle = new JLabel();
            pointsTitle.setText("<html>" + languageBundle.getString("solutionApproximations") + "</html>");
            pointsTitle.setComponentOrientation(orientation);
            pointsTitle.setFont(new Font(mainFont, Font.BOLD, 25));
            titlePanel.add(pointsTitle, BorderLayout.NORTH);

            // inputs : n
            JLabel enterNTitle = new JLabel("<html>" + languageBundle.getString("enterNIteration") + " : " + "</html>");
            enterNTitle.setComponentOrientation(orientation);
            enterNTitle.setHorizontalAlignment(SwingConstants.LEFT);

            SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 1000, 1);
            enterNSp = new JSpinner(spinnerModel);
            JSpinner.NumberEditor editor = (JSpinner.NumberEditor) enterNSp.getEditor();
            editor.getTextField().setColumns(3); // Adjust the width as needed

            JPanel enterN = new JPanel();
            if (local.getLanguage().equals("en")) {
                enterN.setLayout(new FlowLayout(FlowLayout.LEFT));
                enterN.add(enterNTitle);
                enterN.add(enterNSp);
            } else {
                enterN.setLayout(new FlowLayout(FlowLayout.RIGHT));
                enterN.add(enterNSp);
                enterN.add(enterNTitle);
            }

            titlePanel.add(enterN, BorderLayout.CENTER);

            //point card area
            pointsText = new JTextArea();
            //pointsText.setPreferredSize(new Dimension(450, 550));
            pointsText.setFont(new Font(mainFont, Font.PLAIN, 20));
            pointsText.setEditable(false);
            pointsScrollPane = new JScrollPane(pointsText);
            pointsScrollPane.setPreferredSize(new Dimension(450, 480));

            // Continue Button
            solveButton = new JButton(Solve);
            solveButton.setFont(new Font(buttonFont, Font.BOLD, 20));
            solveButton.setFocusPainted(false);
            solveButton.setPreferredSize(new Dimension(60, 50));
            Color customGreen = new Color(34, 139, 34);  // RGB values for green color
            solveButton.setBackground(customGreen);
            solveButton.setForeground(Color.white);  // Set the text color to white for better visibility
            solveButton.setEnabled(false); // Disable the button initially

            // content panel
            JPanel contentPanel = new JPanel(new BorderLayout());
            contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
            contentPanel.add(titlePanel, BorderLayout.NORTH);
            contentPanel.add(pointsScrollPane, BorderLayout.CENTER);
            contentPanel.add(solveButton, BorderLayout.SOUTH);

            pointsCard.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            pointsCard.add(contentPanel, BorderLayout.CENTER);

        }

        JPanel inputCard = new JPanel();
        {
            //title
            JLabel cardTitle = new JLabel();
            cardTitle.setText(languageBundle.getString("newtonRaphsonSystemMethod"));
            cardTitle.setFont(new Font(mainFont, Font.BOLD, 25));

            //info panel
            JPanel infoPanel = new JPanel(new GridLayout(1, 1, 0, -50));
            infoPanel.add(cardTitle);

            // inputs : fx
            JLabel enterFxTitle = new JLabel("<html>" + languageBundle.getString("enterButton") + " f(x,y) :" + "</html>");
            enterFxTitle.setComponentOrientation(orientation);
            JTextField enterFxField = new JTextField();
            enterFxField.setPreferredSize(new Dimension(100, 40));

            JPanel enterFx = new JPanel();
            GridLayout inputLayout = new GridLayout(2, 1, 0, -5);
            enterFx.setLayout(inputLayout);
            enterFx.add(enterFxTitle);
            enterFx.add(enterFxField);

            // inputs : gx
            JLabel enterGxTitle = new JLabel("<html>" + languageBundle.getString("enterButton") + " g(x,y) :" + "</html>");
            enterGxTitle.setComponentOrientation(orientation);
            JTextField enterGxField = new JTextField();
            enterGxField.setPreferredSize(new Dimension(100, 40));

            JPanel enterGx = new JPanel();
            enterGx.setLayout(inputLayout);
            enterGx.add(enterGxTitle);
            enterGx.add(enterGxField);

            // inputs : dfdx
            JLabel enterDfdxTitle = new JLabel("<html>" + languageBundle.getString("enterButton") + " dfdx :" + "</html>");
            enterDfdxTitle.setComponentOrientation(orientation);
            JTextField enterDfdxField = new JTextField();
            //enterAField.setPreferredSize(new Dimension(100, 50));

            JPanel enterDfdx = new JPanel();
            enterDfdx.setLayout(inputLayout);
            enterDfdx.add(enterDfdxTitle);
            enterDfdx.add(enterDfdxField);

            // inputs : dfdy
            JLabel enterDfdyTitle = new JLabel("<html>" + languageBundle.getString("enterButton") + " dfdy :" + "</html>");
            enterDfdyTitle.setComponentOrientation(orientation);
            JTextField enterDfdyField = new JTextField();
            //enterAField.setPreferredSize(new Dimension(100, 50));

            JPanel enterDfdy = new JPanel();
            enterDfdy.setLayout(inputLayout);
            enterDfdy.add(enterDfdyTitle);
            enterDfdy.add(enterDfdyField);

            // inputs : dgdx
            JLabel enterDgdxTitle = new JLabel("<html>" + languageBundle.getString("enterButton") + " dgdx :" + "</html>");
            enterDgdxTitle.setComponentOrientation(orientation);
            JTextField enterDgdxField = new JTextField();
            //enterBField.setPreferredSize(new Dimension(100, 50));

            JPanel enterDgdx = new JPanel();
            enterDgdx.setLayout(inputLayout);
            enterDgdx.add(enterDgdxTitle);
            enterDgdx.add(enterDgdxField);

            // inputs : dgdy
            JLabel enterDgdyTitle = new JLabel("<html>" + languageBundle.getString("enterButton") + " dgdy :" + "</html>");
            enterDgdyTitle.setComponentOrientation(orientation);
            JTextField enterDgdyField = new JTextField();
            //enterBField.setPreferredSize(new Dimension(100, 50));

            JPanel enterDgdy = new JPanel();
            enterDgdy.setLayout(inputLayout);
            enterDgdy.add(enterDgdyTitle);
            enterDgdy.add(enterDgdyField);

            // inputs : x0
            JLabel enterX0Title = new JLabel("<html>" + languageBundle.getString("enterButton") + " X0 :" + "</html>");
            enterX0Title.setComponentOrientation(orientation);
            JTextField enterX0Field = new JTextField();
            //enterBField.setPreferredSize(new Dimension(100, 50));

            JPanel enterX0 = new JPanel();
            enterX0.setLayout(inputLayout);
            enterX0.add(enterX0Title);
            enterX0.add(enterX0Field);

            // inputs : y0
            JLabel enterY0Title = new JLabel("<html>" + languageBundle.getString("enterButton") + " Y0 :" + "</html>");
            enterY0Title.setComponentOrientation(orientation);
            JTextField enterY0Field = new JTextField();
            //enterBField.setPreferredSize(new Dimension(100, 50));

            JPanel enterY0 = new JPanel();
            enterY0.setLayout(inputLayout);
            enterY0.add(enterY0Title);
            enterY0.add(enterY0Field);


            // inputs panel
            JPanel inputsPanel = new JPanel(new GridLayout(8, 1, -5, -10));
            inputsPanel.add(enterFx);
            inputsPanel.add(enterGx);
            inputsPanel.add(enterDfdx);
            inputsPanel.add(enterDfdy);
            inputsPanel.add(enterDgdx);
            inputsPanel.add(enterDgdy);
            inputsPanel.add(enterX0);
            inputsPanel.add(enterY0);

            // solve button action
            solveButton.addActionListener(e -> {
                String fxS = enterFxField.getText();
                ExpressionFunction fx = new ExpressionFunction(fxS);
                String gxS = enterGxField.getText();
                ExpressionFunction gx = new ExpressionFunction(gxS);
                String dfdxS = enterDfdxField.getText();
                ExpressionFunction dfdx = new ExpressionFunction(dfdxS);
                String dfdyS = enterDfdyField.getText();
                ExpressionFunction dfdy = new ExpressionFunction(dfdyS);
                String dgdxS = enterDgdxField.getText();
                ExpressionFunction dgdx = new ExpressionFunction(dgdxS);
                String dgdyS = enterDgdyField.getText();
                ExpressionFunction dgdy = new ExpressionFunction(dgdyS);
                BigDecimal x0 = EvaluateString.evaluate(enterX0Field.getText());
                BigDecimal y0 = EvaluateString.evaluate(enterY0Field.getText());
                int n = (int) enterNSp.getValue();
                try {
                    ArrayList<ArrayList<BigDecimal>> points = SystemOfNonLinearEquations.Newton_Raphson.solve(fx, dfdx, dfdy, gx, dgdx, dgdy, x0, y0, n);
                    ArrayList<BigDecimal> xp = points.get(0);
                    ArrayList<BigDecimal> yp = points.get(1);
                    pointsText.setText("");
                    for (int i = 0; i < xp.size(); i++) {
                        String sb = "x" + (i + 1) + " = " + fixAccuracy(xp.get(i)) + '\t' + "y" + (i + 1) + " = " + fixAccuracy(yp.get(i));
                        pointsText.append(sb);
                        pointsText.append("\n");
                    }
                    pointsText.repaint();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? invalidInputs : ex.getMessage(), error, JOptionPane.ERROR_MESSAGE);
                }
            });

            // Add a DocumentListener to each text field
            ArrayList<JTextField> fields = new ArrayList<>();
            fields.add(enterFxField);
            fields.add(enterGxField);
            fields.add(enterDfdxField);
            fields.add(enterDfdyField);
            fields.add(enterDgdxField);
            fields.add(enterDgdyField);
            fields.add(enterX0Field);
            fields.add(enterY0Field);
            addDocumentListenerToFields(solveButton, fields);

            JPanel contentPanel = new JPanel(new BorderLayout());
            contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

            contentPanel.add(infoPanel, BorderLayout.NORTH);
            contentPanel.add(inputsPanel, BorderLayout.CENTER);

            inputCard.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            inputCard.add(contentPanel, BorderLayout.CENTER);

        }
        systemOfNonLinearEquationsPanel.add(inputCard);


        systemOfNonLinearEquationsPanel.add(pointsCard);
    }

    /**
     * Updates the main panel of the main frame with the top panel from the panels stack.
     * Enables/disables the back and home buttons based on the size of the panels stack.
     */
    private void updateMainPanel() {
        mainFrame.getContentPane().removeAll(); // Remove all existing components
        mainFrame.getContentPane().add(panelsStack.peek()); // Add the newContentPanel
        mainFrame.revalidate(); // Revalidate the frame to update the layout
        mainFrame.repaint(); // Repaint the frame to reflect the changes
        backButton.setEnabled(panelsStack.size() > 1);
        homeButton.setEnabled(panelsStack.size() > 1);
    }

    /**
     * Creates a card panel with customizable title, description, and action button.
     *
     * @param title       The title of the card panel.
     * @param description The description of the card panel.
     * @param button      The text for the action button.
     * @param doAction    The ActionListener for the action button.
     * @return The created card panel.
     */
    private JPanel createCard(String title, String description, String button, ActionListener doAction) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());

        JLabel cardTitle = new JLabel();
        cardTitle.setText(title);
        cardTitle.setFont(new Font(mainFont, Font.BOLD, 25));
        cardTitle.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel cardDescription = new JLabel();
        cardDescription.setText(description);
        cardDescription.setFont(new Font(secondFont, Font.PLAIN, 15));
        cardDescription.setHorizontalAlignment(SwingConstants.CENTER);

        JButton cardButton = new JButton(button);
        cardButton.setFont(new Font(buttonFont, Font.BOLD, 20));
        cardButton.setFocusPainted(false);
        cardButton.setPreferredSize(new Dimension(60, 50));
        cardButton.addActionListener(doAction);

        JPanel contentPanel = new JPanel(new GridLayout(3, 1));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        contentPanel.add(cardTitle);
        contentPanel.add(cardDescription);
        contentPanel.add(cardButton);

        card.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        card.add(contentPanel, BorderLayout.CENTER);
        return card;
    }

    /**
     * Creates a card panel with customizable title, description, and action button.
     *
     * @param title         The title of the card panel.
     * @param button        The text for the action button.
     * @param doAction      The ActionListener for the action button.
     * @param toolTipString The description of the card panel.
     * @return The created card panel.
     */
    private JPanel createCard(String title, String button, ActionListener doAction, String toolTipString) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());

        JLabel cardTitle = new JLabel();
        cardTitle.setText(title);
        cardTitle.setFont(new Font(mainFont, Font.BOLD, 25));
        cardTitle.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel cardDescription = new JLabel();
        cardDescription.setText(toolTipString);
        cardDescription.setFont(new Font(secondFont, Font.ITALIC, 15));
        cardDescription.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel tipLabel = new JLabel("<html>" + languageBundle.getString("moreInfo") + "</html>");
        tipLabel.setIcon(bulbIcon);
        tipLabel.setHorizontalAlignment(SwingConstants.CENTER);
        tipLabel.setToolTipText(toolTipString);

        JButton cardButton = new JButton(button);
        cardButton.setFont(new Font(buttonFont, Font.BOLD, 20));
        cardButton.setFocusPainted(false);
        cardButton.setPreferredSize(new Dimension(60, 50));
        cardButton.addActionListener(doAction);

        JPanel contentPanel = new JPanel(new GridLayout(3, 1));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        contentPanel.add(cardTitle);
        contentPanel.add(tipLabel);
        contentPanel.add(cardButton);

        card.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        card.add(contentPanel, BorderLayout.CENTER);
        return card;
    }

    /**
     * Adds a DocumentListener to the specified text fields and associates it with the given button.
     * The DocumentListener updates the enabled state of the button based on changes in the text fields.
     *
     * @param button     the button to enable/disable based on the text field changes
     * @param textFields the list of text fields to attach the DocumentListener to
     */
    private void addDocumentListenerToFields(JButton button, ArrayList<JTextField> textFields) {
        DocumentListener documentListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateButtonEnabledState(button, textFields);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateButtonEnabledState(button, textFields);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateButtonEnabledState(button, textFields);
            }
        };
        for (JTextField field : textFields) {
            field.getDocument().addDocumentListener(documentListener);
        }
    }

    /**
     * Updates the enabled state of the specified button based on the content of the text fields.
     * The button will be enabled if all text fields are non-empty; otherwise, it will be disabled.
     *
     * @param button     the button to update the enabled state
     * @param textFields the list of text fields to check for content
     */
    private void updateButtonEnabledState(JButton button, ArrayList<JTextField> textFields) {
        boolean enabled = true;
        for (JTextField field : textFields) {
            if (field.getText().isEmpty()) {
                enabled = false;
                break;
            }
        }
        button.setEnabled(enabled);
    }

    /**
     * Recursively updates the look and feel of Swing components within the given container and its child components.
     * <p>
     * This method traverses the component hierarchy and applies the appropriate UI updates to all components in the
     * container and its nested containers. It should be called after changing the look and feel of the Swing application
     * to apply the changes to all components and their children.
     *
     * @param container The Container object that contains the components to update. It can be a JPanel, JFrame, or any
     *                  other Swing container.
     */
    private void updatePanelUI(Container container) {
        for (Component component : container.getComponents()) {
            SwingUtilities.updateComponentTreeUI(component);
            if (component instanceof Container) {
                updatePanelUI((Container) component);
            }
        }
    }

    /**
     * Returns an ArrayList containing multiple JPanel objects representing different panels in the application.
     * <p>
     * This method provides a convenient way to obtain references to various panels used in the application. The returned
     * ArrayList includes all the panels defined in the application, such as startPanel, chooseFunctionPanel,
     * interpolationPanel, integralPanel, and so on.
     *
     * @return An ArrayList of JPanel objects representing different panels in the application.
     */
    public ArrayList<JPanel> getPanels() {
        ArrayList<JPanel> panels = new ArrayList<>();
        panels.add(startPanel);
        panels.add(chooseFunctionPanel);
        panels.add(interpolationPanel);
        panels.add(integralPanel);
        panels.add(differentiationPanel);
        panels.add(differentialEquationsPanel);
        panels.add(nonLinearEquationsPanel);
        panels.add(systemOfNonLinearEquationsPanel);
        panels.add(polynomialsPanel);
        panels.add(expressionFunctionPanel);
        panels.add(pointsFunctionPanel);
        panels.add(polynomialFunctionPanel);
        return panels;
    }

    /**
     * Fix the Accuracy to its main value
     *
     * @param num the number to fix its precision
     * @return a new BigDecimal representing the fixed number's precision stripped from trailing zeros
     */
    private BigDecimal fixAccuracy(BigDecimal num) {
        return num.round(new MathContext(Accuracy.getValue(), RoundingMode.HALF_UP)).stripTrailingZeros();
    }

    /**
     * Sets the light theme based on the selected theme name.
     * The available light theme options are:
     * "Light Flat", "Arc Light", "Arc Orange", "Cyan Light", "Solarized Light", "Gray", "Atom One Light".
     * If an unknown theme name is provided, it falls back to "Light Flat" as the default theme.
     */
    private void setLightTheme() {
        switch (mainLightTheme) {
            case "Light Flat": {
                FlatLightLaf.setup();
                break;
            }
            case "Arc Light": {
                FlatArcIJTheme.setup();
                break;
            }
            case "Arc Orange": {
                FlatArcOrangeIJTheme.setup();
                break;
            }
            case "Cyan Light": {
                FlatCyanLightIJTheme.setup();
                break;
            }
            case "Solarized Light": {
                FlatSolarizedLightIJTheme.setup();
                break;
            }
            case "Gray": {
                FlatGrayIJTheme.setup();
                break;
            }
            case "Atom One Light": {
                FlatAtomOneLightIJTheme.setup();
                break;
            }
            default: {
                FlatLightLaf.setup();
                break;
            }
        }
    }

    /**
     * Sets the dark theme based on the selected theme name.
     * The available dark theme options are:
     * "One Dark", "Arc Dark", "Arc Dark Orange", "Carbon", "Dark Flat", "Dark Purple", "Dracula", "Atom One Dark".
     * If an unknown theme name is provided, it falls back to "One Dark" as the default theme.
     */
    private void setDarkTheme() {

        switch (mainDarkTheme) {
            case "One Dark": {
                FlatOneDarkIJTheme.setup();
                break;
            }
            case "Arc Dark": {
                FlatArcDarkIJTheme.setup();
                break;
            }
            case "Arc Dark Orange": {
                FlatArcDarkOrangeIJTheme.setup();
                break;
            }
            case "Carbon": {
                FlatCarbonIJTheme.setup();
                break;
            }
            case "Dark Flat": {
                FlatDarkFlatIJTheme.setup();
                break;
            }
            case "Dark Purple": {
                FlatDarkPurpleIJTheme.setup();
                break;
            }
            case "Dracula": {
                FlatDraculaIJTheme.setup();
                break;
            }
            case "Atom One Dark": {
                FlatAtomOneDarkIJTheme.setup();
                break;
            }
            default: {
                FlatOneDarkIJTheme.setup();
                break;
            }
        }
    }

    /**
     * Sets the application's language and updates UI elements accordingly.
     *
     * @param lang The language code to set (e.g., "en" for English, "ar" for Arabic).
     *             This method changes the display language and text direction of UI components.
     */
    private void setLangauge(String lang) {
        local = new Locale(lang);
        languageBundle = ResourceBundle.getBundle("Bundles.language", local);
        String textDirection = languageBundle.getString("dir");
        if ("RIGHT_TO_LEFT".equalsIgnoreCase(textDirection)) {
            orientation = ComponentOrientation.RIGHT_TO_LEFT;
        } else {
            orientation = ComponentOrientation.LEFT_TO_RIGHT;
        }
        if (lang.equals("en")) {
            mainFont = englishFont;
            secondFont = englishFont;
            buttonFont = englishFont;
            Locale.setDefault(Locale.ENGLISH);
        } else {
            mainFont = arabicFont;
            secondFont = arabicFont;
            buttonFont = arabicFont;
            Locale.setDefault(new Locale("ar", "SA"));
        }
    }

    /**
     * Saves the current application settings to the configuration file.
     * The settings include accuracy, light and dark themes, and selected language.
     * If an I/O exception occurs during the save process, it is caught and logged.
     */
    private void saveSettings() {
        settings.setAccuracy(Accuracy.getValue());
        settings.setLightTheme(mainLightTheme);
        settings.setDarkTheme(mainDarkTheme);
        settings.setLangauge(local.getLanguage());
        settings.saveSettings();
    }

    /**
     * Loads previously saved application settings from the configuration file.
     * The settings include light and dark themes, accuracy, and language.
     * If the configuration file does not exist, default settings are applied.
     * If an I/O exception occurs during the loading process, it is caught and logged.
     */
    private void loadSettings() {
        settings = new SettingsManager();
        mainLightTheme = settings.getLightTheme();
        mainDarkTheme = settings.getDarkTheme();
        Accuracy.setValue(settings.getAccuracy());
        setLangauge(settings.getLanguage());
    }
}
