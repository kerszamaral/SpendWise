package SpendWise.Graphics.Menus;

import javax.swing.*;

import SpendWise.Graphics.PanelScreen;

import java.awt.*;

public class LoginMenu extends PanelScreen {

    private JTextField txtLogin;
    private JTextField txtPassword;
    private JButton btnLogin;

    /**
     * @return the btnLogin
     */
    public JButton getBtnLogin() {
        return btnLogin;
    }

    private JButton btnSignUp;

    private boolean isOpen;

    public LoginMenu() {
        this.initialize();
        this.closeScreen();
    }

    public boolean getIsOpen() {
        return this.isOpen;
    }

    private void initialize() {
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
