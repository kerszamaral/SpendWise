package SpendWise.Interface.PopUps;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import SpendWise.Interface.PopUp;
import SpendWise.Interface.Screen;
import SpendWise.Logic.User;
import SpendWise.Logic.Managers.UserManager;
import SpendWise.Utils.Email;
import SpendWise.Utils.Offsets;
import SpendWise.Utils.Enums.PanelOrder;
import SpendWise.Utils.Enums.SignUpLabels;
import SpendWise.Utils.Graphics.Alerts;
import SpendWise.Utils.Graphics.Components;
import SpendWise.Utils.Graphics.Panels;

public class SignUp extends PopUp {
    private JTextField[] signUpFields;
    private UserManager userManager;
    private ActionListener singUpAction;

    public SignUp(Screen parent, String title, UserManager userManager, ActionListener singUpAction) {
        super(parent, title);
        this.userManager = userManager;
        this.singUpAction = singUpAction;
    }

    @Override
    public void run() {
        // Creating the sign up panel and it's fields
        this.setLayout(new BorderLayout());
        Offsets offsets = new Offsets(50, 0, 100, 100);
        blankPanels = Panels.initializeOffsets((JPanel) this.getContentPane(), offsets);

        JPanel signUpPanel = new JPanel(new GridLayout(SignUpLabels.values().length * 2, 1));
        signUpPanel.setBackground(BACKGROUND_COLOR);

        final int TEXT_WIDTH = 200;

        signUpFields = new JTextField[SignUpLabels.values().length];
        for (SignUpLabels label : SignUpLabels.values()) {
            boolean isPassword = label.getLabelName().toLowerCase().contains("password");

            signUpFields[label.ordinal()] = Components.addTextFieldCenter(signUpPanel, label.getLabelName(),
                    "", TEXT_WIDTH, isPassword, true);
        }

        this.add(signUpPanel, BorderLayout.CENTER);

        // Creating the south panel
        JPanel pnlSouth = new JPanel(new BorderLayout());
        pnlSouth.setBackground(BACKGROUND_COLOR);

        Offsets southOffsets = new Offsets(5, 20, 200, 200);
        Panels.initializeOffsets(pnlSouth, southOffsets);

        // Creating the create account button
        JButton btnCreateAccount = Components.createButton("Create Account", Color.BLACK, BACKGROUND_COLOR, null,
                e -> this.createUser(e));
        pnlSouth.add(btnCreateAccount, BorderLayout.CENTER);

        this.add(pnlSouth, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    private void createUser(ActionEvent e) {
        Alerts.showErrorMessage(getBlankPanel(PanelOrder.NORTH), "");

        if (this.singUpFieldsEmpty()) {
            Alerts.showErrorMessage(getBlankPanel(PanelOrder.NORTH), "One or more fields are empty!");
            return;
        }

        if (!Email.isEmailValid(signUpFields[SignUpLabels.EMAIL.ordinal()])) {
            Alerts.showErrorMessage(getBlankPanel(PanelOrder.NORTH), "Invalid e-mail!");
            return;
        }

        if (!this.isPasswordTheSame()) {
            Alerts.showErrorMessage(getBlankPanel(PanelOrder.NORTH), "Passwords do not match!");
            return;
        }

        JTextField txtUsername = signUpFields[SignUpLabels.USERNAME.ordinal()];
        String username = txtUsername.getText();
        String name = signUpFields[SignUpLabels.NAME.ordinal()].getText();
        String email = signUpFields[SignUpLabels.EMAIL.ordinal()].getText();
        String password = new String(((JPasswordField) signUpFields[SignUpLabels.PASSWORD.ordinal()]).getPassword());

        User user = new User(username, name, email, password, 0, 0);

        Alerts.setErrorBorder(txtUsername, false);
        if (userManager.createUser(user)) {
            this.dispose();
            singUpAction.actionPerformed(e);
        } else {
            Alerts.showErrorMessage(getBlankPanel(PanelOrder.NORTH), "Username already taken!");
            Alerts.setErrorBorder(txtUsername, true);
        }

    }

    private boolean singUpFieldsEmpty() {
        boolean isAnyFieldEmpty = false;
        for (JTextField field : signUpFields) {
            Alerts.setErrorBorder(field, false);
            if (field.getText().isEmpty()) {
                isAnyFieldEmpty = true;
                Alerts.setErrorBorder(field, true);
            }
        }
        return isAnyFieldEmpty;
    }

    private boolean isPasswordTheSame() {
        JPasswordField passwordField = (JPasswordField) signUpFields[SignUpLabels.PASSWORD.ordinal()];
        JPasswordField repeatPasswordField = (JPasswordField) signUpFields[SignUpLabels.REPEAT_PASSWORD.ordinal()];

        Alerts.setErrorBorder(passwordField, false);
        Alerts.setErrorBorder(repeatPasswordField, false);

        String password = new String(passwordField.getPassword());
        String repeatPassword = new String(repeatPasswordField.getPassword());
        if (password.equals(repeatPassword)) {
            return true;
        } else {
            Alerts.setErrorBorder(passwordField, true);
            Alerts.setErrorBorder(repeatPasswordField, true);
            return false;
        }
    }
}
