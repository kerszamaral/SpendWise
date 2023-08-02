package SpendWise.Graphics.Menus;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.*;

import SpendWise.Graphics.Screen;

public class AccountMenu extends Screen {
    // TODO implement AccountMenu integration with User class
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

        this.setLayout(null);
        this.setBackground(Color.green);

        JLabel lblName = new JLabel("Name: ");
        lblName.setBounds(10, 10, 80, 25);
        txtName = new JTextField("Default Name", 20);
        txtName.setBounds(100, 10, 160, 25);
        this.add(lblName);
        this.add(txtName);

        JLabel lblUsername = new JLabel("Username: ");
        lblUsername.setBounds(10, 40, 80, 25);
        txtUsername = new JTextField("Default Username", 20);
        txtUsername.setBounds(100, 40, 160, 25);
        this.add(lblUsername);
        this.add(txtUsername);

        JLabel lblEmail = new JLabel("Email: ");
        lblEmail.setBounds(10, 70, 80, 25);
        txtEmail = new JTextField("Default Email", 20);
        txtEmail.setBounds(100, 70, 160, 25);
        this.add(lblEmail);
        this.add(txtEmail);

        JLabel lblPassword = new JLabel("Password: ");
        lblPassword.setBounds(10, 100, 80, 25);
        txtPassword = new JTextField("**********", 20);
        txtPassword.setBounds(100, 100, 160, 25);
        this.add(lblPassword);
        this.add(txtPassword);
    }
}
