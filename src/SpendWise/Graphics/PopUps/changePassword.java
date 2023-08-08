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
import SpendWise.Utils.Offsets;
import SpendWise.Utils.Enums.PanelOrder;

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
        Offsets offsets = new Offsets(50, 0, 100, 100);
        blankPanels = GraphicsUtils.initializeOffsets((JPanel) this.getContentPane(), offsets);

        // Creating the sign up panel and it's fields
        JPanel editPanel = new JPanel(new GridLayout(6, 1));
        editPanel.setBackground(BACKGROUND_COLOR);

        fieldRepeatPassword = (JPasswordField) GraphicsUtils.addTextFieldCenter(editPanel, "Repeat New Password:", "",
                15, true, true);

        fieldOldPassword = (JPasswordField) GraphicsUtils.addTextFieldCenter(editPanel, "Old Password:", "", 15, true,
                true);

        this.add(editPanel, BorderLayout.CENTER);

        // Creating the south panel and button
        JPanel pnlSouth = new JPanel();
        Offsets southOffsets = new Offsets(5, 20, 200, 200);
        GraphicsUtils.initializeOffsets(pnlSouth, southOffsets);

        JButton btnApplyChanges = GraphicsUtils.createButton("Change Password", Color.BLACK, BACKGROUND_COLOR, null,
                e -> updatePassword(e));
        pnlSouth.add(btnApplyChanges, BorderLayout.CENTER);

        this.add(pnlSouth, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    private void updatePassword(ActionEvent e) {
        GraphicsUtils.clearErrorMessage(getBlankPanel(PanelOrder.NORTH));

        String oldPassword = fieldOldPassword.getText();
        GraphicsUtils.setErrorBorder(fieldOldPassword, false);

        String repeatNewPassword = fieldRepeatPassword.getText();
        GraphicsUtils.setErrorBorder(fieldRepeatPassword, false);

        if (!newPassword.equals(repeatNewPassword)) {
            GraphicsUtils.showErrorMessage(getBlankPanel(PanelOrder.NORTH), "New Passwords do not match!");
            GraphicsUtils.setErrorBorder(fieldOldPassword, true);
            return;
        }

        if (loggedUser.changePassword(oldPassword, newPassword)) {
            this.dispose();
            this.updateFields.run();
        } else {
            GraphicsUtils.showErrorMessage(getBlankPanel(PanelOrder.NORTH), "Old Password Incorrect!");
            GraphicsUtils.setErrorBorder(fieldOldPassword, true);
        }
    }
}
