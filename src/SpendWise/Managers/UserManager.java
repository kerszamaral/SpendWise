package SpendWise.Managers;

import java.util.Hashtable;
import SpendWise.User;

public class UserManager {
    private Hashtable<String, User> users;

    public UserManager() {
        this.users = new Hashtable<String, User>();
    }

    public boolean createUser(String username, String name, String email, String password, double income,
            double monthlyLimit) {
        if (this.users.containsKey(username)) {
            return false;
        }
        User user = new User(username, name, email, password, income, monthlyLimit);
        this.users.put(username, user);
        return true;
    }

    public boolean validateLogin(String username, String password) {
        if (!this.users.containsKey(username)) {
            return false;
        }
        User user = this.users.get(username);
        return user.checkPassword(password);
    }

}
