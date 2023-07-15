import Functions.ExpressionFunction;
import Functions.PointsFunction;
import Functions.Polynomial;
import Numerics.Interpolation;
import Util.EvaluateString;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.Stack;
import java.util.function.Consumer;

public class GUI {
    private JFrame mainFrame;
    private JMenuBar mainMenuBar;
    private JPanel startPanel;
    private JPanel chooseFunctionPanel;
    private JPanel interpolationPanel;
    private JPanel integralPanel;
    private JPanel differentiationPanel;
    private JPanel differentialEquationsPanel;
    private JPanel nonLinearEquationsPanel;
    private JPanel systemOfNonLinearEquationsPanel;
    private JPanel polynomialsPanel;
    private JPanel expressionFunctionPanel;
    private JPanel pointsFunctionPanel;
    private JPanel polynomialFunctionPanel;

    private ImageIcon mainIcon;
    private ImageIcon backIcon;
    private ImageIcon homeIcon;
    private ImageIcon infoIcon;
    private ImageIcon solutionIcon;

    private JButton backButton;
    private JButton homeButton;

    private final String mainFont = "Times New Roman";
    private final String secondFont = "Times New Roman";
    private final String buttonFont = "Times New Roman";

    private Stack<JPanel> panelsStack;
    private PointsFunction function;
    private Consumer<PointsFunction> doAction;

    public GUI() {
        //Apply nimbus theme
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (UnsupportedLookAndFeelException | InstantiationException | IllegalAccessException |
                 ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Locale.setDefault(Locale.ENGLISH); // fix spinner and text showing arabic symbols
        initIcons();
        initMainFrame();
        initMenuBar();
        initPanels();
        updateMainPanel();
        mainFrame.pack();
    }

    private void initIcons() {
        mainIcon = new ImageIcon(Objects.requireNonNull(GUI.class.getClassLoader().getResource("Icons/function.png")));
        backIcon = new ImageIcon(Objects.requireNonNull(GUI.class.getClassLoader().getResource("Icons/left-arrow.png")));
        homeIcon = new ImageIcon(Objects.requireNonNull(GUI.class.getClassLoader().getResource("Icons/home-button.png")));
        infoIcon = new ImageIcon(Objects.requireNonNull(GUI.class.getClassLoader().getResource("Icons/info.png")));
        solutionIcon = new ImageIcon(Objects.requireNonNull(GUI.class.getClassLoader().getResource("Icons/solution.png")));
    }

    private void initMainFrame() {
        mainFrame = new JFrame();
        mainFrame.setVisible(true);
        mainFrame.setTitle("Numeric Calculator");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setIconImage(mainIcon.getImage());
        mainFrame.setMinimumSize(new Dimension(940, 620));
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);
    }

