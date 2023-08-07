package SpendWise.Graphics;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import SpendWise.Utils.PanelOrder;

public abstract class Screen extends JPanel {
    protected final static Color BACKGROUND_COLOR = new Color(98, 210, 162);
    protected JPanel[] blankPanels;

    protected void initialize() {
        this.setLayout(new BorderLayout());
        this.setBackground(BACKGROUND_COLOR);

        blankPanels = new JPanel[PanelOrder.values().length];

        blankPanels[PanelOrder.CENTRAL.ordinal()] = new JPanel();
        JPanel pnlMiddle = blankPanels[PanelOrder.CENTRAL.ordinal()];
        pnlMiddle.setLayout(new BoxLayout(pnlMiddle, BoxLayout.Y_AXIS));
        pnlMiddle.setBackground(Color.WHITE);
        this.add(pnlMiddle, BorderLayout.CENTER);

        blankPanels[PanelOrder.NORTH.ordinal()] = new JPanel();
        JPanel blankPanelNorth = blankPanels[PanelOrder.NORTH.ordinal()];
        initializeBlankPanel(blankPanelNorth, 100, 50);
        this.add(blankPanelNorth, BorderLayout.NORTH);

        blankPanels[PanelOrder.WEST.ordinal()] = new JPanel();
        JPanel blankPanelWest = blankPanels[PanelOrder.WEST.ordinal()];
        initializeBlankPanel(blankPanelWest, 100, 100);
        this.add(blankPanelWest, BorderLayout.WEST);

        blankPanels[PanelOrder.EAST.ordinal()] = new JPanel();
        JPanel blankPanelEast = blankPanels[PanelOrder.EAST.ordinal()];
        initializeBlankPanel(blankPanelEast, 25, 100);
        this.add(blankPanelEast, BorderLayout.EAST);
    }

    public static Color getBackgroundColor(){
        return BACKGROUND_COLOR;
    }

    protected static void initializeBlankPanel(JPanel blankPanel, int width, int height) {
        blankPanel.setBackground(BACKGROUND_COLOR);
        blankPanel.setPreferredSize(new Dimension(width, height));
    }

    public void refresh() {
        this.revalidate();
        this.repaint();
    }

    /**
     * @param panel the panel to get
     * @return the blankPanels
     */
    public JPanel getBlankPanel(PanelOrder panel) {
        return blankPanels[panel.ordinal()];
    }
}
