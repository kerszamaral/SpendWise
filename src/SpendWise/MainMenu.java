package SpendWise;

import SpendWise.Graphics.Menus.*;
import SpendWise.Graphics.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import SpendWise.Managers.UserManager;
import SpendWise.Utils.GraphicsUtils;
import SpendWise.Utils.Offsets;
import SpendWise.Utils.Enums.Contexts;
import SpendWise.Utils.Enums.PanelOrder;

public class MainMenu extends JFrame {
    private final static String APP_NAME = "SpendWise";
    private final static int SCREEN_WIDTH = 1920 / 2;
    private final static int SCREEN_HEIGHT = 1080 / 2;
    private final static Color BACKGROUND_COLOR = GraphicsUtils.BACKGROUND_COLOR;
    private final static int BUTTON_MAX_WIDTH = 160;
    private final static int BUTTON_SPACING = 25;
    private Contexts currentContext;
    private Screen[] screens;
    private JButton[] buttons;
    private Box centerLayout;
    private JPanel sidePanel;
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

        /*
         * The following line can be removed later on, if we
         * wish to make the JFrames of the application occupy
         * a fixed amount of space in the screen, even when it is resized.
         * 
         * Note that we are now hardcoding the size of the JFrames,
         * what is a bad practice.
         */
        this.setResizable(false);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout());
        ImageIcon icon = new ImageIcon("bin/Images/logo.png");
        this.setIconImage(icon.getImage());

        this.currentContext = Contexts.LOGIN;
        this.centerLayout = Box.createVerticalBox();
        this.sidePanel = new JPanel();
    }

    private void refresh() {
        this.revalidate();
        this.repaint();
    }

    private void createLogin(){
        screens[Contexts.LOGIN.ordinal()] = new LoginMenu(e -> login(e), userManager);
    }

    private void createMenus(User loggedUser) {
        screens[Contexts.ACCOUNT.ordinal()] = new AccountMenu(userManager);
        screens[Contexts.BILL.ordinal()] = new BillCreator(loggedUser.getExpensesManager());
        screens[Contexts.CHARTS.ordinal()] = new ChartsMenu();
        screens[Contexts.GROUPS.ordinal()] = new GroupsMenu();
        screens[Contexts.EXPENSES.ordinal()] = new ExpensesMenu(loggedUser.getExpensesManager());
    }

    private void createButtons() {
        Offsets offsets = new Offsets(20, 35, 0, 0);
        JPanel[] blankPanels = GraphicsUtils.createPanelWithCenter(sidePanel, offsets, BACKGROUND_COLOR);

        JPanel mainSidePanel = blankPanels[PanelOrder.CENTRAL.ordinal()];

        mainSidePanel.setLayout(new BoxLayout(mainSidePanel, BoxLayout.Y_AXIS));

        buttons = new JButton[Contexts.values().length];

        // Creates the buttons for each context except for the logout button
        for (Contexts ctx : Contexts.values()) {
            if (ctx == Contexts.LOGIN)
                continue;

            Dimension buttonSize = new Dimension(BUTTON_MAX_WIDTH, 50);
            buttons[ctx.ordinal()] = new MenuButton(ctx.getContextName(), GraphicsUtils.ACCENT_COLOR,
                    GraphicsUtils.BACKGROUND_COLOR, buttonSize);
            buttons[ctx.ordinal()].addActionListener(e -> updateContext(e));

            mainSidePanel.add(buttons[ctx.ordinal()]);
            mainSidePanel.add(Box.createVerticalStrut(BUTTON_SPACING));
        }

        // Easier manipulation of the logout button
        buttons[Contexts.LOGIN.ordinal()] = new JButton(Contexts.LOGIN.getContextName());
        JButton logoutButton = buttons[Contexts.LOGIN.ordinal()];

        logoutButton.setBackground(GraphicsUtils.ACCENT_COLOR);
        logoutButton.setFont(new Font("Arial", Font.BOLD, 13));
        logoutButton.setSize(50, 50);
        logoutButton.setVisible(true);

        logoutButton.addActionListener(e -> logout(e));

        blankPanels[PanelOrder.SOUTH.ordinal()].add(logoutButton);
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
        screens[this.currentContext.ordinal()].refresh();

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
