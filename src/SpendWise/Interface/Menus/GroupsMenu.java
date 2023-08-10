package SpendWise.Interface.Menus;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import SpendWise.Interface.Screen;
import SpendWise.Logic.Managers.GroupManager;
import SpendWise.Utils.Offsets;
import SpendWise.Utils.Enums.GroupFields;
import SpendWise.Utils.Enums.PanelOrder;
import SpendWise.Utils.Graphics.Components;
import SpendWise.Utils.Graphics.Misc;
import SpendWise.Utils.Graphics.Panels;
import SpendWise.Logic.Group;

public class GroupsMenu extends Screen {
    private JPanel pnlGroupManagement;
    private final String IMAGE_PATH = "res/Images/groupsImage.png";
    private JComponent[] fields;
    private GroupManager groupManager;

    public GroupsMenu(GroupManager groupManager) {
        this.groupManager = groupManager;
        groupManager.addGroup("BUCETINHA");
        // groupManager.addGroup("CAMARAO");
        // groupManager.addGroup("VASCO");
        pnlGroupManagement = new JPanel();
        initialize();
    }

    @Override
    protected void initialize() {
        Offsets outerOffsets = new Offsets(DEFAULT_OFFSETS.getNorth(), 35,
                DEFAULT_OFFSETS.getWest(), DEFAULT_OFFSETS.getEast());
        Offsets innerOffsets = new Offsets(50, 70, 50, 50);
        blankPanels = Panels.createPanelWithCenter(this, innerOffsets, outerOffsets, ACCENT_COLOR);

        JPanel centerPanel = getBlankPanel(PanelOrder.CENTRAL);
        JPanel[] innerPanels = Panels.initializeOffsets(centerPanel, new Offsets(0, 0, 0, 150), ACCENT_COLOR);

        JLabel groupTitle = new JLabel("MANAGE GROUPS");
        groupTitle.setFont(STD_FONT_BOLD);
        getBlankPanel(PanelOrder.NORTH).add(groupTitle);

        addImage(innerPanels[PanelOrder.EAST.ordinal()]);


        pnlGroupManagement = innerPanels[PanelOrder.CENTRAL.ordinal()];
        pnlGroupManagement.setLayout(new GridLayout(GroupFields.values().length*2, 2));

        fields = new JComponent[GroupFields.values().length];

        JComponent fieldType = null;
        for (GroupFields field : GroupFields.values()) {
            String label = field.getLabel() + ": ";
            JLabel lbl = new JLabel(label);
            lbl.setVisible(false);
            pnlGroupManagement.add(lbl);
            
            switch (field) {
                case SELECT:
                    lbl.setVisible(true);
                    String[] names = new String[groupManager.getGroups().size()];
                    for (int i = 0; i < groupManager.getGroups().size(); i++) {
                        names[i] = groupManager.getGroups().get(i).getGroupName();
                    }
                    JComboBox<String> selectGroupField = new JComboBox<String>(names);
                    selectGroupField.addActionListener(e -> createUserFields(e));
                    fieldType = selectGroupField;                
                    break;
                case ADD:
                    JTextField addUserField = new JTextField();
                    fieldType = addUserField;
                    addUserField.setVisible(false);
                    break;
                case REMOVE:
                    JTextField removeUserField = new JTextField();
                    fieldType = removeUserField;
                    removeUserField.setVisible(false);
                    break;
            }

            if (fieldType == null)
                continue;

            Misc.defineSize(fieldType, DEFAULT_FIELD_SIZE);

            fieldType.setEnabled(true);
            fieldType.setBackground(ACCENT_COLOR);

            fields[field.ordinal()] = fieldType;
            pnlGroupManagement.add(fieldType);
            //pnlCentral.add(lbl);

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
        String[] buttonsTexts = { "Add Group", "Save" };
        ActionListener[] buttonListeners = { e -> saveButton(e), e -> addGroup(e) };
        Components.initializeButtons(this.getBlankPanel(PanelOrder.SOUTH), new Offsets(30, 10, 400, 20), buttonsTexts,
                ACCENT_COLOR,
                buttonListeners);
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

    private void createUserFields(ActionEvent e) {

        for (JComponent field : fields) {
            field.setVisible(true);
        }

        for (Component lbl : pnlGroupManagement.getComponents()) {       
            if(lbl instanceof JLabel){
                lbl.setVisible(true);
       
            }
        }
        
    }

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

    private void addGroup(ActionEvent e) {
        // TODO Add group button function
    }

    private void saveButton(ActionEvent e) {
        // TODO Save button function
    }
}