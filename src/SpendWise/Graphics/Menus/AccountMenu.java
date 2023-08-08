package SpendWise.Graphics.Menus;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.*;

import SpendWise.Graphics.PopUp;
import SpendWise.Graphics.Screen;
import SpendWise.Graphics.PopUps.editAccount;
import SpendWise.Managers.UserManager;
import SpendWise.Utils.PanelOrder;
import SpendWise.User;

public class AccountMenu extends Screen {
    private JPanel pnlUserData;
    private User loggedUser;
    private UserManager userManager;

    public AccountMenu(UserManager userManager) {
        this.userManager = userManager;
        this.loggedUser = userManager.getLoggedUser();
        pnlUserData = new JPanel();
        this.initialize();
    }

    @Override
    protected void initialize() {
        super.initialize();

        pnlUserData = super.getBlankPanel(PanelOrder.CENTRAL);
        pnlUserData.setBackground(BACKGROUND_COLOR);
        pnlUserData.setLayout(new BoxLayout(pnlUserData, BoxLayout.Y_AXIS));

        addTextField("Name: ", loggedUser.getName(), 100);
        addTextField("Username: ", loggedUser.getUsername(), 100);
        addTextField("E-mail: ", loggedUser.getEmail(), 100);
        addPasswordField("Password: ", "*".repeat(loggedUser.getPasswordSize()), 100);

        Screen.createButton(this.getBlankPanel(PanelOrder.SOUTH), "Edit Account", e -> edit(e));
    }

    private void addTextField(String label, String userValue, int width) {
        JLabel lbl = new JLabel(label);
        pnlUserData.add(lbl);

        JTextField textField = new JTextField(userValue, 20);
        textField.setEditable(false);
        textField.setPreferredSize(new Dimension(width, textField.getPreferredSize().height));
        pnlUserData.add(textField);
    }

    private void addPasswordField(String label, String userValue, int width) {
        JLabel lbl = new JLabel(label);
        pnlUserData.add(lbl);
        JPasswordField passwordField = new JPasswordField(userValue, 20);
        passwordField.setEditable(false);
        passwordField.setPreferredSize(new Dimension(width, passwordField.getPreferredSize().height));
        pnlUserData.add(passwordField);
    }

    private void edit(ActionEvent e) {
        PopUp editAccount = new editAccount(this, "Edit Account", loggedUser, pnlUserData, userManager);
        editAccount.run();
    }
}
