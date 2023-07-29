package SpendWise.Graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import SpendWise.Graphics.Screen;

public class LoginMenu extends JLabel implements Screen {

    private JLabel lblLogin;
    private JTextField txtLogin;
    private JLabel lblPassword;
    private JTextField txtPassword;

    private JButton btnLogin;
    private JButton btnSignUp;

    private JPanel pnlInputs;
    private JPanel pnlButtons;

    private boolean isOpen;

    public LoginMenu() {
        this.initialize();
    }

    public boolean getIsOpen() {
        return this.isOpen;
    }

    private void initialize() {
        this.pnlButtons = null;
        this.pnlInputs = null;
        this.getPnlButtons();
        this.getPnlInputs();
    }

    public JPanel getPnlInputs() {
        if (pnlInputs == null) {
            pnlInputs = new JPanel(new FlowLayout(FlowLayout.CENTER));
        }
        return pnlInputs;
    }

    public JPanel getPnlButtons() {
        if (pnlButtons == null) {
            pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));

            btnLogin = new JButton("Login");
            btnSignUp = new JButton("or Sign Up!");
            pnlButtons.add(btnLogin);
            pnlButtons.add(btnSignUp);
        }
        return pnlButtons;
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
