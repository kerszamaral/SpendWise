package SpendWise.Graphics.Menus;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.*;

import SpendWise.Graphics.PopUp;
import SpendWise.Graphics.Screen;
import SpendWise.Graphics.PopUps.changePassword;
import SpendWise.Managers.UserManager;
import SpendWise.Utils.PanelOrder;
import SpendWise.User;

public class AccountMenu extends Screen {
    private JPanel pnlUserData;
    private User loggedUser;
    private UserManager userManager;
    private JButton btnEditAccount;
    private boolean isEditing = false;
    private JTextField[] txtFields;

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

        txtFields = new JTextField[4];

        txtFields[0] = addTextField("Name: ", loggedUser.getName(), 100, false);
        txtFields[1] = addTextField("Username: ", loggedUser.getUsername(), 100, false);
        txtFields[2] = addTextField("E-mail: ", loggedUser.getEmail(), 100, false);
        txtFields[3] = addTextField("Password: ", "*".repeat(loggedUser.getPasswordSize()), 100, true);

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
        boolean nextState = !isEditing;

        JTextField txtName = txtFields[0];
        JTextField txtUsername = txtFields[1];
        JTextField txtEmail = txtFields[2];
        JPasswordField txtPassword = (JPasswordField) txtFields[3];

        Screen.clearErrorMessage(super.getBlankPanel(PanelOrder.NORTH));
        if (!nextState) {
            for (JTextField txtField : txtFields) {
                Screen.setErrorBorder(txtField, false);
                if (txtField.getText().isEmpty() && !(txtField instanceof JPasswordField)) {
                    Screen.setErrorBorder(txtField, true);
                    Screen.showErrorMessage(super.getBlankPanel(PanelOrder.NORTH),
                            "Please fill all Non Password fields");
                    nextState = true;
                }
            }
        }

        // Decision to edit or not based on previous checks
        if (nextState) {
            txtPassword = (JPasswordField) txtFields[3];
            txtPassword.setText("");
        } else {
            String newName = txtName.getText();
            String newUsername = txtUsername.getText();
            String newEmail = txtEmail.getText();
            String newPassword = new String(txtPassword.getPassword());

            if (!loggedUser.getName().equals(newName)) {
                loggedUser.setName(newName);
            }

            if (!loggedUser.getUsername().equals(newUsername)) {
                userManager.changeUsername(loggedUser.getUsername(), newUsername);
            }

            if (!loggedUser.getEmail().equals(newEmail)) {
                loggedUser.setEmail(newEmail);
            }

            if (!newPassword.isEmpty()) {
                if (!loggedUser.checkPassword(newPassword)) {
                    PopUp changePassword = new changePassword(this, "Change Password", loggedUser, newPassword);
                    changePassword.run();
                }
            }

            txtPassword.setText("*".repeat(loggedUser.getPasswordSize()));
        }

        btnEditAccount.setText(nextState ? "Apply Changes" : "Edit Account");

        for (JTextField txtField : txtFields) {
            txtField.setEditable(nextState);
        }

        isEditing = nextState;
    }
}
