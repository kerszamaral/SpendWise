package SpendWise.Graphics.Menus;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import SpendWise.Graphics.PopUp;
import SpendWise.Graphics.Screen;
import SpendWise.Graphics.PopUps.changePassword;
import SpendWise.Logic.User;
import SpendWise.Logic.Managers.UserManager;
import SpendWise.Utils.Email;
import SpendWise.Utils.Offsets;
import SpendWise.Utils.Enums.AccountFields;
import SpendWise.Utils.Enums.PanelOrder;
import SpendWise.Utils.Graphics.Alerts;
import SpendWise.Utils.Graphics.Components;
import SpendWise.Utils.Graphics.Misc;
import SpendWise.Utils.Graphics.Panels;

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
        Offsets offsets = new Offsets(50, 50, 50, 50);
        blankPanels = Panels.createPanelWithCenter(this, offsets, ACCENT_COLOR);

        pnlUserData = getBlankPanel(PanelOrder.CENTRAL);
        pnlUserData.setLayout(new GridLayout(AccountFields.values().length * 3, 1)); // Label + TextField + Spacer
        pnlUserData.setBackground(ACCENT_COLOR);
        pnlUserData.setAlignmentY(CENTER_ALIGNMENT);

        txtFields = new JTextField[AccountFields.values().length];
        this.updateAccountFields();

        Offsets offsetsBtn = new Offsets(10, 10, 400, 20);
        btnEditAccount = Components.initializeButton(this.getBlankPanel(PanelOrder.SOUTH), offsetsBtn,
                "Edit Account",
                ACCENT_COLOR,
                e -> edit(e));
    }

    private void updateAccountFields() {
        pnlUserData.removeAll();

        final int textFieldSize = 300;
        final boolean startingEditState = false;
        final int verticalGap = 10;

        for (AccountFields field : AccountFields.values()) {
            String label = field.getValue() + ": ";
            Boolean isPassword = field.getValue().toLowerCase().contains("password");
            String userValue = loggedUser.getField(field);

            txtFields[field.ordinal()] = Components.addTextField(pnlUserData, label, userValue, textFieldSize,
                    isPassword,
                    startingEditState);

            pnlUserData.add(Box.createVerticalStrut(verticalGap));
        }

        Misc.refresh(pnlUserData);
    }

    private void edit(ActionEvent e) {
        boolean nextState = !isEditing;

        JTextField txtName = txtFields[AccountFields.NAME.ordinal()];
        JTextField txtUsername = txtFields[AccountFields.USERNAME.ordinal()];
        JTextField txtEmail = txtFields[AccountFields.EMAIL.ordinal()];
        JPasswordField txtPassword = (JPasswordField) txtFields[AccountFields.PASSWORD.ordinal()];

        Alerts.clearErrorMessage(super.getBlankPanel(PanelOrder.NORTH));
        if (!nextState) {
            for (JTextField txtField : txtFields) {
                Alerts.setErrorBorder(txtField, false);
                if (txtField.getText().isEmpty() && !(txtField instanceof JPasswordField)) {
                    Alerts.setErrorBorder(txtField, true);
                    Alerts.showErrorMessage(super.getBlankPanel(PanelOrder.NORTH),
                            "Please fill all Non Password fields");
                    nextState = true;
                }
            }

            if (!Email.isEmailValid(txtEmail)) {
                Alerts.showErrorMessage(super.getBlankPanel(PanelOrder.NORTH), "Please enter a valid email");
                nextState = true;
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
                    PopUp changePassword = new changePassword(this, "Change Password", loggedUser, newPassword,
                            this::changePassword);
                    changePassword.run();
                }
            }

            txtPassword.setText(loggedUser.getField(AccountFields.PASSWORD));
        }

        btnEditAccount.setText(nextState ? "Apply Changes" : "Edit Account");

        for (JTextField txtField : txtFields) {
            txtField.setEditable(nextState);
        }

        isEditing = nextState;
    }

    private void changePassword() {
        this.updateAccountFields();
        Alerts.clearErrorMessage(getBlankPanel(PanelOrder.NORTH));
        Alerts.showMessage(getBlankPanel(PanelOrder.NORTH), "Password changed successfully!",
                BACKGROUND_COLOR);
    }
}
