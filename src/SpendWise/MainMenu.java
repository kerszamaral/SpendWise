package SpendWise;

import SpendWise.Graphics.Menus.*;
import SpendWise.Graphics.*;
import SpendWise.Utils.Contexts;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;

public class MainMenu extends JFrame {
    private final static String AppName = "SpendWise";
    private final static int ScreenWidth = 1920 / 2;
    private final static int ScreenHeight = 1080 / 2;
    private final static Color BackgroundColor = new Color(0, 177, 216);
    private final static int buttonMaxWidth = 285;
    private final static int buttonSpacing = 25;

    private final static String[] buttonNames = { "Account", "Bill", "Charts", "Groups", "Expenses", "Logout" };

    private Contexts currentContext;
    private Screen[] screens;
    private JButton[] buttons;
    private Box centerLayout;
    private Box sidePanel;

    public MainMenu() {
        this.initialize();
    }

    public boolean run() {
        this.createMenus();
        this.logout(null);
        this.getContentPane().add(centerLayout, BorderLayout.CENTER);
        this.createButtons();
        this.getContentPane().add(sidePanel, BorderLayout.EAST);
        this.refresh();

        return true;
    }

    private void initialize() {
        this.setTitle(AppName);
        this.setSize(ScreenWidth, ScreenHeight);
        this.getContentPane().setBackground(BackgroundColor);

        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout());

        this.currentContext = Contexts.LOGIN;
        this.centerLayout = Box.createVerticalBox();
        this.sidePanel = Box.createVerticalBox();
    }

    private void refresh() {
        this.revalidate();
        this.repaint();
    }

    private void createMenus() {
        screens = new Screen[Contexts.values().length];
        screens[Contexts.ACCOUNT.ordinal()] = new AccountMenu();
        screens[Contexts.BILL.ordinal()] = new BillCreator();
        screens[Contexts.CHARTS.ordinal()] = new ChartsMenu();
        screens[Contexts.GROUPS.ordinal()] = new GroupsMenu();
        screens[Contexts.EXPENSES.ordinal()] = new ExpensesMenu();
        screens[Contexts.LOGIN.ordinal()] = new LoginMenu(e -> login(e));
    }

    private void createButtons() {
        sidePanel.add(Box.createVerticalGlue());
        sidePanel.add(Box.createHorizontalStrut(buttonMaxWidth));

        buttons = new JButton[Contexts.values().length];

        // Creates the buttons for each context except for the logout button
        for (int i = 0; i < Contexts.values().length; i++) {
            if (i == Contexts.LOGIN.ordinal())
                continue;

            buttons[i] = new MenuButton(buttonNames[i], new Color(255, 255, 255));

            buttons[i].addActionListener(e -> updateContext(e));

            sidePanel.add(buttons[i]);
            sidePanel.add(Box.createVerticalStrut(buttonSpacing));
        }

        // Easier manipulation of the logout button
        buttons[Contexts.LOGIN.ordinal()] = new JButton(buttonNames[Contexts.LOGIN.ordinal()]);
        JButton logoutButton = buttons[Contexts.LOGIN.ordinal()];

        logoutButton.setBackground(new Color(235, 235, 235));
        logoutButton.setFont(new Font("Arial", Font.BOLD, 13));
        logoutButton.setSize(50, 50);
        logoutButton.setVisible(true);

        logoutButton.addActionListener(e -> logout(e));

        sidePanel.add(buttons[Contexts.LOGIN.ordinal()]);

        sidePanel.add(Box.createVerticalGlue());
    }

    private void updateContext(ActionEvent e) {
        // Clears the screen for the new context
        this.centerLayout.removeAll();
        centerLayout.add(Box.createVerticalGlue());

        // Sets the new context
        if (e != null) {
            for (int i = 0; i < buttons.length; i++) {
                buttons[i].setSelected(false);
                if (e.getSource() == buttons[i]) {
                    this.currentContext = Contexts.values()[i];
                    buttons[i].setSelected(true);
                }
            }
        }

        // Adds the new context to the screen
        this.centerLayout.add(screens[this.currentContext.ordinal()]);

        switch (this.currentContext) {
            case LOGIN:
                this.sidePanel.setVisible(false);
                break;
            default:
                this.sidePanel.setVisible(true);
                break;
        }

        centerLayout.add(Box.createVerticalGlue());
        this.refresh();
    }

    private void login(ActionEvent e) {
        LoginMenu login = (LoginMenu) this.screens[Contexts.LOGIN.ordinal()];

        if (login.authorizeUser()) {
            this.currentContext = Contexts.ACCOUNT;
            this.buttons[Contexts.ACCOUNT.ordinal()].setSelected(true);
        } else {
            this.currentContext = Contexts.LOGIN;
        }

        this.updateContext(null);
    }

    private void logout(ActionEvent e) {
        this.currentContext = Contexts.LOGIN;
        this.updateContext(null);
    }
}
