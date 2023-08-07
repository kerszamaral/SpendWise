package SpendWise.Graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class PopUp extends JFrame {

    private Component originalWindow;
    private String title;
    private final int WIDTH = 600;
    private final int HEIGHT = 400;
    private final static Color BACKGROUND_COLOR = Screen.getBackgroundColor();
    private JPanel pnlNorth;

    /**
     * @return the pnlNorth
     */
    public JPanel getPnlNorth() {
        return pnlNorth;
    }

    public PopUp(Component originalWindow, String title) {
        this.originalWindow = originalWindow;
        this.title = title;
        this.initialize();
    }

    protected void initialize() {
        this.setLayout(new BorderLayout());
        this.setTitle(title);
        this.setSize(WIDTH, HEIGHT);
        this.getContentPane().setBackground(BACKGROUND_COLOR);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(originalWindow);
        ImageIcon icon = new ImageIcon("bin/Images/logo.png");
        this.setIconImage(icon.getImage());
        this.setResizable(false);
    }

    public void setCentralPanel(JPanel pnlCentral) {
        pnlNorth = new JPanel();
        Screen.initializeBlankPanel(pnlNorth, 100, 50);
        pnlNorth.setLayout(new FlowLayout(FlowLayout.CENTER));
        JPanel pnlBlankEast= new JPanel();
        Screen.initializeBlankPanel(pnlBlankEast, 100, 50);
        JPanel pnlBlankWest= new JPanel();
        Screen.initializeBlankPanel(pnlBlankWest, 100, 50);

        JPanel pnlMain = new JPanel();
        pnlMain.setLayout(new BorderLayout());
        pnlMain.add(pnlCentral, BorderLayout.CENTER);
        pnlMain.add(pnlNorth, BorderLayout.NORTH);
        pnlMain.add(pnlBlankEast, BorderLayout.EAST);
        pnlMain.add(pnlBlankWest, BorderLayout.WEST);

        this.add(pnlMain);
    }
    
}
