package SpendWise;

import SpendWise.Graphics.Menus.*;
import SpendWise.Graphics.*;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;

public class MainMenu extends JFrame {
    private int curContext;
    private boolean loggedIn;
    private final static int ScreenWidth = 1920 / 2;
    private final static int ScreenHeight = 1080 / 2;
    private final static String AppName = "SpendWise";
    private final static Color BackgroundColor = new Color(0, 177, 216);

    private final static String[] buttonNames = { "Account", "Bill", "Charts", "Groups", "Expenses" };

    private PanelScreen[] screens;
    private MenuButton[] buttons;

    public MainMenu() {
        this.initialize();
    }

    private void initialize() {
        this.setSize(ScreenWidth, ScreenHeight);
        this.setVisible(true);
        this.setTitle(AppName);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout());
        this.curContext = 0;
        this.loggedIn = false;
        this.getContentPane().setBackground(BackgroundColor);
    }

    public void setCurContext(int curContext) {
        this.curContext = curContext;
    }

    public int getCurContext() {
        return curContext;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public boolean getLoggedIn() {
        return loggedIn;
    }

    private void closeButtons() {
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i] != null && buttons[i].isVisible())
                buttons[i].setVisible(false);
        }
        return;
    }

    private void openButtons() {
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i] != null && !buttons[i].isVisible())
                buttons[i].setVisible(true);
        }
        return;
    }

    private void refresh() {
        this.revalidate();
        this.repaint();
        return;
    }

    private void clearScreen() {
        for (int i = 0; i < screens.length; i++) {
            screens[i].closeScreen();
        }
        return;
    }

    public boolean run() {
        Box centerLayout = Box.createVerticalBox();
        centerLayout.setBackground(BackgroundColor);
        centerLayout.add(Box.createVerticalGlue());
        // ? Login Menu
        LoginMenu login = new LoginMenu();
        centerLayout.add(login, BorderLayout.CENTER);
        login.openScreen();

        // ! End of Login Process

        // ? Account Menu
        screens = new PanelScreen[buttonNames.length];
        screens[0] = new AccountMenu();
        screens[1] = new BillCreator();
        screens[2] = new ChartsMenu();
        screens[3] = new GroupsMenu();
        screens[4] = new ExpensesMenu();

        for (int i = 0; i < screens.length; i++) {
            screens[i].closeScreen();
        }
        centerLayout.add(Box.createVerticalGlue());
        this.getContentPane().add(centerLayout, BorderLayout.CENTER);

        // ? Side Buttons
        Box sideButtons = Box.createVerticalBox();
        buttons = new MenuButton[buttonNames.length];

        for (int i = 0; i < buttonNames.length; i++) {
            sideButtons.add(Box.createVerticalGlue());
            buttons[i] = new MenuButton(buttonNames[i]);
            sideButtons.add(buttons[i]);

            buttons[i].addActionListener(e -> {
                this.clearScreen();
                this.buttons[this.getCurContext()].closeMenu();
                for (int j = 0; j < buttonNames.length; j++) {
                    if (e.getSource() == buttons[j]) {
                        this.setCurContext(j);
                        break;
                    }
                }
                centerLayout.removeAll();
                centerLayout.add(Box.createVerticalGlue());
                centerLayout.add(screens[this.getCurContext()], BorderLayout.CENTER);
                centerLayout.add(Box.createVerticalGlue());
                this.buttons[this.getCurContext()].openMenu();
                screens[this.getCurContext()].openScreen();
                this.refresh();
            });
        }
        sideButtons.add(Box.createVerticalStrut(50));

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(new Color(235, 235, 235));
        logoutButton.setFont(new Font("Arial", Font.BOLD, 13));
        logoutButton.setSize(50, 50);
        logoutButton.setVisible(false);

        sideButtons.add(logoutButton);

        this.getContentPane().add(sideButtons, BorderLayout.EAST);
        this.refresh();

        login.getBtnLogin().addActionListener(e -> {
            this.setLoggedIn(true);
            login.closeScreen();
            centerLayout.removeAll();
            centerLayout.add(Box.createVerticalGlue());
            centerLayout.add(Box.createVerticalGlue());
            this.openButtons();
            logoutButton.setVisible(true);
            this.refresh();
        });

        logoutButton.addActionListener(e -> {
            this.setLoggedIn(false);
            login.openScreen();
            this.clearScreen();
            centerLayout.removeAll();
            centerLayout.add(Box.createVerticalGlue());
            centerLayout.add(login, BorderLayout.CENTER);
            centerLayout.add(Box.createVerticalGlue());
            this.closeButtons();
            logoutButton.setVisible(false);
            this.refresh();
        });

        return true;
    }
}
