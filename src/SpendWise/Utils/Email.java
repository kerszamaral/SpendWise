package SpendWise.Utils;

import javax.swing.JTextField;

import SpendWise.Utils.Graphics.Alerts;

public abstract class Email {
    public static boolean isEmailValid(JTextField emailField) {
        final String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        Alerts.setErrorBorder(emailField, false);
        String email = emailField.getText();
        if (email.matches(emailRegex)) {
            return true;
        } else {
            Alerts.setErrorBorder(emailField, true);
            return false;
        }
    }
}
