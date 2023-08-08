package SpendWise.Graphics;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import SpendWise.Utils.PanelOrder;

public abstract class Screen extends JPanel {
    public final static Color BACKGROUND_COLOR = new Color(98, 210, 162);
    public final static Font STD_FONT = new Font("Arial", Font.PLAIN, 14);
    public final static Font STD_FONT_BOLD = new Font("Arial", Font.BOLD, 14);

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
        initializeBlankPanel(blankPanelEast, 0, 100);
        this.add(blankPanelEast, BorderLayout.EAST);

        blankPanels[PanelOrder.SOUTH.ordinal()] = new JPanel();
        JPanel blankPanelSouth = blankPanels[PanelOrder.SOUTH.ordinal()];
        initializeBlankPanel(blankPanelSouth, 100, 50);
        this.add(blankPanelSouth, BorderLayout.SOUTH);
    }

    public static void initializeBlankPanel(JPanel blankPanel, int width, int height) {
        blankPanel.setBackground(BACKGROUND_COLOR);
        blankPanel.setPreferredSize(new Dimension(width, height));
    }

    public static void createOffsets(JPanel panel, int northOffSet, int southOffSet, int westOffSet, int eastOffSet) {
        JPanel pnlNorth = new JPanel();
        initializeBlankPanel(pnlNorth, 1, northOffSet);
        panel.add(pnlNorth, BorderLayout.NORTH);

        JPanel pnlWest = new JPanel();
        initializeBlankPanel(pnlWest, westOffSet, 1);
        panel.add(pnlWest, BorderLayout.WEST);

        JPanel pnlEast = new JPanel();
        initializeBlankPanel(pnlEast, eastOffSet, 1);
        panel.add(pnlEast, BorderLayout.EAST);

        JPanel pnlSouth = new JPanel();
        initializeBlankPanel(pnlSouth, 1, southOffSet);
        panel.add(pnlSouth, BorderLayout.SOUTH);
    }

    public static void createButton(JPanel panel, String text, ActionListener actionListener) {
        // Creates the panel that is going to the south of the screen
        JPanel pnlSouth = new JPanel();
        pnlSouth.setLayout(new BorderLayout());

        createOffsets(pnlSouth, 10, 10, 550, 0);

        // Creates the button itself and adds it to the east panel
        JButton btnEdit = new JButton(text);
        btnEdit.setBackground(Color.BLACK);
        btnEdit.setForeground(BACKGROUND_COLOR);
        btnEdit.addActionListener(actionListener);
        pnlSouth.add(btnEdit, BorderLayout.CENTER);

        // And, finnaly, add the south panel to the screen
        panel.add(pnlSouth, BorderLayout.SOUTH);
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
