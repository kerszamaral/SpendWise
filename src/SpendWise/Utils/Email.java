package SpendWise.Utils;

import javax.swing.JTextField;

public abstract class Email {
    public static boolean isEmailValid(JTextField emailField) {
        final String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        GraphicsUtils.setErrorBorder(emailField, false);
        String email = emailField.getText();
        if (email.matches(emailRegex)) {
            return true;
        } else {
            GraphicsUtils.setErrorBorder(emailField, true);
            return false;
        }
    }
}
