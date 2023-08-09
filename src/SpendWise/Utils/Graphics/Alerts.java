package SpendWise.Utils.Graphics;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public abstract class Alerts {

    public static void setBorder(JTextField field, Color color) {
        Border border = BorderFactory.createLineBorder(color);
        field.setBorder(border);
    }

    public static void setErrorBorder(JTextField field, Boolean isError) {
        if (isError) {
            setBorder(field, Color.RED);
        } else {
            setBorder(field, Color.BLACK);
        }
    }

    public static void showMessage(JPanel panel, String msg, Color color) {
        panel.removeAll();
        JLabel errorLabel = new JLabel(msg);
        errorLabel.setForeground(color);
        panel.add(errorLabel);
        panel.revalidate();
        panel.repaint();
    }

    public static void showErrorMessage(JPanel panel, String error) {
        showMessage(panel, error, Color.RED);
    }

    public static void clearErrorMessage(JPanel panel) {
        showMessage(panel, "", Color.BLACK);
    }

}
