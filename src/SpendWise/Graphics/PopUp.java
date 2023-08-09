package SpendWise.Graphics;

import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import SpendWise.Utils.Enums.PanelOrder;
import SpendWise.Utils.Graphics.BlankPanels;
import SpendWise.Utils.Graphics.Colors;
import SpendWise.Utils.Graphics.Fonts;
import SpendWise.Utils.Graphics.Sizes;

public abstract class PopUp extends JFrame implements BlankPanels, Colors, Fonts, Sizes {
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
        this.setSize(POPUP_WIDTH, POPUP_HEIGHT);
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
