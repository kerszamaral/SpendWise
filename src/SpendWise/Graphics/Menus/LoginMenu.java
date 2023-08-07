package SpendWise.Graphics.Menus;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import SpendWise.User;
import SpendWise.Graphics.Screen;
import SpendWise.Managers.UserManager;
import SpendWise.Utils.LoginLabels;
import SpendWise.Graphics.PopUp;

public class LoginMenu extends Screen {
    private JButton btnLogin;
    private JButton btnSignUp;
    private JTextField txtLogin;
    private JPasswordField txtPassword;
    private UserManager userManager;
    private JTextField[] signUpFields;
    private final String[] signUpLabels = { "Name:", "Username:", "Email:", "Password:", "Repeat Password:" };
    private PopUp signUpWindow;

    public LoginMenu(ActionListener loginAction, UserManager userManager) {
        this.initialize();
        this.userManager = userManager;
        btnLogin.addActionListener(loginAction);
        btnSignUp.addActionListener(e -> this.signUp(e));
    }

    @Override
    protected void initialize() {
        super.initialize();

        this.setLayout(null); // We will use absolute positioning

        final int MIDDLE_X = 400;
        final int MIDDLE_Y = 200;
        final int SPACER_X = 10;
        final int SPACER_Y = 5;

        final int START_OFFSET = 100;
        final int MIDDLE_OFFSET = 30;
        final int TEXT_WIDTH = 200;
        final int TEXT_HEIGHT = 30;

        // Username Fields
        JLabel lblUsername = this.createLabel("Username:");
        lblUsername.setBounds(MIDDLE_X - 50, MIDDLE_Y - START_OFFSET, TEXT_WIDTH, TEXT_HEIGHT);
        this.add(lblUsername);

        txtLogin = this.createTextField(false);
        txtLogin.setBounds(MIDDLE_X - 50, MIDDLE_Y - START_OFFSET + TEXT_HEIGHT, TEXT_WIDTH, TEXT_HEIGHT);
        this.add(txtLogin);

        // Password Fields
        JLabel lblPassword = this.createLabel("Password:");
        lblPassword.setBounds(MIDDLE_X - 50, MIDDLE_Y - MIDDLE_OFFSET, TEXT_WIDTH, TEXT_HEIGHT);
        this.add(lblPassword);

        txtPassword = (JPasswordField) this.createTextField(true);
        txtPassword.setBounds(MIDDLE_X - 50, MIDDLE_Y - MIDDLE_OFFSET + TEXT_HEIGHT, TEXT_WIDTH, TEXT_HEIGHT);
        this.add(txtPassword);

        // Buttons
        final int BUTTON_WIDTH = 95;
        final int BUTTON_HEIGHT = 30;
        final int BUTTON_START_X = MIDDLE_X - BUTTON_WIDTH / 2;
        final int BUTTON_START_Y = MIDDLE_Y + 2 * TEXT_HEIGHT + 2 * SPACER_Y;

        btnLogin = createButton("Login", Color.WHITE, Color.BLACK);
        btnLogin.setBounds(BUTTON_START_X, BUTTON_START_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        this.add(btnLogin);

        btnSignUp = createButton("Sign Up!", Color.BLACK, BACKGROUND_COLOR);
        btnSignUp.setBounds(BUTTON_START_X + BUTTON_WIDTH + SPACER_X, BUTTON_START_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        this.add(btnSignUp);

        JPanel whiteBox = new JPanel();
        whiteBox.setBackground(Color.WHITE);
        whiteBox.setBounds(250, 50, 400, 300);
        this.add(whiteBox);

        this.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.BLACK);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 14));
        return label;
    }

    private JTextField createTextField(Boolean isPassword) {
        JTextField textField = isPassword ? new JPasswordField(15) : new JTextField(15);
        textField.setBackground(Color.WHITE);
        textField.setFont(textField.getFont().deriveFont(14));
        return textField;
    }

    private JButton createButton(String text, Color background, Color foreground) {
        JButton button = new JButton(text);
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFont(button.getFont().deriveFont(Font.BOLD, 14));
        return button;
    }

    private void signUp(ActionEvent action) {
        signUpWindow = new PopUp(this, "Sign Up");

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
        signUpWindow.setCentralPanel(signUpPanel);

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

        signUpWindow.add(pnlSouth, BorderLayout.SOUTH);

        signUpWindow.setVisible(true);
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
            JOptionPane.showMessageDialog(this, "User created successfully!");
            signUpWindow.dispose();
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
        JPanel northPanel = signUpWindow.getPnlNorth();
        northPanel.removeAll();
        JLabel errorLabel = new JLabel(error);
        errorLabel.setForeground(Color.RED);
        northPanel.add(errorLabel);
        northPanel.revalidate();
        northPanel.repaint();
    }

    public boolean authorizeUser() {
        String username = txtLogin.getText();
        String password = new String(txtPassword.getPassword());
        if (username.equals("") || password.equals("")) {
            JOptionPane.showMessageDialog(this, "Please enter a username and password.");
            return false;
        }

        if (userManager.validateLogin(username, password)) {
            return true;
        } else {
            JOptionPane.showMessageDialog(this, "Invalid user.");
            return false;
        }
    }
}
