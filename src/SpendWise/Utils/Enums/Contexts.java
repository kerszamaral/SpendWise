package SpendWise.Utils.Enums;

public enum Contexts {
    ACCOUNT("Account"),
    BILL("Add Expense"),
    CHARTS("Analysis"),
    GROUPS("Groups"),
    EXPENSES("Expenses"),
    LOGIN("Logout");

    private final String contextName;

    Contexts(String contextName) {
        this.contextName = contextName;
    }

    public String getContextName() {
        return contextName;
    }

    public static Contexts getContext(String contextName) {
        for (Contexts context : Contexts.values()) {
            if (context.getContextName().equals(contextName)) {
                return context;
            }
        }
        return null;
    }
}
