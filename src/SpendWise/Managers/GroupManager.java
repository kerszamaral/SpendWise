package SpendWise.Managers;

import java.util.ArrayList;

import SpendWise.User;

public class GroupManager {
    private ArrayList<ArrayList<User>> groups;

    public GroupManager() {
        this.groups = new ArrayList<ArrayList<User>>();
    }

    private boolean isGroupIDValid(int groupID) {
        if (groupID < 0 || groupID >= this.groups.size()) {
            return false;
        }
        return true;
    }

    public boolean addUser(int groupID, User user) {
        if (!isGroupIDValid(groupID)) {
            return false;
        }
        ArrayList<User> group = this.groups.get(groupID);
        if (group.contains(user)) {
            return false;
        }
        group.add(user);
        return true;
    }

    public double calculateExpense(int groupID) {
        if (!isGroupIDValid(groupID)) {
            return 0;
        }
        ArrayList<User> group = this.groups.get(groupID);

        double totalExpense = 0;
        for (User user : group) {
            totalExpense += user.getExpensesManager().calculateTotalExpense();
        }

        return totalExpense;
    }

    public double calculateEssentialExpense(int groupID) {
        if (!isGroupIDValid(groupID)) {
            return 0;
        }
        ArrayList<User> group = this.groups.get(groupID);

        double totalExpense = 0;
        for (User user : group) {
            totalExpense += user.getExpensesManager().calculateEssentialExpenses();
        }

        return totalExpense;
    }

    public double calculateNonEssentialExpense(int groupID) {
        if (!isGroupIDValid(groupID)) {
            return 0;
        }
        ArrayList<User> group = this.groups.get(groupID);

        double totalExpense = 0;
        for (User user : group) {
            totalExpense += user.getExpensesManager().calculateNonEssentialExpenses();
        }

        return totalExpense;
    }

    public double calculatePercentage(int groupID, User user) {
        if (!isGroupIDValid(groupID)) {
            return 0;
        }
        ArrayList<User> group = this.groups.get(groupID);

        double totalExpense = 0;
        for (User u : group) {
            totalExpense += u.getExpensesManager().calculateTotalExpense();
        }

        return user.getExpensesManager().calculateTotalExpense() / totalExpense;
    }

    public double calculateAverage(int groupID) {
        if (!isGroupIDValid(groupID)) {
            return 0;
        }
        ArrayList<User> group = this.groups.get(groupID);

        double totalExpense = 0;
        for (User user : group) {
            totalExpense += user.getExpensesManager().calculateTotalExpense();
        }

        return totalExpense / group.size();
    }
}
