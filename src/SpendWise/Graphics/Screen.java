package SpendWise.Graphics;

import javax.swing.JPanel;
import java.awt.Color;

public abstract class Screen extends JPanel {
    protected final static Color BackgroundColor = new Color(0, 177, 216);

    protected void initialize() {
        this.setBackground(BackgroundColor);
    }

    public void refresh() {
        this.revalidate();
        this.repaint();
        return;
    }
}
