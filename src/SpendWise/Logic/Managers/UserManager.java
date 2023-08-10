package SpendWise.Logic.Managers;

import java.util.Hashtable;

import SpendWise.Logic.User;

public class UserManager {
    private Hashtable<String, User> users;
    private User loggedUser;

    public UserManager() {
        this.users = new Hashtable<String, User>();
        this.loggedUser = new User();

        // TODO remove this
        // Just to skip all the sign in process
        createUser(new User("admin", "admin", "admin@admin.com", "admin", 0, 0));
        createUser(new User("a", "a", "admin@admin.com", "a", 0, 0));
        getUser("a").getGroupManager().createGroup("test");
    }

    public boolean changeUsername(String username, String newUsername) {
        if (!this.users.containsKey(username)) {
            return false;
        }
        User user = this.users.get(username);
        user.setUsername(newUsername);
        this.users.remove(username);
        this.users.put(newUsername, user);
        return true;
    }

    public void removeUser(User user) {
        this.users.remove(user.getUsername());
    }

    public void removeUser(String username) {
        this.users.remove(username);
    }

    public void addUser(User user) {
        this.users.put(user.getUsername(), user);
    }

    public void addUser(String username, User user) {
        this.users.put(username, user);
    }

    public User getLoggedUser() {
        return this.loggedUser;
    }

    public boolean createUser(User user) {

        if (this.users.containsKey(user.getUsername())) {
            return false;
        }
        this.users.put(user.getUsername(), user);
        return true;
    }

    public boolean validateLogin(String username, String password) {
        if (!this.users.containsKey(username)) {
            return false;
        }
        User user = this.users.get(username);

        boolean isValid = user.checkPassword(password);

        if (isValid) {
            this.loggedUser = user;
        }

        return isValid;
    }

    public Hashtable<String, User> getUsers() {
        return this.users;
    }

    public User getUser(String username) {
        return this.users.get(username);
    }
}
