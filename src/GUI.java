import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.Stack;

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

    private ImageIcon mainIcon;
    private ImageIcon backIcon;

    private JButton backButton;

    private String mainFont = "Times New Roman";
    private String secondFont = "Times New Roman";
    private String buttonFont = "Times New Roman";

    private Stack<JPanel> panelsStack;

    public GUI() {
        //Apply nimbus theme
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (UnsupportedLookAndFeelException | InstantiationException | IllegalAccessException |
                 ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        initIcons();
        initMainFrame();
        initMenuBar();
        initPanels();
        updatePanel();
        mainFrame.pack();
    }

    private void initIcons() {
        mainIcon = new ImageIcon(Objects.requireNonNull(GUI.class.getClassLoader().getResource("Icons/function.png")));
        backIcon = new ImageIcon(Objects.requireNonNull(GUI.class.getClassLoader().getResource("Icons/left-arrow.png")));
    }

    private void initMainFrame() {
        mainFrame = new JFrame();
        mainFrame.setVisible(true);
        mainFrame.setTitle("Numeric Calculator");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setIconImage(mainIcon.getImage());
        mainFrame.setMinimumSize(new Dimension(940, 620));
        mainFrame.setLocationRelativeTo(null);
    }

    private void initMenuBar() {
        mainMenuBar = new JMenuBar();
        mainMenuBar.setLayout(new BorderLayout());

        backButton = new JButton();
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelsStack.pop();
                updatePanel();
            }
        });
        backButton.setPreferredSize(new Dimension(30, 30));
        backButton.setIcon(backIcon);
        backButton.setFocusPainted(false);

        mainMenuBar.add(backButton, BorderLayout.WEST);
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
        ActionListener enterInterpolation = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelsStack.add(chooseFunctionPanel);
                updatePanel();
            }
        };
        JPanel interpolationCard = createCard(title, description, button, enterInterpolation);
        startPanel.add(interpolationCard);

        //***********************************************************************

        //init Integral Card
        title = "Integral";
        description = "Integrate an entered function using numeric integration ways";
        button = "Enter";
        ActionListener enterIntegral = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelsStack.add(chooseFunctionPanel);
                updatePanel();
            }
        };
        JPanel integralCard = createCard(title, description, button, enterIntegral);
        startPanel.add(integralCard);

        //***********************************************************************

        //init Differentiation Card
        title = "Differentiation";
        description = "Differentiate an entered function using numeric differentiation ways";
        button = "Enter";
        ActionListener enterDifferentiation = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelsStack.add(chooseFunctionPanel);
                updatePanel();
            }
        };
        JPanel diffCard = createCard(title, description, button, enterDifferentiation);
        startPanel.add(diffCard);

        //***********************************************************************

        //init Differential Equations Card
        title = "Differential Equations";
        description = "Solve an entered differential equations using numeric ways";
        button = "Enter";
        ActionListener enterDiffEQ = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Entered");
            }
        };
        JPanel diffEQCard = createCard(title, description, button, enterDiffEQ);
        startPanel.add(diffEQCard);

        //***********************************************************************

        //init Non-Linear Equations Card
        title = "Non-Linear Equations";
        description = "Solve an entered non-Linear equations using numeric ways";
        button = "Enter";
        ActionListener enterNonLinEQ = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Entered");
            }
        };
        JPanel nonLinEQCard = createCard(title, description, button, enterNonLinEQ);
        startPanel.add(nonLinEQCard);


        //***********************************************************************

        //init System Of Non-Linear Equations Card
        title = "System Of Non-Linear Equations";
        description = "Solve an entered system of non-Linear equations using numeric ways";
        button = "Enter";
        ActionListener enterSysNonLinEQ = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Entered");
            }
        };
        JPanel sysOfNonLinEQCard = createCard(title, description, button, enterSysNonLinEQ);
        startPanel.add(sysOfNonLinEQCard);


        //***********************************************************************

        //init Differential Equations Card
        title = "Polynomials";
        description = "Do several tasks on an entered polynomial using numeric ways";
        button = "Enter";
        ActionListener enterPolys = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Entered");
            }
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
        GridLayout startLayout = new GridLayout(1, 2);
        startLayout.setHgap(5);
        startLayout.setVgap(5);
        chooseFunctionPanel.setLayout(startLayout);


        ActionListener enterExpressionFunction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Enter expr");
            }
        };
        JPanel expressionFunctionCard = createCard("Expression Function",
                "Functions like x^2+3, sin(x), exp(x) ...", "Enter", enterExpressionFunction);


        ActionListener enterPointsFunction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Enter points");
            }
        };
        JPanel pointsFunctionCard = createCard("Points Function",
                "Functions like x0=.. y0=.. , x1=.. y1=.. ", "Enter", enterPointsFunction);


        chooseFunctionPanel.add(expressionFunctionCard);
        chooseFunctionPanel.add(pointsFunctionCard);
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
        ActionListener enterGeneralMethod = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelsStack.add(chooseFunctionPanel);
                updatePanel();
            }
        };
        JPanel generalMethodCard = createCard(title, description, button, enterGeneralMethod);
        interpolationPanel.add(generalMethodCard);

        //***********************************************************************

        //init Lagrange Card
        title = "Lagrange";
        description = "Get the Interpolation Function using Lagrange method using Lagrange Polynomials";
        button = "Enter";
        ActionListener enterLagrange = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelsStack.add(chooseFunctionPanel);
                updatePanel();
            }
        };
        JPanel lagrangeCard = createCard(title, description, button, enterLagrange);
        interpolationPanel.add(lagrangeCard);

        //***********************************************************************

        //init Newton-Gregory Forward Subtraction Card
        title = "Newton-Gregory Forward Subtraction";
        description = "Get the interpolation function using Newton-Gregory Forward Subtractions method\n" +
                "It also can get Newton-Gregory Forward Subtractions Table values";
        button = "Enter";
        ActionListener enterNGFS = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelsStack.add(chooseFunctionPanel);
                updatePanel();
            }
        };
        JPanel NGFSCard = createCard(title, description, button, enterNGFS);
        interpolationPanel.add(NGFSCard);

        //***********************************************************************

        //init Newton-Gregory Backward Subtraction Card
        title = "Newton-Gregory Backward Subtraction";
        description = "Get the interpolation function using Newton-Gregory Backward Subtractions method\n" +
                "It also can get Newton-Gregory Backward Subtractions Table values";
        button = "Enter";
        ActionListener enterNGBS = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelsStack.add(chooseFunctionPanel);
                updatePanel();
            }
        };
        JPanel NGBSCard = createCard(title, description, button, enterNGBS);
        interpolationPanel.add(NGBSCard);

        //***********************************************************************

        //init Newton Forward Divided Subtractions Equations Card
        title = "Newton Forward Divided Subtractions";
        description = "Get the interpolation function using Newton Forward Divided Subtractions method\n" +
                "It also can get Newton Forward Divided Subtractions Table values";
        button = "Enter";
        ActionListener enterNFDS = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Entered");
            }
        };
        JPanel NFDSCard = createCard(title, description, button, enterNFDS);
        interpolationPanel.add(NFDSCard);

        //***********************************************************************

        //init Newton Backward Divided Subtractions Equations Card
        title = "Newton Backward Divided Subtractions";
        description = "Get the interpolation function using Newton Backward Divided Subtractions method\n" +
                "It also can get Newton Backward Divided Subtractions Table values";
        button = "Enter";
        ActionListener enterNDBS = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Entered");
            }
        };
        JPanel NFBSCard = createCard(title, description, button, enterNDBS);
        interpolationPanel.add(NFBSCard);

        //***********************************************************************

        //init Least-Squares Card
        title = "Least-Squares";
        description = "Get the interpolation function using Least-Squares method";
        button = "Enter";
        ActionListener enterLeastSquares = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Entered");
            }
        };
        JPanel leastSquaresCard = createCard(title, description, button, enterLeastSquares);
        interpolationPanel.add(leastSquaresCard);


        //***********************************************************************

        //init Spline Card
        title = "Spline";
        description = "Get the interpolation function using Spline method";
        button = "Enter";
        ActionListener enterSpline = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Entered");
            }
        };
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
        ActionListener enterRect = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelsStack.add(chooseFunctionPanel);
                updatePanel();
            }
        };
        JPanel rectCard = createCard(title, description, button, enterRect);
        integralPanel.add(rectCard);

        //***********************************************************************

        //init Trapezoidal Card
        title = "Trapezoidal";
        description = "Calculates the integral using the trapezoidal method";
        button = "Enter";
        ActionListener enterTraps = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelsStack.add(chooseFunctionPanel);
                updatePanel();
            }
        };
        JPanel trapsCard = createCard(title, description, button, enterTraps);
        integralPanel.add(trapsCard);

        //***********************************************************************

        //init Simpson 1/3 Card
        title = "Simpson 1/3";
        description = "Calculates the integral using Simpson's 1/3 method";
        button = "Enter";
        ActionListener enterSimpson3 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelsStack.add(chooseFunctionPanel);
                updatePanel();
            }
        };
        JPanel simpson3Card = createCard(title, description, button, enterSimpson3);
        integralPanel.add(simpson3Card);

        //***********************************************************************

        //init Simpson 3/8 Card
        title = "Simpson 3/8";
        description = "Calculates the integral using Simpson's 3/8 method";
        button = "Enter";
        ActionListener enterSimpson8 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelsStack.add(chooseFunctionPanel);
                updatePanel();
            }
        };
        JPanel simpson8Card = createCard(title, description, button, enterSimpson8);
        integralPanel.add(simpson8Card);

        //***********************************************************************

        //init Paul Card
        title = "Paul";
        description = "Calculates the integral using Paul's method";
        button = "Enter";
        ActionListener enterPaul = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Entered");
            }
        };
        JPanel paulCard = createCard(title, description, button, enterPaul);
        integralPanel.add(paulCard);

        //***********************************************************************

    }

    private void initDiffPanel() {
        differentiationPanel = new JPanel();
        differentiationPanel.setName("Differential");
        differentiationPanel.setPreferredSize(mainFrame.getSize());
        differentiationPanel.setBackground(new Color(100, 100, 100));
        GridLayout startLayout = new GridLayout(2, 2);
        startLayout.setHgap(5);
        startLayout.setVgap(5);
        differentiationPanel.setLayout(startLayout);

        //***********************************************************************

        //init Lagrange Card
        String title = "Lagrange";
        String description = "Get Differential Function As Polynomial using Lagrange";
        String button = "Enter";
        ActionListener enterLagrange = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelsStack.add(chooseFunctionPanel);
                updatePanel();
            }
        };
        JPanel lagrangeCard = createCard(title, description, button, enterLagrange);
        differentiationPanel.add(lagrangeCard);

        //***********************************************************************

        //init  Newton-Gregory Forward Subtractions Card
        title = "Newton-Gregory Forward Subtractions";
        description = "Get Differential Function As Polynomial using Newton-Gregory Forward Subtractions";
        button = "Enter";
        ActionListener enterNGFS = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelsStack.add(chooseFunctionPanel);
                updatePanel();
            }
        };
        JPanel NGFSCard = createCard(title, description, button, enterNGFS);
        differentiationPanel.add(NGFSCard);

        //***********************************************************************

        //init Newton-Gregory Backward Subtractions Card
        title = "Newton-Gregory Backward Subtractions";
        description = "Get Differential Function As Polynomial using Newton-Gregory Backward Subtractions";
        button = "Enter";
        ActionListener enterNGBS = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelsStack.add(chooseFunctionPanel);
                updatePanel();
            }
        };
        JPanel NGBSCard = createCard(title, description, button, enterNGBS);
        differentiationPanel.add(NGBSCard);


        //***********************************************************************

        //init Central,Forward,Backward Subtractions Card
        title = "Central,Forward,Backward Subtractions";
        description = "Calculate the differential function of a specified value using Central/Forward/Backward Subtractions";
        button = "Enter";
        ActionListener enterCFBS = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelsStack.add(chooseFunctionPanel);
                updatePanel();
            }
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
        ActionListener enterEuler = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelsStack.add(chooseFunctionPanel);
                updatePanel();
            }
        };
        JPanel eulerCard = createCard(title, description, button, enterEuler);
        differentialEquationsPanel.add(eulerCard);

        //***********************************************************************

        //init Taylor Card
        title = "Taylor";
        description = "Solving a differential equation using the Taylor method";
        button = "Enter";
        ActionListener enterTaylor = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelsStack.add(chooseFunctionPanel);
                updatePanel();
            }
        };
        JPanel taylorCard = createCard(title, description, button, enterTaylor);
        differentialEquationsPanel.add(taylorCard);

        //***********************************************************************

        //init Modified Euler Card
        title = "Modified Euler";
        description = "Solving a differential equation using the Modified Euler method";
        button = "Enter";
        ActionListener enterModifiedEuler = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelsStack.add(chooseFunctionPanel);
                updatePanel();
            }
        };
        JPanel modifiedEulerCard = createCard(title, description, button, enterModifiedEuler);
        differentialEquationsPanel.add(modifiedEulerCard);


        //***********************************************************************

        //init Hein Card
        title = "Hein";
        description = "Solving a differential equation using the Hein method";
        button = "Enter";
        ActionListener enterHein = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelsStack.add(chooseFunctionPanel);
                updatePanel();
            }
        };
        JPanel heinCard = createCard(title, description, button, enterHein);
        differentialEquationsPanel.add(heinCard);

        //***********************************************************************

        //init Ralston Card
        title = "Ralston";
        description = "Solving a differential equation using the Ralston method";
        button = "Enter";
        ActionListener enterRalston = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelsStack.add(chooseFunctionPanel);
                updatePanel();
            }
        };
        JPanel ralstonCard = createCard(title, description, button, enterRalston);
        differentialEquationsPanel.add(ralstonCard);

        //***********************************************************************

        //init Runge_Kutta Card
        title = "Runge-Kutta";
        description = "Solving a differential equation using the Runge-Kutta method";
        button = "Enter";
        ActionListener enterRunge_Kutta = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelsStack.add(chooseFunctionPanel);
                updatePanel();
            }
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
        ActionListener enterBisection = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelsStack.add(chooseFunctionPanel);
                updatePanel();
            }
        };
        JPanel bisectionCard = createCard(title, description, button, enterBisection);
        nonLinearEquationsPanel.add(bisectionCard);

        //***********************************************************************

        //init False Position Card
        title = "False Position";
        description = "Solving non-linear equations using the False Position method";
        button = "Enter";
        ActionListener enterFalsePosition = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelsStack.add(chooseFunctionPanel);
                updatePanel();
            }
        };
        JPanel falsePositionCard = createCard(title, description, button, enterFalsePosition);
        nonLinearEquationsPanel.add(falsePositionCard);

        //***********************************************************************

        //init Secant Card
        title = "Secant";
        description = "Solving non-linear equations using the Secant method";
        button = "Enter";
        ActionListener enterSecant = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelsStack.add(chooseFunctionPanel);
                updatePanel();
            }
        };
        JPanel secantCard = createCard(title, description, button, enterSecant);
        nonLinearEquationsPanel.add(secantCard);


        //***********************************************************************

        //init Newton_Raphson Card
        title = "Newton-Raphson";
        description = "Solving non-linear equations using the Newton-Raphson method";
        button = "Enter";
        ActionListener enterNewton_Raphson = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelsStack.add(chooseFunctionPanel);
                updatePanel();
            }
        };
        JPanel newton_RaphsonCard = createCard(title, description, button, enterNewton_Raphson);
        nonLinearEquationsPanel.add(newton_RaphsonCard);

        //***********************************************************************

        //init Halley Card
        title = "Halley";
        description = "Solving non-linear equations using the Halley method";
        button = "Enter";
        ActionListener enterHalley = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelsStack.add(chooseFunctionPanel);
                updatePanel();
            }
        };
        JPanel halleyCard = createCard(title, description, button, enterHalley);
        nonLinearEquationsPanel.add(halleyCard);

        //***********************************************************************

        //init Fixed Point Iteration Card
        title = "Fixed Point Iteration";
        description = "Solving non-linear equations using the Fixed Point Iteration method";
        button = "Enter";
        ActionListener enterFixedPointIteration = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelsStack.add(chooseFunctionPanel);
                updatePanel();
            }
        };
        JPanel fixedPointIterationCard = createCard(title, description, button, enterFixedPointIteration);
        nonLinearEquationsPanel.add(fixedPointIterationCard);

        //***********************************************************************
    }



    private void updatePanel() {
        mainFrame.getContentPane().removeAll(); // Remove all existing components
        mainFrame.getContentPane().add(nonLinearEquationsPanel); // Add the newContentPanel
        mainFrame.revalidate(); // Revalidate the frame to update the layout
        mainFrame.repaint(); // Repaint the frame to reflect the changes
        if (panelsStack.size() > 1) {
            backButton.setEnabled(true);
        } else {
            backButton.setEnabled(false);
        }

    }
}
