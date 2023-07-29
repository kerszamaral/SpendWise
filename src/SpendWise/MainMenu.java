package SpendWise;

import SpendWise.Graphics.LoginMenu;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import SpendWise.Graphics.Screen;

public class MainMenu extends JFrame {
    private int curContext;
    private boolean loggedIn;
    private final static int ScreenWidth = 1920 / 2;
    private final static int ScreenHeight = 1080 / 2;
    private final static String AppName = "SpendWise";

    public MainMenu() {
        this.initialize();
    }

    private void initialize() {
        this.setSize(ScreenWidth, ScreenHeight);
        this.setVisible(true);
        this.setTitle(AppName);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout());
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

    private void changeContext() {
        return;
    }

    private void makeBackground() {
        return;
    }

    public boolean run() {
        LoginMenu login = new LoginMenu();

        this.getContentPane().add(login.getPnlButtons(), BorderLayout.CENTER);

        return true;
    }
}
