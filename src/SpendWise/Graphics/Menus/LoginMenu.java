package SpendWise.Graphics.Menus;

import javax.swing.*;

import SpendWise.Graphics.Screen;

import java.awt.*;
import java.awt.event.ActionListener;

public class LoginMenu extends Screen {

    private JTextField txtLogin;
    private JTextField txtPassword;
    private JButton btnLogin;

    private JButton btnSignUp;

    public LoginMenu(ActionListener loginAction) {
        this.initialize();
        this.btnLogin.addActionListener(loginAction);
    }

    @Override
    protected void initialize() {
        super.initialize();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel pnlInputs = new JPanel();
        pnlInputs.setLayout(new BoxLayout(pnlInputs, BoxLayout.Y_AXIS));

        JLabel lblLogin = new JLabel("Login: ");
        txtLogin = new JTextField(20);
        pnlInputs.add(lblLogin);
        pnlInputs.add(txtLogin);

        JLabel lblPassword = new JLabel("Password: ");
        txtPassword = new JTextField(20);
        pnlInputs.add(lblPassword);
        pnlInputs.add(txtPassword);

        this.add(pnlInputs);

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));

        btnLogin = new JButton("Login");
        btnSignUp = new JButton("or Sign Up!");
        pnlButtons.add(btnLogin);
        pnlButtons.add(btnSignUp);

        this.add(pnlButtons);
    }

    public boolean authorizeUser() {
        return true;
    }
}
