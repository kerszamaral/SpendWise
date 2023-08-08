package SpendWise.Utils;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;

import SpendWise.Utils.Enums.PanelOrder;

public abstract class GraphicsUtils {
    public final static Color BACKGROUND_COLOR = new Color(98, 210, 162);
    public final static Color ACCENT_COLOR = new Color(255, 255, 255);
    public final static Font STD_FONT = new Font("Arial", Font.PLAIN, 14);
    public final static Font STD_FONT_BOLD = new Font("Arial", Font.BOLD, 14);

    public static void initializeBlankPanel(JPanel blankPanel, int width, int height, Color color) {
        blankPanel.setBackground(BACKGROUND_COLOR);
        blankPanel.setPreferredSize(new Dimension(width, height));
    }

    public static void initializeBlankPanel(JPanel blankPanel, int width, int height) {
        initializeBlankPanel(blankPanel, width, height, BACKGROUND_COLOR);
    }

    public static JPanel initializeBlankPanel(int width, int height) {
        JPanel blankPanel = new JPanel();
        initializeBlankPanel(blankPanel, width, height);
        return blankPanel;
    }

    public static JPanel[] createOffsets(int northOffSet, int southOffSet, int westOffSet,
            int eastOffSet) {
        JPanel[] blankPanels = new JPanel[PanelOrder.values().length];

        blankPanels[PanelOrder.NORTH.ordinal()] = initializeBlankPanel(0, northOffSet);

        blankPanels[PanelOrder.WEST.ordinal()] = initializeBlankPanel(westOffSet, 0);

        blankPanels[PanelOrder.CENTRAL.ordinal()] = new JPanel();

        blankPanels[PanelOrder.EAST.ordinal()] = initializeBlankPanel(eastOffSet, 0);

        blankPanels[PanelOrder.SOUTH.ordinal()] = initializeBlankPanel(0, southOffSet);


        return blankPanels;
    }

    /**
     * Creates blank panels and adds them to the given panel
     * 
     * @return the blank panels created, in the order of PanelOrder
     */
    public static JPanel[] createOffsets(JPanel panel, int northOffSet, int southOffSet, int westOffSet,
            int eastOffSet) {
        panel.setLayout(new BorderLayout());
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

    public static Pair<JLabel, JTextField> createTextField(String label, String userValue, int width,
            Boolean isPassword,
            Boolean isEditable) {
        JLabel lbl = new JLabel(label);

        JTextField textField = isPassword ? new JPasswordField(userValue, 20) : new JTextField(userValue, 20);
        textField.setEditable(isEditable);

        Dimension size = new Dimension(width, 30);
        textField.setMinimumSize(size);
        textField.setPreferredSize(size);
        textField.setMaximumSize(size);

        return new Pair<JLabel, JTextField>(lbl, textField);
    }

    public static JTextField addTextField(JPanel panel, String label, String userValue, int width, Boolean isPassword,
            Boolean isEditable) {
        Pair<JLabel, JTextField> pair = createTextField(label, userValue, width, isPassword, isEditable);
        panel.add(pair.getKey());
        panel.add(pair.getValue());
        return pair.getValue();
    }

    public static JPanel createPanelText(String label, String userValue, int width, Boolean isPassword,
            Boolean isEditable) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        addTextField(panel, label, userValue, width, isPassword, isEditable);

        return panel;
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
