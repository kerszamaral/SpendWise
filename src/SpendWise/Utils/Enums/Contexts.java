package SpendWise.Utils.Enums;

public enum Contexts {
    ACCOUNT("Account"),
    BILL("Bill"),
    CHARTS("Charts"),
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
}
