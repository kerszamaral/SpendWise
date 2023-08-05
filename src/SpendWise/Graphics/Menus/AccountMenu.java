package SpendWise.Graphics.Menus;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.*;

import SpendWise.Graphics.Screen;

import SpendWise.User;

public class AccountMenu extends Screen {
    private JPanel pnlUserData;
    private User loggedUser;
    
    public AccountMenu(User loggedUser) {
        this.loggedUser = loggedUser;
        pnlUserData = new JPanel();
        this.initialize();
    }
    
    @Override
    protected void initialize() {
        super.initialize();

        this.setLayout(new BorderLayout());
        this.setBackground(BACKGROUND_COLOR);
        pnlUserData.setLayout(new BoxLayout(pnlUserData, BoxLayout.Y_AXIS));
        pnlUserData.setBackground(BACKGROUND_COLOR);

        addTextField("Name: ", loggedUser.getName(), 100);
        addTextField("Username: ", loggedUser.getUsername(), 100);
        addTextField("E-mail: ", loggedUser.getEmail(), 100);
        addPasswordField("Password: ", loggedUser.getPassword(), 100);
        this.add(pnlUserData, BorderLayout.CENTER);
    
        addEditButton();
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

    private void addEditButton() {
        JButton btnEdit = new JButton("Edit");
        btnEdit.setBackground(Color.BLACK);
        btnEdit.setForeground(BACKGROUND_COLOR);
        btnEdit.addActionListener(e -> this.edit(e));

        JPanel pnlEdit = new JPanel();
        pnlEdit.setLayout(new BorderLayout());
        pnlEdit.add(btnEdit, BorderLayout.EAST);
        pnlEdit.setBackground(BACKGROUND_COLOR);

        this.add(pnlEdit, BorderLayout.SOUTH);
    }

    private void edit(ActionEvent e) {
        // TODO implement edit button
        // Criar um pop-up que permite editar os campos
    }

}
