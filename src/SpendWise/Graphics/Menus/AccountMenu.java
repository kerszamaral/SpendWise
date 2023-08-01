package SpendWise.Graphics.Menus;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import SpendWise.Graphics.PanelScreen;

public class AccountMenu extends PanelScreen {

    private boolean isOpen;

    private JTextField txtName;
    private JTextField txtUsername;
    private JTextField txtEmail;
    private JTextField txtPassword;

    public AccountMenu() {
        this.initialize();
        this.closeScreen();
    }

    public boolean getIsOpen() {
        return this.isOpen;
    }

    private void initialize() {
        JPanel fields = new JPanel(new GridLayout(4, 2));

        JLabel lblName = new JLabel("Name: ");
        txtName = new JTextField("Default Name", 20);
        fields.add(lblName);
        fields.add(txtName);

        JLabel lblUsername = new JLabel("Username: ");
        txtUsername = new JTextField("Default Username", 20);
        fields.add(lblUsername);
        fields.add(txtUsername);

        JLabel lblEmail = new JLabel("Email: ");
        txtEmail = new JTextField("Default Email", 20);
        fields.add(lblEmail);
        fields.add(txtEmail);

        JLabel lblPassword = new JLabel("Password: ");
        txtPassword = new JTextField("**********", 20);
        fields.add(lblPassword);
        fields.add(txtPassword);

        this.add(fields);
        return;
    }

    public boolean openScreen() {
        this.isOpen = true;
        this.setVisible(isOpen);
        return this.isOpen;
    }

    public boolean closeScreen() {
        this.isOpen = false;
        this.setVisible(isOpen);
        return !(this.isOpen);
    }
}
