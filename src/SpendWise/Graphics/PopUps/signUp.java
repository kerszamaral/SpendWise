package SpendWise.Graphics.PopUps;

import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;
import javax.swing.border.Border;

import SpendWise.User;
import SpendWise.Graphics.PopUp;
import SpendWise.Graphics.Screen;
import SpendWise.Managers.UserManager;
import SpendWise.Utils.LoginLabels;
import SpendWise.Utils.PanelOrder;

public class signUp extends PopUp {
    private JTextField[] signUpFields;
    private final String[] signUpLabels = { "Name:", "Username:", "Email:", "Password:", "Repeat Password:" };
    private UserManager userManager;

    public signUp(Screen parent, String title, UserManager userManager) {
        super(parent, title);
        this.userManager = userManager;
    }

    public void run() {
        // Creating the sign up panel and it's fields
        JPanel signUpPanel = new JPanel(new GridLayout(6, 2));
        signUpPanel.setBackground(BACKGROUND_COLOR);
        signUpFields = new JTextField[signUpLabels.length];
        for (int i = 0; i < signUpLabels.length; i++) {
            JLabel txtInfo = new JLabel(signUpLabels[i]);
            txtInfo.setForeground(Color.WHITE);
            txtInfo.setFont(txtInfo.getFont().deriveFont(Font.BOLD, 14));
            signUpPanel.add(txtInfo);

            boolean isPassword = signUpLabels[i].contains("Password");
            signUpFields[i] = isPassword ? new JPasswordField(15) : new JTextField(15);
            signUpPanel.add(signUpFields[i]);
        }
        this.setCentralPanel(signUpPanel);

        // Creating the spacers for the layout to look nice
        JPanel pnlSouth = new JPanel();
        Screen.initializeBlankPanel(pnlSouth, 100, 50);
        pnlSouth.setLayout(new BorderLayout());

        JPanel pnlSouthEast = new JPanel();
        Screen.initializeBlankPanel(pnlSouthEast, 200, 50);
        pnlSouth.add(pnlSouthEast, BorderLayout.EAST);

        JPanel pnlSouthWest = new JPanel();
        Screen.initializeBlankPanel(pnlSouthWest, 200, 50);
        pnlSouth.add(pnlSouthWest, BorderLayout.WEST);

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
        this.errorString("");

        if (this.singUpFieldsEmpty()) {
            this.errorString("One or more fields are empty!");
            return;
        }

        if (!this.isEmailValid()) {
            this.errorString("Invalid e-mail!");
            return;
        }

        if (!this.isPasswordTheSame()) {
            this.errorString("Passwords do not match!");
            return;
        }

        JTextField txtUsername = signUpFields[LoginLabels.USERNAME.ordinal()];
        String username = txtUsername.getText();
        String name = signUpFields[LoginLabels.NAME.ordinal()].getText();
        String email = signUpFields[LoginLabels.EMAIL.ordinal()].getText();
        String password = new String(((JPasswordField) signUpFields[LoginLabels.PASSWORD.ordinal()]).getPassword());

        User user = new User(username, name, email, password, 0, 0);

        this.errorBorder(txtUsername, false);
        if (userManager.createUser(user)) {
            this.dispose();
            JOptionPane.showMessageDialog(this, "User created successfully!");
        } else {
            this.errorString("Username already taken!");
            this.errorBorder(txtUsername, true);
        }
    }

    private boolean singUpFieldsEmpty() {
        boolean isAnyFieldEmpty = false;
        for (JTextField field : signUpFields) {
            this.errorBorder(field, false);
            if (field.getText().isEmpty()) {
                isAnyFieldEmpty = true;
                this.errorBorder(field, true);
            }
        }
        return isAnyFieldEmpty;
    }

    private boolean isEmailValid() {
        final String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        JTextField emailField = signUpFields[LoginLabels.EMAIL.ordinal()];
        this.errorBorder(emailField, false);
        String email = emailField.getText();
        if (email.matches(emailRegex)) {
            return true;
        } else {
            this.errorBorder(emailField, true);
            return false;
        }
    }

    private boolean isPasswordTheSame() {
        JPasswordField passwordField = (JPasswordField) signUpFields[LoginLabels.PASSWORD.ordinal()];
        JPasswordField repeatPasswordField = (JPasswordField) signUpFields[LoginLabels.REPEAT_PASSWORD.ordinal()];

        this.errorBorder(passwordField, false);
        this.errorBorder(repeatPasswordField, false);

        String password = new String(passwordField.getPassword());
        String repeatPassword = new String(repeatPasswordField.getPassword());
        if (password.equals(repeatPassword)) {
            return true;
        } else {
            this.errorBorder(passwordField, true);
            this.errorBorder(repeatPasswordField, true);
            return false;
        }
    }

    private void errorBorder(JTextField field, Boolean isError) {
        final Border blackBorder = BorderFactory.createLineBorder(Color.BLACK);
        final Border redBorder = BorderFactory.createLineBorder(Color.RED);
        field.setBorder(isError ? redBorder : blackBorder);
    }

    private void errorString(String error) {
        JPanel northPanel = this.getBlankPanel(PanelOrder.NORTH);
        northPanel.removeAll();
        JLabel errorLabel = new JLabel(error);
        errorLabel.setForeground(Color.RED);
        northPanel.add(errorLabel);
        northPanel.revalidate();
        northPanel.repaint();
    }
}
