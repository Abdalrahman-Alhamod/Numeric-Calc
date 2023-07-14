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
        //initInterpolationPanel();
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
        startPanel = new JPanel();
        startPanel.setName("Interpolation");
        startPanel.setPreferredSize(mainFrame.getSize());
        startPanel.setBackground(new Color(100, 100, 100));
        GridLayout startLayout = new GridLayout(4, 2);
        startLayout.setHgap(5);
        startLayout.setVgap(5);
        startPanel.setLayout(startLayout);

        //***********************************************************************

        //init Interpolation Card
        String title = "General Method";
        String description = "...";
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
        title = "Lagrange";
        description = "...";
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
        title = "Newton-Gregory Subtraction";
        description = "...";
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
        title = "Newton Divided Subtractions";
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

    private void updatePanel() {
        mainFrame.getContentPane().removeAll(); // Remove all existing components
        mainFrame.getContentPane().add(panelsStack.peek()); // Add the newContentPanel
        mainFrame.revalidate(); // Revalidate the frame to update the layout
        mainFrame.repaint(); // Repaint the frame to reflect the changes
        if (panelsStack.size() > 1) {
            backButton.setEnabled(true);
        } else {
            backButton.setEnabled(false);
        }

    }
}
