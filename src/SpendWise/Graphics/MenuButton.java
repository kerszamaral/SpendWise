package SpendWise.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import SpendWise.Utils.Enums.Contexts;

import java.awt.Color;
import java.awt.ComponentOrientation;

public class MenuButton extends JButton {
    private String name;
    private ImageIcon icon;
    private Color Background;

    public MenuButton(String name, Color background) {
        this.name = name;
        this.Background = background;
        String fileName = Contexts.getContext(name).toString().toLowerCase();
        icon = new ImageIcon("src\\Images\\" + fileName + "Icon.png");
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
        this.setOpaque(false);
        this.setVisible(true);
        this.setBackground(Background);

        // Aligment settings
        this.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        this.setHorizontalAlignment(JButton.RIGHT);
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
        this.setOpaque(true);
    }

    private void closeMenu() {
        this.setText("");
        this.setOpaque(false);
    }
}
