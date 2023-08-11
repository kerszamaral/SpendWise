package SpendWise.Logic;


import java.io.Serializable;
import java.util.ArrayList;

public class Group implements Serializable {

    private ArrayList<User> users;
    private String groupName;

    public Group(String groupName) {
        this.groupName = groupName;
        users = new ArrayList<User>();
    }

    public void addUser(User user) {
        users.add(user);
        user.getGroupManager().addGroup(this);
    }

    public void removeUser(User user) {
        users.remove(user);
        user.getGroupManager().removeGroup(this);
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public String getGroupName() {
        return groupName;
    }

    @Override
    public String toString() {
        return groupName;
    }
    
}
