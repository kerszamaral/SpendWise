package SpendWise.Utils.Enums;

public enum ExpenseType {
    FIXED("Fixed"),
    ONETIME("One-time"),
    RECURRING("Recurring");

    private final String type;

    ExpenseType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}