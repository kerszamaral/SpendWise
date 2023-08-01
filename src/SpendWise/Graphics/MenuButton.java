package SpendWise.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class MenuButton extends JButton {
    private String name;
    private ImageIcon iconClosed;
    private ImageIcon iconOpen;

    public MenuButton(String name) {

        this.name = name.toLowerCase();
        iconOpen = new ImageIcon("src\\Images\\" + this.name + "IconHover.png");
        iconClosed = new ImageIcon("src\\Images\\" + this.name + "Icon.png");
        this.initialize();
    }

    private void initialize() {
        this.setIcon(iconClosed);
        this.setText("");
        this.setSize(50, 50);
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setContentAreaFilled(false);
        this.setOpaque(false);
        this.setVisible(false);
        this.setHorizontalAlignment(JButton.LEFT);
        this.setHorizontalTextPosition(JButton.LEFT);
        this.setVerticalTextPosition(JButton.CENTER);

    }

    public void openMenu() {
        this.setIcon(this.iconOpen);
    }

    public void closeMenu() {
        this.setIcon(this.iconClosed);
    }

}
