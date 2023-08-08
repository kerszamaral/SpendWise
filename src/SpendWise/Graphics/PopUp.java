package SpendWise.Graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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

    /**
     * @param panel the panel to get
     * @return the blankPanels
     */
    public JPanel getBlankPanel(PanelOrder panel) {
        return blankPanels[panel.ordinal()];
    }
}
