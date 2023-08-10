package SpendWise.Logic;


import java.util.ArrayList;

public class Group {

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
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public String getGroupName() {
        return groupName;
    }


    
}
