package SpendWise.Logic.Managers;

import java.io.Serializable;
import java.util.ArrayList;

import SpendWise.Logic.Group;
import SpendWise.Logic.User;

public class GroupManager implements Serializable {
    private ArrayList<Group> groups;

    public GroupManager() {
        this.groups = new ArrayList<Group>();
    }

    private boolean isGroupValid(String groupName) {
        
        Group group = findGroup(groupName);
        if (groups.contains(group)) {
            return true;
        }
        else {
            return false;
        }
    }

    public void createGroup(String groupName) {
        if (isGroupValid(groupName)) {
            return;
        }

        Group group = new Group(groupName);
        groups.add(group);
    }
    
    public void addGroup(Group group) {
        if (isGroupValid(group.getGroupName())) {
            return;
        }
        
        // Groups work in a "communist" way, so there is no leader in a group
        // Once you're added to a group, you'll have the same rights as everyone else
        groups.add(group);
    }

    
    public double calculateExpense(String groupName) {
        if (isGroupValid(groupName)) {
            return 0;
        }

        Group group = findGroup(groupName);
        double totalExpense = 0;
        for (User user : group.getUsers()) {
            totalExpense += user.getExpensesManager().calculateTotalExpense();
        }

        return totalExpense;
    }

    public double calculateEssentialExpense(String groupName) {
        if (!isGroupValid(groupName)) {
            return 0;
        }
        
        Group group = findGroup(groupName);
        double totalExpense = 0;
        for (User user : group.getUsers()) {
            totalExpense += user.getExpensesManager().calculateEssentialExpenses();
        }

        return totalExpense;
    }

    public double calculateNonEssentialExpense(String groupName) {
        if (!isGroupValid(groupName)) {
            return 0;
        }
        Group group = this.findGroup(groupName);

        double totalExpense = 0;
        for (User user : group.getUsers()) {
            totalExpense += user.getExpensesManager().calculateNonEssentialExpenses();
        }

        return totalExpense;
    }

   /*  public double calculatePercentage(int groupID, User user) {
        if (!isGroupValid(groupID)) {
            return 0;
        }
        ArrayList<User> group = this.groups.get(groupID);

        double totalExpense = 0;
        for (User u : group) {
            totalExpense += u.getExpensesManager().calculateTotalExpense();
        }

        return user.getExpensesManager().calculateTotalExpense() / totalExpense;
    } */

   /*  public double calculateAverage(int groupID) {
        if (!isGroupValid(groupID)) {
            return 0;
        }
        ArrayList<User> group = this.groups.get(groupID);

        double totalExpense = 0;
        for (User user : group) {
            totalExpense += user.getExpensesManager().calculateTotalExpense();
        }

        return totalExpense / group.size();
    } */

    public Group findGroup(String groupName){
        for (Group group : groups) {
            if (group.getGroupName().equals(groupName)) {
                return group;
            }
        }
        return null;
    }

    public ArrayList<Group> getGroups() {
        return this.groups;
    }
}
