package SpendWise;

import SpendWise.Graphics.Menus.*;
import SpendWise.Graphics.*;
import SpendWise.Utils.Contexts;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import SpendWise.Managers.UserManager;

public class MainMenu extends JFrame {
    private final static String APP_NAME = "SpendWise";
    private final static int SCREEN_WIDTH = 1920 / 2;
    private final static int SCREEN_HEIGHT = 1080 / 2;
    private final static Color BACKGROUND_COLOR = Screen.getBackgroundColor();
    private final static int BUTTON_MAX_WIDTH = 285;
    private final static int BUTTON_SPACING = 25;
    private final static String[] BUTTON_NAMES = { "Account", "Bill", "Charts", "Groups", "Expenses", "Logout" };
    private Contexts currentContext;
    private Screen[] screens;
    private JButton[] buttons;
    private Box centerLayout;
    private Box sidePanel;
    private UserManager userManager;

    public MainMenu() {
        this.initialize();
    }

    public boolean run() {
        this.userManager = new UserManager();
        this.createLogin();
        this.logout(null);
        this.getContentPane().add(centerLayout, BorderLayout.CENTER);
        this.createButtons();
        this.getContentPane().add(sidePanel, BorderLayout.EAST);
        this.refresh();

        return true;
    }

    private void initialize() {
        screens = new Screen[Contexts.values().length];
        this.setTitle(APP_NAME);
        this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.getContentPane().setBackground(BACKGROUND_COLOR);
        this.setLocationRelativeTo(null);

        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout());
        ImageIcon icon = new ImageIcon("bin/Images/logo.png");
        this.setIconImage(icon.getImage());

        this.currentContext = Contexts.LOGIN;
        this.centerLayout = Box.createVerticalBox();
        this.sidePanel = Box.createVerticalBox();
    
    }

    private void refresh() {
        this.revalidate();
        this.repaint();
    }

    private void createLogin(){
        screens[Contexts.LOGIN.ordinal()] = new LoginMenu(e -> login(e), userManager);
    }

    private void createMenus(User loggedUser) {
        screens[Contexts.ACCOUNT.ordinal()] = new AccountMenu(loggedUser);
        screens[Contexts.BILL.ordinal()] = new BillCreator();
        screens[Contexts.CHARTS.ordinal()] = new ChartsMenu();
        screens[Contexts.GROUPS.ordinal()] = new GroupsMenu();
        screens[Contexts.EXPENSES.ordinal()] = new ExpensesMenu();
    }

    private void createButtons() {
        sidePanel.add(Box.createVerticalGlue());
        sidePanel.add(Box.createHorizontalStrut(BUTTON_MAX_WIDTH));

        buttons = new JButton[Contexts.values().length];

        // Creates the buttons for each context except for the logout button
        for (int i = 0; i < Contexts.values().length; i++) {
            if (i == Contexts.LOGIN.ordinal())
                continue;

            buttons[i] = new MenuButton(BUTTON_NAMES[i], Color.WHITE);

            buttons[i].addActionListener(e -> updateContext(e));

            sidePanel.add(buttons[i]);
            sidePanel.add(Box.createVerticalStrut(BUTTON_SPACING));
        }

        // Easier manipulation of the logout button
        buttons[Contexts.LOGIN.ordinal()] = new JButton(BUTTON_NAMES[Contexts.LOGIN.ordinal()]);
        JButton logoutButton = buttons[Contexts.LOGIN.ordinal()];

        logoutButton.setBackground(Color.WHITE);
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

        this.refresh();
    }

    private void login(ActionEvent e) {
        LoginMenu login = (LoginMenu) this.screens[Contexts.LOGIN.ordinal()];

        if (login.authorizeUser()) {
            createMenus(userManager.getLoggedUser());         
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
