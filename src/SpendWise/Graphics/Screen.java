package SpendWise.Graphics;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;

public abstract class Screen extends JPanel {
    protected final static Color BACKGROUND_COLOR = new Color(98, 210, 162);

    protected void initialize() {
        this.setBackground(BACKGROUND_COLOR);
    }

    public static Color getBackgroundColor(){
        return BACKGROUND_COLOR;
    }

    protected static void initializeBlankPanel(JPanel blankPanel, int width, int height) {
        blankPanel.setBackground(BACKGROUND_COLOR);
        blankPanel.setPreferredSize(new Dimension(width, height));
    }

    public void refresh() {
        this.revalidate();
        this.repaint();
    }
}
