package SpendWise.Graphics.Menus;

import java.awt.GridLayout;

import javax.swing.*;

import SpendWise.Graphics.Screen;

public class AccountMenu extends Screen {
    private JTextField txtName;
    private JTextField txtUsername;
    private JTextField txtEmail;
    private JTextField txtPassword;

    public AccountMenu() {
        this.initialize();
    }

    @Override
    protected void initialize() {
        super.initialize();

        this.setLayout(new GridLayout(4, 2));

        JLabel lblName = new JLabel("Name: ");
        txtName = new JTextField("Default Name", 20);
        this.add(lblName);
        this.add(txtName);

        JLabel lblUsername = new JLabel("Username: ");
        txtUsername = new JTextField("Default Username", 20);
        this.add(lblUsername);
        this.add(txtUsername);

        JLabel lblEmail = new JLabel("Email: ");
        txtEmail = new JTextField("Default Email", 20);
        this.add(lblEmail);
        this.add(txtEmail);

        JLabel lblPassword = new JLabel("Password: ");
        txtPassword = new JTextField("**********", 20);
        this.add(lblPassword);
        this.add(txtPassword);
    }
}
