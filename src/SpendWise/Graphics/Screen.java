package SpendWise.Graphics;

import javax.swing.JPanel;
import java.awt.Color;

public abstract class Screen extends JPanel {
    protected final static Color BACKGROUND_COLOR = new Color(98, 210, 162);

    protected void initialize() {
        this.setBackground(BACKGROUND_COLOR);
    }

    public static Color getBackgroundColor(){
        return BACKGROUND_COLOR;
    }

    public void refresh() {
        this.revalidate();
        this.repaint();
    }
}
