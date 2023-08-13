package SpendWise.Utils.Enums;

public enum Contexts {
    ACCOUNT("Account"),
    BILL("Add Expense"),
    ANALYSIS("Analysis"),
    GROUPS("Groups"),
    EXPENSES("Expenses"),
    LOGIN("Logout");

    private final String name;

    Contexts(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static Contexts getContext(String name) {
        for (Contexts context : Contexts.values()) {
            if (context.getName().equals(name)) {
                return context;
            }
        }
        return null;
    }
}
