package SpendWise.Graphics;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import SpendWise.Utils.PanelOrder;

public abstract class Screen extends JPanel implements BlankPanels {
    protected final static Color BACKGROUND_COLOR = GraphicsUtils.BACKGROUND_COLOR;
    protected final static Font STD_FONT = GraphicsUtils.STD_FONT;
    protected final static Font STD_FONT_BOLD = GraphicsUtils.STD_FONT_BOLD;

    protected JPanel[] blankPanels;

    protected void initialize() {
        this.setLayout(new BorderLayout());
        this.setBackground(BACKGROUND_COLOR);

        blankPanels = GraphicsUtils.createOffsets(this, 50, 50, 100, 0);

        blankPanels[PanelOrder.CENTRAL.ordinal()] = new JPanel();
        JPanel pnlMiddle = blankPanels[PanelOrder.CENTRAL.ordinal()];
        pnlMiddle.setLayout(new BoxLayout(pnlMiddle, BoxLayout.Y_AXIS));
        pnlMiddle.setBackground(Color.WHITE);
        this.add(pnlMiddle, BorderLayout.CENTER);
    }

    public void refresh() {
        this.revalidate();
        this.repaint();
    }

    /**
     * @return the backgroundColor
     */
    public static Color getBackgroundColor() {
        return BACKGROUND_COLOR;
    }

    /**
     * @param panel the panel to get
     * @return the blankPanels
     */
    public JPanel getBlankPanel(PanelOrder panel) {
        return blankPanels[panel.ordinal()];
    }

    /**
     * @return the stdFont
     */
    public static Font getStdFont() {
        return STD_FONT;
    }

    /**
     * @return the stdFontBold
     */
    public static Font getStdFontBold() {
        return STD_FONT_BOLD;
    }
}
