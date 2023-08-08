package SpendWise.Graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import SpendWise.Utils.PanelOrder;

public abstract class PopUp extends JFrame {
    protected final static Color BACKGROUND_COLOR = Screen.getBackgroundColor();
    protected final static Font STD_FONT = Screen.getStdFont();
    protected final static Font STD_FONT_BOLD = Screen.getStdFontBold();
    private final int WIDTH = 600;
    private final int HEIGHT = 400;
    private Component originalWindow;
    private String title;
    private JPanel[] blankPanels;

    public PopUp(Component originalWindow, String title) {
        this.originalWindow = originalWindow;
        this.title = title;
        this.blankPanels = new JPanel[PanelOrder.values().length];
        this.initialize();
    }

    public abstract void run();

    protected void initialize() {
        this.setLayout(new BorderLayout());
        this.setTitle(title);
        this.setSize(WIDTH, HEIGHT);
        this.getContentPane().setBackground(BACKGROUND_COLOR);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(originalWindow);
        ImageIcon icon = new ImageIcon("bin/Images/logo.png");
        this.setIconImage(icon.getImage());
        this.setResizable(false);
    }

    public void setCentralPanel(JPanel pnlCentral) {

        blankPanels[PanelOrder.NORTH.ordinal()] = new JPanel();
        JPanel pnlNorth = blankPanels[PanelOrder.NORTH.ordinal()];
        Screen.initializeBlankPanel(pnlNorth, 100, 50);
        pnlNorth.setLayout(new FlowLayout(FlowLayout.CENTER));

        blankPanels[PanelOrder.EAST.ordinal()] = new JPanel();
        JPanel pnlBlankEast = blankPanels[PanelOrder.EAST.ordinal()];
        Screen.initializeBlankPanel(pnlBlankEast, 100, 50);

        blankPanels[PanelOrder.WEST.ordinal()] = new JPanel();
        JPanel pnlBlankWest = blankPanels[PanelOrder.WEST.ordinal()];
        Screen.initializeBlankPanel(pnlBlankWest, 100, 50);

        JPanel pnlMain = new JPanel();
        pnlMain.setLayout(new BorderLayout());
        pnlMain.add(pnlCentral, BorderLayout.CENTER);
        pnlMain.add(pnlNorth, BorderLayout.NORTH);
        pnlMain.add(pnlBlankEast, BorderLayout.EAST);
        pnlMain.add(pnlBlankWest, BorderLayout.WEST);

        this.add(pnlMain);
    }
    
    /**
     * @param panel the panel to get
     * @return the blankPanels
     */
    public JPanel getBlankPanel(PanelOrder panel) {
        return blankPanels[panel.ordinal()];
    }
}
