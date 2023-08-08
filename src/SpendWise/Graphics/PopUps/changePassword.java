package SpendWise.Graphics.PopUps;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.*;

import SpendWise.User;
import SpendWise.Graphics.PopUp;
import SpendWise.Graphics.Screen;
import SpendWise.Utils.GraphicsUtils;

public class changePassword extends PopUp {
    private User loggedUser;
    private String newPassword;
    private Runnable updateFields;

    JTextField fieldRepeatPassword;
    JTextField fieldOldPassword;

    public changePassword(Screen parent, String title, User loggedUser, String newPassword, Runnable callback) {
        super(parent, title);
        this.loggedUser = loggedUser;
        this.newPassword = newPassword;
        this.updateFields = callback;
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
        GraphicsUtils.createOffsets((JPanel) this.getContentPane(), 50, 0, 100, 100);
        this.add(editPanel, BorderLayout.CENTER);

        JPanel pnlSouth = new JPanel();
        GraphicsUtils.initializeBlankPanel(pnlSouth, 100, 50);
        pnlSouth.setLayout(new BorderLayout());

        JPanel pnlSouthEast = new JPanel();
        GraphicsUtils.initializeBlankPanel(pnlSouthEast, 200, 50);
        JPanel pnlSouthWest = new JPanel();
        GraphicsUtils.initializeBlankPanel(pnlSouthWest, 200, 50);
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
            this.updateFields.run();
            JOptionPane.showMessageDialog(this, "Password changed successfully!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Old Password Incorrect!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
