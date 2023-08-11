package SpendWise.Utils.Enums;

public enum GroupActions {
    ADD("Add User"),
    DELETE("Delete User");

    private final String type;

    GroupActions(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String toString() {
        return this.type;
    }
}