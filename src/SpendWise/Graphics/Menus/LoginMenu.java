package SpendWise.Graphics.Menus;

import javax.swing.*;
import SpendWise.Graphics.Screen;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginMenu extends Screen {
    private JButton btnLogin;
    private JButton btnSignUp;

    public LoginMenu(ActionListener loginAction) {
        initializeComponents();
        btnLogin.addActionListener(loginAction);
        btnSignUp.addActionListener(new SignUpAction());
    }

    private void initializeComponents() {
        super.initialize();
        setBackground(new Color(52, 152, 219));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel pnlContent = new JPanel();
        pnlContent.setLayout(new BoxLayout(pnlContent, BoxLayout.Y_AXIS));
        pnlContent.setOpaque(false);

        createInputField(pnlContent, "Username:", new JTextField(15));
        createInputField(pnlContent, "Password:", new JPasswordField(15));

        btnLogin = createButton("Login");
        btnSignUp = createButton("Sign Up!");

        JPanel pnlButtons = new JPanel();
        pnlButtons.setOpaque(false);
        pnlButtons.add(btnLogin);
        pnlButtons.add(btnSignUp);

        pnlContent.add(pnlButtons);
        pnlContent.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(pnlContent);
    }

    private void createInputField(JPanel panel, String labelText, JComponent inputField) {
        JLabel label = new JLabel(labelText);
        label.setForeground(Color.WHITE);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 14));

        inputField.setBackground(Color.WHITE);
        inputField.setFont(inputField.getFont().deriveFont(14));

        JPanel inputPanel = new JPanel();
        inputPanel.setOpaque(false);
        inputPanel.add(inputField);

        panel.add(label);
        panel.add(inputPanel);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.WHITE);
        button.setFont(button.getFont().deriveFont(Font.BOLD, 14));
        return button;
    }

    private class SignUpAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame signUpFrame = new JFrame("Sign Up");
            signUpFrame.getContentPane().setBackground(new Color(52, 152, 219));
            signUpFrame.setSize(400, 300);
            signUpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            
         
            signUpFrame.setLocationRelativeTo(null);

            JPanel signUpPanel = new JPanel(new GridLayout(6, 2));
            signUpPanel.setBackground(new Color(52, 152, 219));

            String[] labels = { "Name:", "Username:", "Email:", "Password:", "Repeat Password:" };
            for (String labelText : labels) {
                signUpPanel.add(new JLabel(labelText));
                signUpPanel.add(new JTextField(15));
            }

            JButton signUpButton = new JButton("Save");
            signUpButton.addActionListener(actionEvent -> {
                // Registration logic
                // ...
                signUpFrame.dispose(); // Close the window after registration
            });

            signUpPanel.add(signUpButton);
            signUpFrame.add(signUpPanel);

            
            signUpPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            signUpFrame.setVisible(true);
        }
    }

    public boolean authorizeUser() {
        // Placeholder, actual authorization logic needed
        return true;
    }
}
