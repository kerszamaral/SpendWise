package SpendWise.Graphics.Menus;

import SpendWise.Utils.PanelOrder;
import SpendWise.Graphics.Screen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GroupsMenu extends Screen {
    private JPanel pnlGroupManagement;

    //Placeholder lists
    private final String[] groups = {"","Group A", "Group B", "Group C"};
    private final String[] members = {"","Member X", "Member Y", "Member Z"};

    public GroupsMenu() {
        pnlGroupManagement = new JPanel();
        initialize();
    }

    @Override
    protected void initialize() {
        super.initialize();
        pnlGroupManagement = super.getBlankPanel(PanelOrder.CENTRAL);
        pnlGroupManagement.setLayout(new BoxLayout(pnlGroupManagement, BoxLayout.Y_AXIS));

        addInputGroup("Select Group: ", groups);
        addInputGroup("Add Member:   ");
        addInputGroup("Remove Member:", members);

        Screen.createButton(this.getBlankPanel(PanelOrder.SOUTH), "Save", this::saveButton);
    }

    private void addInputGroup(String label, String... options) {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
    
        JLabel lbl = new JLabel(label);
        lbl.setBackground(Color.WHITE); // n entendi pq n ficou branco o fundo dos textos, mas isso ainda vai ser modificado entao azaar
        inputPanel.add(lbl);

    
        if (options.length > 0) {
            JComboBox<String> comboBox = new JComboBox<>(options);
            comboBox.setPreferredSize(new Dimension(20, comboBox.getPreferredSize().height));
            comboBox.setBackground(Color.WHITE);
            inputPanel.add(comboBox);
        } else {
            JTextField textField = new JTextField();
            textField.setPreferredSize(new Dimension(20, textField.getPreferredSize().height));
            textField.setBackground(Color.WHITE);
            inputPanel.add(textField);
        }
    
        pnlGroupManagement.add(inputPanel);
    }

    private void saveButton(ActionEvent e) {
        // TODO Save button function
    }
}
