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
    public final static Dimension DEFAULT_FIELD_SIZE = new Dimension(100, 30);

    public static void defineSize(JComponent component, Dimension size) {
        component.setMinimumSize(size);
        component.setPreferredSize(size);
        component.setMaximumSize(size);
    }

    public static void initializeBlankPanel(JPanel blankPanel, int width, int height, Color color) {
        blankPanel.setBackground(BACKGROUND_COLOR);
        blankPanel.setPreferredSize(new Dimension(width, height));
    }

    public static void initializeBlankPanel(JPanel blankPanel, int width, int height) {
        initializeBlankPanel(blankPanel, width, height, BACKGROUND_COLOR);
    }

    public static JPanel createBlankPanel(int width, int height) {
        JPanel blankPanel = new JPanel();
        initializeBlankPanel(blankPanel, width, height);
        return blankPanel;
    }

    public static JPanel createBlankPanel(int width, int height, Color color) {
        JPanel blankPanel = new JPanel();
        initializeBlankPanel(blankPanel, width, height, color);
        return blankPanel;
    }

    public static JPanel[] createOffsets(Offsets offsets) {
        JPanel[] blankPanels = new JPanel[PanelOrder.values().length];

        blankPanels[PanelOrder.NORTH.ordinal()] = createBlankPanel(0, offsets.getNorth());

        blankPanels[PanelOrder.WEST.ordinal()] = createBlankPanel(offsets.getWest(), 0);

        blankPanels[PanelOrder.CENTRAL.ordinal()] = new JPanel();

        blankPanels[PanelOrder.EAST.ordinal()] = createBlankPanel(offsets.getEast(), 0);

        blankPanels[PanelOrder.SOUTH.ordinal()] = createBlankPanel(0, offsets.getSouth());

        return blankPanels;
    }

    /**
     * Creates blank panels and adds them to the given panel
     * 
     * @return the blank panels created, in the order of PanelOrder
     */
    public static JPanel[] initializeOffsets(JPanel panel, Offsets offsets) {
        panel.setLayout(new BorderLayout());
        JPanel[] blankPanels = createOffsets(offsets);

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
    public static JPanel[] initializeOffsets(JPanel panel, Offsets offsets, Color color) {
        JPanel[] blankPanels = initializeOffsets(panel, offsets);

        for (PanelOrder pnl : PanelOrder.values()) {
            if (blankPanels[pnl.ordinal()] != null) {
                blankPanels[pnl.ordinal()].setBackground(color);
                panel.add(blankPanels[pnl.ordinal()], pnl.getConstrains());
            }
        }

        return blankPanels;
    }

    public static JPanel[] createPanelWithCenter(JPanel mainPanel, Offsets innerOffsets, Offsets outerOffsets,
            Color innerColor) {
        JPanel[] outerBlankPanels = initializeOffsets(mainPanel, outerOffsets, BACKGROUND_COLOR);

        JPanel[] innerBlankPanels = initializeOffsets(outerBlankPanels[PanelOrder.CENTRAL.ordinal()], innerOffsets,
                innerColor);
        return innerBlankPanels;
    }

    public static JPanel[] createPanelWithCenter(JPanel mainPanel, Offsets innerOffsets, Color innerColor) {
        final int OUTER_LEFT = 100;
        final int OUTER_RIGHT = 0;
        Offsets outerOffsets = new Offsets(innerOffsets.getNorth(), innerOffsets.getSouth(), OUTER_LEFT, OUTER_RIGHT);
        JPanel[] outerBlankPanels = initializeOffsets(mainPanel, outerOffsets, BACKGROUND_COLOR);

        JPanel[] innerBlankPanels = initializeOffsets(outerBlankPanels[PanelOrder.CENTRAL.ordinal()], innerOffsets,
                innerColor);
        return innerBlankPanels;
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
        defineSize(textField, size);

        return new Pair<JLabel, JTextField>(lbl, textField);
    }

    public static JTextField addTextField(JPanel panel, String label, String userValue, int width, Boolean isPassword,
            Boolean isEditable) {
        Pair<JLabel, JTextField> pair = createTextField(label, userValue, width, isPassword, isEditable);
        panel.add(pair.getKey());
        panel.add(pair.getValue());
        return pair.getValue();
    }

    public static JTextField addTextFieldCenter(JPanel panel, String label, String userValue, int width,
            Boolean isPassword,
            Boolean isEditable) {
        Pair<JLabel, JTextField> pair = createTextField(label, userValue, width, isPassword, isEditable);
        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.X_AXIS));
        innerPanel.setOpaque(false);
        innerPanel.add(pair.getKey());
        panel.add(innerPanel);
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

    public static JButton createButton(String text, Color background, Color foreground, Dimension size,
            ActionListener actionListener) {
        JButton button = new JButton(text);

        button.setBackground(background);
        button.setForeground(foreground);
        button.setFont(STD_FONT_BOLD);
        button.addActionListener(actionListener);

        GraphicsUtils.defineSize(button, size);

        return button;
    }

    public static JButton createButton(String text, Color background, Color foreground, Dimension size) {
        JButton button = new JButton(text);

        button.setBackground(background);
        button.setForeground(foreground);
        button.setFont(STD_FONT_BOLD);

        GraphicsUtils.defineSize(button, size);

        return button;
    }

    public static JButton initializeButton(JPanel mainPanel, Offsets offsets, String text, Color color,
            ActionListener actionListener) {
        // Creates the panel that is going to the south of the screen
        JPanel panel = new JPanel();
        initializeOffsets(panel, offsets, color);

        // Creates the button itself and adds it to the east panel
        JButton btnEdit = createButton(text, Color.BLACK, BACKGROUND_COLOR, null, actionListener);
        panel.add(btnEdit, BorderLayout.CENTER);

        // And, finnaly, add the south panel to the screen
        mainPanel.add(panel, BorderLayout.SOUTH);

        return btnEdit;
    }

    public static String getColorHex(Color color) {
        return "#" + Integer.toHexString(color.getRGB()).substring(2);
    }
}
