package SpendWise.Graphics.PopUps;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.*;

import SpendWise.User;
import SpendWise.Graphics.PopUp;
import SpendWise.Graphics.Screen;
import SpendWise.Managers.UserManager;

public class editAccount extends PopUp {
    private User loggedUser;
    private JPanel pnlUserData;
    private UserManager userManager;

    public editAccount(Screen parent, String title, User loggedUser, JPanel pnlUserData, UserManager userManager) {
        super(parent, title);
        this.loggedUser = loggedUser;
        this.pnlUserData = pnlUserData;
        this.userManager = userManager;
    }

    @Override
    public void run() {
        JPanel editPanel = new JPanel(new GridLayout(6, 2));
        editPanel.setBackground(BACKGROUND_COLOR);

        JLabel txtName = new JLabel("New name: ");
        txtName.setForeground(Color.WHITE);
        txtName.setFont(STD_FONT);
        editPanel.add(txtName);
        JTextField fieldName = new JTextField(loggedUser.getName(), 15);
        editPanel.add(fieldName);

        JLabel txtUsername = new JLabel("New username: ");
        txtUsername.setForeground(Color.WHITE);
        txtUsername.setFont(STD_FONT);
        editPanel.add(txtUsername);
        JTextField fieldUsername = new JTextField(loggedUser.getUsername(), 15);
        editPanel.add(fieldUsername);

        JLabel txtEmail = new JLabel("New e-mail: ");
        txtEmail.setForeground(Color.WHITE);
        txtEmail.setFont(STD_FONT);
        editPanel.add(txtEmail);
        JTextField fieldEmail = new JTextField(loggedUser.getEmail(), 15);
        editPanel.add(fieldEmail);

        JLabel txtOldPassword = new JLabel("Old password: ");
        txtOldPassword.setForeground(Color.WHITE);
        txtOldPassword.setFont(STD_FONT);
        editPanel.add(txtOldPassword);
        JTextField fieldOldPassword = new JTextField("", 15);
        editPanel.add(fieldOldPassword);

        JLabel txtPassword = new JLabel("New password: ");
        txtPassword.setForeground(Color.WHITE);
        txtPassword.setFont(STD_FONT);
        editPanel.add(txtPassword);
        JTextField fieldPassword = new JTextField("", 15);
        editPanel.add(fieldPassword);

        this.setLayout(new BorderLayout());
        Screen.createOffsets((JPanel) this.getContentPane(), 50, 0, 100, 100);
        this.add(editPanel, BorderLayout.CENTER);

        JPanel pnlSouth = new JPanel();
        Screen.initializeBlankPanel(pnlSouth, 100, 50);
        pnlSouth.setLayout(new BorderLayout());

        JPanel pnlSouthEast = new JPanel();
        Screen.initializeBlankPanel(pnlSouthEast, 200, 50);
        JPanel pnlSouthWest = new JPanel();
        Screen.initializeBlankPanel(pnlSouthWest, 200, 50);
        pnlSouth.add(pnlSouthEast, BorderLayout.EAST);
        pnlSouth.add(pnlSouthWest, BorderLayout.WEST);

        JButton btnApplyChanges = new JButton("Apply Changes");
        btnApplyChanges.setBackground(Color.BLACK);
        btnApplyChanges.setForeground(BACKGROUND_COLOR);
        btnApplyChanges.addActionListener(actionEvent -> {
            userManager.removeUser(loggedUser);
            loggedUser.setName(fieldName.getText());
            loggedUser.setUsername(fieldUsername.getText());
            loggedUser.setEmail(fieldEmail.getText());
            String oldPassword = fieldOldPassword.getText();
            String newPassword = fieldPassword.getText();
            loggedUser.changePassword(oldPassword, newPassword);
            userManager.addUser(loggedUser);
            this.updateAccountFields();
            this.dispose();
        });
        pnlSouth.add(btnApplyChanges, BorderLayout.CENTER);

        this.add(pnlSouth, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    private void addTextField(String label, String userValue, int width) {
        JLabel lbl = new JLabel(label);
        pnlUserData.add(lbl);

        JTextField textField = new JTextField(userValue, 20);
        textField.setEditable(false);
        textField.setPreferredSize(new Dimension(width, textField.getPreferredSize().height));
        pnlUserData.add(textField);
    }

    private void addPasswordField(String label, String userValue, int width) {
        JLabel lbl = new JLabel(label);
        pnlUserData.add(lbl);
        JPasswordField passwordField = new JPasswordField(userValue, 20);
        passwordField.setEditable(false);
        passwordField.setPreferredSize(new Dimension(width, passwordField.getPreferredSize().height));
        pnlUserData.add(passwordField);
    }

    private void updateAccountFields() {
        pnlUserData.removeAll();
        addTextField("Name: ", loggedUser.getName(), 100);
        addTextField("Username: ", loggedUser.getUsername(), 100);
        addTextField("E-mail: ", loggedUser.getEmail(), 100);
        addPasswordField("Password: ", "*".repeat(loggedUser.getPasswordSize()), 100);
        pnlUserData.revalidate();
        pnlUserData.repaint();
    }
}
