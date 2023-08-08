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

    public LoginMenu(ActionListener loginAction, UserManager userManager) {
        this.initialize();
        this.userManager = userManager;
        btnLogin.addActionListener(loginAction);
        btnSignUp.addActionListener(e -> this.signUp(e));
    }

    @Override
    protected void initialize() {
        this.setLayout(new BorderLayout());

        Offsets outerOffsets = new Offsets(100, 100, 270, 270);
        Offsets innerOffsets = new Offsets(50, 100, 50, 50);
        blankPanels = GraphicsUtils.createPanelWithCenter(this, innerOffsets, outerOffsets, ACCENT_COLOR);

        // Login Panel
        JPanel pnlLogin = getBlankPanel(PanelOrder.CENTRAL);
        pnlLogin.setLayout(new BoxLayout(pnlLogin, BoxLayout.Y_AXIS));
        pnlLogin.setAlignmentX(Component.CENTER_ALIGNMENT);

        final int TEXT_WIDTH = 200;

        // Username Fields
        txtLogin = GraphicsUtils.addTextFieldCenter(pnlLogin, "Username", "", TEXT_WIDTH, false, true);
        pnlLogin.add(Box.createRigidArea(new Dimension(0, 20)));

        // Password Fields
        txtPassword = (JPasswordField) GraphicsUtils.addTextFieldCenter(pnlLogin, "Password", "", TEXT_WIDTH, true,
                true);
        pnlLogin.add(Box.createRigidArea(new Dimension(0, 50)));

        // Buttons
        final Dimension BUTTON_SIZE = new Dimension(95, 30);

        btnLogin = GraphicsUtils.createButton("Login", Color.WHITE, Color.BLACK, BUTTON_SIZE);
        getBlankPanel(PanelOrder.SOUTH).add(btnLogin);

        btnSignUp = GraphicsUtils.createButton("Sign Up!", Color.BLACK, BACKGROUND_COLOR, BUTTON_SIZE);
        getBlankPanel(PanelOrder.SOUTH).add(btnSignUp);
    }

    private void singUpSuccess(ActionEvent e) {
        GraphicsUtils.showMessage(getBlankPanel(PanelOrder.NORTH), "Sign up successful!", BACKGROUND_COLOR);
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
        GraphicsUtils.showErrorMessage(getBlankPanel(PanelOrder.NORTH), "");

        if (username.equals("")) {
            GraphicsUtils.showErrorMessage(getBlankPanel(PanelOrder.NORTH), "Please enter a username.");
            GraphicsUtils.setErrorBorder(txtLogin, true);
            return false;
        }

        if (password.equals("")) {
            GraphicsUtils.showErrorMessage(getBlankPanel(PanelOrder.NORTH), "Please enter a password.");
            GraphicsUtils.setErrorBorder(txtPassword, true);
            return false;
        }

        if (userManager.validateLogin(username, password)) {
            return true;
        } else {
            GraphicsUtils.showErrorMessage(getBlankPanel(PanelOrder.NORTH), "Invalid username or password.");
            GraphicsUtils.setErrorBorder(txtLogin, true);
            GraphicsUtils.setErrorBorder(txtPassword, true);
            return false;
        }
    }
}
