package SpendWise.Graphics.Menus;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import SpendWise.Graphics.Screen;
import SpendWise.Graphics.PopUps.signUp;
import SpendWise.Managers.UserManager;
import SpendWise.Graphics.PopUp;

public class LoginMenu extends Screen {
    private JButton btnLogin;
    private JButton btnSignUp;
    private JTextField txtLogin;
    private JPasswordField txtPassword;
    private UserManager userManager;

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

    private void signUp(ActionEvent e) {
        PopUp signUpWindow = new signUp(this, "Sign Up", userManager);
        signUpWindow.run();
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
