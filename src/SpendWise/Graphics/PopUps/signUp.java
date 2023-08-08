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
import SpendWise.Utils.Enums.PanelOrder;
import SpendWise.Utils.Enums.SignUpLabels;

public class signUp extends PopUp {
    private JTextField[] signUpFields;
    private UserManager userManager;
    private JPanel pnlTop;
    private ActionListener singUpAction;

    public signUp(Screen parent, String title, UserManager userManager, ActionListener singUpAction) {
        super(parent, title);
        this.userManager = userManager;
        this.singUpAction = singUpAction;
    }

    @Override
    public void run() {
        // Creating the sign up panel and it's fields
        JPanel signUpPanel = new JPanel(new GridLayout(12, 2));
        signUpPanel.setBackground(BACKGROUND_COLOR);
        signUpFields = new JTextField[SignUpLabels.values().length];
        for (SignUpLabels label : SignUpLabels.values()) {
            JLabel txtInfo = new JLabel(label.getLabelName());
            txtInfo.setForeground(Color.WHITE);
            txtInfo.setFont(txtInfo.getFont().deriveFont(Font.BOLD, 14));
            txtInfo.setHorizontalAlignment(JLabel.RIGHT);
            signUpPanel.add(txtInfo);

            boolean isPassword = label.getLabelName().toLowerCase().contains("password");
            signUpFields[label.ordinal()] = isPassword ? new JPasswordField(15) : new JTextField(15);
            signUpPanel.add(signUpFields[label.ordinal()]);

            // Creating the spacers for the layout to look nice
            signUpPanel.add(new JLabel(""));
            signUpPanel.add(new JLabel(""));
        }

        this.setLayout(new BorderLayout());

        JPanel[] blankPanels = GraphicsUtils.createOffsets((JPanel) this.getContentPane(), 50, 0, 100, 100);
        pnlTop = blankPanels[PanelOrder.NORTH.ordinal()];

        this.add(signUpPanel, BorderLayout.CENTER);

        // Creating the spacers for the layout to look nice
        JPanel pnlSouth = new JPanel(new BorderLayout());
        pnlSouth.setBackground(BACKGROUND_COLOR);

        GraphicsUtils.createOffsets(pnlSouth, 5, 20, 200, 200);

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
        GraphicsUtils.showErrorMessage(pnlTop, "");

        if (this.singUpFieldsEmpty()) {
            GraphicsUtils.showErrorMessage(pnlTop, "One or more fields are empty!");
            return;
        }

        if (!this.isEmailValid()) {
            GraphicsUtils.showErrorMessage(pnlTop, "Invalid e-mail!");
            return;
        }

        if (!this.isPasswordTheSame()) {
            GraphicsUtils.showErrorMessage(pnlTop, "Passwords do not match!");
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
            GraphicsUtils.showErrorMessage(pnlTop, "Username already taken!");
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
