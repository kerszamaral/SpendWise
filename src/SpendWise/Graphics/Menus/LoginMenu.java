package SpendWise.Graphics.Menus;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import SpendWise.Graphics.PopUp;
import SpendWise.Graphics.Screen;
import SpendWise.Graphics.PopUps.signUp;
import SpendWise.Managers.UserManager;
import SpendWise.Utils.Offsets;
import SpendWise.Utils.Enums.PanelOrder;
import SpendWise.Utils.Graphics.Alerts;
import SpendWise.Utils.Graphics.Components;
import SpendWise.Utils.Graphics.Panels;

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
        blankPanels = Panels.createPanelWithCenter(this, innerOffsets, outerOffsets, ACCENT_COLOR);

        // Login Panel
        JPanel pnlLogin = getBlankPanel(PanelOrder.CENTRAL);
        pnlLogin.setLayout(new BoxLayout(pnlLogin, BoxLayout.Y_AXIS));
        pnlLogin.setAlignmentX(Component.CENTER_ALIGNMENT);

        final int TEXT_WIDTH = 200;

        // Username Fields
        txtLogin = Components.addTextFieldCenter(pnlLogin, "Username", "", TEXT_WIDTH, false, true);
        pnlLogin.add(Box.createRigidArea(new Dimension(0, 20)));

        // Password Fields
        txtPassword = (JPasswordField) Components.addTextFieldCenter(pnlLogin, "Password", "", TEXT_WIDTH, true,
                true);
        pnlLogin.add(Box.createRigidArea(new Dimension(0, 50)));

        // Buttons
        final Dimension BUTTON_SIZE = new Dimension(95, 30);

        btnLogin = Components.createButton("Login", Color.WHITE, Color.BLACK, BUTTON_SIZE);
        getBlankPanel(PanelOrder.SOUTH).add(btnLogin);

        btnSignUp = Components.createButton("Sign Up!", Color.BLACK, BACKGROUND_COLOR, BUTTON_SIZE);
        getBlankPanel(PanelOrder.SOUTH).add(btnSignUp);
    }

    private void singUpSuccess(ActionEvent e) {
        Alerts.showMessage(getBlankPanel(PanelOrder.NORTH), "Sign up successful!", BACKGROUND_COLOR);
    }

    private void signUp(ActionEvent action) {
        PopUp signUpWindow = new signUp(this, "Sign Up", userManager, e -> singUpSuccess(e));
        signUpWindow.run();
    }

    public boolean authorizeUser() {
        String username = txtLogin.getText();
        String password = new String(txtPassword.getPassword());
        Alerts.setErrorBorder(txtLogin, false);
        Alerts.setErrorBorder(txtPassword, false);
        Alerts.showErrorMessage(getBlankPanel(PanelOrder.NORTH), "");

        if (username.equals("")) {
            Alerts.showErrorMessage(getBlankPanel(PanelOrder.NORTH), "Please enter a username.");
            Alerts.setErrorBorder(txtLogin, true);
            return false;
        }

        if (password.equals("")) {
            Alerts.showErrorMessage(getBlankPanel(PanelOrder.NORTH), "Please enter a password.");
            Alerts.setErrorBorder(txtPassword, true);
            return false;
        }

        if (userManager.validateLogin(username, password)) {
            return true;
        } else {
            Alerts.showErrorMessage(getBlankPanel(PanelOrder.NORTH), "Invalid username or password.");
            Alerts.setErrorBorder(txtLogin, true);
            Alerts.setErrorBorder(txtPassword, true);
            return false;
        }
    }
}
