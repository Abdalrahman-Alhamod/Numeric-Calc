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
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.Stack;
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
     * The main font used in the GUI.
     */
    private final String mainFont = "Times New Roman";
    /**
     * The secondary font used in the GUI.
     */
    private final String secondFont = "Times New Roman";
    /**
     * The font used for buttons.
     */
    private final String buttonFont = "Times New Roman";
    /**
     * A boolean representing the current theme
     */
    boolean darkModeEnabled;
    SettingsManager settings;
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
     * Constructs a new instance of the GUI class.
     */
    public GUI() {
        try {
            settings = new SettingsManager();
            mainLightTheme = settings.getLightTheme();
            mainDarkTheme = settings.getDarkTheme();
            Accuracy.setValue(settings.getAccuracy());
            setLightTheme();
            Locale.setDefault(Locale.ENGLISH); // fix spinner and text showing arabic symbols
            initIcons();
            initMainFrame();
            initMenuBar();
            initPanels();
            updateMainPanel();
            mainFrame.pack();
            mainFrame.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
                settings.setAccuracy(Accuracy.getValue());
                settings.setLightTheme(mainLightTheme);
                settings.setDarkTheme(mainDarkTheme);
                settings.saveSettings();
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

        JLabel aboutLabel = new JLabel("<html>" + "Created by Abd_HM  ;) <br>" + "Full program documentation on my github : <br>" + "</html>");
        aboutLabel.setFont(new Font(Font.SERIF, Font.BOLD, 15));
        aboutLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        JLabel contactLabel = new JLabel("Contact me : ");
        contactLabel.setFont(new Font(Font.SERIF, Font.BOLD, 15));
        // Create JLabels with hyperlink functionality for each link
        JButton docLabel = createHyperlinkButton("Github.com/Abdalrahman-Alhamod/Numeric-Calc", "https://github.com/Abdalrahman-Alhamod/Numeric-Calc");
        docLabel.setBackground(new Color(0, 102, 204)); // Set color to light blue

        JButton emailLabel = createHyperlinkButton("E-mail", "mailto:abd.alrrahman.alhamod@gmail.com");
        emailLabel.setBackground(new Color(204, 0, 0)); // Set color to dark red

        JButton githubLabel = createHyperlinkButton("Github", "https://github.com/Abdalrahman-Alhamod");
        githubLabel.setBackground(new Color(0, 153, 0)); // Set color to light green

        JButton linkedInLabel = createHyperlinkButton("LinkedIn", "https://www.linkedin.com/in/abd-alrrahman-alhamod/");
        linkedInLabel.setBackground(new Color(0, 102, 153)); // Set color to dark blue

        JButton facebookLabel = createHyperlinkButton("Facebook", "https://www.facebook.com/profile.php?id=100011427430343");
        facebookLabel.setBackground(new Color(59, 89, 152)); // Set color to Facebook blue


        JPanel links = new JPanel(new GridLayout(2, 2));
        links.add(emailLabel);
        links.add(githubLabel);
        links.add(linkedInLabel);
        links.add(facebookLabel);

        // Add the labels to the panel
        panel.add(aboutLabel);
        panel.add(docLabel);
        panel.add(contactLabel);
        panel.add(links);

        // Show the custom option dialog
        infoButton.addActionListener(e -> JOptionPane.showOptionDialog(null, panel, "About", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, innovationIcon, new Object[]{}, null));
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
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
            JLabel enterAccuracyTitle = new JLabel("Set Accuracy ( the number of digit after the comma ) : ");
            enterAccuracyTitle.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
            enterAccuracyTitle.setHorizontalAlignment(SwingConstants.LEFT);

            SpinnerNumberModel spinnerModel = new SpinnerNumberModel(Accuracy.getValue(), 1, 30, 1);
            JSpinner enterAccuracySp = new JSpinner(spinnerModel);
            JSpinner.NumberEditor editor = (JSpinner.NumberEditor) enterAccuracySp.getEditor();
            editor.getTextField().setColumns(2); // Adjust the width as needed

            JPanel enterAccuracy = new JPanel();
            enterAccuracy.setLayout(new FlowLayout(FlowLayout.LEFT));
            enterAccuracy.add(enterAccuracyTitle, BorderLayout.WEST);
            enterAccuracy.add(enterAccuracySp, BorderLayout.CENTER);
            //enterAccuracy.setBorder(BorderFactory.createEmptyBorder(10, 0, -10, 0));

            JLabel lightThemeLabel = new JLabel("Set Light Theme ");
            lightThemeLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
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

            JPanel lightThemePanel = new JPanel(new FlowLayout());
            lightThemePanel.add(lightThemeLabel);
            lightThemePanel.add(lightThemeCombo);

            JLabel darkThemeLabel = new JLabel("Set Dark Theme ");
            darkThemeLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
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

            JPanel darkThemePanel = new JPanel(new FlowLayout());
            darkThemePanel.add(darkThemeLabel);
            darkThemePanel.add(darkThemeCombo);

            JPanel settingsPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(5, 5, 5, 5);
            settingsPanel.add(enterAccuracy, gbc);
            gbc.gridy++;
            gbc.insets = new Insets(5, -180, 5, 5);
            settingsPanel.add(lightThemePanel, gbc);
            gbc.insets = new Insets(5, -178, 5, 5);
            gbc.gridy++;
            settingsPanel.add(darkThemePanel, gbc);

            JButton confirmButton = new JButton("Confirm");
            confirmButton.setFocusPainted(false);
            Color customGreen = new Color(34, 139, 34);  // RGB values for green color
            confirmButton.setBackground(customGreen);
            confirmButton.setForeground(Color.white);  // Set the text color to white for better visibility

            JButton defaultButton = new JButton("Default");
            defaultButton.setFocusPainted(false);
            defaultButton.setBackground(Color.lightGray);
            defaultButton.setForeground(Color.black);  // Set the text color to white for better visibility

            JButton cancelButton = new JButton("Cancel");
            cancelButton.setFocusPainted(false);

            Object[] buttons = {cancelButton, defaultButton, confirmButton};

            cancelButton.addActionListener(cancel -> {
                Window optionDialog = SwingUtilities.getWindowAncestor(enterAccuracy);
                optionDialog.dispose();
            });
            confirmButton.addActionListener(solve -> {
                Window optionDialog = SwingUtilities.getWindowAncestor(enterAccuracy);
                optionDialog.dispose();
                int newAccuracy = (int) enterAccuracySp.getValue();
                Accuracy.setValue(newAccuracy);
                mainLightTheme = String.valueOf(lightThemeCombo.getSelectedItem());
                mainDarkTheme = String.valueOf(darkThemeCombo.getSelectedItem());
                updateTheme.accept(new FlatLightLaf());
            });

            defaultButton.addActionListener(defaultSettings -> {
                Window optionDialog = SwingUtilities.getWindowAncestor(enterAccuracy);
                optionDialog.dispose();
                Accuracy.setValue(10);
                mainLightTheme = "Light Flat";
                mainDarkTheme = "One Dark";
                updateTheme.accept(new FlatLightLaf());
            });
            JOptionPane.showOptionDialog(null, settingsPanel, "Settings", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, settingsKeyIcon, buttons, null);

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
        //***********************************************************************

        //init Interpolation Card
        String title = "Interpolation";
        String description = "Interpolate an entered function using numeric interpolation ways like General Method," + " Lagrange, Newton-Gregory, Least-Squares";
        String button = "Enter";
        ActionListener enterInterpolation = e -> {
            panelsStack.add(interpolationPanel);
            updateMainPanel();
        };
        JPanel interpolationCard = createCard(title, description, button, enterInterpolation);
        startPanel.add(interpolationCard, BorderLayout.NORTH);
        //***********************************************************************

        //init Integral Card
        title = "Integral";
        description = "Integrate an entered function using numeric integration ways";
        button = "Enter";
        ActionListener enterIntegral = e -> {
            panelsStack.add(integralPanel);
            updateMainPanel();
        };
        JPanel integralCard = createCard(title, description, button, enterIntegral);
        cardsPanel.add(integralCard);

        //***********************************************************************

        //init Differentiation Card
        title = "Differentiation";
        description = "Differentiate an entered function using numeric differentiation ways";
        button = "Enter";
        ActionListener enterDifferentiation = e -> {
            panelsStack.add(differentiationPanel);
            updateMainPanel();
        };
        JPanel diffCard = createCard(title, description, button, enterDifferentiation);
        cardsPanel.add(diffCard);

        //***********************************************************************

        //init Differential Equations Card
        title = "Differential Equations";
        description = "Solve an entered differential equations using numeric ways";
        button = "Enter";
        ActionListener enterDiffEQ = e -> {
            panelsStack.add(differentialEquationsPanel);
            updateMainPanel();
        };
        JPanel diffEQCard = createCard(title, description, button, enterDiffEQ);
        cardsPanel.add(diffEQCard);

        //***********************************************************************

        //init Non-Linear Equations Card
        title = "Non-Linear Equations";
        description = "Solve an entered non-Linear equations using numeric ways";
        button = "Enter";
        ActionListener enterNonLinEQ = e -> {
            panelsStack.add(nonLinearEquationsPanel);
            updateMainPanel();
        };
        JPanel nonLinEQCard = createCard(title, description, button, enterNonLinEQ);
        cardsPanel.add(nonLinEQCard);


        //***********************************************************************

        //init System Of Non-Linear Equations Card
        title = "System Of Non-Linear Equations";
        description = "Solve an entered system of non-Linear equations using numeric ways";
        button = "Enter";
        ActionListener enterSysNonLinEQ = e -> {
            panelsStack.add(systemOfNonLinearEquationsPanel);
            updateMainPanel();
        };
        JPanel sysOfNonLinEQCard = createCard(title, description, button, enterSysNonLinEQ);
        cardsPanel.add(sysOfNonLinEQCard);


        //***********************************************************************

        //init Differential Equations Card
        title = "Polynomials";
        description = "Do several tasks on an entered polynomial using numeric ways";
        button = "Enter";
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
        JPanel expressionFunctionCard = createCard("Expression Function", "Functions like x^2+3, sin(x), exp(x) ...", "Enter", enterExpressionFunction);


        ActionListener enterPointsFunction = e -> {
            panelsStack.add(pointsFunctionPanel);
            updateMainPanel();
        };
        JPanel pointsFunctionCard = createCard("Points Function", "Functions like x0=.. y0=.. , x1=.. y1=.. ", "Enter", enterPointsFunction);

        ActionListener enterPolynomialFunction = e -> {
            panelsStack.add(polynomialFunctionPanel);
            updateMainPanel();
        };
        JPanel polynomialFunctionCard = createCard("Polynomial Function", "Functions like p(x) = a0 + a1*x + a2*x^2 ... ", "Enter", enterPolynomialFunction);


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

        //***********************************************************************

        //init General Method Card
        String title = "General Method";
        String description = "Get the Interpolation Function using" + " the General Method by solving a system of equations using Gaussian elimination ";
        String button = "Enter";
        ActionListener enterGeneralMethod = e -> {
            doAction = pointsFunction -> {
                try {
                    Polynomial ans = Interpolation.GeneralMethod.getIFAP(pointsFunction);
                    //create title
                    JLabel interTitle = new JLabel();
                    interTitle.setText("Interpolation answer using General Method : ");
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

                    String[] response = {"Cancel", "Continue with Polynomial"};

                    int feed = JOptionPane.showOptionDialog(null, contentPanel, "Interpolation", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon, response, response[1]);
                    if (feed == 1) {
                        polynomial = ans;
                        panelsStack.add(polynomialsPanel);
                        updateMainPanel();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? "Invalid inputs" : ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            };
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel generalMethodCard = createCard(title, button, enterGeneralMethod, description);
        interpolationPanel.add(generalMethodCard);

        //***********************************************************************

        //init Lagrange Card
        title = "Lagrange";
        description = "Get the Interpolation Function using Lagrange method using Lagrange Polynomials";
        button = "Enter";
        ActionListener enterLagrange = e -> {
            doAction = pointsFunction -> {
                try {
                    Polynomial ans = Interpolation.Lagrange.getIFAP(pointsFunction);

                    //create answer title
                    JLabel interTitle = new JLabel();
                    interTitle.setText("Interpolation answer using Lagrange : ");
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
                    shortPolyTitle.setText("Interpolation answer with no shorthand : ");
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


                    String[] response = {"Cancel", "Continue with Polynomial"};

                    int feed = JOptionPane.showOptionDialog(null, contentPanel, "Interpolation", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon, response, response[1]);
                    if (feed == 1) {
                        polynomial = ans;
                        panelsStack.add(polynomialsPanel);
                        updateMainPanel();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? "Invalid inputs" : ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            };
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel lagrangeCard = createCard(title, button, enterLagrange, description);
        interpolationPanel.add(lagrangeCard);

        //***********************************************************************

        //init Newton-Gregory Forward Subtraction Card
        title = "Newton-Gregory Forward Subtraction";
        description = "Get the interpolation function using Newton-Gregory Forward Subtractions method\n" + "It also can get Newton-Gregory Forward Subtractions Table values";
        button = "Enter";
        ActionListener enterNGFS = e -> {
            doAction = pointsFunction -> {
                try {

                    // inputs : degree
                    JLabel enterDegreeTitle = new JLabel("Enter degree of the required polynomial : ");
                    enterDegreeTitle.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
                    enterDegreeTitle.setHorizontalAlignment(SwingConstants.LEFT);

                    SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 100, 1);
                    JSpinner enterDegreeSp = new JSpinner(spinnerModel);
                    JSpinner.NumberEditor editor = (JSpinner.NumberEditor) enterDegreeSp.getEditor();
                    editor.getTextField().setColumns(2); // Adjust the width as needed

                    JPanel enterDegree = new JPanel();
                    enterDegree.setLayout(new FlowLayout(FlowLayout.LEFT));
                    enterDegree.add(enterDegreeTitle, BorderLayout.WEST);
                    enterDegree.add(enterDegreeSp, BorderLayout.CENTER);
                    enterDegree.setBorder(BorderFactory.createEmptyBorder(10, 0, -10, 0));

                    JButton solveButton = new JButton("Solve");
                    solveButton.setFocusPainted(false);
                    Color customGreen = new Color(34, 139, 34);  // RGB values for green color
                    solveButton.setBackground(customGreen);
                    solveButton.setForeground(Color.white);  // Set the text color to white for better visibility

                    JButton cancelButton = new JButton("Cancel");
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

                    JOptionPane.showOptionDialog(null, enterDegree, "Interpolation", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon64, buttons, null);
                    if (cancelPressed[0]) return;

                    ArrayList<BigDecimal> values = Interpolation.Newton_GregoryForwardSubtractions.getUDV(function);

                    //create table values title
                    JLabel tableTitle = new JLabel();
                    tableTitle.setText("The values of the upper diameter of the Newton-Gregory Forward Subtractions Table : ");
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
                    interTitle.setText("Interpolation answer using Newton-Gregory Forward Subtraction : ");
                    interTitle.setFont(new Font(mainFont, Font.PLAIN, 20));


                    JTextArea polyAns = new JTextArea();
                    polyAns.append("P(x) : ");
                    polyAns.append(ans.toString());
                    polyAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    polyAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(polyAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(300, 70));

                    JLabel shortPolyTitle = new JLabel();
                    shortPolyTitle.setText("Interpolation answer with no shorthand : ");
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


                    String[] response = {"Cancel", "Continue with Polynomial"};

                    int feed = JOptionPane.showOptionDialog(null, contentPanel, "Interpolation", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon, response, response[1]);
                    if (feed == 1) {
                        polynomial = ans;
                        panelsStack.add(polynomialsPanel);
                        updateMainPanel();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? "Invalid inputs" : ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            };
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel NGFSCard = createCard(title, button, enterNGFS, description);
        interpolationPanel.add(NGFSCard);

        //***********************************************************************

        //init Newton-Gregory Backward Subtraction Card
        title = "Newton-Gregory Backward Subtraction";
        description = "Get the interpolation function using Newton-Gregory Backward Subtractions method\n" + "It also can get Newton-Gregory Backward Subtractions Table values";
        button = "Enter";
        ActionListener enterNGBS = e -> {
            doAction = pointsFunction -> {
                try {

                    // inputs : degree
                    JLabel enterDegreeTitle = new JLabel("Enter degree of the required polynomial : ");
                    enterDegreeTitle.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
                    enterDegreeTitle.setHorizontalAlignment(SwingConstants.LEFT);

                    SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 100, 1);
                    JSpinner enterDegreeSp = new JSpinner(spinnerModel);
                    JSpinner.NumberEditor editor = (JSpinner.NumberEditor) enterDegreeSp.getEditor();
                    editor.getTextField().setColumns(2); // Adjust the width as needed

                    JPanel enterDegree = new JPanel();
                    enterDegree.setLayout(new FlowLayout(FlowLayout.LEFT));
                    enterDegree.add(enterDegreeTitle, BorderLayout.WEST);
                    enterDegree.add(enterDegreeSp, BorderLayout.CENTER);
                    enterDegree.setBorder(BorderFactory.createEmptyBorder(10, 0, -10, 0));

                    JButton solveButton = new JButton("Solve");
                    solveButton.setFocusPainted(false);
                    Color customGreen = new Color(34, 139, 34);  // RGB values for green color
                    solveButton.setBackground(customGreen);
                    solveButton.setForeground(Color.white);  // Set the text color to white for better visibility

                    JButton cancelButton = new JButton("Cancel");
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

                    JOptionPane.showOptionDialog(null, enterDegree, "Interpolation", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon64, buttons, null);
                    if (cancelPressed[0]) return;


                    ArrayList<BigDecimal> values = Interpolation.Newton_GregoryBackwardSubtractions.getLDV(function);

                    //create table values title
                    JLabel tableTitle = new JLabel();
                    tableTitle.setText("The values of the lower diameter of the Newton-Gregory Backward Subtractions Table : ");
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
                    interTitle.setText("Interpolation answer using Newton-Gregory Backward Subtraction : ");
                    interTitle.setFont(new Font(mainFont, Font.PLAIN, 20));


                    JTextArea polyAns = new JTextArea();
                    polyAns.append("P(x) : ");
                    polyAns.append(ans.toString());
                    polyAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    polyAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(polyAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(300, 70));

                    JLabel shortPolyTitle = new JLabel();
                    shortPolyTitle.setText("Interpolation answer with no shorthand : ");
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


                    String[] response = {"Cancel", "Continue with Polynomial"};

                    int feed = JOptionPane.showOptionDialog(null, contentPanel, "Interpolation", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon, response, response[1]);
                    if (feed == 1) {
                        polynomial = ans;
                        panelsStack.add(polynomialsPanel);
                        updateMainPanel();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? "Invalid inputs" : ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            };
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel NGBSCard = createCard(title, button, enterNGBS, description);
        interpolationPanel.add(NGBSCard);

        //***********************************************************************

        //init Newton Forward Divided Subtractions Equations Card
        title = "Newton Forward Divided Subtractions";
        description = "Get the interpolation function using Newton Forward Divided Subtractions method\n" + "It also can get Newton Forward Divided Subtractions Table values";
        button = "Enter";
        ActionListener enterNFDS = e -> {
            doAction = pointsFunction -> {
                try {

                    // inputs : degree
                    JLabel enterDegreeTitle = new JLabel("Enter degree of the required polynomial : ");
                    enterDegreeTitle.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
                    enterDegreeTitle.setHorizontalAlignment(SwingConstants.LEFT);

                    SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 100, 1);
                    JSpinner enterDegreeSp = new JSpinner(spinnerModel);
                    JSpinner.NumberEditor editor = (JSpinner.NumberEditor) enterDegreeSp.getEditor();
                    editor.getTextField().setColumns(2); // Adjust the width as needed

                    JPanel enterDegree = new JPanel();
                    enterDegree.setLayout(new FlowLayout(FlowLayout.LEFT));
                    enterDegree.add(enterDegreeTitle, BorderLayout.WEST);
                    enterDegree.add(enterDegreeSp, BorderLayout.CENTER);
                    enterDegree.setBorder(BorderFactory.createEmptyBorder(10, 0, -10, 0));

                    JButton solveButton = new JButton("Solve");
                    solveButton.setFocusPainted(false);
                    Color customGreen = new Color(34, 139, 34);  // RGB values for green color
                    solveButton.setBackground(customGreen);
                    solveButton.setForeground(Color.white);  // Set the text color to white for better visibility

                    JButton cancelButton = new JButton("Cancel");
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

                    JOptionPane.showOptionDialog(null, enterDegree, "Interpolation", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon64, buttons, null);
                    if (cancelPressed[0]) return;


                    ArrayList<BigDecimal> values = Interpolation.NewtonForwardDividedSubtractions.getUDV(function);

                    //create table values title
                    JLabel tableTitle = new JLabel();
                    tableTitle.setText("The values of the upper diameter of the Newton Forward Divided Subtractions Table : ");
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
                    interTitle.setText("Interpolation answer using Newton Forward Divided Subtractions : ");
                    interTitle.setFont(new Font(mainFont, Font.PLAIN, 20));


                    JTextArea polyAns = new JTextArea();
                    polyAns.append("P(x) : ");
                    polyAns.append(ans.toString());
                    polyAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    polyAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(polyAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(300, 70));

                    JLabel shortPolyTitle = new JLabel();
                    shortPolyTitle.setText("Interpolation answer with no shorthand : ");
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


                    String[] response = {"Cancel", "Continue with Polynomial"};

                    int feed = JOptionPane.showOptionDialog(null, contentPanel, "Interpolation", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon, response, response[1]);
                    if (feed == 1) {
                        polynomial = ans;
                        panelsStack.add(polynomialsPanel);
                        updateMainPanel();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? "Invalid inputs" : ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            };
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel NFDSCard = createCard(title, button, enterNFDS, description);
        interpolationPanel.add(NFDSCard);

        //***********************************************************************

        //init Newton Backward Divided Subtractions Equations Card
        title = "Newton Backward Divided Subtractions";
        description = "Get the interpolation function using Newton Backward Divided Subtractions method\n" + "It also can get Newton Backward Divided Subtractions Table values";
        button = "Enter";
        ActionListener enterNDBS = e -> {
            doAction = pointsFunction -> {
                try {

                    // inputs : degree
                    JLabel enterDegreeTitle = new JLabel("Enter degree of the required polynomial : ");
                    enterDegreeTitle.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
                    enterDegreeTitle.setHorizontalAlignment(SwingConstants.LEFT);

                    SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 100, 1);
                    JSpinner enterDegreeSp = new JSpinner(spinnerModel);
                    JSpinner.NumberEditor editor = (JSpinner.NumberEditor) enterDegreeSp.getEditor();
                    editor.getTextField().setColumns(2); // Adjust the width as needed

                    JPanel enterDegree = new JPanel();
                    enterDegree.setLayout(new FlowLayout(FlowLayout.LEFT));
                    enterDegree.add(enterDegreeTitle, BorderLayout.WEST);
                    enterDegree.add(enterDegreeSp, BorderLayout.CENTER);
                    enterDegree.setBorder(BorderFactory.createEmptyBorder(10, 0, -10, 0));

                    JButton solveButton = new JButton("Solve");
                    solveButton.setFocusPainted(false);
                    Color customGreen = new Color(34, 139, 34);  // RGB values for green color
                    solveButton.setBackground(customGreen);
                    solveButton.setForeground(Color.white);  // Set the text color to white for better visibility

                    JButton cancelButton = new JButton("Cancel");
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

                    JOptionPane.showOptionDialog(null, enterDegree, "Interpolation", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon64, buttons, null);
                    if (cancelPressed[0]) return;


                    ArrayList<BigDecimal> values = Interpolation.NewtonBackwardDividedSubtractions.getLDV(function);

                    //create table values title
                    JLabel tableTitle = new JLabel();
                    tableTitle.setText("The values of the lower diameter of the Newton Backward Divided Subtractions Table : ");
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
                    interTitle.setText("Interpolation answer using Newton Backward Divided Subtractions : ");
                    interTitle.setFont(new Font(mainFont, Font.PLAIN, 20));


                    JTextArea polyAns = new JTextArea();
                    polyAns.append("P(x) : ");
                    polyAns.append(ans.toString());
                    polyAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    polyAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(polyAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(300, 70));

                    JLabel shortPolyTitle = new JLabel();
                    shortPolyTitle.setText("Interpolation answer with no shorthand : ");
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


                    String[] response = {"Cancel", "Continue with Polynomial"};

                    int feed = JOptionPane.showOptionDialog(null, contentPanel, "Interpolation", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon, response, response[1]);
                    if (feed == 1) {
                        polynomial = ans;
                        panelsStack.add(polynomialsPanel);
                        updateMainPanel();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? "Invalid inputs" : ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            };
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel NFBSCard = createCard(title, button, enterNDBS, description);
        interpolationPanel.add(NFBSCard);

        //***********************************************************************

        //init Least-Squares Card
        title = "Least-Squares";
        description = "Get the interpolation function using Least-Squares method";
        button = "Enter";
        ActionListener enterLeastSquares = e -> {
            doAction = pointsFunction -> {
                try {

                    // inputs : degree
                    JLabel enterDegreeTitle = new JLabel("Enter degree of the required polynomial : ");
                    enterDegreeTitle.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
                    enterDegreeTitle.setHorizontalAlignment(SwingConstants.LEFT);

                    SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 100, 1);
                    JSpinner enterDegreeSp = new JSpinner(spinnerModel);
                    JSpinner.NumberEditor editor = (JSpinner.NumberEditor) enterDegreeSp.getEditor();
                    editor.getTextField().setColumns(2); // Adjust the width as needed

                    JPanel enterDegree = new JPanel();
                    enterDegree.setLayout(new FlowLayout(FlowLayout.LEFT));
                    enterDegree.add(enterDegreeTitle, BorderLayout.WEST);
                    enterDegree.add(enterDegreeSp, BorderLayout.CENTER);
                    enterDegree.setBorder(BorderFactory.createEmptyBorder(10, 0, -10, 0));

                    JButton solveButton = new JButton("Solve");
                    solveButton.setFocusPainted(false);
                    Color customGreen = new Color(34, 139, 34);  // RGB values for green color
                    solveButton.setBackground(customGreen);
                    solveButton.setForeground(Color.white);  // Set the text color to white for better visibility

                    JButton cancelButton = new JButton("Cancel");
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

                    JOptionPane.showOptionDialog(null, enterDegree, "Interpolation", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon64, buttons, null);
                    if (cancelPressed[0]) return;


                    Polynomial ans = Interpolation.LeastSquares.getIFAP(pointsFunction, degree[0]);

                    //create title
                    JLabel interTitle = new JLabel();
                    interTitle.setText("Interpolation answer using Least-Squares : ");
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

                    String[] response = {"Cancel", "Continue with Polynomial"};

                    int feed = JOptionPane.showOptionDialog(null, contentPanel, "Interpolation", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon, response, response[1]);
                    if (feed == 1) {
                        polynomial = ans;
                        panelsStack.add(polynomialsPanel);
                        updateMainPanel();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? "Invalid inputs" : ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            };
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel leastSquaresCard = createCard(title, button, enterLeastSquares, description);
        interpolationPanel.add(leastSquaresCard);


        //***********************************************************************

        //init Spline Card
        title = "Spline";
        description = "Get the interpolation function using Spline method";
        button = "Enter";
        ActionListener enterSpline = e -> {
            doAction = pointsFunction -> {
                try {
                    ArrayList<Polynomial> ans = Interpolation.Spline.getIFAPs(pointsFunction);
                    //create title
                    JLabel interTitle = new JLabel();
                    interTitle.setText("Interpolation answer using Spline : ");
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

                    String[] response = {"OK"};

                    JOptionPane.showOptionDialog(null, contentPanel, "Interpolation", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon, response, response[0]);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? "Invalid inputs" : ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
        cardsPanel.setBackground(new Color(100, 100, 100));
        //***********************************************************************

        //init Rectangular Card
        String title = "Rectangular";
        String description = "Calculates the integral using the rectangular method";
        String button = "Enter";
        ActionListener enterRect = e -> {
            doAction = pointsFunction -> {
                try {

                    BigDecimal a = function.getXp().get(0);
                    BigDecimal b = function.getXp().get(function.getXp().size() - 1);
                    int n = function.getXp().size() - 1;
                    BigDecimal ans = Integral.getRect(function, a, b, n);

                    //create title
                    JLabel interTitle = new JLabel();
                    interTitle.setText("Integration answer using Rectangular method : ");
                    interTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    //create ans scrolled
                    JTextArea polyAns = new JTextArea();
                    polyAns.append("Answer : ");
                    polyAns.append(String.valueOf(fixAccuracy(ans)));
                    polyAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    polyAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(polyAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(100, 50));

                    //create content panel
                    JPanel contentPanel = new JPanel(new GridLayout(2, 1));
                    contentPanel.add(interTitle);
                    contentPanel.add(polyAnsScrollPane);

                    String[] response = {"OK"};

                    JOptionPane.showOptionDialog(null, contentPanel, "Integration", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon, response, response[0]);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? "Invalid inputs" : ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            };
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel rectCard = createCard(title, description, button, enterRect);
        cardsPanel.add(rectCard);

        //***********************************************************************

        //init Trapezoidal Card
        title = "Trapezoidal";
        description = "Calculates the integral using the trapezoidal method";
        button = "Enter";
        ActionListener enterTraps = e -> {
            doAction = pointsFunction -> {
                try {

                    BigDecimal a = function.getXp().get(0);
                    BigDecimal b = function.getXp().get(function.getXp().size() - 1);
                    int n = function.getXp().size() - 1;
                    BigDecimal ans = Integral.getTraps(function, a, b, n);

                    //create title
                    JLabel interTitle = new JLabel();
                    interTitle.setText("Integration answer using Trapezoidal method : ");
                    interTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    //create ans scrolled
                    JTextArea polyAns = new JTextArea();
                    polyAns.append("Answer : ");
                    polyAns.append(String.valueOf(fixAccuracy(ans)));
                    polyAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    polyAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(polyAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(100, 50));

                    //create content panel
                    JPanel contentPanel = new JPanel(new GridLayout(2, 1));
                    contentPanel.add(interTitle);
                    contentPanel.add(polyAnsScrollPane);

                    String[] response = {"OK"};

                    JOptionPane.showOptionDialog(null, contentPanel, "Integration", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon, response, response[0]);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? "Invalid inputs" : ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            };
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel trapsCard = createCard(title, description, button, enterTraps);
        cardsPanel.add(trapsCard);

        //***********************************************************************

        //init Simpson 1/3 Card
        title = "Simpson 1/3";
        description = "Calculates the integral using Simpson's 1/3 method";
        button = "Enter";
        ActionListener enterSimpson3 = e -> {
            doAction = pointsFunction -> {
                try {

                    BigDecimal a = function.getXp().get(0);
                    BigDecimal b = function.getXp().get(function.getXp().size() - 1);
                    int n = function.getXp().size() - 1;
                    BigDecimal ans = Integral.getSimpson3(function, a, b, n);

                    //create title
                    JLabel interTitle = new JLabel();
                    interTitle.setText("Integration answer using Simpson's 1/3 method : ");
                    interTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    //create ans scrolled
                    JTextArea polyAns = new JTextArea();
                    polyAns.append("Answer : ");
                    polyAns.append(String.valueOf(fixAccuracy(ans)));
                    polyAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    polyAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(polyAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(100, 50));

                    //create content panel
                    JPanel contentPanel = new JPanel(new GridLayout(2, 1));
                    contentPanel.add(interTitle);
                    contentPanel.add(polyAnsScrollPane);

                    String[] response = {"OK"};

                    JOptionPane.showOptionDialog(null, contentPanel, "Integration", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon, response, response[0]);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? "Invalid inputs" : ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            };
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel simpson3Card = createCard(title, description, button, enterSimpson3);
        cardsPanel.add(simpson3Card);

        //***********************************************************************

        //init Simpson 3/8 Card
        title = "Simpson 3/8";
        description = "Calculates the integral using Simpson's 3/8 method";
        button = "Enter";
        ActionListener enterSimpson8 = e -> {
            doAction = pointsFunction -> {
                try {

                    BigDecimal a = function.getXp().get(0);
                    BigDecimal b = function.getXp().get(function.getXp().size() - 1);
                    int n = function.getXp().size() - 1;
                    BigDecimal ans = Integral.getSimpson8(function, a, b, n);

                    //create title
                    JLabel interTitle = new JLabel();
                    interTitle.setText("Integration answer using Simpson's 3/8 method : ");
                    interTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    //create ans scrolled
                    JTextArea polyAns = new JTextArea();
                    polyAns.append("Answer : ");
                    polyAns.append(String.valueOf(fixAccuracy(ans)));
                    polyAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    polyAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(polyAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(100, 50));

                    //create content panel
                    JPanel contentPanel = new JPanel(new GridLayout(2, 1));
                    contentPanel.add(interTitle);
                    contentPanel.add(polyAnsScrollPane);

                    String[] response = {"OK"};

                    JOptionPane.showOptionDialog(null, contentPanel, "Integration", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon, response, response[0]);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? "Invalid inputs" : ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            };
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel simpson8Card = createCard(title, description, button, enterSimpson8);
        cardsPanel.add(simpson8Card);

        //***********************************************************************

        //init Paul Card
        title = "Paul";
        description = "Calculates the integral using Paul's method";
        button = "Enter";
        ActionListener enterPaul = e -> {
            doAction = pointsFunction -> {
                try {

                    BigDecimal a = function.getXp().get(0);
                    BigDecimal b = function.getXp().get(function.getXp().size() - 1);
                    int n = function.getXp().size() - 1;
                    BigDecimal ans = Integral.getPaul(function, a, b, n);

                    //create title
                    JLabel interTitle = new JLabel();
                    interTitle.setText("Integration answer using Paul's method : ");
                    interTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    //create ans scrolled
                    JTextArea polyAns = new JTextArea();
                    polyAns.append("Answer : ");
                    polyAns.append(String.valueOf(fixAccuracy(ans)));
                    polyAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    polyAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(polyAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(100, 50));

                    //create content panel
                    JPanel contentPanel = new JPanel(new GridLayout(2, 1));
                    contentPanel.add(interTitle);
                    contentPanel.add(polyAnsScrollPane);

                    String[] response = {"OK"};

                    JOptionPane.showOptionDialog(null, contentPanel, "Integration", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon, response, response[0]);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? "Invalid inputs" : ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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

        //***********************************************************************

        //init Lagrange Card
        String title = "Lagrange";
        String description = "Get Differential Function As Polynomial using Lagrange";
        String button = "Enter";
        ActionListener enterLagrange = e -> {
            doAction = pointsFunction -> {
                try {
                    Polynomial ans = Differentiation.Lagrange.getDFAP(pointsFunction);

                    //create answer title
                    JLabel interTitle = new JLabel();
                    interTitle.setText("Differentiation answer using Lagrange : ");
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

                    String[] response = {"Cancel", "Continue with Polynomial"};

                    int feed = JOptionPane.showOptionDialog(null, contentPanel, "Differentiation", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon, response, response[1]);
                    if (feed == 1) {
                        polynomial = ans;
                        panelsStack.add(polynomialsPanel);
                        updateMainPanel();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? "Invalid inputs" : ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            };
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel lagrangeCard = createCard(title, description, button, enterLagrange);
        differentiationPanel.add(lagrangeCard);

        //***********************************************************************

        //init  Newton-Gregory Forward Subtractions Card
        title = "Newton-Gregory Forward Subtractions";
        description = "Get Differential Function As Polynomial using Newton-Gregory Forward Subtractions";
        button = "Enter";
        ActionListener enterNGFS = e -> {
            doAction = pointsFunction -> {
                try {

                    // inputs : degree
                    JLabel enterDegreeTitle = new JLabel("Enter degree of the required polynomial : ");
                    enterDegreeTitle.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
                    enterDegreeTitle.setHorizontalAlignment(SwingConstants.LEFT);

                    SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 100, 1);
                    JSpinner enterDegreeSp = new JSpinner(spinnerModel);
                    JSpinner.NumberEditor editor = (JSpinner.NumberEditor) enterDegreeSp.getEditor();
                    editor.getTextField().setColumns(2); // Adjust the width as needed

                    JPanel enterDegree = new JPanel();
                    enterDegree.setLayout(new FlowLayout(FlowLayout.LEFT));
                    enterDegree.add(enterDegreeTitle, BorderLayout.WEST);
                    enterDegree.add(enterDegreeSp, BorderLayout.CENTER);
                    //enterDegree.setBorder(BorderFactory.createEmptyBorder(10,0,-10,0));

                    // inputs : rank
                    JLabel enterRankTitle = new JLabel("Enter rank of the required differentiation : ");
                    enterRankTitle.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
                    enterRankTitle.setHorizontalAlignment(SwingConstants.LEFT);

                    SpinnerNumberModel spinnerModelRank = new SpinnerNumberModel(1, 1, 100, 1);
                    JSpinner enterRankSp = new JSpinner(spinnerModelRank);
                    JSpinner.NumberEditor editorRank = (JSpinner.NumberEditor) enterRankSp.getEditor();
                    editorRank.getTextField().setColumns(2); // Adjust the width as needed

                    JPanel enterRank = new JPanel();
                    enterRank.setLayout(new FlowLayout(FlowLayout.LEFT));
                    enterRank.add(enterRankTitle, BorderLayout.WEST);
                    enterRank.add(enterRankSp, BorderLayout.CENTER);
                    //enterRank.setBorder(BorderFactory.createEmptyBorder(10,0,-10,0));

                    JPanel inputs = new JPanel(new GridLayout(2, 1));
                    inputs.add(enterDegree);
                    inputs.add(enterRank);

                    JButton solveButton = new JButton("Solve");
                    solveButton.setFocusPainted(false);
                    Color customGreen = new Color(34, 139, 34);  // RGB values for green color
                    solveButton.setBackground(customGreen);
                    solveButton.setForeground(Color.white);  // Set the text color to white for better visibility

                    JButton cancelButton = new JButton("Cancel");
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

                    JOptionPane.showOptionDialog(null, inputs, "Differentiation", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon64, buttons, null);
                    if (cancelPressed[0]) return;


                    Polynomial ans = Differentiation.Newton_GregoryForwardSubtractions.getIFAP(pointsFunction, degree[0], rank[0]);

                    JLabel interTitle = new JLabel();
                    interTitle.setText("Differentiation answer using Newton-Gregory Forward Subtraction : ");
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


                    String[] response = {"Cancel", "Continue with Polynomial"};

                    int feed = JOptionPane.showOptionDialog(null, contentPanel, "Differentiation", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon, response, response[1]);
                    if (feed == 1) {
                        polynomial = ans;
                        panelsStack.add(polynomialsPanel);
                        updateMainPanel();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? "Invalid inputs" : ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            };
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel NGFSCard = createCard(title, description, button, enterNGFS);
        differentiationPanel.add(NGFSCard);

        //***********************************************************************

        //init Newton-Gregory Backward Subtractions Card
        title = "Newton-Gregory Backward Subtractions";
        description = "Get Differential Function As Polynomial using Newton-Gregory Backward Subtractions";
        button = "Enter";
        ActionListener enterNGBS = e -> {
            doAction = pointsFunction -> {
                try {

                    // inputs : degree
                    JLabel enterDegreeTitle = new JLabel("Enter degree of the required polynomial : ");
                    enterDegreeTitle.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
                    enterDegreeTitle.setHorizontalAlignment(SwingConstants.LEFT);

                    SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 100, 1);
                    JSpinner enterDegreeSp = new JSpinner(spinnerModel);
                    JSpinner.NumberEditor editor = (JSpinner.NumberEditor) enterDegreeSp.getEditor();
                    editor.getTextField().setColumns(2); // Adjust the width as needed

                    JPanel enterDegree = new JPanel();
                    enterDegree.setLayout(new FlowLayout(FlowLayout.LEFT));
                    enterDegree.add(enterDegreeTitle, BorderLayout.WEST);
                    enterDegree.add(enterDegreeSp, BorderLayout.CENTER);
                    //enterDegree.setBorder(BorderFactory.createEmptyBorder(10,0,-10,0));

                    // inputs : rank
                    JLabel enterRankTitle = new JLabel("Enter rank of the required differentiation : ");
                    enterRankTitle.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
                    enterRankTitle.setHorizontalAlignment(SwingConstants.LEFT);

                    SpinnerNumberModel spinnerModelRank = new SpinnerNumberModel(1, 1, 100, 1);
                    JSpinner enterRankSp = new JSpinner(spinnerModelRank);
                    JSpinner.NumberEditor editorRank = (JSpinner.NumberEditor) enterRankSp.getEditor();
                    editorRank.getTextField().setColumns(2); // Adjust the width as needed

                    JPanel enterRank = new JPanel();
                    enterRank.setLayout(new FlowLayout(FlowLayout.LEFT));
                    enterRank.add(enterRankTitle, BorderLayout.WEST);
                    enterRank.add(enterRankSp, BorderLayout.CENTER);
                    //enterRank.setBorder(BorderFactory.createEmptyBorder(10,0,-10,0));

                    JPanel inputs = new JPanel(new GridLayout(2, 1));
                    inputs.add(enterDegree);
                    inputs.add(enterRank);

                    JButton solveButton = new JButton("Solve");
                    solveButton.setFocusPainted(false);
                    Color customGreen = new Color(34, 139, 34);  // RGB values for green color
                    solveButton.setBackground(customGreen);
                    solveButton.setForeground(Color.white);  // Set the text color to white for better visibility

                    JButton cancelButton = new JButton("Cancel");
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

                    JOptionPane.showOptionDialog(null, inputs, "Differentiation", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon64, buttons, null);
                    if (cancelPressed[0]) return;


                    Polynomial ans = Differentiation.Newton_GregoryBackwardSubtractions.getIFAP(pointsFunction, degree[0], rank[0]);

                    JLabel interTitle = new JLabel();
                    interTitle.setText("Differentiation answer using Newton-Gregory Backward Subtraction : ");
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


                    String[] response = {"Cancel", "Continue with Polynomial"};

                    int feed = JOptionPane.showOptionDialog(null, contentPanel, "Differentiation", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon, response, response[1]);
                    if (feed == 1) {
                        polynomial = ans;
                        panelsStack.add(polynomialsPanel);
                        updateMainPanel();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? "Invalid inputs" : ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            };
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel NGBSCard = createCard(title, description, button, enterNGBS);
        differentiationPanel.add(NGBSCard);


        //***********************************************************************

        //init Central,Forward,Backward Subtractions Card
        title = "Central,Forward,Backward Subtractions";
        description = "Calculate the differential function of a specified value using Central/Forward/Backward Subtractions";
        button = "Enter";
        ActionListener enterCFBS = e -> {
            doAction = pointsFunction -> {
                try {

                    JLabel enterXLabel = new JLabel("Enter x value to get differentiation at it : ");
                    enterXLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));

                    JTextField enterXField = new JTextField();
                    enterXField.setColumns(25);

                    JLabel enterMethodLabel = new JLabel("Choose a method : ");
                    enterMethodLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));

                    JComboBox<String> enterMethodCombo = new JComboBox<>();
                    enterMethodCombo.addItem("Backward Subtractions");
                    enterMethodCombo.addItem("Central Subtractions");
                    enterMethodCombo.addItem("Forward Subtractions");

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
                    gbc.insets = new Insets(5, -150, 5, 5);
                    inputs.add(enterMethodLabel, gbc);
                    gbc.gridx++;
                    inputs.add(enterMethodCombo, gbc);

                    JButton solveButton = new JButton("Solve");
                    solveButton.setFocusPainted(false);
                    Color customGreen = new Color(34, 139, 34);  // RGB values for green color
                    solveButton.setBackground(customGreen);
                    solveButton.setForeground(Color.white);  // Set the text color to white for better visibility
                    solveButton.setEnabled(false);

                    ArrayList<JTextField> fields = new ArrayList<>();
                    fields.add(enterXField);
                    addDocumentListenerToFields(solveButton, fields);

                    JButton cancelButton = new JButton("Cancel");
                    cancelButton.setFocusPainted(false);

                    Object[] buttons = {cancelButton, solveButton};

                    final boolean[] cancelPressed = {false};
                    cancelButton.addActionListener(cancel -> {
                        cancelPressed[0] = true;
                        Window optionDialog = SwingUtilities.getWindowAncestor(inputs);
                        optionDialog.dispose();
                    });

                    final BigDecimal[] x = {new BigDecimal(0)};
                    final String[] method = {""};
                    solveButton.addActionListener(solve -> {
                        Window optionDialog = SwingUtilities.getWindowAncestor(inputs);
                        optionDialog.dispose();
                        x[0] = EvaluateString.evaluate(enterXField.getText());
                        method[0] = String.valueOf(enterMethodCombo.getSelectedItem());
                    });

                    JOptionPane.showOptionDialog(null, inputs, "Differentiation", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon64, buttons, null);
                    if (cancelPressed[0]) return;

                    BigDecimal ans;

                    switch (method[0]) {
                        case "Backward Subtractions": {
                            ans = Differentiation.Subtractions.Backward.getValueAt(function, x[0]);
                            break;
                        }
                        case "Central Subtractions": {
                            ans = Differentiation.Subtractions.Central.getValueAt(function, x[0]);
                            break;
                        }
                        case "Forward Subtractions": {
                            ans = Differentiation.Subtractions.Forward.getValueAt(function, x[0]);
                            break;
                        }
                        default: {
                            throw new Exception("invalid input");
                        }
                    }


                    JLabel interTitle = new JLabel();
                    interTitle.setText("Differentiation answer using " + method[0] + " : ");
                    interTitle.setFont(new Font(mainFont, Font.PLAIN, 20));


                    JTextArea polyAns = new JTextArea();
                    polyAns.append("Answer : ");
                    polyAns.append(String.valueOf(fixAccuracy(ans)));
                    polyAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    polyAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(polyAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(100, 70));


                    JPanel contentPanel = new JPanel(new GridLayout(2, 1, 0, -20));
                    contentPanel.setBorder(BorderFactory.createEmptyBorder(-20, 0, 0, 0));
                    contentPanel.add(interTitle);
                    contentPanel.add(polyAnsScrollPane);


                    String[] response = {"OK"};

                    JOptionPane.showOptionDialog(null, contentPanel, "Differentiation", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon, response, null);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? "Invalid inputs" : ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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

        //***********************************************************************

        //init Euler Card
        String title = "Euler";
        String description = "Solving a differential equation using the Euler method";
        String button = "Enter";
        ActionListener enterEuler = e -> {
            JLabel eulerTitle = new JLabel("Euler's Method");
            eulerTitle.setFont(new Font(mainFont, Font.BOLD, 25));

            JLabel enterYTitle = new JLabel("Enter Y ' : ");
            enterYTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterYField = new JTextField();
            enterYField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterYField.setPreferredSize(new Dimension(250, 50));

            JLabel enterX0Title = new JLabel("Enter x0 : ");
            enterX0Title.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterX0Field = new JTextField();
            enterX0Field.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterX0Field.setPreferredSize(new Dimension(250, 50));

            JLabel enterY0Title = new JLabel("Enter Y0 : ");
            enterY0Title.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterY0Field = new JTextField();
            enterY0Field.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterY0Field.setPreferredSize(new Dimension(250, 50));

            JLabel enterHTitle = new JLabel("Enter h : ");
            enterHTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterHField = new JTextField();
            enterHField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterHField.setPreferredSize(new Dimension(250, 50));

            JLabel enterXTitle = new JLabel("Enter x : ");
            enterXTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterXField = new JTextField();
            enterXField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterXField.setPreferredSize(new Dimension(250, 50));

            JPanel contentPanel = new JPanel(new GridLayout(6, 2, -150, 0));
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

            JButton solveButton = new JButton("Solve");
            solveButton.setFocusPainted(false);
            solveButton.setPreferredSize(new Dimension(80, 40));
            solveButton.setFont(new Font("Arial", Font.PLAIN, 17));
            Color customGreen = new Color(34, 139, 34);  // RGB values for green color
            solveButton.setBackground(customGreen);
            solveButton.setForeground(Color.white);  // Set the text color to white for better visibility
            solveButton.setEnabled(false);

            JButton cancelButton = new JButton("Cancel");
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
                    difeqTitle.setText("Differential Equation solution using Euler's method : ");
                    difeqTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    //create ans scrolled
                    JTextArea difeqAns = new JTextArea();
                    difeqAns.append("Answer : ");
                    difeqAns.append(String.valueOf(fixAccuracy(ans)));
                    difeqAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    difeqAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(difeqAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(100, 50));

                    //create content panel
                    JPanel ansContentPanel = new JPanel(new GridLayout(2, 1));
                    ansContentPanel.add(difeqTitle);
                    ansContentPanel.add(polyAnsScrollPane);

                    JOptionPane.showConfirmDialog(null, ansContentPanel, "Differential Equations", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? "Invalid inputs" : ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            JOptionPane.showOptionDialog(null, contentPanel, "Differential Equations", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon128, buttons, null);

        };
        JPanel eulerCard = createCard(title, description, button, enterEuler);
        differentialEquationsPanel.add(eulerCard);

        //***********************************************************************

        //init Taylor Card
        title = "Taylor";
        description = "Solving a differential equation using the Taylor method";
        button = "Enter";
        ActionListener enterTaylor = e -> {
            JLabel heinTitleLabel = new JLabel("Taylor's Method");
            heinTitleLabel.setFont(new Font(mainFont, Font.BOLD, 25));
            JPanel heinTitle = new JPanel();
            heinTitle.add(heinTitleLabel);
            heinTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

            // inputs : n
            JLabel enterNTitle = new JLabel("Enter n – The number of terms in the Taylor series :");
            enterNTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            enterNTitle.setHorizontalAlignment(SwingConstants.LEFT);

            SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 100, 1);
            JSpinner enterNSp = new JSpinner(spinnerModel);
            JSpinner.NumberEditor editor = (JSpinner.NumberEditor) enterNSp.getEditor();
            editor.getTextField().setColumns(2); // Adjust the width as needed

            JButton confirmButton = new JButton("Confirm");
            confirmButton.setPreferredSize(new Dimension(80, 30));

            JPanel enterN = new JPanel();
            enterN.setLayout(new FlowLayout(FlowLayout.LEFT));
            enterN.add(enterNTitle, BorderLayout.WEST);
            enterN.add(enterNSp, BorderLayout.CENTER);
            enterN.add(confirmButton, BorderLayout.EAST);


            // input
            JLabel enterDivsTitle = new JLabel("Enter Derivatives : ");
            enterDivsTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            enterDivsTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0)); // Adjust the top and bottom padding

            JScrollPane enterDivsScroll = new JScrollPane();
            enterDivsScroll.setPreferredSize(new Dimension(500, 100));


            // inputs panel
            JPanel inputsPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.insets = new Insets(5, 0, 5, 0); // Add vertical spacing between components

            inputsPanel.add(enterN, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            gbc.gridwidth = 2;
            inputsPanel.add(enterDivsTitle, gbc);

            gbc.gridy++;
            inputsPanel.add(enterDivsScroll, gbc);


            JLabel enterX0Title = new JLabel("Enter x0 : ");
            enterX0Title.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterX0Field = new JTextField();
            enterX0Field.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterX0Field.setPreferredSize(new Dimension(200, 50));

            JLabel enterY0Title = new JLabel("Enter Y0 : ");
            enterY0Title.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterY0Field = new JTextField();
            enterY0Field.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterY0Field.setPreferredSize(new Dimension(200, 50));

            JLabel enterHTitle = new JLabel("Enter h : ");
            enterHTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterHField = new JTextField();
            enterHField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterHField.setPreferredSize(new Dimension(200, 50));

            JLabel enterXTitle = new JLabel("Enter x : ");
            enterXTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterXField = new JTextField();
            enterXField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterXField.setPreferredSize(new Dimension(200, 50));

            JPanel contentPanel = new JPanel();
            GridBagLayout layout = new GridBagLayout();
            contentPanel.setLayout(layout);

            GridBagConstraints constraints = new GridBagConstraints();
            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.insets = new Insets(5, -130, 5, 10); // Adjust the padding as needed


            constraints.gridx = 0;
            constraints.gridy = 0;
            contentPanel.add(enterX0Title, constraints);

            constraints.gridx = 1;
            constraints.weightx = 1.0; // Make the text field expand horizontally
            contentPanel.add(enterX0Field, constraints);

            constraints.gridy++;

            constraints.gridx = 0;
            contentPanel.add(enterY0Title, constraints);

            constraints.gridx = 1;
            constraints.weightx = 1.0; // Make the text field expand horizontally
            contentPanel.add(enterY0Field, constraints);

            constraints.gridy++;

            constraints.gridx = 0;
            contentPanel.add(enterHTitle, constraints);

            constraints.gridx = 1;
            constraints.weightx = 1.0; // Make the text field expand horizontally
            contentPanel.add(enterHField, constraints);

            constraints.gridy++;

            constraints.gridx = 0;
            contentPanel.add(enterXTitle, constraints);

            constraints.gridx = 1;
            constraints.weightx = 1.0; // Make the text field expand horizontally
            contentPanel.add(enterXField, constraints);

            contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));

            JPanel showPanel = new JPanel(new BorderLayout());
            showPanel.add(heinTitle, BorderLayout.NORTH);
            showPanel.add(inputsPanel, BorderLayout.CENTER);
            showPanel.add(contentPanel, BorderLayout.SOUTH);

            JButton solveButton = new JButton("Solve");
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

            JButton cancelButton = new JButton("Cancel");
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
                    difeqTitle.setText("Differential Equation solution using Taylor's method : ");
                    difeqTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    //create ans scrolled
                    JTextArea difeqAns = new JTextArea();
                    difeqAns.append("Answer : ");
                    difeqAns.append(String.valueOf(fixAccuracy(ans)));
                    difeqAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    difeqAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(difeqAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(100, 50));

                    //create content panel
                    JPanel ansContentPanel = new JPanel(new GridLayout(2, 1));
                    ansContentPanel.add(difeqTitle);
                    ansContentPanel.add(polyAnsScrollPane);

                    JOptionPane.showConfirmDialog(null, ansContentPanel, "Differential Equations", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? "Invalid inputs" : ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            JOptionPane.showOptionDialog(null, showPanel, "Differential Equations", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon128, buttons, null);
        };
        JPanel taylorCard = createCard(title, description, button, enterTaylor);
        differentialEquationsPanel.add(taylorCard);

        //***********************************************************************

        //init Modified Euler Card
        title = "Modified Euler";
        description = "Solving a differential equation using the Modified Euler method";
        button = "Enter";
        ActionListener enterModifiedEuler = e -> {
            JLabel modEulerTitleLabel = new JLabel("MidPoint : Modified Euler's Method");
            modEulerTitleLabel.setFont(new Font(mainFont, Font.BOLD, 25));
            JPanel modEulerTitle = new JPanel();
            modEulerTitle.add(modEulerTitleLabel);
            modEulerTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

            JLabel enterYTitle = new JLabel("Enter Y ' : ");
            enterYTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterYField = new JTextField();
            enterYField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterYField.setPreferredSize(new Dimension(250, 50));

            JLabel enterX0Title = new JLabel("Enter x0 : ");
            enterX0Title.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterX0Field = new JTextField();
            enterX0Field.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterX0Field.setPreferredSize(new Dimension(250, 50));

            JLabel enterY0Title = new JLabel("Enter Y0 : ");
            enterY0Title.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterY0Field = new JTextField();
            enterY0Field.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterY0Field.setPreferredSize(new Dimension(250, 50));

            JLabel enterHTitle = new JLabel("Enter h : ");
            enterHTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterHField = new JTextField();
            enterHField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterHField.setPreferredSize(new Dimension(250, 50));

            JLabel enterXTitle = new JLabel("Enter x : ");
            enterXTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterXField = new JTextField();
            enterXField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterXField.setPreferredSize(new Dimension(250, 50));

            JPanel contentPanel = new JPanel(new GridLayout(5, 2, -180, 0));
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

            JPanel showPanel = new JPanel(new BorderLayout());
            showPanel.add(modEulerTitle, BorderLayout.NORTH);
            showPanel.add(contentPanel, BorderLayout.CENTER);

            JButton solveButton = new JButton("Solve");
            solveButton.setFocusPainted(false);
            solveButton.setPreferredSize(new Dimension(80, 40));
            solveButton.setFont(new Font("Arial", Font.PLAIN, 17));
            Color customGreen = new Color(34, 139, 34);  // RGB values for green color
            solveButton.setBackground(customGreen);
            solveButton.setForeground(Color.white);  // Set the text color to white for better visibility
            solveButton.setEnabled(false);

            JButton cancelButton = new JButton("Cancel");
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
                    difeqTitle.setText("Differential Equation solution using Modified Euler's method : ");
                    difeqTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    //create ans scrolled
                    JTextArea difeqAns = new JTextArea();
                    difeqAns.append("Answer : ");
                    difeqAns.append(String.valueOf(fixAccuracy(ans)));
                    difeqAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    difeqAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(difeqAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(100, 50));

                    //create content panel
                    JPanel ansContentPanel = new JPanel(new GridLayout(2, 1));
                    ansContentPanel.add(difeqTitle);
                    ansContentPanel.add(polyAnsScrollPane);

                    JOptionPane.showConfirmDialog(null, ansContentPanel, "Differential Equations", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? "Invalid inputs" : ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            JOptionPane.showOptionDialog(null, showPanel, "Differential Equations", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon128, buttons, null);
        };
        JPanel modifiedEulerCard = createCard(title, description, button, enterModifiedEuler);
        differentialEquationsPanel.add(modifiedEulerCard);


        //***********************************************************************

        //init Hein Card
        title = "Hein";
        description = "Solving a differential equation using the Hein method";
        button = "Enter";
        ActionListener enterHein = e -> {
            JLabel heinTitleLabel = new JLabel("MidPoint : Hein's Method");
            heinTitleLabel.setFont(new Font(mainFont, Font.BOLD, 25));
            JPanel heinTitle = new JPanel();
            heinTitle.add(heinTitleLabel);
            heinTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

            JLabel enterYTitle = new JLabel("Enter Y ' : ");
            enterYTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterYField = new JTextField();
            enterYField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterYField.setPreferredSize(new Dimension(200, 50));

            JLabel enterX0Title = new JLabel("Enter x0 : ");
            enterX0Title.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterX0Field = new JTextField();
            enterX0Field.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterX0Field.setPreferredSize(new Dimension(200, 50));

            JLabel enterY0Title = new JLabel("Enter Y0 : ");
            enterY0Title.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterY0Field = new JTextField();
            enterY0Field.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterY0Field.setPreferredSize(new Dimension(200, 50));

            JLabel enterHTitle = new JLabel("Enter h : ");
            enterHTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterHField = new JTextField();
            enterHField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterHField.setPreferredSize(new Dimension(200, 50));

            JLabel enterXTitle = new JLabel("Enter x : ");
            enterXTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterXField = new JTextField();
            enterXField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterXField.setPreferredSize(new Dimension(200, 50));

            JPanel contentPanel = new JPanel(new GridLayout(5, 2, -100, 0));
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

            JPanel showPanel = new JPanel(new BorderLayout());
            showPanel.add(heinTitle, BorderLayout.NORTH);
            showPanel.add(contentPanel, BorderLayout.CENTER);

            JButton solveButton = new JButton("Solve");
            solveButton.setFocusPainted(false);
            solveButton.setPreferredSize(new Dimension(80, 40));
            solveButton.setFont(new Font("Arial", Font.PLAIN, 17));
            Color customGreen = new Color(34, 139, 34);  // RGB values for green color
            solveButton.setBackground(customGreen);
            solveButton.setForeground(Color.white);  // Set the text color to white for better visibility
            solveButton.setEnabled(false);

            JButton cancelButton = new JButton("Cancel");
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
                    difeqTitle.setText("Differential Equation solution using Hein's method : ");
                    difeqTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    //create ans scrolled
                    JTextArea difeqAns = new JTextArea();
                    difeqAns.append("Answer : ");
                    difeqAns.append(String.valueOf(fixAccuracy(ans)));
                    difeqAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    difeqAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(difeqAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(100, 50));

                    //create content panel
                    JPanel ansContentPanel = new JPanel(new GridLayout(2, 1));
                    ansContentPanel.add(difeqTitle);
                    ansContentPanel.add(polyAnsScrollPane);

                    JOptionPane.showConfirmDialog(null, ansContentPanel, "Differential Equations", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? "Invalid inputs" : ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            JOptionPane.showOptionDialog(null, showPanel, "Differential Equations", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon128, buttons, null);
        };
        JPanel heinCard = createCard(title, description, button, enterHein);
        differentialEquationsPanel.add(heinCard);

        //***********************************************************************

        //init Ralston Card
        title = "Ralston";
        description = "Solving a differential equation using the Ralston method";
        button = "Enter";
        ActionListener enterRalston = e -> {
            JLabel ralstonTitleLabel = new JLabel("MidPoint : Ralston's Method");
            ralstonTitleLabel.setFont(new Font(mainFont, Font.BOLD, 25));
            JPanel ralstonTitle = new JPanel();
            ralstonTitle.add(ralstonTitleLabel);
            ralstonTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

            JLabel enterYTitle = new JLabel("Enter Y ' : ");
            enterYTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterYField = new JTextField();
            enterYField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterYField.setPreferredSize(new Dimension(200, 50));

            JLabel enterX0Title = new JLabel("Enter x0 : ");
            enterX0Title.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterX0Field = new JTextField();
            enterX0Field.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterX0Field.setPreferredSize(new Dimension(200, 50));

            JLabel enterY0Title = new JLabel("Enter Y0 : ");
            enterY0Title.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterY0Field = new JTextField();
            enterY0Field.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterY0Field.setPreferredSize(new Dimension(200, 50));

            JLabel enterHTitle = new JLabel("Enter h : ");
            enterHTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterHField = new JTextField();
            enterHField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterHField.setPreferredSize(new Dimension(200, 50));

            JLabel enterXTitle = new JLabel("Enter x : ");
            enterXTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterXField = new JTextField();
            enterXField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterXField.setPreferredSize(new Dimension(200, 50));

            JPanel contentPanel = new JPanel(new GridLayout(5, 2, -100, 0));
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

            JPanel showPanel = new JPanel(new BorderLayout());
            showPanel.add(ralstonTitle, BorderLayout.NORTH);
            showPanel.add(contentPanel, BorderLayout.CENTER);

            JButton solveButton = new JButton("Solve");
            solveButton.setFocusPainted(false);
            solveButton.setPreferredSize(new Dimension(80, 40));
            solveButton.setFont(new Font("Arial", Font.PLAIN, 17));
            Color customGreen = new Color(34, 139, 34);  // RGB values for green color
            solveButton.setBackground(customGreen);
            solveButton.setForeground(Color.white);  // Set the text color to white for better visibility
            solveButton.setEnabled(false);

            JButton cancelButton = new JButton("Cancel");
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
                    difeqTitle.setText("Differential Equation solution using Ralston's method : ");
                    difeqTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    //create ans scrolled
                    JTextArea difeqAns = new JTextArea();
                    difeqAns.append("Answer : ");
                    difeqAns.append(String.valueOf(fixAccuracy(ans)));
                    difeqAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    difeqAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(difeqAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(100, 50));

                    //create content panel
                    JPanel ansContentPanel = new JPanel(new GridLayout(2, 1));
                    ansContentPanel.add(difeqTitle);
                    ansContentPanel.add(polyAnsScrollPane);

                    JOptionPane.showConfirmDialog(null, ansContentPanel, "Differential Equations", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? "Invalid inputs" : ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            JOptionPane.showOptionDialog(null, showPanel, "Differential Equations", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon128, buttons, null);
        };
        JPanel ralstonCard = createCard(title, description, button, enterRalston);
        differentialEquationsPanel.add(ralstonCard);

        //***********************************************************************

        //init Runge_Kutta Card
        title = "Runge-Kutta";
        description = "Solving a differential equation using the Runge-Kutta method";
        button = "Enter";
        ActionListener enterRunge_Kutta = e -> {
            JLabel rangeTitleLabel = new JLabel("Runge-Kutta Method");
            rangeTitleLabel.setFont(new Font(mainFont, Font.BOLD, 25));
            JPanel rangeTitle = new JPanel();
            rangeTitle.add(rangeTitleLabel);
            rangeTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

            JLabel enterYTitle = new JLabel("Enter Y ' : ");
            enterYTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterYField = new JTextField();
            enterYField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterYField.setPreferredSize(new Dimension(200, 50));

            JLabel enterX0Title = new JLabel("Enter x0 : ");
            enterX0Title.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterX0Field = new JTextField();
            enterX0Field.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterX0Field.setPreferredSize(new Dimension(200, 50));

            JLabel enterY0Title = new JLabel("Enter Y0 : ");
            enterY0Title.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterY0Field = new JTextField();
            enterY0Field.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterY0Field.setPreferredSize(new Dimension(200, 50));

            JLabel enterHTitle = new JLabel("Enter h : ");
            enterHTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterHField = new JTextField();
            enterHField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterHField.setPreferredSize(new Dimension(200, 50));

            JLabel enterXTitle = new JLabel("Enter x : ");
            enterXTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterXField = new JTextField();
            enterXField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterXField.setPreferredSize(new Dimension(200, 50));

            JPanel contentPanel = new JPanel(new GridLayout(5, 2, -100, 0));
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

            JPanel showPanel = new JPanel(new BorderLayout());
            showPanel.add(rangeTitle, BorderLayout.NORTH);
            showPanel.add(contentPanel, BorderLayout.CENTER);

            JButton solveButton = new JButton("Solve");
            solveButton.setFocusPainted(false);
            solveButton.setPreferredSize(new Dimension(80, 40));
            solveButton.setFont(new Font("Arial", Font.PLAIN, 17));
            Color customGreen = new Color(34, 139, 34);  // RGB values for green color
            solveButton.setBackground(customGreen);
            solveButton.setForeground(Color.white);  // Set the text color to white for better visibility
            solveButton.setEnabled(false);

            JButton cancelButton = new JButton("Cancel");
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
                    difeqTitle.setText("Differential Equation solution using Runge-Kutta method : ");
                    difeqTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    //create ans scrolled
                    JTextArea difeqAns = new JTextArea();
                    difeqAns.append("Answer : ");
                    difeqAns.append(String.valueOf(fixAccuracy(ans)));
                    difeqAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    difeqAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(difeqAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(100, 50));

                    //create content panel
                    JPanel ansContentPanel = new JPanel(new GridLayout(2, 1));
                    ansContentPanel.add(difeqTitle);
                    ansContentPanel.add(polyAnsScrollPane);

                    JOptionPane.showConfirmDialog(null, ansContentPanel, "Differential Equations", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? "Invalid inputs" : ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            JOptionPane.showOptionDialog(null, showPanel, "Differential Equations", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon128, buttons, null);
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
        nonLinearEquationsPanel.setName("Non-Linear Equations");
        nonLinearEquationsPanel.setPreferredSize(mainFrame.getSize());
        nonLinearEquationsPanel.setBackground(new Color(100, 100, 100));
        GridLayout startLayout = new GridLayout(3, 2);
        startLayout.setHgap(5);
        startLayout.setVgap(5);
        nonLinearEquationsPanel.setLayout(startLayout);

        //***********************************************************************

        //init Bisection Card
        String title = "Bisection";
        String description = "Solving non-linear equations using the Bisection method";
        String button = "Enter";
        ActionListener enterBisection = e -> {
            JLabel bisTitleLabel = new JLabel("Bisection Method");
            bisTitleLabel.setFont(new Font(mainFont, Font.BOLD, 25));
            JPanel bisTitle = new JPanel();
            bisTitle.add(bisTitleLabel);
            bisTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

            JLabel enterFTitle = new JLabel("Enter f(x) : ");
            enterFTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterFField = new JTextField();
            enterFField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterFField.setPreferredSize(new Dimension(200, 50));

            JLabel enterATitle = new JLabel("Enter a : ");
            enterATitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterAField = new JTextField();
            enterAField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterAField.setPreferredSize(new Dimension(200, 50));

            JLabel enterBTitle = new JLabel("Enter b : ");
            enterBTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterBField = new JTextField();
            enterBField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterBField.setPreferredSize(new Dimension(200, 50));

            JLabel enterETitle = new JLabel("Enter e : ");
            enterETitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterEField = new JTextField();
            enterEField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterEField.setPreferredSize(new Dimension(200, 50));


            JPanel contentPanel = new JPanel(new GridLayout(4, 2, -100, 0));
            contentPanel.add(enterFTitle);
            contentPanel.add(enterFField);
            contentPanel.add(enterATitle);
            contentPanel.add(enterAField);
            contentPanel.add(enterBTitle);
            contentPanel.add(enterBField);
            contentPanel.add(enterETitle);
            contentPanel.add(enterEField);

            JPanel showPanel = new JPanel(new BorderLayout());
            showPanel.add(bisTitle, BorderLayout.NORTH);
            showPanel.add(contentPanel, BorderLayout.CENTER);

            JButton solveButton = new JButton("Solve");
            solveButton.setFocusPainted(false);
            solveButton.setPreferredSize(new Dimension(80, 40));
            solveButton.setFont(new Font("Arial", Font.PLAIN, 17));
            Color customGreen = new Color(34, 139, 34);  // RGB values for green color
            solveButton.setBackground(customGreen);
            solveButton.setForeground(Color.white);  // Set the text color to white for better visibility
            solveButton.setEnabled(false);

            JButton cancelButton = new JButton("Cancel");
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
                    difeqTitle.setText("Non-Linear Equation solution using Bisection method : ");
                    difeqTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    //create ans scrolled
                    JTextArea difeqAns = new JTextArea();
                    difeqAns.append("Answer : ");
                    difeqAns.append(String.valueOf(fixAccuracy(ans)));
                    difeqAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    difeqAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(difeqAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(100, 50));

                    //create content panel
                    JPanel ansContentPanel = new JPanel(new GridLayout(2, 1));
                    ansContentPanel.add(difeqTitle);
                    ansContentPanel.add(polyAnsScrollPane);

                    JOptionPane.showConfirmDialog(null, ansContentPanel, "Non-Linear Equations", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? "Invalid inputs" : ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            JOptionPane.showOptionDialog(null, showPanel, "Non-Linear Equations", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon128, buttons, null);
        };
        JPanel bisectionCard = createCard(title, description, button, enterBisection);
        nonLinearEquationsPanel.add(bisectionCard);

        //***********************************************************************

        //init False Position Card
        title = "False Position";
        description = "Solving non-linear equations using the False Position method";
        button = "Enter";
        ActionListener enterFalsePosition = e -> {
            JLabel flsTitleLabel = new JLabel("False Position Method");
            flsTitleLabel.setFont(new Font(mainFont, Font.BOLD, 25));
            JPanel flsTitle = new JPanel();
            flsTitle.add(flsTitleLabel);
            flsTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

            JLabel enterFTitle = new JLabel("Enter f(x) : ");
            enterFTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterFField = new JTextField();
            enterFField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterFField.setPreferredSize(new Dimension(200, 50));

            JLabel enterATitle = new JLabel("Enter a : ");
            enterATitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterAField = new JTextField();
            enterAField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterAField.setPreferredSize(new Dimension(200, 50));

            JLabel enterBTitle = new JLabel("Enter b : ");
            enterBTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterBField = new JTextField();
            enterBField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterBField.setPreferredSize(new Dimension(200, 50));

            JLabel enterETitle = new JLabel("Enter e : ");
            enterETitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterEField = new JTextField();
            enterEField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterEField.setPreferredSize(new Dimension(200, 50));


            JPanel contentPanel = new JPanel(new GridLayout(4, 2, -100, 0));
            contentPanel.add(enterFTitle);
            contentPanel.add(enterFField);
            contentPanel.add(enterATitle);
            contentPanel.add(enterAField);
            contentPanel.add(enterBTitle);
            contentPanel.add(enterBField);
            contentPanel.add(enterETitle);
            contentPanel.add(enterEField);

            JPanel showPanel = new JPanel(new BorderLayout());
            showPanel.add(flsTitle, BorderLayout.NORTH);
            showPanel.add(contentPanel, BorderLayout.CENTER);

            JButton solveButton = new JButton("Solve");
            solveButton.setFocusPainted(false);
            solveButton.setPreferredSize(new Dimension(80, 40));
            solveButton.setFont(new Font("Arial", Font.PLAIN, 17));
            Color customGreen = new Color(34, 139, 34);  // RGB values for green color
            solveButton.setBackground(customGreen);
            solveButton.setForeground(Color.white);  // Set the text color to white for better visibility
            solveButton.setEnabled(false);

            JButton cancelButton = new JButton("Cancel");
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
                    difeqTitle.setText("Non-Linear Equation solution using False Position method : ");
                    difeqTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    //create ans scrolled
                    JTextArea difeqAns = new JTextArea();
                    difeqAns.append("Answer : ");
                    difeqAns.append(String.valueOf(fixAccuracy(ans)));
                    difeqAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    difeqAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(difeqAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(100, 50));

                    //create content panel
                    JPanel ansContentPanel = new JPanel(new GridLayout(2, 1));
                    ansContentPanel.add(difeqTitle);
                    ansContentPanel.add(polyAnsScrollPane);

                    JOptionPane.showConfirmDialog(null, ansContentPanel, "Non-Linear Equations", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? "Invalid inputs" : ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            JOptionPane.showOptionDialog(null, showPanel, "Non-Linear Equations", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon128, buttons, null);
        };
        JPanel falsePositionCard = createCard(title, description, button, enterFalsePosition);
        nonLinearEquationsPanel.add(falsePositionCard);

        //***********************************************************************

        //init Secant Card
        title = "Secant";
        description = "Solving non-linear equations using the Secant method";
        button = "Enter";
        ActionListener enterSecant = e -> {
            JLabel secTitleLabel = new JLabel("Secant Method");
            secTitleLabel.setFont(new Font(mainFont, Font.BOLD, 25));
            JPanel secTitle = new JPanel();
            secTitle.add(secTitleLabel);
            secTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

            JLabel enterFTitle = new JLabel("Enter f(x) : ");
            enterFTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterFField = new JTextField();
            enterFField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterFField.setPreferredSize(new Dimension(200, 50));

            JLabel enterX0Title = new JLabel("Enter X0 : ");
            enterX0Title.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterX0Field = new JTextField();
            enterX0Field.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterX0Field.setPreferredSize(new Dimension(200, 50));

            JLabel enterX1Title = new JLabel("Enter X1 : ");
            enterX1Title.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterX1Field = new JTextField();
            enterX1Field.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterX1Field.setPreferredSize(new Dimension(200, 50));

            JLabel enterETitle = new JLabel("Enter e : ");
            enterETitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterEField = new JTextField();
            enterEField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterEField.setPreferredSize(new Dimension(200, 50));


            JPanel contentPanel = new JPanel(new GridLayout(4, 2, -100, 0));
            contentPanel.add(enterFTitle);
            contentPanel.add(enterFField);
            contentPanel.add(enterX0Title);
            contentPanel.add(enterX0Field);
            contentPanel.add(enterX1Title);
            contentPanel.add(enterX1Field);
            contentPanel.add(enterETitle);
            contentPanel.add(enterEField);

            JPanel showPanel = new JPanel(new BorderLayout());
            showPanel.add(secTitle, BorderLayout.NORTH);
            showPanel.add(contentPanel, BorderLayout.CENTER);

            JButton solveButton = new JButton("Solve");
            solveButton.setFocusPainted(false);
            solveButton.setPreferredSize(new Dimension(80, 40));
            solveButton.setFont(new Font("Arial", Font.PLAIN, 17));
            Color customGreen = new Color(34, 139, 34);  // RGB values for green color
            solveButton.setBackground(customGreen);
            solveButton.setForeground(Color.white);  // Set the text color to white for better visibility
            solveButton.setEnabled(false);

            JButton cancelButton = new JButton("Cancel");
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
                    difeqTitle.setText("Non-Linear Equation solution using Secant method : ");
                    difeqTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    //create ans scrolled
                    JTextArea difeqAns = new JTextArea();
                    difeqAns.append("Answer : ");
                    difeqAns.append(String.valueOf(fixAccuracy(ans)));
                    difeqAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    difeqAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(difeqAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(100, 50));

                    //create content panel
                    JPanel ansContentPanel = new JPanel(new GridLayout(2, 1));
                    ansContentPanel.add(difeqTitle);
                    ansContentPanel.add(polyAnsScrollPane);

                    JOptionPane.showConfirmDialog(null, ansContentPanel, "Non-Linear Equations", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? "Invalid inputs" : ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            JOptionPane.showOptionDialog(null, showPanel, "Non-Linear Equations", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon128, buttons, null);
        };
        JPanel secantCard = createCard(title, description, button, enterSecant);
        nonLinearEquationsPanel.add(secantCard);


        //***********************************************************************

        //init Newton_Raphson Card
        title = "Newton-Raphson";
        description = "Solving non-linear equations using the Newton-Raphson method";
        button = "Enter";
        ActionListener enterNewton_Raphson = e -> {
            JLabel newtTitleLabel = new JLabel("Newton-Raphson Method");
            newtTitleLabel.setFont(new Font(mainFont, Font.BOLD, 25));
            JPanel newtTitle = new JPanel();
            newtTitle.add(newtTitleLabel);
            newtTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

            JLabel enterFTitle = new JLabel("Enter f(x) : ");
            enterFTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterFField = new JTextField();
            enterFField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterFField.setPreferredSize(new Dimension(200, 50));

            JLabel enterDFTitle = new JLabel("Enter f '(x) : ");
            enterDFTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterDFField = new JTextField();
            enterDFField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterDFField.setPreferredSize(new Dimension(200, 50));

            JLabel enterX0Title = new JLabel("Enter X0 : ");
            enterX0Title.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterX0Field = new JTextField();
            enterX0Field.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterX0Field.setPreferredSize(new Dimension(200, 50));


            JLabel enterETitle = new JLabel("Enter e : ");
            enterETitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterEField = new JTextField();
            enterEField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterEField.setPreferredSize(new Dimension(200, 50));


            JPanel contentPanel = new JPanel(new GridLayout(4, 2, -100, 0));
            contentPanel.add(enterFTitle);
            contentPanel.add(enterFField);
            contentPanel.add(enterDFTitle);
            contentPanel.add(enterDFField);
            contentPanel.add(enterX0Title);
            contentPanel.add(enterX0Field);
            contentPanel.add(enterETitle);
            contentPanel.add(enterEField);

            JPanel showPanel = new JPanel(new BorderLayout());
            showPanel.add(newtTitle, BorderLayout.NORTH);
            showPanel.add(contentPanel, BorderLayout.CENTER);

            JButton solveButton = new JButton("Solve");
            solveButton.setFocusPainted(false);
            solveButton.setPreferredSize(new Dimension(80, 40));
            solveButton.setFont(new Font("Arial", Font.PLAIN, 17));
            Color customGreen = new Color(34, 139, 34);  // RGB values for green color
            solveButton.setBackground(customGreen);
            solveButton.setForeground(Color.white);  // Set the text color to white for better visibility
            solveButton.setEnabled(false);

            JButton cancelButton = new JButton("Cancel");
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
                    difeqTitle.setText("Non-Linear Equation solution using Newton-Raphson method : ");
                    difeqTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    //create ans scrolled
                    JTextArea difeqAns = new JTextArea();
                    difeqAns.append("Answer : ");
                    difeqAns.append(String.valueOf(fixAccuracy(ans)));
                    difeqAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    difeqAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(difeqAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(100, 50));

                    //create content panel
                    JPanel ansContentPanel = new JPanel(new GridLayout(2, 1));
                    ansContentPanel.add(difeqTitle);
                    ansContentPanel.add(polyAnsScrollPane);

                    JOptionPane.showConfirmDialog(null, ansContentPanel, "Non-Linear Equations", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? "Invalid inputs" : ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            JOptionPane.showOptionDialog(null, showPanel, "Non-Linear Equations", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon128, buttons, null);
        };
        JPanel newton_RaphsonCard = createCard(title, description, button, enterNewton_Raphson);
        nonLinearEquationsPanel.add(newton_RaphsonCard);

        //***********************************************************************

        //init Halley Card
        title = "Halley";
        description = "Solving non-linear equations using the Halley method";
        button = "Enter";
        ActionListener enterHalley = e -> {
            JLabel hallTitleLabel = new JLabel("Halley's Method");
            hallTitleLabel.setFont(new Font(mainFont, Font.BOLD, 25));
            JPanel hallTitle = new JPanel();
            hallTitle.add(hallTitleLabel);
            hallTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

            JLabel enterFTitle = new JLabel("Enter f(x) : ");
            enterFTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterFField = new JTextField();
            enterFField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterFField.setPreferredSize(new Dimension(200, 50));

            JLabel enterDFTitle = new JLabel("Enter f '(x) : ");
            enterDFTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterDFField = new JTextField();
            enterDFField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterDFField.setPreferredSize(new Dimension(200, 50));

            JLabel enterD2FTitle = new JLabel("Enter f ''(x) : ");
            enterD2FTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterD2FField = new JTextField();
            enterD2FField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterD2FField.setPreferredSize(new Dimension(200, 50));

            JLabel enterX0Title = new JLabel("Enter X0 : ");
            enterX0Title.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterX0Field = new JTextField();
            enterX0Field.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterX0Field.setPreferredSize(new Dimension(200, 50));


            JLabel enterETitle = new JLabel("Enter e : ");
            enterETitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterEField = new JTextField();
            enterEField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterEField.setPreferredSize(new Dimension(200, 50));


            JPanel contentPanel = new JPanel(new GridLayout(5, 2, -100, 0));
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

            JPanel showPanel = new JPanel(new BorderLayout());
            showPanel.add(hallTitle, BorderLayout.NORTH);
            showPanel.add(contentPanel, BorderLayout.CENTER);

            JButton solveButton = new JButton("Solve");
            solveButton.setFocusPainted(false);
            solveButton.setPreferredSize(new Dimension(80, 40));
            solveButton.setFont(new Font("Arial", Font.PLAIN, 17));
            Color customGreen = new Color(34, 139, 34);  // RGB values for green color
            solveButton.setBackground(customGreen);
            solveButton.setForeground(Color.white);  // Set the text color to white for better visibility
            solveButton.setEnabled(false);

            JButton cancelButton = new JButton("Cancel");
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
                    difeqTitle.setText("Non-Linear Equation solution using Halley's method : ");
                    difeqTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    //create ans scrolled
                    JTextArea difeqAns = new JTextArea();
                    difeqAns.append("Answer : ");
                    difeqAns.append(String.valueOf(fixAccuracy(ans)));
                    difeqAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    difeqAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(difeqAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(100, 50));

                    //create content panel
                    JPanel ansContentPanel = new JPanel(new GridLayout(2, 1));
                    ansContentPanel.add(difeqTitle);
                    ansContentPanel.add(polyAnsScrollPane);

                    JOptionPane.showConfirmDialog(null, ansContentPanel, "Non-Linear Equations", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? "Invalid inputs" : ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            JOptionPane.showOptionDialog(null, showPanel, "Non-Linear Equations", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon128, buttons, null);
        };
        JPanel halleyCard = createCard(title, description, button, enterHalley);
        nonLinearEquationsPanel.add(halleyCard);

        //***********************************************************************

        //init Fixed Point Iteration Card
        title = "Fixed Point Iteration";
        description = "Solving non-linear equations using the Fixed Point Iteration method";
        button = "Enter";
        ActionListener enterFixedPointIteration = e -> {
            JLabel fpiTitleLabel = new JLabel("Fixed Point Iteration Method");
            fpiTitleLabel.setFont(new Font(mainFont, Font.BOLD, 25));
            JPanel fpiTitle = new JPanel();
            fpiTitle.add(fpiTitleLabel);
            fpiTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

            JLabel enterGTitle = new JLabel("Enter g(x) : ");
            enterGTitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterGField = new JTextField();
            enterGField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterGField.setPreferredSize(new Dimension(200, 50));


            JLabel enterX0Title = new JLabel("Enter X0 : ");
            enterX0Title.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterX0Field = new JTextField();
            enterX0Field.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterX0Field.setPreferredSize(new Dimension(200, 50));


            JLabel enterETitle = new JLabel("Enter e : ");
            enterETitle.setFont(new Font(mainFont, Font.PLAIN, 20));
            JTextField enterEField = new JTextField();
            enterEField.setFont(new Font(secondFont, Font.PLAIN, 18));
            enterEField.setPreferredSize(new Dimension(200, 50));


            JPanel contentPanel = new JPanel(new GridLayout(3, 2, -100, 0));
            contentPanel.add(enterGTitle);
            contentPanel.add(enterGField);
            contentPanel.add(enterX0Title);
            contentPanel.add(enterX0Field);
            contentPanel.add(enterETitle);
            contentPanel.add(enterEField);

            JPanel showPanel = new JPanel(new BorderLayout());
            showPanel.add(fpiTitle, BorderLayout.NORTH);
            showPanel.add(contentPanel, BorderLayout.CENTER);

            JButton solveButton = new JButton("Solve");
            solveButton.setFocusPainted(false);
            solveButton.setPreferredSize(new Dimension(80, 40));
            solveButton.setFont(new Font("Arial", Font.PLAIN, 17));
            Color customGreen = new Color(34, 139, 34);  // RGB values for green color
            solveButton.setBackground(customGreen);
            solveButton.setForeground(Color.white);  // Set the text color to white for better visibility
            solveButton.setEnabled(false);

            JButton cancelButton = new JButton("Cancel");
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
                    difeqTitle.setText("Non-Linear Equation solution using Fixed Point Iteration method : ");
                    difeqTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    //create ans scrolled
                    JTextArea difeqAns = new JTextArea();
                    difeqAns.append("Answer : ");
                    difeqAns.append(String.valueOf(fixAccuracy(ans)));
                    difeqAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    difeqAns.setEditable(false);
                    JScrollPane polyAnsScrollPane = new JScrollPane(difeqAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(100, 50));

                    //create content panel
                    JPanel ansContentPanel = new JPanel(new GridLayout(2, 1));
                    ansContentPanel.add(difeqTitle);
                    ansContentPanel.add(polyAnsScrollPane);

                    JOptionPane.showConfirmDialog(null, ansContentPanel, "Non-Linear Equations", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? "Invalid inputs" : ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            JOptionPane.showOptionDialog(null, showPanel, "Non-Linear Equations", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon128, buttons, null);
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
        polynomialsPanel.setName("Differential Equations");
        polynomialsPanel.setPreferredSize(mainFrame.getSize());
        polynomialsPanel.setBackground(new Color(100, 100, 100));
        GridLayout startLayout = new GridLayout(3, 1);
        startLayout.setHgap(5);
        startLayout.setVgap(5);
        polynomialsPanel.setLayout(startLayout);

        //***********************************************************************

        //init Value At x Card
        String title = "Value At x";
        String description = "Evaluates the polynomial at the specified value of x using Horner's method";
        String button = "Enter";
        ActionListener enterValueAt = e -> {
            JLabel enterPolyLabel = new JLabel("Enter Polynomials : ");
            enterPolyLabel.setFont(new Font(mainFont, Font.BOLD, 25));
            JPanel enterPoly = new JPanel();
            enterPoly.add(enterPolyLabel);


            // inputs : degree
            JLabel enterDegreeTitle = new JLabel("Enter degree of Polynomial :");
            enterDegreeTitle.setFont(new Font(secondFont, Font.PLAIN, 20));
            enterDegreeTitle.setHorizontalAlignment(SwingConstants.LEFT);

            SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, 1000, 1);
            JSpinner enterDegreeSp = new JSpinner(spinnerModel);
            JSpinner.NumberEditor editor = (JSpinner.NumberEditor) enterDegreeSp.getEditor();
            editor.getTextField().setColumns(3); // Adjust the width as needed

            JButton confirmButton = new JButton("Confirm");
            confirmButton.setPreferredSize(new Dimension(80, 30));

            JPanel enterDegree = new JPanel();
            enterDegree.setLayout(new FlowLayout(FlowLayout.LEFT));
            enterDegree.add(enterDegreeTitle, BorderLayout.WEST);
            enterDegree.add(enterDegreeSp, BorderLayout.CENTER);
            enterDegree.add(confirmButton, BorderLayout.EAST);


            // input coeffs
            JLabel enterCoeffsTitle = new JLabel("Enter Coefficients : ");
            enterCoeffsTitle.setFont(new Font(secondFont, Font.PLAIN, 20));
            enterCoeffsTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0)); // Adjust the top and bottom padding

            JScrollPane enterCoeffsScroll = new JScrollPane();
            enterCoeffsScroll.setPreferredSize(new Dimension(220, 150));

            JPanel inputsPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
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

            JButton continueButton = new JButton("Continue");
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

            JButton cancelButton = new JButton("Cancel");
            cancelButton.setFocusPainted(false);
            cancelButton.setPreferredSize(new Dimension(100, 40));
            cancelButton.setFont(new Font("Arial", Font.PLAIN, 17));

            Object[] buttons = {cancelButton, continueButton};

            final boolean[] cancelPressed = {false};
            cancelButton.addActionListener(cancel -> {
                try {
                    cancelPressed[0] = true;
                    Window optionDialog = SwingUtilities.getWindowAncestor(inputsPanel);
                    optionDialog.dispose();
                } catch (Exception ignored) {
                }
            });

            continueButton.addActionListener(solve -> {
                Window optionDialog = SwingUtilities.getWindowAncestor(inputsPanel);
                optionDialog.dispose();
                try {
                    ArrayList<BigDecimal> coeffs = new ArrayList<>();
                    for (JTextField field : coeffsFields) {
                        BigDecimal coeff = EvaluateString.evaluate(field.getText());
                        coeffs.add(coeff);
                    }
                    polynomial = new Polynomial(coeffs);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? "Invalid inputs" : ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            if (polynomial == null) {
                JOptionPane.showOptionDialog(null, inputsPanel, "Polynomials", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon128, buttons, null);
                if (cancelPressed[0]) return;
            }

            JLabel horTitleLabel = new JLabel("Horner's Method");
            horTitleLabel.setFont(new Font(mainFont, Font.BOLD, 25));
            JPanel hornerTitle = new JPanel();
            hornerTitle.add(horTitleLabel);

            //create title
            JLabel polyTitle = new JLabel();
            polyTitle.setText("Your Polynomial : ");
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
            JLabel enterXLabel = new JLabel("Enter x : ");
            enterXLabel.setFont(new Font(mainFont, Font.PLAIN, 20));

            // inter x field
            JTextField enterXField = new JTextField();
            enterXField.setColumns(10);

            //create content panel
            JPanel contentPanel = new JPanel(new GridBagLayout());
            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.insets = new Insets(10, 0, 10, 0);

            contentPanel.add(hornerTitle, gbc);
            gbc.gridy++;
            contentPanel.add(polyTitle, gbc);
            gbc.gridy++;
            contentPanel.add(polyAnsScrollPane, gbc);
            gbc.gridy++;
            contentPanel.add(enterXLabel, gbc);
            gbc.insets = new Insets(10, -150, 10, 0);
            gbc.gridx++;
            contentPanel.add(enterXField, gbc);

            JButton solveButton = new JButton("Solve");
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
                    solTitle.setText("Value at x using Horner's Method : ");
                    solTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    //create ans scrolled
                    JTextArea solAns = new JTextArea();
                    solAns.append("Answer : ");
                    solAns.append(String.valueOf(fixAccuracy(ans)));
                    solAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    solAns.setEditable(false);
                    JScrollPane solAnsScrollPane = new JScrollPane(solAns);
                    solAnsScrollPane.setPreferredSize(new Dimension(100, 50));

                    //create content panel
                    JPanel solContentPanel = new JPanel(new GridLayout(2, 1));
                    solContentPanel.add(solTitle);
                    solContentPanel.add(solAnsScrollPane);

                    String[] response = {"OK"};

                    JOptionPane.showOptionDialog(null, solContentPanel, "Polynomials", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon, response, response[0]);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? "Invalid inputs" : ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            Object[] solvedButtons = {cancelButton, solveButton};

            JOptionPane.showOptionDialog(null, contentPanel, "Polynomials", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon128, solvedButtons, null);
        };
        JPanel valueAtCard = createCard(title, description, button, enterValueAt);
        polynomialsPanel.add(valueAtCard);

        //***********************************************************************

        //init Divide on (x - a) Card
        title = "Divide on (x - a)";
        description = "Divides the polynomial by (x - a) using Horner's method and returns the resulting polynomial";
        button = "Enter";
        ActionListener enterDivideOn = e -> {
            JLabel enterPolyLabel = new JLabel("Enter Polynomials : ");
            enterPolyLabel.setFont(new Font(mainFont, Font.BOLD, 25));
            JPanel enterPoly = new JPanel();
            enterPoly.add(enterPolyLabel);


            // inputs : degree
            JLabel enterDegreeTitle = new JLabel("Enter degree of Polynomial :");
            enterDegreeTitle.setFont(new Font(secondFont, Font.PLAIN, 20));
            enterDegreeTitle.setHorizontalAlignment(SwingConstants.LEFT);

            SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, 1000, 1);
            JSpinner enterDegreeSp = new JSpinner(spinnerModel);
            JSpinner.NumberEditor editor = (JSpinner.NumberEditor) enterDegreeSp.getEditor();
            editor.getTextField().setColumns(3); // Adjust the width as needed

            JButton confirmButton = new JButton("Confirm");
            confirmButton.setPreferredSize(new Dimension(80, 30));

            JPanel enterDegree = new JPanel();
            enterDegree.setLayout(new FlowLayout(FlowLayout.LEFT));
            enterDegree.add(enterDegreeTitle, BorderLayout.WEST);
            enterDegree.add(enterDegreeSp, BorderLayout.CENTER);
            enterDegree.add(confirmButton, BorderLayout.EAST);


            // input coeffs
            JLabel enterCoeffsTitle = new JLabel("Enter Coefficients : ");
            enterCoeffsTitle.setFont(new Font(secondFont, Font.PLAIN, 20));
            enterCoeffsTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0)); // Adjust the top and bottom padding

            JScrollPane enterCoeffsScroll = new JScrollPane();
            enterCoeffsScroll.setPreferredSize(new Dimension(220, 150));

            JPanel inputsPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
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

            JButton continueButton = new JButton("Continue");
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

            JButton cancelButton = new JButton("Cancel");
            cancelButton.setFocusPainted(false);
            cancelButton.setPreferredSize(new Dimension(100, 40));
            cancelButton.setFont(new Font("Arial", Font.PLAIN, 17));

            Object[] buttons = {cancelButton, continueButton};

            final boolean[] cancelPressed = {false};
            cancelButton.addActionListener(cancel -> {
                try {
                    cancelPressed[0] = true;
                    Window optionDialog = SwingUtilities.getWindowAncestor(inputsPanel);
                    optionDialog.dispose();
                } catch (Exception ignored) {
                }
            });

            continueButton.addActionListener(solve -> {
                Window optionDialog = SwingUtilities.getWindowAncestor(inputsPanel);
                optionDialog.dispose();
                try {
                    ArrayList<BigDecimal> coeffs = new ArrayList<>();
                    for (JTextField field : coeffsFields) {
                        BigDecimal coeff = EvaluateString.evaluate(field.getText());
                        coeffs.add(coeff);
                    }
                    polynomial = new Polynomial(coeffs);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? "Invalid inputs" : ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            if (polynomial == null) {
                JOptionPane.showOptionDialog(null, inputsPanel, "Polynomials", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon128, buttons, null);
                if (cancelPressed[0]) return;
            }

            JLabel horTitleLabel = new JLabel("Horner's Method");
            horTitleLabel.setFont(new Font(mainFont, Font.BOLD, 25));
            JPanel hornerTitle = new JPanel();
            hornerTitle.add(horTitleLabel);

            //create title
            JLabel polyTitle = new JLabel();
            polyTitle.setText("Your Polynomial : ");
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
            JLabel enterALabel = new JLabel("Enter a : ");
            enterALabel.setFont(new Font(mainFont, Font.PLAIN, 20));

            // inter a field
            JTextField enterAField = new JTextField();
            enterAField.setColumns(10);

            //create content panel
            JPanel contentPanel = new JPanel(new GridBagLayout());
            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.insets = new Insets(10, 0, 10, 0);

            contentPanel.add(hornerTitle, gbc);
            gbc.gridy++;
            contentPanel.add(polyTitle, gbc);
            gbc.gridy++;
            contentPanel.add(polyAnsScrollPane, gbc);
            gbc.gridy++;
            contentPanel.add(enterALabel, gbc);
            gbc.insets = new Insets(10, -150, 10, 0);
            gbc.gridx++;
            contentPanel.add(enterAField, gbc);

            JButton solveButton = new JButton("Solve");
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
                    solTitle.setText("<html>" + "The result Polynomial by dividing on (x-a) <br>" + "using Horner's Method : " + "</html>");
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
                    JPanel solContentPanel = new JPanel(new GridLayout(2, 1));
                    solContentPanel.add(solTitle);
                    solContentPanel.add(solAnsScrollPane);

                    String[] response = {"OK"};

                    JOptionPane.showOptionDialog(null, solContentPanel, "Polynomials", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon, response, response[0]);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? "Invalid inputs" : ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            Object[] solvedButtons = {cancelButton, solveButton};

            JOptionPane.showOptionDialog(null, contentPanel, "Polynomials", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon128, solvedButtons, null);
        };
        JPanel divideOnCard = createCard(title, description, button, enterDivideOn);
        polynomialsPanel.add(divideOnCard);

        //***********************************************************************

        //init Diff at x Card
        title = "Diff at x";
        description = "Calculates the value of the derivative of the polynomial at the specified value of x and rank using Horner's method";
        button = "Enter";
        ActionListener enterDiffAt = e -> {
            JLabel enterPolyLabel = new JLabel("Enter Polynomials : ");
            enterPolyLabel.setFont(new Font(mainFont, Font.BOLD, 25));
            JPanel enterPoly = new JPanel();
            enterPoly.add(enterPolyLabel);


            // inputs : degree
            JLabel enterDegreeTitle = new JLabel("Enter degree of Polynomial :");
            enterDegreeTitle.setFont(new Font(secondFont, Font.PLAIN, 20));
            enterDegreeTitle.setHorizontalAlignment(SwingConstants.LEFT);

            SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, 1000, 1);
            JSpinner enterDegreeSp = new JSpinner(spinnerModel);
            JSpinner.NumberEditor editor = (JSpinner.NumberEditor) enterDegreeSp.getEditor();
            editor.getTextField().setColumns(3); // Adjust the width as needed

            JButton confirmButton = new JButton("Confirm");
            confirmButton.setPreferredSize(new Dimension(80, 30));

            JPanel enterDegree = new JPanel();
            enterDegree.setLayout(new FlowLayout(FlowLayout.LEFT));
            enterDegree.add(enterDegreeTitle, BorderLayout.WEST);
            enterDegree.add(enterDegreeSp, BorderLayout.CENTER);
            enterDegree.add(confirmButton, BorderLayout.EAST);


            // input coeffs
            JLabel enterCoeffsTitle = new JLabel("Enter Coefficients : ");
            enterCoeffsTitle.setFont(new Font(secondFont, Font.PLAIN, 20));
            enterCoeffsTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0)); // Adjust the top and bottom padding

            JScrollPane enterCoeffsScroll = new JScrollPane();
            enterCoeffsScroll.setPreferredSize(new Dimension(220, 150));

            JPanel inputsPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
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

            JButton continueButton = new JButton("Continue");
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

            JButton cancelButton = new JButton("Cancel");
            cancelButton.setFocusPainted(false);
            cancelButton.setPreferredSize(new Dimension(100, 40));
            cancelButton.setFont(new Font("Arial", Font.PLAIN, 17));

            Object[] buttons = {cancelButton, continueButton};

            final boolean[] cancelPressed = {false};
            cancelButton.addActionListener(cancel -> {
                try {
                    cancelPressed[0] = true;
                    Window optionDialog = SwingUtilities.getWindowAncestor(inputsPanel);
                    optionDialog.dispose();
                } catch (Exception ignored) {
                }
            });

            continueButton.addActionListener(solve -> {
                Window optionDialog = SwingUtilities.getWindowAncestor(inputsPanel);
                optionDialog.dispose();
                try {
                    ArrayList<BigDecimal> coeffs = new ArrayList<>();
                    for (JTextField field : coeffsFields) {
                        BigDecimal coeff = EvaluateString.evaluate(field.getText());
                        coeffs.add(coeff);
                    }
                    polynomial = new Polynomial(coeffs);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? "Invalid inputs" : ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            if (polynomial == null) {
                JOptionPane.showOptionDialog(null, inputsPanel, "Polynomials", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon128, buttons, null);
                if (cancelPressed[0]) return;
            }

            JLabel horTitleLabel = new JLabel("Horner's Method");
            horTitleLabel.setFont(new Font(mainFont, Font.BOLD, 25));
            JPanel hornerTitle = new JPanel();
            hornerTitle.add(horTitleLabel);

            //create title
            JLabel polyTitle = new JLabel();
            polyTitle.setText("Your Polynomial : ");
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
            JLabel enterXLabel = new JLabel("Enter x : ");
            enterXLabel.setFont(new Font(mainFont, Font.PLAIN, 20));

            // inter x field
            JTextField enterXField = new JTextField();
            enterXField.setColumns(10);

            // inputs : degree
            JLabel enterDiffDegreeTitle = new JLabel("Enter degree of Differentiation :");
            enterDiffDegreeTitle.setFont(new Font(secondFont, Font.PLAIN, 20));
            enterDiffDegreeTitle.setHorizontalAlignment(SwingConstants.LEFT);

            SpinnerNumberModel spinnerModelDiff = new SpinnerNumberModel(0, 0, 100, 1);
            JSpinner enterDiffDegreeSp = new JSpinner(spinnerModelDiff);
            JSpinner.NumberEditor editorDiff = (JSpinner.NumberEditor) enterDiffDegreeSp.getEditor();
            editorDiff.getTextField().setColumns(2); // Adjust the width as needed

            JPanel enterDiffDegree = new JPanel();
            enterDiffDegree.setLayout(new FlowLayout(FlowLayout.LEFT));
            enterDiffDegree.add(enterDiffDegreeTitle, BorderLayout.WEST);
            enterDiffDegree.add(enterDiffDegreeSp, BorderLayout.CENTER);
            enterDiffDegree.setBorder(BorderFactory.createEmptyBorder(0, -5, 0, 0));

            //create content panel
            JPanel contentPanel = new JPanel(new GridBagLayout());
            gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.insets = new Insets(10, 0, 10, 0);

            contentPanel.add(hornerTitle, gbc);
            gbc.gridy++;
            contentPanel.add(polyTitle, gbc);
            gbc.gridy++;
            contentPanel.add(polyAnsScrollPane, gbc);
            gbc.gridy++;
            contentPanel.add(enterXLabel, gbc);
            gbc.insets = new Insets(10, -220, 10, 0);
            gbc.gridx++;
            contentPanel.add(enterXField, gbc);
            gbc.gridx = 0;
            gbc.gridy++;
            gbc.insets = new Insets(10, 0, 10, 0);
            contentPanel.add(enterDiffDegree, gbc);

            JButton solveButton = new JButton("Solve");
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
                    solTitle.setText("<html> " + "Derivative Polynomial answer at x <br>" + "using Horner's Method : " + "</html>");
                    solTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    //create ans scrolled
                    JTextArea solAns = new JTextArea();
                    solAns.append("Ans : ");
                    solAns.append(String.valueOf(fixAccuracy(ans)));
                    solAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    solAns.setEditable(false);
                    JScrollPane solAnsScrollPane = new JScrollPane(solAns);
                    solAnsScrollPane.setPreferredSize(new Dimension(100, 50));

                    //create content panel
                    JPanel solContentPanel = new JPanel(new GridLayout(2, 1));
                    solContentPanel.add(solTitle);
                    solContentPanel.add(solAnsScrollPane);

                    String[] response = {"OK"};

                    JOptionPane.showOptionDialog(null, solContentPanel, "Polynomials", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon, response, response[0]);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? "Invalid inputs" : ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            Object[] solvedButtons = {cancelButton, solveButton};

            JOptionPane.showOptionDialog(null, contentPanel, "Polynomials", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, keyboardIcon128, solvedButtons, null);
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

        JPanel pointsCard = new JPanel();
        JTextArea pointsText;
        JScrollPane pointsScrollPane;
        JButton continueButton;
        {

            //points card title
            JPanel titlePanel = new JPanel();
            JLabel pointsTitle = new JLabel();
            pointsTitle.setText("Generated Points : ");
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
            continueButton = new JButton("Continue");
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
            cardTitle.setText("Expression Function");
            cardTitle.setFont(new Font(mainFont, Font.BOLD, 25));


            // description
            JLabel cardDescription = new JLabel();
            cardDescription.setText("<html>" + "Supported Functions : <br>" + " <b>Polynomials </b> : x^3 + 9*x^2 -5*x +10 <br>" + " <b>Exponential </b> : exp(x^2) .. exp(1/x) <br>" + " <b>Binary Logarithm </b> : log(3*x) .. log(-x) <br>" + " <b>Trigonometric Functions </b> : sin(x),cos(x),tan(x) <br>" + " <b>Inverse Trigonometric Functions </b> : asin(x),acos(x),atan(x) <br>" + " <b>Hyperbolic Trigonometric Functions </b> : sinh(x),cosh(x),tanh(x) <br>" + "</html>");
            cardDescription.setFont(new Font(secondFont, Font.ITALIC, 15));
            cardDescription.setBackground(inputCard.getBackground());

            //info panel
            JPanel infoPanel = new JPanel(new GridLayout(2, 1, 0, -20));
            infoPanel.add(cardTitle);
            infoPanel.add(cardDescription);

            // inputs : exp
            JLabel enterExpTitle = new JLabel("Enter Expression Function :");

            JTextField enterExpField = new JTextField();
            enterExpField.setPreferredSize(new Dimension(100, 40));

            JPanel enterExp = new JPanel();
            GridLayout inputLayout = new GridLayout(2, 1, 0, -5);
            enterExp.setLayout(inputLayout);
            enterExp.add(enterExpTitle);
            enterExp.add(enterExpField);

            // inputs : a
            JLabel enterATitle = new JLabel("Enter a – The lower bound of the x-coordinate range :");

            JTextField enterAField = new JTextField();
            //enterAField.setPreferredSize(new Dimension(100, 50));

            JPanel enterA = new JPanel();
            enterA.setLayout(inputLayout);
            enterA.add(enterATitle);
            enterA.add(enterAField);

            // inputs : b
            JLabel enterBTitle = new JLabel("Enter b – The upper bound of the x-coordinate range :");

            JTextField enterBField = new JTextField();
            //enterBField.setPreferredSize(new Dimension(100, 50));

            JPanel enterB = new JPanel();
            enterB.setLayout(inputLayout);
            enterB.add(enterBTitle);
            enterB.add(enterBField);

            // inputs : n
            JLabel enterNTitle = new JLabel("Enter n – The number of points (including a and b) :");
            enterNTitle.setHorizontalAlignment(SwingConstants.LEFT);

            SpinnerNumberModel spinnerModel = new SpinnerNumberModel(2, 2, 1000, 1);
            JSpinner enterNSp = new JSpinner(spinnerModel);
            JSpinner.NumberEditor editor = (JSpinner.NumberEditor) enterNSp.getEditor();
            editor.getTextField().setColumns(3); // Adjust the width as needed

            JPanel enterN = new JPanel();
            enterN.setLayout(new FlowLayout(FlowLayout.LEFT));
            enterN.add(enterNTitle, BorderLayout.WEST);
            enterN.add(enterNSp, BorderLayout.CENTER);

            // inputs panel
            JPanel inputsPanel = new JPanel(new GridLayout(4, 1, -5, 10));
            inputsPanel.add(enterExp);
            inputsPanel.add(enterA);
            inputsPanel.add(enterB);
            inputsPanel.add(enterN);

            // Generate Button
            JButton generateButton = new JButton("Generate");
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
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? "Invalid inputs" : ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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

        JPanel pointsCard = new JPanel();
        JTextArea pointsText;
        JScrollPane pointsScrollPane;
        JButton continueButton;
        {

            //points card title
            JPanel titlePanel = new JPanel();
            JLabel pointsTitle = new JLabel();
            pointsTitle.setText("Generated Points : ");
            pointsTitle.setFont(new Font(mainFont, Font.BOLD, 25));
            titlePanel.add(pointsTitle, BorderLayout.CENTER);

            //point card area
            pointsText = new JTextArea();
            pointsText.setFont(new Font(mainFont, Font.PLAIN, 20));
            pointsText.setEnabled(false);
            pointsScrollPane = new JScrollPane(pointsText);
            pointsScrollPane.setPreferredSize(new Dimension(450, 485));

            // Continue Button
            continueButton = new JButton("Continue");
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
            cardTitle.setText("Points Function");
            cardTitle.setFont(new Font(mainFont, Font.BOLD, 25));


            // description
            JLabel cardDescription = new JLabel();
            cardDescription.setText("<html>" + "Supported Points Type : <br>" + " <b>BigDecimal </b> : 1.919239 <br>" + " <b>Integer </b> : 12392 <br>" + " <b>PI </b> : pi, 2*pi, pi/4 <br>" + "</html>");
            cardDescription.setFont(new Font(secondFont, Font.ITALIC, 15));
            cardDescription.setBackground(inputCard.getBackground());

            //info panel
            JPanel infoPanel = new JPanel(new GridLayout(2, 1, 0, -10));
            infoPanel.add(cardTitle);
            infoPanel.add(cardDescription);


            // inputs : n
            JLabel enterNTitle = new JLabel("Enter n – The number of points (x points) :");
            enterNTitle.setHorizontalAlignment(SwingConstants.LEFT);

            SpinnerNumberModel spinnerModel = new SpinnerNumberModel(2, 2, 1000, 1);
            JSpinner enterNSp = new JSpinner(spinnerModel);
            JSpinner.NumberEditor editor = (JSpinner.NumberEditor) enterNSp.getEditor();
            editor.getTextField().setColumns(3); // Adjust the width as needed

            JButton confirmButton = new JButton("Confirm");
            confirmButton.setPreferredSize(new Dimension(80, 30));

            JPanel enterN = new JPanel();
            enterN.setLayout(new FlowLayout(FlowLayout.LEFT));
            enterN.add(enterNTitle, BorderLayout.WEST);
            enterN.add(enterNSp, BorderLayout.CENTER);
            enterN.add(confirmButton, BorderLayout.EAST);


            // input x and y
            JLabel enterPointsTitle = new JLabel("Enter x and y points : ");
            enterPointsTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0)); // Adjust the top and bottom padding

            JScrollPane enterPointsScroll = new JScrollPane();
            enterPointsScroll.setPreferredSize(new Dimension(450, 300));


            // inputs panel
            JPanel inputsPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.insets = new Insets(5, 0, 5, 0); // Add vertical spacing between components

            inputsPanel.add(enterN, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            gbc.gridwidth = 2;
            inputsPanel.add(enterPointsTitle, gbc);

            gbc.gridy++;
            inputsPanel.add(enterPointsScroll, gbc);

            // Generate Button
            JButton generateButton = new JButton("Generate");
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
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? "Invalid inputs" : ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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

        JPanel pointsCard = new JPanel();
        JTextArea pointsText;
        JScrollPane pointsScrollPane;
        JButton continueButton;
        {

            //points card title
            JPanel titlePanel = new JPanel();
            JLabel pointsTitle = new JLabel();
            pointsTitle.setText("Generated Points : ");
            pointsTitle.setFont(new Font(mainFont, Font.BOLD, 25));
            titlePanel.add(pointsTitle, BorderLayout.CENTER);

            //point card area
            pointsText = new JTextArea();
            pointsText.setFont(new Font(mainFont, Font.PLAIN, 20));
            pointsText.setEnabled(false);
            pointsScrollPane = new JScrollPane(pointsText);
            pointsScrollPane.setPreferredSize(new Dimension(450, 482));

            // Continue Button
            continueButton = new JButton("Continue");
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
            cardTitle.setText("Polynomial Function");
            cardTitle.setFont(new Font(mainFont, Font.BOLD, 25));


            // description
            JLabel cardDescription = new JLabel();
            cardDescription.setText("<html>" + "Supported Coefficients Type : <br>" + " <b>BigDecimal </b> : 1.919239 <br>" + " <b>Integer </b> : 12392 <br>" + " <b>PI </b> : pi, 2*pi, pi/4 <br>" + "</html>");
            cardDescription.setFont(new Font(secondFont, Font.ITALIC, 15));
            cardDescription.setBackground(inputCard.getBackground());

            //info panel
            JPanel infoPanel = new JPanel(new GridLayout(2, 1, 0, -10));
            infoPanel.add(cardTitle);
            infoPanel.add(cardDescription);

            // inputs : a
            JLabel enterATitle = new JLabel("Enter a – The lower bound of the x-coordinate range :");

            JTextField enterAField = new JTextField();
            //enterAField.setPreferredSize(new Dimension(100, 50));

            JPanel enterA = new JPanel();
            GridLayout inputLayout = new GridLayout(2, 1, 0, -5);
            enterA.setLayout(inputLayout);
            enterA.add(enterATitle);
            enterA.add(enterAField);

            // inputs : b
            JLabel enterBTitle = new JLabel("Enter b – The upper bound of the x-coordinate range :");

            JTextField enterBField = new JTextField();
            //enterBField.setPreferredSize(new Dimension(100, 50));

            JPanel enterB = new JPanel();
            enterB.setLayout(inputLayout);
            enterB.add(enterBTitle);
            enterB.add(enterBField);

            // inputs : n
            JLabel enterNTitle = new JLabel("Enter n – The number of points (including a and b) :");
            enterNTitle.setHorizontalAlignment(SwingConstants.LEFT);

            SpinnerNumberModel spinnerModel = new SpinnerNumberModel(2, 2, 1000, 1);
            JSpinner enterNSp = new JSpinner(spinnerModel);
            JSpinner.NumberEditor editor = (JSpinner.NumberEditor) enterNSp.getEditor();
            editor.getTextField().setColumns(3); // Adjust the width as needed

            JPanel enterN = new JPanel();
            enterN.setLayout(new FlowLayout(FlowLayout.LEFT));
            enterN.add(enterNTitle, BorderLayout.WEST);
            enterN.add(enterNSp, BorderLayout.CENTER);

            // inputs : degree
            JLabel enterDegreeTitle = new JLabel("Enter degree of Polynomial :");
            enterDegreeTitle.setHorizontalAlignment(SwingConstants.LEFT);

            spinnerModel = new SpinnerNumberModel(0, 0, 1000, 1);
            JSpinner enterDegreeSp = new JSpinner(spinnerModel);
            editor = (JSpinner.NumberEditor) enterDegreeSp.getEditor();
            editor.getTextField().setColumns(3); // Adjust the width as needed

            JButton confirmButton = new JButton("Confirm");
            confirmButton.setPreferredSize(new Dimension(80, 30));

            JPanel enterDegree = new JPanel();
            enterDegree.setLayout(new FlowLayout(FlowLayout.LEFT));
            enterDegree.add(enterDegreeTitle, BorderLayout.WEST);
            enterDegree.add(enterDegreeSp, BorderLayout.CENTER);
            enterDegree.add(confirmButton, BorderLayout.EAST);


            // input coeffs
            JLabel enterCoeffsTitle = new JLabel("Enter Coefficients : ");
            enterCoeffsTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0)); // Adjust the top and bottom padding

            JScrollPane enterCoeffsScroll = new JScrollPane();
            enterCoeffsScroll.setPreferredSize(new Dimension(220, 150));


            JPanel inputsPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
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
            JButton generateButton = new JButton("Generate");
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
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? "Invalid inputs" : ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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

        JPanel pointsCard = new JPanel();
        JTextArea pointsText;
        JScrollPane pointsScrollPane;
        JSpinner enterNSp;
        JButton solveButton;
        {

            //points card title
            JPanel titlePanel = new JPanel(new BorderLayout());
            JLabel pointsTitle = new JLabel();
            pointsTitle.setText("Solution Approximations : ");
            pointsTitle.setFont(new Font(mainFont, Font.BOLD, 25));
            titlePanel.add(pointsTitle, BorderLayout.NORTH);

            // inputs : n
            JLabel enterNTitle = new JLabel("Enter n – The number of iterations :");
            enterNTitle.setHorizontalAlignment(SwingConstants.LEFT);

            SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 1000, 1);
            enterNSp = new JSpinner(spinnerModel);
            JSpinner.NumberEditor editor = (JSpinner.NumberEditor) enterNSp.getEditor();
            editor.getTextField().setColumns(3); // Adjust the width as needed

            JPanel enterN = new JPanel();
            enterN.setLayout(new FlowLayout(FlowLayout.LEFT));
            enterN.add(enterNTitle, BorderLayout.WEST);
            enterN.add(enterNSp, BorderLayout.CENTER);

            titlePanel.add(enterN, BorderLayout.CENTER);

            //point card area
            pointsText = new JTextArea();
            //pointsText.setPreferredSize(new Dimension(450, 550));
            pointsText.setFont(new Font(mainFont, Font.PLAIN, 20));
            pointsText.setEditable(false);
            pointsScrollPane = new JScrollPane(pointsText);
            pointsScrollPane.setPreferredSize(new Dimension(450, 480));

            // Continue Button
            solveButton = new JButton("Solve");
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
            cardTitle.setText("<html>" + "System of Non-Linear Equations <br>" + "Newton-Raphson Method <br> " + "</html>");
            cardTitle.setFont(new Font(mainFont, Font.BOLD, 25));

            //info panel
            JPanel infoPanel = new JPanel(new GridLayout(1, 1, 0, -30));
            infoPanel.add(cardTitle);

            // inputs : fx
            JLabel enterFxTitle = new JLabel("Enter f(x,y) :");

            JTextField enterFxField = new JTextField();
            enterFxField.setPreferredSize(new Dimension(100, 40));

            JPanel enterFx = new JPanel();
            GridLayout inputLayout = new GridLayout(2, 1, 0, -5);
            enterFx.setLayout(inputLayout);
            enterFx.add(enterFxTitle);
            enterFx.add(enterFxField);

            // inputs : gx
            JLabel enterGxTitle = new JLabel("Enter g(x,y) :");

            JTextField enterGxField = new JTextField();
            enterGxField.setPreferredSize(new Dimension(100, 40));

            JPanel enterGx = new JPanel();
            enterGx.setLayout(inputLayout);
            enterGx.add(enterGxTitle);
            enterGx.add(enterGxField);

            // inputs : dfdx
            JLabel enterDfdxTitle = new JLabel("Enter dfdx :");

            JTextField enterDfdxField = new JTextField();
            //enterAField.setPreferredSize(new Dimension(100, 50));

            JPanel enterDfdx = new JPanel();
            enterDfdx.setLayout(inputLayout);
            enterDfdx.add(enterDfdxTitle);
            enterDfdx.add(enterDfdxField);

            // inputs : dfdy
            JLabel enterDfdyTitle = new JLabel("Enter dfdy :");

            JTextField enterDfdyField = new JTextField();
            //enterAField.setPreferredSize(new Dimension(100, 50));

            JPanel enterDfdy = new JPanel();
            enterDfdy.setLayout(inputLayout);
            enterDfdy.add(enterDfdyTitle);
            enterDfdy.add(enterDfdyField);

            // inputs : dgdx
            JLabel enterDgdxTitle = new JLabel("Enter dgdx :");

            JTextField enterDgdxField = new JTextField();
            //enterBField.setPreferredSize(new Dimension(100, 50));

            JPanel enterDgdx = new JPanel();
            enterDgdx.setLayout(inputLayout);
            enterDgdx.add(enterDgdxTitle);
            enterDgdx.add(enterDgdxField);

            // inputs : dgdy
            JLabel enterDgdyTitle = new JLabel("Enter dgdy :");

            JTextField enterDgdyField = new JTextField();
            //enterBField.setPreferredSize(new Dimension(100, 50));

            JPanel enterDgdy = new JPanel();
            enterDgdy.setLayout(inputLayout);
            enterDgdy.add(enterDgdyTitle);
            enterDgdy.add(enterDgdyField);

            // inputs : x0
            JLabel enterX0Title = new JLabel("Enter X0 :");

            JTextField enterX0Field = new JTextField();
            //enterBField.setPreferredSize(new Dimension(100, 50));

            JPanel enterX0 = new JPanel();
            enterX0.setLayout(inputLayout);
            enterX0.add(enterX0Title);
            enterX0.add(enterX0Field);

            // inputs : y0
            JLabel enterY0Title = new JLabel("Enter Y0 :");

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
                    JOptionPane.showMessageDialog(null, ex.getMessage() == null ? "Invalid inputs" : ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
        cardDescription.setFont(new Font(secondFont, Font.ITALIC, 15));
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

        JLabel tipLabel = new JLabel("More info");
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
}
