package SpendWise.Graphics.Menus;

import javax.swing.*;
import SpendWise.Graphics.Screen;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginMenu extends Screen {
    // TODO make them work with users
    private JButton btnLogin;
    private JButton btnSignUp;
    private JTextField txtLogin;
    private JPasswordField txtPassword;

    public LoginMenu(ActionListener loginAction) {
        this.initialize();
        btnLogin.addActionListener(loginAction);
        btnSignUp.addActionListener(e -> this.signUp(e));
    }

    @Override
    protected void initialize() {
        super.initialize();

        this.setLayout(null);

        final int middleX = 400;
        final int middleY = 200;
        final int spacerX = 10;
        final int spacerY = 5;

        final int startOffset = 100;
        final int middleOffset = 30;
        final int textWidth = 100;
        final int textHeight = 20;

        // Username Fields

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setForeground(Color.WHITE);
        lblUsername.setFont(lblUsername.getFont().deriveFont(Font.BOLD, 14));
        lblUsername.setBounds(middleX, middleY - startOffset, textWidth, textHeight);
        this.add(lblUsername);

        txtLogin = new JTextField(15);
        txtLogin.setBackground(Color.WHITE);
        txtLogin.setFont(txtLogin.getFont().deriveFont(14));
        txtLogin.setBounds(middleX, middleY - startOffset + textHeight + spacerY, textWidth, textHeight);
        this.add(txtLogin);

        // Password Fields

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setForeground(Color.WHITE);
        lblPassword.setFont(lblPassword.getFont().deriveFont(Font.BOLD, 14));
        lblPassword.setBounds(middleX, middleY - middleOffset, textWidth, textHeight);
        this.add(lblPassword);

        txtPassword = new JPasswordField(15);
        txtPassword.setBackground(Color.WHITE);
        txtPassword.setFont(txtPassword.getFont().deriveFont(14));
        txtPassword.setBounds(middleX, middleY - middleOffset + textHeight + spacerY, textWidth, textHeight);
        this.add(txtPassword);

        // Buttons
        final int buttonWidth = 100;
        final int buttonHeight = 20;
        final int buttonStartX = middleX - buttonWidth / 2;
        final int buttonStartY = middleY + 2 * textHeight + 2 * spacerY;

        btnLogin = createButton("Login");
        btnLogin.setBounds(buttonStartX, buttonStartY, buttonWidth, buttonHeight);
        this.add(btnLogin);

        btnSignUp = createButton("Sign Up!");
        btnSignUp.setBounds(buttonStartX + buttonWidth + spacerX, buttonStartY, buttonWidth, buttonHeight);
        this.add(btnSignUp);

        this.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.WHITE);
        button.setFont(button.getFont().deriveFont(Font.BOLD, 14));
        return button;
    }

    private void signUp(ActionEvent e) {
        JFrame signUpWindow = new JFrame("Sign Up");
        final int signUpWindowX = 400;
        final int signUpWindowY = 300;
        signUpWindow.setSize(signUpWindowX, signUpWindowY);
        signUpWindow.getContentPane().setBackground(BackgroundColor);
        signUpWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        signUpWindow.setLocationRelativeTo(this);

        JPanel signUpPanel = new JPanel(new GridLayout(6, 2));
        signUpPanel.setBackground(BackgroundColor);

        final String[] labels = { "Name:", "Username:", "Email:", "Password:", "Repeat Password:" };
        for (String labelText : labels) {
            signUpPanel.add(new JLabel(labelText));

            signUpPanel.add(labelText.equals("Password:") || labelText.equals("Repeat Password:")
                    ? new JPasswordField(15)
                    : new JTextField(15));
        }

        JButton signUpButton = new JButton("Save");
        signUpButton.addActionListener(actionEvent -> {
            // TODO save user to database
            signUpWindow.dispose();
        });

        signUpPanel.add(signUpButton);
        signUpPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        signUpWindow.add(signUpPanel);
        signUpWindow.setVisible(true);
    }

    public boolean authorizeUser() {
        // Placeholder, actual authorization logic needed
        return true;
    }
}
