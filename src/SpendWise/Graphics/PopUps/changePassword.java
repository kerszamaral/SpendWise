package SpendWise.Graphics.PopUps;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.*;

import SpendWise.User;
import SpendWise.Graphics.PopUp;
import SpendWise.Graphics.Screen;
import SpendWise.Utils.PanelOrder;

public class changePassword extends PopUp {
    private User loggedUser;
    private JPanel pnlUserData;
    private String newPassword;

    JTextField fieldRepeatPassword;
    JTextField fieldOldPassword;

    public changePassword(Screen parent, String title, User loggedUser, String newPassword) {
        super(parent, title);
        this.loggedUser = loggedUser;
        this.pnlUserData = parent.getBlankPanel(PanelOrder.CENTRAL);
        this.newPassword = newPassword;
    }

    @Override
    public void run() {
        JPanel editPanel = new JPanel(new GridLayout(6, 2));
        editPanel.setBackground(BACKGROUND_COLOR);

        JLabel txtRepeatPassword = new JLabel("Repeat New password: ");
        txtRepeatPassword.setForeground(Color.WHITE);
        txtRepeatPassword.setFont(STD_FONT);
        editPanel.add(txtRepeatPassword);
        fieldRepeatPassword = new JTextField("", 15);
        editPanel.add(fieldRepeatPassword);

        JLabel txtOldPassword = new JLabel("Old password: ");
        txtOldPassword.setForeground(Color.WHITE);
        txtOldPassword.setFont(STD_FONT);
        editPanel.add(txtOldPassword);
        fieldOldPassword = new JTextField("", 15);
        editPanel.add(fieldOldPassword);

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

        JButton btnApplyChanges = new JButton("Change Password");
        btnApplyChanges.setBackground(Color.BLACK);
        btnApplyChanges.setForeground(BACKGROUND_COLOR);
        btnApplyChanges.addActionListener(e -> updatePassword(e));
        pnlSouth.add(btnApplyChanges, BorderLayout.CENTER);

        this.add(pnlSouth, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    private void updatePassword(ActionEvent e) {
        String oldPassword = fieldOldPassword.getText();
        String repeatNewPassword = fieldRepeatPassword.getText();

        if (!newPassword.equals(repeatNewPassword)) {
            JOptionPane.showMessageDialog(this, "New Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (loggedUser.changePassword(oldPassword, newPassword)) {
            this.dispose();
            this.updateAccountFields();
            JOptionPane.showMessageDialog(this, "Password changed successfully!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Old Password Incorrect!", "Error", JOptionPane.ERROR_MESSAGE);
        }
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
