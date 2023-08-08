package SpendWise.Graphics.PopUps;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import SpendWise.User;
import SpendWise.Graphics.PopUp;
import SpendWise.Graphics.Screen;
import SpendWise.Managers.UserManager;
import SpendWise.Utils.GraphicsUtils;
import SpendWise.Utils.Offsets;
import SpendWise.Utils.Enums.PanelOrder;
import SpendWise.Utils.Enums.SignUpLabels;

public class signUp extends PopUp {
    private JTextField[] signUpFields;
    private UserManager userManager;
    private ActionListener singUpAction;

    public signUp(Screen parent, String title, UserManager userManager, ActionListener singUpAction) {
        super(parent, title);
        this.userManager = userManager;
        this.singUpAction = singUpAction;
    }

    @Override
    public void run() {
        // Creating the sign up panel and it's fields
        this.setLayout(new BorderLayout());
        Offsets offsets = new Offsets(50, 0, 100, 100);
        blankPanels = GraphicsUtils.initializeOffsets((JPanel) this.getContentPane(), offsets);

        JPanel signUpPanel = new JPanel(new GridLayout(SignUpLabels.values().length * 2, 1));
        signUpPanel.setBackground(ACCENT_COLOR);

        final int TEXT_WIDTH = 200;

        signUpFields = new JTextField[SignUpLabels.values().length];
        for (SignUpLabels label : SignUpLabels.values()) {
            boolean isPassword = label.getLabelName().toLowerCase().contains("password");

            signUpFields[label.ordinal()] = GraphicsUtils.addTextFieldCenter(signUpPanel, label.getLabelName(),
                    "", TEXT_WIDTH, isPassword, true);
        }

        this.add(signUpPanel, BorderLayout.CENTER);

        // Creating the south panel
        JPanel pnlSouth = new JPanel(new BorderLayout());
        pnlSouth.setBackground(BACKGROUND_COLOR);

        Offsets southOffsets = new Offsets(5, 20, 200, 200);
        // GraphicsUtils.initializeButton(pnlSouth, southOffsets, "Create Account",
        // ACCENT_COLOR, singUpAction)
        GraphicsUtils.initializeOffsets(pnlSouth, southOffsets);

        // Creating the create account button
        JButton btnCreateAccount = new JButton("Create Account");
        btnCreateAccount.setBackground(Color.BLACK);
        btnCreateAccount.setForeground(BACKGROUND_COLOR);
        btnCreateAccount.addActionListener(e -> this.createUser(e));
        pnlSouth.add(btnCreateAccount, BorderLayout.CENTER);

        this.add(pnlSouth, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    private void createUser(ActionEvent e) {
        GraphicsUtils.showErrorMessage(getBlankPanel(PanelOrder.NORTH), "");

        if (this.singUpFieldsEmpty()) {
            GraphicsUtils.showErrorMessage(getBlankPanel(PanelOrder.NORTH), "One or more fields are empty!");
            return;
        }

        if (!this.isEmailValid()) {
            GraphicsUtils.showErrorMessage(getBlankPanel(PanelOrder.NORTH), "Invalid e-mail!");
            return;
        }

        if (!this.isPasswordTheSame()) {
            GraphicsUtils.showErrorMessage(getBlankPanel(PanelOrder.NORTH), "Passwords do not match!");
            return;
        }

        JTextField txtUsername = signUpFields[SignUpLabels.USERNAME.ordinal()];
        String username = txtUsername.getText();
        String name = signUpFields[SignUpLabels.NAME.ordinal()].getText();
        String email = signUpFields[SignUpLabels.EMAIL.ordinal()].getText();
        String password = new String(((JPasswordField) signUpFields[SignUpLabels.PASSWORD.ordinal()]).getPassword());

        User user = new User(username, name, email, password, 0, 0);

        GraphicsUtils.setErrorBorder(txtUsername, false);
        if (userManager.createUser(user)) {
            this.dispose();
            singUpAction.actionPerformed(e);
        } else {
            GraphicsUtils.showErrorMessage(getBlankPanel(PanelOrder.NORTH), "Username already taken!");
            GraphicsUtils.setErrorBorder(txtUsername, true);
        }

    }

    private boolean singUpFieldsEmpty() {
        boolean isAnyFieldEmpty = false;
        for (JTextField field : signUpFields) {
            GraphicsUtils.setErrorBorder(field, false);
            if (field.getText().isEmpty()) {
                isAnyFieldEmpty = true;
                GraphicsUtils.setErrorBorder(field, true);
            }
        }
        return isAnyFieldEmpty;
    }

    private boolean isEmailValid() {
        final String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        JTextField emailField = signUpFields[SignUpLabels.EMAIL.ordinal()];
        GraphicsUtils.setErrorBorder(emailField, false);
        String email = emailField.getText();
        if (email.matches(emailRegex)) {
            return true;
        } else {
            GraphicsUtils.setErrorBorder(emailField, true);
            return false;
        }
    }

    private boolean isPasswordTheSame() {
        JPasswordField passwordField = (JPasswordField) signUpFields[SignUpLabels.PASSWORD.ordinal()];
        JPasswordField repeatPasswordField = (JPasswordField) signUpFields[SignUpLabels.REPEAT_PASSWORD.ordinal()];

        GraphicsUtils.setErrorBorder(passwordField, false);
        GraphicsUtils.setErrorBorder(repeatPasswordField, false);

        String password = new String(passwordField.getPassword());
        String repeatPassword = new String(repeatPasswordField.getPassword());
        if (password.equals(repeatPassword)) {
            return true;
        } else {
            GraphicsUtils.setErrorBorder(passwordField, true);
            GraphicsUtils.setErrorBorder(repeatPasswordField, true);
            return false;
        }
    }
}
