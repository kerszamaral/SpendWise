package SpendWise.Utils.Graphics;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JComponent;

public abstract class Misc {

    public static void defineSize(JComponent component, Dimension size) {
        component.setMinimumSize(size);
        component.setPreferredSize(size);
        component.setMaximumSize(size);
    }

    public static void refresh(JComponent component) {
        component.revalidate();
        component.repaint();
    }

    public static String getColorHex(Color color) {
        return "#" + Integer.toHexString(color.getRGB()).substring(2);
    }

}
