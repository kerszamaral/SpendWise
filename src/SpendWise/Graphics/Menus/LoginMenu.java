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

        final int MIDDLE_X = 400;
        final int MIDDLE_Y = 200;
        final int SPACER_X = 10;
        final int SPACER_Y = 5;

        final int START_OFFSET = 100;
        final int MIDDLE_OFFSET = 30;
        final int TEXT_WIDTH = 200;
        final int TEXT_HEIGHT = 30;

        // Username Fields

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setForeground(Color.BLACK);
        lblUsername.setFont(lblUsername.getFont().deriveFont(Font.BOLD, 14));
        lblUsername.setBounds(MIDDLE_X-50, MIDDLE_Y - START_OFFSET, TEXT_WIDTH, TEXT_HEIGHT);
        this.add(lblUsername);

        txtLogin = new JTextField(15);
        txtLogin.setBackground(Color.WHITE);
        txtLogin.setFont(txtLogin.getFont().deriveFont(14));
        txtLogin.setBounds(MIDDLE_X-50, MIDDLE_Y - START_OFFSET + TEXT_HEIGHT, TEXT_WIDTH, TEXT_HEIGHT);
        this.add(txtLogin);

        // Password Fields

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setForeground(Color.BLACK);
        lblPassword.setFont(lblPassword.getFont().deriveFont(Font.BOLD, 14));
        lblPassword.setBounds(MIDDLE_X-50, MIDDLE_Y - MIDDLE_OFFSET, TEXT_WIDTH, TEXT_HEIGHT);
        this.add(lblPassword);

        txtPassword = new JPasswordField(15);
        txtPassword.setBackground(Color.WHITE);
        txtPassword.setFont(txtPassword.getFont().deriveFont(14));
        txtPassword.setBounds(MIDDLE_X-50, MIDDLE_Y - MIDDLE_OFFSET + TEXT_HEIGHT, TEXT_WIDTH, TEXT_HEIGHT);
        this.add(txtPassword);

        // Buttons
        final int BUTTON_WIDTH = 95;
        final int BUTTON_HEIGHT = 30;
        final int BUTTON_START_X = MIDDLE_X - BUTTON_WIDTH / 2;
        final int BUTTON_START_Y = MIDDLE_Y + 2 * TEXT_HEIGHT + 2 * SPACER_Y;

        btnLogin = createButton("Login");
        btnLogin.setBounds(BUTTON_START_X, BUTTON_START_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        this.add(btnLogin);

        btnSignUp = createButton("Sign Up!");
        btnSignUp.setBounds(BUTTON_START_X + BUTTON_WIDTH + SPACER_X, BUTTON_START_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        this.add(btnSignUp);

        JPanel whiteBox = createWhiteBox();
        this.add(whiteBox);

        this.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private JPanel createWhiteBox() {
        JPanel whiteBox = new JPanel();
        whiteBox.setBackground(Color.WHITE);
        whiteBox.setBounds(250, 50, 400, 300);
        return whiteBox;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.WHITE);
        button.setFont(button.getFont().deriveFont(Font.BOLD, 14));
        return button;
    }

    private void signUp(ActionEvent e) {
        JFrame signUpWindow = new JFrame("Sign Up");
        final int SIGN_UP_WINDOW_X = 400;
        final int SIGN_UP_WINDOW_Y = 300;
        signUpWindow.setSize(SIGN_UP_WINDOW_X, SIGN_UP_WINDOW_Y);
        signUpWindow.getContentPane().setBackground(BackgroundColor);
        signUpWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        signUpWindow.setLocationRelativeTo(this);

        JPanel signUpPanel = new JPanel(new GridLayout(6, 2));
        signUpPanel.setBackground(BackgroundColor);

        final String[] labels = { "Name:", "Username:", "Email:", "Password:", "Repeat Password:" };
        for (String labelText : labels) {
            JLabel txtInfo = new JLabel(labelText);
            txtInfo.setForeground(Color.WHITE);
            txtInfo.setFont(txtPassword.getFont().deriveFont(Font.BOLD,14));
            signUpPanel.add(txtInfo);

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
