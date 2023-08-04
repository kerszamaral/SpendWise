package SpendWise.Graphics.Menus;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.*;

import SpendWise.Graphics.Screen;

import SpendWise.User;

public class AccountMenu extends Screen {
    // TODO implement AccountMenu integration with User class
    private User loggedUser;
    
    public AccountMenu(User loggedUser) {
        this.loggedUser = loggedUser;
        this.initialize();
    }
    
    @Override
    protected void initialize() {
        super.initialize();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(BACKGROUND_COLOR);

        addTextField("Name: ", loggedUser.getName(), 100);
        addTextField("Username: ", loggedUser.getUsername(), 100);
        addTextField("E-mail: ", loggedUser.getEmail(), 100);
        addPasswordField("Password: ", loggedUser.getPassword(), 100);
        addEditButton();
    }

    private void addTextField(String label, String initialValue, int width) {

        JLabel lbl = new JLabel(label);
        this.add(lbl);

        JTextField textField = new JTextField(initialValue, 20);
        textField.setEditable(false);
        textField.setPreferredSize(new Dimension(width, textField.getPreferredSize().height));
        this.add(textField);

    }

    private void addPasswordField(String label, String initialValue, int width) {
        JLabel lbl = new JLabel(label);
        this.add(lbl);
        JPasswordField passwordField = new JPasswordField(initialValue, 20);
        passwordField.setEditable(false);
        passwordField.setPreferredSize(new Dimension(width, passwordField.getPreferredSize().height));
        this.add(passwordField);

    }

    private void addEditButton() {
        JButton btnEdit = new JButton("Edit");
        btnEdit.setBackground(Color.BLACK);
        btnEdit.setForeground(BACKGROUND_COLOR);
        btnEdit.addActionListener(e -> this.edit(e));
        this.add(btnEdit);
    }

    private void edit(ActionEvent e) {
        // TODO implement edit button
        // Criar um pop-up que permite editar os campos
    }

}
