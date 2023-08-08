package SpendWise.Graphics;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import SpendWise.Utils.PanelOrder;

public abstract class Screen extends JPanel implements BlankPanels {
    public final static Color BACKGROUND_COLOR = new Color(98, 210, 162);
    public final static Font STD_FONT = new Font("Arial", Font.PLAIN, 14);
    public final static Font STD_FONT_BOLD = new Font("Arial", Font.BOLD, 14);

    protected JPanel[] blankPanels;

    protected void initialize() {
        this.setLayout(new BorderLayout());
        this.setBackground(BACKGROUND_COLOR);

        blankPanels = createOffsets(this, 50, 50, 100, 0);

        blankPanels[PanelOrder.CENTRAL.ordinal()] = new JPanel();
        JPanel pnlMiddle = blankPanels[PanelOrder.CENTRAL.ordinal()];
        pnlMiddle.setLayout(new BoxLayout(pnlMiddle, BoxLayout.Y_AXIS));
        pnlMiddle.setBackground(Color.WHITE);
        this.add(pnlMiddle, BorderLayout.CENTER);
    }

    public static void initializeBlankPanel(JPanel blankPanel, int width, int height) {
        blankPanel.setBackground(BACKGROUND_COLOR);
        blankPanel.setPreferredSize(new Dimension(width, height));
    }

    public static JPanel[] createOffsets(JPanel panel, int northOffSet, int southOffSet, int westOffSet,
            int eastOffSet) {
        JPanel[] blankPanels = new JPanel[PanelOrder.values().length];

        JPanel pnlNorth = new JPanel();
        blankPanels[PanelOrder.NORTH.ordinal()] = pnlNorth;
        initializeBlankPanel(pnlNorth, 1, northOffSet);
        panel.add(pnlNorth, BorderLayout.NORTH);

        JPanel pnlWest = new JPanel();
        blankPanels[PanelOrder.WEST.ordinal()] = pnlWest;
        initializeBlankPanel(pnlWest, westOffSet, 1);
        panel.add(pnlWest, BorderLayout.WEST);

        JPanel pnlEast = new JPanel();
        blankPanels[PanelOrder.EAST.ordinal()] = pnlEast;
        initializeBlankPanel(pnlEast, eastOffSet, 1);
        panel.add(pnlEast, BorderLayout.EAST);

        JPanel pnlSouth = new JPanel();
        blankPanels[PanelOrder.SOUTH.ordinal()] = pnlSouth;
        initializeBlankPanel(pnlSouth, 1, southOffSet);
        panel.add(pnlSouth, BorderLayout.SOUTH);

        return blankPanels;
    }

    public static JPanel[] createOffsets(JPanel panel, int northOffSet, int southOffSet, int westOffSet, int eastOffSet,
            Color color) {
        JPanel[] blankPanels = Screen.createOffsets(panel, northOffSet, southOffSet, westOffSet, eastOffSet);
        for (JPanel blankPanel : blankPanels) {
            if (blankPanel != null)
                blankPanel.setBackground(color);
        }
        return blankPanels;
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

    public static void setErrorBorder(JTextField field, Boolean isError) {
        final Border blackBorder = BorderFactory.createLineBorder(Color.BLACK);
        final Border redBorder = BorderFactory.createLineBorder(Color.RED);
        field.setBorder(isError ? redBorder : blackBorder);
    }

    public static void showErrorMessage(JPanel panel, String error) {
        panel.removeAll();
        JLabel errorLabel = new JLabel(error);
        errorLabel.setForeground(Color.RED);
        panel.add(errorLabel);
        panel.revalidate();
        panel.repaint();
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
