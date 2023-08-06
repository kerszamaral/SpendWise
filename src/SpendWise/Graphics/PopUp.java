package SpendWise.Graphics;

import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
//import javax.swing.JPanel;


public class PopUp extends JFrame {

    private Component originalWindow;
    private String title;
    private final int WIDTH = 600;
    private final int HEIGHT = 400;
    private final static Color BACKGROUND_COLOR = Screen.getBackgroundColor();

    public PopUp(Component originalWindow, String title) {
        this.originalWindow = originalWindow;
        this.title = title;
        this.initialize();
    }

    protected void initialize() {
        this.setTitle(title);
        this.setSize(WIDTH, HEIGHT);
        this.getContentPane().setBackground(BACKGROUND_COLOR);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(originalWindow);
        ImageIcon icon = new ImageIcon("bin/Images/logo.png");
        this.setIconImage(icon.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
}
