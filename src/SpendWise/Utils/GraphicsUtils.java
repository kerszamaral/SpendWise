package SpendWise.Utils;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;

import SpendWise.Utils.Enums.PanelOrder;

public abstract class GraphicsUtils {
    public final static Color BACKGROUND_COLOR = new Color(98, 210, 162);
    public final static Font STD_FONT = new Font("Arial", Font.PLAIN, 14);
    public final static Font STD_FONT_BOLD = new Font("Arial", Font.BOLD, 14);

    public static void initializeBlankPanel(JPanel blankPanel, int width, int height) {
        blankPanel.setBackground(BACKGROUND_COLOR);
        blankPanel.setPreferredSize(new Dimension(width, height));
    }

    public static JPanel initializeBlankPanel(int width, int height) {
        JPanel blankPanel = new JPanel();
        initializeBlankPanel(blankPanel, width, height);
        return blankPanel;
    }

    public static JPanel[] createOffsets(int northOffSet, int southOffSet, int westOffSet,
            int eastOffSet) {
        JPanel[] blankPanels = new JPanel[PanelOrder.values().length];

        blankPanels[PanelOrder.NORTH.ordinal()] = initializeBlankPanel(1, northOffSet);

        blankPanels[PanelOrder.WEST.ordinal()] = initializeBlankPanel(westOffSet, 1);

        blankPanels[PanelOrder.EAST.ordinal()] = initializeBlankPanel(eastOffSet, 1);

        blankPanels[PanelOrder.SOUTH.ordinal()] = initializeBlankPanel(1, southOffSet);

        return blankPanels;
    }

    /**
     * Creates blank panels and adds them to the given panel
     * 
     * @return the blank panels created, in the order of PanelOrder
     */
    public static JPanel[] createOffsets(JPanel panel, int northOffSet, int southOffSet, int westOffSet,
            int eastOffSet) {
        JPanel[] blankPanels = createOffsets(northOffSet, southOffSet, westOffSet, eastOffSet);

        for (PanelOrder pnl : PanelOrder.values()) {
            if (blankPanels[pnl.ordinal()] != null)
                panel.add(blankPanels[pnl.ordinal()], pnl.getConstrains());
        }

        return blankPanels; // Return the blank panels so that the caller can add stuff to them
    }

    /**
     * Creates blank panels and adds them to the given panel with the given color
     * 
     * @return the blank panels created, in the order of PanelOrder
     */
    public static JPanel[] createOffsets(JPanel panel, int northOffSet, int southOffSet, int westOffSet, int eastOffSet,
            Color color) {
        JPanel[] blankPanels = createOffsets(northOffSet, southOffSet, westOffSet, eastOffSet);

        for (PanelOrder pnl : PanelOrder.values()) {
            if (blankPanels[pnl.ordinal()] != null) {
                blankPanels[pnl.ordinal()].setBackground(color);
                panel.add(blankPanels[pnl.ordinal()], pnl.getConstrains());
            }
        }

        return blankPanels;
    }

    public static void setBorder(JTextField field, Color color) {
        Border border = BorderFactory.createLineBorder(color);
        field.setBorder(border);
    }

    public static void setErrorBorder(JTextField field, Boolean isError) {
        if (isError) {
            setBorder(field, Color.RED);
        } else {
            setBorder(field, Color.BLACK);
        }
    }

    public static void showMessage(JPanel panel, String msg, Color color) {
        panel.removeAll();
        JLabel errorLabel = new JLabel(msg);
        errorLabel.setForeground(color);
        panel.add(errorLabel);
        panel.revalidate();
        panel.repaint();
    }

    public static void showErrorMessage(JPanel panel, String error) {
        showMessage(panel, error, Color.RED);
    }

    public static void clearErrorMessage(JPanel panel) {
        showMessage(panel, "", Color.BLACK);
    }

    public static void refresh(JComponent component) {
        component.revalidate();
        component.repaint();
    }

    public static JButton createButton(JPanel panel, String text, ActionListener actionListener) {
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

        return btnEdit;
    }
}
