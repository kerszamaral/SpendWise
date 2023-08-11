package SpendWise.Interface.PopUps;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import SpendWise.Interface.PopUp;
import SpendWise.Interface.Screen;
import SpendWise.Logic.User;
import SpendWise.Utils.Offsets;
import SpendWise.Utils.Enums.PanelOrder;
import SpendWise.Utils.Graphics.Alerts;
import SpendWise.Utils.Graphics.Components;
import SpendWise.Utils.Graphics.Panels;

public class CreateGroup extends PopUp {
    private User loggedUser;

    private JTextField groupNameField;

    public CreateGroup(Screen parent, String title, User loggedUser) {
        this.loggedUser = loggedUser;
        super(parent, title);
    }
    
    @Override
    public void run() {
        Offsets offsets = new Offsets(50, 0, 100, 100);
        blankPanels = Panels.initializeOffsets((JPanel) this.getContentPane(), offsets);
    
        // Creating the sign up panel and it's fields
        JPanel editPanel = new JPanel(new GridLayout(6, 1));
        editPanel.setBackground(BACKGROUND_COLOR);

        groupNameField = (JTextField) Components.addTextFieldCenter(editPanel, "Repeat New Password:", "",
                15, true, true);

    }
}
