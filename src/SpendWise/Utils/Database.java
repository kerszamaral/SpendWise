package SpendWise.Utils;

import java.awt.event.WindowAdapter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import SpendWise.Logic.Managers.UserManager;

public class Database extends WindowAdapter {
    private static String filename = "spendwise.db";

    public static UserManager loadUserManager() {
        try {
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(file);

            UserManager userManager = (UserManager) in.readObject();

            in.close();
            file.close();

            return userManager;
        } catch (Exception e) {
            System.out.println("Database file not found, loading default");
            return new UserManager();
        }
    }

    public static boolean saveUserManager(UserManager userManager) {
        try {
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(userManager);

            out.close();
            file.close();

            return true;
        } catch (Exception e) {
            System.out.println("Could not write Database file");
            return false;
        }
    }
}