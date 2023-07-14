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

        //init Interpolation Card
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

        //init General Method Card
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

    private void updatePanel() {
        mainFrame.getContentPane().removeAll(); // Remove all existing components
        mainFrame.getContentPane().add(interpolationPanel); // Add the newContentPanel
        mainFrame.revalidate(); // Revalidate the frame to update the layout
        mainFrame.repaint(); // Repaint the frame to reflect the changes
        if (panelsStack.size() > 1) {
            backButton.setEnabled(true);
        } else {
            backButton.setEnabled(false);
        }

    }
}