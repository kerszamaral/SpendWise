package SpendWise.Interface.Menus;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import SpendWise.Interface.Screen;
import SpendWise.Utils.Offsets;
import SpendWise.Utils.Enums.GroupFields;
import SpendWise.Utils.Enums.PanelOrder;
import SpendWise.Utils.Graphics.Components;
import SpendWise.Utils.Graphics.Misc;
import SpendWise.Utils.Graphics.Panels;

public class GroupsMenu extends Screen {
    private JPanel pnlGroupManagement;
    private final String IMAGE_PATH = "res/Images/groupsImage.png";
    private JComponent[] fields;

    public GroupsMenu() {
        pnlGroupManagement = new JPanel();
        initialize();
    }

    @Override
    protected void initialize() {
        blankPanels = Panels.createPanelWithCenter(this, new Offsets(50, 50, 50, 50), ACCENT_COLOR);

        JPanel centerPanel = getBlankPanel(PanelOrder.CENTRAL);
        JPanel[] innerPanels = Panels.initializeOffsets(centerPanel, new Offsets(0, 0, 0, 150), ACCENT_COLOR);

        JLabel groupTitle = new JLabel("MANAGE GROUPS");
        groupTitle.setFont(STD_FONT_BOLD);
        getBlankPanel(PanelOrder.NORTH).add(groupTitle);

        addImage(innerPanels[PanelOrder.EAST.ordinal()]);

        pnlGroupManagement.setLayout(new GridLayout(3, 2));

        fields = new JComponent[GroupFields.values().length];

        JComponent fieldType = null;
        for (GroupFields field : GroupFields.values()) {
            // String label = field.getLabel() + ": ";
            // JLabel lbl = new JLabel(label);

            switch (field) {
                case SELECT:
                    break;
                case ADD:
                    JTextField addUserField = new JTextField();
                    fieldType = addUserField;
                    break;
                case REMOVE:
                    JTextField removeUserField = new JTextField();
                    fieldType = removeUserField;
                    break;
            }

            if (fieldType == null)
                continue;

            Misc.defineSize(fieldType, DEFAULT_FIELD_SIZE);

            fieldType.setEnabled(true);
            fieldType.setBackground(ACCENT_COLOR);

            fields[field.ordinal()] = fieldType;
            pnlGroupManagement.add(fieldType);
            // pnlCentral.add(lbl);

            // JComponent fieldType = null;
            // switch (field) {

            // }
        }

        /*
         * addInputGroup("Select Group: ", groups);
         * addInputGroup("Add Member: ");
         * addInputGroup("Remove Member: ", members);
         * innerPanels[PanelOrder.CENTRAL.ordinal()] = pnlGroupManagement;
         */

        centerPanel.add(pnlGroupManagement, BorderLayout.CENTER);
        Components.initializeButton(this.getBlankPanel(PanelOrder.SOUTH), new Offsets(10, 10, 400, 20), "Save",
                ACCENT_COLOR,
                this::saveButton);
    }

    /*
     * private void addInputGroup(String label, String... options) {
     * JPanel inputPanel = new JPanel();
     * inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
     * 
     * JLabel lbl = new JLabel(label);
     * lbl.setBackground(ACCENT_COLOR); // n entendi pq n ficou branco o fundo dos
     * textos, mas isso ainda vai ser modificado entao azaar
     * inputPanel.add(lbl);
     * 
     * 
     * if (options.length > 0) {
     * JComboBox<String> comboBox = new JComboBox<>(options);
     * comboBox.setPreferredSize(new Dimension(20,
     * comboBox.getPreferredSize().height));
     * comboBox.setBackground(ACCENT_COLOR);
     * inputPanel.add(comboBox);
     * } else {
     * JTextField textField = new JTextField();
     * textField.setPreferredSize(new Dimension(20,
     * textField.getPreferredSize().height));
     * textField.setBackground(ACCENT_COLOR);
     * inputPanel.add(textField);
     * }
     * 
     * pnlGroupManagement.add(inputPanel);
     * }
     */

    private ImageIcon resizeImage(String path, int width, int height) {
        Image originalImage = new ImageIcon(path).getImage();
        ImageIcon resizedImageIcon = new ImageIcon(originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH));
        return resizedImageIcon;
    }

    private void centralizeImage(JPanel panel, JLabel lblImage) {
        JPanel[] imageOffsets = Panels.initializeOffsets(panel, new Offsets(90, 90, 25, 0), ACCENT_COLOR);
        imageOffsets[PanelOrder.CENTRAL.ordinal()].add(lblImage);
    }

    private void addImage(JPanel panel) {
        ImageIcon resizedImageIcon = resizeImage(IMAGE_PATH, 100, 100);
        centralizeImage(panel, new JLabel(resizedImageIcon));
    }

    private void saveButton(ActionEvent e) {
        // TODO Save button function
    }
}
