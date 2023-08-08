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
    private JButton btnEditAccount;
    private boolean isEditing = false;
    private JTextField txtName, txtUsername, txtEmail;
    private JPasswordField txtPassword;

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

        txtName = addTextField("Name: ", loggedUser.getName(), 100, false);
        addTextField("Username: ", loggedUser.getUsername(), 100, false);
        addTextField("E-mail: ", loggedUser.getEmail(), 100, false);
        addTextField("Password: ", "*".repeat(loggedUser.getPasswordSize()), 100, true);

        btnEditAccount = Screen.createButton(this.getBlankPanel(PanelOrder.SOUTH), "Edit Account", e -> edit(e));
    }

    private JTextField addTextField(String label, String userValue, int width, boolean isPassword) {
        JLabel lbl = new JLabel(label);
        pnlUserData.add(lbl);

        JTextField textField = isPassword ? new JPasswordField(userValue, 20) : new JTextField(userValue, 20);
        textField.setEditable(false);
        textField.setPreferredSize(new Dimension(width, textField.getPreferredSize().height));
        pnlUserData.add(textField);

        return textField;
    }

    private void edit(ActionEvent e) {
        // isEditing = !isEditing;
        // btnEditAccount.setText(isEditing ? "Apply Changes" : "Edit Account");
        PopUp editAccount = new editAccount(this, "Edit Account", loggedUser, pnlUserData, userManager);
        editAccount.run();
    }
}
