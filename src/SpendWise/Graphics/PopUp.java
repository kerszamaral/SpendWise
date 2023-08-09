package SpendWise.Graphics;

import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import SpendWise.Utils.GraphicsUtils;
import SpendWise.Utils.Enums.PanelOrder;
import SpendWise.Utils.Graphics.Colors;

public abstract class PopUp extends JFrame implements BlankPanels, Colors {
    protected final static Font STD_FONT = GraphicsUtils.STD_FONT;
    protected final static Font STD_FONT_BOLD = GraphicsUtils.STD_FONT_BOLD;
    protected final static Dimension DEFAULT_FIELD_SIZE = GraphicsUtils.DEFAULT_FIELD_SIZE;

    private final int WIDTH = 600;
    private final int HEIGHT = 400;
    private Component originalWindow;
    private String title;
    protected JPanel[] blankPanels;

    public PopUp(Component originalWindow, String title) {
        this.originalWindow = originalWindow;
        this.title = title;
        this.blankPanels = new JPanel[PanelOrder.values().length];
        this.initialize();
    }

    public abstract void run();

    protected void initialize() {
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
