package SpendWise.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;

public class MenuButton extends JButton {
    private String name;
    private ImageIcon icon;
    private Color Background;

    public MenuButton(String name, Color background) {
        this.name = name.toLowerCase();
        this.Background = background;
        icon = new ImageIcon("src\\Images\\" + this.name + "Icon.png");
        this.initialize();
    }

    private void initialize() {
        // Visual settings
        this.setIcon(icon);
        this.setText("");
        this.setSize(50, 50);
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setContentAreaFilled(true);
        this.setVisible(true);
        this.setBackground(Background);

        // Aligment settings
        this.setAlignmentX(JButton.LEFT_ALIGNMENT);
        this.setHorizontalAlignment(JButton.LEFT);
        this.setHorizontalTextPosition(JButton.LEFT);
        this.setVerticalTextPosition(JButton.CENTER);
    }

    @Override
    public void setSelected(boolean b) {
        super.setSelected(b);
        if (b) {
            this.openMenu();
        } else {
            this.closeMenu();
        }
    }

    private void openMenu() {
        this.setText(this.name);
    }

    private void closeMenu() {
        this.setText("");
    }
}