    private void initMenuBar() {
        mainMenuBar = new JMenuBar();
        mainMenuBar.setLayout(new BorderLayout());

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
        infoButton.addActionListener(e -> {
            JOptionPane.showConfirmDialog(null, "Created by Abd_HM 💙", "About", JOptionPane.DEFAULT_OPTION);
        });
        infoButton.setPreferredSize(new Dimension(50, 50));
        infoButton.setIcon(infoIcon);
        infoButton.setFocusPainted(false);

        mainMenuBar.add(backButton, BorderLayout.WEST);
        mainMenuBar.add(homeButton);
        mainMenuBar.add(infoButton, BorderLayout.EAST);
        mainFrame.setJMenuBar(mainMenuBar);
    }

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
        panelsStack = new Stack<>();
        panelsStack.add(startPanel);
    }

    private void initStartPanel() {
        startPanel = new JPanel();
        startPanel.setName("Start");
        startPanel.setPreferredSize(mainFrame.getSize());
        startPanel.setBackground(new Color(100, 100, 100));
        GridLayout startLayout = new GridLayout(4, 2);
        startLayout.setHgap(5);
        startLayout.setVgap(5);
        startPanel.setLayout(startLayout);

        //***********************************************************************

        //init Interpolation Card
        String title = "Interpolation";
        String description = "Interpolate an entered function using numeric interpolation ways";
        String button = "Enter";
        ActionListener enterInterpolation = e -> {
            panelsStack.add(interpolationPanel);
            updateMainPanel();
        };
        JPanel interpolationCard = createCard(title, description, button, enterInterpolation);
        startPanel.add(interpolationCard);

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
        startPanel.add(integralCard);

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
        startPanel.add(diffCard);

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
        startPanel.add(diffEQCard);

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
        startPanel.add(nonLinEQCard);


        //***********************************************************************

        //init System Of Non-Linear Equations Card
        title = "System Of Non-Linear Equations";
        description = "Solve an entered system of non-Linear equations using numeric ways";
        button = "Enter";
        ActionListener enterSysNonLinEQ = e -> {
            //panelsStack.add(nonLinearEquationsPanel);
            //updateMainPanel();
        };
        JPanel sysOfNonLinEQCard = createCard(title, description, button, enterSysNonLinEQ);
        startPanel.add(sysOfNonLinEQCard);


        //***********************************************************************

        //init Differential Equations Card
        title = "Polynomials";
        description = "Do several tasks on an entered polynomial using numeric ways";
        button = "Enter";
        ActionListener enterPolys = e -> {
            panelsStack.add(polynomialsPanel);
            updateMainPanel();
        };
        JPanel polysCard = createCard(title, description, button, enterPolys);
        startPanel.add(polysCard);

        //***********************************************************************

    }

    private JPanel createCard(String title, String description, String button, ActionListener doAction) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());

        JLabel cardTitle = new JLabel();
        cardTitle.setText(title);
        cardTitle.setFont(new Font(mainFont, Font.BOLD, 25));

        JLabel cardDescription = new JLabel();
        cardDescription.setText(description);
        cardDescription.setFont(new Font(secondFont, Font.ITALIC, 15));

        JButton cardButton = new JButton(button);
        cardButton.setFont(new Font(buttonFont, Font.BOLD, 20));
        cardButton.setFocusPainted(false);
        cardButton.setPreferredSize(new Dimension(60, 50));
        cardButton.addActionListener(doAction);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        contentPanel.add(cardTitle, BorderLayout.NORTH);
        contentPanel.add(cardDescription, BorderLayout.CENTER);
        contentPanel.add(cardButton, BorderLayout.SOUTH);

        card.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        card.add(contentPanel, BorderLayout.CENTER);
        return card;
    }

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
        JPanel expressionFunctionCard = createCard("Expression Function",
                "Functions like x^2+3, sin(x), exp(x) ...", "Enter", enterExpressionFunction);


        ActionListener enterPointsFunction = e -> {
            panelsStack.add(pointsFunctionPanel);
            updateMainPanel();
        };
        JPanel pointsFunctionCard = createCard("Points Function",
                "Functions like x0=.. y0=.. , x1=.. y1=.. ", "Enter", enterPointsFunction);

        ActionListener enterPolynomialFunction = e -> {
            panelsStack.add(polynomialFunctionPanel);
            updateMainPanel();
        };
        JPanel polynomialFunctionCard = createCard("Polynomial Function",
                "Functions like p(x) = a0 + a1*x + a2*x^2 ... ", "Enter", enterPolynomialFunction);


        chooseFunctionPanel.add(expressionFunctionCard);
        chooseFunctionPanel.add(pointsFunctionCard);
        chooseFunctionPanel.add(polynomialFunctionCard);
    }

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
        String description = "Get the Interpolation Function using" +
                " the General Method by solving a system of equations using Gaussian elimination ";
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
                    polyAns.setEnabled(false);
                    polyAns.setDisabledTextColor(Color.BLACK);
                    JScrollPane polyAnsScrollPane = new JScrollPane(polyAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(250, 65));

                    //create content panel
                    JPanel contentPanel = new JPanel(new GridLayout(2, 1));
                    contentPanel.add(interTitle);
                    contentPanel.add(polyAnsScrollPane);

                    String[] response = {"Cancel", "Continue with Polynomial"};

                    int feed = JOptionPane.showOptionDialog(null, contentPanel, "Interpolation", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, solutionIcon, response, response[1]);
                    if (feed == 1) {
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
        JPanel generalMethodCard = createCard(title, description, button, enterGeneralMethod);
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
                    polyAns.setEnabled(false);
                    polyAns.setDisabledTextColor(Color.BLACK);
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
                    polyAnsNSH.setEnabled(false);
                    polyAnsNSH.setDisabledTextColor(Color.BLACK);
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
        interpolationPanel.add(lagrangeCard);

        //***********************************************************************

        //init Newton-Gregory Forward Subtraction Card
        title = "Newton-Gregory Forward Subtraction";
        description = "Get the interpolation function using Newton-Gregory Forward Subtractions method\n" +
                "It also can get Newton-Gregory Forward Subtractions Table values";
        button = "Enter";
        ActionListener enterNGFS = e -> {
            doAction = pointsFunction -> {
                try {

                    int degree = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter degree of the required polynomial : ", "Interpolation", JOptionPane.QUESTION_MESSAGE));

                    ArrayList<Double> values = Interpolation.Newton_GregoryForwardSubtractions.getUDV(function);

                    //create table values title
                    JLabel tableTitle = new JLabel();
                    tableTitle.setText("The values of the upper diameter of the Newton-Gregory Forward Subtractions Table : ");
                    tableTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    //create table value scrolled
                    JTextArea tableAns = new JTextArea();
                    tableAns.append(values.toString());
                    tableAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    tableAns.setEnabled(false);
                    tableAns.setDisabledTextColor(Color.BLACK);
                    JScrollPane tableAnsScrollPane = new JScrollPane(tableAns);
                    tableAnsScrollPane.setPreferredSize(new Dimension(300, 70));

                    Polynomial ans = Interpolation.Newton_GregoryForwardSubtractions.getIFAP(pointsFunction, degree);

                    JLabel interTitle = new JLabel();
                    interTitle.setText("Interpolation answer using Newton-Gregory Forward Subtraction : ");
                    interTitle.setFont(new Font(mainFont, Font.PLAIN, 20));


                    JTextArea polyAns = new JTextArea();
                    polyAns.append("P(x) : ");
                    polyAns.append(ans.toString());
                    polyAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    polyAns.setEnabled(false);
                    polyAns.setDisabledTextColor(Color.BLACK);
                    JScrollPane polyAnsScrollPane = new JScrollPane(polyAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(300, 70));

                    JLabel shortPolyTitle = new JLabel();
                    shortPolyTitle.setText("Interpolation answer with no shorthand : ");
                    shortPolyTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    String asnNSH = Interpolation.Newton_GregoryForwardSubtractions.getIFASNS(pointsFunction, degree);

                    JTextArea polyAnsNSH = new JTextArea();
                    polyAnsNSH.append("P(x) : ");
                    polyAnsNSH.append(asnNSH);
                    polyAnsNSH.setFont(new Font(mainFont, Font.PLAIN, 20));
                    polyAnsNSH.setEnabled(false);
                    polyAnsNSH.setDisabledTextColor(Color.BLACK);
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
        interpolationPanel.add(NGFSCard);

        //***********************************************************************

        //init Newton-Gregory Backward Subtraction Card
        title = "Newton-Gregory Backward Subtraction";
        description = "Get the interpolation function using Newton-Gregory Backward Subtractions method\n" +
                "It also can get Newton-Gregory Backward Subtractions Table values";
        button = "Enter";
        ActionListener enterNGBS = e -> {
            doAction = pointsFunction -> {
                try {

                    int degree = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter degree of the required polynomial : ", "Interpolation", JOptionPane.QUESTION_MESSAGE));

                    ArrayList<Double> values = Interpolation.Newton_GregoryBackwardSubtractions.getLDV(function);

                    //create table values title
                    JLabel tableTitle = new JLabel();
                    tableTitle.setText("The values of the lower diameter of the Newton-Gregory Backward Subtractions Table : ");
                    tableTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    //create table value scrolled
                    JTextArea tableAns = new JTextArea();
                    tableAns.append(values.toString());
                    tableAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    tableAns.setEnabled(false);
                    tableAns.setDisabledTextColor(Color.BLACK);
                    JScrollPane tableAnsScrollPane = new JScrollPane(tableAns);
                    tableAnsScrollPane.setPreferredSize(new Dimension(300, 70));

                    Polynomial ans = Interpolation.Newton_GregoryBackwardSubtractions.getIFAP(pointsFunction, degree);

                    JLabel interTitle = new JLabel();
                    interTitle.setText("Interpolation answer using Newton-Gregory Backward Subtraction : ");
                    interTitle.setFont(new Font(mainFont, Font.PLAIN, 20));


                    JTextArea polyAns = new JTextArea();
                    polyAns.append("P(x) : ");
                    polyAns.append(ans.toString());
                    polyAns.setFont(new Font(mainFont, Font.PLAIN, 20));
                    polyAns.setEnabled(false);
                    polyAns.setDisabledTextColor(Color.BLACK);
                    JScrollPane polyAnsScrollPane = new JScrollPane(polyAns);
                    polyAnsScrollPane.setPreferredSize(new Dimension(300, 70));

                    JLabel shortPolyTitle = new JLabel();
                    shortPolyTitle.setText("Interpolation answer with no shorthand : ");
                    shortPolyTitle.setFont(new Font(mainFont, Font.PLAIN, 20));

                    String asnNSH = Interpolation.Newton_GregoryBackwardSubtractions.getIFASNS(pointsFunction, degree);

                    JTextArea polyAnsNSH = new JTextArea();
                    polyAnsNSH.append("P(x) : ");
                    polyAnsNSH.append(asnNSH);
                    polyAnsNSH.setFont(new Font(mainFont, Font.PLAIN, 20));
                    polyAnsNSH.setEnabled(false);
                    polyAnsNSH.setDisabledTextColor(Color.BLACK);
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
        interpolationPanel.add(NGBSCard);

        //***********************************************************************

        //init Newton Forward Divided Subtractions Equations Card
        title = "Newton Forward Divided Subtractions";
        description = "Get the interpolation function using Newton Forward Divided Subtractions method\n" +
                "It also can get Newton Forward Divided Subtractions Table values";
        button = "Enter";
        ActionListener enterNFDS = e -> System.out.println("Entered");
        JPanel NFDSCard = createCard(title, description, button, enterNFDS);
        interpolationPanel.add(NFDSCard);

        //***********************************************************************

        //init Newton Backward Divided Subtractions Equations Card
        title = "Newton Backward Divided Subtractions";
        description = "Get the interpolation function using Newton Backward Divided Subtractions method\n" +
                "It also can get Newton Backward Divided Subtractions Table values";
        button = "Enter";
        ActionListener enterNDBS = e -> System.out.println("Entered");
        JPanel NFBSCard = createCard(title, description, button, enterNDBS);
        interpolationPanel.add(NFBSCard);

        //***********************************************************************

        //init Least-Squares Card
        title = "Least-Squares";
        description = "Get the interpolation function using Least-Squares method";
        button = "Enter";
        ActionListener enterLeastSquares = e -> System.out.println("Entered");
        JPanel leastSquaresCard = createCard(title, description, button, enterLeastSquares);
        interpolationPanel.add(leastSquaresCard);


        //***********************************************************************

        //init Spline Card
        title = "Spline";
        description = "Get the interpolation function using Spline method";
        button = "Enter";
        ActionListener enterSpline = e -> System.out.println("Entered");
        JPanel splineCard = createCard(title, description, button, enterSpline);
        interpolationPanel.add(splineCard);

    }

    private void initIntegralPanel() {
        integralPanel = new JPanel();
        integralPanel.setName("Integral");
        integralPanel.setPreferredSize(mainFrame.getSize());
        integralPanel.setBackground(new Color(100, 100, 100));
        GridLayout startLayout = new GridLayout(3, 2);
        startLayout.setHgap(5);
        startLayout.setVgap(5);
        integralPanel.setLayout(startLayout);

        //***********************************************************************

        //init Rectangular Card
        String title = "Rectangular";
        String description = "Calculates the integral using the rectangular method";
        String button = "Enter";
        ActionListener enterRect = e -> {
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel rectCard = createCard(title, description, button, enterRect);
        integralPanel.add(rectCard);

        //***********************************************************************

        //init Trapezoidal Card
        title = "Trapezoidal";
        description = "Calculates the integral using the trapezoidal method";
        button = "Enter";
        ActionListener enterTraps = e -> {
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel trapsCard = createCard(title, description, button, enterTraps);
        integralPanel.add(trapsCard);

        //***********************************************************************

        //init Simpson 1/3 Card
        title = "Simpson 1/3";
        description = "Calculates the integral using Simpson's 1/3 method";
        button = "Enter";
        ActionListener enterSimpson3 = e -> {
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel simpson3Card = createCard(title, description, button, enterSimpson3);
        integralPanel.add(simpson3Card);

        //***********************************************************************

        //init Simpson 3/8 Card
        title = "Simpson 3/8";
        description = "Calculates the integral using Simpson's 3/8 method";
        button = "Enter";
        ActionListener enterSimpson8 = e -> {
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel simpson8Card = createCard(title, description, button, enterSimpson8);
        integralPanel.add(simpson8Card);

        //***********************************************************************

        //init Paul Card
        title = "Paul";
        description = "Calculates the integral using Paul's method";
        button = "Enter";
        ActionListener enterPaul = e -> System.out.println("Entered");
        JPanel paulCard = createCard(title, description, button, enterPaul);
        integralPanel.add(paulCard);

        //***********************************************************************

    }

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
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel CFBSCard = createCard(title, description, button, enterCFBS);
        differentiationPanel.add(CFBSCard);

        //***********************************************************************
    }

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
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel eulerCard = createCard(title, description, button, enterEuler);
        differentialEquationsPanel.add(eulerCard);

        //***********************************************************************

        //init Taylor Card
        title = "Taylor";
        description = "Solving a differential equation using the Taylor method";
        button = "Enter";
        ActionListener enterTaylor = e -> {
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel taylorCard = createCard(title, description, button, enterTaylor);
        differentialEquationsPanel.add(taylorCard);

        //***********************************************************************

        //init Modified Euler Card
        title = "Modified Euler";
        description = "Solving a differential equation using the Modified Euler method";
        button = "Enter";
        ActionListener enterModifiedEuler = e -> {
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel modifiedEulerCard = createCard(title, description, button, enterModifiedEuler);
        differentialEquationsPanel.add(modifiedEulerCard);


        //***********************************************************************

        //init Hein Card
        title = "Hein";
        description = "Solving a differential equation using the Hein method";
        button = "Enter";
        ActionListener enterHein = e -> {
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel heinCard = createCard(title, description, button, enterHein);
        differentialEquationsPanel.add(heinCard);

        //***********************************************************************

        //init Ralston Card
        title = "Ralston";
        description = "Solving a differential equation using the Ralston method";
        button = "Enter";
        ActionListener enterRalston = e -> {
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel ralstonCard = createCard(title, description, button, enterRalston);
        differentialEquationsPanel.add(ralstonCard);

        //***********************************************************************

        //init Runge_Kutta Card
        title = "Runge-Kutta";
        description = "Solving a differential equation using the Runge-Kutta method";
        button = "Enter";
        ActionListener enterRunge_Kutta = e -> {
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel runge_KuttaCard = createCard(title, description, button, enterRunge_Kutta);
        differentialEquationsPanel.add(runge_KuttaCard);

        //***********************************************************************
    }

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
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel bisectionCard = createCard(title, description, button, enterBisection);
        nonLinearEquationsPanel.add(bisectionCard);

        //***********************************************************************

        //init False Position Card
        title = "False Position";
        description = "Solving non-linear equations using the False Position method";
        button = "Enter";
        ActionListener enterFalsePosition = e -> {
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel falsePositionCard = createCard(title, description, button, enterFalsePosition);
        nonLinearEquationsPanel.add(falsePositionCard);

        //***********************************************************************

        //init Secant Card
        title = "Secant";
        description = "Solving non-linear equations using the Secant method";
        button = "Enter";
        ActionListener enterSecant = e -> {
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel secantCard = createCard(title, description, button, enterSecant);
        nonLinearEquationsPanel.add(secantCard);


        //***********************************************************************

        //init Newton_Raphson Card
        title = "Newton-Raphson";
        description = "Solving non-linear equations using the Newton-Raphson method";
        button = "Enter";
        ActionListener enterNewton_Raphson = e -> {
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel newton_RaphsonCard = createCard(title, description, button, enterNewton_Raphson);
        nonLinearEquationsPanel.add(newton_RaphsonCard);

        //***********************************************************************

        //init Halley Card
        title = "Halley";
        description = "Solving non-linear equations using the Halley method";
        button = "Enter";
        ActionListener enterHalley = e -> {
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel halleyCard = createCard(title, description, button, enterHalley);
        nonLinearEquationsPanel.add(halleyCard);

        //***********************************************************************

        //init Fixed Point Iteration Card
        title = "Fixed Point Iteration";
        description = "Solving non-linear equations using the Fixed Point Iteration method";
        button = "Enter";
        ActionListener enterFixedPointIteration = e -> {
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel fixedPointIterationCard = createCard(title, description, button, enterFixedPointIteration);
        nonLinearEquationsPanel.add(fixedPointIterationCard);

        //***********************************************************************
    }

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
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel valueAtCard = createCard(title, description, button, enterValueAt);
        polynomialsPanel.add(valueAtCard);

        //***********************************************************************

        //init Divide on (x - a) Card
        title = "Divide on (x - a)";
        description = "Divides the polynomial by (x - c) using Horner's method and returns the resulting polynomial";
        button = "Enter";
        ActionListener enterDivideOn = e -> {
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel divideOnCard = createCard(title, description, button, enterDivideOn);
        polynomialsPanel.add(divideOnCard);

        //***********************************************************************

        //init Diff at x Card
        title = "Diff at x";
        description = "Calculates the value of the derivative of the polynomial at the specified value of x and rank using Horner's method";
        button = "Enter";
        ActionListener enterDiffAt = e -> {
            panelsStack.add(chooseFunctionPanel);
            updateMainPanel();
        };
        JPanel diffAtCard = createCard(title, description, button, enterDiffAt);
        polynomialsPanel.add(diffAtCard);

        //***********************************************************************

    }

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
            cardDescription.setText("<html>" +
                    "Supported Functions : <br>" +
                    " <b>Polynomials </b> : x^3 + 9*x^2 -5*x +10 <br>" +
                    " <b>Exponential </b> : exp(x^2) .. exp(1/x) <br>" +
                    " <b>Binary Logarithm </b> : log(3*x) .. log(-x) <br>" +
                    " <b>Trigonometric Functions </b> : sin(x),cos(x),tan(x <br>" +
                    " <b>Inverse Trigonometric Functions </b> : asin(x),acos(x),atan(x) <br>" +
                    " <b>Hyperbolic Trigonometric Functions </b> : sinh(x),cosh(x),tanh(x) <br>" +
                    "</html>");
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
                String n = String.valueOf((int) enterNSp.getValue());
                try {
                    PointsFunction pointsFunc = expFunc.toPointsFunction(a, b, n);
                    ArrayList<Double> xp = pointsFunc.getXp();
                    ArrayList<Double> yp = pointsFunc.getYp();
                    pointsText.setText("");
                    for (int i = 0; i < xp.size(); i++) {
                        String sb = "x" + i + " = " + xp.get(i) +
                                '\t' +
                                "y" + i + " = " + yp.get(i);
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
            cardDescription.setText("<html>" +
                    "Supported Points Type : <br>" +
                    " <b>Double </b> : 1.919239 <br>" +
                    " <b>Integer </b> : 12392 <br>" +
                    " <b>PI </b> : pi, 2*pi, pi/4 <br>" +
                    "</html>");
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
                ArrayList<Double> xp = new ArrayList<>();
                ArrayList<Double> yp = new ArrayList<>();
                try {
                    for (JTextField xField : xFields) {
                        String xFiledContent = xField.getText();
                        double x = EvaluateString.evaluate(xFiledContent);
                        xp.add(x);
                    }
                    for (JTextField yField : yFields) {
                        String yFiledContent = yField.getText();
                        double y = EvaluateString.evaluate(yFiledContent);
                        yp.add(y);
                    }
                    PointsFunction pointsFunc = new PointsFunction(xp, yp);
                    pointsText.setText("");
                    for (int i = 0; i < xp.size(); i++) {
                        String sb = "x" + i + " = " + xp.get(i) +
                                '\t' +
                                "y" + i + " = " + yp.get(i);
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
            continueButton.addActionListener(e -> {
                panelsStack.pop();
                updateMainPanel();
            });

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
            cardDescription.setText("<html>" +
                    "Supported Coefficients Type : <br>" +
                    " <b>Double </b> : 1.919239 <br>" +
                    " <b>Integer </b> : 12392 <br>" +
                    " <b>PI </b> : pi, 2*pi, pi/4 <br>" +
                    "</html>");
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


            //JOptionPane.showConfirmDialog(null,enterN,"e",JOptionPane.DEFAULT_OPTION);

            // input x and y
            JLabel enterPointsTitle = new JLabel("Enter Coefficients : ");
            enterPointsTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0)); // Adjust the top and bottom padding

            JScrollPane enterPointsScroll = new JScrollPane();
            enterPointsScroll.setPreferredSize(new Dimension(220, 150));


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

                enterPointsScroll.setViewportView(coeffsPanel); // Set the pointsPanel as the viewport's view component
                enterPointsScroll.revalidate(); // Revalidate the scroll pane to update its content

                // Add a DocumentListener to each text field;
                ArrayList<JTextField> fields = new ArrayList<>(coeffsFields);
                fields.add(enterAField);
                fields.add(enterBField);
                addDocumentListenerToFields(generateButton, fields);
            });

            generateButton.addActionListener(e -> {
                ArrayList<Double> coeffs = new ArrayList<>();
                try {
                    for (JTextField coeffField : coeffsFields) {
                        String coeffFiledContent = coeffField.getText();
                        double coeff = EvaluateString.evaluate(coeffFiledContent);
                        coeffs.add(coeff);
                    }
                    Polynomial poly = new Polynomial(coeffs);
                    String a = enterAField.getText();
                    String b = enterBField.getText();
                    String n = String.valueOf((int) enterNSp.getValue());
                    PointsFunction ptsFunc = poly.toPointsFunction(a, b, n);
                    ArrayList<Double> xp = ptsFunc.getXp();
                    ArrayList<Double> yp = ptsFunc.getYp();
                    pointsText.setText("");
                    for (int i = 0; i < xp.size(); i++) {
                        String sb = "x" + i + " = " + xp.get(i) +
                                '\t' +
                                "y" + i + " = " + yp.get(i);
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

    private void changeColorAnime(Component comp, Color color) {
        Color srcColor = comp.getBackground();

        int duration = 1000; // Animation duration in milliseconds
        int steps = 30; // Number of animation steps

        int srcRed = srcColor.getRed();
        int srcGreen = srcColor.getGreen();
        int srcBlue = srcColor.getBlue();

        int destRed = color.getRed();
        int destGreen = color.getGreen();
        int destBlue = color.getBlue();

        int redStep = (destRed - srcRed) / steps;
        int greenStep = (destGreen - srcGreen) / steps;
        int blueStep = (destBlue - srcBlue) / steps;

        final int[] currentStep = {0};

        javax.swing.Timer timer = new javax.swing.Timer(duration / steps, e -> {
            int newRed = srcRed + redStep * currentStep[0];
            int newGreen = srcGreen + greenStep * currentStep[0];
            int newBlue = srcBlue + blueStep * currentStep[0];

            comp.setBackground(new Color(newRed, newGreen, newBlue));
            mainFrame.repaint();

            currentStep[0]++;

            if (currentStep[0] > steps) {
                ((javax.swing.Timer) e.getSource()).stop();
            }
        });
        timer.start();
    }

    private void updateMainPanel() {
        mainFrame.getContentPane().removeAll(); // Remove all existing components
        mainFrame.getContentPane().add(panelsStack.peek()); // Add the newContentPanel
        mainFrame.revalidate(); // Revalidate the frame to update the layout
        mainFrame.repaint(); // Repaint the frame to reflect the changes
        backButton.setEnabled(panelsStack.size() > 1);
        homeButton.setEnabled(panelsStack.size() > 1);
    }


    // Add a DocumentListener to each text field
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

    // Update the enabled state of the button based on text fields' content
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
}
