package SpendWise.Graphics.Menus;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import SpendWise.Graphics.Screen;
import SpendWise.Graphics.PopUps.signUp;
import SpendWise.Managers.UserManager;
import SpendWise.Utils.GraphicsUtils;
import SpendWise.Utils.Offsets;
import SpendWise.Utils.Enums.PanelOrder;
import SpendWise.Graphics.PopUp;

public class LoginMenu extends Screen {
    private JButton btnLogin;
    private JButton btnSignUp;
    private JTextField txtLogin;
    private JPasswordField txtPassword;
    private UserManager userManager;
    private JPanel pnlTop;

    public LoginMenu(ActionListener loginAction, UserManager userManager) {
        this.initialize();
        this.userManager = userManager;
        btnLogin.addActionListener(loginAction);
        btnSignUp.addActionListener(e -> this.signUp(e));
    }

    @Override
    protected void initialize() {
        this.setLayout(new BorderLayout());

        Offsets offsets = new Offsets(100, 100, 270, 270);
        GraphicsUtils.initializeOffsets(this, offsets);
        final int TEXT_WIDTH = 200;
        final int TEXT_HEIGHT = 30;

        JPanel pnlMiddle = new JPanel();
        pnlMiddle.setBackground(Color.WHITE);
        pnlMiddle.setLayout(new BorderLayout());

        Offsets middleOffsets = new Offsets(50, 50, 100, 100);
        JPanel[] blankPanels = GraphicsUtils.initializeOffsets(pnlMiddle, middleOffsets, Color.WHITE);
        pnlTop = blankPanels[PanelOrder.NORTH.ordinal()];

        JPanel pnlLogin = new JPanel();
        pnlLogin.setBackground(Color.WHITE);
        pnlLogin.setLayout(new BoxLayout(pnlLogin, BoxLayout.Y_AXIS));
        pnlLogin.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Username Fields
        JPanel lblUsername = this.createPanel("Username");
        pnlLogin.add(lblUsername);

        txtLogin = this.createTextField(false, TEXT_WIDTH, TEXT_HEIGHT);
        pnlLogin.add(txtLogin);

        pnlLogin.add(Box.createRigidArea(new Dimension(0, 20)));

        // Password Fields
        JPanel lblPassword = this.createPanel("Password");
        pnlLogin.add(lblPassword);

        txtPassword = (JPasswordField) this.createTextField(true, TEXT_WIDTH, TEXT_HEIGHT);
        pnlLogin.add(txtPassword);

        pnlLogin.add(Box.createRigidArea(new Dimension(0, 50)));

        // Buttons
        final int BUTTON_WIDTH = 95;
        final int BUTTON_HEIGHT = 30;
        JPanel pnlButtons = new JPanel();
        pnlButtons.setBackground(Color.WHITE);

        btnLogin = createButton("Login", Color.WHITE, Color.BLACK, BUTTON_WIDTH, BUTTON_HEIGHT);
        pnlButtons.add(btnLogin);

        btnSignUp = createButton("Sign Up!", Color.BLACK, BACKGROUND_COLOR, BUTTON_WIDTH, BUTTON_HEIGHT);
        pnlButtons.add(btnSignUp);

        pnlButtons.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlLogin.add(pnlButtons);

        pnlMiddle.add(pnlLogin, BorderLayout.CENTER);
        this.add(pnlMiddle, BorderLayout.CENTER);
    }

    private JPanel createPanel(String text) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        JLabel label = new JLabel(text);
        label.setForeground(Color.BLACK);
        label.setBackground(Color.RED);
        label.setFont(STD_FONT_BOLD);
        panel.add(label);
        return panel;
    }

    private JTextField createTextField(Boolean isPassword, int width, int height) {
        JTextField textField = isPassword ? new JPasswordField(1) : new JTextField(2);
        textField.setBackground(Color.WHITE);
        textField.setFont(STD_FONT);

        Dimension size = new Dimension(width, height);
        GraphicsUtils.defineSize(textField, size);

        return textField;
    }

    private JButton createButton(String text, Color background, Color foreground, int width, int height) {
        JButton button = new JButton(text);
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFont(STD_FONT_BOLD);

        Dimension size = new Dimension(width, height);
        GraphicsUtils.defineSize(button, size);

        return button;
    }

    private void singUpSuccess(ActionEvent e) {
        GraphicsUtils.showMessage(pnlTop, "Sign up successful!", BACKGROUND_COLOR);
    }

    private void signUp(ActionEvent action) {
        PopUp signUpWindow = new signUp(this, "Sign Up", userManager, e -> singUpSuccess(e));
        signUpWindow.run();
    }

    public boolean authorizeUser() {
        String username = txtLogin.getText();
        String password = new String(txtPassword.getPassword());
        GraphicsUtils.setErrorBorder(txtLogin, false);
        GraphicsUtils.setErrorBorder(txtPassword, false);
        GraphicsUtils.showErrorMessage(pnlTop, "");

        if (username.equals("")) {
            GraphicsUtils.showErrorMessage(pnlTop, "Please enter a username.");
            GraphicsUtils.setErrorBorder(txtLogin, true);
            return false;
        }

        if (password.equals("")) {
            GraphicsUtils.showErrorMessage(pnlTop, "Please enter a password.");
            GraphicsUtils.setErrorBorder(txtPassword, true);
            return false;
        }

        if (userManager.validateLogin(username, password)) {
            return true;
        } else {
            GraphicsUtils.showErrorMessage(pnlTop, "Invalid username or password.");
            GraphicsUtils.setErrorBorder(txtLogin, true);
            GraphicsUtils.setErrorBorder(txtPassword, true);
            return false;
        }
    }
}
